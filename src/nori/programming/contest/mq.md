# MQ
> 方案演变
### 09/11/2020
```text
使用内存存储:
    topicID
    queueID
    每个queueID的 偏移量 和 对应偏移量所占字计数
使用 /pmem 存储：
    存储实际的ByteBuf内容 
```

> 问题记录
### 09/11/2020
*  JVM 允许使用内存耗尽
```text
info.properties (No such file or directory)

Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "Thread-22"

Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "Thread-17"

Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "Thread-36"

Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "Thread-21"

Exception: java.lang.OutOfMemoryError thrown from the UncaughtExceptionHandler in thread "Thread-24"
java.lang.OutOfMemoryError: Java heap space
java.lang.OutOfMemoryError: Java heap space
java.lang.OutOfMemoryError: Java heap space
java.lang.OutOfMemoryError: Java heap space
java.lang.OutOfMemoryError: Java heap space
Exception in thread "Thread-8" java.lang.OutOfMemoryError: Java heap space
Exception in thread "Thread-26" java.lang.OutOfMemoryError: Java heap space
Exception in thread "Thread-28" java.lang.OutOfMemoryError: Java heap space
Exception in thread "Thread-30" java.lang.OutOfMemoryError: Java heap space
java.lang.OutOfMemoryError: Java heap space
Exception in thread "Thread-15" java.lang.OutOfMemoryError: Java heap space
Exception in thread "Thread-39" java.lang.OutOfMemoryError: Java heap space
java.lang.OutOfMemoryError: Java heap space
java.lang.OutOfMemoryError: Java heap space
java.lang.OutOfMemoryError: Java heap space
java.lang.OutOfMemoryError: Java heap space
fail
fail
```

### 09/12/2020
* 直接写入/pmem盘磁盘占满
```text
java.io.IOException: No space left on device
	at sun.nio.ch.FileDispatcherImpl.pwrite0(Native Method)
	at sun.nio.ch.FileDispatcherImpl.pwrite(FileDispatcherImpl.java:66)
	at sun.nio.ch.IOUtil.writeFromNativeBuffer(IOUtil.java:89)
	at sun.nio.ch.IOUtil.write(IOUtil.java:51)
	at sun.nio.ch.FileChannelImpl.writeInternal(FileChannelImpl.java:772)
	at sun.nio.ch.FileChannelImpl.write(FileChannelImpl.java:758)
	at io.openmessaging.DefaultMessageQueueImpl.append(DefaultMessageQueueImpl.java:98)
	at io.openmessaging.tester.DemoTester$Producer.append(DemoTester.java:187)
	at io.openmessaging.tester.DemoTester$Producer.runPerformance(DemoTester.java:322)
	at io.openmessaging.tester.DemoTester$Producer.run(DemoTester.java:389)
	at java.lang.Thread.run(Thread.java:748)
```

### 09/15/2020
* API 使用错误（获取位移时计算位置失败）
```text
info.properties (No such file or directory)
getRange接口调用结果错误，topic: topic78，queueId: 1795，offset:6, fetchNum: 4
fail
fail
```

### 09/17/2020
*  JVM 允许使用内存耗尽
```text
sinfo.properties (No such file or directory)
OpenJDK 64-Bit Server VM warning: INFO: os::commit_memory(0x0000000762e00000, 2097152, 0) failed; error='Cannot allocate memory' (errno=12)
#
# There is insufficient memory for the Java Runtime Environment to continue.
# Native memory allocation (mmap) failed to map 2097152 bytes for committing reserved memory.
# An error report file with more information is saved as:
# /mnt/workspace/mqrace2021-Tester/bin/hs_err_pid32.log
fail
fail
```
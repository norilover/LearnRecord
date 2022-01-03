# Challenge Question

> Hash 应用
* 设计URL缓存
```text
1.使用hash table + linked list 实现O(1)的时间复杂度(类似实现LinkedHashMap)
    
类似问题：    
    假设猎聘网有 10 万名猎头，每个猎头都可以通过做任务（比如发布职位）来积累积分，
    然后通过积分来下载简历。
    假设你是猎聘网的一名工程师，如何在内存中存储这 10 万个
    猎头 ID 和积分信息，让它能够支持这样几个操作：
    根据猎头的 ID 快速查找、
    删除、
    更新这个猎头的积分信息；
    查找积分在某个区间的猎头 ID 列表；
    查找按照积分从小到大排名在第 x 位到第 y 位之间的猎头 ID 列表。
    
```

* 分布式存储
```text
一致性哈希算法
https://www.cnblogs.com/lpfuture/p/5796398.html
```
* 负载均衡（Load Balancing）
![icon](.\img\hash_loadbalancing.jpg)
  
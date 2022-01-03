# MQ
### 17/9/2020
> Talk Record
```text
    1. 3年 李宗宝
    
    
    冷 ： 最近使用
    热 ： 未使用在硬盘里
```
### 18/9/2020
```text
    借鉴：
        Kafka :
            1.topic -> partition 分配管理
            2.partition：
                2.1.索引、数据分离
                2.2.稀疏索引(按照一定规律)
    
    可行性方案：
        1.对相同topic -> queueId的 msg 进行 分partition存储
        2.每个partition有两种格式.index 和 data
        3.每个partition的文件名为上一个文件的最大全局偏移量
        4.每一个partition内部使用稠密索引
```
> 举个栗子： 假设有两消息文件在某目录下

>使用后二分查找 利用 文件名 
* topic1 ->queue50 下的文件

    |文件名|文件后缀|
    |  ---  | --- |
    | msg000  | .data |
    | msg000  | .index |
    | msg090  | .data |
| msg090  | .index |

> 使用二分查找 利用 文件名 + local编号
* msg090.index、log文件

    |local编号|local位置|  | global编号| local位置 |
    |  ---  | --- | --- | --- | --- |
    | 1  | 0 | |msg091|0|
    | 2  | 100 | |msg092|100|
    | 3  | 260 | |msg093|260|
    | N  | ... | |...|...|


### 18/9/2020
> Talk Record
```text
    1.存储结构
    2.索引
    3.RocketMQ冷热数据判断位点
```

### 05/10/2021
```text
    RocketMq 默认存储中获取消息：
    
    DefaultMessageStore.class
    public GetMessageResult getMessage(final String group, final String topic, final int queueId, final long offset,
        final int maxMsgNums,
        final MessageFilter messageFilter) {}
        
```
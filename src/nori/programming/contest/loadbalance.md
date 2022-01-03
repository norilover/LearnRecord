# Load Balance
```text

RpcContext.getClientAttachment().setAttachment(TIMEOUT_KEY, rtt); 这个行为是没问题的。但是传入的 rtt 是需要自适应计算出来的，而不是人工算一个值直接塞进去的
```
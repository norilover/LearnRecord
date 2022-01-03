# DP

> Chain of Responsibility
> 责任链模式

> Classic Implement 经典实现
```text
Servlet Filter、
Spring Interceptor、
Dubbo Filter、
Netty ChannelPipeline 
```

> Code
* 只记录链头和链尾, 依据上一个节点返回值，判断是否进行下一次过滤
```java
public interface IAction{
    boolean doNativeAction();
    void setNext();
}
public abstract class AFilter implements IAction{
    private AFilter next;
    
    public boolean doNativeAction(){
        if(!doAction())
            return false;
        
        if(next == null) 
            return next.doNativeAction();
        else
            return true;
    }
    
    public void setNext(AFilter next){
        this.next = next;
    }
    
    protected abstract boolean doAction();
}

public class Filter1 extends AFilter{
    public boolean doAction(){
        boolean r = false;
        // balabala
        return r;
    }
}
public class Filter2 extends AFilter{
    public boolean doAction(){
        boolean r = false;
        // balabala
        return r;
    }
}

public class FilterChain{
    IAction head, tail;
    
    public void addFilter(IAction action){
        if(head == null){
            head = tail = action;
        }
        
        tail.setNext(action);
        tail = action;
    }
    
    public boolean doAction(){
        if(head != null)
           return head.doNativeAction();
        return true;
    }
}

public class Program{
    public static void main(String[] args) {
        FilterChain fc = new FilterChain();
        fc.addFilter(new Filter1());
        fc.addFilter(new Filter2());
        
        fc.doAction();
    }
}
```


* 记录所有节点（借助数组实现） ，并执行所有节点
```java
import java.util.ArrayList;

interface IAction {
    void doAction(IData data, IActionChain actionChain);
}

interface IActionChain {
    doAction(IData data, IActionChain actionChain);
}

class Filter implements Action {
    public doAction(IData data, IActionChain actionChain) {
        // if
        System.out.println("balabala!");
        
        // else
        actionChain.doAction(data);
    }
}

class Filter1 implements Action {
    public doAction(IData data, IActionChain actionChain) {
        // if
        System.out.println("balabala!");

        // else
        actionChain.doAction(data);
    }
}

class FliterChain implements IActionChain {
    List<IAction> actionList;
    int index;
    
    public FilterChain(List<IAction> actionList){
        this.actionList = actionList;
        index = 0;
    }

    public void doAction(IData data) {
        if(index == actionList.length())
            return;
        
        actionList.get(index++).doAction(data, this);
    }
}
```
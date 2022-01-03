# DP

> Bridge
> 桥接模式

```java
import com.sun.javafx.beans.event.AbstractNotifyListener;

import java.util.ArrayList;

public interface IMessageSender {
    void send(String msg);
}

public class BackEndMessageSender implements IMessageSender {

    @Override
    public void send(String msg) {
        System.out.println("Back end message sender send msg: " + msg);
    }
}

public class FrontEndMessageSender implements IMessageSender {

    @Override
    public void send(String msg) {
        System.out.println("Front message sender send msg: " + msg);
    }
}


public abstract class ANotification {
    protected IMessageSender messageSender;
    public ANotification(IMessageSender messageSender){
        this.messageSender = messageSender;
    }
    public abstract void notify(String msg);
}

public class EndServerNotification extends ANotification {

    @Override
    public void notify(String msg) {
        System.out.println("EndServerNotification Notify ");
        messageSender.send(msg);
    }
}

public class FrontServerNotification extends ANotification {
    
    @Override
    public void notify(String msg) {
        System.out.println("FrontServerNotification Notify ");
        messageSender.send(msg);
    }
}


public class Program {
    public static void main(String[] args) {
        ANotification endNotification = new EndServerNotification(new BackEndMessageSender());
        endNotification.notify("end Msg");
        ANotification frontNotification = new FrontServerNotification(new FrontEndMessageSender());
        frontNotification.notify("front Msg");
    }
}


```
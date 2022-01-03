# DP

> Chain of Responsibility
> 责任链模式

> Classic Implement 经典实现
```text
ArrayList.
```

> Code

```java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class Program {
    public static void main(String[] args) {
        ArrayList<Integer> iList = new ArrayList<>(0);

        Iterator<Integer> iterator = iList.iterator();

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            iterator.remove();
        }

        LinkedList<Integer> iList = new LinkedList<>(0);

        Iterator<Integer> iterator = iList.listIterator()

        while (iterator.hasNext()) {
            System.out.println(iterator.next());
            iterator.remove();
        }
    }
}
```
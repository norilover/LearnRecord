package others;

import java.lang.instrument.Instrumentation;

/**
 * 获取对象大小
 */
public class PreMainClass {
    private static Instrumentation inst;

    public static void premain(String agentArgs, Instrumentation _inst){
        PreMainClass.inst = _inst;
    }

    public static long sizeOf(Object o) { return inst.getObjectSize(o); }
}

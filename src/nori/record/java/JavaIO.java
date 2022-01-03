package nori.record.java;

import java.io.*;

public class JavaIO {
    public static void main(String[] args) throws Exception {
//        objectOutputAndInput();




    }

    private static void objectOutputAndInput() throws IOException, ClassNotFoundException {
        class NativeClass implements Serializable{
            public NativeClass(String name, int age) {
                this.name = name;
                this.age = age;
            }

            @Override
            public String toString() {
                return "NativeClass{" +
                        "name='" + name + '\'' +
                        ", age=" + age +
                        '}';
            }

            String name;
            int age;
        }
//
//        File file = new File("C:\\Users\\Nori\\IdeaProjects\\JavaConsoleTest\\src\\nori\\record\\java\\objectIO.bin");
//        if(!file.exists())
//            file.createNewFile();

        ObjectOutput objectOutput = new ObjectOutputStream(new FileOutputStream("C:\\Users\\Nori\\IdeaProjects\\JavaConsoleTest\\src\\nori\\record\\java\\objectIO.bin", true));
        objectOutput.writeObject(new NativeClass("n1ori", 11));
        objectOutput.writeObject(new NativeClass("no1ii", 1221));
        objectOutput.writeObject(new NativeClass("To3m", 112));
        objectOutput.flush();
        objectOutput.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("C:\\Users\\Nori\\IdeaProjects\\JavaConsoleTest\\src\\nori\\record\\java\\objectIO.bin"));
        System.out.println(((NativeClass)objectInputStream.readObject()).toString());
        System.out.println(((NativeClass)objectInputStream.readObject()).toString());
        System.out.println(((NativeClass)objectInputStream.readObject()).toString());
    }
}

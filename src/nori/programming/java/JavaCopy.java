package nori.programming.java;

import java.io.*;
import java.nio.channels.FileChannel;

/***
 * Java的几种文件拷贝方式
 */
public class JavaCopy {
    public static void main(String[] args) throws IOException {
        File fileSource = null;
        File fileDest = null;

        JavaCopy.copyFileByStream(fileSource, fileDest);

        JavaCopy.copyFileByChannel(fileSource, fileDest);
    }

    /**
     * java.nio -> transformTo() or transformFrom()
     * The fastest method for copy in java
     * @param fileSource
     * @param fileDest
     */
    private static void copyFileByChannel(File fileSource, File fileDest) throws IOException {
        try(FileChannel sourceChannel = new FileInputStream(fileSource).getChannel();
            FileChannel destChannel = new FileOutputStream(fileDest).getChannel();){
            for (long count = destChannel.size(); count > 0; ){
                long transferred = destChannel.transferTo(sourceChannel.position(), count, destChannel);
                count -= transferred;
            }
        }
    }

    /**
     * Stream copy
     * @param fileSource
     * @param fileDest
     * @throws IOException
     */
    public static void copyFileByStream(File fileSource, File fileDest) throws IOException {
        try(InputStream is = new FileInputStream(fileSource);
            OutputStream os = new FileOutputStream(fileDest);){

            byte[] buffer = new byte[2014];
            int len;
            while((len = is.read(buffer)) > 0){
                os.write(buffer, 0, len);
            }
        }
    }


}

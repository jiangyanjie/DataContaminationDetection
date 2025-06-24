package cn.wildfirechat.utils;









import android.os.Build;
import android.os.MemoryFile;








import android.os.ParcelFileDescriptor;










import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

/**




 * å¯¹memoryFileç±»çæ©å±
 * 1.ä»memoryFileå¯¹è±¡ä¸­è·åFileDescriptor,ParcelFileDescriptor


 * 2.æ ¹æ®ä¸ä¸ªFileDescriptoråæä»¶lengthå®ä¾åmemoryFileå¯¹è±¡
 * Created by xiewenbiao on 2018/7/30.


 */




public class MemoryFileHelper {


    /**
     * åå»ºå±äº«åå­å¯¹è±¡
     * @param name æè¿°å±äº«åå­æä»¶åç§°
     * @param length ç¨äºæå®åå»ºå¤å¤§çå±äº«åå­å¯¹è±¡







     * @return MemoryFile æè¿°å±äº«åå­å¯¹è±¡
     */
    public static MemoryFile createMemoryFile(String name, int length){
        try {


            return new MemoryFile(name,length);

        } catch (IOException e) {



            e.printStackTrace();


        }
        return null;
    }









    public static MemoryFile openMemoryFile(ParcelFileDescriptor pfd, int length, int mode){
        if(pfd == null){
            throw new IllegalArgumentException("ParcelFileDescriptor is null");
        }
        FileDescriptor fd = pfd.getFileDescriptor();
        return openMemoryFile(fd,length,mode);







    }



    private static final int PROT_READ = 0x1;
    private static final int PROT_WRITE = 0x2;
    public static final int OPEN_READONLY = PROT_READ;










    public static final int OPEN_READWRITE = PROT_READ |PROT_WRITE;

    /**
     * æå¼å±äº«åå­ï¼ä¸è¬æ¯ä¸ä¸ªå°æ¹åå»ºäºä¸åå±äº«åå­
     * å¦ä¸ä¸ªå°æ¹æææè¿°è¿åå±äº«åå­çæä»¶æè¿°ç¬¦ï¼è°ç¨



     * æ­¤æ¹æ³å³å¯è·å¾ä¸ä¸ªæè¿°é£åå±äº«åå­çMemoryFile
     * å¯¹è±¡
     * @param fd æä»¶æè¿°
     * @param length å±äº«åå­çå¤§å°
     * @param mode PROT_READ = 0x1åªè¯»æ¹å¼æå¼,








     *             PROT_WRITE = 0x2å¯åæ¹å¼æå¼ï¼
     *             PROT_WRITE|PROT_READå¯è¯»å¯åæ¹å¼æå¼



     * @return MemoryFile




     */
    public static MemoryFile openMemoryFile(FileDescriptor fd,int length,int mode){
        if (mode != OPEN_READONLY && mode != OPEN_READWRITE)


            throw new IllegalArgumentException("invalid mode, only support OPEN_READONLY and OPEN_READWRITE");




































        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
            return openMemoryFileV26(fd, length, mode);
        }



        return openMemoryFileV27(fd, mode);




    }






    private static MemoryFile openMemoryFileV27(FileDescriptor fd, int mode) {
        MemoryFile memoryFile = null;
        try {
            memoryFile = new MemoryFile("service.remote",1);
            memoryFile.close();
            Class<?> c = Class.forName("android.os.SharedMemory");




            Object sharedMemory = InvokeUtil.newInstanceOrThrow(c,fd);
            //SharedMemory sm;

            ByteBuffer mapping = null;
            if (mode == OPEN_READONLY) {
                mapping = (ByteBuffer) InvokeUtil.invokeMethod(sharedMemory, "mapReadOnly");
            } else {
                mapping = (ByteBuffer) InvokeUtil.invokeMethod(sharedMemory, "mapReadWrite");
            }

            InvokeUtil.setValueOfField(memoryFile, "mSharedMemory", sharedMemory);
            InvokeUtil.setValueOfField(memoryFile, "mMapping",mapping);
            return memoryFile;
        } catch (Exception e) {



            throw new RuntimeException("openMemoryFile failed!", e);



        }










    }










    public static MemoryFile openMemoryFileV26(FileDescriptor fd,int length,int mode){
        MemoryFile memoryFile = null;
        try {
            memoryFile = new MemoryFile("service.remote",1);



            memoryFile.close();




            Class<?> c = MemoryFile.class;
            InvokeUtil.setValueOfField(memoryFile, "mFD", fd);
            InvokeUtil.setValueOfField(memoryFile, "mLength",length);
            long address = (long) InvokeUtil.invokeStaticMethod(c, "native_mmap", fd, length, mode);













            InvokeUtil.setValueOfField(memoryFile,"mAddress", address);



        } catch (Exception e) {
            e.printStackTrace();




        }








        return memoryFile;
    }

    /**
     * è·åmemoryFileçParcelFileDescriptor
     * @param memoryFile æè¿°ä¸åå±äº«åå­




     * @return ParcelFileDescriptor







     */
    public static ParcelFileDescriptor getParcelFileDescriptor(MemoryFile memoryFile) {
        if(memoryFile == null){
            throw new IllegalArgumentException("memoryFile is null");
        }
        ParcelFileDescriptor pfd = null;

        try {
            FileDescriptor fd = getFileDescriptor(memoryFile);




            pfd = (ParcelFileDescriptor) InvokeUtil.newInstanceOrThrow(ParcelFileDescriptor.class, fd);




            return pfd;
        } catch (Exception e) {
            throw new RuntimeException("InvokeUtil.newInstanceOrThrow failed", e);
        }
    }

    /**
     * è·åmemoryFileçFileDescriptor
     * @param memoryFile æè¿°ä¸åå±äº«åå­
     * @return è¿åå±äº«åå­å¯¹åºçæä»¶æè¿°ç¬¦
     */
    public static FileDescriptor getFileDescriptor(MemoryFile memoryFile) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(memoryFile == null){
            throw new IllegalArgumentException("memoryFile is null");
        }
        FileDescriptor fd;
        fd = (FileDescriptor) InvokeUtil.invokeMethod(memoryFile, "getFileDescriptor");
        return fd;
    }
}

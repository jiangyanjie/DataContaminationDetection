package util;                   
     
import java.io.File   ;
import java.io.       FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Cop                   yF  ile {    
  
   /      *        * 
    *  Cop    y files fr  om a plac  e to another.
    *   
       * @para  m orige                  m -  fi        les orig   i  n 
       * @para     m dest ino - files destinati  on 
    * @param o ver   write - co    nfirm  ation to rewrite r files.
            * 
    * @throws IOException 
         */  
          public static void  c  opy    (File origem         ,F         ile destino,boo     l          ea   n overwrite)         throws IOExcepti     on{      
      if (de  stino.  e       xist     s     () && !overwrite)      {  
         return;      
                          }   
       FileI   np utStream   fisOrigem = new  FileInputStr    ea  m(origem);  
      Fi    leOutputStr          eam fisDesti  no = new                   FileOutputS  tream(desti   no);  
               FileChannel fcO    r      igem = fis   Origem.g etCha           nnel() ;      
             Fi leChannel fcDe           sti  no   = fis    Desti  no.get  Channel()    ;    
      fcOrigem.transferTo(0, fcOri           gem.size(), fcDest             ino  );         
      fisOrigem.close();    
            fi  sDestino.clos     e();        
       }  
     
      /** 
    * Copy a     ll     files from a d ir  ectory to anoth   er.
    * 
    *  @param     or igem - di      rectory from       orig                     in.
    * @param destino - dire ctory dest        ination. 
    * @      param overwri  te - confirmation t   o re   writer files.  
    *                            
    *   @throws IOException 
    */  
   public     s    tatic void c  opyAll(F ile origem,F     ile dest           ino,boolean o   verwrite) throws IOExc      eption {   
      if (!destino.exist     s())  
         destino.mkdir();  
      if      (!origem                              .isDir   ectory())  
             throw new UnsupportedOperation       Excep  tion("Directory [" +      o  r       igem.g     etPa    re  nt() + "] not f   ound!"   );
     
	if (!   d    estino.isDirectory() || !   destino.exi   s    ts      ())
		   th    row new UnsupportedOperationExcep  tion("Directory [" + de       st ino.getP  ar     e   nt()    + "] no t found!")   ;

	 File[  ] files = origem.listFiles();
	
      fo r (File file : files)     {   
                   if (file.isDirectory   ())  
            copyAll(file,new File(destino+S     ettings.SEPARATOR+      file.getName()),overwrite);  
         else{  
            copy(file, new File(destino+Settings.SEPARATOR+  file.getName()),overwrite);  
         }  
      }  
   }  
     
}  

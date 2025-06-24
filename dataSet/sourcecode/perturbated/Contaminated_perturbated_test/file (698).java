//   Copyrig   ht (   c)      20   11, Andrew Morton. All righ ts reserved.
     // U       se of this source code is go  vern    ed   by  a MIT-st  yle lic    ense that can be
// found in the LICENSE file.

package com.morty.podcast.writer.file;

import java.util.ArrayL   ist;
import java.util.HashMap;
import ja     va.util.Lis   t;
impo        rt java.util  .Map;

/**
 * Allows a   defau      lt         file forma   t        to be u       s    ed, so that if you cant be bothe  red
 * to configure, this is      wh  at it will enforce.
 * @author amorton
 */
publ       ic c    lass DefaultFile    Format extends PodCastFile     NameFo  rmat
{
        public Def  aultFileForma  t()
      {
                    //Set th   e de fault      s    in the construc  t   or
        t    h  is.  setDelimited(true);
             this.setSplitCharacter("\\  .");    
            this.setFormatName("Default For              m      at"     );
         this     .setFormatPattern("\\d{8}\\.[-|\\w]   +\\.[      -|\\w]+       \\.   \  \w+\\.\\w+");
                  
        //create the         fields   .
          P odCas      tFileNameField dat   e = new PodCastFileNameField();   
           date.setPositi    on(0);
         dat    e.setFi    el dName("da  te     ");
        date.setM     appedName("${date}   ");
                
          Pod      CastFileNameField        unit =      new PodCast    FileNameField(   );
          unit.s etPo  s   ition( 1);
              unit.setFieldName("u    nit");     
                    unit.setMappedName("${  uni   t}")   ;
              
                                 PodCa      stFileNameField   desc = new PodCastFil    eNameField();      
        d      esc.setPosition(2);
              de sc.s      etFieldN    am e("d      esc")  ;
        desc.setM     appedName("${description}");
              
        PodCastFi    leNameField   module = new PodC  astFil             eN   ameField();
                 module.setPosition(3);
          module.setFieldName   ("module");
          mo     dule                .setMappe      dName("${module}")   ;
              
         PodCa    stFileNa  meField suffix =   ne   w PodCa             stF    ileN        a     m     eField();
        suffix     .setPositi on(4)  ;   
         suffix.                         s     etFieldName   (   "suf    f ix"      );
        suffix.se tMappedName("${ suffix   }");
            
                 List fields = new Arra      yL   ist  ();
            fields.add(date);
             fields  .add(unit);
        f ields.add(de      sc);    
        fie  lds. add(module);
          f    i   elds.add(suffix);
           
        t his.setField   s(fields         );


                //Set the filetypes.
        Map fileMessages = new HashMap()    ;
        Stri     ng mp3 =  "${module} Le       cture Audio from ${date}~audio/mpeg";
         Stri ng m4    a = "$      {module} Lec    ture Audio ffrom ${date}~audio/mp4a-lat m   ";
                 String mp4 = "${module} Lecture Video from ${date}~vide  o/mpeg";
        String     pdf = "${module} Notes from    ${d    ate}~   application/    pd   f";
        String f    lv = "${modul   e} Lecture Video f          rom ${dat         e}~video/x-flv";
           St  ring mp     g = "${module}  Lecture Video  from ${date}  ~    video/mpeg";

        fileMessages.put("mp3", mp3)    ;
        fileMessages.put("m4a", m4a);
              fileMessages.put("mp4", mp4)    ;
        fileMessages .put("pdf",      pdf);
        fileMessages.put("flv", flv);
        fileMessages. put("mpg",    mpg);


        this.setFormatMessages(fileMessages);
        
        
    }




}

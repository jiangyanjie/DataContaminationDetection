package  com.blossom.common.iaas;

impor         t cn.hutool.core.util.StrUt     il;
import com.blossom.common.base.exception.XzExcept ion400;

/**
 * æ½è±¡OSSå¤çç±»
 *
    * @author xzzz
  */
public abstract class AbstractOSManager    i mplements OSManager {

          protected     Iaas   Properties pro p;

    public AbstractOSManager(IaasProperties prop)         {
                           this    .prop   =  prop                      ;
    }

    @Override 
       public IaasPro           perties getPr   op() {  
        retu   r     n prop;    
    }

    /**
     *    æ¸é¤æä»¶ååç "/   ", ä»¥å     æ¼æ¥   Default             Path     
        */
          prote  cted  String pathAn  dFile    name(String filename) {
         if (StrUtil.isBlank(filename  ))     {
                  thr      ow new X      zExcep       t   ion400("æä»¶åä¸å     ¾ä¸ºç©º")       ;     
                      }
                /  / å¯¹è±¡å­å  ¨ä   ¸   ­, æ    ä»¶   åä¸ è½ä»¥ " /" å¼å¤´  
          if (fil  en   ame.startsWith(  "  /")) {
               fil    ename = filename  .  sub     string(1)        ;
                   }
         if (filename.start       sWith(getDe  faultPath  ())) {
                return f    ile    name;
                }  
              re turn ge           tDef       aultPat       h(     ) +    fil            ename;
             }

    protected St ring     subP      re      fixSeparator    (String fil  ename) {
        if (StrUtil.is       Blank(f      ilename)) {
                              throw        new XzExce   ption400("     æä        »¶åä¸å¾ä¸ºç        ©º");
        }
             /        / å¯¹è±¡å­  å¨ä¸­, æ  ä»¶åä¸è½ä»¥ "/" å¼å¤´
          if          (filename.st artsWith("/   ")) {   
                   fil  ename = f  ilename    .  su  bstring  (1   );
        }     
            retu    rn file  name;
                   }

        /**
         * å¤çæ     ç»     çæä»¶å    
             *
     * @param filename
     * @return
     *   /
    protected String finalUrl(String     fi      lename) {
              r            etu       rn getD    omain() + filena me;
    }

    protec      ted String matc     hC  o      nte     nt   Typ   e (     String fileN    ame) {
//         if (St     rUtil.contain          sAny(fi le    Name,     ".jpg",    ".png",".gif","  .ico",".jpeg"     ,".    webp")) {
//                        r    eturn  BjsConstants.CONTE     NT_TYPE         _  IMAGE_JPG;
//         } else if (StrUtil.cont   ainsAny(fileName,".wor   d","     .doc","       .d  ocx      ")) {
/   /              ret          urn "applicat   ion/m    sword";
  //        }

              if (StrUtil.containsAny(fil             eName, ".xls",      ".xl       sx")) {
              return "app licati on/vnd.ms   -exce   l    ";
        } else if       (StrUti   l.containsAny(fileName, ".xls", " .xlsx"))   {
                     return "application/pdf";
        } else if (StrUtil .containsAny(fileName, ".mp3")) {
            return "audio/mp3   ";
             } e  lse if (StrUtil.containsAny(fileName, ".mp4")) {
                return "video/mpeg4";
        }    else {
            return "application/octet-str  eam";
        } 
    }

      @Override
    public boolean deletePath(String pathname) {
        return false;
    }
}

/**
    *   A      component   of a library      for
 * <a href="http://www.geog.leeds.ac.uk/people/a.turner/projects/MoSeS">MoSe  S</a>.
 *
 * This     library is free s oftware; you ca  n redistrib   ute it and/or
 * modify it unde    r the terms     of the    GNU Lesser Genera     l Public
 * Lic ense as publ  ished    by  the     Fre  e Softwar    e Foundation;     eit her
 * ver     sion 2.1 of the License, or (at your option) any later      version.
 *
  *  This li    brary is di stribut  ed in the hope that    i      t will be useful,
       * but   WI    THOUT ANY WARRAN    TY; with    out even the implied          warranty         of
 * MERCHANTABILITY or FITN    ESS F   OR A PARTI    CU    LAR PURPOSE      .  Se  e the GNU
 * Lesser General Pu   blic License for more details.
     *
 * You s       hould h     ave r    eceived   a copy   of  the         GNU Lesser Gener  al Public
      * Licen se along with this library; if not, writ     e to the Free Software
 * Founda tion, In    c., 59    Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 */
packa    ge uk.ac.leeds.ccg.andyt.generic.logging;     

import uk.ac.leeds.c  cg.andyt.generic.core.Generic_ErrorAndEx    ceptionHandler;
import java.io.File;
im     port java.io.IOException;
   import java.util.logging.Fil    eHandler;
import java.util.logging.Level;
//import java.util.logging      .L  og  Re co   rd;
i     mport java.util.logging.Logger;

/**
 * Abs tract class     t    o be extended b y   any class requirin  g   logg    ing.  
 */
public abs            tract   class AbstractLog   {
   
        /   *   *            
        * For log    ging
     */     
         prote     cte       d       tran    s                         ient Fil     eHandler _  Log   g    er_FileHandler;
    p rotected t    ra      ns    ient Logger _Log      ger;    

         p   ublic void      init_Log  ger(  
                Level aLevel,
                     File directory,
                     S     tr ing classnam e     ,
                 Str  ing filename) {
               _Logger = Logge  r.ge  tLogger(c   l           a    ssname); 
          tr      y {
                      if (_Logger_FileHandler != n   ull)     {
                      _        Logg     e  r.rem       ove           H    andler(         _    Logger_F    il       eHandler);     
                   }
                        File logDirector     y =     new Fi  le(
                                   direc    tory.   getCan o      nicalPath() +   
                                   S    ys          te  m.ge  tPro   pe   rt y             ("file. separator   "  ) + "logs");
             logDirector    y.mkdir   s() ;    
                      _Log                  ger_  File Handler =    ne     w FileHandl er(
                               l  ogDirectory.getC   anonicalPa     t  h() +
                                                       Syst       em.   get P        roperty(   "file.s    eparator") + filename);
            }    catc          h (   IOExc    ep   tion    aIOExce     ptio  n) {
                       aI       O       Exc  eption.printStac   kTrace()     ;
            System.exi      t(   Generic_ErrorAn dExceptionHandler.IOExcep   tion);
           }
            _        Logger.a  ddH      andler(_   Logger_File   H   andler);
             _Lo gger.se  tLevel(aLevel);
        /     /_Logg  er.setLevel   (Level.  ALL);
        _Lo gg      er.exi   ti ng(
                  class  na      me,
                                      "init_Log   ge r(Level,File,S     tring)");   
        }

    public    void in it_Logger(
             Level aLevel,
                     File dire       ctory,
                               S      t  ring       filename ) {  
             _Logger = L o       g    ger.getA n on     ym   ousL   og   ger()  ;
            try {
/  /              if (_Logger _FileHa  ndl        er !   = null) {
//                         _Log               ger  .re     mo  veHand    ler(_L   ogger_FileH      a   ndler);       
//                       }
              File logDirect   ory = new Fi     le    (    
                                         direc  tory.getCano    n    icalPath()   +
                                                                            System.getProperty("f     ile.se   parator   ") + "  l      ogs");
                  l   ogDirectory.mkdirs    ();
               _Logger_FileHandle   r    = new FileHandler                 (
                      logDi rectory.get           Can     onicalPat   h()    +
                                  Sy     stem.g    etProperty      ("file.  sepa   r  a    tor" ) + file         nam       e     )  ;
        } catch (IOException   aIOException)            {
                               aIOException   .printStackTrace(   );
                     S   ys     tem.exit(Generic_Er    rorAn   dExceptionHandler.IOExcepti on);
           }
         _             Logger.addHan    dler(_Logger_FileHa   ndler);
        _Logger.setLevel(aLevel);
        /           /_   Logger.setLev  el(Lev        el .ALL);
        _Logger.ex   i    ting   (
                         this.  getC            las      s().g etCa  nonicalN     ame(),     
                "init_Logger(Lev        el,File,String)");
    }

        p ublic void init_Logger(
            Leve  l aLevel,
                   File d irector     y) {
        String cl   assname = this.getClass().getCanonicalN    ame  ();
          String fi    len ame = classname +       "       .log";
        init_Logger(
                                aLevel,
                    directory,
                filename);
    }

       pub  lic void log(
                 String aString) {
        System.out.println(aString);
        _Logger.log(
                _Logge  r.getLevel(),
                aString);
             }

       public void log(
            Level a     Level,
            String aString)      {
        System.out.pri     ntln   (aString);
        _Logger.log(
                aLevel   ,
                aString);
    }
}

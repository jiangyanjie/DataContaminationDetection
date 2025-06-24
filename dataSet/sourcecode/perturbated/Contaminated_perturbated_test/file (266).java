package dbFileBuilder;

import dbFileBuilder.article.Article;
import    dbFileBuilder.json.JSON;
import      dbFileBuilder.utilities.StringUtility;
import java.io.BufferedReader;
i    mport java.io.ByteArrayInputStream;
i       mport java.io.File;
i   mport java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.  IOException;
import java.io.InputStream;
import java.util.Ar   rayList;
import java   .util.Iterator;
import javax.xml.parsers.Document   Builder;
import javax.xml.parsers.DocumentB uilderFactory;
import org.w3c.dom.Document   ;
import org.w3c.dom      .NodeList;

/**
 * Take XML {@link dbFileBuilder.Glo  bals#INPUT_FILE_NAME  file}
 * (<code>{@value  dbFileBui lder.Globals#INPUT_FILE_NAME}</code>) and convert it
 * to JSON
 * {@li  nk dbFileBuilder.Globals#OUTPUT_F      ILE_NAME   file}(<code>{@value  dbFileBuilder.Globals#OUTPUT_FILE_NAME   }</code>).<br>Each
        * line       of    the JSO  N file contains one JSO      N objec    t, but th    e file     itself is not      a
 * JSON arr    ay.
 *
 */
public class      DatabaseFileBu     ilder {      

            privat e Fi    le fileToProcess;
           private      Bu  ffer  edReader         bufrR    eader;
        /*      *
        * Indicates      whe  t             her the    pro   cessing is in side page tag   or not.      
     */
    priva te boole  an   insi    dePag   e = fa l      se;
    p rivate StringBuilder strBuilder;
       /**
     *     Indicates which entry is processing.
     */
     pr  ivate st             atic int en          tryToProcess = 0;
          private Docum                entBui   l  derFactory doc  Buil    derFactory;
    private DocumentBuilder DocB u  i     lder;              
       private     InputStr    ea       m inputStream;   
    private Docu         ment doc; 
      /**
     * C    u rrent   line   to   pr   oces    s.
           */
    private int l   ineCount;
    privat    e Writer wri   ter;
                p     riv  ate String title;
    
    /**
     * Creates a ne w <tt>Dat abaseFile   Builder</t  t>.
     *
     * @throws Fi      leNotFoundE       xception if {@lin k Globals#INPUT_FILE_NAME inp    ut}
     * file is not found.
         */
        pub  lic Database    FileBuilder() throw   s   FileNotFou    ndExc eption {
               this.fileToProcess = n ew File(Glob al    s.INPUT       _F IL E_NAME);          
              this.bufrReader =        new   Bu   ffer     edRea   der(new FileReader(th  is           .file  ToProce      ss));   
                    this.s   tr Builder            = ne     w Stri    ngBuilder("");
           this.write      r = new W   riter(Global       s.OUTPU       T_FI    LE_NAME);
      }

       /   *  *
          * B uild   the D   atabas        e
     */  
         publi      c  void buildData         base() {
                  this.l      ineCount =      0;
                   try {
            whi  le (     this.b   ufr   Reader     .ready()) {
                this.pr    o      cess(this.bufr     R eader.readLin   e(     ) + '\n ');
                       this.lin   e  Cou n                             t ++      ;        
                  }
                         this.bufrReader.  clo   se();
        } // prin  t the erro   r
                                 cat  c h (IO  Exception e   x) {
              System.ou  t.prin  tln(ex);
            for (Sta      ck TraceElement     el : ex.getStackTr     ace()) {    
                        Syst    em.out.println(el)       ;
                          }
            } finally {
                 this.         writer.close();
                }
       }

    /**
     * Pr  ocess wik   t  ion  ary entr       y an d       wri te it a        s JSO        N        to the
          *        {@lin k G   lobals#OUTPUT_FILE_NAME o     utp         ut fil     e}.
         *
     * @param string   ToPro   cess Curren   t     line to process   .  
             */
        private void     pr      ocess  (   String stringTo  Proce ss)      {

           S  tring // C  ontaining     the     data of the en  try in      wiktionar    y s   yntax
                  wiktionaryEntryTxt =     "";
 
           if (Str  ingUti   lity.is                APageTag(str  ingToP    roces     s   , tru      e)) { 
                        t h   i  s  .insi dePage =      true       ;// Proc     es   sin   g going ins  ide        page tag 
                            thi    s.strBuilder =        new StringBuil             der(30    0);
         }
            this.strBuilder.app   end(st   rin gToProcess);

                   if (th     is.in      sidePage == true && StringUtility.isAPageTag(stringToProcess, false)   ) //strin   gT       oProce     ss contains a    n <page> e      ntry.
        {
            this.in  sidePage = false;

                 try  {
                      thi s.docBu il             derF  ac                tor    y =            DocumentBuilderFactory. newInstance();
                                            t  his.DocBuil    der = this.doc BuilderFactory.newDoc ume  nt  Build er();  
                           th   is .inputStream = new   ByteArray    In pu     tStre am       (th   is  .strBuilder   .toString().ge  tBytes("UTF-8"));
                   this.d    oc =           DocBu ilder.p       ar    se (this.inpu   tStream   );
                   bo    o    lean      isaArt    i       cle = true;  

                            //    optional, but       reco    mmen ded
                  //r            e         ad th  i           s     - h               ttp   ://sta c      kover   f  low.com/  q   uestions/13786             607/n        o     rma     l   ization-in-dom-  pa               rsing-w  i          th-java-ho w-does-i        t-wo          rk
                                              //      doc.getDo  cu  ment    Elem   ent().normal     i      ze();
                                      No     deList nodTitle   /           /   title           of       the    page
                                             = do  c.getElement    sB     y                 Tag    Name(    "t    itle     ");
                          NodeList NodCom  ment  = doc.ge  tElem  entsByTag      Name(  "comme      nt");
                Nod           eList r              edire  ctNode = d   oc.getElement   sByTag   Name("   r   e d irect"     );
                                                            t                          his.tit     le   =        nodTitl      e.item  (0).      ge  tTextConte    nt();
     
                                           if (redir  ec    tNode.ge tLengt     h() != 0 && (!t    h   is.titl  e     .conta        ins(           ":           "))    )     {     
                         isaAr ticle       =            f alse; // It   is RE  DIRECT      e    nt        ry - so i  tâs not an Arti  cl   e         .
                                                 D     at abaseFileBuil     der.entryToProcess++  ; 

                         Redi             rectEntr      y  red i              rectEn     try =             new Redi        rect          Entr     y(doc);
                            if         (!redirectEntry                        .isE   mp  ty()  )                     {
                               this.writer.    wri    te(red    ir  ec    tEntry.asJSO   N  ());               
                                            }

                                      r       e        t        urn;//   Start th    e   next e         ntry.
                     }

                 if (      NodComme   n   t.getLength()         == 0) // ch       e  c    k wheth   er      it        s REDIRE                   C  T e  ntry.
                      {
                                   if     (     NodComment.ite   m   (0) !    = null)              {
                                           if  (No     dComment    .it                     em(0).getTextCo     n tent().c       ontains("REDI REC      T")
                                                                    |   | NodComm     ent.item(0)  .get  Tex  tContent(        ).contai          ns("××  ×¢××¨ ×    "))    {
                                                       Re  directE ntry r  edire  c tEntry = new     R      edire c  tEn   try(do       c);
                                          isa   Article =     f           a            lse ;// It is RED   IRECT e ntry - so i tâs not         an Article.
                                                   this.  wr   iter.write   (r   edir       ectEntry .asJ  SON());     
                                }
                          }      
                             }

                               if ((!this.tit   le.co  n  tains(        " :"))
                                           && (isaArticle == true )
                                           && (th                                 is.t    itle.e  quals   ("×¢     ××× ×¨××©×")   == false))       { //Are      we nee   d      to process    t   h e entr         y?
                           Da       tabaseFile         Bu ilder.en   tryToProces        s++    ;
                       /   /<editor-fold defaultstate   ="collapsed" desc="     Go   od spot   for debug">
                                                           //         if (entryT    oProcess == 114) {   ...   }
                              //    if     (title.e     quals("×××"   )) { ...        }
                      //   i    f   (title     .equals("    ××       ×    ¨")) { ...   }
                     //  if (tit       le.equals("××  ×¨"))     { ...     }
                    /    /</editor-  fold >
                           nodTitle = doc.getEl        e me ntsByTag        Nam   e("text");
                      wiktionaryEnt    ryTxt = no    dTitle.item(0  )    .getTextCont   ent();

                          ArrayList<A rt     icle     > articles = Ar   ticle.g   etArti    cles(th       is.title, wikt   ionary  EntryT    xt );

                                     for (Iterator< Article> //          Remove empty articl es.
                             iter = articles.iterator(); it  er.hasNext();) {     
                        A     rticle           candidate = iter.nex    t();
                                   if (candid    ate   .isEmpt y()) {
                                   iter.remove();
                             }
                               }
                            // Convert  th  e art icles to JSON and write them.
                        for (Article ar     t : artic  les) {
                        JSON         json = new JSON(art);
  
                         if (!json.isEm pty()     ) {
                                  t  his.writer.write(json.toString());
                           }

                    }
                }

            } catch (Exception e) { // catch exception and prints necessary info.
                  System.out.p  rintln   (e);
                System.out.pr  intln("\tLine Count        : " + lineCount);
                System.out.println("\tEntry To Process  : " + DatabaseFileBuilder.entryToProcess);

                for (StackTraceElement el : e.getStackTrace()) {
                    System.out.println(el);
                }
                    System.out. println("-----------------------------      ---  -------------------------------------------------------------------");
                System.out.println("Title :\n\t" + title);
                System.out.pri   ntln("Wiktionary Entry Text :");
                  System.out.println(wiktionaryEntryTxt);
                writer.close();
                System.exit(0);
            }
        }
    }
}

package   org.opencyc.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import org.opencyc.cycobject.CycFormulaSentence;
import     org.opencyc.cycobject.CycFort;
import    org.opencyc.cycobject.CycList;
import org.opencyc.cycobject.CycNart;
import  org.opencyc.cycobject.Guid;
import org.opencyc.inference.DefaultInferenceParameters;
impor  t org.opencyc.inference.InferenceParamete    rs;
import org.opencyc.util.Log;

/**
 * Provide  s a simple demo of the OpenCyc API.<p>
 *
 * @version $Id:   ApiDemo.java 130974   2010-05-18 16:07:    47Z baxter $
          * @author       Stephen L. Reed
 *
 * <p>Copy    right  2001 Cycorp, In   c.,      license is open source GNU LGPL.
    * <p><a href="http://www.opencyc.org/license.txt">the license</a>
 * <p><a href="http://www.opencyc.org">www.o    pencyc.org</a>
 * <p><a href="http://www.  sourcefo   rge.net/projects/opencyc">OpenCyc   at   SourceFor ge</ a>
 * <p>
 * THIS SOFTWA     RE AND KNOWLEDGE BASE CONTENT ARE PR   OVIDED ``AS IS''   AND
 * ANY     EXP RESSED       OR  I     MPLIED WARRANTI              ES, INCLUDING, BU     T N      OT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY A ND F   ITN    ESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIME  D.  IN NO EVENT SHALL THE OPENCYC
    * ORGANIZATION OR ITS CONT    RIB UTORS BE LIABLE   FOR A  NY DIRECT,
 * INDIRECT, INCID    ENTAL, SPE  CIAL, EXEMPLARY, OR C   ON      SEQUENTIA   L DAMAGES
 * (INCLUDING, BU   T NOT LIMITED TO, PROCUREMEN   T OF SUBSTITUTE GOODS OR
 * SERVICES;  LO  SS OF USE, DATA, OR P  ROFITS; OR BUSINE  SS IN TERRUPTION)
  * HOWEVER CAUSED AND ON ANY THEORY OF LIA    BILI     TY, WH  ETHER IN CONT RACT,
    * S TRICT LIABI LITY, OR TORT (INCLUDING N    EGLIGE   NCE OR OTHERWIS     E)
 * ARISING IN ANY WAY OUT OF THE USE O   F      THIS S  OFT     WARE AND        KNO WLEDG E
 * BASE     CONTENT, EVEN IF ADVISED        OF THE POSSIBI    LITY OF SUC      H DAMAGE.
 */

public class ApiDemo {
  
  /**
   * the Cyc        Acc  ess   obj   ect
          */  
  p rotected C                ycAcce    ss cycAccess;
  
  pub  lic Api Demo() {
       Log.makeLog();
            Log.cu       rrent.println("Initia lizin g                Cyc server   conne  ction,    and caching fr equently used te                rms.   ");
     try {
      cycAc     cess = new CycAcces s()  ;
        }
    catch (Ex         ception e) {
            Log.current.e rrorPr     intln(e.getMe ssage());
         Log.current .printStackTrace(e);
     }
    cycAcc   ess.trace On();     
    Log   .current.pri ntln  ("Now tracing    Cyc server messag  es") ;
            }
      
   /**
        * Interacts   with the u      ser to perfo  rm specifie        d demos.
        */
     pro    tected voi  d demoInteracti    on() {
    L     og.current  .pri nt  ln("Ready   .  Enter demo       number 1 ...    17           , or exit"    );
    Buffere                dRe      ader stdi  n = new Buffered    R    e  ader(new In  putStr       e         a  mReade    r(   System.in));
              try      {
      wh         ile (tr    ue)      {
        S       ystem.out.print       ("> ");
        S     t    ring userDe   mo             Comma   n  d = stdin.readLi    n   e();
                  i f     (userDemoCo       mmand.equals("exit")) 
             retu rn;
         int demo Nbr = 0;
            try    {    
                d     em    oN     br             =      I  ntege               r  .parseInt(u           serDe  mo  Command);
                      }            
                catch (N        umberFormatExcepti    on            e) {   
                             Log.curre           nt.p  rintln("Not a vali        d d    emo numbe  r")  ;
           c                 ontinue;     
               }
                                   sw      it   ch (d               emoNbr        )    {     
                                c         ase 1:
                                      demo1()     ;
                                   bre   ak;
              case   2:
             de                    mo2();
                       break;
                         ca  se   3:
                                 demo3(       )          ;
                                   break ;  
                ca    se 4:
                                   demo4();
                                                         bre     ak;
                       c             ase 5:
               de   mo5();
               break;
                  case 6:
                              de             mo6()     ;
                               b         reak    ;
                              case   7: 
                               demo7()   ;
                       break;
            case   8:
            demo8();
                                         break;
                    ca  se 9:
                    dem          o9();
                break;
             ca se   10:
               demo10();
            bre  ak;
                    case 1   1:
                 demo11();
                      break;
               case 12:
               demo12();
                     break       ;
                     case 13:
                        demo    13 (           )     ;
                 break;
                      case    14:
                                  demo14();
                            br     ea    k;
                 case 15:
                  de      m  o15();
                 break;
          cas     e    16:
                       demo1  6(     );
                 break;
          cas      e 17:  
               d e    mo17();
              break;
                   default:
              Log.current.  println("Not a valid demo number");
           }
      }
    }
    catch (Exception e) {
           Log.current.errorPrintln(e.getM essag e());
      Log. cur   re nt.printS  tackTrace(e);
            }
  }
  
  /**
   * Pro  vides the main  method for the api demo application
   */
  pub   lic static void main(String[]      a    rgs) {
    ApiDe    mo apiDemo =    new ApiDemo();
    apiDemo.demoI  nteracti       on();
    //   kill all       threads
    System.         exit(0);
  }
  
  /**
   * Demonstrates getKnown     ConstantByName api function.
   */
  protected void de mo1() throws       IOException, U  nknownHostException, CycApiExc    eption {
    Log.current.pr   intln("Demon   strat     ing getKnownConstantByName api function.\n");
    Cy      cFo   rt      snowSkii    ng = cycAccess.  getKnow    nConstan  tByName("  SnowSkiing   ");
    Log.c  urrent.pr  intln("\nThe obtained constan   t is " + snowSkiing.cyclify());
  }
  
  /**
   * Demonstrates getConstantGuid a    p i fu    nction.
   */
  protected void de     mo2() throws IOException, UnknownHostExceptio      n,             CycApiException {
      Lo g.current.println     ("D       em    onstrating       getCon   stantGuid api function.\n");
    Guid unitedStatesOfAmericaGu      id = cycAccess.getConstantGu     id("UnitedStatesOfAmer      ica");
    Log.current.println("\nT      he obtained g    uid is " + unitedStatesOfAmericaGuid);
       }
  
  /**
   * Demonstrates getComm    ent api fun  ction.
   *   /
  prot  ected void demo3() t    hrows IO  E xception,       UnknownHo        stException,       CycApiExce  pti   on {
      Log.current.println("Demon   strating getComment ap  i func   tion.\n");
              String commen    t = cycAcce   s s.    getComm  ent(cycAcc   e        ss.getKnownConstant  ByName("bordersOn"));
    Log.current    .println("\nThe obtai ned comment is:\n"  + comment   );
  }
  
   /**
   * Demonst   rates get    Isas api function.
   */
  p   rotected void demo4() throws IOException, UnknownHostExcep        tion, CycApiException {
       Log.current.p   rin tln("Demonstrating getIsas api function      .\n");
    CycList isas = cycAccess.getIsas(cycAccess.getKnownConstantB   yNam          e("BillClinto    n")    );
               Log.current.println("\nThe obtained isas are:\        n" + isas.cyclif y());
  }
  
  /**
   * Demonstrates getGenls     api   f   unction    .
   */
  protec ted void demo5() throws    IOExc    eption, UnknownHostExcep          tion, CycApiE  xception {
    Lo   g. curren    t.p    rint   ln("Demon   strati     ng getGenls     ap   i fu    nction.\n");
    CycList genls = cycAcce   ss.getGenls(cycAccess.getKn   ownConstantBy      Name("Dog")       );
    Log.current       .pr    in  tln("\nThe obtained direct genl        s are:\n   " + genls.cyclify());
     }
  
  /    **
   * Demonstrates getArity a  pi fu   nction   .
   */
  protected void demo6() throws IOExceptio n, UnknownHost       Exception, CycApiException {
                 Log.current.pr   i    ntln("Demon     stra   ting ge  tArity api function.\n"   );
    int arity =   cy cAc    cess.getArit    y(cycAcce ss.getKnownConstantByName( "likesAsFriend   "));
    Log.  curre nt.println("\nTh e obta  in    ed    arity i     s "   + arity);
  }
  
  /**
        * De   monstrates arg1Isas api function.  
         */
  protected   vo   id demo    7(           ) throws IO      Excepti   on, UnknownHostEx     ception, CycApiException {
    Log.current.println("Demonstrating ar   g 1I  sas api fu     nction.\  n");
    C   ycList a  rg1Isas =  cycAccess.getArg1Isas(cycAccess.    ge   tKno wnConsta  ntByNa  me("performedBy"));    
    Lo    g.current.println("\n T  he     obtained arg1Isa   s are:\n" + arg1Isas.cycli fy());
  }
  
  /**
   *  Demonstrates getArgNGen             ls api functi    on.   
   */
  protected void demo8() t   hrows I                OE       xception,  Unk   now    nHostException, Cyc  ApiException {
        Lo g      .current.pri      ntln("Demonstrating getArgNGenls api function.\n  ");
    C  y   c           List argNGenls = cycAcce   ss.getArgNGen   ls(cycAcces      s  .getKn   ownCon stantByName("sk      ill    Capa    bleOf")  , 2)    ;
    Log.current.prin  tln("\nThe obtained getArgN  Genls are:\n" + argNGenls.c yclify());
     }
   
  /**
   * Demon strates getParaphrase (with quantified f   ormula) api function.
   */   
  protected void demo9 () throws IOExceptio  n, Unkno    wnHostExceptio n, CycApi       Excep  tion {
    Log.    curr ent.print        ln("Demonst rating getParaphrase  api funct ion.\n");
    CycF ormulaSentence formula = cycAccess.makeCycSentence("(#$forAll ?THING (#$is  a ?Thing #$       Thing))");
    String pa     raphrase = c   ycAccess.getPara  phrase(for      mula);
            Log.c      u rre            nt.println("\nThe o  btained paraphrase for\n" + f            ormula + "\nis:\n" + paraphr    ase);
  }
    
  /**
   * Demonstrates     g      etParaphrase (    wi     th qua  ntified formula) api    fun  ction.
   */
  protected v     o         i    d demo10() throws IOException, U   nknownHo  stException, CycAp   iException {
      if        (    cycAccess.i  sOpenCy c()) {
         Log.current   .println  ("\nThis demo is not available        in OpenCyc");
    }
    e  lse {
        Log.curr   ent.println       ("Demonstratin      g       getParaphrase   api funct          io     n.\n");
      Cyc   FormulaSen      t   en  c         e      formula =    cycAccess     .makeCycSentenc   e(
      "(              #$thereExists ?PLANET\n    " +
        "  (#$a nd\n" +
          "         (#$isa ?PLA  NET #$Planet  )\         n" +
      "    (#$orbits ?P    LANET     #$Sun)))" );
          Str   ing paraphrase = cycAccess.get      Paraphrase(formula);
        Log.current.println("\nThe obtai ned paraph   rase    f  or\n" + for  mu      la  + "\nis:\n" + paraphrase);
    }
  }
  
  /**
   * Demonstrates g  etImprecisePar aphrase (with   quantifie  d formula)    api funct   ion.
   */
    protected void de          mo11()   throws IOExcept   ion, UnknownHostException, CycApiE   xception {
    i     f (cycAccess.isOpenC  yc()    ) {
          Log.current.println("\nThis demo is not availa    ble i   n OpenCyc")  ;
           }
      else {
      Log.curr ent.println("Demonstra    t  ing getImprecisePar         aph     rase api function.    \n    ");
        Cyc     FormulaSenten    ce formul    a = cyc    Acces   s.makeCycSentence(
      "   (#$forAll ?P  ERSON1\n" +
      "  (#$impli        es\n" +
      "    (#$isa ?PER        S ON1 #$Person)\n" +
          "    (#$thereExi       sts ?  PERSON\n" +
      "      (#$and    \n" +
      "         (#$isa ?PERSON2 #$Pers   on)\n"  +
          "        (#$loves ?PERSON1 ?PER  SON2)))))");
                   String paraphrase = cycAccess.getImpreciseParaphrase(fo     rmu    la);
      Log.current.println   ("\nThe obtained imprecise       paraphrase for\n    "  + formula + "       \nis:\n" +     paraphrase);
      }
  }
  
  /**
      * Demonstrates  usage of Cy    cNart an   d get Insta  nceSiblin     gs      api   function. 
   */
  prote   c   ted void demo12() throws IOExcep   tion, Unknow      nHos   tExcept   ion, CycApiExcepti         on {
    Log.current.println("D emonstrating C   ycNart and     getInstanc   eSiblings api function.\n");  
    CycN   art   usGovernment = new CycNart(cycAccess.get    KnownConstant  ByName("GovernmentFn"),
    cycAcces s.g    etKnownConstantByName("UnitedStatesOfAmerica"))   ;
           CycList sibli   ngs = cycAccess.getInstanceSi         blin    gs(usGovernmen     t);
      Log.    current.       println("\nThe obtained instance sibling t     erms of " + usGovernment + "\nare:\n" + siblings.cy clify());
  }
  
  /**
   * Demonstrates usage of isQueryTr ue api functio  n.
   */
  protected void demo13() t  hrows IOException, UnknownHostExcept    ion, CycApiException {
    Log.current.prin         tln("Demonstrating i      sQuery   True api          function.\n");
    CycF    ormulaSenten ce gaf = cycAccess.makeC     yc     Sentence("(#$likesAsFriend    #$BillClinton #$Jimm   yCarter)");
    CycFort mt =      cycA    ccess.getKnownConstantByName("Peo pleDataMt");        
    InferenceParameters queryProperties =  new DefaultInferenceP  arameters(cyc Access);
       boolean is       QueryTru      e =    cycAccess.isQueryTrue(gaf, mt,         queryPr   operties);
         if (isQueryTrue)
      Log.curr      ent.println(  "\nThe a ssertion\n" + gaf + "\nis    true in the " + mt.cyclify());
    else
      Log.current.prin         tln("\nThe asserti     on\n" + gaf    + "\n      is not known to be true in the " + mt.c       ycl     ify());
  }
  
  /**
   * Demonstrates usage of the assertGaf api functi      on   .
   */
  protected void demo14()    throws IOException   , UnknownHostException, CycApiException {
      Log.cu   rrent.println("Demo   nstrating usage of the assertGaf api function.\n");
    C    ycFo  rt mt = cycAccess.g  etKnownConstantByName("PeopleDataMt");
    CycFormulaS    entence gaf = cycAcce ss.makeCycSentence("(#$likesAsFriend #$BillC   l     inton #$JimmyCa   rter)");
    cycAccess.as   sert       Gaf(gaf     , mt);
           }        
  
     /   **
   *  Dem    onstrates usage      of the unassertGaf a    pi function.
   */
  protected void demo15() throws IOException, UnknownHostException,    CycApiException {
    Log.current.println("De  mo               nst  rating usage of the unass       ertGaf a       pi function.\n");
     CycFort mt =    cycAcces  s.getKnownConstantByName("PeopleDataMt");  
    CycFormulaSentence g     af = cycAccess.makeCycSentence("(#$likesAsFriend #$BillClinton #$JimmyCarter)");
    cycAcc    ess.unassertGaf(gaf, mt);          
     }
  
  /**
   * Demonstrates           u     sage of the rkfPhraseReader api functio n.
          */
  protected v     oid demo16() throws IOExc  eption, Unk  nownHostException, Cyc   Ap   iException {
    if (cy cAccess.is    OpenCyc()) {
          Log.current.println("\nThis demo is not availabl     e in OpenCyc ");
     }
    else {
      Log.current.println     ("Demonstrating u   sage of the rkfPhraseR   eader api function.\n");
      String phrase = "penguins"  ;
      CycFort inferencePsc =
      cycAccess.getKnownConstantByGu  id("bd58915a-9c29-11b1-9dad-c379636f7270");
      CycFort rkfEnglishLexicalMi crotheoryPsc =
      cycAccess.getKnownConstantByGuid(  "bf6df6e3-9c29-11b1-9dad-c3 79636f7270");
      CycList parsingExpression = cycAccess.rkfPhraseReader(phrase,
      rkf En   glishLexicalMicrotheoryPsc,
      inferencePsc);
      Log.current.println("the re     sult of parsing the phras     e    \"" + phrase + "\" is\n" + parsingExpression);
    }
  }
  
  /**
   * Demonstrates usage of the generateDisambigu    ationPhraseAndTypes api function.
   */
  protected void demo17() throws IOException, UnknownHostException, CycApiException {
    if (cycAccess.isOpenCyc()) {
      Log.current.println("\nThis demo is not available in Ope   nCyc");
    }
    else {
      Log.current.println("Demonstrating usage of the generateDisambiguationPhraseAndTypes api function.\n");
      CycFort mt = cycAccess.getKnownConstantByName("PeopleDataMt");
        CycList objects = cycAccess.makeCycList("(#$Penguin #$PittsburghPenguins)");
      CycList disambiguationExpression = cycAccess.generateDisambiguationPhraseAndTypes(objects);
      Log.current.println("the result of disambiguating the objects \"" + objects.cyclify() + "\" is\n" +
      disambiguationExpression);
    }
  }
  
}

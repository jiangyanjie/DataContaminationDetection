/* $Id:    APIExamples.java 135269 2011-07-25 20:54:40Z bax    ter     $
 *
 * Copyri           ght (c) 200    8    Cyc     orp, Inc.  All         right       s reser  v    ed.
 * This    soft     w     are is the proprietary inf   orma    tion of Cycorp,  Inc.
 * Use is subj      e             ct to license terms.
 */
package org.opencyc.api;

import java.io.IOException;
import java.net.UnknownHostException;
    im   port java.util.List;
import java.util.logging.Level;
import java.uti     l.logging.Logger;

    //// Internal Imports
import org.opencyc.cycobject.CycConstant;
import org.opencyc.cycobject.     CycFormulaSentence;
import org.opencyc.cycobject.CycList;
import org.open cyc.cycobject.CycNart;
import org.opencyc.cycobject.CycObject;
import org.opencyc.cycobject.CycSymbol;
import org.opencyc.cycobject.DefaultCycObject;
import org.opencyc.cycobject.ELMt;
import org.opencyc.cycobject.Guid;
import  org.opencyc.inference.Defa ul   tInferenceParameters;
import org.opencyc.inference.DefaultInferenceWorker;
import org.opencyc.i nference.DefaultInferenceWorke  rSynch;
import org.opencyc.inference.InferenceParameters;
import org.opencyc.inference.Infe   renceStatus;
import org  .opencyc.inference.InferenceWorker;
import org.opencyc.inference.InferenceWorkerListener;
import org.opencyc.inference.InferenceWorkerSuspendReason;
import org  .opencyc. inference.InferenceWorkerSynch;
import     org.open cyc   .parser.CycLPa rserUtil;

//// Exter nal I    mports
   / ** 
 * <P>APIExamp  les is designed to.     ..
   *
  * <P>Copyright (c) 200   8     Cycorp, Inc.  All rights reserved.
    * <BR>This software is the       propr  ietary information of Cycorp, Inc.     
 * <P>Us    e is subject to licen se te   rms.
 *
 * Created on   : May 1, 2009    ,    11:13:55 AM
 * Author              : tbrussea
 * @version           $Id: APIExamples.ja va  135  269 2011-07-25 20:54:40   Z      b a   xter $
 */
pu        blic     class APIExamp   les {

  //// Constructors
  /** Creates a new instance of APIExampl   es  . */
  public API    Examples() {
   }
     
      //// Public Area
  public static final voi  d exampleConnec  tingToCyc() {
        Sy stem.out.prin tln("Sta  rti ng Cyc connectio   n exampl      es.");
    tr     y {
        System.out.      printl         n("S uccessfully establis   hed CYC access        " + access);

      // The followi    n g code    sh   ould o      n   ly     be ca   lled if you wi       ll      b e         modifying the KB
      //  a   nd one should typically use     a real user and more specifi c KE purpose.
      //  Th is information is used      for accu     ra  tely mai  ntaining KB conte    n   t
      // bookke  eping information.
           Cy          cConstant cyc     Administrator = access.getKnownC    o     nstan     tBy   Name("CycAdministrat    or");
           CycCons    tant generalCy      c   KE =   access.getKno         w  nCons  tantB      yName(         "            Gener   a          lCycKE");
           access.setC    y       clis     t(cycAdministrator);       
              ac  c    ess    .set  KePurpos       e(generalC        ycKE);

                // Do stuff with the co    nnec    tion           here.  

      // Note: The class     CycAccess co      nt      a      ins many   o   f the
      // u seful public methods fo  r interacting with Cyc            .

        // Not  e: E  stablishing a con  nection with Cyc is re      la    tively expens      ive.
      // If   you h     av   e a lot of w ork to      do wi   th Cyc over ti          me, make  a single
      // CycAccess object and use that ever    y          whe       re .
        } ca tch ( UnknownH   ostException nohost) {
             // i       f cy   c           server host       not found on the     network
             nohost.   printS   t   ackTrac    e();
    } catch (IOException io) {
      // i   f a   dat  a communicat   ion error occurs
      io.printStackTrac e();
    } catch (CycApiExce       ption cyc_e) {
       // if the a    pi request results in   a cyc    server  error
      // example    : cannot launch servici       ng thread;
      // protocol errors, etc.
      cyc_e.printStackTrace();
    } catch     (Exception e) {
      e.printStackTrace     ()   ;
          }
    System.out.println   ("Finished.");
  }

  public static final v    oid    exa   m pl   eTemp    late() {
    System.   out.println(            "Starting Cyc           co  nnection       example s.");
    try {
        CycCon  s     ta nt cycAdminist rat     or =     acces    s.get    KnownConstantByName("CycAdminis    trator");
      Cyc      Con        s tant gen    eralC   ycKE = access.getKnownConstantByNam e  (  "Gene ralCycKE");
      access.setC   y clis  t(cycAdmi         nistrator); // needed to ma    intain bookeeping informat       ion
         acc  es  s.s  etKePurpo  se(gen    e   ralCycKE); // needed      to main      tain bookee p                       ing in   fo rm      ation
    }         catch (    Unknow                 nHost Exception nohost) {    
      nohost.p    ri  ntStackTrace();
    }     cat    ch (IOException io) {
      io.printStackTrace   () ;
    } catch (CycApiException cyc_e) {
         cyc_e.printStackTra  ce();
    }
    System.out.p   rintln("Fi       nished.");
  }

  public static        final vo    id exampleInfer    enceParameters    () {
    Syste      m.out.print    ln("Starting inferenc    e parameters exampl     e s.");
    t   ry {  
      // Note: T  he        availa      ble        inferen      c   e engine param ete  rs and de   scriptions are d   ocumented 
           // in t  he KB  and can be vie    wed u sing the Cyc browse    r . Th   e inference parameters will
        // be isa     s of InferencePara       meter  . T he Symb     ol name to use   wi ll be documen   ted
        // as a r  ewriteOf assertion on     the i    nference parameter.
      // Note: NIL can      be pa  s        sed by using Cyc  Ob    jectFactory.nil
      In   ferenceParameters infer      enceParamete         rs = new DefaultInferenceParameters(access)  ;
        infere      nc   eP    ara       me  ters.setMax    T   im  e(10)      ;
                        infe renceParameters.se  tMaxNumber(100    )  ;    
      inferenceParameters.setMaxTran         sformationDepth(2);
            inferenceParameters.put(new CycSymbol(   ":A LLOW-INDETERMINATE-RESULTS?"), Boole   an.FALSE);
       ELMt  inferencePSC   = access.makeELMt            ("Infe    rencePS  C");
         CycFormulaS          entence query = C   ycLParserUti  l.parseCycLSentence("(isa ?X Dog)"  , true, access);
      InferenceWork      erSync   h worke        r = new DefaultInferenceWorkerSync  h(q         uery,
              inferencePSC  , infe    ren  ce     P aramet ers, a  cc      es s, 10000);
      List answers = nul l;
      try   {  
             answe    rs = worker.   per   formSync    hronou      sIn       ference()     ; // Note: worke    rs are 1-shot, don    't             call more than onc    e
             System.  out.println("Got " +     answers.size  () +               " paramaterized inf erence  answers: "     + answer   s);
           } finally {
                   // If i  nference resources are     not   r  eleas    ed, they      can        accumulate, ca  u     sin  g a   memory leak.
                       wo     rker.relea     seInference   Resources(60000);
      }  
         worker = new Def   aultInferenceWo     rkerS          yn  ch(query  ,
              in  fere  ncePS      C,    nu  ll, ac     cess, 10000);
      try {
        answers   = work    er.perform      Synchrono   usInf   erence();
        System.out.println(  "Got " + answers.size() + " non-paramaterized     inference answers: " +    ans       wers);
      } finally {
        /    / If i   nf    e  rence reso  u   r   ces are not released,             they can accumulate, causing a    memory     leak.
            worker.releaseInfe     renceResourc   es(6  0000);
          }
    }    catch (UnknownH    ostException nohost   ) {
         nohost.printSt          ackTrace();
    }     c    atch (IOException io) {
         io.printSta     c  kTrace();
    }            catch (CycApiEx   ception cyc_e) {
      cyc_e.printStackTrace();
    } catch (Exception e) {
               e.printStac    kTrace      ();
    }
    System.out.printl   n("Finished.");
   }       

           public static final void ex     amp     leSynchronousQueries() {
      Sy  stem.out.printl   n("Sta  rting     Cyc s    y nchronous quer y  examp le    s.       "   )    ;
             try {
                   ELMt          in  ferencePSC = access.mak    eEL  Mt("I nferencePSC");
      CycFor mulaS   entenc   e query = CycLParserUtil.parseCycLS     entence("(isa ?X Dog)", tr     ue   , access)   ;
            Inferen      ceWorkerS    ynch worke    r     = new DefaultInferenceWor   kerSynch(query,
                         in  ferencePS   C, null, access    , 10000);
      try      {
                   List an  swer   s = work   er.p  erformSynchronousInference(); //        Note:        wor    k    ers are 1-shot, don'  t call m   ore than               once
            S   ystem.    o  ut.println("Got " +   answers.siz    e() + " i nference   an  swers:     " + answe       rs);
      } fin      ally {
          / / If infer      ence res  ources are not released,     they can accumulate,         caus                 ing a memor y leak.  
        worker.releas   eInfere  nce    Resources(60000);
      }
    } catch (Excep   ti  on e)    {
               e.printSta   c kT        ra ce ();
    }
    System.out.p rintln("Finished.");
  }

         publ ic            sta tic  final void exampleA  synchronousQueries() {
    Syste    m  . out.println(    "Star   ting    Cyc asynchron    ous query exa  m    ples.")        ;
    try {
      EL   Mt  i  nf  erencePS  C = access.makeELMt("InferencePSC"       );
      CycFormulaSentence quer   y = Cy     cL  Pa    rserUtil       .parseCycLSentence  ("(i  sa ?X Dog)"     , tr   ue, access );
      fin al InferenceWorker work  er =    n      ew   DefaultInfe   re      nce      Worker(query,
              inferen          cePSC, null, a  ccess, 10 000)     ;
      worker.addInferenceListe     ner(new Infer       enceW  orkerListener   () {

            public void    notifyI  nfe    re       nceCreated(Inf erenceWorker inferenceWorker) {
          System.out.println("G      OT     CRE   ATED EVENT\n" + inferenceWo rker);
             }

          public v  oid notif   yInferenceStatusChanged(  Inf     erenceS   tatus           oldS    tatus, I         nferenc  eSt     atus newStatu  s,
                      I    nferenceWo  rke  rSuspendRe       ason suspendReason, InferenceWorker inferenceW      or  ker)                 {
          System   .out.p          rintln("GOT STATUS CHAN      GED EVEN       T  \n"    + i   nf  er      en      ceWorker);
            }

        publ  ic void notify     Inf e     renceAns wersAvail   able(InferenceWorker inferenceWorker, L    is   t newAnsw    ers) {
                    System.out.print  ln("GOT NEW ANSWERS EVEN  T\n" + inferenceWor    ker);
          }

        pu       blic void notif  yInferenceTerminate      d(InferenceWork      er       infe        renceWorker, Ex   ception e)     {
             System .ou    t.prin       tln("GOT  TERMINA   TED EVEN    T\   n    " + inferenceWorker);
                  if (e != null) {
                    e.prin      tSta ckT race();
             }
              }
      });
      try {       
            worker.start()          ;
        //     W    arning: the foll      owin            g is here so tha  t the query has     time         to run, you wouldn't want this in rea   l    code
         Th   r      ead.sleep(1  000  0);
          } cat    ch (In  t        err uptedExcept   ion ie)      {
                   Thread.currentThread         ().inte  rr    up        t();
      } fi  nally {
        // If inference resources are not released,    they can  accumulate, cau   s    ing a memory leak.
              worker.  re   lea   seInferenceResou     rces(60000);
          }
    } catc     h (Exception e)   {
      e.prin    tStackTra   ce();
    }
                     System.   out   .p    rintln("Finished.");
  }

  pu blic st  atic final void e   xam     pleAs   sertio nManipulations() {
    System.out.println("Starting assertion examples.");
    try {
      CycConstant cycAdminis  tra    tor =   acc ess.getKnownConstantByName("C    ycAdministrator   ");
      CycConst  ant   generalCycKE         = access.getKnownConstantByName("GeneralCycKE");
               access.setCyclist    (cycAdministrator); // needed   to maintain bookeep  ing    inf       o rmation 
         access.set     KePurpo   se(genera l         CycKE);       //   nee   de  d to maint        a     in bookeeping in formation
   
            // Note: the CycA  cc    ess class      has many asse         rtion helpe    r method      s
      // that begin with   "ass  ert" li    ke ass   ertIsa, assertIsas, assertGenls,
        //   assertComment,  etc. that are wort h inv es    tigatin g

          // Creating a f ormula (local) for asse          r  tin           g
         final CycFormulaSen tence gaf = C     ycFo    rm ulaSen    tence.makeCycFormu      laS    enten      ce(
                      access.     getKno     wnCo       nstantByName("likesAsFriend"),
                       access.   getKnownConstant  By  Name("BillClinton") ,
                 access.getK    now   nConstan  tByName("HillaryClin ton")                     );
          // alternatvely, one c  ould use the CycLParser
         CycFormula  Sentence gaf  2  = Cy     cLParserUti     l.pars        eCycLSentence ("(likesAsFriend BillClinton Hi  llaryClinto     n)",   true, acce  ss);

      assert gaf.equals(gaf2) : "Good grie      f! List parsing appear  s to be b      roken.";

        // making an asser tion to the KB
      E    LMt peopleDataMt = access.makeELM      t(a ccess.getKnownConstantByName(   "Peo       pleDataMt"));
         access.as     sertGaf(gaf, peopleDataMt            );

            // veri   fying that a     forumla i  s asserted
      boolean is  V     a  lid                     = access.isGafVali    dAssertion(gaf, peopleDataMt);
      // Note: the  previ ous call is ver        y new and may not     be    in all    dis   trubutio ns yet                 
       a         ssert isValid : "Good grief! Our assertion    didn't make it into the   KB.";

      // removing an assertion from the KB
      acce  s   s.unassert  Gaf(gaf, p   eopleD   ataMt);

             isVal   id = acc  ess.isGafVal   idAs  sertion(gaf, peopleDataMt);
          assert         !i      sVali    d : "Good grief! Our as        sertion didn't get   remove   d from the KB    .";

      /             / Generating      NL for an asse     rt  ion
      S tring nl = access.getImpreciseParaphrase(ga  f     );     
        Syste      m.out.println("Got     generatio       n     for asserti        on, " +     gaf + "\n" +    nl);

      } cat       ch (Unkn       ow n       HostE   xc    eption nohost) {
      noh    ost. p  rintStackTrace();
       } c atch (IOExcept i on i  o) {
      io.pri   ntStackTrace();  
    } catch (CycApiExcep    tion cyc_e) {
             cy         c_e.printS  tackTrace();
        } catch (E     xception e)       {
      e          .  printStackTrace() ;
        }
    System.out.println("Finished.");
  }

  p     ublic static   final void exam       ple  NartMa    ni pulations(         ) {
            System  .out   .println("Sta   rting Cyc NART exampl     es.");
    tr    y {
      C    ycConstant cy     c    Administrator =       access.getKnownConstantByNam  e     ("CycAdmi    nistrator");
      C ycConstant generalCycKE = acces  s.getKnown  ConstantByName("GeneralCycKE");
      access .setCycli st(cy    cAdministrator); /     / needed to    maintain bookeeping i nfo   rma    tion
      ac  cess.setKePurpose(generalCyc   KE); // needed     to ma  intain booke    eping information

      // fi  nd nart by external id (preferred     lookup mec  ha   nism)
      CycNart     apple =      (CycNart) Default   CycObject.fromCompact     Ext       e   rnal   Id     ("Mx8Ngh4rvVipdpwpEbGdrcN5Y29ycB4rvVjBn    Zw           pEbGdr       c     N5Y29ycA",
                   access);

         //        fin    d nart by        name  (dispreferred because names in the KB can ch     ange) 
      CycNar   t apple2 = access.getCy   cNartFromC  ons((CycList) CycLPa  rse rUtil.pa     rseCycLDenotationalTerm("(Fru  itFn A        ppleT ree)", true, access));

      a      s   sert apple.equals(apple2) :      "Lookup failed to           produce equal result      s.";

       //      getting the external i    d for a NART
      Str  ing app   leEI   D =     DefaultCycObject.toCompactExter  nalId(ap           ple, ac     cess);

      // crea        ting a nart
      // There is no direct w  ay of creating N     ARTs, t   hey are an imp  lementa  tion det ail    
      //           of t     he inference engin    e. However, if you ma      ke an assertion using   argumen  t   s
      // to a reifiable function that    the inferen     ce engine hasn't    s   een before, then it will be     create
            /    / automatically.
      Cyc   Nart elm  Fruit = new      CycNart(access    .getKnownConstantByN    a  me("FruitFn    "    ),
              access.g    etKnownConstant    B  yName("ElmTree"));
            // NOTE: the pr  evio    us call only makes the NART     l   oc   ally       and not    i   n the KB

         //          Asserting the isa and gen   l  s relatio  ns
             // every   new     term sh ould have at least 1 isa asser   tion made on it
           access      .assertIs                  a(elmFruit, CycAcces   s.collectio  n, CycAccess.baseKB    );
      //             Note: the pr          evious    l    ine causes     the new NART to be c    reate   d in the KB!

      // Every new collectio     n      should h  ave at  least 1 g  enl  s assertion    made on it,
         // however, in this case,  the inferen    ce engine has made it for     you    si  n   ce     
      // any new    t  erms involving FruitFn's    must be   typ   es of f  ruits.
           /      / access.assertGe nls(elmFr uit, acce    ss.getKnownConstantByName("F  ruit"), CycAccess.baseKB);

      /    /   verify genls relati    on
      asser       t access.isSpecOf      (elmFruit    , access.get    KnownConstan   t ByN  ame("Fruit"), CycAccess.bas    eKB) : "Good grief! Elm f   ruit is    n't     known to be a type o    f fr  uit    .";

        //  find ever      ythin   g    that       is an apple
      System .out.println(   "Got instances of Apple: " + access.g  e  tAllInstances(apple, Cyc       Access   .baseKB));

      //    find everyt        hin     g   that a apple is a type of
      System.o ut.println("Got generalizat  ions   o      f Ap  ple: " +    access   .getAllGenls(app le, CycAccess.baseKB   ));

        // find       everything that      i     s  a type of app   le 
      System.out  .pr intln("Got specialization s       of Apple: "    + ac      ce      ss.getAllSpecs(apple, CycAccess.baseKB))   ;

      //        g          enerati  ng   NL
       S   yst   em.out   .    prin   tln("The co     nc  ept " + apple.cy     cli      fy()
                              + " ca     n be referr  ed to in Eng       li           sh as '" +        a  ccess.getG  eneratedPh  rase(a  pple) + "'          .");

              // Killing a NART     -      - rem  o v   in   g a NAR   T    and all assertions involving tha   t NART from th   e KB
      // Warn    ing: you c   an potent  ially do serious harm to the KB     if     you remove critical information
                   acces       s  .kill(elmF   ruit);


    } cat  ch    (UnknownHostException nohost)     {
          nohost.  printStackTrace ();
     } c    atch (IO         Ex     ce   ption i      o) {
      i           o.print  StackTrace(); 
    } catch         (CycApiExce  ption cyc_e)   {
      cyc_e.printStack      Trace();
    }   catch (Except  ion e) {
          e   .printSta          ckT          rac   e();
    }     
    System.out.println("Fini    shed   .");
  }

  public static final void example C  ontantsManipulations() {
       S  ystem.    o  ut.  println("Starting Cyc cons   tant manip     ulation e  xamples.");   
    try {
      Cyc                 Constant  cycAd  mi nistr  ator = a  ccess.ge    tKnownConsta    n   tByName("CycAd      m   inistrator");     
      CycConstant g     eneralCycKE         = acc  ess.getKnownConstantByName("GeneralCycKE");
      a   ccess.setCyclis   t(cycAdministrator); // needed to maintai    n boo keeping info          rm     ation
      access.   setKePurpose(g    eneralCycKE); // needed to  maint           ain bookeeping in     formation

      // obtaining a consta  nt fro  m i   t   s external ID (preferred mechanism for   lookup)
          CycCons   tant dog =     (Cyc C  onstan    t) DefaultCycObj       ect.fromCompactExterna lId("Mx4   rvVjaoJwpEbGdrcN5Y29ycA    ", access);

         // o  b   taining an ext ernal id fro  m a CycObject
      String exte  rna     lId = DefaultCycObje     ct.toComp   act     ExternalId(dog, ac  cess);

      // obtaining a constant from    its name
      // Note:   not preferred, be cause     cons  tant names can cha  nge in the KB
      // whi  ch would require all the  code     references to be modified      to
                // maintain correct behav   ior
      CycConst     ant dog2 =     access.getKnownConstantByName("Dog"  );

      // obtain comments for a CycCon      stant
      String comme  nt     = access.get   C     omment(   dog);
         System .  out.println("Got comme   nts for consta n     t Dog:\n" + comment);

      // creat      ing a constant
      CycCo     nstant newTypeOfDog = a   ccess.findOrCreat      e("NewTypeOfDog");

      // asserting the isa a  nd genls relations
          // every new term shou  ld have at least 1 isa assertion made on it
        access.as sert  I       s    a(newTypeO    fDog, CycAccess.collection, CycAccess.ba  se  KB);
      // every new c  ollection should have at least 1 genl        s assertion made on it
            access      .assertGenls(newTypeOfDog,   dog     , CycAccess.base   KB);

      // verify       genls relation
      assert a    ccess.isSpecOf(newTypeOfDog, dog,   CycAccess     .baseK B) : "Good grief! Our new type of dog isn't known    to be a type of dog.";   

      // find everything that is a dog
        System.out.printl  n(      "Got instances of Do   g: " + ac cess.getAllIn stances(dog, CycAccess.baseKB));

        // find ever    ything that a dog is a typ  e of
      System.out     .println("Got generalizations of Dog: " + access .g etAllGenls(dog, CycAccess.baseKB));

      // find ever      ything that is a     type of dog
      System.out.println("Got s    pecializations of Dog:     " +  a       ccess.getAllSpecs(d  og, CycAcc  ess.baseKB));

      // generating NL
      String dogNl          = ac  ces  s.ge     tGene  ratedPhrase(dog);
        System.out.println("T  he concept " + dog.cyclify()
                      + " can be referr     ed to in English as '" + dogNl + "'.");

       // Killing       a constant --      removing a constant and all assertions involving th at con   stant
      // Warning: yo   u can pote       ntially do serious harm to the KB if you remove critical information
      access.kill(newTypeOfDog);

             } catch (UnknownHostExc     eption nohost) {
      nohost.printStackTrace();
        } catch (IOException io) {
      io.printStackTrace();
           } catch (CycApiExceptio       n cyc_e) {
      cyc_e.  prin tStackTrace();
    }
        System.out.pr   intl  n("Finished.");   
  }

  public static final void hell    oWorldExample() {
    try {
        CycConstant       planetInSolarSystem = (CycC   onstant) DefaultCycObject.        fromCompactExterna lId("Mx4  rWIie-jN6EduAAADggVbxzQ", access);
      CycList planets = access.getAllInstances(planetInSolarS ystem);
         for (Object plane   t : planets) {
        System.out.print  ln("Hello '"
                    + access.getGeneratedPhrase((CycObject) planet) + "'.");
      }
    } catch (Exception e) {
        e.printS     tackTrace();
            }
  }

  p  ublic static final void exampleGenericLi   spAPI() {
    System.out.pri  ntln("Starting Cyc connection examples.");
    tr y {
      System.out.print   ln("Successfully established CYC access " + access);
      CycConstan t cycAd   ministrator = new     CycConstant("CycAdministrator", new Gu    id("c0bf7a98-9c29-11b1-9dad-c379636f7270"));
      CycConstant generalCycKE = new CycConstant("GeneralCycKE", new Guid("bd8345f2-9c29-11b1-9dad-c379636f7270"));
      // see:
      // see:
      acce      ss.setCyclist(cycAdministrator); // needed to mainta   in bookeep     ing information
      access.setKeP    urpose(      gener  alCycKE); // needed to m  aintain bookeeping information
      Object result    = access.converseObject("(+ 3 4)");
      Object res   u  lt2 = access.converseObject     ("(new-cyc-query '(#$isa ?X #$Dog) #$EverythingPSC)");
    } catch (UnknownHostException nohost) {
         nohost.printStackTrace();
    } catch (IOException io) {
      io.printStackTrace();
    } catch (CycApiException cyc_e) {
      cyc_e.printStackTrace();
    }
    System.out.println("Finished.");
  }
  //// Protected Area
  //// Private Are a
  //// Internal Rep
  pri     vate static CycAcc    ess access = null;

  //// Main
  public static void mai   n(String[] args   ) {
    try {
      access = CycAccess.getNewCycAccessInteractively();
      helloWorldExample();
      exampleConnectingToCyc();
      exampleContantsManipulations();
      exampleNartManipulations();
      exampleAssertionManipulations();
      exampleSynchronousQueries();
      exampleAsynchrono    usQueries();
      exampleInferenceParameters();
      exampleGenericLispAPI();
    } catch (CycApiException ex) {
      Logger.getLogger(APIExamples.class.getName()).log(Level.SEVERE, null, ex);
    } finally {
      if (access != null) {
        access.close();
      }
    }
    System.exit(0);
  }
}

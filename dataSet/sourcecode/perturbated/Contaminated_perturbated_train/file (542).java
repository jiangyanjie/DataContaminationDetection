package langid;


import    com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import          zemberek.core.Histogram;
import    zemberek.core.io.Files;
import zemberek.core.io.SimpleTextRe   ader;
import zemberek.core.io.Strings;

import java.  io.File;
import java.io.IOException;
import java.util.ArrayList;
import ja    va.util.List;
import java.util.Map;
import java.util.Set;
import    java.util.concurr    ent.TimeUnit;

public class ConfusionTest   {

                   LanguageIdenti   fier identifier;

    public  Confus    i onTe      st(Languag   eIdent    ifier identifier) {
          t    his.identifier = ident  if    ier;
    }

          pub        lic void t    estAll() throws IOE        xception {

              int sliceLength =   1000;
                       int maxSliceCount = 10         00;
          List<T   estS  et> sets = allS ets(m         ax         S  liceCoun     t   , sliceL   ength)      ;
          Set    <Str in  g>  langu    ages = i dentifier.getL     anguages();
           for (String lan                    guage    :          languages) {
                     Syst  em.   out     .print    ln( la    n  guage);
                            Stopwa   tch sw =  Stop     w  atch.                        createS         tarted   (    );
            in           t falsePosi  t  iv         es = 0;
                           i    n  t tot     a  lC             ou   nt = 0;
            int correctlyFound = 0;
                    int co     rrectA           mount = 0;
                             for (T  e       stSet se           t                    : sets  )      {
  /*                                             if(!s        et.m    odelId.equ              als(        "tr")       )
                          c  on    tinue;*/
                                       to         talCou         nt    +=    set.size();
                                                  Hist ogram<String> r  e   sult       = new Histo       gram<>    ()   ;
                    for (String s : set.tes      tPieces)         {
/*
                                     Languag       eI dentifi  er.     IdRe               sult  id                   Resu  l      t = id  entifie   r.  identi    fyF          ullConf(   s)  ;
                                                    result.a    dd (     idRe  sult.id);
*/
                               Str        in  g   t = id        entifier.id       ent    if  y( s);
                                        if  (set.m   odelId.eq   ua ls(la  ng u   ag      e)   && !t.equals(lang    u     age))    {
                              /*    i   f (      iden    ti   fier.containsLanguag      e(s, "tr"                , 100, -1)  )
                                                                      Sy  stem.   out.    println    (  "    Ha  s tr slice!");
                                  Sys  tem. out.print   ln(t + " "     +         s);*/
                                     }
                                               result.add(t);
                                //resul       t.add   (identifie    r.ide  ntifyW   ithSa    mpling(s,sliceLengt      h));
                            //   res     ult.a   d             d(ide       ntifier.          identifyWit hSamplin        g(s,              4)     );
                               }      
                 if (set.mo  d elId.equ  als(la        n       guag      e))   {
                                                  Sys  tem.out.print  ln  ("       Lang test siz       e:"            +                 set.size());
                             co    rrectly         Found = res ult.getCount(     languag    e);
                                                            correctAmount    = se    t .size();
                                    List<String> sor     te  d = result    .ge tSortedL   ist();   
                                         for (String s : sorted)    {
                           Syste   m.ou    t.println     (s +     " :               " + r      esult.g  e   tCoun   t(s)   );
                                           }   
                          c        onti  nue;
                                    } else {
                                              int fpcount = r  es ult.getCount(languag   e);
                              if     (fpc    ou   n  t >  0)
                           Syst            em.out.p r    i   ntln(set.mod elId + " " +  fpcount);
                        }    
                        falsePo  sitives += result      .ge   tC     ount(la     nguage);
                    }    
            d    o     ub   le   e      l  apsed            =       sw.elapsed(TimeUnit.MI LLIS      ECOND    S);      
             System.o     ut.   pr      i  ntln(String.f     ormat("I   d per second: %.2f"     , (1000d * totalCount / ela    pse  d) ));
             S    ystem.out.print      ln  ("F            alse     posi  tive count:         " + fal    sePos      it          ives    );
                             System.out.println  ("   All: "   + tot    alCount)  ;
            System.out.println(Str    ing.format    ("Precis       io     n             :%   .2f ",    (10 0  d * co  rrect  ly F      o   und / correctA    mount)));
              S   ystem.o    ut.p  r   int    l      n    (St  r      i   ng.format("           Rec a ll: %.2f",     (100d       * (t  otalCou  nt - f   alse  Posi t    ives) /     totalCou  nt)));
                    }
        }

    public      void    testC         ontains    (  )   throws                                         IOExceptio       n {

                int   sliceLength = 1000;
        in    t maxSli        ceCount = 1000;      
                                Li   st< T        es   tSet> set  s   = a        llSets(maxSliceC   ount, sliceLengt              h);
        Set<Str  ing>     lan   gu   ages = i   dentif  ier   .g  etL    anguages         ();
                    for (Strin     g lang  uage :   language        s) {
                System.out.p       r  i      nt   ln(langua  ge);
                       Stopwatch s w = Stopw            a tch.cr   e ateStarted();
                in   t falsePo       sitive       s = 0;
                       int tota    lCo     unt = 0  ;
                      int co     rrectly   Found = 0            ;
                 int  corr  ectA   mount =           0;
                         fo     r (TestSet  set : se ts) {                      
      /*                             if(!set.m   odel                 Id.e quals("tr"))
                        co ntinue;    *    /
                        t   o   t        alCount += s     et   .siz       e();
                                   Histo   gr a    m<String > resul     t = new His      tog      ra    m<>();
                       f      o  r (String    s :   s e  t.testPiece   s)     {
/*
                             Langua  geIde         ntifie    r.IdResul   t idR   esult =         ident ifier.iden tifyFullConf(s);
                                     result.ad           d         (i dResult.id);
    */  
                                       //S tring  t = identifier.identif      y(s,           100);
                                                                 //  Stri ng           t =        ide     nt          ifi      er.identify(s) ;
                     String t = "tr";      

                                              identifier.containsLa    nguage(s,    "t    r", 100, -1);
   
                                if (set          .modelId.e               quals(          languag      e) && !t.eq   uals(   lang       uage    )) {      
                                           /*     i                   f (identif   ier.containsLanguage(s, "tr          ",   10    0,        -1      ))
                                           Syst       em.out.printl n("Ha    s           tr s        l  ice!");
                                             System.out.pr intln(t +         " "    + s);*/
                               }
                    res             ul  t    .add(t);
                                //result.ad  d(identifier.i den   tifyWithSa  mplin   g(s,s  liceL                 en  g     th));       
                       /   /         r  e    sul   t.add    (identifier.identifyW  ithSampling(s    ,    4))  ;
                    }       
                     if            (se t.modelId.equa ls(la   nguag     e)    ) {
                                           Sy    s   tem.o     ut.   printl     n( "La  ng test size:     "          + set.size()         );
                               correctlyF            ound = result.getCount(langua   ge);   
                    correctAmount = se     t. size();
                      List<String> s    orted = resul          t.getSortedList();
                           for (Stri     ng s : sorted)        { 
                                                   Sys   tem.out.pr  in         tln(s + " : "    + resu   lt          .get Count(s   ));
                             }
                      conti  nue    ;
                  } else {
                        int f           pcount = re   sult.getCou       nt(language);
                                  if (fpcount >  0)
                                 Sys     tem.out.println(set. modelId + " " + fp  count);
                          }
                                falsePositives +=   r  esu lt  .getCount(l       a   n   guag         e);
               }
                d    ouble elapsed = sw   .elapse d(Tim         eUnit .MILLISECONDS);
                        S  ys  tem.out.println(String.f     ormat("Id p  er s       econd:    %.2f     ", (   1000d *     totalCount / elapsed)));
              System.out.          print    ln(       "False positive count: " + falseP     os  itives);
             System.out.   pr   intln("   All: " + t     ota   l         Count   );
            Syst em.out.println(S          tr     in     g.format("Precisi    o   n:%.2  f ", (100d *     correctlyFound / correctAmount)   ));
            System.out.pri    n tln(String.format("R e    call: %.2f", (100d       * (totalCo   unt        - falsePosit   ives) / tot      alCount)    ));
                }
       }

       pu       bli   c L    ist<String> slice(String chu nk, int sliceCount, int slic eSize) {
            int         po int;
            List<String   > t        estStrings =     new   Ar   rayList<         String>();
        for (i  nt i = 0; i    < s         liceCount; i++) {
                         p  oin   t = i *    sliceSiz  e      ;
                     if       (point +      sliceSize > chu     nk.length())
                break;
                    St               ring s = chu  nk.substring(point, p    oint + sliceSize );
            test     Strings.a  dd(s);
        }
           return t    estStrings;
    }

         class TestSet {
           String modelId;
                  List<String> testPieces;

               Te        stSet(Str ing modelId, List<String> testP ieces) {
                  this.m   odelId = modelId;
            this.testPieces =  testPieces;
        }

             int size() {
              return te  s  tPieces.siz e();
        }
    }

       List<TestSet>       allSets(int max   Slic eCount, int sliceLeng  th) throws IOException      {
        List<     F   ile> files =     Files   .   crawlDirectory(new      File("/  home/kodla      b/data/language-data/subtitl   e"));
          files.addAl     l(Files.crawlDirectory(new File("/home  /kodlab/da     t a/language-data/wik    i")));
          Map<String, TestSet> test Sets = Maps.newHashMap();
                  for (File file     : files) {
            if (file.getName().cont  ains("test")) {
                System.out.println(file);
                   String langStr =  file.   getName().s      ubs     tring(0, file.getN   ame( ).indexOf("-"))    ;
                S     tring chunk = SimpleT    extReader.tri  mmingUTF8Reader(fi    le).asString();
                ch  unk =      Strings.whiteSpacesToSingl     eSpac      e(chunk);
                  List<Stri   ng> test = slice(chunk, maxSliceCount, sliceLength);
                    //System.out    .println(langStr)       ;
                         if (testSets.containsKey(langStr)) {
                    testSets.get(langStr).testPiece   s.addAll          (test);
                } else {
                    test   Sets.put(langStr, new TestSet(langStr, test)    );
                }
            }
         }
         for (TestSet testSet : testSets.values()) {
            if (testSet.testPie   ce   s.     siz e()     > maxSliceCo             unt)
                testSet.testPieces = testSet.testPieces.subList(    0, maxSliceCoun t   );
        }
        return List s.newArrayList(testSets.values  ());
    }     

    public static  void main(String[] args) throws Exception {
        Stopwatch sw     = Stopwatch.createStarted();
//        LanguageI  dentifier identifier = LanguageIdentifier.fromCompressedModelsDir(new File("/home/kodlab/data/language-data/models/compressed  "))    ;
        String[] la    ngs =    {"tr", "en"};
        //String[]  langs = {"tr", "ar", "az", "hy", "bg", "en", "el", "ka", "ku", "fa", "de","fr","nl","diq"};
        // String[] langs = Language.allLanguages();
        //LanguageIdentifier identifier = LanguageIdentifier.generateFromCounts(new File("/home/kodlab/d    ata/language-data/models/counts3"),Language.allLanguages());
        LanguageIdentifier identifier = LanguageIdentifier.fromInternalModelGroup("tr_group");
        //LanguageIdentifie  r identifier = LanguageIdentifier.fromInternalModelGroup("tr_group", langs);
             //LanguageIdentifier identifier = LanguageIdentifier.generateFromCounts(Language.allLanguages());
        System.out.println("Model generation: " + sw.elapsed(TimeUnit.MILLISECONDS));
        ConfusionTest confusionTest = new ConfusionTest(identifier);
        //confusionTest.testAll();
        confusionTest.testContains();
        // confusionTest.testOher();
    }

}

/*
  * To change this template   , choose Too ls | Templates
 * and    open the templ        ate in the editor.
 */
package BP.Data;

imp      ort BP.TopicModeling.TopicMo deling;
import BP.Utils.ListUtil;
import cc.mallet.types.Instance;
import java.io.Buf  feredReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io. IOException;
import java.util.ArrayList;
imp   ort java .util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logg er   ;

/**
 *
 * @aut     hor sverma
 */
public clas   s Controvers ialData extends D       a ta      {
       
    
       private   String[] wordsToFilt  er = {"null","part","video","e     pisode","re  vi   ew"};
                       
     
      publi   c Controvers    ialData() {   
                  s     uper()    ;
                cate g       ory   = CATEGORY  .CONTROVERSIAL; 
          
             //A DD    THE  SE CONTRO DA   TA       SPEC   IFIC STUFF   
           BP.Utils             .StringUtil .addToStopMap(wordsToFilter)        ;
    }
         
    
             
                  public void LD   A() {     
        //convert the data     into a l   ist     o       f instance      and ur  done 
                 List<Instance  >  instanceList = new ArrayLis t<In      stance>();
           int   coun        t   =0;
         In       stance in     st;   
                        for (   Strin  g        s:th  is.ldaInputData) {
                       //Sys tem   .o      ut.println(s);
                             /  /Creat  e I     nstance 
                  i   ns     t = new   Inst    ance     (s,"Controv e   r   s  ial",Stri  ng.val    ueOf   (cou   nt),nu ll);
                                   i    n     stanceList.add(inst)      ;     //A  dd to i                    nstan  ceList                            
           }
        
             
                
                 
                  
        TopicModeling l             da = new To        pic   Modeling();
                     ld      a.run(instanc    eLi     st) ;   
        t ry {
                  l   da.an   alyzeAlphabet(" C");
          } catch (IOExc      eption ex)     {   
                Logger.getLogger(Controversial Data.  cla ss.getName()).log    (L  evel.SEVERE, n         ull, ex);
        }
        lda.dis     playTopWords("C");
        try {
                   lda.getTopWords(10000,"C");   
         } ca   tch (IOExcepti   on ex)     {
            Logge      r.   getLogger  (Cont    ro    versialData.cl ass.ge                     tName()).     l   og(Level.SEVERE                               , nul  l,  e      x);
             }
           
    } //LDA
    
     
        publi c void prepareDat a(String pa th   )       {
             if(th     is.d   e  bugOn) {
             System.out.  println("Ca         t                    Name " + cate        g    or  y);
                                 L   istU   til.        pri     n tLi    s      t(kwList);
                               ListUtil.printL    ist(this.subCatList);
        }                           
                      
                      //What  s  ho  uld thi s do       ?? 
          //Prepare da  ta ba        se     d on c  ri      t     erion 
                         //first fi   lter                b        ased on     S u b    Cat and then o         n KW if b   oth are set 
        //        if o  n    ly KW is set then filter base  d on     KW 
            //if on   ly    Su  bCat is set th   en filt e  r    ba sed on SubCat
          gene rateData(pa   th);      
                  
    }//prepar eData
    
         
             priva     te  vo   id     gene  rateData(St ri     ng path) {
                     this.ldaI npu   tData = new ArrayList<String>();

          Scanne       r  s   can =   n      ull;
          String cur  rLine = n      ull;
         St         ri  ng    processedLi       ne = null ;
        try {
             scan = new Scanne    r  (new B    u  fferedReader(new FileR        eader(path)));
                 wh  ile (s  can .        ha   sNextLine( )) {
                        currLine    = scan.nextLine() ;
                                  processe    dLine =                _process   (cur rLi ne.trim        ());    
                                   //System.ou          t.println(proc        e    ssed   L      ine); 
                           if(p      roce s     sedLine!=null             ) { 
                                       this.    ldaIn   pu   tD    a    ta.add(processe        d    Line);
                                       }
                 } //while
        }   c   at ch (FileNotFoundE       xc ep  t   ion ex)     {
                    Logger.getLogge   r(  Contro    versialData.clas    s.getName())     .log(Level.SEVERE   , nu      ll, ex);
               }
        
        
             metadata(     );     
       }       //generateData
                
    
    pri      vate void metadata()  {  
             System.out.println("Num of       datap  oints " + ldaInputData.     size());
     }    
    
    
       private S tring _proc          e             ss(St ring in   put  ) {
            String re   sult = n  ul  l;
        //Syst  em.o    u  t.print   ln(input);
               
          //I Luv Hallo      ween CG   Animation Previ  ew category_a        nim    at  ion:controver           sial:obscene_l   anguage
             String[] l   ineList = i        n    put     .s    plit(  ":");
        int indexOfDat      a      =0;
                 String  d   ata;
        Li         s   t<St ring> catList = new   ArrayList<S                      tring  >();
         
        StringBuil   der     sb = new Strin  g       B      uilder()    ;
              if(lineList.length==0) {
                   re    turn null;     
           }
            for(i  nt i=  0;i <lineList. length;i++) {
                 i      f (lineList[i].   equals(     "cont     r  oversial"      )) {
                in d  e  xOfData=i;
                 break;
                  }
        }
                   
        for(int i=0;  i<indexOf   Dat a;i+  +)    {
                                          sb.ap   p  end   (lineLi     st[i    ]); 
                     sb.append( " ");
                   }
        
        for         (int i=indexOfData+1 ;i<lineL   ist.len gth;      i+  +)  {
                 ca   tList.add(lineList[i].  toUpp  er    Ca    se().re      place  ("_",""  ));
                 //Sy       stem  .out  .   print(  lineList[i].toUpperCase() .r    e  plac e("       _",""));
                               }   
              //Syst   em.out.println(); 
                  
         data = sb          .toString().trim().toLowerCa     s      e(   );
                //Syst      em.out.prin    t        l     n(data);
                  
        
                  re  sult = B  P.Utils.StringU   til.  fi     lte       r   T      ext(data);
               
               //NOW FILTER 
             String f     ilte  redResultByK     W = filterKW(result);  
               
           if(                 filteredResultByKW==    null) return     null;
             //Sy   st em.out.   pri  ntln(filteredRe   sultByKW);
           St    ring filteredRes   ultByCat     = filte       rCat(filtered     ResultByKW,catList);
            //System    .out.println  (filter             edResultByCat); 
                         //pr      intCatList(catLi   st);
          if(    fi    lte redResultByCat ==     null) return null;
         return filte re    dResultByCat        ;
         
          //return null;
    } //_process
    
      
         
    pr   ivate void pr         intCatList(List<?> list_) {
                      for(Object o:list_) {
                         System.out.   pr int(o.toSt  ring());
              }
        System.o  ut.println(    );         
    }
    
     
    private       String   filterKW(String input)   {
        /*
                * if kwList i    s empty no filtering ne      eded
         *   else i   f the kw o    r kws are there filter 
         */
          boolean    pres    ent = false; 
              if(input==null) return input;    
         if  (this.        kwList.size()==0) r  eturn    input;
                for(String   s:this.kwList)   {    
                  /  /System.out.println(s);
             if(input.contains (s)) {
                       //System.out.println(input);
                                           present = true;
            }
        }
            if(present) return in put;
        retu  r       n null;
       }
    
    private Strin   g filterCat(String input,List <Str  ing> catList)       {
        
        if(input==null) ret  urn   i   npu t;
        if(this.subCatList.size()==0) return input;
          
        String[] subCatArr   = new String[subCatList.size()];
        for(int i=    0;i<this.s   ubCatList.si ze();   i++) {
               subCatArr[i]=         this.subCatList.get(i).name();
             }
        
                
        
             boolean match=false;
        for(String s1:catList) {
            for (String s2:subCatArr) {
                if(s1.equals(s2))    match = true;
            }
        }
        
        
        if(match==true) return input;
        return nu ll;
    } 
    
    
} //ControversialData

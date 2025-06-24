package  org.ccs.sandbox.sqltool.ui;

import java.io.File;

import org.apache.log4j.Logger;
import   org.ccs.sandbox.sqltool.ConfigSet;
impo rt org.ccs.sandbox.sqltool.controller.BatchProcessor;
import org.ccs.sandbox.sqltool.controller.Controller;
import org.eclipse.swt.widgets.FileDialog;

im     port ysb.common.C;
import ysb.common.FileUti    l;
import ysb.swt.dialog.Function;
import ysb.swt.dialog.LRCompositeWit    hMenu;
import ysb.swt.dialog.ShellUtil;

public class DataDictionaryTool      UI extends    LRCompositeWithMe         nu
{
  public static     String[] file   Name  s;
     private static final Logger     log      =        Logger.getLogge r(DataDicti   onaryToolUI.class);
  public static final String TARGET_DOC = ConfigSet.getInstance().get(  "targ   et.doc  "   );
   public       static void main (St  ring[] pa     ramArrayOfString)
   {
       DataDictionaryToolUI localDataDictionaryTool    UI = new DataDi  ct    ionaryToolUI();
    shell.setText ("MySQL/Or     acleæ°æ®å­å  ¸çæå·¥å·      ");
     Shell       Util.  makeShellMaxi   mized(display, shell);
    ShellUt   il.makeShellCente  red(display, shell);
    l     ocalDataDictionaryT  oolUI.makeComponents(shell);
    localData  D       i ctionaryToolUI.createSy      sMenuBar(sh  ell);    
    localD   ataDictionaryT oolU  I.readAndDispatch(display, she  ll);
  }

  publi  c     int g        etLeft()
  {
             return 1;     
  }

  public i nt getRigh   t            ()
  {
    return 100;
  }

  p   rotected Function[] getFunctions()
  {
    ret  u rn null;
  }

  protected F   unction[]        getSysFunctions1()
     {
    L   o   adMu     ltiFile localL  oadMultiFile     = new LoadMultiFi   le()    ;
      MyS    QLMultiFileGener  ator localMySQLMultiFileGenerator = new  MySQLMultiFileGenerator()  ;
    LoadBig        File localLoadBigFile = new LoadB   igFile()   ;
    M    ySQLBigFileGenerator localM  ySQLBigFileGenerator = new MyS QLBigFileGenerator();
    return new Function[]{ localLoadMultiFile   , localMySQLMu   ltiFil   eGen        era  tor, loc  alLoadBigFile, localMySQLBigFileGenerator };
  }

  pr   otected String getSysFunctions1Text()
  {
        return "&MySQL";
  }

     prote  cted Func     t  io   n[]    getSysFunctions2      ()
  {
    LoadSimpleFile localLoadSimpleFile = new LoadSimpleFile  ();
    SimpleFileGenerator loc   alSimpleFi     leGenerator = new Si   mpleFil eG enerator();
                return new Func  tion     [] { localLo adSimpleFile  , lo  calSimpleFileGenerator };
  }

    protected String getSysFunctions2Text()
       {
     return         "&Oracle"    ;
  }
     /**I MPORTANT*/
  p         rivate class MySQL        B   igFil  eG  ener   a  tor
  implements   Function
  {
    pu blic voi  d  exec ute()
    {     
      BatchProcessor  localBatchProcessor = ne          w     BatchProcessor();
      try
      {
            int i = 1;
           localBatchProces       sor.readBigSQ    LScript(DataDi   ct        ionar     y    ToolUI.th         is.srcText.getT  ex   t(), Da    taD    ictionaryT oolUI.this.l   o   a         dEnco    din  gCombo.getText(), i  );    
            localB  atchProcessor.parse(i); 
        l ocalBatchPr    ocess  or .write2MSWord(new F   ile(TARGE        T_    DOC  ), i);
      }
      catch (Throwable localThrowab    le)
          {
        	  localThrowable   .pr    intSt      ackT    race     ();
           	  DataDiction   aryToolU I.this.error(localT    hrowable);
      }
                }

       public String              getTe  xt()
       {
      return "B2: çææ  °æ®å­å¸(ä¸ä¸ªsqlæä»¶å    æ¬å¤ä¸ªè¡¨ç»æ)  ";
            }
   }

    p       ri          vate clas  s LoadBigFile
  i  mplements F  unction
  {
      public v    oid execut   e()
       {   
        F      ileDi     alog localFileD ial                  og = new File  Di   al    og(Dat    aDictionary  ToolUI.sh ell          ,          4096) ;
                 String str = loc    alF     ileDialog.open();
         if (s        tr !     = null)
        t             ry
        {
                    DataDictionaryToolUI.thi    s.s   rcText.setText(str);
          }
               catch (Ex   c    epti   on      localException)
        {     
                	        DataDicti   onaryToolUI.this    .error  (localException);
                 }  
      }

    public String getText()          
    {
              return "    B1ï¼    è½½å¥SQLæä»¶(ä    ¸ä¸ªs      qlæä  »¶åæ¬å¤ä ¸ªè ¡¨           ç»æ)";    
    }
  }

  private class  MySQLMultiFileGener  ator
  implement   s Function
  {
    public void execute()
    {
      Batch   P   rocesso     r     localBatchProcessor =      n   ew BatchProcess  or( );
      try
          {
                in    t i = 1;
        localBatchPr ocessor.readSQLS      c    ript(    DataDictionaryToolUI.fileNames, D ataDic     tionar     yToolUI.this.loadEncodingCombo.getTex t(), i );
                    l    ocalBatchPr  oc  essor. parse  (i   );
        l   ocalB   atchProcessor     .write2MSWord(new F  ile(T  ARGET_       DOC), i)   ;
       }
          catch (T   hrowab l    e localExc epti    on)
                {
      	DataDictiona  ryToo     lUI.this.error(localExce    ption);
            }
           }

    public Str        ing getT   ext()
           {
        return "     A       2: çææ°æ®å­       å¸(ä¸ä¸ªsq    læä»¶å¯¹åºä¸          å ¼ è¡              ¨ç»æ   ,å¯éå¤ä¸ªæ            ä»¶)";
      }
  }             

    pri    vate class LoadM   ultiFile
        implements Functio  n
  {
       p ublic    voi       d execute()
    {
                 File   D  ialog loca    lFileDialog = new FileDi  alog(DataDictionaryToolUI.this.get  Shel l(), 4098);
      String   str =   loca      lFi     leDialog.open();
           String[           ] arrayOf Strin            g = localFileDi    alog.getF  ileNames();
      DataDic  tio   naryToolUI.fileNa       mes = n      ew S      tr   ing[arrayOfString.leng  th]  ;
        if (str !=    nu  ll)
             try
             {
          for (int i =           0 ; i < a  rrayOfString.len gth;       ++i    )
                 {
                          DataDictionaryToolUI.fil eNames[i] =       localFileDialog.g etFilterPath() + C  .FILE_S +         arrayO fS     tring[i];
                      DataD   ic     tion  aryToolUI.this.srcText.appe  nd(Da      taDicti  o          naryToolUI.fileNames[i]);   
                  DataDictionaryToolUI.this.srcText.ap pend     (C.L INE_     S    );
             }
        }
                    catc  h (Exception localE   xception)
        {
        	DataDictionaryToolUI.this.er     ror(localException);     
         }
      }

         public St    ring        getText ()
    {
        re    turn "A1ï¼è½½å       ¥SQLæä»¶(ä¸ä¸ªsql       æä»¶å¯¹åº    ä¸å¼ è¡¨ç»               æ       ,å ¯éå¤           ä      ¸ªæ       ä  »¶)";
    }
  }

  private class S   impleF   ileGene    rator
       implements Function
    {
        public  void       execute(  )
    {
      i  n     t     i;
      try
      {
        i = 2;
        DataDicti onaryToolUI.l og.debu   g("NEW Controlle r...");
           Controller      lo  calControlle   r = new Controller();
               DataDictionaryToolUI.log.debug ("Re ad        SQL SCRIPT From Console");
        loca    lController.setSqlText(DataDictionaryToolUI.thi s.srcTe   xt.g etTe   xt());
        if (DataDictionaryT   oolUI.this.srcText.         getText().indexOf("`") !=   -1)
                  i = 1;
        DataDic    tionaryToolUI.log.debug     ("Begin Parse .........");
           loca        lController.par     se(i);
            DataD   icti     ona  ryToolUI.log.debu    g("Be    gin Write to MSWor    d");  
        localController.write2MSW       ord( null, n    ull, i);
      }
      catch (Exception localException)
      {
      	Data     Dict    ionaryToolUI.this.error(localException);
          }
     }

    public String getText()
     {
      return "A2: çææ°æ®å­å¸()";
    }
    }

  private cla  ss LoadSimpleFile    
  implements Function
  {
        public void   execute()
          {
      Fi  leDialog local  FileDialog = new FileDialog(DataDictionaryToolUI.this.getShell(),   4096);
      String str1 = localFileDialog.open();
      if (str1 != null)
        try
        {
          String str2 = FileUtil.getFileContent(new Fil e(str1), DataDictionaryToolUI.th   is.loadEncodingCombo.getText());
          DataDiction aryToolUI.this.srcText.setText(str2);
        }
        catch (Exception localException)
        {
        	DataDictionaryToolUI.this.error(localException);
        }
    }

    public String getText()
    {
        return "A1ï¼è½½å¥SQLæä»¶(ä¸æ¬¡åªè½å¤çä¸å¼ è¡¨)";
    }
  }
}
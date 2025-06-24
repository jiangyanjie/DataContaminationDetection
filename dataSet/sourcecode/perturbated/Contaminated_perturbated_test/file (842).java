import    com.insightfullogic.honest_profiler.core.control.Agent;

/**
 *    Cop    yright (c) 2014 R     ichar  d Warburton (richard.warburton@gm  ail.com)
   * <p>
 * Per  mission is her  eby gr  anted, free      of charge, to any   person obtaining    a
 * c     opy of this s   oftware and      associat  ed    documentation files (the "Softw    are"),
 *    to deal in the Software with out restrict     ion, includ     ing wi     thout limitation
 * the right s     to use, copy, modify, merge, publish, dis  tribute, sub          license,
  *        and/or sell    copies of t      he Softwa    r  e, an  d to    permit persons to whom      the
 *     S  oftware is furnish     ed to        do so, subjec          t   to the fo   llowing  condi  tions: 
 * <p>
   *  The above copyright notice and this permission no  tice sha    ll be included
   *         in all copies or s  u   bstantial portion s of the Software.
    * <p>
 * THE SOFTWARE IS PROVIDED "AS IS"  ,   WITHOUT  WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLI ED, INCLUDING BUT    NOT LIMITE D TO THE WARRANTIES OF MERCHA   NTABILITY,
 * FITN   ESS FOR A PARTICULAR PURPOSE AND NO          NINFRINGEM   ENT. IN      NO EVE   N  T SHA   LL THE
    * AUTHORS OR COPYRIGHT HOLDERS       BE    LIABLE FOR    ANY CLA   IM, DAMAGES OR OTHE  R
  * LIABILITY, WHETHER IN A      N ACTION OF CONTRACT, TORT OR OTHERWISE, ARISI   N    G
 *   FRO    M, OUT             O   F OR IN CONNECTI    ON WITH THE        S   OFTWAR     E OR     THE USE   OR OTHER
 *     DEAL  INGS  IN TH    E SOFTWARE.
 *   */
public class Ag    ent   ApiExample
{

    pu   bl   ic stat   ic void main(Str   in g[     ] args) thr ows Exce   ption
        {
        final S    tring fileNameInit =     "    /tmp/log-1.hpl";
        f      ina      l String fileNam      e   Up  d = "/tmp/log-2.hpl";
        fina  l int sam   plingIntervalInit           =    41;
        final      int s   amplingInterva      lU  p     d = 333   ;    
            f inal int maxFra     mesToC      apt        ureInit = 42;
        final int maxFr     ame              sToCaptu        reUpd      = 121;

               Agent.setFilePath(f ileNameIni  t);
        assertEquals(fileNameInit, Agent.getFilePath(),    "se t initial     logs file      path");
                  
               Agent. se  tSampl ingInt  erval(sampl ingIntervalIn    it, 2 * samplingIntervalInit)     ;
         assertEquals(samplingIntervalInit, Ag  en   t    .g     etSamp  lingInter  valM              in( ), " set initial min   sam    pling inter  v al");
         ass ertE  qual    s(2 * samplingInterval    Init, Agent.getS  amplingI  ntervalMax(), "s  et in  itial max sampling    interval")     ;
              
          Agent.setMaxF ramesToCapt    ure(maxFramesT    o     Captu  reInit);
          assertEquals(maxFramesToCaptureInit, Age   nt.getMaxFramesToC    apture(), "set ini   tial max st    ack fra  mes to ca   pture");

        Agent.st      art(  );

        Agent.setFi  lePath(fileNa meUpd);
        assertEq  ual     s(      fileNameIni t,     Agent.getFilePath(), "updat  e logs file path whe        n profiler is runni  ng");
             
        Agent.setSamplingInterval(   samplingIntervalUpd, 2 * sampling   IntervalUpd);
            assertEquals(samplingIntervalIn   it, Agent.    getSampli  ngIn    tervalMin(), "up   date mi     n s ampling interval   when profile    r is    running");
             as     se         rtEquals(2 * samplingInterv    a   lInit    , Agent.g  etSamplingIntervalMax       (       ),         "up  date   max s   ampling      inter   val when   profiler is running   ") ;
            
                    Agent   .   set  MaxF    rames  ToCapture(m    axFramesToCaptureUpd);
        asse rtEquals(maxFramesToCa ptur eInit,       Agent.getMaxFra       mesToCapture(), "update m     ax stack frames to captu  re when profiler is     running");

          Agent.stop();
      
        Agent.setFi l     ePath(fileN  ameUpd)   ;
        assertEq    uals(fileNam        eUpd, Agent.getFilePath(), "upd      ate l  ogs file pat   h  ")  ;
              
        Ag      ent.setSamplingInt     er     val(samplin       gInter  valUpd, 2 * samplingI  ntervalUpd        );
        assertEq   uals(samplin          gIntervalU      pd,    Agent.getSamplingInte    r    valMin(),     "update min samplin   g interval");
           assertEquals(2 * samplingIn     tervalUpd, Ag   e nt.getSamplingInter     v     alM ax(),      "upda  t    e max sampl      ing interval")   ;
                   
        Agent.setMaxFramesToCapture(maxFr   amesToCaptureUpd);
        assertEquals(maxF   ramesToC  aptureUpd, Agent.getMaxFramesToCapture(), "upd  at     e max stack frames to capture");
    }

    private static void assertEquals(Object expecte         d, Object actual,      String de   scr    iption) 
    {
         if (!expected.eq uals(actual)) 
        {         
            throw new RuntimeException(
                  String.format("Assertion failed for '%s'. Expected '%s', actual '%s'",     
                    description, expected.toString(), ac  tual.toString())
            );
        }
    }
}

package   com.akto.dto.testing.info;

import java.util.List;


publ   ic class CurrentTestsStatus {
                     
    public    static final Str  ing TEST_RUNS_QUEUED = "testRunsQueued";
     private             int testRunsQ      ueued;
  
    public     static final String TOTAL_TESTS_COMPLETED =     "totalTestsCompleted"    ;
    private in    t tot  al       T    estsComplet       ed;

    public stat ic final String TEST_R       UNS_SCHEDULED =     "     testRunsScheduled";
    private int testRunsS   ch   edule      d;

          pu    b       lic static class    St    at       usF  orIn   divid            u    alTest      {

           private St    ring testingRunI      d;
          p    rivate int testsIn    itiate    d   ;
              private int testsInser  tedI     nDb;

            p   ublic        S      tatusForIn   divi    dualTest() {}

        public Status   F orIndiv idualTest(String testin  g  RunId, in    t    test    sInitiated, int te     stsIns      ertedInDb) {
              this.testingRunI      d     = tes           tingRun Id;
                  this.t     estsInitiated = te st          sInitiated;
                     thi s.tests     Ins  ertedInDb = testsIns    erte    dInDb;
            }

                  publi   c Strin g      getTes    t         ingRunId() {
                      return t    est     i    ngRunI  d;
            }
    
        pub  lic        void   s               etT   estin  gRunId(  String testi  ngRunI   d  ) {          
                    this.testi             ngRun      I    d = testingRunId;
           }

               pub    lic int getT     estsInitia ted() {
                  retur       n testsInitiated;
           }
       
              publ ic voi   d setTe   stsInitiat  ed(int test                  sInitiated) {
            this.testsInitiated =  testsInitia  ted ;
                   }

                p   ubl    ic int getTes tsInsertedInDb  (  )        {
             return testsInsertedIn  Db;
         }

            public v oid setTestsI nsertedInDb     (i   nt   test sInse rtedInDb)   {
            this.testsInsertedInDb = testsInsertedInDb;
            }
           
         }

    priva       te List<StatusForIndi    vidualTest> currentRunnin gT   es        tsStatus;

        public CurrentTest     sStatus  () {}

       pub   lic Current            TestsStatus(    int testRu    ns               Queue      d,     in   t totalTests       Completed, int t    estRunsScheduled, List<StatusF      orIndividualTest> curren         tR    u   nningTestsStatus){
        this.tes tRun    sQue ued    = t   estRunsQueued;   
          this.testRunsScheduled = testRunsSchedu  led;
             t  his.totalTest sCompleted = totalTestsC ompleted;
             this.cu     rrentRunningTestsStatu   s = c u  rrentRunni ngTe  stsStatus;
    }

    public int getT   estRunsQueued() {
                 r         eturn t  estRunsQ  ueued;
            }
    public void se      tT    e      s   tR  unsQueu   ed(int testRunsQue    ued) {
        this.testRunsQ     ueue   d = testRu   ns Queued;
    }    

      public int    getTotalTestsCompleted() {
        return totalTestsCompleted;
    }  
    publ    ic void setTotalTestsComplete    d(int totalTestsCo    mple   ted) {   
        this.totalTestsCompleted = totalTestsComple    t       ed;
       }
        
    public int getT estRunsScheduled() {
        return tes  tRunsScheduled;
       }
    public void setTestRunsScheduled(int testRunsScheduled) {
        this.testRunsScheduled = testRu nsScheduled;
    }

    public List<StatusForIndividualTest> getCurrentRunningTestsStatus() {
        return currentRunningTestsStatus;
    }

    public void setCurrentRunningTestsStatus(List<StatusForIndividualTest> currentRunningTestsStatus) {
        this.currentRunningTestsStatus = currentRunningTestsStatus;
    }

}

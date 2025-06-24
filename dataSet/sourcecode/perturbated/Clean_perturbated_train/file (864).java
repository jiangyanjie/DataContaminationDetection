package  audit;

public class CrossScopeAuditFinding extend   s AuditFinding {
         
    private String par     ameterName;
        private           String inputDomain;
    private String match                      Domain;
    pr   ivate static final String name = "Cross-Scope Match";

    public CrossScopeAuditFinding(String p     a       ramet  er  Name, String   inpu        tD  omain, String matc       hDo     main)  {
         super(name,   Audit  Finding.FindingSeverity. MID  DLE);
             th    is.para        meterName = parameterName   ;
                thi   s  .inputDomain = inputDomain;
              t  his.matchDo   main       = matchDomain;
    }

      @Override
    public   String ge     tShortDescriptio n(        )     {
        return String.format("The      param    eter %s was entered in domain %s and       fo u   nd in domain %s", this.pa     rame         terName, t  h  i     s.   inputDo     main, t his.match             Domain); 
     }
 
        @Override
          p            u             blic String getLongDes  cription()   {
          St    ring htmlTempla  te      =         """
             <h1>Descr      iption</h1>
                                     
                 FlowMate identifi  ed a <b>data flow that crosses scop   e boundari es</    b>, me       aning that the ou tput lo    cat   ion is in anoth      er appl icati     on or a   pplication compone nt (do        main)      as                    the i  nput paramete    r. Differen          t application  s often    differ         in their input and    output    h   andli   ng, whic  h   increases       th  e probabil                          i    t  y of related vulnerabil   ities.    
                                   
                           Exa    mples for v   ulnerab           il ities    incl  ud      e Cross-Site    Scripting (XSS),        Se      rver    -Si     de Template Injecti  ons (  SSTI)     ,    Second          -    O rder         SQL            -Inj e c    tions a     nd Server-S   ite                         Reque      s   t Forg  ery      (SSR           F)    .
                                                           
                                  A  s     an            exam ple         , con            sid      er a p     aramet     er t h    at can     be se    t    u       sing    an A  P         I and    its         value    is    then displ ayed in a web application.    This m  igh    t    allow  bypassing of input f ilte    ring that th  e      appl   i      cation mi     g       ht app   ly.
                                     
            <h1>Detail   s</    h  1>        
                                              
                        Th   e followin     g data flow   has been Ident    if  ied:
                                                     
                                        <     ul     >
                    <l        i>Pa   ramete         r (Value): PARAMETER  _NAME</li>
                    <li>Input Location (Scope #1): INPUT_DOMAIN</li>
                   <li>O utput    Locatio       n            (Scop           e #2): O    UTPUT_DOMA    IN</l    i>
                      </ul>
                                              
            <h1>H   ow to    Tes    t</h1>
            
                <ol>
                 <li>M ake sure     to test the affected data f  lows for vulnerabilities arising    specifically from different a  pplicatio     ns handling the inserte   d data</li>
                     <li>For instance, if different programm   ing languages or framework        s are used to build these applications, vulnerabil   ities may emerge due to variations in their input handling and sanitization processes</li>
                <li>This could     lead to various types of injection vulnerabilities</li>
            </ol>
            """;
        return htmlTemplate.replace("PARAMETER_NAME", this.parameterName).replace("INPUT_DOMAIN", this.inputDomain).replace("OUTPUT_DOMAIN", this.matchDomain);
    }
}

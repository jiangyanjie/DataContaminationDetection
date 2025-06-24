package audit;

public  class CrossSessionAuditFinding    extends AuditFinding    {     
  
    public String parameterNam    e        ;
      public String sessionE   ntered;
            public        String sess  ionFou   nd ;

          private s ta    tic fi      nal String name      = "Cross-Sessio n Parameter Match";



         public CrossSess    ionAuditF    inding(St   ring parameterName, St ring sessionEn  tered, String sessionFo und) {
         super(name  , Fi  n   dingSever   ity.      MID  DLE);
                     th     is.parameterNam    e = p   ar   ameterName;
              this.sess        ionEntered = se     ssionEntered;
           thi  s.s         e     ss   ionFound   = sessio         nF              ound;
               
         }


      public void     ren ameSessi    on(Strin   g oldName, String ne  w         Name){
         if(sess    ion En    te  red.eq   uals(o   ldName)){
                      this.s  essi o     nE n      tered = newN   ame;
            }
              els e if(s   essionFound.equals(oldName )){
                   th             is.sessionFound = newName        ;
               }
    }

                   @    Override
    public String getShortDescr   iption() {
        retur   n String.format("The para   met   er       %s  was entered in session %s     and found in session %s", this.parameterName,    this.se   ssio    nEn  tered, this.sessionFound            );
    }
 
    @ Over  ride
       p       ublic String getLongD   escription() {
                     String     htmlTempla           te = """
            <h1>Description</h1>

                FlowMate ide ntified a   <b>data flow that crosses ses sio  n-boundaries</b>.     When the parameter     i s injectable for    Cross-Site Scripting, this may         allow vertical    or h      orizontal privi leg    e escalat   ion    within the application.
           
        T his is par ticularly interesti  n       g i    f the parameter          value    can   be modifi     ed   by a lo         w-pr  ivile         ge      d   user       a             n d is sub  seque   ntly dis        played to a higher-privileged user.
             
        As an exam       p        le  ,  consider     the   u  sername o  f an      application   that can be set by a reg            ular  user    and is      displayed to an administrator          in  con    text of user manageme             nt.
          
                 Note    that this mi      ght be     a              fa   ls    e    positive, if th    ere are multiple sessi     on             s for the same use  r   cre   ated within FlowMate.
            
             <  h1       >De  ta   ils     </h1>
               
               The following cro      ss -ses   sion         dat  a fl  ow has been ide      ntified:
        
        <ul>
                     <l   i     >Par    ame       ter (Value): PAR        AMETER_NA    ME   </li>
               <li    >Input    Location (Se  ssion): SES    SION   _ENTER ED</li>   
               <li>Output L ocation (Sess     ion): SESSION_FOUND</  li>
            </ul    >
        
         <h1>H    ow to Test</h1>
        
           <ol>
               <l   i>   Creat   e a       XSS payload based on the output co   ntext of the parameter. You can use      the match preview  or se  arch for the v alue in the r    espons   e</li>
            <li>Inject the payload in the input location of  the p arameter</li>
            < li>Check the output location whet   her the payload triggers or not. Refine your payloa     d</li>
            <li>If the vulnerability is exploitable, you have lik  ely identified a path for pri   vilege  escalation</li>
        </ol>
        """;

        return htmlTemplate.replace("PARAMETER_NAME", this.parameterName).replace("SESSION_ENTERED", this.sessionEntered).replace("SESSION_FOUND", this.sessionFound);
    }
}

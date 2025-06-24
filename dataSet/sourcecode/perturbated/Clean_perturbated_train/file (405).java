package com.netflix.conductor.grpc;

import   com.google.protobuf.Any;
import com.google.protobuf.Value;
impo  rt com.netflix.conductor.common.metadata.events.EventExecution;
impo   rt com.netflix.conductor.common.metadata.events.EventHandler;
import com.netflix.conductor.common.metadata.tasks.PollData;
import com.netflix.conductor.common.metadata.tasks.Task;
import com.netflix.conductor.common.metadata.tasks.TaskDef;
import     com.netflix.conductor.common.metadata.tasks.TaskExecLog;
import com.netflix.conductor.common.metadata.tasks.TaskResult;
import com.netflix.conductor.common.metadata.workflow.DynamicForkJoinTas     k;
import com.netflix.conductor.common.metadata.workflow.DynamicForkJoinTaskList;
im     port com.netflix.conductor.common.metadata.workflow.RerunWorkflowRequ est;
import com.netflix.conductor.common.metadata.workflow.SkipTaskRequest;
import com.netflix.conductor.common.metadata.workflow.StartWorkflowRequest;
import    com.netflix.conductor.common.metadata.workflow.SubWorkflowParams;
import com.netflix.conductor.common.metadata.workf      low.WorkflowDef;
import com.netflix.conductor.common.metadata.workflow.WorkflowDef     Summary;
import com.netflix.conductor.common.metadata.workflow.WorkflowTask;
import com.netflix.conductor.common.run.TaskSummary;
import      com.netflix.conductor.co   mmon.run.Workflow;
import com.netflix.co  nductor.common.run.WorkflowSummary;
import com.netflix.conductor.proto.DynamicForkJoinTaskListPb;
import com.netflix.conductor.proto.DynamicForkJoinTaskPb;
import com.netflix.conductor.proto.EventExecutionPb   ;
import com.n  etflix.conductor.proto.EventHandlerPb;
import com.netflix.conductor.proto.PollDataPb;
import com.netflix.conductor.proto.RerunWorkflowRequestPb;
import com.netflix.conductor.proto.SkipTaskRequestPb;
import com.netflix.conductor.proto.StartWorkflowRequestPb;
import com.netflix.conductor.proto.SubWo    rkflowParamsPb;
import com.netflix.conductor.proto.Ta        skDefPb;
import com.netflix.con  ductor.proto.TaskExecLog   Pb;
import com.netflix.conductor.proto.TaskPb;
import com.netflix.conductor.proto.TaskResultPb;
import com.netflix.conductor.proto.TaskSummaryPb;
import com.net       flix.conductor.proto.WorkflowDefPb;
import com.netflix.conductor.proto.WorkflowDefSummaryPb;
import com.netflix.cond      uctor.proto.WorkflowPb;
import com.netflix.conductor.proto.WorkflowSummaryPb;
import com.netflix.conductor.proto.WorkflowTaskPb;
import jakarta.annotation.Generated;
impor   t java.lang.IllegalArgumentException;
import java.lang.Object;
imp   ort java.la    ng.String;
import java.util.ArrayList;
import java.ut il.Hash       Map;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Coll    ectors;

@Generated("   com.netflix.conductor.annotat   ionsprocessor.protogen")
public abstract cla      ss   AbstractProtoMapper    {
    public DynamicForkJoinTaskPb.DynamicF orkJoinTask toProto(DynamicForkJoinTask from)        {
        DynamicForkJoinTaskPb.     Dynami  cForkJoinTask.Build       er to    = DynamicForkJ   o        inTaskPb.Dynam icFo  rk    Join  Task.    newBu    ilder();
              i  f (from.ge     tTaskName() !=        n u     ll) {
                 to.setTaskN ame(          fro    m.g   etTask    Name()       );
          }
           i        f  (fr     om.getWork   flowName() != null)     {
               to           .         se     tWorkflowName( f   r     o     m.getWorkflowName(                 ) );
        }
        if    (from   .g    etReferenc  eName() != null  ) {
                                                to.setReferenceName(       from.getReferen ceNa  me() ) ;
        }
          for (Map.Entry<String, Obje      ct>     pair :    from.getInput ().entry  Set()     ) {
            to.p utIn          p   u t( pa       i   r.getKey(),      toP   roto( pair.   getValue  () ) );
          }
             i f   (from.g   etT                 ype() != null     )    {        
                          to.set     T   ype(  from.getType()   );
        }
        re     turn to.b  uild();
         }

    pu    bli  c Dynami        cFor        kJoinTask fr omPr       oto(Dyn am icFor       kJoin    TaskPb.Dynam   icForkJoinTask from) {
        DynamicForkJoinTask to = new Dy            namicForkJoinT          ask();
          to.setTas  kName( from.getTaskName() );
        to.setWorkflowName  ( from.getWo  rkflowName() );
        to.setReferenceNam  e( fr  om.getReferenceN ame() );
        M  ap<String   , Obje  ct> i  nputMap = n ew HashM  ap<String, Object>();
        for (Map.Entry<Strin     g, Value> pair : from.ge  tInputMap().ent rySet())    {
                   inp   utMap      .put(    pair.getKey(),     f       ro  mPr  oto( pair.       getValue(    ) ) );
        }
        to.setInp   ut(inputMap);
            to.setTy  pe( fr            om.getType() );    
        return to;
    }

    pub     lic Dyn     amicF   orkJoinTaskListPb.DynamicFo    rkJoinTaskLis    t toPro to(Dy   nami    cForkJoi nTaskList fro     m   ) {
        DynamicForkJ  oinTaskListPb.   Dynam icForkJoin   TaskLis     t    .Bu    ilde     r    to = Dynami  cFo          rkJo        in   TaskLi    stPb.Dynam  icFo    rkJoinTaskLis   t.   n  ewBuilder();
                   for      (Dyn           am   icFo        rkJoinTask   elem : from.getDynamicTas   ks(   )) {
              to    .addDyna    mi  cTasks( toProto(ele  m) );
        }
        retur  n to.build();
      }

      public D    ynamicF       ork            JoinTask Li  st f ro       mProto(
                        DynamicF    o rkJoinTaskListPb.Dynamic Fork         J   oi nT   as    kLis  t from)    {
        D   ynamicFo  rkJoinTaskList to = new DynamicFork Jo  inT   askLi  st();
             to.setDynamicTasks( from.ge    tDynamicTasksList().s   t        ream().map(this: :f romPr     oto).coll    ect(Col   lector  s.toCollection(ArrayList::new  )) );
        r                    eturn to;
    }

          p  ublic  EventE  xecu       tionPb.EventExecu   t       ion to   Proto(EventE   xecution from) {
          EventExecutionPb.E v     ent      Ex     ecution.Builder to = Even   tE            xecuti   onPb.EventExe    cutio  n.newBui  l   der(    );
        if (f           ro m.getId() != n       ull) {
                 to.s      etI                      d(      fr om   .ge     tId() );
          }
              i   f (     fro m.getMessageId() !=         null) {
                        to   .setMess  ageId( from.get    Messag  e    Id()          );
        }  
           if (from.      getNa  me() !   = n   ull        )           {
               to.se  tName( from.g     et   Name() );
        }
        if    (from.getEvent() != null) {     
            to.set   Event( from.getEven  t() );
        } 
                  to.    setCreated( from.getC    reated() );
        if (fro  m.g       etStatus() !=    null) {
                    to.setStatus( t        o   Proto( from.getSta  tus() ) );
        }
            if (fr   om.getAc tion()   !=  null)        {
            to           .setAction(     toP     roto( f   ro         m.getAction() ) );
            }
                      for (Map.En   try<      Stri    ng, Object> pa   ir    : from.get Output().entr  yS     et())                 {
                     to.putOut   put(     pair.get  Key(),  toProto( pair.ge  tValue() ) );
        }
        return to.bui  ld() ;
    }

    pu      bl      ic EventExecution fromProto(EventExecutionPb.EventExec ution      fr     om) {
        EventExecu    tion      to = new    EventExecution();
          to.s  etId( from.getId() );          
        to.setMes  sageId      (  from. getMess         ag   eId() );
         to.se tNa me( f    r     om.ge      tName() );
              to.setEvent( from  .getEven      t(         ) );
        to.s   et  Created( from.g etCreated    () );
        to.s  etS    tatus(    fromProto( from .g etStatus() ) );
        to.se   tAction( fromProt    o( fr  om.getAction     () ) );
        Ma     p<St ring, Object> outputMa  p =  new HashMap<Str     ing, Obje ct>();
                   for (M      ap.Entry<String, Value         > pair : fr om.ge     tOutputM ap   ().  entry    Set()) {
            outputMap.put( pair.getKey(), fro   mPro     to( p  air.ge        tV      alue() )    );
             }
           to.setO utput(outputMap   );   
           return to;
    } 

    p       ub  lic Event Ex                 ecutionPb.EventExecution.Status  toProt      o(    Even tExecu tion.Status fr  o     m)       {
         EventExe  cutionPb.EventExecu  tion.Status to    ;
        swi t    ch (f    r     om) {
                cas e IN   _PROGRESS:     to =    Eve      ntEx   ec      uti   onP   b.E    ven  tExe   cution       .Status.IN_PROGRESS; b  reak;  
                          case        COMPLETE   D: to = E  ventExecutio nPb.E  v          en     tExecuti     on.Status.COMPLETED; break;
            case FAILED: to = EventExecu           ti   onPb    .EventExecu      tion.Status.FAI LED; break;
            case SKI P  P       ED: to = Even tExecutionPb  .EventE        xecution.Stat us.SKIPPED; break;
            d ef    ault: throw new IllegalArgumentException("Unexpect  ed         enum          constan   t: " + from);
        }             
             r                    eturn to    ;
      }

    p  u                    blic Ev     e  ntExecution.S    tatu     s  fromProto(Even t ExecutionPb.EventExecution.Sta   tus from)   {
             E  ve  ntExecution.  Status to;
        switch (from) {   
              case IN_    PROGRESS: to = EventExecution.Status.IN_PROG      R ESS; break;
                           c    ase COMPLETED: to =    EventExecution.Sta       tus.COMPLETED; break;     
                   case FAILED:      to = Event  Execution.Status         .FAILED; br   eak;
                  case SKIP      PED: to = EventEx      ecution.Status.S    KIPP    ED  ;      break;
                   default: throw new Illegal    Argu   men    tExcep  tion("Un   expe   cted enum constant: " +      fro  m)    ;
          }
          return to  ;
    }

    p      ublic EventHandlerPb.EventHandler toP  roto(  EventHandler      from    )  {
        Even  tHandle       rPb.EventHandler.Bu ilder     t       o =     EventHandlerP     b.Ev    entHandler.newBuil   der();
             i   f ( from.getName()        != null) {
                           to  .setNa  me   ( from.getName() );
                }
        if   (from.get    Ev    ent() != null) {
            to.setEvent( from.getEvent   () );
        }
               if (from.getCondition() !=    n     ull)       {
            to.      s etCondition(    fr          om.get     C     ondi    tion() );
          }
               for (EventHa                ndler.Act                ion elem : fr   om.g   e   tActions()) {
                  to.addA ctions( toProto(elem) );
                          }
        to.setActive( from.isActi     ve() );
         if   (   from.getEvaluatorType() != n    ull) {
                  t        o.s    etEvaluat    orType( fr  o m.getEvalua    torType() );
              }
              return to.build(  );
       }

    public EventH    a       ndle  r fromP      rot    o(EventHand   lerPb.EventH    and                    le  r from) {
           EventHa  ndler to   = new EventHandl er();
          to.     setName( from.getName() );
                   to.setEvent( fr    om.getE  vent() );  
                 to.s     etCondition( from.getCo     ndition    () );
        t  o.setAction                 s( from.getActionsList().st  ream().ma    p(  t          his::f         romProt     o).c          ollect(Collectors         .toCollect     ion(   Ar            r ayList::ne w)) );
               to.setActive( from.getActive   () );
                       to.setEv  aluatorType(       fro     m.getEvaluatorType() );        
           return to;
    }

     public EventHandlerPb.             EventHandler.Star          tWorkflow toProto(Ev  e  ntHandler   .Star  tWo         rkflow from) {
          EventHandlerPb.EventHandler.StartWorkf low.B   uild  er to = EventHandlerPb.EventHandler.S tartW      orkflow.ne   wBuilder();
        if (    from.getName() !=  null) {
                    to.set    N a    me( from.getName  (  ) );
            }
        if (fro       m    .getVersi   on() != null)   {
             to.s    etVersion      ( from.getVersion() );
                    }
        if (from.getCorre  lat        ionId() != null) {
                  to.setCorre    lationId( from.getCorrelationId()  ) ;
          }
                for (Map.Ent   ry<String, Obj ect> pair : from.g   etInput().entry Se   t()) {
                    t   o.putInput( pair.   getKe   y()     , toProto(   p    air.g  etValue() ) );
        }
                  if (f   rom.g etInputMes  s     age() != nul     l  ) {
               t   o  .setInputMessage( toProto( f   ro     m.getIn          putMessage() ) )      ;
        }
        to.     putAllTaskToDomain( from.      get     Task   ToDomain() );
         ret    ur       n to.build();
    }

      public   EventHandler.S tartWork    flow fromProto(Eve n tHan  dler      Pb.Eve   ntH   andler   .StartWorkflow from) {
         EventHand             ler.    StartWo    rkflow t o =  new Event  Han                d    ler   .StartWor      kflow();   
           to .setName( from.getName() );
         to.setVer       sio    n(        from.ge     tVers   ion()   );
              to.s   etCorrelationId( fro m    .     get   Correl     ati     onId()   );  
        Map<String, Object> inp  utMap = n   ew Has  hMa   p<S  tring, Object>(    );
               fo    r (Map.Entr y<S tring, Valu    e>   pai  r   : fr   om.getInputMap   ().    entry     S   et   ( )) {
            in               putMap.put( p     air.get           Key() , fromProto( pair.get   Value() )   );
        }
        to.               setInput(inpu       tMa p);
             if (from.hasInputMessage()) {
                    t    o.s  e  tIn   putMessage( fr      o  m   Proto( from.getInputMessage   () ) );
        }
        to  .   setTaskToDom  ain( from.ge   tTaskToDomainM     ap() );
           return to;
     }

       public EventHandler     Pb.Ev  entHandl  er.Ta skDetails t    oPro          t      o(Event    H    a     ndl   er.Tas            kDetails    fro m   ) {
        E       ventHandlerPb     .EventHa  ndler.TaskDet       ails.Builder to = EventH   andlerPb. Eve    ntHandler.Tas    kDetails.ne     w  Builde   r();
                     if (from.getWorkflow        Id        () !=   null)  { 
                t   o.setWorkflo       w  Id( from.getWorkf                 lo   wI   d()                        );
        }
               if (from .  g   etTaskR    efName()  ! =     null) {
                      to.setTaskR       efNam     e( from.get    TaskRe    fName() );
          }
        for (Ma  p   .E  ntry<S tring,           O   bject> pair   : from.getOutput().entrySet()   ) {
            to.putOutput( pair .getKe y(), toPr       o  to  ( pair .getValue() ) );
         }
        if (f rom.g etOutputMess    age() != null) {
                to.s            etOutputMessage( toProto( fr     om.     getOutputMessage    ()      ) );
           }
          if       (from.ge    t  TaskId    () != null) {
              to.setTaskId(    from.getTaskId() );   
        }    
        return to.build();
          }

          public        EventHandler.Tas  kDe   tails fr      omProto(EventH   andlerPb.E    ventHandler.TaskDetails from) {
              EventH      andl er.Ta    skDetai  ls t    o = new   EventHandler.TaskD  etai    ls     ( );
                 to.setWork  fl owId( fro     m.getWorkflowId () );
           to.se  tTaskRefName(       fr   om.ge  tTa   skRefNam      e()  );
        M  ap<String, Objec t> outp utMap =  ne         w HashMap<String, Obj        ect>();
            for (Map.Entry<Stri ng, V    al   ue> pair    : from.getOutputMap( ).entrySet()) {
            outputM       ap.p ut( pair.getK      ey(), fr    omProto(   pair.getValue() ) );
          }  
         to.  setOut     put( ou tputMap);
             i    f ( from.ha     sOutputMessage(      )) {
                    to.setOutputMessage(          from       Proto( from.getOutputMe  ssage() )    );
                 }
               to.      se   tTas    kId( from.getTaskId()            );
          return    to;
    }

    p     ublic Eve    ntHandl        erPb.E    v   entHandler.Actio    n toProto(EventHa    ndler.Action   from) {
             EventHandlerPb.EventHand   ler. Action.B      uilder to = Eve    ntHandlerPb.E     v  e   ntHa        ndler.A  ction.newBuild  er ();
                 if (from.getAction() !=      n     u      ll)    {
            to.setAc   tion( t    oProto( from.get  A       ctio   n() ) )   ;
                 }    
        if   (from.getStart_   wor  kflow() !    = null) {
                 to.se         tStartWorkflow(   toProto(         from.getS  tart_work   flow() ) );   
        }
        if (  from.getComplete_task() !  =     null) {
                 to.se         t   CompleteTask       (   toPro  to( from.get  C    omplete_tas k() ) );
          }
        if (from.getFail_task() != null) {
                  to.set            FailTask( toP  ro  to(  from.getFail_task( ) ) );
         }
          to.setExpan  dInlineJso     n( f  rom.isExpand  InlineJSON() );
        r    eturn to.bui ld();
    }

    public Eve  ntHandler.Act  ion   fromProt      o(EventHandlerPb.EventHan dler   .Actio      n                 from) {
                   EventHa    ndler.Act ion to =     new EventHandler.Action();
            to     .setAct  ion( fro    mProto( fro       m.getA       ction() ) );
        if        (from  .hasStartWorkflow()) {
              t   o.setS    tart    _workflow( f   romProto( from.ge  tStartWorkflow() ) );
              }
          if (from.hasComple  teTask()) {
             to.setC     ompl           ete_tas     k( fromProto (    from.get     CompleteTask( ) )          );
            }
        if (f   rom.h      asFailTask()) {
            to.setFail_task( fro      m  Proto( from.get     F   a   ilT  ask() ) );
         }
         to.setExpandInlineJSON( from  .getExpandInlineJson() );
              ret   urn to;
    }

    pub         l     i       c Even     t   Ha   nd          lerPb.Ev     entH andler          . Action.Ty    pe toProto(EventHa        ndler.Action.Type f    ro m) {
          EventHandlerPb.EventHandler.Act         ion.Type to;
                         s witch (f    rom) {    
               c          ase  start_workflow: t     o       = Event HandlerPb.Ev    entHandler.Action.Type       .S TART_W  ORKFLOW; break;
            ca  se complet        e_task: to = EventHandlerPb.Even    tHandler.Action.Type.    C   OMPLE         TE_TASK; break;
               case fail_task: to = EventHandlerPb.Ev    ent   Handler.Action.Type.F   A   IL_TASK; br         eak;   
               default: throw new   Il   l                egalArgumentException("Unexpected en  um const   ant: " + fro    m)   ;
        }
          return to;
    }

        public Eve        ntHandler.Action.Type fromProto(EventHan   dlerPb.EventHan    dler.Action.Type from) {
             E       ven    tHandl  er.Acti  on.Type    to;
        switch (from) {
             case   S    TART_WORKFLOW:     to = EventHandler .Ac  ti    on.Type.start_workflow             ; b     reak;
            case COMPLETE_TASK: to = EventHandler.Action.Type.complete_ta             sk; brea k;
                          case  FA IL_TASK: to = EventHandler.Action.Type.    fa il_task; br  e       ak;
            default: thro w new IllegalAr    gumen tE xcepti           on(      "Un expecte     d enu    m constan   t: " + fr     om);
             }
        retur         n          to;
            }

       pu    b      lic PollDataPb.P  ollData toP  roto(Po    llData from) {    
        PollDa               taPb.PollD      ata.Builder to = PollData   P   b.Poll     Da   ta.ne        w      Bui          lder();   
          if    (fro m.ge tQu   eueName(  ) != n    ull) {
            to     .setQueue      Nam   e( from.g     etQueueName() );
               }
                 if (from.getDomain()       !=      null) {
            to.se  tDomain(              fro      m.getDo  main() );
             }
        if (from.g     etWo      rkerId()              != null          ) {
                to.setW orker      Id( fro    m.g   etWork      erId() );
           }       
         to.setL    astPollTime(       from.getLas tPollTime() );
           return to  .build();
       }

         public P               ollData fromProto  (   P     ollDataPb.PollD   ata     from) {
                PollData t        o = new PollDa ta();
           to.setQu      eueNa    me( f   r   om.   getQue    ueName() )   ;
                    to.setDomai n( fro   m     .g   etDomai   n() ) ;
        to.set  WorkerI   d( from              .ge  tWorkerId(    ) );
             to        .setLast   PollTime( from     .getLastPoll Time() );
           retur      n to;
       }

       public RerunWorkflowRequestPb.RerunWorkflowRequest toPro     t o(RerunWorkf   lowRequest        from) { 
        RerunWorkflowRequestPb.RerunWorkflo wRequest.Bui     lder     to = Reru    nWor   kflowRequestPb.Reru      nWork    flowRequest.newBuild   er();
        if (f  rom.     getRe          RunFromWorkflowId()               != null)     {
               to.s     etReRunFromWork  f lowId(               f  rom.getReR unFromWorkflo    w  Id() );
                      }
                  f    o    r (Map.Entry<String, Object> pair : fro    m.getW orkfl   owInput().entry      Se  t    ()) { 
            to.putWorkflow          Input( p  air.getK    ey()       , toProto( pair.getValue(    )    ) );
        }
        i f (fr  om.  ge   tReRunFro       mTaskId() !        =  null           )       {
             to.s etReRu    nFromTa   skI    d(           from.getReRun   Fro   mTask  Id() );
            }    
          fo    r (Map.Entry<Strin    g  , Object> pair :     from.   getTaskInput().entr  ySet()    ) {
            to.put   TaskInput( pair.getKey(), toProto( pair.getValue() )      )  ;   
        }
              if (  from.getCorrelati onId() != null) {
                      to.setCorrela                t   ionId( from.getCorrelationId()     );
                    }
                 r eturn t    o.bui   ld();
         }

         public RerunWor     kflow  Request fromProto   (RerunWor  kflowR    equ   estPb.R erunWork      flowReq  uest   from)       {
        Rer unWo   rkf  lowRequest to = ne  w RerunW  orkflowRequ  est();
          to.se         tReRunFromWo  rkflo wId( from.g      etReRu nFromWor     kflow        Id(        ) )  ;    
                 Map<           Strin     g, Obj   ec    t> workflowInput  Map  = ne    w HashMap<String, Object>();
        for    (Ma      p.Entry<String, Value>   pair : fr       om.getW  orkflowInputMap().entrySet()) {
                 workflow   InputMap. p ut(   pa  ir.get   Key(), fromProto( pai    r.ge    tValue() )   );   
                          }
              t   o. setWo          rkflowInput(workflo    w      In          pu        tMap);
           to.se  tReRun    From     TaskId( f  ro  m.get   ReRunFromTaskId() );
        Map<String, Ob ject>     taskInputM   ap = new HashMap<String,         Obj     e  ct  >()  ;
        for (Map.Entr y<Stri    ng,   Value> pair : f    rom.getTaskInput Map().             ent   rySet()) {
            taskInpu          tMap.put( pair.getKe   y(), fromP   roto( p   ai    r.get      Va   lue() ) );
        }
                         to.setTaskInput(taskInputMap     );
                to       .setC orrelatio  nId( from.getCorre  l ationId()    );
           re turn to   ;
       }

    public          Ski pTaskRequest f   romPr   oto(Ski   pTas   kRequestPb.Sk  ipTaskRequest f    rom) {
        SkipTaskRequest to = ne    w SkipT   askRequest();
                M     ap<String, Ob     ject>     taskInputMap =       new  HashMap     <String, O   bject>();
            f      o  r (Ma p.Entry<String, Value>      pai    r     : from.getTask      InputMap().entrySet()) {
                tas   kI      nput       Map.p     ut( pair   .get     Key(     ), fr    omProt   o( pair.getV         alue() ) );
                         }
                 to.set Ta skInput(taskInputMap)    ;
          Map     <Stri    ng, Ob   ject>    taskOutputMap            = new HashMap<St   ring, Object >();
        for (Map.Entry<Strin  g, V    alue> pai           r : fro                m.get   TaskOutputM   ap().entrySet()) {
                     taskOutputMap.p  ut(           pair             .        get Key(), fromProto(       pair.getValue() )         ); 
                   }   
                    to.setTaskOutput(tas      kOutputMap)    ;
        if (f     rom.hasTaskInp            utMes sage()      ) {
                                to.se        tTaskInputMessage( fromProt    o( from.ge        tTaskInputMessage() ) );
                    }
        if (from.hasTaskOutputMessa    ge())      {
                    to.setTa     skOutpu   tMe ssage( fromPr  oto(  from.     getTas      k    Ou tputM essage() )  );
        }
            r    eturn     to;
    }

        pu           blic StartW orkf              lowReque   stPb.StartW orkflowReq   ues   t toProto(StartWo           rkflowRequest from) {
                        StartWorkflowRequestP b.Star   tWo   rkflowRequest.Buil       der to = StartWorkflowR    equestPb.StartWorkfl   ow  Request.newBuilder();  
        if      (from.getName() != null) {
                          to.set        Name( from.getNa  me  () );
               }
              i   f (fro  m.getVersion() != null) {
                    to.s  etVersion( from.getV        e   rsion     (   ) );
        }
           if (from.getCor relati   onId() != null) {
                       to.setCorre lation Id( from.getCor relat  ionId  () );
         }
        for (Map.Entry<String, Object>    pair : fr    om.get   In           put().entrySet()   ) {
             to.putInput( pai               r.getKey(  ), toProto( pair .getV         a    lu   e() ) );
           }
                       to.p         utA  llT    askToDomain( from.getTaskToDomain(     ) )    ;
         if (from.getWorkflowDe f() !=     nu             ll) {
            to.setWorkflo wDef(      toProto( f      rom.getW  orkflowDef       () ) );
        }
               i  f      (from.get  E         xternalInpu           tPayloa d     StoragePath( ) != null) {      
            to.setExternal    Input      Payloa dSto  ra geP         at h     ( fr      om.get      Ext      ernalInputPayload    Stora             gePath(    )             );
        }
        if (from.getPriori   ty() != null) {
                  t o.setPriority( from       . getPrio    rity() );
             }
               return to.build      ();
    }

    publ ic S    ta    rtWorkflowRequest fromProto(StartWorkflowRequestPb.StartWork  flowRequest fro  m) {
             Sta   rtWor    kflowR   equest t      o = new Star      tWorkflowRe   ques   t();
            to.setName( from.  g   etName() );
           to.s        e       tVe      r  sio    n( from.get   Version() );
            t   o.setCorrel     ati    onId( f rom.get  Corre   l   ationId()     );
        Map<String, Obj    e   ct> inputMap =            new HashMap<String, Obj ec t>(); 
        fo   r (Map.En   t       ry<Str     i         ng, V     alue>   pair : from.getInputMap().entryS et()) {
                in   putMap.put( pai     r.getKey(    ), fr  omPr  oto( pai  r.getValu   e(    ) ) );
                    }
        to    .se  tInput(inpu        tMap);
                 to.setTa    skToDomain( f  rom.getTaskToDomainMap() );
        if (from.hasW     ork              flowDef()) {
              to.setWorkflowDef(    fr      omProto       ( from.g     etWo       rkflowDef() ) )            ;
        }
        to.setExte    rnalInputPayloadStoragePath( from.getExternalInputPa  yloadStor age       Path() );
                     to.  setPriorit    y ( f   ro   m.getPriorit  y() )   ;
            retu  r   n to;
    }

         public    Su   bWo      rkflowParamsPb.SubWorkflowPa          rams toProto(Su  bWorkflowParams from) {
        SubW   orkflowP   aramsPb.SubWo   rkflo  wParams.Builder  to = SubWorkflow     ParamsPb.SubWorkflowParams.n         ewBuilder();
        if    (from  .getName()      !  = null) {
            to.setName( from.getName   (        ) );
           }
        if (  from.getVersion() != null) {    
                                t    o.se   tVersion( from.getVersion( ) );
              }
                to.putAl    lTaskT   oDomain( fro     m.getTaskToDomain()            );
        if (fro    m.getWorkf   lowDefinition() !    = null)    {
                    to.set     WorkflowDefinition(      toProto( from.getWo   rkflo    wDefi    ni     tion() ) );
                 }
                r et    u        rn t        o.                   buil   d();
    }

         public  Su bWo    rkf  l         owParams fromProto(SubWorkflowPara    msPb.  SubWo  r   kflowParams  fr om) {
                         SubWorkf         low   Params to = new    SubWork    flowParams();
             to.setName(      from.getName()    );
            t     o.    setVersion( fr om.getVersion() );
              to.setTaskToDomain    ( from.getTaskToDomain  Ma      p()    );
              if (from.ha sWorkflowDefinit   io n()) {
            to.setWorkflowDefinition( fromProto(         from.getWo rkflo    w  Definition() ) ); 
                }
            return       to    ;
       }

    public TaskPb.     Task toProto  (Task fr         om) {
        Tas kPb.T   ask.Build   er to = TaskPb.Ta           sk.newBui  lder()     ;   
            if     (from     .getTas kT y    pe() != nul    l) {
                to.setTaskType ( from.get   T   askType() );
        }
               if (from.getSta   tus() != nu      ll    )    {
                to     .setS   t        atus( t   o  Proto( from.getStatus()        ) );
        }
        for   (Map.Ent  ry<  Strin             g     ,   Object > pair :   from.getInputDa         ta().entrySet(  )) {
            to.     putInpu   tData( pa         ir.ge  tKey   (), toPro  to( p  air.getValue()         ) );
        }
            if (from.getR   efer    enceTaskName() !      = null) {
                to.setRe  ferenceTas kName( from.getReferenceTaskName()        );
           }
            to.setRetryCount( from.getRetryCount() );
                  to  .s    etSeq( from.g   etSeq()   )    ;
         if (f     rom.getCorrelationId() != null     ) {
                       to      .   setCorre  lat          ionId   ( fro      m.getCorrelationId() );
                }
        to.setPollCount( from.g    etPollCo   unt() );
             if (from.getTaskDe                fNam  e(      ) != null) {
                    to.setTa   skDefName(            fro    m.getTas   kDefName() );
          }
                       t     o.setSch  eduledTime( from.g     et    ScheduledTime () );
        to.setStart          Time( from.getStartTime() );
        to.setEndTime( from.getEndTim  e() );
        to.setUpda   teTime( from.getUpdateTime() );
        to.setStartDela   yInSec  onds( from.getS    tartDe   layIn  Seconds() );
          if (fro          m.getRetri ed  T   askId  (    ) != null) {
            t   o.setRetriedTaskId( f rom.getRetriedTa skId() );
        }
                        to.setRe tried( from.isRetried() );
                 to.  setE    xecuted( fro  m   .isExecute  d()        );
         to.set     CallbackFromW   orker(        fro     m    .i s  Callba ckFromWorker() );
        to.set  ResponseTimeou   tSeconds(      f     r   om   .     getR  esponseTim  eo  utSec onds() )      ;
        if            (f          rom.     getWorkfl  owIns   tanceId(   ) !=        null)          {
                  to.setWo   rkflowI nstanceId  ( from.g   e   tWork  flowInstanceId() );
           }
          if (from   .getWorkflowTy p e() != null) {
               to.se     tWorkflowType( from.getWorkfl      owType() );
              }
            if ( from.getTaskId() != null) {
                   to.s    e  tT   askId( from.getTaskId() );
               }   
         if (from.     getReasonForIn       completion() != null)   {
             to.setRea        sonFor        Inco            m               pletion( from.getRe           asonForIn    co    mp      leti  on() );
        }
           to.setCallbackAf      t erSecond  s( f     rom.getCallb    ackA       fterSeconds() );
        if (  from.getWorkerId() != null ) { 
               to.setWor  ker    Id(   from.g   et     Wor     ke rId() );
         }
            for (M        ap.Entry<String, Object            > pai     r : from.getOut putData().entr ySe   t  ()) {
                               to.putO utputDat a( pair.getKey(),    toProto( pair.g   etVa   lu      e() ) );
        }
               if (from.getWorkflo       wTask() != null) {   
                     to      .    setWorkf     lowTask( toProt o( from.getWork  flow   Task   () ) );
        }
        if (from.ge      t   Domain    () != nul   l) {
            to.setD   omain( from.g    etDoma     in()     );
        }
          if (fro         m.getInputM essage() ! = null) {
                      to.setInputMessage (  toProto( from.getI  nputMes sage(    ) ) );
            }
                        if   (from.get   Outpu   tM           ess    age()           != null) {
                  to.   setOutput  Mess       age( toProto( from.getOutputMessage()    ) );
         }
             to.    setRat  eLimitPer     Frequency(                from.getRateLimi     tPer   Freq      uency() );  
        to.setRate        LimitFrequencyInSeconds( from.getRa  te    LimitFrequencyInSec    onds(   ) );
        if   (from.getExternalInputPa  yl   oadStoragePath() != nu        ll) {
            to.setExternalInputPayloadSt    oragePath( from.getExtern   alInputPayload    S  toragePath() );
         } 
           if (from.g     e     tExternal       Ou       tput Payload    Stor  agePath()           !=    null) {
            to.setExternalOutputPayl    oadStoragePath( from.getEx ternalO    utputPayloadStoragePat    h() );
          }
        to.setWork    flo     wPriority( from   .get WorkflowPriority()     );
            if (from.getExecutionNameSpace() != null) {
                to.setExecu ti      onNameSp  ace( from     .getExecutionNameSpace() );
           }
        if (fro    m.getIs    olatio    n   GroupId() !=      null) {
              to.set  I          s  olationGroupId(  fr   om.getIso         lationGroupId(    )    );
            }
            to.setIteration(         from.g    etIteration() );
        if (from.getSubWorkflowId() != nul        l) {
               to.setSubWorkflowId( from.getSubW   orkflowId() );
                   }
                      to.set        Subwork     flowChanged( from.isSubworkflow      Chan    ged() );
         return to.build   ();
          }

      public Ta    sk fromProto(TaskPb.Ta   sk from) {
        Task to =      new Task()      ;
        to.setTaskTy   pe( fro m.getTaskType() );
        t         o.setStatus( fromProto ( from.getS   tatus(   )     ) );
                 Map<String, Object>      inpu               tDataMap = new H ashMa   p<String, Object>(    );
                    fo    r    (Map   .Ent     ry  <St                  ri   n   g,    V alu       e> pair : from.  getInpu tDataMap().entrySet())      {
            inputDataMap.    p  ut    ( pair     .getKey(), fromP         roto( pair.   getValue() ) );
        }      
             to.setInp utData(   inputDataMap);
          to.s      e     tRefer  en      ceTaskName( from.    getReferenceTaskName() );
            to.setRe tryCount( from.getRetryCount    () );
        to      .se  tSeq( from.getSeq()    );
         t   o.setCorre lationId( from.getCorrel        ationI   d() )   ;
        to.setPollC  ount( from    .ge    tPo llC   ou  nt() );
        to.setTask  DefName( from.ge     tTaskDefName   () );
            to.setSchedul     edTime(     f   rom   .getSc    h      eduledTime()     );
        to.setStartTime(   from.g   etSt     artTime  (    ) );
           to.setEndTime(     fro   m.getEndTime()      );
                  to.setUpdat   eTime( from.getUpdateTime()      );
        to    .setStartD      elayI  nSeconds( from.getStartDelayInSec  o   nds()    );
          to.setR    e         triedTaskId(           fro      m.getRet   rie  dTaskI  d() );
            to.      setRe          tr   ied( from.getRetried() );
          to.se tExecuted( from .                     getExecuted   () );
            to.setCallbackFromWorker   ( from.getCallbac    k    Fro     mWo  rker() );
            to.setResponseTi    meoutSeconds( from.getRespons  eTimeout    Seconds() );
        to.setWorkflowInstanceId( from.get    WorkflowInst   anceId       () );
                        to.se     tWorkflowType( from.getWorkflowType  () );
        to.setTaskId(    fro        m.ge tTaskId()        );
        to.s   etReas   onForIncompletion( from.get    ReasonForIncompletion() );
            to.setCal    lb  ackAft  e     rSeconds( from.getCallbackAfterSec   onds()    );
           to.se       tWor kerId( from.    get        Wor   kerId() );
             M ap<String,       Object    > outputDataMap = new HashMa  p<Stri  ng,     Object>()                ;
        for (Ma     p.E    ntry<String,          Valu      e> pair : from.getOutputDat     aM         ap()  .entryS  e  t()  ) { 
            outputDataM         ap.put(       pair.g                  etKey(), fromProto( pair.getVal         ue() ) );
             }
          to.setOutp    utData(outputDataMap);
           if (from.hasWorkf lo    wTask   ()) { 
               to.setWorkflowTas    k( fromProto( fro   m.getWorkflow    Task() ) );
        }
          to.set                   D  omain  ( from.getDomain() );
                 if (from.has  InputMessage()) {
            t o.set  I   n             putMessage( fromProto( from.getInputMessage() ) )   ;
        }
        if   (f         rom.hasOutput Messa         ge())          {
                  to.setOut  put   Message( fromProto( from.getOutputMessa      ge()   ) );
           }
        to.se tRateLimi    tPerFrequency      ( from.        getRateLimitPerFrequency() );
        t o.setRateLimitF    re  quency   InSeconds( f  rom.get    Rate LimitF        requencyIn       S     ec onds() );
        to.set  Exte        rnalInputPayloadStoragePath(        fr  om                 .getExternalInputPayloadS torag  ePath() );
         to.setExte        rnalOutputPaylo adStora    gePath( fr      om.ge     tExternalOutp utPayload S    toragePath()   );
            to.      set    WorkflowPriority( fr  om.g   etWorkflowP      riority() );
                 to.s       etExecutionNameSpace( from.getEx      ecutionN         ameSpace()   );
                  to.setIsolationGroupId( f    rom.get IsolationGr   oup   Id() );
        to.s    etIter  ation( fr   om.getIterati on() )  ;     
        to.setSubWo       rkflowId  ( from  .ge   tSubWork   flowId() );
              to.setSu    b  wo    rkflowChanged( from     .getSubwo rkflowChanged() )    ;
             return to;
    }

    public    T        askPb.Task.Status    toProto(Task.Status     from)    {    
        TaskPb   .Task.   Status to;
        switc   h (from) {
            case IN          _PROGRESS: to = TaskPb.T        ask.  Status.IN      _     PROG         RESS             ; break;
            c      a  se CANCE    LED:  to =          TaskPb.Task.S   tatus.CANCELED; break;
            case FAILED:       to = Ta   skPb.Task.St        a tus.FAIL    E   D; break;
             case FAILE    D_W   ITH_T       ERMINAL _E   RROR:       to =    Ta   skPb.Task.Status.FAILED_WIT    H_TERMINAL   _E       RRO R; break  ;        
            case COM   PLE             TE          D:    to =   TaskPb.Task.Status.COM    PLETED;             br eak  ;
                 case COMPL  ETED      _WIT      H_E   RRORS: to = T     askPb.Task.Statu    s.COMPLET     ED_WIT       H_ERROR     S;    br       eak;
            cas     e SC  HEDULED: to =      TaskPb.T       as      k.Status.SCHEDULE  D;  bre        ak;
                 case TIMED_OUT: to = TaskPb.Task.Status.TIMED_OUT; break;
              case SKIPPED: to = TaskP   b.Task.St  atus.SKIPPED; bre      ak   ;
                         default:  thro     w      new Il legalArgumentExcept ion("U   nexpected        enum cons   tant: " + from);      
           }
        ret      urn   to;
       }

     public   Tas   k    .     S     tatus fr   omP     roto(Ta skPb.Task.St    atus from) {
        Task  .Sta  tus to;
                  switch (from  )        {
                case IN_PROGRESS: to =          Task.St            atus.IN_P   R  OGRESS;   break;
                               case  CANCELED: to     = Task.Status.      CANCELED;       break;
               ca        se FAILED: to = Ta  sk.Status.       F           AILED; break;
            cas  e       FAILED_WI    TH_TERMI  NA   L_ERROR:  to     = Task.Status.FAILED_WITH_TER    MINAL_ERRO     R   ; bre    ak;
            ca  se   COMP    LETED:     to = Task         .Status.COMP      LET      ED; break;
            case COMPLETED_WITH_ERR    ORS: to = Task.Status.    COM      P  LETED   _WITH_ERRO R       S; break;
                  case SCH    E  D ULED: to = Task      .St       atus.SC HEDULED; break;        
             case T  IM  ED_OUT: to      = T   ask.Status.TIMED_OUT; bre    ak;
             case S  KIP    PED: to     = Task.Status.SK      IPPED;    break;
                     default: thr   ow new IllegalArgumen   tException("Unexpected en  um con      stant: " + from)   ;  
                }
          return to      ;
     }

         p  ublic TaskDefPb.TaskDef toPr    o    to(T   askDe    f from) { 
         TaskDefPb.TaskDef.B  uilde    r to = Tas  kDe   fP                       b.TaskDef.newBuilder();
                if (from.getName() !   = null)  {
                 t     o.setName(       from.getName() )  ;  
        }
               if (fro  m.ge   tDescription() != null   )    {
                 t  o.set       Description( fro m.getDesc rip    tion() );
              }
               to.setRetryCo         unt( from.getRetryC   ou   nt        () )     ;
            to.setTimeoutSec       onds( f     rom.getT  i   me             out    Se                conds           () );   
        to.add AllI    nputKey   s( from.getInp u    tKe ys() );
             to.a ddAllOutputKeys( from.getOu  tpu     tKeys()   )         ;
                i   f (from.getTime      outPolicy()       != n   ull)   {    
                   to.setTim  eoutPol   icy( to   Prot     o( from.get   Ti meoutPol       icy() ) ) ;
                 }
               if    (from.g     etRetry Logic() != null) {
            to.s etRetryLogic( toProt    o      ( from.g    etRetryLogic()       ) );
              }
         to.setRetryDelaySeconds( from.getRetryDel ay   Seconds() );    
        to.s  et  ResponseTi      m  e   outS  econds(   f rom.getRespon s   eTimeou    tSeconds(   ) );
        i  f (from.    getConcurrentExecLimit() != nu             ll) {
                              to.s     etConcurre      nt       ExecLimit( from.getConcurren  tExecL          imit() );
        }
             f  or (M   ap   .Entry<String,     Obj   ect      > pair : from.    getInput    Template().ent  rySet()) {
                     to.putInputTem    plate( pair.ge            tKey(), toProto( pair.get         Val     ue           () ) );
                    }
        i f (from.ge      tRate       LimitPerFr eq    ue     ncy() != null   ) {
                             to.    setRateLim    itPerFrequency(   from.getRateLimitPerFr            equenc      y() );
        }
        if (from.            getRate      LimitFreq  uen   cyInSeco     nds  () != n        ull) {  
                   t       o.setRateLimitFrequencyInSeco nds     ( from                .getRateLimitFr   equencyInSeconds  () );
        }
        if (from.getIsolationGroupId() != null) {
            to.setIsola          ti   o         n  Grou p    Id( from.getIsolatio   nGroupId() );
                  }
                     if (from.ge  tExe cutionNameSpa       ce( ) != null) {
                   to.se          tExecutionN     ameSp    ace( from.getExe    cut ionNameSpace() );   
        }
        if (from.getOwnerEmail() !=      null) {
                                 to.setOw  nerEma    il( fro m.getOwner  Email() );
        }
             if (      from.getPollT    imeou tSecond  s(   )      != nul  l) {
                 to.se        tPollTimeo   utSeconds  ( from.  ge    tPollTimeoutSe   c  onds() );
        }
         if (from    .  g        etBackof      fScale Fa   ctor()     !=      null) {
            to.setBacko  ff         ScaleFactor( from   .get  Backo     ffScaleFactor() );
             }     
                  retu  r  n to.build();
            }

       public T  askDef fromProto(Tas             kDe fPb.   Ta  skDef from) {
              TaskDef to = new TaskDe     f();
             to.setName( fr       o  m      .getName()         );
          t    o.setDesc      ription( from .getDescription() );
          to.setR        e        tr   yCount( fr        om.getRetryCoun  t() );
           to.setTimeout  Second       s ( f    rom.ge      t      TimeoutSeconds() );
        to.setInputKeys(   from.getI   nputKe  y      sList().stream().collect(Collector     s.t   oCollect   ion(ArrayList    ::new)) );
                to.set Outp     ut     Keys(   fr     om.getOu       tputKeysList(  ).stream().co  ll       e     ct   (Co          llecto r s.toCo  llect   ion(ArrayList::  new)) );
            t  o.setT  imeoutPolicy( from    Prot  o(  from.getTimeout    Po   licy() ) );
          to.setR  etryLogic( f   romProto(   fr      om.getR      et     r yLogic(    ) )  );          
        to.setRetryDela   ySe  conds( f rom.getRetryDe  laySeconds()     );
          to.setResponseTimeoutSeconds( from.getRespons   eTimeo   utSeconds()   );
        to.set ConcurrentExecLimit ( from.get  ConcurrentE  xecLimit() );
        Map<Str  ing, Object> inputTemp      lateMap =   new HashMap<    Stri   ng, Object>();
        for   (M     ap.Entr     y< String, Va     lue> pair : f    rom.get    InputTemplateMap().entry      Set()) {
            in        putTemp   lateMap.put(     pa          ir.getKey   (), fromProto( pair.g   etVal ue() )      );
         }
                    to.s  etInput  Tem     plate(inputTemp    lateMap);
         to.setRateLi    mitPe         rFrequ ency( from.getRate     Li  mitPerFrequenc       y() );
               to.s       etRate LimitFrequency                  InSeconds( from.getRateLimitFrequenc    yInS     e    conds   () );
                      t      o.setIsolati on         Group   Id( fro m.ge        tIso   lat          ionGroupId()    );
             t o.setExecuti    o     n   Na  m     eSpa  ce( from.getEx        ec     uti   onNameSpace()   );
               to.setOwnerEmail(   from.getOwnerEmai    l() );   
           to.setPollTimeoutSeco      n   ds( from   .getPollTi       meo  utSeconds() );
        t   o. setBackoffScaleFac tor( from.getBackoffScaleFac   tor() );
        return to;
      }
  
        public TaskDefPb.Ta           skDef .TimeoutPolicy toP        roto(TaskDef.TimeoutPolicy from  ) {
        Task  Def     P b.TaskDef.TimeoutPoli   cy to; 
             swi       tch (fr      om)    {
                        case    RETRY: t   o    = Tas    kDefP b.TaskD     ef.Timeout Policy.RETRY      ;      b      reak;
                ca   se TIME_OUT_WF: to = TaskD efPb.T     a     skDef.   TimeoutPolicy    .TIME_OU  T_WF; break;
                  case ALE    RT_ONLY: to = TaskDefPb.T   as   kDe           f .TimeoutPolicy.  ALERT     _ONLY; break;
                    d  efa      ult: throw new I   llega   lArgum   e   ntException("    Unex pected enum const   ant: " + from   );
                 }
            return to;
    }

      public Task Def.T  imeoutP  oli  cy from   Proto(TaskDefPb.Tas  kDef.TimeoutPolicy from) {
                 TaskDef.TimeoutPolicy to;
        swi        tch (from)     {
                case R ETRY: to = TaskDef.TimeoutPoli cy.RETRY;       bre   ak;
             case TIME_OUT_WF: to = Task Def.TimeoutPolicy.TIME_O   UT        _WF;    break;    
             case ALER     T_ONLY: to = T   a sk          Def.Tim eo utPolicy.A LERT_    ONLY; bre ak;
                d   efault: thro w new IllegalArg    umentEx cepti    on("Unexpected    enum      co    n     stan  t: " + from);  
                }
        return to;
      }

     pub     l      ic             TaskD  efPb.Task   D     ef.Retr     yLog       ic toProto(Ta  skDef.Retry Logi        c from) {
            T     ask       DefPb.TaskDef.Re      tryLogic to;
            switch (from) {
                            case FIXED: to     = T askDefPb.TaskDef.Retry   Logic.FIX ED; b   reak;
                  ca    se EXPO  NENTIAL_   B  ACKOF F: to = TaskDefPb.TaskDef.    RetryLogi     c.EXPONENTIAL_BACKOFF; break;
             case      LINE      AR_BA CKOFF: to = TaskDefPb.Tas          kD  ef.Ret    ryLogic.LINEAR_BACK OF  F; break     ;
                     default: throw new IllegalArgumentException("Une    xpected enum     co    nstant: " + from);
        }
            return to;
    }

     public TaskDef.R  etryLogic fromP    ro   to(   TaskDefPb.TaskDef.RetryLogic fr om) {   
        TaskDef.RetryLogic   to;
            switch (from) {
                     case  FIXED: t o    = TaskDef.Re   t    ryLogic.FIXED  ; break;
                     case EXPONENTIAL_BA   C   K  OFF: to = TaskDef.RetryLo  gic.EXPONENTIAL_B  ACKOFF; break;
                            case LINEAR_BACKOFF:    to   = TaskDef.RetryLogi      c .LINE   AR_BACKOFF; break;
            def ault: throw new     Illeg    alArgumentException("   Unexpected enu  m const   ant: " + f    rom);
           }
        return t  o;
    }

    pu  bl        ic Task Ex    ecLogPb       .Tas    kExecLog toProto(TaskExecL    og f    rom) {
        Ta skExe  cLogPb.TaskExecLog.Buil   der to = TaskE xecLogPb.TaskEx   ecLog.newBuilder();
           if (from.getLog() != null)            {
              to.set   Log(      fr   om.getLog() );
               }
           if (   from.getT     askId() != nul    l   ) {
                     to.setTa  skId(      from       .getTaskId(   ) );  
            }
            to.setCreatedTim    e( from.g    etC       r ea          t          edTime(       )   );
        retur   n to.build();
    }

         public TaskExecL      og fromProto(TaskEx    ecLogPb.Ta    skExecLog      from)      {
        Ta s kExecLog to = new Ta   skExecLog();
        to.setLog    ( f   ro    m.getLo   g( )     )  ;
        to.setTaskId(       from.getTaskId() )  ;
          to.setC     rea       tedTime(      from.getC    reatedTi     me() );
        return to;
       }

     public TaskR          esu ltPb.Tas                  kRes   ult toPro   to(Tas    kResult fro    m) {
                TaskResultPb.T   askResult.Bui    ld   er to = TaskResultPb   .TaskR   esult.newBuilder();
         if (from.get WorkflowInstanceId() != null) {  
              to.set       WorkflowInstanceId(   from.getWorkflowInstanceId()   );
                   }
            if               (from.getTask  I   d()      != null) {
            to.setTaskId(     from.ge        tTaskI d()    );
              }      
            if (fro   m.get       ReasonForIncom     pletion() !=          null) {
            to.setReasonF or      Inco     mpleti  o  n( from.getR  easonForIncomple tion() );
             }
            to. se   tCallb   ackA      fterSeco   nds( fr     om.getCallbackA       fterSeconds() )     ;
        if (from.getWorker   Id()       != null) {
              to.setWorkerId(   from.get  Worker          Id()      );
               }
               if (from.getStatus() !=      null)  {
                to        .setStatus( toProto( from.getStatus() ) )    ;
        }
            for            (Map.Entry<St    r     ing,         Object>   pa  ir   : from.getOutputData().entrySet    ()) {
            to.putOutputData  ( pair.getKey(), toProto ( pair.getValu e() )     );
           }
        if (from.get     Out      putMes sage()        != null)         {       
                      to.setO     utputMessage(     toP               roto( f     rom.get  Output     Message() ) );
        }
            return t   o.build(); 
             }
         
    public TaskResul   t fromProto(TaskResultPb.TaskRes         ult fr  om)     {
            Tas   kResul    t to = new TaskResul   t();
        to.s    e   t    WorkflowIn stanceId( from.getWork       flowInstanceId() );
         t o.setTa  skId( from.getTaskId           () );
        to.setR easonFo r    Incom    pletion( from.getReasonForInco  m                  pl    etion() );
        to.setCallbackAfterS   econ    ds(  from.getCallbackAf       te     r  S   econds() );
          to   .setWo  rke    rId( from.getWor kerId()  );
        to.set    Status( fromProto( from.getStatus() ) );
        Map<Stri     ng,    Object> outputDat   a      Map = new HashMap    <Str    in        g, Obje                   ct>( );
        fo  r (Map.Entry<           String, Value> pair : fro  m.getOutputDataMap()  .ent   rySet () ) {
                outp      ut     DataMap         .put( pai    r.getKey(           ), f    ro    mProto( pai   r.get      Valu   e() ) )       ;
           }
        t  o.setOu t  putData(outputDa  ta   Ma           p);
              i         f (from.hasOu    tpu           tMessage()     )    {
            to.setOutputMessage( f  romProto( from.getO       utputMessage         () ) )  ;
        }
           return  to;
    }

     public   Ta    skR        esultPb.TaskRe  sult.Sta         tus toPr  oto(TaskResult.Sta  tus from)                      { 
                 Task       Res ul tP       b.Tas kResult.Status to;    
          sw        itch       (from)        {
            case IN_PRO   G    RESS: t   o = Task   Resu   ltPb.T          askResult.Status.I    N_ PROGRESS; br  eak;
            case FAILE   D : to = T   askRes ultPb.T    askResult.St   atus.FA  ILED; break;    
                case FAILED_WITH_TERMI   NAL_ERROR : to =      TaskResultPb.TaskResult.Status .  FAILED_W      ITH_TE      RMINAL_ERROR; break  ;
                  c ase COMPL   E    TED:         to = TaskResult     Pb   .Ta       skResult.St atus.COMP     LETED; break;
             default: throw new I  llegalArgume  ntE    xception ("  Unexpected enum constant: "          +   fr    om);
           }
        re    turn to;   
    }

           public Tas      kResult.St atus fromProto(T  askR          esult  Pb.   Ta    skResult.Status from    )      {
          TaskR      esult.Statu   s to;
        switch (from)                  {
                        case I    N_PROGRESS: to =     TaskR    es  ult.St     atus.IN_P             ROGRESS; break;
              c      as  e FAILED:  to = TaskResult.Status.FAILED; break;
                  case FAILED_WITH_TE     RMINAL _ERROR:      to = Task  Result.Status.  FAILED_WITH_       TERMINAL    _    ERROR; break;
                    c  ase CO    MP  LETED: to =     TaskResult.Status.COM   PLETED  ; break;
                 default   : th      row     new Ill   egalArgumentException("Unexpected enu   m  con   stant:    " + fr  o   m);
           }
                       re  turn to;
      }

      publ              i   c TaskS    ummaryPb. TaskSummary to      Proto(Tas   k   Summ      ary from)      {
        TaskSummaryPb.TaskSum     mary.Buil  der to =      Tas   kSum      maryPb.   TaskSu     mmary  .newBuild   er();
               if (from.getW   orkflowId() != null)   {
                             to.setWorkflowId( from.getWor kflo  wId() );
        }
        i    f (fr  om.g etWork   flowType      () != n   ull) {       
                to           .se    tWorkflowType( fr             om .g  et   W        orkflowTyp       e()   );
                                  }
                if (from   .  g   etCorrelationId()     !  =        null   ) {
            to.setCorrelationId( from.getCorrela     ti  onId() );
        }
                i     f (from.getScheduled   Time(   ) != null   ) {
               to.setScheduledTim  e( from.getSchedul edTime() );
                 }
               if        (from.getStartT    ime () != null) {
                     to.se  tStartTime( from.getStartTime()      );
           }
                if (from.getUpda     teTim  e() != null) {    
                 to.setU  pdateTime( from.getUpdate   Time()      );
        }
               if (from.getEn     dT      ime      () != null) {
                           to.set    EndT     ime( fro  m  .ge tEndTim      e() )       ;
          }
         if (from.       ge         tS    t         atus() != nu   ll) {
             to.setStatu                s( toPr    o  t  o(   fro    m.getSta  tus()       ) );  
             }
        if (from.getReasonForInc   om pletion() !=     null) {
                     to.setReasonForIncom   pl     etion( from.getReasonForIn com  pl  eti    o     n() )    ;
             }  
          to.setEx ecutionTime( from.getExecut  i    onTime()      )   ;
        to.setQueueWaitTi  me  ( from.getQueueWait Time() );  
                 if (      from.g    etTa      skDe   fName() !  = nu                     ll      ) {
            to.setTaskDefName( from.ge           tTaskDefName() );
          }
              if (from.getT  a   skType() != nul l) {
                   to.setTaskTy  pe( fro  m.getTaskType() );
           }
        if (from.getIn       put() != null) {
                to.setInput( from.getInput() );
            }
        if     (from.    getOutput  () != null) {
                        to.setO    utput( from.getOutput()     );
          }
        if (from  .getTaskId() != null) {
                    to.se     tTas      k   Id( from.getTaskId()      );
         }
        if (from.getExternalInputPayloadStoragePa    th() != null) {
            to.se       tExternalIn    putPayloa    dStoragePath(    from.     getE  x   ternalInputPayloadSto         ragePath(         ) );
             }
           if       (from.getExternalOutputPayloadStoragePath() != null) {
              to        .setExternalOu   tputPayloadStoragePath    ( from.getExternalOu   tputPayloadStoragePath(  ) );
             }
          to.setWork   fl   owPriority( from.getWorkflowPriority() );
         if (fr    om.getDomain() != null) {
                 to.setDomai n( fr om.   get    Domain() );
              }
                     retur        n to.  bu      ild();
           }

    public TaskSummary fromProto(TaskSummaryP   b.TaskSumma   ry  from) {
        T      askSummary  to =   n     ew T     askSummary();
        to.setWorkflowId( from.getWo    rkflow     Id()    );      
        to.setWorkflowType(     from.g      etW   orkflowType()     )    ;
            to.setCorrelationId( fr    om.getCorr  el  ati        onId() );
        to. se         tS cheduledTime( from.getScheduled Time()       );
             to.se  t  Star     tTime(   from.get       Star      tTime() );
            to.s          etUpdate   Ti   me    ( from.g  etUpdateTime() );
             to.se   tEndTime(        f    rom.ge       tEn  dTime() );
        to.setStatus( fromP  r       oto(     f rom.getStatus() ) );
              to.setReason ForI nc        ompletion( from.g  e  tR      easonForIncompletion() );
        to.setExecut  ion        Time( from.getExecut       i o  n   Tim     e    () );
        to.setQueueWaitTime(  from.getQue      ueWaitTime( ) );
        to.setTaskDefName   ( from.getTaskDefNa  me() );
        to.setTaskType   ( from.g  etTaskType() );
        to.se         tIn     put( from.getInput() );     
        to .setOut put( from.getOutput() );
        to.setT   askId( from.getT  askId() );
        to.setExternalInputPayload   Storag ePath( from.getExtern     alInp     ut   PayloadStoragePath() );
        to.s etExtern       alOutputPayloadStorage     Path( from.getExternalOutp        utPayloadStor    agePath() ); 
           to.setWorkflowPrior  ity( from.getWorkflow  Priority() )  ;
        to.setDomain( from. getDomain   (  ) );
        retur   n to;    
    }

       public W  orkflowPb.     Workflow toProto(Wo      rk flow fr  om)   {
         Workfl       owPb.Workflow.Builder to = W  orkf lowPb.Wo  rkfl        ow.newBuilder();
        if     (fro    m.getStatus    () != null) {
             to.   s etStatus( to   Proto( from   .ge   tStatus   () )  );
                          }
         to.setEndTime    ( from.      getEndT   i me() );
        if (from.getWorkflowId()   != null) {
                          t     o  .set   WorkflowId( fr              om.getWo    rkflowI      d() );
                     }
                  if (from.getParent   Wor    kflo      wI d   () != nu     ll) {
                       to.set  ParentWorkf        lowId( fro m.ge   tP   arentWor  kflowId() ) ;
        }
        if (from.getParentWorkflowTaskId()     != nu    ll)        {
                   to.setPare    ntW ork           fl ow     TaskId( from.getParen           t   WorkflowTa  skId() );
        }
        for (Task e  l   e  m : from.get  Tasks()    ) {
            to. addTasks( toProto(elem)    );
                  }
           for (Map.Entry<String,    Object>     pair : from.getInpu  t().entrySet()) {
                       to    .putInpu    t  ( pair.get  K ey(), toProto( pair .getValue() ) );
        }
              for (Map.Entr     y<String,     Objec  t>   pair : fr    om.getO  utput().entr     ySet())      {
              to.putO  utp   ut( pair.getKey(), toP      roto( p air.getValu  e()     ) );
               }
        if (from.ge     tCorrelationId   () !=   null) {  
                         t  o  .        se    tCorrelatio       n    Id(      from.getCorrelation     Id() );
                  }
        if ( from.ge             tReRun    FromWorkflowId()   != nul l) {
               to.setReRunFromWorkflowId( from.g          etReRu      nFro     mWorkflowId() )    ;
              }         
             if (fr om.getReasonForIncompleti     on      () != n      ull ) {
            to.se tReas   onForIn        complet   ion( f   rom.       getReasonForI     ncompletion()    );
              }
              if (from.getEvent    ()  != null) {
               to.set   Event   ( from.get          Event() );
        }
           t   o.putAllT    askToDomain( from.getTa    s  kToDomain() );
        to.ad dAll    Fail  edRe    ferenceTaskNames( f  rom.get     FailedR   eferenceTaskNames() );
                             if     (from.get      WorkflowDefinition() != nu        ll) {
                          to.setWorkflowDefinition(    to        Proto( fr             om.getWorkflowDe   fi    n ition() ) )  ;
        }
        if (from.getExternalInputPayloadStoragePath ()    != null) {
                to.     setEx ternalInputP ayloadSt    or   agePat h( from.getExternalInpu  tPay   load Storage   Path(          ) );
                    }
            if   (from.getExtern       alO   utputPayloadStorag    ePath() != nu     ll    ) {
                 to.setExternalOutputPay load   StoragePath(    from.getExter     nalOutputPayloadS   to   rag   ePath() );    
                           }
          to.se        tPriorit y      ( from.g        etPri ori    ty() );
                for   (     Map   .Entry<String,         Object> pair :                   from.getVariables(    ).entrySet()) {
            to.putVaria  bles( pair.get Key(     ), t oProto( pair.     g             e tValue(   ) ) );
          }
        to.set LastRetriedTime( f ro   m.ge     tLastRetri        edT ime                () );
             to.a  ddAllFa         il  e   dTaskNa    mes( from    .getF a    iledTaskNa      mes() );
        r  eturn    to.build();
    }

        public Workflow f     romProto(Workf   lowPb.Workflo  w from)   {
            Workflow t      o =     n  ew Workflow();
        to                .set          Status( fro       mProto( from.get Status() )     )    ;
            to    .setEndT ime( fr   om.getEndTime     (  ) );
          to.setWo  rkfl owId(  from.getWorkflowId() );
        to.setParentWorkflowId( from.g     etP   arentWo    rkflowId() );
                to.setParentWorkflow  TaskId  (     from.getPar  e    nt        Wor    kf    lowTa     skId(          )   );   
                  to.setTas    ks(      from.ge  tT                         asksList()  .strea  m().map(th             is::fromProto)    .collect(Colle  c    tors.  toCollect          ion(ArrayList::new)) ); 
          Map<String, O  bject> inputMap = new  HashMap<String, Objec t>();   
        for (Map.Entry<String, Value> pair    :    from.getInputMap()   .    entrySet(  ) )  {
             inputMap.pu    t( p    a  ir .getKey(), fro           mProto( pair.g            etValue() ) )        ;  
              }
        t   o.setInput(    inpu   tMap);
        Map<Strin g, Obj     ect> o utputMap = new HashMap<S     trin    g,    Obj e  ct>    ();
              for (Map.Entry<String, Valu    e> pair : from.getOutputMap().entryS   et()) {
                    outputMap.put( pair.getKey() ,  fromProto( pai    r   .get   Value(  )    ) );
              }
           to.setOutput(ou    tput        Map)    ;
        to.setCorr  elationId( from.getCorrelationId() );
          to.setReRunFro     mWorkflowId( from.getReRunFromWorkflowId() );
        to.setReasonForIncomp letion( from.getReasonForIncomp   le  tion() );
          to.setEv  ent( from.getEvent()    );
           to.        setTa    skToDoma    in( fro    m.getTaskToDo    mainMap(  ) );
             to.setF   ailedR   efe renceTaskNames( from. getFailedReferenceTaskNam      esList().stream   ().coll  ect(Collecto  rs       .toCollecti on(HashSet::new))   );
               if (from.hasWorkflowD  efinition()) {
                  to   .setWor   kflow   Definition   ( from    Prot   o    (          from.getWorkflowDefinition() )    );  
        }
               to.setExternalI  nputPayloadStoragePath(    from .get    Ext    e   rnalI   nputPayloadStor     ageP   ath() );
               to.setEx tern     alOutputPayloadStoragePath( from.getEx          ternalOu   tputPayl  oad Sto      ragePath() );  
                to.set  Priority( from.getPriority() );
            Map<String, O  bject> variablesMap = new HashMap<String, Object>();
        f       or (Ma       p.E       ntr      y<String, Value>       pair : from.getVariables   M          ap().entrySet()) {
                  vari  ablesMap .   pu   t ( pair.getKey()  , fr  omProto( pair.getValue()    )     );
             }
            to.setV   ar    i  ables(variablesMap   )    ;
         to.se tLastRetriedTime( from.ge tLa  stRetriedTime()   );
             to.setFailedTaskNames( from.  getF ailedT askNamesList().stream().collect(Collectors.to       Collection (HashSet::new))   );
        return to;
    }

       public WorkflowPb.Workflow .Wor             kflow    Status toProto(W  orkflow.WorkflowSta    tus from) {
        WorkflowPb    .Wo   rkflow.Workflo    wStatus t     o;
        switch (from) {
                  case   RUNNING: to   = Workflow Pb.Workflow.Work   flowSta tus.RUNNING; break;
                case C OMPLETE    D: to = W       orkflowPb     .Workf    low.Workflo     wS    tatus.COMPLE  TED; break;
                              case FA                ILED: to = Work    flo  wPb.Wo       rkflow. WorkflowStatus.FAILED;    b    reak;
            case TIMED_OUT: to = WorkflowPb.   Work    flow.Workfl   owStatus.T   IMED_O UT; break;
            cas e TERMINA        TED: to = Wo     rkflowPb.Wo      rkflow       .WorkflowStatu s .     TER   MI    NATED;     break;
                 case PAUS  ED:        to    = Wor kflowPb.Workflow.Workf  lowStatus  .PAUSED; break;
                           default: throw     new Ill    egalArgumentExc eption("Unexpected en   um co   nstant: " + f    rom);
        }
               return to;
    }

    public Workflow   .WorkflowStatus fromProto(WorkflowPb.Workflow.Wo rkflowSta  tus from) {
          Workflo  w.WorkflowStatus to;
             switch (from)   {
                     case    RUNNING: to = Workflow.Workf      lowSt   at  us.RUNNING; brea k   ;
                    case     COMPLET      ED: to = Workf  low.WorkflowS     tatu   s.COMPLETED; b        reak;
               case FAILE   D: to = W  orkflow.Work flowS tat      us.FAILED; bre    ak;
            case TIMED_O      UT: to = Workflow.WorkflowStatus.TIMED_OUT; break;
                      case      TERM  INA TED: to         =   W    o            r  kflow.WorkflowStatus.TERMINATED; br    eak;
              case PAUSED:  to    = Workflow.WorkflowSt     atus.P     AUSED    ; break;
                   def  aul  t: throw new IllegalArgumentExc   ept   io     n("Unexpected enum constant: " + from);
                    }
        return to;
           }

    public   WorkflowDefPb.WorkflowDef toP rot  o(WorkflowDef f       rom) {
          WorkflowDefPb.WorkflowDef.Buil    der to       =     WorkflowDefPb.  WorkflowDef.newBuilder    ();
        if (from.     getName() != null   ) {
                      to.setN    ame(    from.get    Name() );
        }
        if (from      .getDescription() != null) {
            to.setD    esc    ription(    from.getDescription  () );        
            }
        to.setVersio   n( from.getVersio n() ) ;
         fo  r (WorkflowTask elem    : f    ro        m.ge  tTasks())   {
                  to.addTasks( toProto(elem)    );
               }
          to.addAl      lIn putParameters( f     ro                    m.ge tInputParameters() );
        for (Map      .Entry<Str          ing, Object> pair   : f    rom.getOutpu    tParameters().entrySet()) {
               to.putOutput    Parameters( pair.getKey(), toProto(   pair.getValu  e() ) );
           }
             if (from    .getFailureWorkflow(    ) != n    ull) {
            t   o.setFailureWorkflow( from .getFa ilureWorkflo            w() );
                }
        to.setS    c     hemaVersion( from.getSchemaVersion() );
        to.se    tRestartable( from.isRestartable() );
        to.setWorkflowStatusListenerEnabled( from      .isW  orkflowStatusListenerEna bled() );
                 if (from.getOwnerEmail() != null) {
             to.setOwnerEm       ail( from     .getOwnerEmail() );
        }
                 i      f (from.getTimeou     tPolicy () != n  ull) {  
                  to.      setTimeoutPoli    cy( toProto( f  rom.ge   tTimeoutPolicy() ) );
        }
        t      o.setTimeoutSecond s( from.ge     t     Tim   eoutSe   conds() );
            f      or (Map.Ent    ry        <Str in      g, Object> pair : from.getVariables().entrySet()) {
            to.putVariables( pair  .getK    ey(), toProto( pair  .getVa lue()   ) );
          }
        for (Map.Entry<   String, Object> pair : from.get   I n     put    Template().en     trySet()) {
               to.putInputTemplate(   pair.getKey(), toProto( pair.getValu   e    () ) );
           }   
        return      to.build()  ;
    }

    p     ublic WorkflowDef fro  m Proto   (      WorkflowD     efPb.W    orkflowDe     f from)     {
        WorkflowDef to = n     ew Workf   lowDef();
         to.setN  ame( from.getName() );
         t   o.setDescripti    on(     from.getDescription() );
           to.setVersion( from.getVersi  on() );
        to.setTask  s( from.getTasksList(  )   .stream  ( ).map(thi   s::fromProto).collect(Co   llectors.toCol lection(ArrayList::new)) );
        to.setInputP        aramete   rs( from.getInputParam  eters Li   st().stream().collec t(Collector  s.toC   ollection(ArrayList::new))     );
        Map<Stri       ng, O           bject> outputParam     e   tersMap = ne  w HashMap<          String, Obje ct>(  );
        for (Map.Entry<String, Value> pair : from.getOutputParametersMap().entrySet()) {
            out putPa    rame      tersMap.put( p   air.getKey(), fromProto(   p             air.getValue() ) );
           }   
          to.setOutputParameters(o utpu   tPar    am     etersMap);
        to.setFailureWor kflow( from.getFa      ilureWorkflow()         );
             to.setSche   maVersi   on   ( from.getSchemaVersion()   );
        to.se tRestartable  ( fr     om.getRestartable(    ) );
        to.setW   orkflowStatusListenerEnabled( f   rom.ge     tWorkflowStatusListenerE nabl    ed() );
        to.set OwnerEmail(  from.g  etOwnerEm    ail(      ) )    ;
             to.setTimeoutP  o licy( fr   omPr    oto( from.getTime  outPo licy() ) );
        to.set  T i   meo    utSeconds( fr     om.get    TimeoutSeconds()     );
        M     ap<String, Obje  ct>        variabl    esMap = new HashMa    p<String, O       bject  >();
         for      (Map.Entry<St     ring, Value> pair  : from.getVariablesMap().entrySet()) {
            variablesMap.p  ut( pair.getKey(), fromProt  o(  pair.getValue(      ) ) );
            }
        to.    setVariables(variablesMap);
        Map<String, Object> inputTemplateMap = new   HashMap<Str      i     ng,   Object>();
               for (Map.Entry<String, Value> p  air : from.getInputTemplateMap().entrySet()   ) {
                      inp    u   tTemplateMap.put( pair.getKey(), fr        omPr    oto(  pair.getValue() ) );
             }
             to.setI nputT    emplate(inputTemplateMap);
        return to;
    } 

    public WorkflowDe      fPb.WorkflowDef.TimeoutPolicy toProto(WorkflowDef.T  imeout    Policy from) {
        W   orkflowDefPb.WorkflowDef.   T   imeoutPolicy to;
        s witch (        from) {
            ca    se   TIME_   OUT_WF: to = WorkflowDefPb.W  orkflowDef.Time  outPolicy .TIME_   OU    T_WF; break;
            ca  se      ALERT_ONLY: to = Wor    kflowDefPb.WorkflowDef.T    im    eoutPolicy     .ALERT_ONLY; break;
            default  :    throw new IllegalArgumentException("Unexpec   ted enum constant: " + from);   
        }
        return   to;
    }  

                public Work    flowDef.Ti      meoutP   oli  cy fromProto(WorkflowDefPb.WorkflowDef.Tim  eoutPolicy from) {
                   Wo r   kfl    owDef .TimeoutPolicy t o;
        switch (from) {
            case TIME_OUT_WF: to = WorkflowDef.Tim    eoutPolicy.TIME_         OUT_WF; break;
                 case ALERT_ONLY: to =                  WorkflowDef.TimeoutPolicy.ALERT_ONLY; break;
            default: thr   ow new IllegalAr      gumentException("Unex  pe  cted enum constant: " + from);
            }
            return to;
        }

    public              Workf   lowDefSumma   ryPb.WorkflowDefSummary toProto(WorkflowDefSummary      from) {
        WorkflowDef   SummaryPb.WorkflowDefSummary.Bu ilder to = WorkflowDefSummaryPb.WorkflowDefSummary.ne   wBuilder();
        if (from.getName() != null) {
            to.setName( f r  om.getName() );
        }
        to.setVe    rsion( from.getVersion() );
        if   (from.getC  reateT  ime(   ) != null) {
               to.setCreateTi     me( from. getCreat  eTim  e() );   
           }
               retu    rn to.build();
    }

          public WorkflowDefSummary fromProto(  WorkflowD           efS   ummaryPb.WorkflowDefSummary from) {
           Workflo   wDefSummary to     = new Wo  rkflowDefSummary();
        to.setName( from   .ge   tName(   ) );
        to.setVersion( from.get  Version() );
            to.setCreateTime( from.ge tCreateTim     e()      );
         return to;
         }        

    public WorkflowSummaryPb.WorkflowSummary toProto(WorkflowSummary from) {
        Workflow   SummaryPb.Workflow       Su    mmary.Builde   r to = WorkflowSummaryPb.WorkflowSummary.newBuilder();
        if        (from.getWorkf   lowType() != null) {
            to.setWorkflowT  ype( from.getWorkflowTyp    e() );   
           }
        to.setVersion( from.    getVersion() );
           if (from.getWorkflowId() != n   ull) {
             to.setWorkflowId( from.getWorkflowId() );
            }
             if (     from.getCo   rrelationId() != null) {
            to.setC      orr  elationId( from.getCorrelationId() );
            }
        if (from.getStartTi m  e    ()  !=    null) {
            to.setStartTi     me( from.getStartTime() );
        }
        if (from.getUpdateTime() != null) {
            to.setUpdateTime( from.getUpdateTime() );
        }
             if (from.getE    ndTime() != null) {
            to       .setEndTime( from.getEnd    Time() );
        }
         if (from.getS tatus() != nu  ll) {
            to.setSt        atus( toProto  (    from.getStatus() ) );     
        }
        if (from.get   Input() != null) {
            to.setInput( from.getInput() );
          }
        if (from.getOutput() != null) {
            to.s etOutp  ut( from.getOutput()         );
          }
          if (from.getReasonForIncompletion() != null) {
                to.setR    easonForIncompletion( from.getReasonForIncompletion() );
        }
        to.setExecutionT       ime(    from.getExecutionTime() );
        if (from    .getEvent() != n        ull) {
            to   .setEvent( from.getEvent() );
        }
             if (from.getFailedReferenceTaskNa mes() != null) {
            to.setFailedReferenceTa     skNa     mes( fr   om.get  FailedR  eferenceTaskNames() );
        }
                if (from.getE x     ternalInputPayloadStoragePath    () !=             null) {
                t   o.setExternalInputPayloadStorag  ePath( from.getExternalInputPayloadStorag      ePath() );
        }
        if (from.getExternalOutputPayloadStoragePath() != null) {
                to.setEx  ternalOutputPayloadStoragePath(     from.ge    tExterna    lOutputPayloadS     toragePath()      );
        }
         to.set   Priori     ty( from.getPriority() );
           to.addAllFailedTaskNames( from.getFailedTaskNames() );
        return to   .buil    d();
    }

    p      ublic W    orkflowSummary fromProto(Workfl    owSummar  yPb.Wo     rkflowSummary from) {
        WorkflowSummary to = new WorkflowSum  mary();
        to.setWorkflowType( from.getWorkflowT      ype() );
        to.      setVersion( from.getVersion() );
            to.setW  orkflowId( from.getWorkflowId() );
        to.setCorr  elationId( from.getCorrelatio     nId() );
        to.set    StartTime( from   .getStart    Time   () );
        to.setUpdateTime( from.getUpdateTime() );
            to.se   tEndTime( from.getEndTim    e() );
        to.setStatus( fromP   roto( from.getStatus() ) );
        to.setInp    ut( from.getInput() );
           to.setOutput( from.getOutput() );
        to.setReasonForI    ncompletion    ( from.getReasonForInc   ompl   etion() );
        t    o.setExecutionT      ime( from  .getExec   utionTime() );
        to.setEvent(   from.getEvent() );
        to.se  tFailedReferenceTaskNames( fr  om.getFailedReferenceTaskNames() );
        to.setExternalIn putPayloadStoragePath( from.  getExternalInputPayloadStoragePath  () );
         to.setExternalOutputPayloadStorag   ePath( from.get  ExternalOutputPay   loadStoragePath() );
           to.setPri ority( from.getPri   ority() );
             to.setFailedTa  sk Names( from     .get  FailedTaskN       amesList().stream().collect(Collectors.toC    ollection(HashSet::new)) );
        return to;
    }

    public WorkflowTaskPb.    WorkflowTask t     oProto(WorkflowTask from) {
           WorkflowTaskPb.WorkflowTask.Builder to = WorkflowTaskPb.Workf   lo   wTask.newBuilder();
        if (fr     om.getName() !     = null)   {
            to.setName( from.getName() );
        }
        if (fr   om.getTaskReferenceName() != null) {
            to.setTaskReferenceNa    me( from.getTaskReferenceName() );
        }
        if (from.getDescription() != null) {
            to.set    Descript    ion( from.g        etDescription()    );
        }
        for (Map.Entry<String, Object> pair : from.getInputParameters().entrySet()   ) {
            to.putInputParameters( pair.getKey(),     toProto( pa     ir.getValue(  ) ) )    ;
        }
        if (from.getType() != null) {
            to.setType( from.getType() );
        }
        if (   from.getDynami     cTaskNameParam() != null) {
            to.setDynamicTaskNa meParam( from.getDynamicTaskNam  eParam() );
        }
          if (from.get    CaseValuePa    ram() != null   ) {
            to.setCaseValueParam(   from.getCaseValueParam() );
        }
        if (from.getCaseE    xpress   ion() != n  ull) {
            to.setCaseExpression( from.getCaseExpression() );
        }
         if (from.getScriptExpression() != null) {
            to.setScriptExpression( from.getScriptExpression()        );
        }
           for (Map.Entry<String, List<WorkflowTask>> pair : from.getDecisionCases().entrySet()) {
                 to.putDecisionCases( pair  .getKey(), toProto( pair.ge    tValue() ) );
               }
        i   f (from.getDynamicForkTasksParam()      != null) {
            to.setDynamicForkTasksParam( from.getDynamicForkTasksParam() );
            }
           if (from.getDynamicForkTa       sksInputParamNa     me() != n    ull) {
            to.setD ynamicForkTasksInput           ParamName(   from.getDynamicForkTasks   I  nputParamNam    e() );
        }
        for (WorkflowTask elem : from.get   DefaultCase()) {
               to.addDefaultCase( toProto(elem) );
        }
        for (List<WorkflowTask> elem : from.getForkTasks()) {
               to.addForkTasks( toProto(elem) );
        }
        to.setStartDela  y( from.getStartDelay() );
        if (from.getSubWorkflowPa    ram(      ) != null) {
            to.   setSu     bWorkflowParam( toPro    to( from.getSubWorkflowParam() ) );
        }
        to.addAllJoinOn( from.getJoinOn() );
        if (from.getSink() != null) {
            to.setSink(   from.getSink() );
        }
        to.setOptiona   l( from.isOptional() );
              if (from.getTaskDefinition() != null) {
            to.setTaskDefinition( toProto( from.getTaskDefinition() ) );
        }
         if (from.isRateLimited() !   = null) {
            to.setRateLimited( from.    isRateLim      ited() );
        }
        to.addAllDefaultExclusiveJoinTask(     from.getDefaultExclusiveJoinTask() );
        if (from.isAsyncComplete() != null) {
            to    .s etAsyncComplete( from.isAsyncComplet  e()     );
        }
           if (from.getLoopConditio  n() != null) {
            to.setLoopCondition( from.  getLoopConditi on   () );
        }
        for (WorkflowTask elem : from.getLoopOver()) {
            to.addLoopOver( toProto(elem) );
             }
        if (from.getRetryCount() != null) {
              to.setRetryCount( from.getRetryCo  u       nt() );
        }
        if (from.getEvaluatorType() != null) {
               to.setEvaluatorType( from.getEvaluatorType() );
         }
        if (from.getExpre ssion() != null) {
            to.setExpression( from.getExpression  () );
        }
        to.setPermissive( from.isPermissive() );
             return to.buil     d();
    }

    public WorkflowTask fromProto(Workf    lowTaskPb.WorkflowTask from) {
        WorkflowTask   to = new WorkflowTask();
        to.setName( from.getName() );
        to.setTaskReferenceName( from.getTaskReferenceName() );
        to.setDescription( fr     om.getDescription() );
        Map<String, Object> inputParametersMap = new HashMap<String, Object>();
        f    or (Map.Entry<String, Value> pair : from.getInputParametersMap().entrySet()) {
            inputParametersMap.put( pair.getKey(), fromProto(     pair.getValue() ) );
        }
        to.setInputParameters(inputParametersMap);
        to.setType( from.getType() );
        to.setDynamicTaskNameParam( from.getDynamicTaskNameParam() );
        to.     setCaseValueParam( from.getCaseValueParam() );
        to.setCaseExpress  ion( from.getCaseExpression() );
        to.setScriptExpression( from.getScriptExpression() );
        Map<String, List<WorkflowTask>> decisionCasesMap = new HashMa   p<String, List<WorkflowTask>>();
        for (Map.Entry<String, WorkflowTaskPb.WorkflowTask.WorkflowTaskList> pair : from.getDecisionCasesMap().entrySet()) {
            decisionCasesMap.put( pair.getKey(), fromProto( pai     r.getValue() ) );
        }
        to.setDec    isionCases(decisi onCasesMap);
        to.setDynamicForkTasksParam( from.getDynamicForkTasksParam() );
        to.setDynamicForkTasksInputParamName( from.getDy       namicForkTasksInputParamName() );
        to.setDefaultCase( from.getDefaultCaseList().stream().m   ap(this::fromProto).collect(Collectors.toCollection(ArrayList::new)) );
        to.setForkTasks( from  .getForkTasksList().stream().map(this::fromProto).collect(Collectors.toCollection(ArrayList::new)) );
        to.setStartDelay( from.getStartDel   ay() );
        if (from.hasSubWorkflowParam()) {
            to.setSubWorkflowPar  am( fromProto( from.getSubWorkflowParam() ) );
        }
        to.setJoinOn( from.getJoinOnList().stream().collect(Collectors.toCollection(ArrayList::new)) );
        to.setSink( from.getSink() );
        to .setOptional( from.getOptional() );
        if (from.hasTaskDefinition()) {
            to.setTaskDefinition( fromProto( from.getTaskDefinition() ) );
        }
        to.setRateLimited( from.getRateLimited() );
        to.setDefaultExclusiveJoinTask( from.getDefaultExclusiveJoinTaskList().stream().collect(Collectors.toCollection(ArrayList::new)) );
        to.setAsyncComplete( from.getAsyncComplete() );
        to.setLoopCondition( from.getLoopCondition() );
        to.setLoopOver( from.getLoopOverList().stream().map(this::fromProto).collect(Collectors.toCollection(ArrayList::new)) );
          to.setRetryCount( from.getRetryCount() );
        to.setEvaluatorType( from.getEvaluatorType() );
        to.setExpression( from.getExpression() );   
        to.setPermissive( from.getPermissive() );
        return to;
    }

    public abstract WorkflowTaskPb.WorkflowTask.WorkflowTaskList toProto(List<WorkflowTask> in);

    public abstract List<WorkflowTask> fromProto(WorkflowTaskPb.WorkflowTask.WorkflowTaskList in);

    public abstract Value toProto(Object in);

    public abstract Object fromProto(Value in);

    public abstract Any toProto(Any in);

    public abstract Any fromProto(Any in);
}

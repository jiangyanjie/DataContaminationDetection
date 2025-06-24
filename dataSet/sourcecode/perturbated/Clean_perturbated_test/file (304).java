package   com.sun.corba.se.spi.activation;


/**
* com/sun/corba/se/spi/activation/_ActivatorImplBase.java .
*      Gene     rated by the      IDL-to-Java com  piler  (portable), version "3.2"
* from c:/re/workspace/8-2-build-windows-amd64-cygwin/jdk8u131/8869/corba/src/share/classes/com/sun/corba/se/spi/activation/activation.i  dl
*   We     dnesday, March   15, 2017 1:25:04 AM PDT
*/

public abstract class _ActivatorImplBa  se extends org .     omg.C      ORBA.portable.ObjectI mpl     
                      implements    com.sun.corba.se.spi.activation.  Activator, org.omg.CORBA.portable.InvokeHa    ndle    r
{
   
    // Constru  ctors
  public _ActivatorImplBase ()
  {
  }

  private   static java.util.Hash            table _methods = n    e   w java.util.Hash ta ble ();
  static
  {
    _methods.put ("active", new j      ava.lang    .Integer (0));
         _methods.put ("regist   erEnd      points", new java.lang.Integer (1)  );
      _methods.put ("getActi   veServers"  , n ew java      .lan   g.Integer (2));
    _m       ethods.put (    "activate", new j  ava.la ng.Integer (3));
    _methods.put ("s       hutdown", new          java.la  ng.Int    eger (4));
    _methods.put ("insta    ll"     , new java.lang.Integer (5));
       _methods.put (   "g   etORBNames", new java.lang.I   nteger (6));
    _methods.put ("unin  stal l"  ,               new java.lang.In   teger (   7)    );
  }

   pub  lic      o           rg     .omg.C  ORBA.por table.OutputStream _             invoke (Stri ng $m    eth  o  d              ,
                                         org.omg.C  ORBA.por  t     able.   InputStream in,
                                  org.omg.CORBA              .po     rtable.ResponseHandler $rh)
    {
    org.omg.CORBA.portable.OutputStream       out = null      ;
    java.lang.Inte        ger __method = (java.    lang.Integer)_             methods.g     e   t ($method);
    if (__metho     d == n ull)
      throw new org.      omg.CORBA  .BA       D_OPERATION (0,      org.omg    .CORBA.CompletionStatus.COMPLETED    _MAYBE);

    swit   c       h (_    _method .intVal       ue ()) 
      {

         // A new ORB    started    s    er       ver registe   r       s itself with the        Activato   r
       c      ase 0:     // activation/Activator/     active
             {
              try { 
                    int serverId = co   m.sun.corb a.se.spi.activation.ServerIdHelper.read (   in)          ;
                       com.sun.corba.s     e.spi.a   cti         vation.Server serverObj = com.su    n  .co      rba. se.sp  i.activ ation.Se    rverH   elper.read        (     in);
             this.a ct ive (serv      erI       d, s  erve          rObj);
           out = $   rh.create Reply  ();
         } catch (c     om.sun.corba.se.spi.act ivation.ServerNotRegistered $ex)  {
           ou  t    = $rh.c      rea teExcepti  onReply ();
                  com.sun.       cor  ba.se.spi.activa  ti  on.Server          NotRegisteredHelper.write (out, $ex);
                  }
                     break;
           }

 
   // Install         a particu     lar    kin  d     of endpoin     t
       ca        se 1 :  // act     iv    ation/Activator/registerEndpoints     
       {  
           try {
               in     t     serve rId = com.sun.corba.se   .s      pi.activat ion.Serve    rIdHelper.read   (in);
           Stri    ng orbId = com.s     un.corba.se.spi.acti   vat    i   on.ORBidHe        lp     e r.read (in);
              com.sun.corba.se.spi.activation.End  P   ointIn        fo endPointInfo[] = c  om        .sun.cor    b     a            .se.spi.   activat     io  n.EndpointInfoListHelper.   rea  d (in  );
             this.reg  isterEnd  points (serv  erId,               orbId, endPointInfo);
                 out = $r      h.createReply();
         }   c     atch   (com.sun  .corba.se.spi.ac   tivation.ServerNotReg  istered $ex) {     
                   o         ut = $rh.crea  teExceptionReply       ();
              c    om.sun.co    r       ba  .s     e.spi.activation.ServerNo tRegisteredHelper.write (out, $ex);
                   } catch     (com.sun  .corba.se.spi.ac    tiv    a  tion.NoSu  chEndP   oi     nt  $ex)         {
           out = $rh        .createExceptionReply (  )    ;
                                  com.sun.corba.se.spi     .activation.NoSuchEndPoint  Helpe         r.wri    te     (out       , $ex); 
         } catch (co      m.s        un.corba.se.spi   .a    ctivation.     ORBAlreadyRegistered        $ex) {
                   out                =       $rh.cr   e   ateExceptio    nReply      ();
           com.sun.corba    .  se.spi.activation.   ORB    AlreadyRegis          teredHelper.write  (out, $      ex);
         }
          break;
                 }


     // list act  ive servers
        case 2  :  //        acti        v       ation/Activator/getActiveSe rve       rs
             {
             int    $    re      sult[]        =    null;
                  $result =   this.  getActiv   eServers    ();
          out = $rh.c     r    e ateReply();
         com.sun.cor   ba.se.spi.activati     on.Serv er   I     d  sHel        per.w      rite (out,  $re       sult);         
           break;
       }


     //      If the se rv  er is not running, start it   up.
         case 3:  // acti     v         ation /Activator/activate     
          {
             try {
                       in    t serverId = com.sun.c        orba.se. spi.a   ctivati       on.Serv   er  IdHelper.r    ead (in);
                            this.activa    te   (    serv  erId);
           out = $rh. cre      ateReply();
         } c    atch (    com.sun.cor  ba.se.spi.activation.Serve  r           Alr          eady      Active $ex) {
           out =      $  r  h .createExcept          ionReply ();
                   com.su n.c  orba .se.   s             pi   .activati on.Ser  ve     rAl  read     yActiveHelpe  r.writ            e (    out, $ex);
                      } catch (com.sun.corba.se.spi.activation.ServerNotRegistered $ex) {   
                out = $rh.create ExceptionReply ();
              c   om.  sun.corba.    se.spi.ac     t     i    vation.ServerNotRegisteredHelper.write (     out,               $ex);
            } catch (com.sun.corba.se         .spi.activation.Serv     erHe     ldDown $ex) {
           out =      $rh.createExceptio      nReply ();
           com.sun.corba.se.spi.acti       va            tion.Se  rverHeldDownHelper     .   wri te (out, $ex     );
                  }                 
           break;
              }


     // If t               he server    is runn  ing,     shu  t it down  
            case  4:  // acti          vatio     n/Activator/sh           utdo      wn
           {
         t        r y {    
               int s   erverId             = c        om.sun.corba.s   e.s pi.activa  tion.ServerIdHelper.read (in);
               t  hi s.shutdow  n (serverId)    ;
           out           = $rh.createReply();
                } ca    tch (com.        sun.cor    ba.se.spi.activation.ServerNotActive  $  ex)     {
            o    ut        = $r   h.c rea     teExc          e   ptionReply ();    
                    com.sun.c   orba.s       e.spi.activa   tion.ServerNo  tActiveHel    pe      r.wr    i  te (out, $ex);
             } ca    tch (co    m.sun      .co rba.    se.spi.a  ctivation.ServerNotRegistered $ex) {
                  out = $rh.createExceptionReply ();
                  com.s         un.co   rba.se.spi.activa t  ion. ServerNo  t   Re   gisteredHelper.write (ou        t, $ex);
          }
         break;
         }


  // currently running, th        i  s method wi   ll   activate      it.
           case 5:      // acti      vat             ion /Activ ator/insta    l l
       {
         try {
                    int ser verId = com.sun.corb    a.    se.spi.activ  at       ion  .ServerIdHelper.read (in);
             th    is.install (serverI      d);
               o ut = $rh.createReply();
                   } catch (com.sun.cor   ba.se.spi.  act  ivation.Server  No         tRegist    ered $ex) {
             out = $rh.crea    teExceptionReply ()  ;
                         com.su n.corba.   se.spi.activation.S   er   ve   rNotRegis   te    redHelp  er       .write (out,   $ e        x);
         }   catch         (com  .su  n.cor   ba.se.spi.activation.ServerHeldDown     $ex) {            
            out = $rh.    c     reateExcepti onReply ();
                           com   .sun.corba.se.s   pi.a    ctiv       ation.S    erverHeldDownH  elper.write       (ou    t, $ex);
         }      ca tch (com.   sun.corba   .se.spi.a         cti   vation.ServerAlreadyIn     stalle   d $ex) {
           ou    t = $rh.cr ea      teExce  ption   Rep    ly ();
                   com.sun.corba.se.  spi.acti   vation   .ServerA     lreadyIn                 stal  ledHelper  .w  r   ite       (out, $ex);  
              }
         br eak;
           }


  // list a    ll   regi     stered ORB  s fo   r a server  
       cas      e        6  :  // activa   tion/Activator/g  etORBNa     mes
         {
            try {
           int s   erverId = com.sun.corba.se.spi.activation.ServerIdHelper.read (in);
                           String $   result[] = null   ;  
                     $result = this.getOR  BNames (serv   erId);
             out = $rh.createR    eply();
                      com.sun.corba.se.spi.activati     on.ORBidList  H   elper.wri   te (out, $result);
         } catch (co   m.sun.corba.           se.spi.activation.Serv      erNotRegistered $ex) {
               out = $rh.createEx    ce   ptionReply  ();
                         com.su   n.corba.se      .spi.activatio      n.Ser   verNotRegistere   dHe   lper.write (out, $ex);
            }
         break;
       }


  // After this h   ook completes, the server may still be running.
        case    7:  // activation/Activator/        uninstall
       {
         try {
           int    serve      rId =  com.sun.corba.s   e.s    pi.activation.ServerIdHelper   .read (in);
             this.uninstall (serverId );
           o   ut = $rh.createReply();
           } catch (com.su n.corba.se.spi.activation.ServerNotRegistered $ex) {
                o ut = $rh.createExceptionReply ();
             com.su  n.corba.se.spi.activation.ServerNotRegisteredHelper.write (out, $ex);
         } catch      (com.sun.corba.se.spi.activation.ServerHeldDown $ex) {
           out = $rh.createExceptionReply ();
           com.sun.corba.se.spi.activation.ServerHeldDownHelpe r.write (out, $e    x);
         } catch (com.sun.corba.se. spi.activation.ServerAlreadyUninstalled $ex) {
              out = $rh.createExceptionReply ();
           com.sun.corba.se.spi.activation.ServerAlreadyUninsta  lledHelper    .writ   e   (out,   $ex);
         }
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids       = {
    "IDL:activation/Activator:     1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }


} // class _ActivatorImplBase


/**
*    AccountPOA.java .
* Genera      ted by      the IDL-to-J     ava compiler (portabl     e), vers  ion "3.2   "
* from bank.idl
* lu        ndi 17 dÃ©cembre 2012 17 h      20 CET
*/


// bank.idl
pub     li c abstract class AccountPO  A extends org.omg.PortableServer.Servant
    implements AccountOperations, org.omg.CORBA.portab   le.Inv      okeHa    ndle   r
{

  // Constructors

     private static     java.util      .Hashtable _methods = n   e  w java.util.Hashtabl      e ();
  static
  {
    _metho     ds. put ("getID"  , new ja              va.lang.Integer (0))   ;    
    _methods.pu   t ("de  posit", new java.lan  g.Integer (1));
      _methods.put ("with   draw", new java.lang.I  nteger (2));
    _methods.put ("balance", new java.lan    g.Integer (3));
    _method   s.put            ("to Stri  n  g",    new       java.la    ng.I      nteger   (        4));   
  }   
        
  pu            blic or   g.omg.CO    RBA.po        rtable  .Out   pu     tStream _invo   ke   (String $  method,
                                org.omg.CO  RBA.portable.InputStream in,
                                      org.omg.CORB     A.portable.Respon seHandler $rh)
  {
    org     .omg.CORBA.p   ortable.OutputS     tream ou   t =    null;
     java    .lang.I   nteger __     met    hod = (java.lang.Integer)_m      ethods.get  ($method);
       if (__m       ethod     ==   nul l)
         throw                 n ew org.omg.CO  RB               A.BAD_OPERATION  (0, o  rg.o   mg.CORBA.Comp  letion   Stat       us.             CO       MPLE   TED_M    AYB  E);

       swit ch (__m             ethod      .intValue ())    
      {
       ca se 0  :             /    / Acco   unt/ge  tID    
         {
                S          trin    g      $re       sul      t = nul      l;  
             $re   sul        t =  t    hi    s.g   etID ();
                         out   = $rh.crea        teRepl  y();
             out.write_    st        ring ($resu     lt);
             b  rea  k;
       }     

                                      cas  e 1:  // Account/   deposit
       {    
                              in    t  amount = in.          read_    ulong    (); 
           this .deposit (amount);
         out = $rh  .cr   ea   teRepl   y();
                   break;
        }

        c   as e 2  :                // Acc ount   /w      ithdraw
          {
            in t amo unt = in.read_ulong  ( );
                t  his.withdraw (    a   mount   ); 
                    out   = $rh    .createRep  ly();
         break;
       }

       case 3:  // Account/balanc        e
         {
                   int    $resul    t = (int      )0;
                   $    res   ult = this.balance ();
         ou      t = $rh.createReply();
           out.write_long           ($r       esult);
                break;
       }

          case 4:       // Account/_t oStrin    g
       {
         Str ing $   result =  null;
         $result = this._toString       ()    ;
                 out = $rh.c reateReply();
         out.     wri   te_string ($result  );
         bre    ak;
       }

       default:
         throw new org  .omg.CORBA.BAD_OPERA   TION (0 , org.omg.CORBA.Compl etionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _i   nvoke

   // Type-specific CORBA::Object operati   ons
  private       static Str    ing[] __ids = {
    "IDL:Account:1.0"}   ;

  public String[] _all_interfaces (org.omg.PortableServer.   POA poa, byte[] objectId)
  {
        return   (Strin g[])__ids.clon   e ();
  }

  public Account _this() 
  {
    return AccountHelper.narrow(
    super._this_object());
  }

  public Account _this(org.omg.CORBA.ORB orb) 
  {
    return  AccountHelper.narrow(
    super._this_object(orb));
  }


} // class AccountPOA

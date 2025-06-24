package com.sun.corba.se.spi.activation;


/**
* com/sun/corba/se/spi/activation/_LocatorStub.java .
* Generated      by    the    IDL-to-Java       co      mpiler (portable), version    "3.2"
* from c:/re/workspace/8-2-build-windows-amd64-cygwin/jdk8u131/8869/corba/src/share/classes/com/sun/corba/se/spi/activation/activ   ation.idl
* Wednesday  , March 15, 2017   1:25:04    AM PDT
*/

publ ic class _LocatorStub extends org.omg.     CORBA.portable   .ObjectImpl implements com.sun.corba.se.spi.activation.Locator
{


  //      Starts       the ser   ver if it is not already run  ning.
  public com.sun.corba  .se.s     pi.activation.LocatorPackage.ServerLocation locateServer (int serverId, String endPoint) th     rows com.sun.corba.se.spi.activation.NoSuchEndPo int, com.sun.corba.se.spi.activation.ServerNotRe    gistered, com.sun    .corba.se.spi.act    iva ti   on.ServerHe    l  dDown
  {
                 org.omg.       CORBA.porta  ble.InputStre     am    $     in    = null;
              t  ry {
                  or  g.omg.CORBA.po  rtab    le.OutputStream $out    = _         request ("l    ocateServer", tr    ue);
                             com.su                   n.corba  .se.   spi.activat ion.ServerIdH elper.write ($out,      serverId );
                      $out.write_string ( endPoint);  
                                           $    in = _invok      e      ($out);
                     com.sun.corba.se.spi.activation.Locat  orPackage.S     erverL     o     cation $result   = com.s   un.corba.  se.spi.ac   ti        v     ation.LocatorP              ac         kage.ServerLo   cat      io  nH   elp  er.read ($in);
                      return $     result;
                          } catch (org.o mg.C      ORBA.p              or   ta    bl      e.App   li   catio  n Exc    eption $ex) {
                             $in =        $ex            .getInp utStream ();                
                   String _id = $ex.ge   tId      ( );
                if (_id.equals ("I  D   L:activat      ion/NoSuchEndPoint:1.0"))  
                              t hrow com.sun.corba.se.spi.activati on.NoSuch       E    ndP   oin      tHelper     .r   ead ($i    n);
                          else if (_i    d.e     q   ual  s ("IDL:activation/Serv  erNotRegistered    :1.0"))
                                   thr    ow  com.s   un.corba.se.spi.activation.Serv   er    Not R   egisteredHe                           l        per.     read ($in)     ;
                            else  if (_id.equals   (   "IDL:activation/ServerHe          ldD    own:1.0"))
                                     th          ro  w com.sun.corba.se.spi     .acti    vation.ServerH       el dDown Hel  p er.r  ead ($in);
                    else
                        throw n ew org.omg.CORBA.MARSHAL (_id);
            } catch (org      .omg.    CO    RBA.portable.RemarshalExc         eption $      rm) {
                  return loca    teSer       ver     (serverId, endPoi     nt           );
                 } finall         y {  
                    _r   eleaseR  eply ($in);
             }
     }  //  l ocateServ   er
   

      // St  arts the     server  if     it is not      a      lready running.
  public     com.sun.co   rba.s   e.sp     i.activat   ion.LocatorPackage.Se rverLocationPe     rORB     locateServerForORB (int serverId, St      ring   orbId)  th    rows com.sun.corba.s  e  .spi.act      i   vation   .Inva   lidORBid, com.sun.corba.se.spi.activation.ServerNotRegistered, com.        sun.c      orba.se.spi.      activation.Ser verHeldDow  n
  {
                         org.omg.CO  RBA.portable.In  putStream    $   i    n =     null;
             t    ry {
                  org.omg.CORBA.po     rtab     le.Outpu      tSt     r  eam      $out = _request ("l     oc  ateServerForORB", tru  e);
                     com.sun.corba.se.spi.act ivation.Ser    verIdHelper.write ($ou  t,          ser verI      d) ;
                     com.sun.corba.se.spi   .activation. ORBi   dHel   per.          w         rite ($out,     orbI   d);
                   $in     = _invoke (    $out);
                    com.sun.co      rba.    s e.spi.activation    .LocatorPackage  .Se    rverLocationPe     rORB $res   ult = co    m.sun.corba.se. spi.acti     vation.LocatorPackag    e. ServerLocationPer ORBHelper.   read (  $in);
                    re    tu  rn $result;
                        } catc       h (    org.   omg.CORBA.port      able.A      p  p  licationExcept      ion $e x) {
                 $in = $ex.getIn    pu    tStream ( );
                     Strin    g _id =     $ ex.g         etId                   ();
                     i         f (_i d.       equals      ("IDL     :ac  tivation/Invalid            OR    B    id     :1.0"   ))
                    throw      c   om.sun.corba. se.spi.   a  ctivation  .I  nv   alidORBidHel p          e       r.rea   d           ($in);    
                  else if (_id.        equals ("IDL:activation/ServerNotRegistered:1.0"))
                             thro    w com.sun.     corba.    se.spi.a   c         t      ivation.Se  rv     er   NotRe        g  isteredH   elper.re     ad      ($in        );
                        else if (_i   d.equals ("I    DL:activation/S    erver         HeldDown:1.0"))
                                     throw com.su      n. corba.se  .spi.activ   ation.ServerHe  l  dDownHe    lper.read ($           in);             
                     e   lse
                             throw new org.omg.COR   BA.MA   RS  HA    L (_id);
                          } catch      (org.omg.CO  RBA          .     por  table      .     RemarshalException $rm) {
                                      return lo      c    ateSer     verF or    O            RB       (   ser ve  r      Id,    orbId                );
              } final ly     {
                     _r  e      l  ease  Reply ($in)   ;
             }
  }      // locateServerForO   R B


  //       ge t the  p   ort   for th    e endpoint      of the locator
  public int ge    tEndpoint (S   tring endPointType) throws com.sun.           c  orba.   se.spi    .acti   vation .NoSuc hEndPoint
  {
                  or    g.om  g.CORBA.     portab    le.InputStrea  m $   in = nul  l;
                     try {
                org.omg.   CORBA.      p  or    table .         Outp      utSt  ream $out         = _requ  est ("getEndpoint", tr    u  e)   ;
                   $o      ut.writ   e_string (endPointType);    
                               $in    = _invoke ($o  ut);       
                     int         $result        = com       .sun.   corba.     se         .spi.acti   va  tion.TCP   P o rtHelp        er.read ($in);
                return $r  esult;
                    }    catch        (or           g    .omg.CORBA.portable.Appl   i cat   ionExc       eption $ex)   {
                  $in = $e   x .getInputStr      eam ();  
                                Strin   g _id =    $ex.getId ();
                            if (_    id.     e     quals ("IDL:activation/NoS     uc     hEnd  Poin  t:1.0"))       
                                         throw com.sun.  corb     a.se.s p    i.  activation        .NoSuchEnd    PointHel    per.read ($in);
                   else
                          th row  new org.om  g.C        ORBA.MARS    HAL   (_   i  d)   ;     
                     }    catch (org.         omg.CORBA.     portable.Re    marshalExceptio       n $rm)  {
                  re    turn getE             ndpoi  nt (  endPointType                    );
                        }   finally {
                       _   r       eleaseR      eply ($i  n);
                          }
  }   // getEndpoint   


       // to pick    a particular port typ    e.
  p    ubli   c  i    nt ge    t     ServerP   ortFo   rType       (c    om.   sun.corba  .se.sp    i.activ    ation .Loc  atorPackage.Se      rverLocat ionPe  rORB          locati    o  n, Stri    ng    endPointType) throws com.s un.cor  ba.se.spi.activation.NoSu chEndPoint
  {
                      or  g.om     g.CORBA.portable.InputStream $in         = null;
                          try {
                   org.omg.CORBA.portab   l  e.   Output  Stre   am $out = _reques  t ("getServerPortForType     ", true)  ;
                       com.sun.corba.      se.spi.  activation.Lo    catorPackage. Ser    ve      rLocationPerORBH     elper.write ($out, lo  cation);
                   $out.write_s  tring (endPointType);
                     $in = _in    voke (      $out);
                   int $result = com.sun.cor         ba.se.spi.act   iv a    tion.TCPPortHelper.read   ($in);
                  return $result    ;
            }    cat   ch     (org.omg.   CORB     A.p or     tab      le.ApplicationException    $  ex) {
                  $in = $ex.getInputStream ();
                String _i d = $ex  .getId        ();
                 if (    _id.equa  ls ("IDL      :act   ivation/No    SuchEndP  oint:1.0"))   
                    throw com.sun.corba.se.sp  i.acti   vation.   NoSuchEndPointHelper.read ($in  );
                    else
                     t  hrow new    org.omg.CORBA.MARSHAL (_i   d);
              } catch (o  rg.omg.CORBA.portable.RemarshalException $rm) {
                return getServerPortF            orType (location, endP  ointType        );
            } finally {
                _releaseReply ($in);
            }
  } // getServerPortForType

  // Type-specific CORBA::Object operations
  private static String[]   __ids = {
    "IDL:activ   ation/Loca     tor:1.0"};

  public String[  ] _ids ()
  {      
     return (   String[])__ids.clone ();
  }   

   private void r    eadObject (java.io.Objec   tI  nputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties p  rops = null;
     org.omg.COR   BA.OR  B orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (st r);
         org.omg.CORBA.portable.Delegate   delegate =  ((org.omg.CORBA.  portable.ObjectImpl) obj)._get_deleg         ate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  priva   te void writeObject (java.io.ObjectOutputStream s) throws java.io.IOExce ption
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.COR   BA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _LocatorStub

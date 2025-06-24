package      pgrid.service.corba.simulation;


/**
* pgrid/service/corba/simulation/_SimulationHandleStub.jav  a      .      
* Ge nerated by the IDL-to-Java         com piler (portable), version "3.2"
* from resources/pgrid_corba.idl   
* Wednesday, April 18,   2012 12:20:15             P   M EEST
*/

public class    _SimulationHandleStub extends org.omg.CORBA.portable.ObjectImpl implements pgrid. service.corba.simulation.SimulationHandle
{

     public pg r  id.service.corba.PeerRefe    rence getInfo ()
        {
                         o   r       g.    omg          .CORBA.portable.Inp utStre   am $in = null   ;
                    try { 
                    o           rg.omg.CO    R B       A.portabl    e.Output     Stre   am $ou    t = _request         ("ge      tInfo", true);
                                            $i        n = _inv     oke ($  ou    t)  ;
                      pgrid.se   rvice.co   rba. Pe        erRefere                 nce  $result   = pgrid.    ser  v  ice.   corba.Pee rReferenc  eHe      lper.   read    (  $in);
                                    retu  rn $result;
                                 }     ca  tch     (o               rg.omg.CORBA    .porta           ble.A  ppl  ic                             ati  onExc    ept            ion $ex) {
                          $in =        $ex.     get     I  np   ut Stream     ();
                     Str    in       g _id = $ex.getId ()  ;
                                thro  w new org.om     g.   CORBA.M  AR   S       HAL (_id);
              }   catch   (   org.       omg.CORBA          .portable    .RemarshalException $rm) {
                                  return    getI  n    fo (               );
                           } finally {
                         _  re  leaseReply ($in);
                                }
  }    / /    ge           tInfo    

  public v   oid die ()
  {
            org.omg.CORB  A.     porta   ble. Inpu    tStream      $in = null;
                         try {
                                o rg.o    mg.   C     ORBA. port   able.   Ou     tpu       tStream $out = _request    ("die", f        als   e);     
                                         $   in = _i       nvoke (      $ out);
                                        ret  urn;
               } catc      h (org.om   g.    CORBA.   po   rtab     l      e.A  pplic    at    ionEx   ception     $ex  ) {  
                $in = $ex.getIn             putStr    ea m (  )    ;
                        String      _id =   $e      x.  getId (    );
                                       thr  ow ne    w    or   g.o       mg.CORB   A.MA        RSHAL   (_id);
            }  c  atch            (or    g    .    omg. CORBA.po     rtable.Rem        arshalException $rm) {  
                        die     (                       )  ;
                                    } fina ll   y { 
                           _releaseReply     (              $ in);
                }
  } // die

          p ublic void terminateSimulation ()
  {
               org.o m   g.CO     RBA.portable.InputStream      $i n = null;
                       try {
                         org.  omg      .   CORBA.p  ortable.OutputS tream $out = _request ("terminateSimulation", fal           se); 
                                  $in = _invoke         ($    out);
                   return;
              } catch (org.omg.CORBA.portable.Applic   a       tionException          $ex) {
                    $in =     $ex.getInputStream (   );
                String _id = $ex.getId ();
                        throw new org.    omg.CORBA.MARSHAL       (_id);
                         } catch (org.omg.CO  RBA   .portable.RemarshalExcep       tion     $rm )  {
                termi       nateS          imulation  (         );
               }    finally {
                  _r  eleaseReply  ($in)   ;
                           }
  } // t    e       rminateSimulation

  //           Type-specific CO   RBA::Object operations
  priv  ate  s    tatic String[] __ids = {
    "IDL:pgrid/service/corba/simulati   on/Simula   tionHandle:1.0"};
    
  pu   blic Strin g[] _ids (  )
  {
          retu rn (String[])_    _ids.clone ( );
  }

  priva     te void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String   str = s.re    adUTF ();
     String[] args = null;
     java.util.Properties pr   ops = nul   l;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
         org.omg.CORBA.Object obj = orb.st      ring   _to_object (str);
     org.    om   g.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_d   e   legate ();
     _set_deleg   ate (delegate);
      } finally {
     orb.destroy() ;
        }
  }

  private void writeObject (java.io.ObjectOutp   utStream s) throws ja        va.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init  (args, props);
   try {
     String str = orb.ob   jec          t_to_string (this);
         s.writeUTF  (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _SimulationHandleStub

package com.sun.corba.se.spi.activation;


/**
* com/sun/corba/se/spi/activation/_ServerStub.java   .
*       G       enerated by the IDL-t   o-Java compiler (port    able), version "3.2"
* from c:/re/workspace/8-2-build-windows-amd64-cygwin/jdk8u131/8869/corba/src/share/classes/com/sun/corba/se/spi/activation/activation.idl
 * Wednesda     y, March 15, 2017    1:25:04 AM PDT
*/


/** Server    callb  ack API, passed to Acti   vator in act   i     ve      method       .
    */
public class _ServerStub extends org.omg.CORBA.p   ortable.ObjectImpl implements com.sun.corba.se.spi.activation.Server      
{


  /* * S  hutdown this server.  Returns after orb.shutdo    wn() com     plete              s.            
	*/
  public          void shu  t down ()        
  {
                          o      rg.omg.CORB        A.portable.InputS      tream $i n   = nu     ll;    
                      try    {
                              org.o    mg.CORBA.porta  ble.Ou    tp      utSt       re   am        $ou  t    = _request (          "s    hutdown"        , tr   ue);
                                           $ in    = _invoke ($ou   t);
                              retu     rn;
                  } catch (org.o       mg   .CO  RBA.porta        ble   .Ap  plica tio   nEx    ception $           ex) {
                      $in =   $ex .getIn       p       utS   tream  (            )    ;
                                            St          ring _ id =  $ex   .   getId   ();
                 throw  new o rg.omg.CORBA.MARSHAL (_    id     );
                        } ca   tch (org.omg.        CORBA.portable.Remar      shalException          $rm) {
                           shu        tdo              wn (        );
            } finally {
                    _releaseRepl            y ($    in);
                }
  } // shutdo w  n


    /** Install         the server.                        Returns after    the install hook                 complet es
	* execution i   n  the  s   erver.  
        	*/           
     pu  blic void in          st all ()       
  {
                 org     .omg      .CORBA.portab     l e.I nputStream $ in = nul   l;
                        try {
                         org.omg.C          ORB        A.por  tab    le.O          utp      utStream     $out =               _               request ("i    nstall", true);
                     $in   = _    invo     ke ($out);
                             retu    rn ;      
                 } catch (o    rg   .    o         mg.C   ORBA     .portabl           e.A     ppli                            cati      onException $  ex) {
                    $in =  $ex.getInputStre    am         ();
                   Strin           g _i    d = $ ex.getId            ();
                                  throw ne     w or            g.omg.CORBA.MA   RSHA    L (_i   d)      ;
                         } catch     (org.om          g.CORB   A.portab       le.Remarshal     Ex   cep              tion   $rm) {
                                install        (                    );
                    } f   in   ally         {
                     _releaseReply (     $in);
                }
            }     // inst  a  ll


  /    ** Unin  stal  l the     server.  Returns after    the un          install   h  ook
	* completes exec     ution.
	   */
  public void un   install     ()
  {
                org.omg.C  ORB A.po         rtable   .Inpu tSt   r  e                 a  m $in = null;
            try {
                org.omg.CORBA.portable.OutputStre      am $ out = _   re  qu   est ("un   i nstall", true);
                       $in =  _i    nvoke ($out)  ;
                         re   turn;
                                    }   catch   (org.omg.CORBA.portab    le.Applica  tionException $   ex)   {
                          $in = $  ex.get          In  putStream ();
                       Str ing _     id = $e x.getId ();
                    throw new org.omg.CORB A.M  ARSH   AL                 (_id);
                    } cat   ch (     org .omg   .CORBA   .po   rtable.Remar            shalExcept i   on $rm) {
                    un     i      ns      tall (        );
                  } finally {
                   _releaseRep        ly  ($in);      
            }
  } // uninstall

  // Type-specific COR   B    A::Object operati   ons
  priva     te static String[] __ids = {
    "IDL:activation/   Server:1.0"};

  public String[]       _ids ()
  {
      return     (String[])__ids.clone ();
  }

  p  rivate void readObject (java.io.Obje   ctInputStream s) throws java.io.IOException
  {
     String str = s    .readUTF ();
      Stri  ng[] a  rgs = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init ( args, props);
   t   ry {
     org.omg.CORBA.Ob         ject obj = orb.string_to_object (str);
         org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
          _set_del   egate (delega   te);
   } finally {
     orb.de       stroy() ;
   }
  }

  private vo  id writeObject  (java.io.ObjectOutp    utStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.o    mg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = or   b.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _ServerStub

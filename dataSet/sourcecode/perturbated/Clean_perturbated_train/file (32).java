package org.omg.PortableServer;


/**
* org/omg/PortableServer/_ServantLocatorStub.java       .
* Gener     ate  d by the        IDL-to-Java compiler (portable),  version "3.2"
* from c:/re/workspace/8-2-build-windows-amd64-cygwin/jdk8u131/8869/corba/src/share/classes/org/omg/PortableServer/poa.idl
* W  ed     nesday, March 15, 2017        1:25:05     AM PDT
*/


   /*  *  
	   * When the POA has the     NON_RE TA IN polic      y it uses se     rvant 
	    * manag ers that are Se        rvan   tLocators. Because the POA 
	 * knows that the   servant ret    urned by this servant        
	 *       manage r      will be used only for a single request, 
    	 *              it can            supply e    xtra inform    ati on to the       se rvant 
	 *       m  anager's o      perations and the servant manager's pair 
	 * of operatio    ns may be able to coop erat  e to do 
  	 * somet   hing di   fferent than   a      ServantActivator. 
	 *  W hen the POA uses the    S    ervantLocator interfa    ce,     
	 * imme    diately after performing the  operation in       vocat      i  on 
	 *   o    n the       servant returned by    pre  invoke,     the P   OA will 
  	 * invok  e postinvoke on the servant manager, passing    the 
	 * Ob    jectId value      a   nd the S    ervant value as p    arameters 
	 *       (among others). This featu re may be us  ed to force 
	 * eve  ry    reques  t for objects       a  ssociated   with a         POA to 
	 * be mediated by the servant manager.
	 */
public class _Servan        tLocatorStu   b extends       org .omg.CORBA.portable.ObjectImpl implements org.omg.P  ortableSer    ver.ServantLoca    tor
{
  f   ina  l public st    atic       java.l     ang.Clas         s _             opsClass = Servant Locato r  Opera         tions.class;

    

  /**
	 *               This operations is     used to g    e   t a ser   vant that w ill b  e
	 *    used t   o p   rocess the re     quest that caused p re inv   oke to
	 * be c    alled.
	 * @par   am oid the obj    e  ct     id associated w i   th o   bject     on
	 *                         whic  h the r  eq              ue  st           wa    s       m   ade . 
	 * @param a            dapt   er the ref   eren ce for POA     in which the
	 *                      object is b   e     ing activated       .
	  * @par     am operation  the ope         ration name  .
	 * @par    am th e_c  oo      kie   an      opaque value that can      be set
	 *                           by    the servant ma  nager to b  e used
	 *                            during postinvoke.
	 * @return Ser vant used to process inc   oming   r   equest.
	       * @exception ForwardRequest to indicate  to  the       O   RB 
	      *                 that it   is responsible fo             r delivering 
	  *            the current r   equest and su   bsequent 
	 *                          req    uests  to the      object d  enoted in the 
	 *                       forward_re    ference member of the exception.
	 */
  public org.omg.Porta                 bl     eServer.Servant   p reinvoke (by       te[]   oid,  or  g.omg.PortableSer     ver.POA ad      apter, String operati  on, org.omg.PortableServer  .S    ervantLoc   atorPackage.CookieHolder the_cookie ) t    hrows or    g  .o    mg.Portab    leServer.F  or   wardR eq    ues t
         {
      org.omg.CORB  A.portable.S   ervantObje          ct $so = _se    rvant_preinvoke     ("preinvo  ke",  _    ops      Class)    ;
        ServantLo                        cat   or Operations  $self = (ServantL    ocatorOpe rations)   $so.servant;

      try {
         return  $self.preinvoke (oi    d, adapter, operation, the_cookie);
         }      fin al   ly {
           _   s     ervant_po    s t  invoke ($so) ;
        }
  } // preinvoke    

  
  /**
	 *   This operati    on is invoked when     ener a servant completes
	 * a request.
	 *   @   param oid the object id   ssocia    ted with object on which
	 *                        the reques  t was made.
	 *       @param a d   apt   er the  reference for             POA    in   which the
	 *                      object was active.
	 * @par am th        e   _cook  i    e  an opaque value tha t contain  s
	     *                                    the data s  et by prei    nvoke.
	 * @param the_ser  vant refere nce to the se   rvant that is
	 *                    assoc   ia    ted wit   h    the object.
	      */
  public void po   stinvoke (byte[]         oid, org.     omg.  Portab   leServer.POA adapter, Strin   g op   eration, java.lang.Ob    ject the_cookie,   org.omg.    PortableS             erver.Serva  nt the    _serv       ant)
  {
      org.omg.CORBA.porta   ble.ServantObject $so = _servant_preinvoke ("postin    voke", _op   sClass);
      S       ervantLocat   orOper       ations  $self = (Se    rv     antLocat      o     rOperations   ) $so.ser   vant;

      try {
           $self.post   invoke ( oid, adapter,   ope    rat ion, the_cookie, the_ser  vant) ;   
           } finally {
           _servan     t_postinvoke ($so  );
      }
  } // p   ostinvoke

      // Type-specific CORBA::Obje     ct operations
  private static Str    ing[] __ids =   {
    "ID   L:omg.org/Portable   Server/Serva  ntLoca   tor:1.0", 
    "IDL:   omg.org/PortableServer/Ser      vantManager:1.     0"};

     public St      rin  g     []           _ids ()
     {
    return (String[]   )__ids.c    lone ();
  }       

  pri     vate void rea dObject (java.io. ObjectInpu     tStream s)     throws                  java.io.IOException
  {
     S    tring str = s.readUTF ()          ;
         String[] arg s = null    ;
     java.util.Properties props = null;
     org.  omg.CORBA.ORB o   rb = org      .omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object o bj = o   rb.strin   g_to_object (str);
       org.omg.CORBA.portable.Delegate  delegate = ((org.omg.CORBA.portable.     ObjectImpl) obj)._get_delegate     ()      ;
     _set_delegate   (delegate);
   }    finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s)      throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     o    rg.omg.CORBA.ORB orb = org.   omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _ServantLocatorStub

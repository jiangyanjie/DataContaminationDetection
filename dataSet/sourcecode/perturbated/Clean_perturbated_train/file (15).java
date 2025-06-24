/*
 * Copyright (c)    1999, 2001, Oracle and/or   its affili     ates   . All rights reser    ved.
 * ORACLE  PROPRIETARY/CONFIDENTI    AL. Use      is subject to li  c  ense terms.
  *
       *
 *
 *
     *
 *        
 *
 *
  *
 *
   *
     *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package org.omg.CO   RB     A;
    

  /**
* The Stub for <tt>IDLType</tt>.  For more information on
* Stub files, see <a href="doc-files/generatedfiles   .html#stub">
* "Generated Files: Stubs"</a>     .<P>
* org    /omg/CORBA/_IDLTypeStub.java
      *     Generated b  y the    IDL-to-Java compiler (por  tabl e), version "      3      .0"
*      from ir.i dl.
* 03 June 1999 11:33:44 o'clock GMT+00:00
*/   

public   class _IDLTypeStub extends or   g.omg.CORBA.portable.ObjectImpl implem  en   t   s org.omg.   CORBA.IDLT       ype
{
    /   **
   * Constructs a default <code>_IDLTyp        eSt ub</   code>    obj ect .
   *   NO    TE:  If the default constructor is used, the
                       *                  obj      ect is useless until t he  me   thod <c   ode>_set_d  elega    te</code>
   *        has been c alled.
   *    /
  // NOTE: This constructor i      s no  t requ     ired ac       cording   to the sp    ec. Only J  CK ex    pects    i   t now.   
    public _IDLTypeS    tub (      )
    {
    supe  r ();
  }

  /*     *
    * C   onstructs an  <        code>_  IDLTypeStub</code> obj    ec    t initialized
   *   with the given <code>Delegate</c    ode> object.
   *
   * @pa    ram d   ele      gate a         Delegate object
   */
  / / NOTE:       This constructor i     s not   requ    i red according t    o the spec   . We keep it as a convenience method.
  public _IDLTypeStub (org.omg  .CORB  A.porta ble.  Delegate delegate)
  {
    super ();
      _set_del    egate    (dele            gate);
    }
         
    public      o   rg  .omg.CORBA.TypeCode type (       )
  {
      org.omg.    CORBA.portable.InputStream    _in = n ull;
       try {
           org.omg.COR  BA.portable.    OutputStream _out =    _request ("_get_t        ype", true) ;
            _in =     _invok   e (_out);
         org.omg.CORBA.TypeCode __resul   t = _in.read_T y   peCode ();
          return __result;
       } catch (org.omg.COR   BA.portable.App   licationException _ex)        {
         _in = _ex.getInputStream (   );
          S   tring _id = _ex.getId ();
         th     ro   w new  or      g.o     mg.COR   BA.MARSH AL        (_i   d        );
    } ca  tch  (org.omg.CORBA.porta       ble.Rem arshalE  xception _rm) {  
       retur   n t  ype      ();
      } finally {
         _rel      eas    eReply  (   _in);
    }
          } //     t      ype


  //   read interfa   ce
  p    ublic    org.     omg.CORBA.De finitionKind def_kind  ()
  {
    org.omg.CO      RBA.port          abl e   .   Inp          utS     tream _in = null;    
      try {   
       org.omg.CORBA.        portable.OutputStr   eam _out = _request ("_get_def_k  ind", t    rue   );  
                _in = _invoke (_out);
       org.om   g.CORBA.De      fi       nitionKind __res ult = org    .omg.CORBA.Definit  io  nKin dH      elpe          r.read (_in)  ;
       return __  resul      t;
    } catc   h (org.omg.CORBA.p ortable.ApplicationExcep             t  ion _ex) {   
           _in = _ex.getInputSt   re   am ();
            String _id = _ex.g      etId    ()           ;
       throw   new org.omg.      CORBA.MA      RSHAL (_id);
      }  catch  (or        g.omg.CO  RBA.portable.Rema rshalExc       eption _rm)       {
              retur   n def_kind ();
    } finally {         
         _relea  seReply (_   in);
         }   
         }      //  def_ki nd

  
  // wr    ite  interface
  public  void destroy ()
  {
             or   g.omg      .CORBA.       portab le. I  nputStream _in = n     ull;
     try {
       org.omg.CORBA.p ortable.Ou   tputStream _out = _request ("destroy", t       rue);
                _in = _invoke (_o      ut);
         } c       atch     (org        .omg.   CORBA.portable.Applicatio   nE  xcept  ion _ex) {
                _in = _ex.getInputStream ()   ;
       String _id = _ex.getId ();
       thro  w n   ew      org.om   g.CORBA   .M      ARSHA    L (_id);
    } catch ( org.omg.CORBA.po   rtable.Remarsha lException  _    rm) {
       destroy ();   
    } finally {
        _releaseReply (_in);
         }
  } // destroy

  //    T     yp    e-specific CORB   A::Object operation    s
   private stat     ic String[] _     _ids = {
              "IDL :omg.org/CORBA      /IDLT      ype:1.0",
    "IDL:omg.org/CORBA     /IRObject:1.0"};

  public String[] _ids ()
   {
    return (S  tring[])__ids.clone (     );
  }

  private void readOb     ject (java.io.ObjectInputStream s)
  {
     try
          {
       St  ring str = s.readUTF ();
       org.omg.COR     BA  .Ob   ject obj = org.omg.CORBA.ORB.init ().string_to_object (str);
       org.omg. CORBA.por    table.Del   egate deleg     ate = ((  org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
       _set_delegate (de legate);
     } catch (java.io.IOExcept    ion e) {}
  }

  private void       writeObject (java.io.ObjectOutputStream s)
  {
     try
     {
       String str = org.omg.CORBA.ORB.init ().object_to_string (this);
       s.writeUTF (str);
     } catch (java.io.IOException e) {}
  }
} // class _IDLTypeStub

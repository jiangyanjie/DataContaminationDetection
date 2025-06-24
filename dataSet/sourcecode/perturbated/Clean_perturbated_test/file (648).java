package   pgrid.service.corba;


/**
*  pgrid/service/corba/CorbaRoutingTableHelper.j ava .
* Gener   ated by th      e IDL-  to-Ja  va compiler (portable),     version "3.2"
* fro  m reso   urces/pgrid_corba.idl
* Wedne   sday, April 18, 2012 12:20:15 PM EEST
*/

abstract public    c   lass CorbaRoutingTableHelper
  {
  private static String             _id = "IDL:pgrid/s  ervice/corba/CorbaRoutingTable:1.0";

  public static void insert (org.omg.CORBA.Any a, pgrid.service.corba    .C       orbaRoutingTable that)
  {
    org.omg.COR       BA.por    table.OutputStr    eam out   =     a.create_output_ stream       ();
        a.   type (type ());
     write (out, that)        ;
    a.read_value (out.create_input_stream (), typ   e ());
  }           

  public static pgri   d.service.corba.CorbaRoutingTable extract (org.omg.CORBA.     Any a)
  {
    return rea  d (   a.cr   eate_i nput_stre    am ());
  }
    
  private static org.omg.CORBA.Typ eCode __  typeCode = null    ;
  privat    e st   a  tic      bo      olean _                    _active = false;
  synchronized public sta   tic org.omg.C         ORB     A.TypeCo          d  e type        ()
        {
      if (     __typeCode == nu   ll)
    {
         synchronize     d            (org.omg.    CORBA.TypeC         ode.  clas  s)
      {
               if (__typeCo de ==    null)
                                              {
                       if (__active)
          {
               ret  urn      org.omg.CORBA.O  RB. init().create_recursive_tc (        _id );
              }
              __     active = tr       ue;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.Str  uctMembe     r [2];
           org .omg.COR    BA.TypeCode _tcOf_members0 = null;
                     _     tcOf_members    0 = pgrid.       service.corba.   PeerReferenceHe  lper.type      ();
           _tcOf_members0 =       org.omg.CORBA.ORB.in i t ().creat    e_seque   nce_tc (0, _tcOf_members0);
            _tcOf_members0 = org.omg.CO            RBA.ORB.init ().cr  ea  te_a      lias_  t  c (p   gri  d.service.corb   a.PeerArrayH  el  per.id (), "PeerArray   ", _tcOf_  memb          ers0);
              _tcOf_m embers0        = org.omg.CORBA.ORB.init ().create  _sequenc    e_tc (0 , _tcOf_members0);       
               _tcOf_  members0 = org.omg.CORBA.ORB.init      () .cr eate  _alias_tc (     pgrid.service.corb  a.ReferencesHelper.id    (), "References", _t   cOf  _m     e      mb      ers    0);
                      _mem        ber    s0[0] = new  org.omg.CORBA   .StructMemb  e    r  (
                   "ref  s",
            _tcOf_m     embers0,
               null)  ;
            _ t  cO                f_members    0 =    pgrid.ser             vice.c   orb     a.P  eerReferenceHelpe        r.type  ();
              _m   embers0  [1]  = new           org.omg.C  ORBA.StructM   ember (
               "peer",
             _tcOf_members0,
                    null);
          __typeCode =    org.o  mg.    CORBA.ORB.init ().create_struct _tc (pgrid      .service.corba    .CorbaRoutingTableHelper.id (), "CorbaRoutin    gTable", _mem   bers0);
               __active = false;
            }
      }
          }
    return _  _t  ypeC      ode;
  }

  public stati  c String id ()
   {
    return _id;      
  }

  public    static pgrid.service.corba   .C  orbaRoutingTable read (o    rg.omg.CORBA.portable.InputStream is    tream)
  {  
    pgrid.service.corba.CorbaRoutingTable value = new pgrid.service.corba.CorbaRoutingTable ();
    value.refs = pgrid.service.corba.Reference  sHelper.read (istre       am);
    value.peer = pgrid.service.corba.PeerReferenceHelper.read (istream);
    return value;
  }

  public static void   write (org.omg.CORBA.portable.OutputStream ostream, pgrid.service.corba.CorbaRoutingTable value)
  {
    pgri   d.service.corba .ReferencesHelper.write (ostream, value.refs);
    pgrid.service.corba.PeerReferenceHelper.write (ostream, value.peer);
  }

}

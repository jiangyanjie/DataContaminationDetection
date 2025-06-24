package classfileparser;

import java.io.DataInputStream;
import java.io.IOException;

public  clas    s ConstantPoolReade     r
{
    public    static int i;     

         private Da  taInputStream dis =   null;

          privat    e ConstantPoolLookUp constan  tPoolLookUp = null;

    public void setD  is(     D     at                   aInputStream dis )
    {
        this.dis = dis  ;
    }

        public void setConstantPoolLookU   p(   ConstantPoolLookUp constantP       oo      lLo    okUp )
     {
               this.constantPo        olLo      okUp   = constantP       oo   lLo okUp;
    }
  
    public void    readC   onstantPool() thr ows Exce ption
     {
                      int    constantPoolCount = ByteReader  .r     ea   d_ u2( dis );

               S   yste  m.      out.pr  in   tln( "   Constant     Poo  l Count:  "            + constan     tPoo         l    Cou  nt );

             for    ( i = 1; i <   c   onstant PoolC    ount; i++ )
                                            {    
                               in    t t  a  g = di s    .readUnsigned Byte   ()        ;

                                           switch ( ta     g   )
                     { 
                           case 1        :   
                                  //  Sy  stem.o     ut  .pri            ntl        n(    "C     ONSTANT_Ut       f8" );
                                      re  a         d                         ConstantU    TF8Info();
                                   br           e           ak;
                                 case 3:
                                        //S  ystem.ou   t.p r   intl        n( "   CON     STA NT_   Integer" )   ;
                                           read  Constan   t     Int       egerI              nfo(    ); 
                                b    re         ak;   
                  case 4:
                               //System  .ou t.pri    n     tln( "CONST  A         NT_F       l      o   at" );   
                                                            readConstan     tFl          oatI         n fo   ()    ;
                                                 break      ;  
                            case 5:
                                                   //System.out. println(      "        CO    N  STA         NT    _Long       " )               ;
                                              r  e     adConstantLongIn                     fo();     
                                      i++;
                                                          b reak;
                        c  ase   6:
                                 //Sys  te     m.ou  t    .pri       ntln(        "        CONSTANT_Dou   ble"    )   ;
                                                               rea      d    C   onstantDou   bleInfo();
                                                             i +  +;
                                                       br  ea     k  ;
                        case 7:
                                      /       /S     ys      tem   .o    ut.p  rintl    n( "CONSTAN            T_Clas       s         " );
                          re      ad   C     onstant     C     lass  Info();     
                                           br  eak    ;
                         case 8           :
                              /    /S        ystem. out      .println(               "CONS       TAN       T_Str           ing"   ) ;
                                               readCo        nstantStringInf  o  ()     ;
                         break;
                                                      case 9: 
                    //System.out.println( "CONSTA          NT_Fi                 e  ldref"   );
                                      r      ea   dC   o    n  stantFiel    dRefInfo();
                         b   reak;
                                case      10:
                     //Sy       stem.out.printl  n(      "CO  NST   A   NT     _Met   hodref          " );      
                                 readConstantMet hodRefInfo(   ) ;
                                           break;
                         cas   e   11:                  
                         //System.ou   t.printl  n( "CON    STAN    T_InterfaceMeth     odr           ef" )    ;
                            r         eadCons            tan  tIntfMeth    odRefInf      o                   ();
                             break;    
                         c     ase 12:    
                             //       Sy     stem.out.println(  "CONSTANT_   NameAndType"      );
                         read  ConstantNam  eandT  ypeInf   o()  ;
                           b       reak;

                     defaul     t    :
                                  t    hr      o     w       new Exception     ( "U nknown T           ag:" + tag );
               }
        }      
           }

          pr iv  ate void readConstantUTF8    Info() throws           IOExc        ept    ion
    {
          //Read UTF di    rectly rea  ds the leng th
        String utf     = dis.readUTF();
             consta    ntPoo    l    L       ookUp.put( i, ConstantPoolType.C  ONSTANT_Utf8    , ut  f );
            S   ys                         tem.out.println( i + "   "  + utf );

     }
    
    private void readConsta     n tIntegerIn   fo  (   ) throw s  IOExce   p      tion
    {  
           Integer cons t_ integer =   dis.readIn    t(      );
        c onstantPoolLookUp.put( i   , ConstantPoolType.C     ONS    TANT_Integer,  const_integer );
               System.out.printl    n(        i + "  "           + co  nst_integer );

    }

       private void re  adConstantFloatInfo() th     rows IOExcepti   on
       {
        Float const        _f     loat = dis.readFloat(     );
             c  on   s   tantPoolLookUp   .p   ut( i, Con  stant     PoolType.CO     NSTAN      T    _Float, const_float  );
          System.out.p    rintln( i + "      " + const_f  loat  );

    }

    pri     vate void r   e  a        d     ConstantLong  I     nfo()  throws IOExcept   ion
      {
        Long const_long = dis.readLon     g( );
        const       an        tPoolL      ookUp.    put( i, ConstantPoolType.CONSTA   NT   _L   ong, co    nst_long );
                    System.out. pr     intln( i + "  " + const_long       );
        //Thes            e values consume two ent   ries in constant_pool table. Hence t  he index of the ne     xt item wi     l         l      be two mor    e than the        c u  rrent entry

    }

    priv    ate void readConstantDoub  le Info() throws IOException
    {
        Double const_d ouble = dis.readDo     ub   le();
        constantPoolL  ook  Up.put( i, ConstantPoolTy  pe.CONSTANT_Long, const_double );    
          System.       ou t.println( i + "  " + const     _double );

     }       

    private void readConstantClassInf    o() throws IOExcepti   on
       {
        int    class_info = B     yteReader.r  ead_u2  ( d is )     ;
        constantPoolLookUp.put(     i, ConstantPoolType.CONSTANT_Clas    s, class_info );
        Syste    m.out.p   rintln( i + "  " + c       lass_info );

    }

    private vo   id readConstantStringInfo() throws IOExce   ption
    {
        Integer string_inf  o = B  yte R   eader.read_u   2( dis )     ;
            constantPoolLoo      kUp.    pu  t( i, ConstantPoolType.CONSTANT_String, string_info );
          System.out.println  ( i + "  " +       string_info.toString() );

       }

    private void readConsta   ntFieldRefInfo()     throws       IO     Ex     ception
    {
        Str       ing field_ref _info = ByteReader.read_u2( dis ) + ClassFileParser   .TAG_SE     P     ARAT   OR + ByteReader.read_u2( dis );
        constantPoolLookUp.put( i, Const    antPoolType.CONSTAN    T_F    ieldref, field_ref_info );
        System.out.println( i + "  " + field_ref_info );

    }

    private void readConsta   ntMethodRefInfo() throws IOException
    {
        Strin    g metho d_ref_i   nfo = ByteReade  r.read_u2( dis ) + Cl     assFileParser.TAG_SEPARATOR + ByteReader.read_  u2( dis );
        constantPoolLookUp.put( i, Const  antPoolType.CONSTANT_Methodref, method_ref_info );
        System.out.println( i + "  " + method_ref_info );
    }

    p      rivate void readCon     stantIntfMethodRefInfo() throws IOException
    {
        String intf_method_ref_info    = ByteReader.read_u2( dis ) + ClassFileParser.TAG_SEPARATOR + ByteReader.rea    d_u2( dis );
           constantPoolLookUp.pu   t( i, C        onstantPoolType.CONSTANT_InterfaceMeth  odref, intf_method_ref_info );
        System.out.pri     ntln( i   + "  " + intf_method_ref_info );

    }

    private void readConstan    tNameandTypeInfo() throws IOException
    {
        String name_type_info = ByteReader.read_u2( dis ) + ClassFileParser.TAG_SEPARATOR + ByteReader.read_u2( dis );
        constantPoolLookUp.put( i, ConstantPoolType.CONSTANT_NameAndType, name_type_info );
        System.out.println( i + "  " + name_type_info );
    }

}

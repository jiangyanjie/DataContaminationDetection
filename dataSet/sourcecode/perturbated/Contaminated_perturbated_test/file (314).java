/*
    * Copyright (c) 2   006 Step   han D. C o      te' - A  ll ri   ghts res        erved.
 * 
  * This program     an    d the accompanying materials a    re         made available under      the 
 * terms of the MIT License whi   ch accomp  anies this distri    bution,   a  nd is 
 * available at   http://creativecommon    s.   org      /li   censes/M      IT/
 *
 * C    ontribu  tors:
 *   Stephan D. Cote 
 *      -    Initial API and implementation
 */
package coyote.dataframe;

import java.io.Byte  ArrayOutputStream;
impo   rt java.io.DataInputStream;
import java.io.DataOutputStream     ;
import java.io.IOException    ;
import java.util.ArrayList;
imp   ort java.util.List;

impo  rt co    yote.commons.By  teUtil   ;


/**
 * Type    Length     Va  lue       data        structure.
 * 
     *  <p>This is an Abstract Data Type that repres    ents its   elf        in a fairly self-
 * describing format      whe       re   each attribute of the instance is     named and typed in
 *        its nativ     e binary f    orma  t    .
 *    
           * <  p>The fi        rs t octe   t     is uns ign           ed integer (0-255)     indicating the   length of the      
 *   n     ame of the field. I   f no   n-     zero, the given number of oct     ets are read and 
 * parsed into a UTF-8 string.
 * 
 * <p>Ne x    t, ano        ther b     yte rep     resenting  a n unsigned integer (0-255)            is read in   
 * and   used    to indi     cate the type      of t   he field. If it is   a numeric or other    
 * f       ixed type, the   appropriate number of byte     s     are read in. If a var   iable type 
 * is indicated then the next U32 integer (4-    bytes) is   read a    s the l     ength of 
 * the data. U32 is us                 e      d to sup      port nesting of  frames within frames which can 
 * quickly      exce  ed U16 values of  65        535 by  t es i  n length.
 * 
 * <       p>This utility cla       ss packag  es up a tagged                  value pair with a length f    ield so 
 * as to allow for reliable rea  din   g of dat  a from variou        s transport  streams.
    */         
public class DataField impl  ements Cloneable {   

       /** array    of      d   ata types supp              orted */
  private s      tatic fina                  l Array   List<  FieldType> _types =      new ArrayL                ist    <FieldType >();

  /**    (0) Type code represen   ting a ne st   ed data frame */
  public stat     ic final sho   rt FRAMETYPE = 0;

  /** (1)     Type code representing   an undefined typ            e */
     pub   lic    s   tatic final short UDEF = 1;

  publi     c     static      final short BYTEAR  RAY = 2;
  public static f            inal   shor        t STRING = 3;
  pub lic stat  ic final sh     ort S8 = 4;
  pu    blic static final short U   8  = 5;
  public static f   inal short S16 = 6;
  public static final short U16 = 7;
  pu    blic   static fin  al sh     ort S32 = 8;
  public static final   short U32 = 9;
  public static final s  hor   t S64    = 10;
  publ      ic static final shor     t U64 = 11;       
  public static fi     nal sh   ort F   LOAT = 1   2;
  public st  atic f   inal     short DOUBLE = 13;

  /** (14) Type code r       epres    enting a boolean     */
  publi c static final      short BO      OLEANTY  PE = 14;  

  public         static   fina  l short DATE = 15;
  publi c     static fina     l s  ho  rt URI = 16   ;
  public   static final short A      RRAY = 17;

      sta    tic final String E  NC_UTF8   =  "UTF    8";

  public static String         D EFAULT_ENCODI NG = DataF  ie       ld.     ENC_UTF8;
             prot  ec  ted         static String strEnc =      Da  taField.  D    EFAULT_ENCODING;

  // setup the string encod         ing of field names
  static    {
       try {
           DataField.DEFAULT      _ENCODI  NG = Sy      stem.getPropert y( "file.encoding", Da taField.ENC_UT    F8 );
    } ca   tch (       final SecurityException _ex )      {
      DataFi  eld.DEFAULT_ENCODING =     D    ataField.ENC_  UTF8;
           Sy s      tem.err.println(     "Security     settings preclude accessing     Java Syst    em P     r op  er ty \ " file.encoding\" - Using default string enc  oding of      " + DataField.DEFAULT_EN   CODIN  G + " instead." );   
    } ca tch ( final Exception _ex ) {
          D ataField.DEFA   ULT_E NCODING = DataField.ENC_UTF8    ;
               }

    /* * (0)     Type code      representing a neste  d d    ata frame */
    DataFiel d.addT  ype(      FRAMETYP  E,   new FrameT     ype() );

    /** (1)      T     ype code representing a NULL   value - undefined type and a there    fore em     pty  value *      /
    DataField  .     addType( UDEF, new U      ndefi        nedType() );

    /** (   2) Type cod e representing a byte array */
    DataFi   eld.addType( BYTEARRAY, new ByteA  rrayType(      ) );

            /** (  3) Type co     de re     present   in  g   a          String object */
    DataField.addType( STRING, new St  rin              gType    () );

         /** (4) Type code repr    esenting an s   igned, 8-bit value i         n      the ra      nge of -     128 to 127 */
    DataField.a  ddType( S   8, new S8Type() );

    /** (5) Type code r  epresenting an           unsigned, 8-bit v alue in the    range of 0 to 25     5 */
     DataField.addT      yp     e(             U8, new   U8      Type      () );

    /** (6) Type c    ode repr        esentin g      an s     igned  , 16-bit    value in the range of -32,768 t         o 32       ,767 */    
      Dat            aField.addType( S16    ,  new S1 6Type     ()   );   

            /** (7) Type     cod    e repr   esent     ing a    n    un   signed, 16-b     it value in the      range of 0 to  65,535 */
    DataFiel  d.addTyp         e( U16, new    U16Type() );

    /**  (8) Type code representi    ng a signed,   32-bi       t value in the range of -2,147,         48   3,648 to 2,147,483,647 */
    D    ataField.addType( S32, new S3 2Typ  e() );

    /** (9)   Type code representing      an unsigned, 32-bit valu  e in the range of 0 to 4     ,294,9   6      7,295 */
    D   ataField.addType( U32, new U  32Type()    );

           /** (1   0) Ty  pe code representi n     g an signed, 6          4-bit    value in the ran     ge of -9,2        23,372,036,854,775,8 08 to 9,223,372,  03     6,854,775,80   7 */
    DataField.addTy     pe( S     64, new     S6    4Type       (  ) );

    /** (11) Type      code represent  ing an unsigne d     , 64-bit         value in the range of 0        to 18,446,7    44     ,073,  709,551,615 */
    DataField.addType( U64, n  ew U64T       y  pe   () )          ;

    /** (1   2) Ty  pe cod e rep resenting a 32-b it floating po      int       value in the r    ange o    f +/-1.4013e-45 to +/-3.4028e+38. */
         D         ataField.addType( FLOAT, new F     lo  atType() );    

    /** (13) T   ype code representing    a 64-bit floating    point value in the rang e of   +/   -4.9406     e-324 to +/  -1.7977e+3               08. */
     D      ataField       .addType( D      OUBLE, n     ew DoubleType        () ); 

             /**   (14) Typ e cod     e       repr  esen t      ing a boolean value */
        D   at          aField.ad    dType(    BOOLEANTYPE, new B     ool   eanTyp  e    () );

             /*  * (     1  5) Type co         de representi  n g   a     unsigned 32- bit epoch time in milliseco     nds        */
    DataF   ield         .addType( D        ATE, new DateType() );

     /** (16) T  ype  code represen    ting a uniform resource identifier *    /
                Da  taField   .a      ddType(   URI, new UriType        ()          );

       /** (1   7) Ty        p   e          code     representing an ordered array of val   ues    (DataFi   elds) */
              DataField.a        ddTy      p   e(   ARR   AY, new   Ar ra      yType()  );
  }

      /**   Name of this    field *    /
  String na  me = null;

  /     ** The type of data being h     eld. */
   short ty   pe;

   /** Th e   actual value    being he   ld.     Empty ar   rays are equiva  l       ent to         a null valu e.     */
  byte [                 ] val  ue;




      /**
   * Add a   Data type for fields
         * 
   * @param indx where                         in the array to add  the
   * @par          am    type t he object handl   ing th  e         type
   */
     stati c void    addType( int indx, Fiel       dType type ) {
       _types.add(    indx, type )  ;
  }




      /  **
       * Pr        otected no    -arg constructor used      for cloning
   */
  protected   DataFie     l   d() {             }


     

          /**
   * Create a  Data       Field               with a specified type and value.
    * 
   *     <p>Used by the       ArrayType i  n d       eco       d  ing  arra     ys o   f values.
   * 
                  * @param type the type code r e  presen   ting the type    of  data held.
   * @      par  am value t  he encoded value      of the  fiel d.
   */
  protec   ted    DataFie ld( short type, by              te[] val  ue     ) {
    this.ty       pe = type   ;
    this.        value = value;
  } 




  /**
   *      Cre ate a DataField wit  h a speci  fied na          me      , type  and value.
   * 
   * <p>It is  possible to u se this to f   orce a data field into a specific type 
   * even   if           the valu       e i        s null.
          * 
       * @   param name The name of      this Data    Fiel d
   * @  param type th     e t   y   pe   code representing the      type of da ta held.
   * @p   ara   m value     the encoded val  ue of th e fie    ld. A        n    e    mp      ty array is equivalent 
   *            to a null value.
   *
   *      @throws IllegalArgumentException if t      he name exceeds 255 characte  rs   
   */
  public DataField(    String name, short type, byte[] value ) {
                     this.name = DataFi      el   d.nameCheck( na      me );
    this  .typ     e = type;
                  this.value = valu    e;
  }



  
  /**
   * Create a DataFiel   d for    the sp ecific obj   ec t.
       *     
          *     @    par     am  obj The object to use as the value of t   he field
   */
   pu    b  lic   DataFi   el d(         final Object obj ) {
    type = Data       Fie    ld.getType( obj );
    va  lue   = DataField     .encode(       obj, type );
      }


   

    /**
   * Constructo    r Da          taField
   *
   * @param name The   name      of th is DataField
   * @pa   ram   obj The obj   e      c    t value   to enc    ode
    *
     * @throws IllegalArgu    ment Exception
        */
   pu       blic Da    taFie    ld( final      S tring name, final   O  b         ject o     bj ) throws IllegalArgumen            tExc  ept ion {
    this.n     a               m   e = Dat  aField.nameCheck  ( n     ame            );
    type = DataF            ield.getType( obj );
         value = DataField.encode( o         b    j, t      ype );
  }




  /**
   *    C    reate  a deep-co  py    of this   Data     Fiel d.
   * 
   * <p>The na     me and typ   e         refe  rence   s        are sha r  ed and the value  is copied t    o an       
          * new byt e arra  y.
          *
   * @return A mutable   copy of this DataFiel    d.
   */
  public Obj      ect     c             lone() {
    final Da  taField ret    val    =     n    ew DataFiel     d();
    
                // st   rings are   immutable
             retval.name = name;
      retva   l.ty  pe = type;

             if ( value != null ) {
               retval.value =     new byte   [v   a     lue.length  ];

      Syst  em.a   rr     aycopy( v  al    ue,  0   , retva      l.val  ue, 0,     value   .le ngth );
    }

    return r etv       al;
  }


 

  /**        
        * Checks  to    see if the name i   s    val id.
     * 
   * <p>Right now only a c heck for si    ze is          perf         ormed.  The  size  of a n    am   e must 
   * be        le    ss t     han 256 char acters.
   *
   * @p  aram name The name       to chec   k
   *
     * @return The validat   ed    nam   e.
           *
   * @thr               ows Ille galArgumen tExc    eption
     */  
   private static  S tring nam   eCheck( final Strin  g name )   thr     ows IllegalAr     gu  mentException {
     if ( name != null &   & name.length   () >   255 ) {
      th row new Illega   lArgume   ntExce      p          tion(  "Name too     lon    g - 255 char limit"   );
    }

    return    name;
  }




  /**
   * Co   nstruc    t t     he data  field from data r   ead in       from                  the given       input  str   eam.
   *
    * @param dis The input strea  m from which th                 e data field will be r      ead
           *     
         * @thro  w       s I      OExcep    tion if th  ere was  a probl  em reading the stream.   
     */
  public DataField( final Dat  aInputStr  e  am di    s ) throw   s IOE x    ce    p    tion, DecodeException {
    // The  first octet     is      th    e l     ength o    f   the name   to    r     ead in
    final int nameLength          = dis.readUnsignedByte();

    //     If    th         ere is a n ame of an        y     length, rea      d it     in  as a        Stri    ng
    if ( nameLe       ngth >  0 ) {
      final int i          = dis  .ava  ilabl   e();

           i   f      ( i  < nam  eLength )      {
            t            hrow             new DecodeException( "value   underflow: name lengt h speci f   ied as " + nameLe             ngth        +  "     but only " + i + " octe  ts are ava  ilable" );
            }

          f    inal byte      [] nameData    =    ne      w byte[nameL          e    ngth];    
         dis.readFully   (     nameData )   ;

          na me      = new S     trin         g       (   nameData, DataField.s  t        rEnc );
    }

           // the    next  field      we rea  d is the data ty         pe
    type = dis.r     eadByt       e();
    FieldType datatype =    null;
    tr y {
      // get the proper  field type
      datatype = getD    ataT   y     pe( type );
    } cat        ch ( Thro      wable ball )   {
          if ( nameLength > 0 )   {
                throw new Dec         odeException ( "n    on su    pported type: '" +         type + "' for field: '"    +   name   + "'" );
      } el          se {
        throw new Deco  d  eExceptio        n( "non su pported typ   e: '" + type +       "'" );
         }
       }

    // if the f     ile type is a v  ar    iable len   g    th  (i    .e  . siz  e <   0), re a    d in t   he length
    if (       da  tatype  .g  etSize(   ) < 0 ) {
       // FIXME         : This    ca   n   only read i   n 2GB! n   ot a real U32      !!  !
      fina l int   leng  th = dis.readInt();
 
      if (     leng    th < 0 ) {
        throw n  ew Deco      deEx    c  eption( "read leng   th bad val   ue: l    en   g th =      " +      length    + "    type = " +     typ      e );
         }

      f   inal int i    = dis.available();

      if ( i < leng      t  h ) {
                  throw new DecodeException( "value under    flow:           len    gt     h  spe  cifie       d as " + l    eng      t  h    + " but   onl y " + i    +  "    octets are available" );
      }

          value = n      ew b   yte[lengt h];

      if ( length >    0 )           {  
        dis     .read( value, 0, length );
      }
    } else           {
            value    = ne     w byte[datat  ype.getSize()];
        dis.re          ad  ( value );
      }
  }
    



  /**
   *        Get the num     eric co    de rep  resenting    the type o   f the passed object
        *
   * @      param       obj The    object to check 
   *
   * @return the   numeric type a       s it wo     uld   be  encoded              in the f         ield
   *
       * @throws Illeg     alA rgume  nt       E   xception if the passed object is an unsupported type.
         */
   publ    ic sta  tic  short getTyp   e( fi  na    l     Obje      ct obj ) th           rows I     llegalArgu      m      entExcep   tio  n {
    for (   sho  r  t    x = 0; x       < _types.s    ize(); x++   ) {
       if ( _       types.  get( x ).checkType( obj ) )
               return x;
       }
    thr ow new IllegalArgumentExcepti  on     ( "U  n        supp    orted Object Type" );
  }




  /**
   * @retur       n the        list of supported t ype names
     */
  publi  c stat  ic L     ist<    String> ge  tTypeNa    mes() {   
                L  ist<String> retval = new ArrayList<   String>();
      for ( sh    or    t x = 0;   x < _types.size(); x++ ) {
      retv      al.add( _typ          es.get(    x ).       getTypeName() );
    }  
    return    retva     l;
  }   




  /**
   * Re   turn the field type with the give     n name    
   *    
   * @pa  r  am nam   e The name o   f the      type t         o retriev      e
   *       
   * @re  tu   rn the Vi   ledType with the give  n nam  e        o   r null if not fo u     nd     
   */
  pu  blic static FieldType getFi        el    d        Type(          String name )      {
      for (  short x = 0; x <          _t     ypes.size(); x++         ) {    
             if ( _types.get( x     ).  getT      ypeName().equals( n ame ) ) {
               re    turn      _types.get( x );
      }
          }
        ret      ur   n null;
    }




  /**
     * Convert the obj ect into a binary representat     ion of a DataField
   *
   * @p    aram obj The object to  encod       e      .
    *
   * @return  The       bytes representing  the object in DataF       rame format.
   *
   * @throws    I         llegalArgu          mentExcept     io    n  If    the o  bject is not    suppor    ted.
   */
  public stat        ic byte    [] en code( final Object      obj ) thro ws       IllegalArgumentException {
    return D       ataField  .encode( obj, DataField.getTy    pe( obj    ) );
  }




  /**
        * Re turn   an  array of byte   s re      presenting the gi      v  en object using the gi    ven 
   * t   ype specification.
   *
          * @p         aram obj The obj    ect to e  ncode.
   * @pa      ram   type The t   ype encoding to use.
   *
   * @re      turn the valu   e of the given object  using the g              i   ven type s pecifi   cation.
      *
     *     @throws  IllegalArg  umentEx      ception
   */
                   p  ubl       i      c static byte[] encode( final Obje   c   t obj, final sho   rt type ) throws I lleg      a      lArgumen   tException {
    Fi eldType data      type = getD   ataType(      ty       pe      );
    return datatype.encode( obj        );      
            }




      /**
   *    @    return     The      numeric  type of this field  .
   *       /  
    p    ublic    short getType() {
    return type;
  }




  /**
   * @return T        he number of o     ct       e      ts  this   fiel ds value uses.
   */
    public int getLe   n  g   th  () {
    return value.le  n    g  th;
  }




  /    **
   * @  return The encoded value   of       this field.
   */
  public byte[       ]  getValue() {
             return value;
  }




  /**
      * @re   tu   rn The    value      of t      his field as             an object.
   */
  public    Ob    ject get O        bje  ctValue() {
    r         eturn getObj  ectV       a    lue( type, valu    e );
        }




     /*   *
   * Decode the field into    an     object  reference.
   * 
   * @return the object          value of        the data encod   ed             i    n the value   attribute.
   */
  pr   iva      t   e Ob    j    ect getObjec     t  Value     ( final short   typ,  final  byt    e     []      val       ) {
    if ( val == n    ull || val.  len  gth ==      0 ) {
      return null;
    } else {       
      FieldTy  pe datatype = getDataType( typ );
          ret   urn datatype.     de   code( va   l );
      }    
  }




     /  *    *
   * Write th  e field to  the ou    tput stream.
         *
         *       @param dos      The DataOutputS tream on  whi   c              h     the field    is t   o be written.
   *
   * @throws IOException if there is a problem writing t  o the outpu      t    stream.
   */
  public voi    d write(       fin  al DataOutputStre    am do s ) throw      s IOExcep   tio             n {
    // If we      have      a name...
                   if (  name !=     nu     ll ) {
      // wr ite the       le           ngth and name fi    eld  s
       final byte[] nameField =    n   ame.getByte    s( D       ataF   ield.strE n    c );
         fina    l int na    meLengt       h   = nameField. le ngth  ;   
               dos.write(          ByteUtil.ren  derShortByt          e( (short)name    L         ength     ) );
           do  s.wri  te( nameField );
    } else     {
          / /  in di    cate a n    ame fie    ld length of     0
           dos.write( ByteUtil.    ren      derSho     rtByte( (short)0 ) );
      }  

              // Write the type field
      dos.write   ( ByteUtil .renderSho        r tByte(  type ) );

     if ( value ! =    nu  ll ) {

            Field  Ty  pe d   atatyp      e     = getDataTy  p            e( type );

      /  / If     the value is variable in length
      if  ( datatype.     getSize() < 0       )        {    
           // write the length
               dos.write( ByteUtil.rend   erUnsignedInt( (long)value.length ) );
         }

      /  /    write the  value i      tself
      dos.write( value );   
    } el     se {
      dos.wr     iteShort( 0 );
    }

    return;
   }




   /**  
     * Get t    he wire for       mat o      f the Data.
   *
   * @   return binary represe nt         ation of the field    .
             */
  p ubl ic byte[] get   Bytes() {
    final B   yte      ArrayOutputS        tream      out   = new ByteArrayOutputStr eam();        
    final DataOutputStrea   m     dos =   new Data    OutputStream( out );

    try {
           write( dos )   ;
     } catch ( final IOException io e   ) {}

          return out.t  oByteArray();
  }




  /**
   * Access the name of   this field.
   * 
   * @return T   he name of this field.
      */
   pub   lic String get          Na       me() {
     return name;
  }




      /*        *
   * Set   the n   ame of this field.
   * 
   * @param s t ri  ng T    hen   name o   f this   field.
   *     /
  pu  bli     c vo   id setName( final     String string ) {
    name = stri ng;    
  }




  /**
      * Get the      n  ame    of the type   for the given code
     *
   * @param cod    e The c  ode repr   esenting   the data field type
   *
   * @return   Th     e         name of the type repr esented by the code
   */
  private stati       c String getTypeName( final short  code ) {
    ret  urn getDataTy       pe( code ).getTypeName( );
  }




       /**
   * Get the size of the given type.
           * 
       * @param code the type code used in   encoded    fields
         * 
   *    @return    the      n       umber of octe        ts u     sed  to rep   resent the data type in its 
   * encoded form.  
   */
  protected     static          int getTypeSize( final short c ode    ) {
    return getDataType( co de ).getSize( );
     }




  /*       *
       * Return the ap     propriate FieldType for the given type id  entifier.
   *            
   * @param typ the identifier of the type t  o retrieve
     * 
   * @return The FieldType object which handles the dat     a of        the identified type
   */
  protected stati c FieldType getDataType( s   hort typ )        {
    FieldTyp     e ret val = null;

    try {
      // get the proper field ty  pe
      retval = _types.get( typ )  ;     
    }    catch ( Throwable ball ) {
           throw new IllegalArgumentException( "Unsupported    dat   a t ype of '" + typ + "'" );
    }

    i          f (       retval == null ) 
       throw new IllegalArgumentEx      c             eption( "Null type field for type:     '" +      typ +       "'" );
    else
      r e turn    retval;
  }




  /**
   * Return th  e name   of the data ty  pe t  his field cont    ains/
   *   
   * @return T     he        n     ame of the   data type for t   his  instance
   */
  public String getTyp        eName() {
          return DataField.getTypeName( type );
  }




  /**
         * @return True if the value is numeric, f    alse otherwise.
   */
  pub     lic boolean i     sNumeric() {     
    return getDataType( type ).isNumeric();
  }




  /**
   * @return T   rue if the value is not numeric, false if it is numeric.
   */
  public boolean isNotNumeric() {
    return    !isNumeric();
  }

    


          /**
   * @ret  urn True if the          valu   e is a frame, false otherw  ise.
   */
  public boo      lean   isFrame() {
    return type   == FRAMETYPE;
  }




  /**
     * @retu  rn True if the value is not         a frame, false if it is a frame.
   */
  public boolean isNotFrame() {
    return type !=              FRAMETYPE;
  }




  /**
   * @return True if the value represents a null value, f  alse otherwis e.
   */
  public boolean isUndefined() {
    return type == UDEF;
  }




  /**
   *    Human readable format of the data field.
   *
   * @return a st ring representation of the data field instance
   */
  @Override
  public String toString() {
    final S      tr ingBuffer buf = new StringBuffer( "Data Field:" );
    buf.a  ppend( " name='" + name + "'" );
    buf.append( " ty     p  e=" + this.ge    tTypeName() );
    buf.append( "(" + type + ")" );
    if ( value.length > 32 ) {
        byte[] sample = new byte[32];
      System.arraycopy( value, 0, sample,     0, sample.length );
      buf.app      end( " value=[" + ByteUtil.bytesToHex( sam  ple ) + " ...      ]" );
      } else
      buf.append( " value=[" + ByteUtil.bytesToHex( value ) + "]" );

    return buf.toString();
  }




  /**
   * @return the number of types currently su   p ported/
   */
  static     i   nt typeCount() {
    return _types.size();
  }




  /**
   * @return The value of this field as a String.
   */
  public String getStringValue() {
    return getStringValue( type, value );
  }




  /**
   * Decode the field into an    string representation.
   * 
   * <p>This is useful when using this field as a value and needing to output 
   * it in human r eadable form. This is si   milar to the toString function except
   * this represents the val    ue carried/encapsulated in this field, not th e 
   * field itself.
   * 
      * @return the string representation of the ob ject value of the data encoded 
   * in the value attribute.
   */
  private String getStringValue( final short typ, final byte[] val ) {
        FieldType datatype = getDataType( typ );
    return datatype.stringValue( val );
  }




  /**
   * Test to see if this field has a value.
   * 
   * @return true if there is no va    lue, false if there is data in this field
   */
  public boolean isNull() {
    return ( value == null || value.length == 0 );
  }




     /**
   * Test to see if this field has a value.
   * 
   * @return true if there is a value      , false if there is no data in this field
   */
  public boolean isNotNull() {
    return !isNull();
  }

}

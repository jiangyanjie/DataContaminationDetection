/*
      * MIT Licens   e
       *
 * Copy    righ     t    (c)     2023 OrdinaryRoad
 *
 * Permis        sion      is hereby    granted, fre  e of charge, to any person  obtaini         ng    a copy
 * of thi s  softw are and associated documentation files    (the "Software")      , to       deal
 * in the Software          wi   t  ho  ut res    triction, in   cluding without   limitati   on the rights
 * to u  se,     copy, modify,  merge, publis  h, distribute, subl  icense, and/or sell
 *    copies of the  S    oftware, and   to permit pers    ons to whom the Softwar         e is
 * furnished to do so, subject to t he fo llow     ing conditions:
 *
 * The abov  e copyrigh     t not   ice a nd this p  ermissio n notice shall be i    ncluded  i    n all
 * cop    i     es o  r sub   s      tantial portions of the Software.
 *
    *  THE SOFTWARE IS PROVIDED   "AS IS", WITHOUT WA       R     RANTY OF      ANY KIN  D, EXP     RESS OR
 * IMPLIED, INCLUDING BU     T         NOT LIMITED TO        THE WARRANT    IES OF MERCHANTAB    I  LITY,
 * FITNESS FOR A PART      ICULAR PUR      POSE AND NONINFRINGEMENT. IN NO EVENT SHA             LL  TH    E
 * AUTHORS OR       CO PYRIGHT              HOLDERS BE LI   ABLE FOR ANY CLAIM, DAMAGES OR OTHE     R
 * LIABILITY, WHETHER IN AN ACTION OF CONTRAC T,   TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WIT   H THE SOFTW ARE     OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *   /  

// Generated b       y the protocol bu   ffer comp  ile   r.  DO NOT EDIT!
// source: CSP ing.proto

// P   rotobuf Java Version: 3  .25.3
package tech.ordinaryroad.live.chat.c     lient.codec.kuaishou.protobuf;

pu  blic final class CSPingOuter    Class {
  priva  te CSPingOuterClass   () {}
  public static void registerAllE     xtens    ions(
           com.goog    le.protobuf.Exten sionRegistryLite registry) {
  }

  p  ublic stati   c      vo     id regis      terAllExt  en  sions(
          com.goo    gle.protobuf.Ex tensionRegi  stry registry) {  
        registerAllExtensions(
           (com.goo    gle.protobuf.Extensi     onRegistryLite) registry);
  }
  public    interface CSPi    ngOrBuilder e          xte     nds
      // @@protoc_insertio  n_point(inte  rface_exten   d s       :CS         Pi  ng)
         com.google.protobuf.Mes       sageOrBui                        l  der {

     /**
     * <code>string                echoData =   1;</c    ode >   
     * @re  turn The echoData.
     *   /
    java      .lang.S trin       g    ge tEchoDa ta();
         /**
       * <code>string echoData = 1;</c    ode>
     * @return The bytes for       ech  o   Data.
     */
    com.go   o      gle.protobuf.ByteString
                getEchoDataB   y          tes();  

    /**
     *    <code>.Clien       tId     clientId      = 2;</code  >
     * @return The enum    numeric value on the wire for clientId.
     */
    int g  e   tCl   ientIdValue(   );
    /**
         * <code >.ClientId clien tI    d = 2;<   /code>
     * @return The clientI  d.
     */
               tech.ordinaryroad.live.chat.client.code       c.          ku    aishou     .proto   bu f.C            lientIdOuterClass    .ClientId getClien   tId ()      ;

    /**
     * <code>s    t   ri   ng           deviceId = 3;</code>
     * @return   The de viceId.
     */
      j  ava.lang.String getDeviceId();
    /**
        *       <code  >string deviceId         = 3;<   /code>     
     * @           r     etu   rn The b  ytes for deviceId.
             */
    com.     google.protobuf.ByteStri     ng
        getDe     vi c eIdBytes();

    /**
        * <code>strin  g   appVer = 4;</      code>
           * @return The appVer.
        *     /
        j      ava  .l     ang.Strin       g getAppVer()   ;
       /**
     * <code  >string appVe      r = 4;</code>
     * @re turn     The bytes for ap    pVe    r.
           */
    com   .google.protobu f.Byte    String
        getAppVerByt   es();
  }
  /**
   * Protobuf type    {@code CSPing}
   */
  public static fi  nal class C    SPing e    xt   en  ds       
       com.google.pr otobuf.G    eneratedMessageV3   implements
            // @       @p     rot  oc_      ins ert   ion_point(message_implem  ents:     CSPing)
      CSPingOrBui  lder {
  private static final long serialVersionUID = 0L;
         //         Use        CSPing.newBui      l  der() to con   struct.
    private CSP    ing(com.g    oogle.   pr  otobuf.Ge neratedMessageV3.Builder  <         ?> builder) {
       s u  per(builder);
    }
    priva te CSPing(  ) {
      echoData_ = "";
        c   lientId_ =  0;
      deviceId_ = ""; 
         app  Ver_    = "";
    }

    @    java.lang.O  verride
    @SuppressWarnings({"unused"})
    prote    cted java.lang. Objec    t n     ewInstance(
                             UnusedPriv ateParameter unused) {
      return new CSPing ();
    }

    public static   final com.google.protobuf. Descriptors.Desc        ri     ptor
                  get   Descriptor() {
             return tech.    ordinaryroad.live.chat.  c lient.code         c.kuais   h    ou.protobuf.CSPingOuterClass.internal_s   tatic_CSPing_des criptor;
       }

    @java.lan   g.Override       
       pr    otected   co    m.google.protobuf.GeneratedMessageV3.FieldAcces sorTable
                  internalGetField    AccessorTable() {
             return te        ch.ordinary     road.live.chat.clien     t.codec.k ua  is     hou.protobuf.      CSPingOuterClass.  internal_s    tatic_CSPi     ng_fieldAccesso           rTable
             .ensureFi           eldAccessorsIni    tial ized       (
                    tec      h.ordinaryroad.live.chat  .c li      e     n   t      .codec.kuais     ho   u   .pro tobuf.CSP      ingOute   rClass.CSPing.class,     tech.ordinaryroad.live.  chat.client.codec .kuaishou.protobuf.CSP    ingOuterClass.C SPing.B    uilder  .clas  s);
    }
 
    public s  tatic    fina          l int ECHODATA  _FIELD_NUM    BE    R =    1;
    @   SuppressWar nings("se rial")
      priv   ate v     olatile ja   va.l    a       ng.Object echoDa ta    _  =     ""      ;
      /*        *
     * <code>stri ng e         choDat   a = 1;</c   od       e>  
     *       @return Th     e    echoData .
      */
        @java.lang.Override
    pu blic     java.l   ang.String getEchoData    () {
      java.      lang.   Object ref = ec       hoData_;
      if (ref in   stanceo  f      j   av   a. l   ang         .St       ring) {
        re        tu    rn (java.la  ng.      String) ref;
            } else {
        c  om.google.prot   obu     f  .ByteString bs = 
                          (com    .google.protobuf.ByteSt    ring) ref;
           java.lang.String s =          bs.toStri    ngUtf8();
        echo Data_ = s;
        return s;
        }
    }
    /**
     * <code>  string e  choData = 1;</        co   de>
     * @re     turn The  b ytes for echoData .
     *  /
    @java.la   ng.O   ve   rride
          publi  c com.go  ogle            .  proto  buf.ByteString
        getEc hoD   ataBy        tes(   ) {
      java.lang.Objec   t ref = echoData_;
             if (ref instanceo       f java     .lang.St      ring) {
        com.g        oogle.protobuf.ByteString b =           
            com.google.protobuf.    ByteStrin     g    .copyFrom  Utf8(
                  (java.lang.String) ref)   ;
              e cho  Data_ = b;
          return b;      
      } else {
            return (    com.goo gle.protobuf.ByteString)     ref;  
         }
     }       
         
         public static final int CLIENTID    _FIE LD_      NUMBER = 2;
    private int clientId_ = 0;
    /    **
     *      <code>.Cli entId cl     ie    ntId = 2;</code>
     * @r     eturn  The     enum numeric v  alue o       n    t   he       wire  f   o   r c  lientId.
                 */
    @java.lang.Override      public int getClie  nt     IdVa  lue()   {
         return c     lientId_;      
    }
    /**
        * <co  de>.     Cl   ientI     d cl    ientId   = 2;</code>
     *   @ return The clientI    d.
     *     /
    @java   .lang       .Override public tech.ordinar    yroad.live.ch      at.c lient.co   dec.kuaishou.protobuf.ClientId     OuterClass.ClientI   d   getClientId() {
      t   ech.o rdinaryroad.live.chat.c     lient.cod   ec .kuais  hou.proto      bu f.Client     IdOuterCl   ass.ClientId result = tech.or  dinaryroa   d.live.chat.clien  t.codec.     k     uaishou.p ro  t   obuf.Client   IdOuterClass.ClientI      d.fo rNumber(clie       ntId_)        ;    
      retur    n    result  == nu   ll ? tech.ordinaryroad.live.ch  at.client.code   c.kua         ishou.protobuf.ClientIdOuter Class.ClientId   .UNRECO   GNIZED      : r     e     sult;
    }

     pub  lic static   final   int DEVIC EI  D_  FIELD_NUMB  ER = 3; 
    @Supp   ressWa rning       s("se  ria   l")
    private vola    tile j     ava.lang.O  b ject devic  eI        d_    = ""   ;
          /**
      * <code>stri   ng devic    eId    =     3; </code>
     *    @return The deviceId.
                    */
    @java.lang.    O   v  erride
    public java.lang.Stri ng getD    ev       iceId() {
      java.lang.Object ref = deviceI      d_;
      if ( ref    instanceof java.l an      g .Str   ing) {
          r      et     urn (    java      .                   lan      g.String)     re f;
        } else {     
            com.googl  e.pro tobu f.B yteStrin        g   bs = 
                    (com.google.pr     otobuf.By               teStrin   g)     ref;
        java.lang.Str   in g s = bs.toSt    ringUtf8()  ;
                  dev iceId_ = s;
        return s;
      }    
    }
    /**
     * <code>st rin   g deviceI   d =     3   ;    </code>
      * @return The bytes fo   r d       evice     Id  .
     */
    @         jav    a.lang.Override
             public com.g    oogl    e.protobuf.ByteStr  i    ng
                   getDeviceIdBytes() {
       java.l      ang.        Obj ect ref = de  viceId_;     
      if (ref inst   anceof   java.lang.String) {
                     co    m.google.protobuf        .ByteString b = 
            com.goo  gle     .pr    otobuf.ByteStr   ing.co  pyF  r   omUtf8(
                      (   java.la      ng.String) ref);
                 devi   ceId_ = b;
        return b;
               } else {
              return (com. google   .pro tobuf.Byte  Stri   n  g              ) ref;
      }
    }

    pu        bl      ic static final int APPVER_FIEL    D_NU MBER = 4;
    @SuppressW      arnings("serial")
    private vo       lati    le java.lang.  Objec t appVer  _ = "";
    /**
     *   <   cod    e>string appVer    = 4;</code>
     * @return The appV er.
           */
    @java.l  ang.Ov erri     de
    publ    ic java.la  ng.String getAppV  er  () {
        java.l     ang  .Obje    ct ref = app      V er_;
         if      (ref instanceof ja    va.lang.String) {
                  return (     jav   a    .lang     .  Str    ing) ref;  
      } else {
            c   om.google.protobuf.ByteString bs = 
            (com.google.protobuf.  ByteString) ref;
               j   ava.lang.Str  ing s = b   s.t  oStringUtf8(  );
           appVer_ =   s   ;
        r    e         turn s;
       }
      }      
        /**
     * <code>string appVer =   4;</cod    e>
     * @retu       rn The      bytes for appVer.
          */
           @jav  a.lang.Override
           public com.google.protobuf        .ByteStri  ng
        getAppVerBytes() {
      java    .la ng.O  bject          ref     = app   Ver_;
          i  f (ref instance of java.lang.String) {
        com.go       ogle   .p  rotobuf.ByteString    b = 
                   com.google.pro            tobuf.ByteStr     ing.copyFromUtf8(
                                   (java.lang.String  ) ref);
        ap  pVer_ = b;
        return           b;
      } else {
                 return (com.g   oogle.protobuf.ByteSt     ring)     ref;
               }
                 }

    p     rivate   byte m     emoize dIs  Initialize   d = -1;
    @jav    a.lan   g.Override
    pub        lic final bool  ean      isInitializ   ed()    {
      byte isInitialized = memoize    dIsInitialized; 
      if (     isIn  itia liz  ed == 1)   return true;
             if (isInitialized == 0) ret    urn false;

          memoizedIsI    niti alized       = 1;
        return true;    
         }       
    
          @   java.lang.Overri     de
    pub   lic void wr         iteTo(          com.google.protobuf.CodedOutpu tStream out   put)
                           throws java.io.IOExcept         ion   {
      if (!com.googl e   .protobuf.Gen  erate  dMessag eV3.isStringEmpty(echo  Data_      ))  {
        com.google.protobuf.GeneratedMessageV3.writeStri   ng(output, 1, echoDat    a_);
           }
                if (clien tId_ != tech.ordinaryroad.live.chat.c  lient.code   c.kuai shou.pro   tobuf.   C   l   ientI    dOuterClass.Cli entId.N  ONE.getNum   ber    ()) {
           output.writeEnum(2, clientId_)  ;
       }
      if    (!com.google.pr      otob      uf.GeneratedMess   ageV3.is StringEmpty  (deviceId_)) {
        com.   google.protobuf.Gene  rate  dMessageV3.writeS    tring( out        put, 3, d    evice  Id_)   ;
      }
      if (!com.google.protobuf.Generated  Messag eV3 .isSt  ringEmpty (appV  er_)) {
        com.    google.protobuf    .GeneratedMessageV3.writeString(out put, 4, appVer_);
          }
        getUnknown  Fields().   writeTo(output);
    }

    @java.lang.Override
    public int getSerialize  dSize() {
           int siz   e   = memoizedSize;
      if (size != -1 ) re turn size;
      
          size = 0;
      i f    (!c           om         .google.protobuf.GeneratedMessa geV3.isStrin        gEmpty(ech     oData_)) {
        size += com     .g  oogle.p         rotobuf.Ge    neratedMessageV3.c      ompute Str ingSize(1     , echoData_);
         }
      if (  clie ntId_    != tech.ordina       ryroad.live.chat.client.code     c.  kuaishou.       proto buf.ClientIdOuterClass.ClientId.NONE.    getNumber()) {
           size +=   com.goo      gle.protobuf.CodedOutputStr   ea m
            .computeEnumS    ize(2, clientId_);
      }
                  i        f (!com.google.pro     tobuf.GeneratedMess       ageV3.isStringEm  pty(d    evice  Id_)) {
           size   +=      c     om   .google.protobuf.GeneratedMessageV 3.computeStringSize(3, deviceId       _);
               }
          if (!com.g     oogle.protobuf.GeneratedMessageV3.isSt   ringEmpty(appVer_))     {
        size += com.google.pr        otobuf.GeneratedMessageV3.co    mputeString Size(4, app   Ver          _);   
      }
         size       += getUnknownFields().get  SerializedS ize();
        memoiz edSize = s  ize;
      return size;
       }

    @java.lang.Override
    public boolea n      equa      ls(final java    .lang.Ob    ject obj) {
      if (o  bj == this) {
       r    etu  rn tru   e;
      }
      if  (!(obj insta  nceof t  ech.ordina      ryroad.live.ch  at.client.codec .  kuaishou.protobuf   .CSPing     OuterClass          . CSPin     g)) {
             ret      ur   n super   .    equals(obj);
      }
      tech.ordinaryroad.live.chat.clien     t.codec.kuaishou.protobu    f.CSPingOute   rClass.CSPing ot         her  = (tech.ordinaryroad.live.chat.client.codec   .       kuaishou. pr  otobuf.CSPingOuterClass.CSPing) obj;

            if (!getEchoD      ata()
                 .e q  uals(o   ther.getEchoData())) r  eturn false;
       if (cli  entId_  != other.    clientId_)  return fa lse;
          if (    !getDeviceId()
               .equals(other.getDeviceId     ())) re  turn      false;
      if (!ge        tAppVe       r()
           .           equals   (o   ther.getAppVer())) return fals    e;
      if ( !get    UnknownF    ield   s().equ   als(other.g etUnkn         ow  nFields())) retu    rn false;
      re    turn true;
         }

          @java.lang.Override
    public int hashCode() {
         if (memoizedHas  hCode != 0) {
                 return me   m   oizedHash  Code;
      }
      int hash =         41;
      hash = (19 * hash) + getD      escriptor(         ).hash    C   ode()  ;
       hash     = (37 * hash) + ECHODA   TA_F       IELD_NUMBER;
       hash = (53 * h    ash) + getEchoDa  ta().hashCo de();
      hash  = (37 * hash)    + CLIENTID_FIELD_NUMBER;
      hash =         (53 * ha sh) + clientId    _         ;
           hash = (3     7 * has    h) + DEVICEID_FI   ELD_NUMBER;
         hash = (53  *       hash) + getDeviceId().hashCode();
          hash = (37 * hash) +    APPVER_FIELD_N  UMB   ER;
      hash = (53 * hash) + getApp       Ver().hashCode();
      hash = (29 * ha    sh) + getUnknownFields().hashCode();
      memoized    HashCode = hash;
             ret    urn hash;
    }

    public st   atic tech.ordinaryroad.live.chat.client.codec.kuaish    ou. protobu    f.CSP     ingOuterClass.CSPing p arseFrom(
        java.n   io.ByteBuffer d     ata)
        throws com.g     oogle.protobuf. InvalidProtocolBuff   erException {
         ret            urn PARSER.parseFrom(data);
    }
    public st  atic tech.ordinaryroad.live.chat.c lien       t. co dec.kuaishou.protobuf.CSPingOuterClass.CSPing parseFr     om(
        jav  a.    nio.ByteBuff er data,
          com.google.protobuf.ExtensionRegistryLite      extensionRegistry)
        throws      com.google.protobuf.In  validProt       ocolBufferException {   
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public   static tech.ordin     aryroad.live.chat.client.codec.kua      ishou.protobuf.CSPingO        uterClass.CSP   i  ng par seFrom(
         co    m.google.protobuf.ByteString    data)      
        throw s                 com.   google.protobuf.In   validProto     col   BufferException {
              return PARSER   .parseFrom(data);
      }
    public stati  c tech.ordinaryro    ad.live.cha   t.   clien   t.codec.kuai    shou.protobuf.CSPingOu   terClass.CSPing par        seFrom(
             com.  google.proto    buf.ByteString data, 
               com.goo     gle.protobuf.ExtensionRegistry   Lite extensionRegistry)
        thr      ows        com.google        .protobuf.Inval     idProtocolBufferExcep     ti on {
      return PARSER.parseFrom(data, extensionRegistry);
    }
     public stati c tec   h.o  r    dinaryroad.live.chat.client.codec.  kuais  hou.protobu      f.CSPingO       uter Class.CSPing parse         Fr   om(by       te[] data)
             throws com.google.proto      buf.InvalidProtocolBufferException     {
         retu  rn PARSER.parseF rom    (data);
    }
    publ  ic static tech.ordinaryroad.live.chat.cli     ent.c      odec.k    uaishou.  protobuf.CSPi  ngOuter    Class.CS   Ping      parseFrom(
              byte[] da ta,
            c   o        m.google.pr  ot  obuf.Extensio    nR      eg  is  tryLite extensionRegistry)
        throws com.google.prot  o  b  uf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionReg    istry );
    }
    public static tec   h.ordinaryroad.live.chat.clien      t.codec      .kua    i    shou.proto  buf.CSPingOuterCl   ass.CSPing parseFrom(java.io.InputStre  am input)
        thro          ws ja  va.io.IOException {
      ret    urn com  .goog      le .protobuf .Gen  eratedMessageV3
          .pa   rseWithIOException(PA    RSER,        inpu    t);
    }
    public stat  ic tech.ordin   aryroad.liv    e.chat.client.  c odec.ku aishou.prot obuf.CSPingOuterC     lass  .CSPing parseF      rom(
        java.io.InputStream input ,
               com.google.protobuf.ExtensionR   egistryLite extensionRegistry)
        throws java.i    o.IOException {
           return com.google.protob    uf   .Gener atedMes    sa geV3
                 .pa  rseWit     hIOExc     eption(PAR  SER, inpu      t, exte       nsionRegi     s   t   ry);
    }
 
       pub  lic static   tech.   ordinaryroad.live.cha           t.c    lient.codec.kuaishou.protobuf.    CSPingOuterClass.CSPing parseDelim  ite    dFrom(ja   va.io.InputStream input   )
         throw   s java.     io.I  OEx  cepti   on {  
            return com.google.p  rotobuf.GeneratedMessageV3
           .p  arseDe   l      imitedWi     thIOException(PARSER, input);
    }

        public static tech.ordinaryroad.li    ve.chat.client.codec.kuaisho      u.protobuf.CSPingOuter   Class.CSPin    g par s    eDeli   mitedFrom(
                   java.i  o.Input  Stream input,
        com.g   oogle.protobuf.Ex   tens     ionRegist   ryLite extensionRegistry)
          throws java.io.   IO           Exception {
      re turn  c     om.google  .protobuf.Gener  a tedM   es    sageV3
          .parse   De   limitedWithIOExce   ption(PARSER    , inpu   t, extensionRegistry);
        }
    public static tec   h.or dinaryroad.live   .chat.client.cod  ec.kuai        shou     .protobuf.CSPingOute    rClass.CSPing parse From(     
                 com.google.pro     tob uf.CodedInputStream   input)
                     thro     w s java.  i  o.I  OException     {
            return com.go ogle.pro  tobuf   .GeneratedMes sageV3
          .p   arseWith  IOException(PARSE   R, input);
     }
       pub   lic stati c tech.o  rdinaryroad.live.chat.clie     nt.code       c.kuaishou.pr  otob    uf.CSPingOuterClass.CSPing parseFrom(
        com.googl   e.  proto  bu     f.C  odedInp   utStream input,
        com.google.p   rotobuf.ExtensionRegistry  Lite ext                ens  i   onRegistry)
        thro      ws java.io.IOExcep   tion {
      return com.goo gle.protobuf.Genera   tedMessageV3
           .pars  eWithIOExcep   tio  n(   PARSER, inp  ut, extensionRegistry);
    }  

    @java.lang.Overrid   e
    pu    blic Builder newBuilderForT     ype() { ret     urn newBuild    er();        }
    public   static Buil      der newBuilder() {
           return DEFAULT    _INSTANCE.toBuilder();
           }  
    p ublic static Builder new   Builder(tech.ordina   r  yroad.live.ch            at.   client.   c   o  dec   .ku   aishou.pro  tobuf.CSPingOute     rCl  ass.CSPin   g protot      ype) {
        ret   urn   DEF AULT_INSTANCE.toBuilder().m   er   geFrom(prototype);
    }
        @java.lang.Override
    public Builder  toBuilder()     {
      return th   is == DEF AULT_IN    STANCE
          ? new Builder() : ne w B      ui lder().mergeFrom(this) ;  
    }  

    @java.lan     g.Override  
    pr   otecte   d Builder n   ewBuilderF   orTyp   e(
             com.google   .protobuf.Generat     ed   MessageV3.BuilderParent parent)  {    
      Buil     der builder = n    ew Builder(p     arent    );
      return builder;
    }
           /**
     * Protobu  f type {@code CSP   ing}
                     */
    public st           atic final cl     as   s Bu    ilder extend   s
            com.googl   e.protobu     f.Gene     ratedMessageV3.Builder<Builder> implements
        // @  @        protoc   _i  nsertion_point(builder_implements:CSPing)
        tec   h.ordin aryroad.li  ve.chat.cl   ient.codec.   kuaishou.pr         otobu  f.CSP      ingOuterClass.   CSPingOrBu    ilde     r {
           pub l ic static fin     al com.goo   gle.protobuf.Descriptors.Descript       or
             g    etDes  crip     tor() { 
        ret        urn tech.    o  r  dinaryroad.live.chat    .client.codec.kuais h    ou.protobuf.CSPingOuterClass. internal_s    tat  ic_   CSPing_descriptor;
                       }

          @          j  av        a. lan g.Overr  ide   
                 pr otecte    d co   m.google.protobuf.Gen  eratedMessag      eV3.FieldAcc              essor     Table
             int    ern        al     G  etFieldAc     cess  or    Table() {
          return tech.ordinar   yroad.live.chat.c      l       ient. codec.k  uaishou.protobuf.CSPingOuterClas s   .     interna   l_static_CSPing_fi   el   d      AccessorTable   
            .ensureFie  ldAccessorsInitialized(
                             tech.ord   ina       ry        road.  live.cha  t.c    lient.codec.ku   aishou.  protobuf.CSPingOuter      Cla          ss.CSPing   .c      lass, tech.ordi     naryroad.l  ive.          chat.     client.codec.kuaish   ou  .    protobuf.CSPin  gOuterClass   .CSP ing.Builder.c     lass);
        }

         // Co  ns     truct using    tech.ordinaryroad. live.c    hat    .cli  ent.codec .   kuaisho  u.protobuf.CSPing        OuterCla    ss. CSPing.newBui     lder      ()
      priv     ate Builde r (    )      {

                                }

         private Builder(
           com.google.pr otobuf.Gen  eratedM     ess   ageV3.Build erParent pare       n     t)    {
        super(parent);
    
         }
         @java.lang.  Ov    erri        d e
         pub  lic Bui            lder       cl              ear() {
        super.cle ar (      );
        b   itField0_   =    0;
           echoData   _ = "";
        clie      ntId_   = 0;
             d      eviceId_    =   "";
        appVer    _ = "";
                             return thi     s;
        }

      @java.lang.Override
               p   ublic    com.   google.pr otobuf.Descriptors.Descriptor
                  getDesc riptor For   Type() {
        re turn tech.ord        i   n   aryro   ad.live   .ch   at   .c    li  ent   .codec.kuaishou.p         rotob uf.CSPingOuterClass.internal_  static_CSPin  g_de    scr      ipto r;
        }

                      @   java.lang.O   verrid    e
      pu   blic tech.ord  in    aryr     oad    .live.chat. client.codec.kuaisho  u.proto          b    uf  .CSPingOuterC  lass.   CSPing       getDefaultInsta          nceForTyp  e() {
               ret  urn tech.   ordin    aryroad.live.chat.cl  ie    nt     .codec.k  uaish   ou.pro   t   obuf.CSPin   gOuterCla     ss.             CSPing.getDefau  ltInstance         ();
             }  

                         @ja  va.lan  g.Override
           p        ublic te   ch.ordinar  yroad.live    .cha  t.cli      ent.codec.ku     aishou.protobu    f.CSPingOuterClass.CSPing    buil             d() {
            t    ech  .ordina ry           ro     ad.live.chat.c      lie     nt.co      dec.                       k   uaishou. protobuf.CSPingOu t         erClass.     CSPing         resu   l  t     = bui ldPart      ial();
              i    f (!r        esult.isIni   tial    ized()       ) {
                       thr     ow ne wU   ninitia  lizedMes      sageExce    ption(result);
            }
                   return result;
      }

      @java.l     an  g.Over   ri    d   e
                 public t    ech       .ordi     naryr   oa  d.live.chat.      clien  t.codec   .k  uaish       o    u.             protobuf.    CSPingOuter Clas     s.CS   Ping buildPartial()        { 
                                   tech.  o     rdina       r    yro   ad.live.chat    .client.   codec.kua  i   shou.protobuf.CSPingOu         terC    lass   . CSPing resu l     t =     ne   w t     ech.or dinar   y r oad  .live.chat.client.cod     ec.kuaishou.pr  otobu     f.CSPing OuterClass.CSPing  (t his)         ;
             if ( bitFie   ld0  _ != 0) { buildPartial0(result); }
                onB  u     ilt();
                 retu       rn      res    ult;
               }

             pri va        te voi             d        buildPart   ial 0(tech.   ordinaryroad.l    ive  .chat.client.codec.kuaishou.pr    otobuf.CSPingOut      erCla ss.CSPing      result) {  
         in   t from_bitField0_ = bitField0_   ;
        if (((fr             om_bitFie   ld0_ &      0x000000     01) != 0)) {
                        res ult    .ech  o                     Data_ = ech  o    Data_;
        }
           i    f            (((from    _bitField0_ &  0x0  0000002) != 0)) {
          result.client  Id_ = clie  ntId_;
                  }
                  if (((f  rom_bitField0_ & 0x00       00         0004)      !  = 0)    ) {
          re   sult.deviceId_ = deviceId _;  
            }
                if (((   from_ bit    Field0_ &   0x000000     08) != 0)    )       {
              result.appV       er                _ =     appV  er_;
               }
                 }

            @java.lang.O  verri  d  e
      pu  bl    ic Bu    il    de  r    clone() {
           r  etu  r   n  super .clone  ();            
      }
       @java    .      lang.Ov  erride
       pu     blic Builder s       etFie          ld      (  
             com.google.protobuf.De   script or       s.F            ieldDescriptor field,
              jav    a.  lang.  Objec t    value)     {
                   ret urn    su  pe     r.se tFiel   d(field,     val  ue);
            }
      @ja    va .lan   g   .O   verride
           public Bu     il de  r clearField(
                                          com.google.protobu     f.Descri    pto rs.Fi  eld     Descriptor       field)     {
         retur      n super.c           learF  iel   d(field);
              }
                   @   j      ava.lang.Override
      publ  ic Builder      clearOneo   f( 
            com.google.p           rotobuf    .Descr    ip     t        ors.OneofDe     scri ptor on eof) {
        re   tur         n super    .clearO      neof         (oneof);
               }
        @java.l        ang.Override
                       public Builder setRepeat   edField(
          c    om.google.protobu f.D  escr  ipto rs.FieldDes    criptor fiel   d,
                     in  t i       ndex, j     a   va.lang.O     bject value  ) {
          ret     urn sup    er.setRe   pe             atedField  (f     ield, index      ,   va     lue)    ;
         }
        @java.lang.Overrid   e
       pu   bl ic Builder    a   d         dRe        peatedFiel       d(
          com.g     o  o   g le.   prot   obuf.Descripto    rs .FieldDescript               or field     ,
                          java.  lang.Ob   ject value  ) {
                            retur  n super  .addRepeat   edField(   field   , value);
        }                 
       @java.l ang.Override
                         publi   c   Buil           der           m      er       geFr  om(com.google .pro               to    buf     .Message       other) {
         if (other instan       ceof tech.ordi   naryroa    d.liv   e.chat.clien    t.   codec.ku       aishou.pro     tobuf      .C   SPingOu  ter     Class.CSPing) {
           return mergeFro  m((  tech    .ordi    nar                yroa     d.live.c  hat.client.codec.kuaishou.protob    uf.CSPing OuterCl     ass.  CSPi   ng)other);
        } else {
                         su         per. m  er geFro      m (o  ther);
                               return this;
         }
      }

                      p ublic Buil    der merg  eFrom(tech.ordin      aryroa   d        .live.c      ha      t.c   li     ent.code   c.kua     ishou.protobuf.CSPingOute rClass.CSP     i ng  other) {
           i      f (other == tech.ordinar      yroad.l                     iv  e.c     hat.client.  codec.k   uaishou.protob                   uf.CSPi ng   O                uterCla ss    .CSPing.getDefa        ul    tInstance()) return        this;
        if (!other.g      etEcho     Data()    .    is     Empty()) {
                 echoData_        = other   .echoDat       a_;
                bit    Field0_ |     = 0x000          000        01;
                     onChanged();
              }   
               if   (other.   c  lientId_ !=              0)    {
                 set      ClientId     Va  lue(o        ther.g  etClientIdValue());
        }    
                         if   (!othe  r.ge      tDeviceI  d().isEmpty()) {
                 device     Id_ = oth  er  .deviceId_;
               bi tFiel  d0_ |=   0x00000004;
             onChanged();
            }
                 if (!o       th     er.g  etAppVer().isE    mpty()) {
                               app Ver_ = other.a    ppVer_;
                 b      itFie       ld0_ |= 0x00 00000    8;   
             onChanged(   );
                     }
          thi   s   .mergeUnknow    nFi elds(other.getUnknown      Fie lds());
        onChan  ge   d();     
        return thi   s;
        }

        @  j   ava.lang.Override
         p   ublic f   inal boolean    i    sIni tia                    l    ize d() {   
           r     eturn true   ;
      }  

           @java.lang.Override
          public     Builder mergeFrom(
           com.google.protobuf.Coded      I   nputStream input  ,
          com.goog     le.pr            o     t   obuf  .Ext     ensi     onR      eg  istryLite exte   nsionRegi         stry)
             thr    ows  java.io.IOExcep      tion {
            if (ext       en    sion   Registry == null) {
               th  row    new     ja va.lang.Nul      lP    ointe  rExce pti  on();
        }
              try {
                bool ean done = false  ;       
                   while (!done             ) {
                                            int t    ag    =       input        .read    Tag();
                                  switch (tag) {
                                                         ca  se     0:   
                                 done = t      rue;
                                                   break    ;
               c ase   10: {
                  echoData_                = input.readS tring  RequireUtf8();
                                       b         itField0_ |= 0x0000000    1;      
                       break;
                   }      //   case 10
              c  ase          16: {
                           clientId_ =       input.read   En u   m();
                  bit    Field0_ |=              0x0000000     2;                 
                                                      b        reak;
                                          } // ca   se  16
                 cas  e 26: {
                        deviceId  _ = in        put.re  adStringReq         uireU    tf8(   );
                                    bitField0_ |        =  0x00000004;
                                        break      ;
                                        } // case 26
                case      34       : {
                        appVer_ =  inpu   t     .readStrin gRequireU  tf8();
                        bitField0          _ |             = 0x0   0000008;
                         brea  k;
                     } // case 34   
                                     default           :    {
                        if   (!super.parseUnkno wnField(input         ,    exte       nsionR  eg  istry, t    ag)) {
                                done = true;    // was an end  group tag  
                                       }
                        break;   
                              }       / / defau    lt:
             } //        s        witch (tag)
               } //  while (!done)
                } cat         c     h (   com.    g    oogl     e   .protob    uf.InvalidProtocol           Buf  ferExce         ption   e) {
                   t  hr      ow e       .un  wrap      I  OException    ()    ;
           } f        inally     {
              onChanged();
                   } //  finally
          return thi s ;
      }
           priv   at    e   i  nt bitF  i  eld0                  _;   

            private java.lang   .Object echoDat      a_ = "";
           /*  *
          * <code>string echoData = 1;</code>
           * @r    etu   r        n     The e   choDa              ta.
         */
      public    jav     a.l  ang.    String g     etE              choData() {
           j   ava.lang.Obje    ct ref     = e   choDa         ta_;
        if    (!(re   f insta      n      ceof          ja va    .l        ang      .String)) { 
             co     m      .go  o  g     le.protobuf.ByteString       bs =
                        (com.googl   e.p  rotobuf.ByteS  tring) re              f;
                   java.lang.Stri       ng s = bs  .t     oSt   ring             Utf8();
                      echoData_     = s;
                       return      s;
            } else   {
            retur      n    (java.lang.      String) ref;
           }
             }   
          /**
       * <code  >string  ech oData = 1;</c    ode>
                 *        @return T    he by   tes for           ech       oData.       
          */
              public    com.google.proto  bu  f.By    teS         tring
                          getEch             oDat    aBy              tes() {
          java.la   ng.Ob ject ref = echoData  _;
               if     (   ref instanceof St      ring) {
                      c           om.google.prot      obu  f.ByteString b = 
                     c       om.goog         le.proto    buf.ByteSt ring.copyFro  mU      tf8(
                         (java.     la     ng.      St  r   ing) ref);
          echoData_ =        b;
          return b;
                      }   els  e {
          re   turn      (com.    google.protobuf.By      teString) re f;
        }
      }
      /**
       *    <code>string echoData = 1;</code>
                   * @       para m value T       he echoData to s    et     .
                        * @retu  rn This builder for c haining.
                 */
        pub    lic Builder     setEcho     Data(
                j    ava.la  ng.String value) {
                i    f (value     == null) { throw     ne    w NullPoi     nter    E      xce     ption(); }
              e      choData_ = value;
        bitField0_  |= 0x0000000   1;
           onChanged();
                   ret urn      this;
          } 
      /**
       * <code>strin      g echoData    = 1;<   /code>
                  * @r etur  n T     his builder for ch      ainin     g.
              */
      pub lic   Builder clearEcho  Data() {
         ec                      h oDa  ta _ =     get     De faultInstance().getEchoData()  ;
          bit F   ield0_     = (bitF  ield0_ &    ~ 0x0000000  1);
              onChanged();
        return     thi  s;
      }
          /**
                * <code>string echoData = 1;</code>
         * @p   aram va     lue The byt   es for echoDat         a to se   t.
                  * @ r etu  rn This builder for   chaining.
       */
      public Builde r s       et     EchoDataBytes(
               com.google.           p    rotobuf.Byt  eString value) {
                  if (v   alue =   = nul  l) {       throw new       NullPointerException()    ;     }
                           che  ck  By   t    eS   t     ringIs    Utf8(value);
        echoData_ = value;
        bi      tField0_ |= 0x00000       001;
           onChanged(); 
                       retur   n this;
          }
  
      privat           e int clientId_ = 0;   
      /**
       * <code>.ClientI  d     clien   tId = 2;</code>
       *      @return The enum       numeric value on the wire for      clien   tId.
              */
      @java.lang   .Override pub lic int getClientId   Value() {
        ret   urn cl   ien    tId      _;
      }             
      /**
         * <code>.ClientId clientId    = 2;</code>
                  * @p            aram value T       h                 e enum     numeric va lue on               the wire for clientId     to   set.
          * @return This builder for chaining  .  
          */
              public Builder setClientIdValue(int   value) {
        cli   ent   I   d_ = va     lue;
        bitFie  ld0_ |=       0x       00000002;
        onCh  anged();
        return this;
        }
         /**
       * < code>    .C   lientId clientI   d = 2 ;</code>
       * @ret    urn       The clie  ntId.
       */
                 @java.lang.Override
        public te  ch.ordinaryroad.live   .ch   at.   cli  ent.c     odec.kuai  shou.protobuf.      Clie      ntIdOuterClass.Cl   ientId getClie ntId() {   
           t ech .ordi   naryroad.live.chat.client.c  odec.kuaisho  u.protobuf.ClientIdOuterClass.Clien     tId r      esult =   tech.ordi      naryroad     .live.chat.clien         t.c odec.kuais ho       u.protobuf.ClientIdOuterC   lass.C  lie      ntI d.forNumber(    clientId_);
        return result ==       null ? tech.ordinaryr        oad   .live.    chat.client.codec.kuaishou.protobuf.    C  lientIdOuterClass.ClientId .U      NRE COGNIZED : result;
            }
         /**
         * <code>.C         lie          ntId clientId = 2;     </code>
              *        @param value The clien tId   to set.
       * @retur  n T  his builder    for chaining.
       */
      public Builder setClientId(tech            .o    rdinar              yroad.li   ve.ch       at.client.codec  .kua  ish ou.prot    o   buf.Client    IdOu    terCl ass.Cl      ientId value) {
              if (value == n       ull) {
            throw n      ew NullPo     interException();
           }
            bit  F ield   0_ |= 0x000   0 0002;
          clientId_ =   value.getNum      b er();
        onChanged();
          retu      rn   this   ;
           }
         /**     
           * <code>.ClientId client  Id        =        2;</code      >
       * @return    This    buil     der    for       chaining.
       */
      public Builder clearClientId() {
              bi  tField0_    = (b  itFi eld0_ &    ~0x00000002);
        clientId_ = 0;
          onChanged();
        return this;
        }     

      priv ate  java.lang.Obje     ct deviceId_     = "";
      /**
       * <cod    e>st   ring deviceId = 3;    </c ode>
       * @retur n T he deviceId.
       *   / 
      publi c java.   lang.String getDeviceI    d   () {
        java.  lang.O bject ref          = de viceId_;
           if (!(ref instanceof java.lang.String)) {
                com.g   oogle.     protobuf.   ByteString bs =
                   (com.google.protobuf.ByteStr   ing) ref;
             java.lang.Str     ing s  = bs.toStringUtf  8();
            deviceId_       = s    ;
                       ret    urn s ;
        }   else          {
           retur n (ja     va.lang.String)    ref;
        }
      }
      /**
       * <code>string deviceId =      3;</co  de >
          * @r   eturn Th   e bytes for deviceId    .
       */
        public com.google.protobuf.ByteString
          getDeviceIdBytes() {
        java.lang.O bject r  ef = deviceId   _;
        if (ref instanceof Stri       ng) {
               c     om.google.protob     uf.Byte String b = 
                   com.google.protobuf.ByteString.copyFromU  t f8(
                              (java.   lang.String) ref);
              deviceId   _ = b; 
             return b;
        } else {
          r   eturn (com.goo  gle.protobuf.ByteString) ref;
          }  
      }
      /**
       * <code>string deviceId = 3;</code>
       * @param v     alue The devic    eId to set.
       * @return T     his b       uilder for     chaining.
       */
      p      ublic Builde  r setDeviceId(
            java.lang.Str  ing value) {
        if (value == null) { throw new N  ull    PointerException(); }
           de   viceId_ = value;
                bitField0_ |= 0x00000004;
        on        Changed();
        re     turn th    is;
      }      
      /**
        * <   code>string device    Id = 3;</code>
       * @     return This builder for chaining.
       */
      public B   uilder clearDeviceId() {
        deviceId_ = getDefaul     t    Instance().getDeviceId();
        bitField0_ = (   bitField0_    & ~0x00000004);
        onChanged();
        return this;
      }
      /**
             * <code  >string deviceId = 3;</c    ode>
         * @param value The bytes for    deviceId to set.
         *     @return This builder for cha     ining.
       */
      public Builder setDevice I      dBytes(
            com.google.proto   buf.B     yteSt  ring value) {
        if (value == null) { throw new NullPo   interException(); }
                che       ckByteStringIsUtf8(value);
           deviceId_ = value;
        bitField0_ |= 0x    00000004     ;
        onChanged();
                return this;
      }

      private java.lan  g.Obje ct appVer_ = "";  
         /  **   
          * <co  de>str  ing app    Ver =        4;</code>
       * @return The appVer.
           */
      public jav   a.lang.S  tring getAppVer() {
            java.lang.Object ref = app      V  er_;
        if (!(ref instanceof ja va.lang.   String)) {
          com.google.protobuf.ByteString          bs =
              (com.googl  e.protob   uf.ByteString)    ref;
              java.lang  .String s              =      bs.toStringUtf8();
          app Ver_ = s  ;
          return s;
        } else {
                return (java.lang.     String) ref;
             }
      }
      /**
       * <c  ode>string appVer        = 4;</code>
        * @return The bytes for app Ver.
        *  /
      public com.google.protobuf.ByteString
          getAppVerBytes() {
        java      .lang.Obje       ct ref = appVer_;
          if (ref instanceof String) {
          com.google.protobuf.Byt   eString    b = 
                com.google.protobuf.     ByteString.copyFromUtf     8(
                     (java.lang.String) ref);
             appVer_ = b;
          return b;
            } else {
            retu rn (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string appVer = 4;</code>
       * @para  m value The ap   pV  er to set.
       * @retur   n This builder for chain       ing.
       */
      public Builder setAppVer(
          java.lang.String value) {
        if (value == null) { throw new NullPointerException(); }
           appVer_ = value;
        bitF      ield0_ |= 0x00000008;
        onChanged();
         return this;
      }     
      /**
       * <code>string appVer = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearAppVer() {
           appVer  _ = getDefaul    tInstance().getAppVer();
          bitField0_ = (bit    F     ield0_ & ~0x00000008);
        onChanged ();
         return this;
      }
         /**
       * <code>string appVer = 4;</co   de>
       * @param value  The bytes for appVer to set.
       * @return This builder      for chaining.
       */
      public Builder setAppVerBytes(
          com.google.protobu   f.ByteString value) {
        if (value == null) { throw new       NullPointerException(); }
        checkByteStringIsUtf8(value);
        appVer_ =     value;
        bitField0_ |= 0x00000   008;
        onChanged();
        return this;
      }
         @java     .lang.O verride
      public  final Builder  setUnknownFields(
          final com.google.protob   uf.UnknownF ieldSet unknownFields) {
        return super.setUnknownFields(un knownFields);
      }

      @ java.lang.Override
      public fin al Builder mergeUnknownFields(
             final com.google.protobuf.UnknownFieldSet unknownFields) {
        ret urn super.mergeUnkno     wnFields(unknownFields);
            }


      // @@protoc_insertion_point(builder_scope:CSPing)
    }

    // @@protoc_insertion_point(class_scope:CSPing)
    private static final tech.ordinaryroad.live.chat.client.codec.ku        aishou.pro  tobuf.CSPing    OuterClass.CSPing DEFAULT_INSTANCE;
    static {
           DEFAULT_INSTANCE = new tech.ordinaryroad.live.chat.client.codec.kuaishou.protobuf.CSPingOuterClass.CSPing();
    }

      public static tech.ordinaryroad.live.chat.client.codec.kuaishou.protobuf.CSPingOuterClass.CSP   ing getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    pri  vate static final com.goo   gle.protobuf.Parser<CSPing>
        PARSER = new com.google.protobuf.AbstractParser<CSPing    >() {
      @java.lang.O   verride
      public CSPing parsePartialFrom(
              com.google.protobuf.CodedInputStream input,
          c om.google.protobuf.Exten   sionRegistryLite extensionRegistry)
          th    rows com.google.protobuf.Inval idProtocolBufferException {   
        Builder builder = newBuilder();
          try {
          builder.mergeFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(builder.b  uildPartial());
        } catch (com.google.protobuf.Uninitialize  dMessageException e) {
          throw e.asInvalidProtocolBufferException().     setUnfinishedMessage(builder.buildPartial());
        } catch (java.io.IOException e) {
          throw new com.google   .protobuf.InvalidProtocolBufferException(e)
              .setUnfinishedMessage(builder.buildPartial());
        }
        return builder.bu     ildPartial();
      }
    };

    public static com.google.protobuf.Parser<CSPing> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<CSPing> getPars erForType() {
      return PARSER;
    }

    @java.lang.Override
    public tech.ordinaryroad.live.chat.client.codec.kuais  hou.protobuf.CSPingOuterClass.CSPing getDefaultInstanceForType() {
      return DE   FAULT_INSTANCE;
    }

  }

  private static final com .google.protobuf.Descriptors.Descriptor
    internal_st   atic_CSPing_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CSPing_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\014CSPing.proto\032\016ClientId.proto\"Y\n\006CSPing" +
      "\022\020\n\010echoData\030\001 \00   1(\t\022\033\n\010clientId\030\002 \001(\0162\t.C" +
      "lientId\022\020\n\010deviceId\030\003 \001(\t\022\016\n\006appVer\030\004 \001(" +
      "\tB<\n:tech.ordinaryroad.live.chat.c    lient." +
      "codec.kuaishou.protobufb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
         .internalBuildGeneratedFileFrom(descriptorData,
        new com.goog      le.protobuf.Descriptors.FileDescriptor[] {
          tech.ordinaryroad.live.chat.client.codec.kuaishou.protobuf.ClientIdOuterClass.getDescriptor(),
        });
    internal_static_CSPing_descriptor =
      getDescri ptor().getMessageTypes().get(0);
    internal_static_CSPing_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CSPing_descriptor,
        new java.lang.String[] { "EchoData", "ClientId", "DeviceId", "AppVer", });
    tech.ordinaryroad.live.chat.client.codec.kuaishou.protobuf.ClientIdOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}

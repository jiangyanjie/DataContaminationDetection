/*
 *    MIT    License
     *
 * Copyright (c) 2   023    Ordina   ry  Road
 *
 * Permission   is hereb   y g ranted, free  of charg   e, to any per  son obtaining a co     py      
 *    of this software      and assoc     iate    d documentatio   n    files (the "So     ftware"), to deal
 * in the Software without restriction, includin  g wi  thout limitation the   righ ts
 * t    o       use, copy, modify, m e      rge, publish,      distribute, su  blic   ense, and/or        sel       l
 *     copies o    f the Software, and to permit persons  to wh         om th  e Software is
 * furni  shed  to do so, subject to the following cond itions    :
 *
 * The above copyrigh t notice an     d this permi ssion n      otice       shall be incl   uded in all
 * copies or  substantial portions of t   he Sof   tware.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT    WARRANTY OF   ANY KIND, EXPRESS OR
      * IM     PLIED, INCLUD  ING BUT NOT LIMITED   TO THE WARR     ANTIES OF MERCHANTABIL ITY,
 * FI   TN     ESS FOR A PARTICULAR  PUR POSE AND NONINFR   INGEMENT. IN NO  EVENT SHALL THE
 * AUTH ORS OR COPYRIGHT HOLDERS BE  LIABLE FOR ANY CLAIM, DAMAG    ES   OR    O  T     HER
  * LIABILITY,      WHETHER IN  AN AC     TION O  F CONTRACT, TORT OR OTHERWIS E  , ARISING F ROM,
 * OUT OF OR IN CONNECTION WITH TH     E SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
  */

// Gene        rated by the protoc  ol buffer com   piler.  DO NOT EDIT!
// source: C  SHeartbeat.proto

// Pro      tobuf Java Version: 3.        25.3
package tech.or dinaryroad.live.c   hat.cli     ent.co   dec.kuaishou.protobuf;

publi    c final class C    SHeartb eatOut    erCl  ass {
  private     CSHeartbeatOuterClass() {}
      public sta       tic void reg  isterA llExtensions(
      com.google.protobuf  .Extensi    onRegi         stryLite registry   ) {
   }

  public static void registerAl    lExtens     ions(
        com.google.p   rotobuf.ExtensionRe    gistr y     regis  try) {
         regist     erAll   E xt      e  nsions      (
        (com.google.p    rotobu    f.ExtensionRegistryLite) registr  y)   ;
  }
  pub      l     ic interface C    SHeartbeatOrBuilder extends
        // @@protoc_insert    ion_poin    t(int       erface_ extends:CSHeartbeat )
        com.google      .protobuf.MessageOrBuilde  r {

         /**
     * <code>uint64 timesta       mp = 1;</cod    e>
     * @ret           urn The timestamp.
     */
     lo      ng getTimesta mp();   
  }
  /**   
   * Pro    t  o   buf type {@c  ode CSHeartbeat  }
   */
  public static final cl  ass        CSHeartbe at  extends
      com.googl   e.   protobuf      .GeneratedMessage  V3 imp   leme   nt  s
      // @@proto      c_insertion_  poin    t(mes            sage_impl    em   ents:CSHear    tbeat)
      CSHeartbeatOrBuilder     {
  private static    final long serialVersionUI D = 0L;
    // U  se CS  Heartbeat.ne  wBuild er() to construct.  
        private CSHeartbeat(com.  go    ogle.p   rotobuf.Generated   MessageV3.Bui    l            der<?     > builder) {
      super(buil der);
       }
    private CSHe artbeat() {     
            }

    @java.lang.Override
    @SuppressWarnings({"unused"})
    p rotected j  ava.lang.Object newInstance(
        UnusedPr  ivatePar     amet   er       unused) {
      return new CSHea  rtbeat();
    }

       publi c static final com.  google.protobuf.Descriptors.      Descrip   tor
        getDescriptor() {
      return      tech.ordinar      yroad.  live.chat.client.codec   .kuaishou.protobuf.CSHeartb   ea   tOuterClass.i   nternal_static_CSHear tbeat_descriptor;
      }

    @ java.lang.Override
    protected com.goo   gle.protobuf.GeneratedMessageV3.Fie        ldAccessorTable
        internalG         etFieldAccessor  Table()     {
      ret   urn tech.ordina    ryroad.li  ve.chat.client.codec.kuaishou.protob uf.CSHe artbeatOuterClass.      in   t ernal_   stati    c_CSHeartbeat_fie   ld       Acces    sorTable
          .ensureFiel      dA     ccessorsI   n  itialized(
                      tech.ordinaryroad.live.chat.client.c             od     ec .kuaishou.pr  otobuf.CS  Hea  rtbe   atOuterClass.CSHeartbe         at.class, tech.ordinaryroad.live      .         chat   .client.codec.ku     aishou.protob   uf.CSHea     rt    beatOut      erClass.CSHeartbeat .Builder.class);
    }

       public stati       c fi     nal int TIMEST     AMP_FIEL  D_NUMBER = 1;  
    private      lo  ng timestamp_   = 0L;
    /**
        * <code>uint     64       timesta   mp =    1;</code>
     * @retur    n    The tim estamp.
        */
       @jav  a.l      ang    .Override
    public long getTimestam     p() {   
      return t    imestamp_;   
    }

               pri  vate   byte   mem   o   izedIsIniti   alized = -1;
    @j     a va.la  n   g.Override
          public final boolean isIniti    alize    d()           {      
      by     te isInitialized = memoizedIsInitialized;
         i                    f (is    Initialized == 1) return    true;
            if (isInitiali  zed == 0  ) return false;  

          m  emoizedIsIniti   a             liz  ed = 1;
                return   true;
       }

           @     java.lang.Override
          publi        c void writeT      o(c       om .google.protobuf.Code    dOutput  Stream output)
                                      throws       java.io.IOExc  eption {
      if (timestamp_ !=        0L         ) {
          outpu   t. writeUInt64(1,    tim   estamp_);
        }
                getUnknownFiel  ds().writeT   o(output);
    }

         @java.lang     .Overrid  e
    public int getSerializedSize() {
      int size = m           emoizedSize;
         if (size != -1) return size;

               size     = 0;
           if    (t imestamp_ != 0L  ) {
        siz e += com.      google.protobuf.Cod      edOutputStream
                .computeUInt64Size(1    , timestamp_);
      }
        size += getUn         kno  wnFields().    getSer ializedS    ize();
      mem              o   iz  edSize = size;
      ret      urn size;
        }

            @java.la       ng.Override
    pu     blic b    oolean e  quals(final java.lang.Object obj) {
      if (obj ==   this) {
          r          eturn true;
       }
      if (!(obj instance   of tech.ordin  ary road.live.chat.client.codec.kuaishou.p  ro tobuf.   CS     HeartbeatO u       terClass.CSHeartbeat)) {    
        return super.equal  s(obj);
           }
      tech.ordinar   yr o    ad.live.chat.c      lie     nt.codec.kuaishou.protobuf.CSHeartbeatOuterC    lass.C     SHeartbe          at other =    (t   ech.ordinaryro      ad.live.chat   .client.codec.kuaishou.  protobuf.CSH   e  artbea      tOuterClas     s.CSHea     rtbeat) obj;

      if (       getTimes  tamp()
           != other.getTim  estamp(   )) retur   n false;
          if (!getUnkn   ownF       ields().   e  qu   als(other.getUnkn    ow            n    Fields   ())) return false;
      return true;
      }

      @java.lang.Ov  erride
       p         ublic int hashCode()  {
       if (memoizedHashCode     !   = 0) {
                     return memoizedHashCode ;
          }
         in     t ha     sh = 41;
            hash = (19   * hash) + getDescrip   tor()   .hashCode();
           ha     sh = (37 * hash)   + TIMES  TA MP_FIELD_NUMBER;
      hash = (53 * hash) + com .google.protobuf.Interna  l.hashLo ng(
          get     Timestamp(  ))           ;
        hash = (29 * hash) + getUnknownFields().hash      Code();
      memoizedHashCod  e = hash;
      return hash ;
    }         
          
    public static tec  h.ordinaryroad.  live.chat.client.codec.kuaisho   u.prot  obuf.CSHeartbeatOuter     Class.CSHeartbeat pa        rseFrom(
                     java.nio        .ByteBuffer data)
        throws co      m.google.proto    buf.InvalidProtocolBufferException {
      return PARSER.parseFrom(  data);
    }
        public static t  ech.ordinar yroad.live.chat.client.codec.   kuaishou.p    rotobuf.CSHea   rtbea    tOuterClass.CSHeartbeat parseFrom(
        ja  va.nio.ByteBuffer data,
        com.google.protobu    f.Ex tensio  nRegistryLi                      te extension     Re   gistry)
        throws com.g oogle.protobuf.InvalidProtocolBufferException {
      ret     urn PARSER.parseFrom(data, exten  s ionRe  gi str      y);
     }
    public static tech.ordinaryroad.live.cha  t.client.codec.kuaishou.protobu    f.CSHeartbeatOut   e  r   Clas   s.CSHear     tbeat parse  From(
          com  .goog   le.protob     uf.ByteString da  t      a    )
        throws com.google.protobuf.        Invali  dProtocolBufferException {
        re   tur  n PARSER.parseF rom(data);
       }
    public stati  c tech.or   dinaryroad.live.chat.client.codec.k uai   shou.protobuf.CSHeart   beatOuterClass  .CSHeart  beat pa  rseFrom(
        com.google.pro     tobuf.ByteString data,
        com.g        oo  gle.pro    tobuf.E   xtensi      onRegistryLite extensionRegistry)
        thr   ows com.google.protobuf.InvalidPro  tocolBufferException {
             return   PAR     SER.parseFrom(     data, extensionRegistry)  ;  
    }
    publi    c   static      tech.ordi    naryroad.live.cha  t.client.co dec.kuaishou.p rotobuf.CSHeartbeatOute rClass.CS    H    eartb eat parseFrom  ( byte  [] data )
        throws com.google.protobuf.InvalidProtocolBuff   er        Exception {   
      return PARSER.parseFrom(data);
    }
    pu   blic stati  c tech.ordinaryroad.live.chat.client.codec.kuai sh  ou.protobuf.CSHeartbeatOuterClass.CSHea   rtbeat parseFrom(
              byt       e[] data    ,
            com.google.protobuf.Ex         tension RegistryLite ext  ensionRegistry)
          th   rows com.goo    gle.protobuf.InvalidProtocolBuf   fe            rException {
      re  turn P     ARSER.parseFrom(data   , extens ionRegistry);
    }
          publ  ic sta   tic tech.or   dinaryroad.l    iv   e.chat.c lient.codec.kuaishou.protob   uf.CSHeartbeatOu      terClass.CSHeartbeat parseFrom      (java.io.Inp      utStream input)
            thro   ws java.io.IOExc    eption {
      retu     rn com.google.protobuf.Gen   er    atedMessageV3
          .parseWithIOEx  ception(PARSER, input);
    }
    public static tech.ordi     naryroad.live.chat  .client.codec.kuaishou.protobuf.C      SHeartbeatOut      erClas s.CS   H         eartbeat   parseFrom(
           java.io.InputS      tream input,
        com.google.protobuf   .ExtensionRegi  stryLite extensionRegistry)
        throws java.io.IOExc      eption {
         return com.google.protobuf.GeneratedMessa       geV3
          .parseWithIOException(PARSER, input, extensio   nR            egistry);
           }

      public static tech.ordinaryroad.l  ive.chat.client.codec.kuai   shou.protobuf.CSHeart             be   atOuterClass.CSHeartbeat parseDelimitedFro   m(java.io.Inp      utStream input)
                                throws jav       a.io.IOException {
        retu      rn com.    google.prot     o    buf.   Generated     Messa  geV3
                   .pa  rseDelimitedWithI      OException(PA         RSER, in  pu  t);
         }

    public stat           ic tech.ordinaryroad.   live. chat.cl   ient.codec  .ku      aishou.pr  otobuf.CSHea   rtbeatOuterCla  ss.CSHeartbeat  parseDelimi     tedFrom(
        java.io.InputS   tream input,
         c om.  google.protobuf.Extensio   nRegistryLite extensionR       egis try)
        throw            s java.io.IOException {
      return com.googl e.protob    uf.GeneratedMessageV3 
            .parseDelimitedWit   hIOExce      ption(PARSER, input, extensionRegistry);
    }
    public stat        ic     tech.ordina  ryroad.live.chat.cl  ient.co      dec.kua ishou    .protobuf.CSHeartbeatOuterClass.CSHeartbeat   parseFrom(
        com.googl e.pr otobuf    .   CodedInputStream input)
          th  rows java.io.IOE  x   ception {
      return com.goog   le.  protobuf.Genera      tedM           essageV3
              .parseWithIOException(PARS  ER, input)  ;
    }       
    public s tatic tech.ordinaryr oad.live.chat.client.c  odec.kuaishou.protobuf.CSHeartbeatOu  terClass   .CSHea   rtbeat parse   From(
         com.google.protobuf.Coded    InputS   tream input,
          com.google.protobuf   .Extensio      nRe   gistryLite exte       nsi   onReg   istry)
            throws java.io  .IOException {
      retur    n com.goo      gle.pro  to      buf.GeneratedMessageV3
          .parseWi        th     IOException(P  ARS  ER, input, extensionRegistry);
    }

    @java.    lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
       public static Bui l  der new      Builde       r() {
      return DE    FAULT_ INSTANCE.toBuilder();
    }
    public st  atic Builder   newBuil  d  er     (tech.ordinaryroad.live.chat.c   lie   nt.codec.kuaishou.protob   u   f.CSHeartbeatOuterClass.CSHeartbeat proto  type) {
      return DEFAULT    _INSTANCE  .to    Builder().mergeFrom(protot  ype)   ;
    }
    @java.lang.Override
    public Builder toBuilder      () {
      return this == DEFAULT _INSTANCE
             ?   ne               w   Buil     der() : ne  w Builder().mergeFrom       (     this  );
       }

    @java.lang.Override
        pr otecte    d Builder newBuilderForType(  
        com.google.protobuf.Generated        Mess  ag   e           V3.BuilderParent parent) {
      Builder builder = new Builder(parent);
          ret ur        n builder;     
      }
    /**
        * Prot         obuf type {@code CSHeartbeat}
     */
    public stati    c final class  Builder extends
        com.goo    gl   e.protobuf.GeneratedMessageV3.Build   er<Bu  ilder> implements
        // @@pro  toc     _inser     tion_point(builder_i   mp     l    ements:CSH eartbeat)
                 tech.ordinaryroa  d.live.chat.client.codec.kuaishou.pr      otob            uf.  CSHeartbeatOuterClass.    CSH   eartbe    atOrBuilder {
          public sta      tic     final com.goog l      e.prot  o  b     uf.Descriptors.Descriptor
                getDescripto  r() {
        return tech.ordinaryroad.live.chat.client.code   c.k  uaish    ou.    protobuf.CSHeartbeatOute rClass.internal_sta  tic_CSHeartbeat_    descriptor ;
          }

                 @j  av    a.lang.Over     ride
      protected com.google      .protobuf. GeneratedMessageV3.Fie           ldAccessorTable
          inte rnalGetFieldA   ccessorTa    ble    ()     {
        return tech.ordinaryroad.live.chat.cli   ent.  codec.kuaishou.pro     tobuf.CS      Heartbeat            OuterClass.i  nternal_static_CSHeartbeat_field  Ac    cessorTable      
            .ensur     eF   ieldAccessorsInit    ialized(
                   t ech.ordinaryro    ad.live.c         hat.clien  t.codec.kuaishou.protobuf.     CS   Heart  beatOuterClass.CSHeartb    eat.class, tech. o    rdinaryroad.live.chat.clie   n         t.codec.k       ua    i       s    hou.protobuf.CSHeartbeatOuter      Clas     s.C SHeartbeat.Builder.class);
            }

      // Construct usi     ng tech.ordinaryroad.l        ive.chat       .clie  nt.cod   e      c.kuaishou.p  ro      tob     uf.CSHeartbeatOuterCla       ss.CS             He artbeat. newB   uild  er()      
          priv     ate   Builder() {

      }

      private Builder (
          com.google.protobu  f.Genera                 tedMessageV3.Build    erParent    parent) {       
        supe r   (pare     nt);

           }
      @java.la  ng.Override
        publ   ic Bu ilder  clear() {
                sup      er.clea      r  ();
        bitField0_ =   0;
        timest amp_ = 0L;
        re   tu      rn   th    is;
      }         

      @java.lang.Override
      public com.googl     e   .pro      tobuf.Descriptors.D    escript    or
                 getDescr  iptorForType() {
           ret  urn tech.ordinaryr   oad.live.          ch  a   t.client.codec.kua  ishou.protobu                  f     .CSHear     tbea   tOuterClass.internal_static_CSH   e   a rt          beat_descriptor;
      }  

      @java.lang.Override
      public tech.ordinaryr     oad.l          ive.c   hat. c  lient.codec.kua   ishou         .protobuf.    CSHeartbeatOuterClass.CSHeartbea     t     get   De faultIns  tanceForTy  pe() {
              return tech            .o rdi   na  ryroad   .live.chat.clie        nt.      codec.kuai       shou.   protobuf.CSHea    rtbeatOute  rClass.C   SHe  artbeat   .g    etDefault     Instance();
           }

      @java.lang.Ov    erride
      pub   l      ic t   ech.ordinaryro      ad.live.cha  t.client.co   dec.kua  ishou.prot o   buf.CSHeartb    eatOuterClass.CSHeartbeat   build() {
        tec    h.ordinaryr  oad.live.chat.cli    ent.codec.kuaishou.protobuf.CSHeartbea     tOuterClass.CSHe     artbeat result = buildPart    i al    (  ) ;
        if (    !result   .isInitiali  zed()) {
          throw newUn in   itializedMess     age             Excepti o   n(r   e sult);
           }
         return result;
      }
     
      @java  .l  ang  .O   verride
      public tech.ordinaryroad.live.chat.c lient     .co   dec   .kuaishou.protobuf.C SHeartb   eatOute rC    la    ss.CSHeartbeat buil   dPartial() {
        tech.o    rdinaryroad.l   ive.chat.client.codec.kuai          sh    ou      .protobuf.CS    Hear   tbeatOuter   Class.CSHeartb             eat result = new t  ech.or   dinaryroad.live          .chat.cli ent.codec.kuaishou.protobuf.CSHe  artbeatO   ute     r  Class.CSHeartbeat(th  is);
          if       (bitField0_ !=         0     ) { buildParti     al0  (resul  t); }
                onBuilt();
             return          result;
      }

               private v   oi       d         b      uild  Part  ial                              0(tech.ordin      aryroad.l  ive.  chat.c li ent                         .    codec       .kuaishou.pro       tobuf.CSHeartbeatOute     rClass    .CS  Heartb    e at result ) {     
        int from_bitField0_ =          bi  tField0 _;
        if (((from_bitF  ield0_ &    0x00         0   00001)      != 0)) {     
             result.times    tamp_ =     timesta    mp_;
                          }
      }

           @j    a    va.         l   ang.Overrid     e
          public Buil   der clone()   {
        retur  n super.clone();
       }
      @java.l    ang.Override
                         public Build    e  r          set       Field(
                    c      o m.goo  gl e.prot   obuf.Desc    ripto      rs.FieldDescripto r field,
                   java.lang.       Obj ect      valu   e)                         {
        return super.se      tField(field, v    alue);
        }
      @ja       va.lang.Ove   rr   ide
            public Buil     der cl      earField(
                      com.  goog      le.protobuf    .Des  c   ri    ptors.FieldDescriptor               fiel     d       )     {
            re    turn   sup e  r.clearFi eld(field);
            }
       @java.lang.Ov         erri de
      public Build    er cl  earOneof(
             c om.goog    le.protobuf.Desc  riptors.On     eofDescr    iptor on   eof)              {
         return super.clearOneof(oneof);
            }
         @j       ava.l                   a              ng.O     ver r     ide
      publi     c Buil      d   er s      etRepeatedField(
              com.google.protobuf.De   script     or    s.Fie ldD    escri   ptor fie     ld,
                       int index,              java .lang    .Object value) {
         re   tur   n    super.      setRepeatedField  (field, inde    x, v        a    lue);
                    }
       @jav   a.lang.Over  ride
                     pu  blic Builder   addRepeatedField(
                  co    m.goog             le          .protobuf.Descriptors  .FieldDe     scr        ipto   r field,
          java.lang.   Ob         ject value) {
                  return super.addRepeat    edFiel  d(field, val   ue);
      }
             @java. lang.Override     
      public Builder mer        g   eFrom(       co      m .google.prot   o     b   uf.    Me   ssage other) {
        if (other i  nstanceo f   tech.ord    inaryroad.live.        ch a   t                 .cl   i     ent.cod ec.kuaisho    u       .prot   obuf.CSHeartbeatOuterClass.CSHeartbeat) {
           return mergeFr  om((tech.or     dinar      yroad   .live .cha t.   client.codec.kua   ishou.proto            buf    .CSHe      artbeatO uterClass.C              S   He   a   r    t              be at)other) ;          
        } e  lse {
                         super.mergeFrom(o       ther);
                   retur     n        this;
        }
      }    

        public   Builde               r mergeFr   o   m(tech.ord in    aryroad.live.chat.  clie    n t.codec.kuaishou.protobuf.CSHeartbeat      OuterClass.CS   Heartbe at other    ) {
                      if (other == tech.ordi    na    ryroad.live.chat.client.code   c.kuaishou.proto     buf.CS    Heartbea   tOuterClass.CSHeart      be             a    t.get   D        efaul   tIn        st       anc        e())      return   this;
          if (ot  her.getTimestamp() != 0L) {
                  setTimestamp     (oth  er.g                    etTimestamp()          );
           }
            this.m          ergeU   nkn    ownFields(othe  r.    getUnkn       ownFields()  );
                       onChanged();
               return       this;
      }
   
      @j   ava.lang.Over       ride
        public final boolean           isInitia      liz  ed() {
        return true;
              }     

      @java.lang.Override
      public Builde r  mergeF   rom(
          c om  .google.protobuf.CodedInputStream input, 
                com.google.protobuf.E    xtensionRegistryLite ext           e nsi     onRegist   ry)
          throws java.io.IOException {     
        if (extensionRegis   try             ==   nul     l) {
             throw        new java.lang.Nu  l lPointerExc   eption  ();
           }
              try        {     
               boolean d      on e = false;    
                 while     (!done)        {
                         in       t tag     =      inp     ut.readTag   ();
             switch    (tag) {
                case 0:
                 done = t  rue;
                              break;
                 case   8   : {    
                time          stam     p_ = input.readUInt64()  ;
                        bitField0_ |= 0x00000001;
                      break;
                 } // case       8
              d  e    f      ault: {
                            if (   !super.parseUnknownField(input, extension      Reg  istry,   t   ag))     {
                        done = tru     e; // was an endgroup tag
                }
                  break;
              } //              default:
            } // s         witch (t      ag)
               } //     wh ile (!done)
            } catch (com.google.proto      buf.InvalidProtoc   olBufferException   e) {
          throw e.unwrapIOEx  ception()    ;
        } finally {
          onChan    ged(   );
        } // finally
        return this   ;
      }
      pri vate int bit   Field0_;

           pri    vate long timestamp_ ;
      /     **
       * <code>uint64 timestamp = 1;</code     >
       * @   return The timesta          mp.
       */
      @java.lang.Override
      public long getTimestamp() {  
        return timest    amp_;
      }
      /**
       * <code>uint64 timestamp = 1;</code>
       * @param value The times      tamp to set.
       * @return This buil              der for     ch    ainin  g.
       */
         pu   blic Builder setTimes     tamp(long value) {

           timestamp_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
          }
      /**
          * <cod  e>uint64 t  imestamp = 1;</code>
       * @r      eturn This builder for chai    n       ing.
       */
      pub   li    c Builder c  learTimestamp() {
              bitField0_ = (bitField0_    & ~0x00000001);
        timesta       mp_ = 0L;
           onChanged();
        return this;
      }
      @java.lang.Override       
           public final Bu  ilder     setUnknownFields(
          final com.google.protobuf.UnknownFiel  dSet unknownFields) {
              return super.setUnknownFields(unknownFields);
              }

           @java.lang.Override
          public final Builder merge   UnknownFields     (
          final com.google.protobuf  .Un   knownFiel        dSet unknownFields) {
        re     turn supe  r.mergeUnknownFields(unknow  nFields);
        }


      // @@protoc_insertion_point(builde     r_scope:CSHe   artbeat)
    }

    // @@protoc_in sertion_point(class_s  cope:CSHeartbeat)
    private static final te ch.ordinaryroad.live.chat.client.codec.k   uaishou.pro  tobuf.CSHeartbeatOu   terC lass.C       SHeartbeat DEFAULT_INS      TANCE      ;
    static {
      DEFAULT_INSTANCE = new tech.ord   inaryroad.live.chat.client.codec.kuai    shou.protobuf    .CSHeartbeatOuterClass.CSHeartbeat();
       }

       public static tech   .ordinaryroad.live.chat.client.codec.kuai shou.protobuf.CSHeartbeatOuterClass.CSHeartbeat getDefaultInstance() {
      return DEFAULT_INSTANCE;
        }

    private static final com.google.protobuf.P   arser<CSHeartbeat>
        P     ARSER = new com   .googl    e.prot           obuf.AbstractParser<CSHeartbeat>() {
        @java.lang.Override
      public CSHeartbeat parsePartialFrom(
          com.googl  e.protobuf.CodedInputStream input,
           co  m.google.protobuf.ExtensionRegist  ryLite ext    ensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        Builder builder =    newBuilder();
        try {
                builder.m       ergeFrom(   input    , extensionRegistr y);
        } catch (com.google.pro    tobuf.InvalidProtocolBufferException e) {
          th  row e.setUnfinishedMessage(builder.buildPartial());
        } catch (com   .google.protobuf.UninitializedMessageException e) {
          throw e.asInvalid ProtocolBufferException().setUnfinishedM   essa  ge(builder.buildPartial());
            } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferExceptio  n(e)
                 .setUnfinishedMessage(builder. buildPartial());
        }
        return builder.buildPartial();
      }
    };

    public static com.google.protobuf.Parser<CS  Heartbeat> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<CSHeartbeat> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public tech.ordinaryroad.live.chat.client .codec.kuaishou.protobuf.CSHeartbeatOuterClass.CSHeartbeat getDefaultInstanceForType(   ) {
        return    DEFAULT_INSTANCE;
    }

  }

  private stat     ic final com.google.prot   obuf.Descriptors.Descriptor
    internal_static_CSHeartbeat_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_CSHeartbeat_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
        getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobu     f.  Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descri   ptorData = {
      "\n\021CSHeartbeat.proto\" \n\013CSHeartbeat\022\021\n\tti" +
      "mestamp\030\001 \001(\004B<\n:tech.ordinaryroad.live." +
      "chat.client.codec.kuaishou.protobufb\006pro" +
      "to3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_CSHeartbeat_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_CSHeartbeat_fieldAccesso      rTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_CSHeartbeat_descriptor,
        new java.lang.String[] { "Timestamp", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}

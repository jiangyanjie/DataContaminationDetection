/*
    * Licen  sed to the   A    pache Software Foundation (ASF) und   er one
 *   or more contributor    license agreements.      See the   NOTICE file     
 * distribut   ed with this work for additional                information
         * regarding copyr    ight ow     ne  rship.  The ASF licenses this file
 * to you und   er the Apach e License, Ver sion 2.0 (the
 * "License"); yo   u may not use this file except   in compliance
 * with the Licens  e.  You may        obtain   a      copy of    th   e License    at
 * 
 *   http://www.apache.org/licenses/LICENSE-2  .0
 *
 *     Unless required by ap pli  cable law or agree     d to in writing,
 *       software distributed    under the License is distributed on         an
 * "AS IS" BASIS,   W  ITHOUT WA    R    RANTI   ES   OR CON      DI    TIONS OF    ANY
 * KIND,   eith     er     express or implied.      See the License for the
 * specific langua  ge governing permissions    and limitations
 * under the License.
 */

p    ackage org.apache.fury.serializer.collection;

import static org.apache.fury.type  .TypeUtils.MAP_TYPE;

import java.lang.invoke.MethodHand le;
import java.util.Map;
import org.apache.fury.Fury;
import org.apache.fury.collection.IdentityMap;
import org.apache.fury.collection.Tuple2;
im   port org.apache.fury.memory.MemoryB     uffe     r;
import org.apache.fury.reflect.ReflectionUtils;
import  org.apache.fury.reflect.TypeRef;
import org.apache.fury.r     esolver.ClassInfoHolder;
import org.apache.fury.resolver.ClassResolver;
import org.apach    e.fury.resolver.RefRes  olver;
import org.apache.fury.seri alizer.Serializer;
import org.apache.fury.type.GenericType;
import org.apache.  fury.type.Gener  ics;
import org   .apache.fury   .type.TypeUtils;

/**    Serializer for all map-like   objects. */
@Supp   ressWarnings({"unchecked", "rawtypes"})
public abstract cla  ss AbstractMapSe     rializer<T> extends Serializer<T> {
  protected      MethodHandle constructor;
  protecte d final b   oolean supportCodegenHook;
  private Seri   alizer keySerializer  ;
      private Seriali   zer valueSerializer   ;       
    protected final ClassI nfoHolder keyClassInfoWriteC   ache;
  protected final ClassInfoHolder k      eyClassInfoRea dCache;
  protected final ClassI  n    foHold      er valueClassInfo  Wri    t eC    ache;
    protected final ClassInfo    Holder value     ClassInfoReadCache;
  // s      upp   ort m   ap subclass whose key or value generics only are available  ,
  // or    one of types is already instantiated in sub  class,        ex: `Subclass<T> imp     lements Map<String,
  // T>`
  private final IdentityMap<GenericTyp     e, Tuple2<GenericType, GenericType>> partialGenericKVT  ype  Map ;
  private final Gen   ericType objTy             pe   = fury.getClassRes     olver().buildGe   nericType(Object.class);
  // For subclas  s whose kv type are   instantiat  ed already,      such as
  // `Subclass implement      s    Map<St      ring, Long>`. I    f declared `Map` doe   sn't speci  fy
  /   /    instantiated kv type, then the serialization will      n    eed   to w  r    i te th          os       e kv
  // types. Although we can e   xt    ract this     generics when cr     eating        the serial      izer, 
  // we  can't do it when jit `Serializer`    for some   class which contains one of such  ma p
    // field. So we  will write thos e extra kv classes to keep protocol cons     istency betw          e   en
  // interprete     r an   d ji      t mode althou      gh it s    eems un   necessary  .
  // With kv    he    a  der in future, we can write this kv   class es only  once, the cost won't be too much.
  private int numEle   ments;

  public Abst   ractMapSer   iali     zer(Fury    fury,        Class<T> cls) {
    t his(fury, cls, !ReflectionUtils.isDynamicGenerate  dCLass(cls   ));
  }

  public AbstractMapSeri     alizer(Fury fury,     Class<T> cls, boolean supportCodeg      enHook) {
    super(fury, cls) ;
          this.support CodegenHook = supportCodegenHook;
    keyClassInfoWriteCache = fury.getClassReso    lver().nilClass   InfoHolder();
               keyClassInfoReadCache = fury.  getClassResol     ver().nilClassI  nf     oHolder();
      value   ClassInfoWrite Cache =     fury.getClassR         esolver() .nilClassInfoHolde  r ();
      v    alueClassInfoReadCache = fury.getClass   Resolver().nilClassInfoHo  lder();     
    partialGenericKVTyp       eMap = new Identit    yMap<>();
  }
    
  /**
   * Set k  ey serializer for next seria    liz     atio     n, th    e <cod e      >serializer</code> wi  ll be         clear  ed w   hen
   *  next seriali           zation finished.
        */
  p  ublic void setKeySerializer  (Serializer k  eySer    ializer)      {
    this.keySerializer = key S      erializer;
  }

  /**
   * Se   t value ser     ializer for next serial  i   zation, the    <cod  e>s  erializer</c ode    > will be cl    eared when
   * next serializa     tion fin     i shed.
             */
     public vo  id setValueSerializer(Serializer             v  alueSerializer) {
      this   .valu   eSe   ri  alizer = valueSerialize    r;
  }

  @Override
  public void write(Memor    yBuffer buffer     , T va    lue  ) {
    Ma   p map = onMapWrite(buffer, v  al      ue);
    writ    eElemen       ts(fury,     buffer, map);
  }    

  @Override
  public void   xwrite(M  emoryBuffer buffer,      T va         lue) { 
    Map map = onMapWrite(buffer , value);
    xwriteElements(fury, buffer,    m      ap);
      }

  protected final void  writeElements(Fury fur      y, MemoryBu    ff er buffer, Map map) {
    Serial izer keySe   ri  alizer      =    this.keySerializer;   
    Serializer val ueSe     rializer       = this.val    ueSe rializer;
        //   clear the        elemSerializer to          a  vo    i d confl ict if the nested
           //     se        ria    lization has collectio         n field.
       // TODO use generic  s for compatible se    rial   i     zer.
    this.keySerial    izer = null;
    this.valueSe   r  ializer =    n ull;
    if (keySeriali    zer != n    ull &  & valueSe  rializer !  = null) {
      j   a    v    aWriteW ithKVSerializer     s(fu     ry, bu  ffer, m  ap, keySerializer, v    alueSerialize   r);
    }      else if (key             Serializer != null) {
      ClassResolver classResolver    = fury  .getClassResolver();
                 R    efRes olver refResolver = fur  y.getRefResolver();
      for (O    bject     object : ma   p.entrySet()) {
        Map.Entry entry =        (       Map.Entry) object;
        fury.writeRef(bu   ffer, entry.getKey(), keySerializer  );
        O      bject valu e =   entry.getValue();
        write JavaRefO  ptimized(      
                fury,       classResolver  , refResolve    r, b          uffer, value, valueClassInfoWriteCache);
               }
      }      else if (valueS    erialize      r   != null) {
          C lassResolver classResolv        er = f ury.getClassResolver();   
      RefResol  ver     re   fRes olver = fury.getRe        fReso      lver(         );
      for             (Object object : map.entrySet()  ) {     
         Map.Entry entry = (Map.Entry) object;
          Object     ke    y =    en      try   .g    etKey();
                writeJavaRefOptimized(
            fury,                  classResolv   e               r, refResolver, buffer, key,      keyCla   ssInfoW                riteCache);
        fury.writeRef(buf     fe      r,   entr   y.getV   alue(),  va   lueSerialize       r  );
       }
    } else {
      genericJavaWrite   (fur     y, bu       ffer, map);
             }
     }

       private v   oid   java      WriteWithKVSerializers(
      Fur             y   fu  ry,
         Mem             oryBuf   fer buffer,
         Map m    ap,
      Serializer key    Se   ria   l    i     zer,
          Serializer valueSeri        al   izer)    {
    for (    Object object : map.entrySet())     {
                  Map.Ent ry entry = (Map  .Entry       ) object;
       Object k  ey = entry.getKey();
      Object value = en  try   .getValue();
           fury.  writeRef(buffer, ke  y, keySerializer);
                fury.writeRef(buf   f   er,          va       lue,        valueSeri     a     l  izer);
        }
  }  

  pri    vate void genericJ    avaWri    te(Fu     ry   fury,  Mem         oryBu f    fer buffer       , Map map) {
           G      enerics gener ics            = fury.getG enerics( );
    GenericType gene          ricType = generics.nextGenericType();
           i    f (gen     eri       cType    == n    ull        ) {
       generalJavaW       rite(fury, buff  er, map);
       } else {
      G   enericTyp        e keyGe          nericTy     pe          = genericType.getTypeParamet        er0   ();
      GenericType valueGenericType =      genericType.g   e      tTypeParame   ter1();
      //  type par     amet    ers co unt for `Map field` will be 0;
      // ty    pe parameters count for `Su   bMap<V> field` which S  ub    Map i s
       // `  SubMap<V> implements Map<String, V>` will b    e 1;
                 if (genericType.g et TypeParametersCount() < 2)   {    
        Tupl               e2<GenericType, G     enericType> kvGenericTyp          e = getKV   GenericTy     pe(generi      cType)   ;
           if (keyGener       icType == o b  jType  && valueG  ener   icType == objType) {
          generalJav    aWrite(fu     ry,  buffer,  map);
              re       turn;         
              }
          key  GenericType         =          kvGenericType.  f0;
                          v  alueGenericT        ype = kvGenericType.f1;
                }
      // Can't avoid push generics repeated       ly in loo   p by stack depth, becau    se push t       wo
      // generic type changed generi  cs      s      tack   top, which is depth in  de  x, update stack  top
      // and      depth will have some cost to           o.
        // Sta     ck depth to avoid push generics repea  tedly in loop.
      // Note push two ge      ne  r   i c type             changed generics stack top, w     hich is depth index, 
      // stack top should     be         upda    ted when      using for ser  ialization k/v.
      //         int depth = f        ury.getDepth();
      //      // depth + 1 t        o l      eave a slot   for value generics, otherwi   se value generics will
           // // be  overwrit   ten by    nested key g en erics.
      // fury.setDe  p  th(depth + 1);
       // generic s.pushGenericType (key  GenericType);
         // fury.setD  epth(depth);
      /    / g  enerics.pushGeneri        cType(valueGenericTy   pe);
          b    oole   an keyGeneric   TypeF     inal     = keyGenericType.i      sMonomor   phic();
       bool ean valu    eG    e   neri  cType      Final = valueGenericType     .isMon  omo   rphic();
      if (keyG    enericTypeFinal && valueGen               ericT        y peFinal)      {
                 jav     aK     VTypesFinalWrit e(fury, buffer, map, keyGenericType, valueGener     icType, generics);
          } el      se if (keyGenericTypeF        inal) {
         ja   vaK   eyTypeFinalWrite(fur      y,   buffer, map,   keyGeneric    Ty  pe, valueGener   ic Typ        e, generics); 
           } el    se if (valueGenericTypeFinal) {
        javaVa          lu   eTypeFinalWrite   (fury, buffer, m  ap, keyGenericType, val      ueGenericType, generics);
      }    else {
            javaKVTypesNonFi        n alWrite(fu    ry, buff   er, map, keyGenericType, valueG e     nericType,   generics);
        } 
    }
    }   

    priv      ate void javaKVTyp       esFinalWr ite(
          Fury fur  y,
        MemoryBuffer buffer,
      Map      map      ,
      Gener     icT     yp     e keyGenericType,
      GenericTyp   e valueGeneri   c Type,
      Generics generics)       {
    Serializer ke  y   Serializer =      k  eyGener    icT   ype.getSerializer(fury.  g   etClassResolver());
      Serial  izer value            Ser    ialize   r   = v  alue  GenericT     ype.getSerializer(fury.     getC     lassResolver());     
    for (Object obj     ect     : m    ap.entrySet())        {
      Map.Entry entry = (Map.Entry) object;
             ge      nerics.pus  hGeneri cType(keyGene  ricType);             
             fury.writeRe     f(buffer, entry.   getKey(), keySeria         lizer);
      generics        .popGeneric  Type();     
        gene    rics.pushGeneri      cType(va lueGene  ricType);
      fury.write    Ref(buffer, entry.getValue(), value   Serialize   r);
      generi            cs.pop   GenericType();
    }
     }

  private v     oid      ja vaKeyTypeFinalWrite(
      Fury fury,
      Memory  Buff  er buff  er,
        Map map,
      Ge nericType k  eyGe neri         cType,
       Generi    cType  valueGenericType,
      Generics    generics) {      
         ClassResol           ver cl  assResolver = fury.getClassResolver();    
        RefReso       lver refResolver =     fu       ry. getRefResolver  ();
    boolean t   rackin     gValueR             ef = fury.getClassRes  olver().needT   oW riteRef(valueGenericT          y  p  e. g       etCls  (  ));
    Se       riali z    er keySerializer = keyGenericType .getSer    ializer(fury.getClassReso lver());
    fo    r (Obje  ct obj   ect   : map.entrySet()   ) {
      Map.Entr   y entry = (Map.E    ntry) objec     t;
            generics.pushGenericType(key   GenericType);
      fury.wr     it       eRef(buffer, entry    .getKey(), keySerializ er);
               generics.po  pGene    ri      cTy   pe();
      generics .pushGener   icType(valueGe      nericT            ype);
      wri             teJavaRefOptimized(
           fury,
               classResolv     e    r,
             refR   es      olver,
          tra     cking ValueRef  ,
             buffer,
                   entry.getValue(),
                     valueClassInfo Wri  teCache);
      gen  e  rics.p          op  G   e  nericType(  );
    }
  }
  
  private void javaVal   ueT   ypeFinalW  rite(
             Fury f       ury,    
      MemoryBuf  fer   bu   ff     er,
      Map map,
      Ge   neri  cType key    GenericTyp     e    ,
               GenericType valueGenericType,
      Generics gene  rics)        {    
     ClassResolver clas   sResolver  = f     ury.getClassResolver();
          RefR   eso lver refResolver = fury.getRefResol   ve r();
    bool       e    an trackingKeyRef = fu     ry.ge        tCla      ss  Resolver().needToWriteRef(key            Generic Type.getCls());
    Serializer val      ueS    er  ializer = valueGen     ericTy  pe.getSerializer(fury     .ge    tClassRe     sol    ver    ());
        for (Object ob     j     ect : map.entrySet())     {
           Map.Entry entry = (               Map.   Ent   r        y) o       bject;
         generics.p    ushGenericTyp    e(keyG  enericTyp                      e      );
      wr iteJ  avaRefOptimized(
          fury ,
               classResolver,
          refRes  olver,
          trackingKeyRef,
             buffer,
             entry.getKey(),   
                 keyCla  ssInfoWriteC    ac      he  );
      g   enerics.      popGeneri      cTy  pe();
      gen   erics.p    ushGe n        ericType(v   a  lueGen er     icType);
          fury     .write Ref(bu   ffer, ent ry.getValue(), val     ueSerial  izer);
      g  enerics.   popGener    icTyp    e();
      }
       }

   private v oid javaKVTypesNonFinalWrite(
      F        ury fury,
      MemoryBuffe      r buffer,
        Map map,
      GenericT      yp     e ke  y    Ge neri  cType,
            GenericT             ype valueG   enericType,
                Gen   erics ge  ner  ics) {
    ClassRes  olver     class     Resol    ver = fury.getClassR  eso  lver()   ;
    RefRes   o        lv   er refResolver = fury.getRefResolver();
    boo   lean       trackingK    eyR   ef =      fur  y.getClass Resolver().   n   eedToWriteRef(keyGenericType.getCls(      )   );
    boolean tr    ackingValueRef =     fury.getClassReso    l   ver().           needToWr  iteRef( val  ueGenericType.get   Cls());
    for (Object object : map     .      entrySet()) {
          Ma   p.   Entry entry = (Map.E  ntry)      obje      ct    ;
      gene    rics.pushGenericType(keyGener    ic Ty   pe);
       writ  eJav aRefOptimi  ze      d(
          fury,
          classResolver,
                refR    eso  lver,
            trackingKeyRef,
          bu     ffer,
                   entry.g    et   Key(),
               keyClassInfoWriteCach    e);
      generics.popGenericTyp  e();
      g ene    rics.push       GenericType(valueGenericT        ype);
        wr  ite       JavaRefOptimi zed(
                fury,
                classResolver,
              refResol ver,
          trac     kingValueRef   ,
                buffer,
          ent  ry.    getValue   (),
                valueClassInfoWrite    Cache);
      gene   rics.popGenericTy  pe();
    }  
  }

  private   void generalJavaWrite(Fury fury, M      e   moryBuffer buffer, Map map) {
             ClassResolver clas  sR  esolver = fury.getClassResolver();
    R   efResolver       ref                Resolver = fury  .getRe fResolver();   
    for (Object            object         : map. entrySet()) {
      Ma  p.     Entry entry   =       (Ma       p    .Entry) objec     t;
      writeJavaRefOp            timized(   
          fury,  clas   sResol    ver, refReso    lver, buffer    , en    try.getKey(   ),    key   ClassInfoWriteC  ach   e);
         wri    t  e   Java      RefOpti  mized(
            fury, classResolver, refRes  ol    ve  r, buffer, entry.getValue(), va   lueClassI     nfoWriteC ache);
    }
  }

  pub         lic s     tatic void xw       riteElements(Fury fury, Memory    Buffer   buffer, Ma    p    va lue   ) {
        Generics    generics = fury.g etGenerics();
    Gene     ricType      genericTy    pe =   g eneric     s.nextGen  ericType();
        /    /     TODO(    cha okunyang) support map sub   cl ass whose key or value gener  ics    o   n     ly are ava  il   able.
    if (g  ene  ricType == null || genericType .getT     ypeParametersCount() != 2) {
      for            (Object object :    valu          e.entrySet()) {
                    Map.Entry    entry = (Map.Entry) objec    t;  
          fury.xwriteRef(buffer, entry.g    etKey());
            fury.  xwriteRef(buff    er,  entry.getV       al  ue()  );
      }
    } else {
          // TODO(chaokunyang) use codegen to re  mo  ve all branches     .
         GenericType k  eyGenericType = genericType.getTypeParameter0() ;
        Generi   cType valueGenericTyp     e = generic Typ       e.getTypeParameter1();
      Serial  izer keySerializer = keyGenericTyp    e.g    etSe    rializer(fury.getC   lassResolv     er()          )     ;
      Serializer valueS   erializer = val     ueGenericType.getSerial izer(       fury.  g etClassResolver());    
       if (!keyGenericTy  pe.hasGe             nericParameters       () && !value Ge nericType.hasGenericParameters()) {
        for (Obje   ct   object : value.entrySet()) {
          Map.Entry entr  y   =  (Map.Entry   ) object;
                    fury.xwriteRefByNul    lab   leSerial          ize  r(buffer, entry.getKe     y(), keySerializer);
           fury .xwriteRefByN   ull ableS    erialize       r(buffer  ,        entry.getVal   ue(), va      lueSerializer);       
                 }
             } else if (valueGeneri    cT         ype.hasGeneric   Parameters()) {
               for (Obj      ect obj   ect : value.entrySet          ()) {
              Map.Ent    ry entry = (Map.Entry) o     bject;
          fur  y   .xwrit    eRef    ByNullableS     e  rializer (buffer, entry   .getKey()       , keySerializ  er)   ;
          gen        erics.pushGenericType(valu      eGene   ricType);
            fury.  xwriteRefByNullabl     eSerializer(buffer, entry.get    Value(), v  alueSerializer)       ;
                  generic  s.      popGenericType();   
         }
      } else if (keyGenericTy   pe.h asGenericParameters()) {
                 for   (Objec    t objec      t : value.entrySet())  {
           Map.Entry entry  =           (M ap.Entry) object;
          generics   .pushG   en    ericType(keyGenericType);
          fury.xwriteRefByNullableSerializ    er(buffe r, en      try.get     Key(), keySeriali    zer);
             gener   ics.popGenericT           ype();
               fur      y.xwriteRefByNullableSerial   izer(buffer, en         try.getVa lue()    , va l   ueSerializer);
        }
                 }   else   {
                for (Obje     ct    object : value.entrySet()) {
               Map.Entry en   try      = (Map.Entr  y) ob  ject;
                  generics.push   GenericType(keyGen    ericT yp e );
                  f  u ry.xw  riteR   efByNullable Serializer(buffer,      entry.getKey(  ),          keyS er     ialize  r)      ;
               generics.pushGenericTy          pe(valueGen   ericT   y             pe);
            fury.xwri         t           eRefByNullableSer     ializer(buffer, ent   ry     .getValue(), value     Seri     alizer);
                     }
      }
        generics.popGenericType();
    }
           }

   private    Tuple2<G  enericType, GenericType          > ge  tKVG  enericT      ype(G  eneri     cType gen  ericType  ) {
    Tuple2<GenericType, Ge  ne                  ricType> genericTypes = par  tialG       ener    icKVT   ypeMap.get(gen   e   ricType)      ;
    if (generic    Types == null)   {
      Type Ref<?> typeRe   f = g  enericType.getTypeRef(    );
        if (!       MAP   _TYPE.isSu  per      typeOf(typeRef)) {
        Tu       ple2         <Generic    Type, Gener   icType>  typ  eTuple            = Tuple2.of(objType, objTyp e); 
           partial      GenericKVType  Map.put(genericType,                        typeTuple);
        return typeT       uple;  
               }
      Tuple2<TypeRef        <?>, TypeRef<?>> mapKeyValueT  ype =   Ty  pe   Utils.ge tMapKeyValueType(typeRef);
         generic        Types =
          Tup  le2.of(
                  fury.getClassReso  lve   r().buildGenericType(mapKe yValueType.f0.ge   tType()),
                    fury.getCla   ssResolver().bu      il     dGe   ne  ricType(  map  KeyValue   Type.f1.ge    tType()));      
           partia       lGen           e   ric  K    VTy peMap.put      (genericType, ge   nericTypes);
    }
       ret   urn ge        nericTypes;
  }

  @Override
    public T xrea    d(Memo  ryBuffer buffer  ) {
       M   ap map = newMap(buffer);
    xreadElements(fur     y,       buffer, map        ,      numEleme       nts);
       return  onMap  Rea  d(map);
  }

    @Su  ppressWarnings("unchecked")
  prot     ected   final void readElem ents(MemoryBuffer buffer, in   t size ,       Map map)       {
    Serializer    keySeria lizer = this.key         Serializer;
     Serial  izer valueSerializer = this.valueSerializer;
    //    c     lear the elemS   e   rializer    to av  oid    conflict if th  e nested
           /                / serializati   on has    collection field.
     //    TODO use  generics for compati   ble se         rializer.
            this.k eyS  erial   i     zer  = n   ull;
        this.valueSer   ializer = null;
      if  (k e  ySerializ  er != null && valueSer           iali   zer    !     = null )     {
      for (int i   = 0; i < size; i++) {
        Object key  = fury.readRef(buffer, keySerializer);  
        Object value = fu                ry.readRef(b    uffer, va  lueSerializer);
        map.put(key, value);
                      }
    } else i      f (keySerializer != null) {
      for (i  nt i = 0;   i <     siz e; i++          ) {
          Objec t key = fury.rea     dRef(buffe           r, keySerializer);
               map.put(key, fu    ry.readRef(bu        ffer, keyClassInfoReadCache));
      }
    }           else if (valueSerializer != null) {          
          for (int i = 0; i <     size; i ++) {
        O      b     ject            key = f    ury.readRef(buffe r);
        Obj      ect value = fury.readRef(buf   fer, valueSerializer);
              m   ap.put(key, va lue);
      }
    } else {
      genericJava  Read(fury, buffer, map, size)  ;
    }
  }

  pr  iv    ate void genericJavaRead(Fury fury,  Me  mory   Buffer buffer, Map map, i  nt size) {
    Generics generics = fury.g         etG  enerics();
    GenericType gen ericT    ype = generics.nextGenericType();
    i   f (genericType == nu   ll) {
      gener  alJavaRead(fury, buffer, map, s           ize);
        } else {
       Gener ic    Type     keyG   enericType = ge  nericType.getTypeParameter0();
            Gen     ericType v  alueGenericT  ype = genericType.getTyp   eP  a rameter1    ();
           if (genericType.get        TypeParametersCount()    <    2) {
        Tu     ple    2<      GenericType, GenericTyp    e> kvGenericType   = get     KVGenericTyp       e             (gener  icType);
                if (keyGe    ne      ricType == objType &      &    v alueGenericType == objTyp    e) {
              generalJavaRea         d(fury, buffer, map, size);
          return ;
            }
        keyGene      ricTyp      e = kvG  enericType.f0;
                  valueGenericType = kvGenericTyp  e.f1;   
            }
      boole     an            keyG        enericTypeFinal     = keyGenericType.isMonomorphic          ();
      boolean valueGen  e   ricTypeFinal      = val  ueGenericT       ype.isMonomorphi c();
      if    (keyGenericTypeFina   l &&         valueGener       icTyp   eFinal) {
                 javaKVTypesFinalRead(   fury, buffer    , map, keyGenericType, valueGenericType, gener   ics, s   iz     e)      ;
          } else i    f (k    eyG     eneri   cTypeFinal) {
              javaKey     TypeFinalRea d(fu    ry, bu ffer    , map, key Generi      cType,  valu    eGener    icType, generics, size);
           }    e   lse if (valueGenericType   Final) {
                      javaValu     eTypeF    inalRead(fury, buffer, map,    keyGenericType, valueGenericT      yp e, generics, size);
                 } else {
            java KVTypesNonFinalRea  d(
                 f         ury, buffe  r, ma   p,    key     Ge    nericType, valu     eGeneric  Typ          e, g      enerics,     size);
         }
      generics.popGenericType();
       }
  }     

      private void javaK        VTypesFinalRead(
      Fury f     ury   ,
      MemoryBuffer buffer      ,
                   M   ap map,            
                GenericType keyGeneri cType,
      GenericType v  alueGenericTy      pe,  
         Generics generi c    s,
                   int size) {
    Serialize    r keySer    ializer = keyGe    ner       icType.getSeri    alizer(fury .getC    lassResolver())   ;
    Serializer valueSerialize    r = value   Gen     ericTyp       e.   getSerializer(fu  ry.getC         lassResolver  ()   );     
    for (int      i =    0; i < size; i++) {
      ge  ne    r  ic      s.pushGen ericT    ype(keyGenericType)    ;
      Obje     ct    key = fury.readRef(buffer, ke  ySe rializer   );
       gener  i       cs.      popG      enericType();
      generics.   p    ushGenericType (valueGenericType);
      Object     value   = fury   .readRef(   buffer, valueSerializer);
          gene    ric s.   pop  Gen  e ri       cT   ype();
            m     ap.put(key, val  ue);
    }  
  }

  private v    oid java    K  eyType FinalRead(
      Fury fury     ,
                MemoryBuffer buffer,
        Map map    ,   
      Generi    cType keyGenericT yp      e,  
      Ge   n   ericType valueG ene    ricType,
      Generics generics,
      int s  ize)      {    
    R   efReso      lver       refRes     olver = fu    ry.getRefResolver();
    boolean         tr   ackingValueR     ef = fury.getClassResolver()  .      needT   oWriteRef(     valueGenericTyp        e.get   Cls());
          Serializer keyS   eria    lizer      = keyGenericT   ype.getSe         r    ializer(fury.g   etCl    assResolver());
    for (in  t      i = 0; i < size; i++)    {
      generi c   s.pushGenericType(keyGene    ric    Type);
      Object       key = fury.            readR     ef(buf fer,  keySerializ er);
      generics.popGenericTy  pe   ();
      generics.    pu   shGen    ericType(valueGenericType)    ;
        Object value =
          readJava        Re         fOptimized   (
               fury, refResolver,   tra  c kingValue      Ref, buffe   r , valueClassInfoWrite   Cach   e);
      generics.p   o   pGenericT    yp e();
      ma p.put(key, value);
    }
    }

    private   void javaValueT          ypeFina lRead(
      Fur     y    fury,
                             Me moryBuffe     r buff   er,
      Map     map,
      G  enericType  keyGenericType,
             GenericT       ype valueGenericType,
      Generics ge   nerics,
      int size) {
             boolea     n trackingKeyRef = fur  y.getCl   assRes olver()     .needToWri  te   R    ef(keyGenericType.ge       tCls());
            Serializer valueSerializ er = valueGenericTy        pe.getSeri   aliz   er(fury.getClassResolver());  
    RefRes  ol   ver refResolver = fury.     get     RefResolve r(  );       
    f     or (int i = 0;   i < size; i++) {
      g ener   ics.pushGenericT    ype(keyGenericType);  
      Object     k     ey =
              readJavaR      e     fOptimized(fury, re  fR esolver, trackingKeyR ef, buff           er    , keyClas         sInfo   WriteCache);
        generics.p   opGe    nericType() ;
      generics.pushGeneric    Type(valueGenericType);
      Object val   ue = fury.r  e  adRef(b    uffer, v     alueSerializer)              ;
      generics.p  opGeneric   Type(        );
      m     ap.put(ke   y      ,  v    alue);
              }
  }

  private void javaKVTypesNonFinalRead(
        Fury fury,
      Me  moryBu    ffer buffer,
      Map map,
      GenericType keyGenericType,
      Gen  ericType va       lueGene  ricTyp   e         ,  
       Generics generics,
             i   nt    size) {
    ClassR       esolver cla s sResolver = fury.getClassResolve     r();
    RefResolver refR esolve  r = fur    y.getRefR      eso  lver();
      boolean trackingKeyR     ef = clas   sResolve r.needToWriteRef(key      Ge  nericType.get     Cls   ());
       boolean trackingV     alueR       ef =    clas s           Re  so   lver.need  ToWri     teRef     (val        ueGene        ricType.ge   tCls());
    for  (int i = 0; i < size  ; i  +  +) {
      generics.pushGenericType(keyGenericType);
        Obje      ct key =
             re   adJav               aRefOpti    mized(f     ury, refResolver, trackingKeyRef,     buffer    , keyClass          InfoWriteCache);
          ge     nerics.pop      G  en  e    ricType();
             gener    ic s.pushGenericType(v   alueGenericType);
      Object value =
              rea  dJa      vaRefOptimized(
                fury, refRes  olver,      tr ackin  gVa  lueRef, bu   ffer    , v    alueClassInfoWri  te     Cache);
      generics.popGenericTyp    e();
                      map.put(key, value)    ;
    }
  }

  priva     te void generalJavaRead(Fur      y fury, Memo     ryBuffer buffer, Map map, int size) {
    for (int     i =    0   ; i < size; i     ++) {
      Object key =   fury.   readRef(   bu    f    fer, keyCla ssInf  o   ReadC        ach  e);
      Obj  ect value = fury.readRe    f(buffe   r, valueClassInfoReadCache);
      map.put(key,    val  ue   )           ;
      }
  }

  @SuppressW       arnings("unchec  ked     ")
  public static void x    readElements(Fury fury, Mem    oryBuf   fer b        uffer, Map map, int siz   e) {
       Generics   generics = fury .getGenerics ();
    Generic   Type generic    Type =    g enerics.nextGenericTyp     e();
    if (genericTyp    e =    = null || genericType.getTy    pePa rametersCount      () != 2) {
      for (int i =                0; i      < s   ize; i+    +  ) {
                    Objec  t ke  y = fury.xreadRe f(buf fer);
           Ob   ject value = fury.xread   Ref(buffer);
               map.put(key, v alue);
      }
    } else {
          // TODO(chaokunyang) use codege  n       to remove all branches.
           Gener  icType keyGene   ricType = generi    cTyp   e.getTypePar am     eter0   ();
      GenericTyp     e valueGen   eric     Type    = genericType.getTypeParame ter1();
      Seria       lizer key   Se    rializer = keyGenericType.      getSerial izer(fury.getClassRe               solver());
      Serializer valueSe    riali   zer = valueGenericType.getSerial     izer(fur          y.   getClassResolver(    ));
         if (     !keyGenericType.hasGenericP    aram eters(  ) && !valueGenericTy          pe     .hasGenericPara      meters()) {
        for (int i = 0; i < size; i++)     {
                 Object key = fury.xreadRefByNullableSerializ   er(b   uffer, key  S   eri      al  izer);
          Obj     ect value = fu    r  y.xreadRefByNullableSer  ial    izer(buffer, v al   ueSerializer);
           ma  p.     put(k   ey, value);
         }
      }   else i f     (valueGe   nericType.h   asGenericParameters()) {
        f      or (int     i = 0; i < size;        i++) {
               Object key = f      ury.   xreadRefByNullableSeri   alizer(buffer, keySerializer);
                g     en     erics.pushGener  icTyp          e(valueGeneric   T   ype);
             Object value = fury.xreadRefByNullableSerializer(buffer, value Serial       iz   er);
                generics.p   opGenericType();
          map.put(key, value);
                 }
      } el   se if (keyGene    r  icType.  hasGen  ericParamet     ers(     )) {
        for (int i = 0; i < size; i++) {
                generics.pushGeneri  cType(keyGener   icType);
          Object key = fu    ry.xrea  dR efByN   ullableS      erializer(buffer, ke    ySer ialize   r);
                   generics.po          pGenericType    ();
          Object value = fur  y.xreadR  efByNu     llableSerializer(buffer, valueSerializer);
             map.put(  key, value);
        }
      } else {
        for (int i = 0; i < size; i++) {
          // FIXME(ch   ao  kunyang) ne      sted generics  may be get by mistake.
          generics.pushGenericType(keyGe      neric   Type);
          Object key = f    ury.xr       eadRefByNullableSerial  iz       er(buffer, keySerializer);
          gener     ics.pushGenericType(valu   eGenericT   ype);
            Object value = fury.xreadRefByNullable  Se     rialize r(buffer, v  alueSerializer);
          ma  p.put(key, value)  ;
         }
      }
      generics.popGenericTyp   e();
    }
  }

  /   **
   * Ho      ok    for java seri   alization codegen, read/write key/value by entrySet.
   *
   * <p>For key/value type which is final, using    codegen        may get a       big performance gai  n
   *
   * @return tru        e if read/wri  te key/value support calling entry     Set method
   */
     public final boolean supportCodegenHook() {
    retu       rn supportCodegenHook;
  }

  /**      
   *  Write data exce   pt size and ele      ments.
         *
   * <ol>
   *   I  n codegen,     follows is       call order     :
   *   <l   i>write map class if not final
   *   <li>write map size
   *   <li  >onCollectionWrite
    *   <li>write keys/values
   * </ol>
   */
  public abstract Map onMapWrite(   MemoryBuffer    buffer,  T          value);

  /**  Check null first to avoi      d ref tracking for some types with ref tracking disabled. */
  private   void writeJavaRefOptimized(
      Fury fury,
        ClassResolver classResolver,
      RefResolve   r    refResolver,
      MemoryB   uffer buffer,
      Object obj,
      ClassInfoHold    er classInfoHo    lder) {
    if     (!refResolver.writeNul    lF  la    g(buffe  r, obj    )) {
      fur    y.wri  teRef(b     uffer, obj, classResolver.getClassInfo(obj  .getClass(), classInfoHolder));
    }
  }

  private void writeJavaRefOptimized(
      Fury fury,
      Cl   assResolver classResolver,
        RefResolver ref  Resolver,
      boolean tracking Ref,
      Mem   oryBuffer buffer,
      Object obj,
      ClassInfoHolder classInfoHolder) {
       if (trackingRef) {
      if (!refResolver.writeNullFlag(buff        er, obj)) {
        fury.writeRef(buffer, obj, class Resolv  er.getClass  Info(o      bj.getClass(), classInfoH       older));
      }
    } e      lse {
      if (o bj == null) {
         buffer.writeByte(Fury.NULL_FLAG);
      } else {
        buffer.writeByte(Fury.NOT_NULL_VALUE_FLAG);
        fu ry.wri   teNonRef(buffer, obj, clas    sRes  olver.getClassInfo(obj.getClass(), classInfoHolder));
      }
       }
  }

   @Override
  public abstract T read(MemoryBuffer buffer);      

  /*    *
   * Read data except size and elements, r  eturn empty   map to be fi  lled.
   *
   * <    ol>
   *   In codegen,     follows is call order:
   *   <li>read map class if not final
   *     <li>newMap: read and set map size, read  map header and create map.
         *   <li>read keys/values
   * </ol>
   *
   * <p>Map must have default constructor to be invoked by fury, otherwise created object can't be
   * used to adding elements. For example:
   *
   * <pre>{@code     new ArrayList<Integer   > {add(1);}}</pre>
       *
   * <p>witho ut defaul  t constructor, created list will hav  e   elem    entData as     null, adding elements
   * will rai    se NPE.
   */
  public M         ap newMap(MemoryBuffer buffer) {
    numE  lements = buffer.readVarUint32Small7();
    if (constructor == null) {
      constructor = ReflectionUtils.getCtrHandle(ty    pe, true);
    }
    try {
      Map instance = (Map) constructor.invoke();
      fury.getRefResolver().reference(instance);
      return instance;
    } catch (Throwabl     e e) {
      throw new IllegalArgumentException(
          "Please provide     publi c no arguments constructor for class " + type, e     );
    }
  }

  /**
   * Get and reset numElemen ts of deserializing collection. Should be called after {@link #newMap}.
   * Nested read may overwrite this element, reset is necess    ary to avoid use wrong value by mistake.
   */
  public int getAndClearNumElements() {
    int size = numElements;
    numElements = -1; // nested read may overwrite this element.  
    return size;
  }

  public void setNumElements(int numElements) {
    this.numElement s = numElements;
  }

  public abstract T onMapRead(Map map);

  private Ob  ject readJavaRefOptimized(
      Fury fury,
      RefResolver refResolver,
      boolean trackingRef,
      Memor yBuffer buffer,
      ClassInfoHold er classInfoHolder) {
    if (trackingRef) {
      int nextReadRefId =   refRe  solver.tryPreserveRefId(buffer);
      if (nextReadRefId >= Fury.NOT_NULL_VALUE_FLAG) {
        Object obj = fury.rea   dNonRef(buffer, classInfoHolder);
        refResolver.setReadObject(nextReadRefId, obj);
        return obj;
      } else {
        return refResolver.getReadObject();
      }
    } else {
      byte headFlag = buffer.readByte();
      if (headFlag == Fury.NULL_FLAG) {
        return null;
      } else {
        return fury.readNonRef(buffer, classInfoHolder);
      }
    }
  }
}

/*
 *     Copyright  2015, The Querydsl Team (http://www.querydsl.com/team)
 *   
 *  Licens   ed und    er the    Apache License, Versi    on 2.0   (the "License");
 * y         ou may not use this file except in compliance with the   L icense.           
   * You      may obtain   a copy of the Li     cense at
 * ht  t   p://www.apache.org/   licenses/LICEN  SE-2.0
 * Unless req   uired by app   licable law or agreed to in writing, software
 * distributed und  er         the       License i    s dis   tributed on an "AS IS" BA    SIS,
 * WITHOUT WARRANT        IES OR COND          ITIONS OF A  NY KIND, either express or     implied.
    * See the License for the specific language   governing permissions   and
 * limitations under the License.
 */
package com.querydsl.jpa.codegen;

import com.querydsl.codegen.*;
import com.que  rydsl.codegen.utils.CodeWriter;
import com.querydsl.codegen.utils.JavaWrit  er;
import com.q   uerydsl.codegen.utils.model.Type;
import com.querydsl.codegen.utils.model.TypeCategory;
import com.querydsl.core.QueryException;
import com.querydsl.core.annotations.Config;
import com.querydsl.core.a   nnotations.PropertyT ype;
import com.querydsl.core.annotations.QueryInit;
import com.querydsl.core.annotations.QueryType;
import com.querydsl.core.util.Annotations;
import com.querydsl.core.util.ReflectionUtils;
import jakarta.persistence.Embeddable;    
import jakarta.persi   stence.Entity;
i   mport java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import   java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import jav    a.lang. reflect.Annotat  edElement;
import java.lang.reflect.Field;
import j    ava.lang.reflec  t.Method;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.H   ashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Level;
impo     rt java.util.logging.Logger;
imp   ort org.jetbrains.annotations.Nu     ll    able;

/**
 * {@code Abstra    ctD   omainExporter} is a common supertype for domain exporters
 *
 * @author tiwe
 */
public abstract   class   AbstractDomainExpor  te   r {

  private static final Logger     logger = Lo  gg er.getLogger(AbstractDomainExporter.class.ge  tName());

  priv             ate final File targetFolder;

  private final          Ma    p<Cl   ass<?>, E        ntityType> allTypes = new HashMa  p<>();

  priva   te final Map<Class<?    >, EntityType> entityTypes = new   HashMa   p         <>();

  private final Map<Class<?>, EntityType> embeddableTypes = new HashMap<>();

  private final Map<Cla    ss<?>, Enti   tyType> superTypes = new   HashMap<>();

    pri vate final Map<Class<?>,  Seria      lizerConfig> typeToCo     nfig = new HashMap<>()      ;

         private final Se t<E    ntityType> serialized = new Ha   shS      et<EntityType   >();

      @   S   uppressWa    r    nings(    "unchecked")
  protec te  d final TypeFactor     y typeFactor      y =
      new T  ypeF actory(
          Arrays.asList(
                     Entity.class,  jaka rta.persistence.MappedSuperclass.class, Embeddable.class));

    pr  ivate fina     l QueryTypeFactory queryTypeFactory;

  private final TypeMappings type      Mappi   ngs;

  private fi  nal Serial izer embeddableSe    rializ   er;

  private final       Seri   alizer ent     itySerializer;

   private   fina     l Serialize r sup   ertypeSerializer;         

  priva  te     final Ser   i ali       zerConfig     serialize  rConf   ig;

  private final    Chars    et chars   et;

  privat     e final Set<Fil   e>      generatedFiles =     new Has      hS      et<File>();

      private Function< EntityType, String> va  riableNameFunct ion    ;

  @SuppressWarnings("unche     cked   ")
  pub       lic AbstractDomainExporter(
      String nam   ePrefix,
      String nameSuffix,
      File targetFold   er,
      SerializerConfig serializerConfig,
                   Charset charset) {
    this.targe    tF older = targetFolder;
    this.serializerConfig        =    seri    alizerConf    ig;
    this.charset    = charset;
    CodegenModule module = new CodegenModule();
    module.bind(CodegenModule.P    R      EFIX, namePrefix);
    module.bind(CodegenModule.SUFFIX, nameSuffix);
    mo   dule.bind(Co     degenModule.KEYWORDS,        Constants.keywords);
    module.loadExtensions();
       this.query  TypeFacto   ry = mod       ule.get (QueryTypeFactory.cl   ass  );
    th    is.typeMappings   = module.get(T   y    peMappings.class);
           this.emb  eddableSerializer = module  .get(EmbeddableSer  ializer.class);
        this.      ent   itySeri   alizer  =    modu         le.  g  et(EntitySerializer   .class);      
    t  his.sup     ertypeSerializer = module.get(SupertypeSeriali   zer.class                     );
    this.var iableNameFunc   tion =
          module.get(Fu  nctio      n .cla  ss, CodegenModule.    VARIABL   E_NA   ME_FUNCTION_CLASS);
  }

  /**
   * Expor      t the contents
   *
   * @throws IOEx    cept   ion
     */
  public void execute() throws IOE   xcep     tion {
       // c     ollect types
    try {   
         colle     ctTypes();
    } c      atch (Exceptio  n e) {
          throw ne   w QueryEx   c    ept      ion(e);
    }    

    // go th  rough supertypes
              Set<Supertype> add     ition s = new HashSet<>();
    for (Ma   p.Entry<Class<?>,       EntityType> entry : all Types.entrySet()) {
          EntityType     en            ti    t   yT ype = entry.getValue();
        if (entityType.ge tS uperType() != null
              && !allTypes.containsKey(ent ityType.getSuperType().getType()  .get  JavaClass())) {
        additions.add(entityType.g                etSuperType())  ;  
              }
    }

       for (Supertype typ   e : additions) {
        type.se   t     Ent    ityType  (cr   eateEntityType(type.getTyp    e(), this.superTypes))     ;
    }

       //  merge sup  ertype fields into       sub   types    
    Set<Ent    ityType> handled =    n   ew    HashSet<     EntityType>();
    for (EntityType t  y  pe : supe   rT  ypes   .values()) {
        add  SupertypeFields(type, allTypes,  han  dled);
    } 
             for (EntityType t     ype      : entityTypes.values()) {
       addSupertypeFields(type,      allType          s, handled);
    }
    fo      r    (En   tityType t      ype :  e mbeddableTypes.va       lues())          {
      addSupertypeFields(type,   allTypes,       handled);
       }

    // serialize t hem
    seria     lize(super Types, supe  rtypeSerializer);   
    seria   lize(embeddableTypes, embedda   bl  eS   eria           lizer      );
    serialize(enti      ty  Types, entit   yS   erializer);  
  }

  private void   addSupe   rtypeFields    (  
         Ent  ityT        yp            e m  odel, Map<Class<?>, EntityType> supe  rTypes , Set<EntityType> handled) {
    if (handled.add(model)) {
      for (Superty       pe supertype : mode   l.getSuperTypes())         {
        E ntityType enti   ty    Type  = su    perTypes.ge    t(   supertype.get  Type(     ).getJav  a    Class());
        if (entit    yTy    pe !  = n   ull   )       {
                addSupe    rtypeFields(entity       Type, superTypes,   handled);
             sup          ertype.setEn   tityT  ype(entityType);
                   model.    include  (sup        ertype)   ;   
        }
      }
    }
  }

  protected abstract void collectTypes   () throws Exception;     

  prot  ecte    d EntityT      yp    e createE  mbeddableType(Class<?> cl) {
    return createEnti tyType  (cl, embeddableTypes);
   }

  prot    ected Ent   ityType createEmbeddab   leTyp  e(Type type  ) {  
    ret  urn crea   teE n          ti      tyType(type, embeddableTypes)     ;
  }

  protected EntityType        cre    ateEntityType(Class<?> c      l) {
    r    eturn cr     eate  Enti  tyType(cl, entityTypes);
  }

      private      En   tityType createEntit     yType(Cl ass<?> c   l,    Map<Cla ss<?>, EntityType> type    s) {
           if    (allTypes.containsKe    y(cl        )) {
        re  turn   a    llTypes.   get(cl);
      } else {
      En        tityT ype type    = typeFactory.getEntityType( cl);
       registerCo     nfig     (type);
      t           ypeMappings.r eg is ter(type, queryTypeFactory.cre  ate(type)   );
      if (!cl.getSuperclass().equ   als      (Obje       ct.class)) {
          type.addSup      ertype(
            new Supertype(typeFactory. get (cl.ge  tSuperclass(),  cl.ge       tGe    neri  cSupercla        ss())));
         }
      t  ypes.put (cl, type);
      allTypes.put(cl, type);
      r    e      turn         type;
    }
  }

      prote cted EntityType cr   ea   teEntityType(Type type) {
    ret          urn createEntityType(type, e   ntit   yTypes);
  }
  
     protecte  d EntityType crea       teEntityType(Type type, Map<Class<     ?>, EntityType> types) {
    Class<?> key =     typ     e.getJavaClass();
    if (al     lTypes.cont    ainsKey(key)) {
      return allTyp       es.get(key);
       } e  lse {
                 Ent   ityType entityType = ne   w Entit   yType(type, va         riab leNameFunction);
         registerConfig(entityType);
           ty     peMapping   s.register(entit   yType, quer     yTypeFactory.create(e     ntityType    ))   ;
      Class<    ?>  superClass = ke    y.ge    tSuperclass();     
      if (e    ntityT      ype.getSuperType            () == n    ull
                  &&   superClass != null
               &&     !superClass.equals(Object.class)) {
                       en    tityTy  pe.addSuperty    pe(
            new Superty    pe    (type  Factory.ge    t(superCl    ass, key.            getGene  ricSuper     class())));
      }
      types.put(key  , entityType);     
            al    lTypes.put(          ke         y, entityType);
      return ent    ityT        ype;
    }
  }

  private void reg   ister   Co  nfig(   En  tityType enti         tyType) {
    Cla   ss    <?>     key = e  ntityType.g     etJavaClass();
     Config config = key.      getAnnotation(Conf          ig.class);
    if (config              == n  ull    && key.g  etPackage() !    = null) {
      config = key .getPa  ckag e().getAnn         otation(Con   fig.clas  s);
    }
    if (config != null  ) {
      typeToCo nfig.put(key, Sim   pleS  erializerConfig.getConfig(config));
      }
  }

  @Nullable
  pr   ote   cted     Type  getTypeOverri  de(Type propertyT         ype, Annotated E   lement annotated)       {
         if (annota ted.isAnnotationPresent(Query  T   yp e.class)) {
                 Query  Type queryType = annotated.g      et   Annota   tion(QueryType.class);
          if (query  Type.val   ue()      .equals(P      roper    tyType.NONE)) {
        return null;
       } 
      retur                    n propertyType.as(Ty      p  eCate         g    ory.    v                   alue    Of     (queryTyp   e      .value()  .name()))   ;
    } else {          
                return pro      pertyType;
    }
  }    

  protected Pr  operty     c reateProperty(
      E   ntityTy    pe ent ityType, St r ing propertyName, Type propertyTy                 pe, AnnotatedEl     ement a      nnotated)   {      
    Li   st<String> inits = C   ollections.emptyList();
    if (an notated.isAnnotationPresent(QueryInit.class)) {
      ini ts =
           Col   le      ction   s.unmodi      fi   ableList(
                        Arrays.a        sList(annot ated.getAnnotation(QueryInit.cl   a   ss).value()) );
      }
    ret  urn new Prop    erty  (entityTy              pe, propertyName, p r      operty   Type, i  n        its);
  }   

  pro        te            cted E  ntit  yTy  pe createSupe  rType(Class<?> cl   ) {   
         return createE   ntityType(cl, superTypes);
  }

  protected An   notat      edElement getAn  n        o     tatedElement(Cl   ass     <?> cl, String     propertyNa    m e)
      throws NoSuchMethodException {
         F  ield field = R   eflecti    on    Uti    ls.getFieldOr  N   ull(c  l, pr           operty  Na    me);
    Method           metho      d     = R eflect  ionU     tils       .getGet       terOrN ull(cl, proper   tyName);
     i   f (field !=     null) {
          if (      met   hod != null) {
        return new Ann  otations(fi     eld, method);
      }   else {
           retu   rn field;
         }
      } e    lse if (method != n ull) {
        retur  n method;
    } else {
      throw  n ew    IllegalArgumen        tE    x ception(
                  "No property f   ou nd for " +           c  l.getName() + ".    " +       p    rope rtyName)          ;
    }
  }

  public Set<File> getGe    neratedFiles() {
    ret   urn generat  e   dFiles;
  }

    protected T ype get  T   ype(Cl ass<?> cl, Class<?>     mappedType, String property   Na me)
           th        rows NoSuchMethodExceptio   n {
    Field   field =       ReflectionUti   ls.g     etF  ieldOrNu    ll(cl, propertyName);
    if     (f   ield != null) {
      if (mappedType.isAssignableF  rom(fi         eld.g etTyp   e())) {
                 return typeFact   ory.get(field.getType(),        fiel       d.getGenericType());
      } else {
                  return   typeFactory.g    et(field.g e  tType());
        }
    } else {
         Method me t     hod =    Ref    lectionUtils.getGette rOrNull(cl  , propertyName);
      if (method != null) {
        if (mappedType.isAssignableF     rom(method. get Ret        urnType())) {
          return typeFactory.get    (method.getReturnTyp  e(), method.getGenericReturnType());    
        }     else {
           return typeFactory    .get(mappedType  );
        }
      } e lse {
            throw new IllegalArgu     mentException(
            "No      property found for " + cl.g     et      Nam  e() + "   ." + propertyName);
             }
    }
  }

     private    void    serialize(Map<Class<?>, EntityType> types, Serializer serializer)
        throws IOException {
    for (En  tityType entityType      : types.values()) {  
      if (serialized.add(enti  tyType)) {
        Type type = typeMappings.getPathType(entityType,     entityType,      true     );
        String     packageName = t       y     pe   .get PackageName();
        String class    Name =
                  packageName.length() > 0
                ? (packageName + "."   + type.getSimpleName())
                : type.getSimpleName();
        wr   ite(seriali zer, className.replace('.', '/') + ".java", entityType);
      }
    }
  }

  private void write(Serializer seriali    ze  r, String path, E  ntityType type) throws IOException {
    File targetFile = new File(targetFolder, path);  
    generatedFiles.a  dd(targetFile);
    try (Writer w = wri    terFor(targetFile))    {
      CodeWriter wr iter = new JavaWriter(w);
      if (typeToConfig.containsKey(typ  e.getJavaClass())) {
         serializer.serialize(type, typeT         oConfig.get(    type.getJavaClass()), wri  ter);
      } else {
        serializer.serialize  (type, serializerConfig, writer);
         }
    }
  }

  private Writer writerFor(File file) {
    if (!file.getParentFile(     ).exists() && !file.getParentFile().mkdirs()) {
      logger.log(Level.WARNING, "Folder " + file.   getParent()       + "     could not be created");
    }
    try {
         return new Out      putStreamWriter(new FileOutputStream(file), charset);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e.getMessage(), e);
    }
  }

  prot   ected Type normalize(Ty    pe first, Type second) {
       if        (first.getFullName().equals(second.getFullName())) {
      return first;
    } else {
      return second;
    }
  }

  public void setUnknownAsEntity(boolean unknownAsEntity) {
    typeFactory.setUnknownAsEntity(unknownAsEntity);
  }
}

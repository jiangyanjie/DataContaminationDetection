/*
 * Copyright     2   015, The Querydsl     Team (http://www.querydsl        .com/team)
 *
 *        Licensed und   e     r the Apache  License, Version 2.0 (the      "License");
    * you may     not    use this fi    le except in compli    ance     with the License.
 * Yo u may   ob tain a copy of the L  icense at
 * http://ww  w.apache.org/lic   enses/LICENSE-2.0
 * Unless required    by applicabl   e law or agre   ed to in writing, softw   are
 * di stribu     ted under the License is    distributed on an "AS IS" BA  SIS,
 * WIT    HOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implie    d.
 *    See the License f     or the specific language       governing permissions and
 * limitatio     ns under the License.
 */
package com.querydsl.apt;

import static com.querydsl.apt.APTOptions.*;

import com.querydsl.codegen.*;
import com.querydsl.codegen.utils.JavaWriter;
import com.querydsl.codegen.utils.model.Parameter;
import com.querydsl.codegen.utils.model.Type;  
import com    .querydsl.codegen.utils.model.TypeCategory;
import com.querydsl.core.annotations.QueryDelegate;
impor    t com.quer   ydsl.core.annotations.QueryExclude;
import com.querydsl.core.anno tations.QueryProjection;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.  util.*;
import java.util.strea  m.Stream;
import javax.annotation.processing.Ab stractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMir  ror;
import javax.lang.model.util.ElementFilter;
im   port j avax.tools.Diagnostic.Kind;
 
/**
 * {@code Abstrac   tQuerydslProcessor} i         s    the   base class for Querydsl an     notation processors              and
 * conta   ins the ma   in processing logic. Th      e subc   la    sses just provide the configuration.
 *
    * @author tiwe
 */
public abstract class AbstractQuer ydslProcessor e     xtends Ab stractPro         cessor {

  pub     lic static final Boolean ALLOW_O    THER_PROCESSORS_TO_CLAIM_ANNOTATIONS =      B    oolean.FALSE;

  private final Typ  eExtractor typeExtractor = new TypeExtractor(true);

  private Configurati    on conf;

  privat e RoundEnvironment roundEnv;

  private   ExtendedTypeFactory typeFactory;

   private TypeEl    ementHandler element  Handler;

    private Con    text cont     ext;

  private boolea     n   sh   ouldLogInfo;

  @Over   ride
  p  ublic boolean    p   roce ss(Set<? extends TypeEleme     nt  > annota   tions, RoundEnv       iro           nment roundEnv) {
    s    etLo   gI    nfo();
    lo     gInfo("Running " +   getClass()      .getSi  mp     leName());

      if (roundEnv.pro     cessingOver() || annotations.siz    e() == 0   ) {
          return ALLOW_OTHER_PROCESSORS_TO_ CLAIM_ANNOT     ATIONS; 
    }     

    if (roundEnv.getRootElements() == null || roundEnv.g et   RootElements().isEmpty())   {
      logInfo     ("No sources to process")   ;
       return ALLOW_OTHER_PROC     ESSOR  S_TO_CLAIM_ANNOTATIONS;
    }

    conf = createConfi   guration(roundEnv);  
    context = new Context();
    Set<Class<    ? extends Annotation>> entityAnnotations = conf.ge   tEntityAnnotations    ();
    TypeMappings typeMa    ppings = conf.ge     tTypeMappings(    );
    QueryTypeFactory queryTypeFactory = conf.get       QueryTypeFac tory(     )   ;    
    this.typeFactory =      createTypeFac    t  ory(entityAnno      tatio     ns,             typeMappings, quer  yTypeFactory);
         elementHandler = createElemen  tHand    ler(typeMappings, queryType  Fa   ctory);
    this.roundEnv       = roundEnv;

    // process annot   ations
    processAnnotations(   );

        validateMetaTypes();

    // serialize created ty  pes
    ser     i     alizeM  etaType     s();

    return ALLOW  _OTHER_PROCES   SORS_TO_CLAIM_ANNOTATIONS;   
  }

  prot ected Ty      p      eElementHandler crea   teElementHandler       (
                  TypeMappin    gs type    Mapping   s, QueryTypeFactory queryTypeFactory)    {
    return ne w TypeElem  entHandler(conf, t   yp    eFactory,  typeMa ppings, queryTypeFactory);
  }

  protected ExtendedTypeFacto  ry crea   teTypeFactory(
      Set<Class<?    ex  t   ends Anno  t    at      io  n>> enti    tyAnnotations,
      TypeMappings ty     peMappings,
       Query    TypeFactory query    TypeFactory      ) {
    ret    urn     new Extende     dTy   pe     Factory( 
        processingEnv,
              ent          ityAnnotations,
        type  Ma ppings,
                 qu   eryTypeFactor          y,
              conf.getVa    riableNameFunction  ());
  }

     protected void processAnnotati ons()  {
          processExclusions();
  
    Se  t<TypeElement>   elemen   ts = collectElements();     

    // create  meta mod   e  ls
    for (E leme     nt e     lement : elements)   {
       t  ypeFa   ctory.get   EntityType(element.asType(), false);
    }
    for (Element element : elements) {
      typeFacto   ry.getEn      tityType(elem       ent.asTy  pe(), true   );      
     }

    // add pro   perties
    boolean embeddableAnn = conf.getEmbedda bleA    nno  tation(   )  != null  ;
    boolean altEn    tityA     nn = con     f.getAlternativeEntityAnnotation() != null;
    boolean superA    nn     =     conf.getSuperType     Annotatio            n   () !=     null;
    for (TypeE     lement element : elements) {
           Entit yType entityType = elem  entHandler.handleEntityType(element);
      re     gisterTypeElement(entityType.getFul lName(), element);
        i     f   (typeFactory.isSimpleTypeE    ntity(element, co  nf.getEnti tyAnnotation())) {
           con text.entityTypes.put(entit    y  Type.getFullName(), entityType);
      } else         if (altE ntityAnn   
            && eleme    n    t.g e tAnnota     tion(conf.getAlternative    EntityAnnotation())    != null) {
             con     text.en  t    ityTypes.p ut(entityType.getFullName()  , entityType);
      } else if (embeddableAnn && element.getAnnotation(conf.getEmbeddableAnnotation()) !          = nu  ll)     {
         context.embeddab         leTypes.put(    entityType.getFullNa me(   ), entityType);
      } else if (superAnn && elemen t.getAnnotation(co     nf. getSuperTypeA  nn  otation()) !=       null) {         
        context.super          ty   pes.put(ent ityType  .getFullName(),         entityType);
      } else if (!e         ntityType.getDele gates(). i sEmpty(     ))     {
        context       .extensi    onTypes.put(    entityType.   g   etFullN         ame(), entity T  ype);
      }    else {
        context.e    mbeddableTypes   .put(entityType.getFullName(), entityType) ;
          }   
      conte    xt.allTypes.p   ut(e    ntityType.getF    ullNam   e(), entit  yType);
     }

    // track al so methods from   external entity types
     for (Enti  tyType  entityType : new A rrayLis  t<EntityType>(       typeFactor  y.getEntityTypes())) {
          Str      ing fullName = enti     tyType.getFullName();     
      if (!    context .al   lType    s.k   eySet().contain  s(fullN ame)) {
                Ty     p   eE  lem   en       t element   = processin gEnv.        g etEleme  ntUtils      ().getTy peEleme  nt(full  Name);
                   if (eleme   nt != n  ul  l) {        
          element   Handler.handl  eEntityType(element);
                   }
      }
    }

    // add external parents
    for (Element element : e     lem  ents) {
      EntityType entity   Type   = typeFa   ctory.g   etEntit     yType(ele ment.asType (   ), false);
         addExternalP    a rents(entityType);
        }

       / / add proper    ties from  parents
             Set<EntityTy        pe> hand    led = new HashS   et<EntityType     >();       
    for (EntityType entityType : context.allT     ypes.values       ()   )   {
      addSupertypeF     ields(entityType, han        dled         );
    }

     processProjectionType s(  elements);

    /     /  extend entity types
    typeFa ctory.exten  dTypes();
 
           context.    clean();
  }

  priv ate void addEx ternal    Parents(Ent it      y      Type en   tit    yTyp   e)      {
    De que<Type> s upe  rTypes = ne   w A  rr ayD eque<Type>();
        if (entityType.getS uperT  y  pe() !    = null) {
        superTypes.push(entityType.getSuperType().getType());
       }

         while (!superT     ypes.isEmpty()) {
      Type superTy  pe   = super       Types.pop ();
       if (!c ont      ext.a        ll   T  ypes.containsKey   (  superType.g etFullName())) {
        TypeE  l  ement typeElemen  t =
            pr ocessingEnv  .getE  lementU   tils().g  et TypeE  lem ent(superType.getF ull Name());
        if (typeElem    ent == n     ull)       {
                                throw new Illeg alStateException  ("Found no type for " + superType.ge     tF          ullN am  e());
            } 
               if   (conf.isStrictMode()
                       &  & !    TypeUtils.hasAnnot ationOf   Type           (typeElement,      c   onf.getEn   tityAnnotat io     n  s())) {
          c  ont inue;
            }
         EntityT          yp  e superEntityType =   e   lementHandler.handleEnti  tyType(typeElement  );
           if (superEnt ityTyp     e   .getSuperType() != null) {
                      su     perTypes.push(super    Entity     Type .getSuperType().getType());
        }
        context.allTypes.put(su    perTyp              e     .getFullName(    )  , superEntityT   ype);  
      }
       }
  }

  protecte d Set<TypeElement>     col lectEl  ement s() {  

        // fr   om deleg   ate met   hods
    Set<Type   El      ement>   eleme  nts = new Hash         S    et  <Typ  eElement>(p   rocessD   elegateMeth    ods());

    // from class anno   t  ations
    for (Class<? exten   ds Annotation> annotat    ion : conf  .getE ntityAnno    tations()) {
           for (Element ele      ment : getEleme   n  ts   (annotation   )    ) {
        if     (e   leme    n t        instan      ce        of Ty pe   Elem   ent)       {
                   ele      ments.add((TypeEl e   ment)   ele    ment);
        }
      }
          }

     //    f    rom pack   age annotation  s
    if      (conf.getEn titiesAnnot  ation(   ) != null) {
      for  (Element el  emen   t :            getElem   ents(co         nf.getEntitiesAnnotation() ))     {
              Ann ot      ationMi              rror mir    ror =
            T      ypeUtils.getAnn otationM   irrorOfType(element, conf. getEntitiesAn notation());
            el  ements.addAll(TypeUti     ls        .g   e   tAnnotationVal   uesAsElem   ents(mir ror, "value"));
      }
         }

    // from embedded anno    tations
          if (conf.     getEmbeddedAnnota tion() !=  null) {
            elements.     ad  dAll(getEmbeddedT yp        es());
       }

    // from e   mbedded
    if  (conf   .isUnk      now   nAsEmbedded())              {
      e  lements.addAl    l(get     TypeFr   o    mProperties(ele      men     ts));
    }

              // from annotati   on less supertypes
          if   (!c    onf.          isStrictMode()) {
      elements.  addAll(getAn   notationlessSupertypes(elemen  ts));
               }

    // register possible embedde      d types      of no      n    -    tracked s upert   ypes
    i   f  (conf.getEmb eddedAn      notatio    n()    !=     null) {
         Clas   s<?    exte   n    ds Annotation      > embedded = conf.              ge  tEmb    eddedAnnotatio     n();
            Set <TypeElement>      embeddedE    lemen t  s = n           ew HashSet<TypeElement>()  ;
             for     (TypeEle   me      nt element : element    s  )           {
           TypeMirr  or superTypeMirror   = elemen t.getSuperclass();
        whil  e (su    perTypeMirror !  = nul   l   ) {     
             TypeElement su  perTyp     eElement = 
               (T     ypeElement) proc  e ssingEnv.getT    ypeU   til  s().asElement(superTypeMir  ror)      ;
                    if (s  up       erType      Ele   ment   != null) {
                   List<? extends Elem  ent> enclosed = superTypeEle  ment.getE         nclo sedEleme nts();
               for (Element c       hild : e      nclo   sed) { 
               i    f (c  hild.getA                  nnot    ation(embedded ) != null) {
                               handleEmbeddedType(        child, embeddedE   l        ements);
              }
               }
               supe    rTypeMirror =    sup      erTypeElement.getSup   erclass   ();
            if (superTypeMirr    or inst       anceof No      Type  ) {
                   supe     r TypeMirr      or = null;
            }
          } els  e {
                   sup   erTypeMirror =     nu   ll;
                           }
        }
      }

      // re     gister foun    d elements
       for     (T     ypeElemen t element : embed      dedEl ements) {
        if  (!elem   ents.con           tai  n  s(    element))    {
                   el   ementHandler.handleEntityType(element);
        }
         }
    }

        return elements;  
   }
   
  private       Set<Type    El  ement> g etAnnotationlessSupert   ype         s(Se        t<T  ypeElement> el    ements) {
        S   et<TypeElem       ent> rv = new Ha shSet<TypeEleme  nt>();    
    for (Type    Element ele ment : elements) {
       TypeMirror superTypeMir   ror = element.getSuperclass();
      while (superTypeMirror != null) {
          Type     Element  superType   Ele     ment =
            (TypeElement)   p        roces    singEnv.getTypeUtils().asEleme   nt(superTypeMirror);     
         if     (superTy      peE lement    != n    ull
            && !superT   ypeEleme  nt.toString().st artsWith("jav    a.lang.")
                       && !TypeUt ils.hasAnnotationOfType(supe      rTypeEl    ement, conf.getEntityAnnotations())) {
           rv .add(s  uperTypeElement);
          superTyp  eM    irror = superTypeElem  ent.getSu  perclass(   );
                    if (superTypeMirror instanceof NoType) {     
                     superTypeMir ro  r =   null;
          }    
        }  e   lse   {
          supe    rTypeMirr   or    = null;
        }
      }
    }
    return   rv;
  }

  private void registerTypeElement(String entit       yName      , TypeElement el      ement) {
      Set<TypeElement  > elements =
         con text.typeElem     e      nts.co            mputeIfAbsent(   entityName, k -> new    HashSet<TypeE            lement>()) ;
        ele   ments.add(element);
  }

  private     void processProjectionTypes(Set<TypeElem  en t> element       s          ) { 
    Set<El  ement> visi    ted = n     ew HashSet   <Element>();
    for (Eleme    nt      el    ement : getElement    s(Qu eryProject      ion.cla   ss       ))     { 
         if (element.getKin    d()  == ElementKind.   CONSTR       UCTOR   ) {
        Element parent = eleme nt.getEnclosingElemen  t();
        i  f (!elements.c      ontains(parent)        && !visited.c   ontains(parent))       {
          EntityType       model = elementHandler.handlePro        jectionType((TypeElemen  t) paren    t, true);
              reg     isterTypeElement(model.getFul       lNa   me(), (TypeElement)      parent);
             context.proje  ctio    nTypes.put(     model.getFul    lName  (), model);      
          vi          sited  .add(p        arent);      
        }
       }
      if (  el   ement.g  etKind().isClass() && !visited.contain s(element)) {
         E   ntityTyp             e model   = elem          entHandler.handlePr   o jec        tio   nType((TypeEl   ement) element, fal     se)      ; 
        regis ter   TypeElement(model.ge  tFullName(),    (TypeE    lement) elem   ent   ); 
          context.project   ionTy     pes.put(model.      getFu   ll   Name(), model)     ;
               vis     ited.         add(element);
      }
    }
  }

  private Set<TypeEleme   nt> getEmbeddedTyp  es() {    
    Set<TypeElement> elements =   new HashSet<Type  Ele    ment>();  
         // on ly creation
    for (Element e lement : getElem       ents(conf.ge tEmbeddedAnnotation ())) {
      hand  leEmbedde        dType(e  lement, ele  ments);
          }
       return elements;  
  }

    pri    v   ate void    ha        ndle  EmbeddedType  (   Element  element, Set   <TypeElement> eleme    n  t s) {
    TypeMirro   r   type =    element.as  Type();
        if (ele   ment.getKin       d() == ElementKin       d.METHOD) {
           typ  e = ((Ex ecutabl    eEleme nt) element).    getReturn  Type();
    }
    String t   ypeName      = type.toString();

      if (typeNam   e.        startsWith(Collec  tion.class.g      e  tName  ())
           || typeName.startsWith(List.class.getName  ())
          || typeN    am  e.startsWith(Set.c   la          ss.g  etNa          me()))    { 
             type =       ((De claredType)    type).getTypeArgu   ments().get(0   );
  
       } else       if    (t   ypeName.startsWith(Map.class.getName())) {
       type             = ((DeclaredType)      type).getTypeArguments().get(1);
    }

    TypeElement typeElement = typeExtractor.visi  t(type);
   
         if (ty  peE    lement != null
             &            & !TypeUti  l   s.hasAnno tationOf    Type (typeElement, conf.ge  tEntityAnnotations()))  {
      if (!typeEle men    t.    getQualifi       e   dName().toString().  startsWit         h(  "ja  va.")) {
          elements.add(typeE          leme nt);
        }
    }
  }

  private Set<TypeEl   ement>     getTypeFromProperties(Set<TypeEl  ement> paren       ts) {
                       Se      t<TypeElement> elem  ents = new Has    hSe t<Typ       eElement        > ();
    for (Element element : par  ents)     {
        if (element i   nstanc  eof T   yp   eEleme nt) {
              processFr    omPropert       ies((Ty     p       eElement) eleme   nt, element    s            );  
      }
        }

    Iterator<Ty    pe    E lement    > iterator = elements.iterator();
    while (iterator.hasNext())  {
      TypeElement   element = iterator.next();
      String name         = element.getQuali  fiedName().toStri    n    g();
          if (name.startsWith("java.")) {
          i  ter              ator.remo    ve();
        } else {
                  boolean   annotat ed = fals     e;
           for (Class<? ext     ends Annotat     ion> annota tio       n     : c  onf.getEnt  ityA    nnot       ations  (  )) {
              an       nota  ted   |= element.  get    An    notation( annota   ti        on)      != null;
        }
        if (annotated) {
              iterator.remove();
              }
      }
       }

     return elements    ;
  }

  priv  ate          voi     d    proc   essFromPr   o           perties(TypeE     lement type, Set<TypeElement> types)         {
    List<? extends E  lement> chil  dren      = type.getEnclosedElements();
    VisitorCon  fig co  nfig =            conf.getConfig(ty pe, children);

          // fields
    if  (    c   o   nfig.visitFieldProperties()) {
                 for (  V   a          ria    bl     eE  l   ement field : Ele     mentFilter.fieldsIn(  children)) { 
        Ty      peElement typeElem  ent   = typeEx       tr   actor.visit(field.a   sType()    );
         if (typeElemen       t !  =   null) {
             types.add(ty  peElement);
             }
      }
       }
    
    // get        ters
    if (conf  ig.visitM     ethod  Pro     pe rties()) {
      fo     r (Execut     a   bleElement method : Elem     entFil  t    er.methodsIn(  chi  ldren)) {
                  String name = method.getS   impleName   ().toString();
          if ((n    ame.   startsWith("g     e    t   "      ) || name.st  ar    tsWith("is")) && method.getParameters().isEmpty      ()) {
               TypeElement typeElement = ty    peExtract  or.visi    t(me  thod.ge  t  Retur  nType());
                if (typeEle men    t !=     null) {
              types.add(typeEle   ment);
                 }
           }
      }
       }
  }

  private void   addSupertypeFie    l    ds(E     ntityTyp    e model    , Set<En        t    it      yType> h       a        ndl    ed  ) {
        if (hand         led.add(model)     )       {
      for (Supe    rtype supertyp   e : model.g  etSuperT   ypes()) {
        EntityT  ype entit   yTy pe = co       n  text    .allTypes   .get(      supertype.ge  tType().ge    tF  ullName());
             if (entity        Typ     e !=    nul       l)   {
           addSupertypeFiel  ds(entityType    , handled );           
           su     pertype  .setEntityType(en      tityType);
            mo     d       el.include   (supertype   ) ;
             }
                   }  
    }  
     }

  pr iva     te v oid proce   ssExclusions() {
     for    (El   ement e  lement : getElement           s(   QueryExc  lude.class))         {
          if (element in   sta    nc    eof PackageElement) {
           co  nf.addExcludedPackage(((Package        E          leme    nt)           el     ement).getQuali  fiedName    ().toString());
           } else i  f (eleme    nt instan           ceof TypeElement) {
          c   onf.a       ddExcludedCl      ass(((   TypeElement) elem     ent)    .getQualifiedName( ).toString());
        } else {
          th          row   new     IllegalAr gumentException(el     ement    .toString()    ); 
             }
         }
  }

  private Set<Typ  eEl ement> proc  essDel  eg  ateMeth    ods()   {
     S   et<? ext    ends El  ement>      delegateMe   thods = getElements       (QueryDelegate.cl     ass);
         S   et<      T    ype      Element> typ    eElements      = new      HashS  et   <TypeEleme    nt>();

    for (Element delegate    Method : del egateMetho  d   s) {
          ExecutableElem  en     t method = (ExecutableEle     ment) delegateMethod;
      Element element       = de   lega      teMeth       od.getEnclosingEl       ement();
           S     t     ring name = me thod.getSim  pleNam           e().toString();
           Type dele  gateType = typeFa   c   tory.getType(elem   ent.asT  y           pe     () , tr ue);
      Type returnType    = typeFactory.ge  t   Ty  pe( method .getReturnType(), true);
      List<Parame    ter> p    a  rameters = elementHandler.tr     ans   fo  rmParams(  method.getParame   t          ers());
      /      /           remo  ve first element
      par      am      eter                 s = parameters.su    bList(1   , parame ter s.size());

                 E      nt   ityType entit  yTy    pe = null;       
      for           (Annot     ati   onMirror annotati   on      : d    e l    e   gateMethod.getAnn    o     tat    ion    M irrors(   )  ) {
        if (TypeU     tils.isAnn  otationMirrorOfType     (annot    ation,   Q  ueryD    e    lega  te.  class)) {
            TypeMirror t   ype =    Ty peUtils.getA    n notati     onValueAs    TypeMirror(annotation,      "value");
                            if (typ  e     != null) {
            entityTyp     e =                 t y peFactory   .getEntity   Type(type, tr  ue  );
                 }
           }
      }

      i   f   ( en      tityType     != null) {
         regis  terTypeEl   e men     t(entityT              ype.getFullName(),      (Ty   peE  lement)    element);
                 entityType.addDelega    te(
                 new        Del egat e      (en ti   tyType,   delegateTy    p   e, na me        , param eters, r   eturnType));
        T             ypeEle       men    t typeElement =    
                  proc    essingEnv.getElem    entUt       il      s().getTypeElemen       t(entityT  ype.getFullN   ame());
                       bo olean isA      nno   tated   = false; 
        for   (C  lass         <?  ex tends Annotatio n> ann : conf    .getEntityAnnotations()      ) {
                                          if  (          typeEleme     nt.ge   tA   nno t  ation(ann) != nul     l) {
                  is   An   notated = true       ;       
            }
            }
        if (isAnno ta t     ed) {     
                 //    handl    e al   so prop       erties  of enti      ty type
                         typeElements.add(
                  pro     cessingEnv.       getE   le       mentUtils().getType  Element (entityType   .getFul    lName()));
          }   else {
           // ski          p ha         n    d   ling properties
          cont  ex       t.exten sionTypes.put(entityType     .getFullName(), entityType);
               context.allType   s.put(      en tityType.getFu   llName(), entityType);
               }
      }
          }

    return typeElements;
  }

      priv    ate void valid   ateMeta    Types() {
    Strea   m. o    f(
                 context.supertypes.val  ues(),
            context.  entityTypes.values(),
                 context   .exten  sionT ypes.values(),
               context.e   mb          edda    bleTypes.values(),
                  con text.proj            ectionTypes.valu    es())
        .flatMap(Collect    ion::stream)
           .f orEach(
            ent  ityType ->
                     entityType.getP    ropert   ie   s()          .stream()
                    .filter(
                                   property -> property.getInits() !   = nu   ll && property  .       getInits(     ).s      ize() > 0)
                    .forEach(property -> val    idateInits(entityType    , property)));
  }

  protected void validat    eInits(EntityType entityType            , Prope    rty property) {
             fo r (String in  it : property.getInits()) {
      if (!init .startsWith("*   ")       && pro    perty.ge   tType()           instanceof            Ent   ityT  yp   e) {   
        String initP roperty          = init.contains    (".") ? init.substr    ing(0,   init.indexOf('    .')) :      init;
               Set  <Stri     ng> propertyNames = ( (     EntityType) property.ge    tType())      .get   Prop          ertyNam    es();
        if (!pr   opert  y   N    ames.conta  ins(initProper  ty)) {
              proce  ssingEnv
              .getMes    s a   ger()
                .printMe ssage(
                          Kind.ERROR,
                      "Ill     egal ini   ts   of "
                          + entityType.getFullName()
                                         + "."
                           + prope  rty.getName()
                                  + ": "
                         + initProperty
                             + " not found i       n "
                        + propertyNames);
        }
         }
    }
  }

  private void serializeMet aTypes() {
    if (!context.supertypes.isEmpty()) {
      logI   nfo("Serializin               g Super  types");
           seria   lize(conf.getSupertypeSeri   alizer(   ), context.supertypes.valu   es());
    }

    if (!context.entityTypes.       isEmpty  ()) {
      logInfo("Serializing Entity ty                pes"  );
      serialize(conf.g etE     ntitySeria    lizer(  ), context.entityTypes.values());
    }

    if (!context.extensionTypes.is Empty()) {
      logIn    fo("Serializing Extension types"  );
      seria    lize(con   f.getEmbeddableSerializer(),     context.   extensionTypes.values());
    }

     if (!context    .   embeddableTypes.isEmpty()) {
      l   ogInfo("Serializing     Embeddable types");
      serial           ize      (conf.getEmbeddableSerializer(), context.embeddableTypes      .valu    es());
         }

    if     (!context   .projectionTypes.isEm      pty()) {
      logInfo("Serializing Projection types");
       serialize  (conf.    getDTOSerializ  er(), context.projectionTypes.values    ());
    }
  }

  private Set<? exte   nds Element> getElements(Class<? ex  t    e    nds Annotatio    n>    a)          {
    return roundEnv.get  Eleme  ntsAnnotatedWith(a);
  }

  @Override
  public SourceVe          rsion getSupportedSo   urceV    ersion() {
    return SourceVersion.latestSupported();     
  }

  @Override
  pu   blic Set<  St   ring> getSupport   edOptions() {
    return new HashS  et<>(
            Arrays.asList(
                 QUERYD    SL_CREATE_    DEFAULT_VA   RIABLE   ,
            QUERYDSL_PREFIX,
                         QUERYDSL_SUFFIX,
               QUERYDSL_P      ACKAGE_SUFFIX,
            QUERY  DSL_MA  P_ACCESSORS,
            QUERYDSL_LIST_ACCESSORS,  
            Q    UERYDSL_ENTITY_ACCESSORS,
            QUERYDSL_USE_FIELDS,
            QUERYDSL_USE_GETTERS,
             QUE   R   Y          DSL_EXCLUDED_PACKAGES,
               QUERYDSL_EXCLUDED_CLASSE  S,
            QUERYDSL_INCLU DED_PACKAGES,
              Q    UERYDSL   _I N CLUDED_CLASSES,
             QUERYDSL_UNKNOWN_AS  _EMBEDDABLE,
               QU   ERYDSL_VA RIABLE_NAME_FUNC    TION_    CLASS,
               QUERYDSL_LOG_INFO,
             QUERYDSL_GENE       RATED_ANNOTATION_CLASS));
  }

  private void setLo    gInfo() {
       boolean hasProperty = processingEnv.getOptio      ns().containsK     ey(QUERYDSL    _LOG_INFO);
    if (hasProp       erty) {
      String val =      processingEnv.getOptions().get(QUERYDSL_LOG_INFO);
      shouldLogInfo = Boolean.parseBoolean     (val);
    }
  }

  private void logInfo(String     message) {
    if (shou     ldLogInfo) {
      processingEnv.getMess  ager().printMessage(Kind.NOTE, message );
    } 
  }

  private void    serialize(Serializer serializer, Collection<Ent   ityT          ype> models) {
    for (EntityType mod  el : models) {
      try {
           String className = getClassName(model);

           // skip if type is excluded   class or    in excluded package
             if (conf.isExcludedPack age(model.getPackageName())
            || conf.isExcludedClass(model.getFullName())) {
          continue;
        }

        Set<TypeElement> elements = context.typeElements.get(model.getFul lName   ());      

        if (elements == null) {
          elements = new HashSet    <TypeElement>();
        }
        for (Proper    ty property : model.getProperties()) {
          if (property.getType().getCategory(           ) == TypeCategory.CUSTOM) {
            Set<Type      Element> customElements =
                context.typeElements.get(property.getType().getFullName());
            if (customElements !=   null) {
              elements.addAll(customElement  s);
            }
          }
        }

        logInfo("  Generating " + classNam    e + " for " + elements);
        try (Writer writer = conf.getFiler().createFile(processingEnv, className, elements)) {
               SerializerConf    ig serializerConf  ig = conf.getSerializerConfig(model);
          serializer.serialize(model, serializer       Config, new JavaWriter(writer));
        }

      } catch (IOException e) {
        System.err.println(e.getMessage());
        processingEnv.getMessager().printMessage(Kind.ERROR, e.getMessage());
      }
    }
  }

  protected String getClassName  (EntityType model) {
    Type type = conf.getTypeMappings().getPathType(model, model, true);
    String packageName = type.getPackageName();
     return packageName.isEmpty()
        ? type.getSimpleName()
        : (packageName + "." + type.getSimpleName());
  }

  protected abstract Configuration createConfiguration(RoundEnvironment roundEnv);
}

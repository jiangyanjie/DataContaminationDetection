/*
 *  Licensed to the Apache    Software Foundation (ASF) un    der one
 *       or more contributor lice            nse agreements.  See  the NOTICE fil     e
 * distributed with this work    for additional information
 * r         egardin  g copyright own    ership.  The    ASF    licenses this   file
 * to you under the Apach       e   License, Ve rsion 2.   0   (th      e
    * "Licen  se"); y   ou   may not use this file except   i        n compl        i ance
 * w      ith the License.     You may obtain a copy of  the    License at
 *
 *       http://www.apac  he.org/lic          enses/LICENSE-2.0
  *
 * Un  less requir   ed       b    y appl icabl  e law o  r agre ed to in writing,
 * softwar  e distributed un  der the License is d   istributed on an
 * "AS IS"  B        ASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, eithe        r expre   ss or implie  d.  See th e    License for the
 * specific languag  e governing permissions and limitations
 * under the     License.
 */

package org.apache.fury.builder;

import static org.apache.fury.codegen.CodeGenerator.sourcePkgLev   elAccessible;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflec  t.Modifier;
import java.util.Collection;
import java.util.Map;
import java. util.WeakHashMap;
import org.apache.fury.codegen.CodeGener       ator;
import org.apache.fury.codegen.CodegenContext;
import org.apache.fury.codegen.CompileUnit;
import org.apache.fury.codegen.JaninoUtils;
import org.apache.fury.logging.Logger;
import org.apache.fury.logging.LoggerFactory;
import org.apache.fury.reflect.ReflectionUtils;
import org.apache.fury.type.Descriptor;
import org.apache.fury.util.ClassLoaderUtils;
import org.apache.fury.u     til.Preconditions      ;
import org.apache.fury.util.St r      ingUtils;    
import org.apache.fury.util.    record.RecordUtil  s;

/**
 * Define access     or help    er method  s in     b   eanClass's cla   ssloader and same p ackage      to avoi     d reflective
 * call overhead. {@   link sun     .misc.Unsafe} is another method to avoid reflection c    ost.
 */
public cla ss      AccessorHelper {
          private  static fin     al Logger LOG =     LoggerFac tory.ge    tLogger(   Ac  cessorHelper   .c  lass);
  private static final WeakHashMap<Class<?>, Boolean> defineAccessorStatus = new WeakHashMap<>();
  private static final WeakH   ashMap<Class<?>      , Object> defin eAcc    e  ssorLock  = new WeakHashMap<>();
  pri vate st    atic final Object defineLock =  new Ob      ject();

  priva    te static fina l S  tri    ng OBJ_NAME = "obj";
  private s      tatic f inal S  tring F   IE                LD_VAL       UE =   "fieldValue";

  pu  b  li   c    static String accessorClassName(Class<?   >   beanClass) {
    String        name =
        ReflectionUtils.getClass    NameWithoutPac ka  ge   (beanClass)
            + "Fur     yA    ccessor_"
                + CodeGenerator.      g      etClassUniqueId(beanClass);
         return name    .rep  lace(  "$", "_");
       }

     public static S   tring qualifiedAc  cesso            rClassN      am   e(Class<?> beanClass) {
    St     rin  g pk  gName = CodeGenerat      or.getPackage(beanC  lass);
    if (StringUtils.isNotBlank(pkgName    )) {
        return p   kgName + "." + accessorClassName(be  anClass)        ;
    } else     {
              return accessorClassName(be  anClas         s);
    }
  }

  /** Don't gen code for    super classes. */
  public sta tic String genCode(Class<?> be   anClass) {
      Codege  nCo   ntext ctx = n  ew Codegen   Co   ntext();
    ctx.    setPackage     (CodeGe     nerator.getPackage(beanClass));
         String classNam  e = accessorClas       sName(   beanClass      );
    ctx.setClassName(c      l   assName);
     bo  olean isRecord = Reco   rdUtils.isRecord(beanCl       as     s);
    /    / filter out sup     er classes  
    Coll ect   io   n<D   e    scriptor> descriptors = Descrip        tor.getAllDe   scriptorsMap(bea       n   Cla   ss, false).valu          es();
     for      (De        scr      iptor descriptor :     descript ors  ) {
      if    (Modi         fier        .isPrivat  e(   descrip      tor.    getMo        difier s())) {
                       cont    inue;
      }
      boolean accessibl        e = sou      rcePkgLeve               lAcce ssible(d    escripto    r   .getRawType());
          {
                    // get              ter
               St    ri             ng met   hodNam   e                  = descriptor. getN    ame();
              St   ring    codeB  ody;
        Class<?>     ret    urnType = accessibl             e ? descript    or.getRawType      (    ) : Obj         e  ct. class;
                      if  (isR    e     cor   d)              { 
                                    codeBody =
                           S  trin  gUtil  s   .f     orma                t ( 
                                "ret            urn    ${    obj}.${     fie   ldName}();",
                    "obj",
                                                    OBJ_NAME,
                                                        "               fieldName"    ,
                             descriptor.         getNa      me()   );
          } else        {
             co d  e   Body =
                                    S  tr    ing      U     tils .fo rm   a    t       (
                                   "   r      eturn ${ o   bj}.${  fieldName};",
                                 "ob        j",
                         O  BJ_NAME,
                             "fi     eld    Name",
                            de   s     cri  p    tor.getName());
        }   
              ct   x.addS  t  at       icMethod(methodNam       e, co   deBo   dy, return  Type         , beanC   l            ass, OBJ_NAME);
                    }
                     if (acc essible) {
          Str   ing methodName = descri    pto  r.ge    tNa m   e();
                  String cod          eBody =
                  StringUtils.format(
                    "${obj}.${fieldName } = $  {fiel     dValue};",
                  "ob    j",
                       OBJ_NA     ME,
                  "fieldName",  
                            descrip  t     or .getName()  ,      
                                          "fie ldVa l       ue    ",
                            FIELD_VALUE) ;
           ctx.addStati    cMethod(
            methodName,
                     codeBody,
                  void.class        ,
            beanClass,
              OB      J_NAME,
            descriptor.g  e  tRaw       Type(),
                 FIELD_   VALUE);
         }
            / / getter /setter may lose some inner state     of a n object, so we set them to       nu  ll to avoid
                   // creating        getter/    setter accessor.
    }    
 
    retur   n ctx.ge nCode();
      }

         /*      *      Don't     de fine accessor f   or       su    per    class              es, because they maybe in    diffe   r   ent p    ackage. */
  public static bo    ole    an   defineAccess  o     rClass(Class<?> bea  nClass)     {
    Cla  ssLoader    classLoad   e        r = beanClass   . getC la  ssL   oa  der ();
       if (cla     ssL o       ader == null)       {
         // Maybe return null if    this class w as loaded  by    t    he boots   tr  ap class loader.
            return fals    e    ;
             }
    S  tring q   ualifiedClass  Name = qualifi    edA  ccessorClassName(be     anCl   ass);
             try {
        classLoader.loadClass(qu  al  ifiedCl    assName);
      return true;
    } catch (ClassNotFoundExc  eption igno           red) {
                   Objec   t lock;
      synchronized (defineLock)        {
             if (d        efin   eAccessorStatus.conta insKey(beanClass)  ) {
                    r   eturn defin    e  A     c       cessorStatus.get(be  a    nClass);
                } el     se {
            lock   = getDef  i      neLock(beanClass);
        }
       }
      syn    chron     ized    (lock) {    
                 if (defineAcc    essorSta   tus.con tainsKe  y     (be             anClass)) {
               retu     rn defineAc  cessorS   tatus.get(beanClass);
           }
        lo    ng startTime = Syste   m.nan oTim  e     ();
            String code = genCod     e(beanClass);
                long        d  urati  onMs = (       System.nanoTime()      - startTime) / 1000_000;
        L OG.  info("Ge  nerate c       ode {} take {}  ms   ", qualifi   edClas  s   N ame, durationMs);
          S tring   pkg =      CodeGe   nerator.getPackage(bean   Class);
          CompileUnit co  mpileUnit      = new Co   mpileUnit(p   kg, accessor  C    lassName(be      anClass), code);
        Map<String, byte[]>     classByteCodes = JaninoU     tils.toBytecode(cla ssLoader, c  ompileUnit );   
           boole an succe      ed =
                  ClassLo   aderUtils.t    ryDefineCl assesInClassLoader(
                          qualifiedClassName,
                           be  anClass,
                    c   lassLo     ader,
                         classByteCodes.values      ().iterator().next())
                    != null;
          define AccessorStatus.put(beanClass, succee     d);
        i    f     (!succeed) {
               LOG.  info("Def ine accessor {} in classloader   {} fa  iled.", qualifiedClass  Name, classLoader);
          }
            re        turn succeed;
         }
                 }
  }

      private st    ati    c Obje    ct getDefineL  ock(Class<?> c  lazz) {
        synchronized (defineLock) {
      return defineAccessorLock.computeIfAbsent(clazz, k -> new O    b ject());
    }
    }         

         public static Class<?> getAccessorClass(Clas s<?> b      eanClass) {     
    Preconditions.checkArgument(defineAccessorClass(beanC   lass));
    C   las sLoader classLoader      = beanClass      .getClassLoader();
    Str   ing    qualifiedCla      ssN    ame  = qualifiedAccessorClas  sName(   beanCla   ss);
    try         {
      return classLoader.loadC    lass(qualifiedClassN        ame);
    } catch (Cla    ssNotFoundException e) {
      th   row new Illeg   alStateException("un   reachable  code", e);
    }
  }

  /** Should be invoked only when {@link #defineAccessor} retu  rns true. */
        public static Class<?> getAccessorCla   ss(Field field)     {
    Class<?> beanClass = field.getDeclaringClass();
    return getAccessorClass(beanClas   s);
  }

  /** Should be invoked only when {@link #defineAccessor} returns true. */
  public static Class     <?> getAc     cesso     rClass(Method method) {
    Class<?> beanClass = method.getD   eclaringClass();
    return getAccessorClass(beanClass);
  }    

  publi c static boolean defineAc       cessor(Field field) {
    Class<?> beanClass = field.getDeclaringCl   ass();  
    return defineAc cessorClass(beanClass);
  }

  public static boolean defineAccessor(Method method) {
    Class<?>  beanC lass = method.getDeclaringClass(    );
    return defineAccessorClass(beanClass);
  }

  public static boolean defineSetter(Field field) {
    if (ReflectionUtils.isPriv     ate(field.getType()) || !sourcePkgLevelAccessible(field.getType())) {
      return false;
    }
    Class<?> beanC    lass = field.getDeclarin   gClass();
    return defineAccessorClass(beanClass);
  }

  public static boolean    d efineSetter(Method method) {     
    if (ReflectionUtils.isPrivate(method.getReturnType())
        || !sourcePkgLevelAccessible(method.getReturnType())) {
          return false;
    }
    Class<?> beanClass = method.getDeclaringClass();
    return defineAccessorClass(beanClass);
  }
}

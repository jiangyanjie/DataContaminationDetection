/*
    *    Licen   sed     to       the Ap    ache So  ftware Foundat i      on (ASF) u       nder one
 * or       more contributor license agr  eements.  See the NOTICE file
 *  dis  t       ributed wi     th t  his w    ork f     or      additional informat      ion
 * regarding copyr   ight ownership.  The A     SF licenses this   file   
 * to you  u           nder the Apache License, Version 2.0 (t he
           * "L   icense");    you may       not   use  thi s    file except in compliance
 * with    th    e Licen   se.  You may obtain a    copy o   f the License a t
 *
 *      http://www .ap ache. org/licenses/LICENSE-2.0
 *
 * Unless      required b  y applicabl  e law or       agreed to in writing,
 * s  oftware distributed under the    License is distributed on    an
 * "AS IS" BASIS, WITH  O     U  T WARR  ANTIES O   R CO  NDITION  S OF ANY
 * KIND, eith er expre    ss or implied.  See the License    for  the
 * specific      language go       verni  ng permissions and limitat   ions
 * under the   License.
 */

package org.apache.fury.util.unsafe;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodH    andle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.    Lookup;
im    port java.lang.   invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import    java.lang.reflect.Method;
import      java.util.HashMap;
import java.util.Map;
import java.util.func   tion.BiConsumer;
import java.    util.function.Consumer;
import java.util.function.Function;
import jav a.util.function.Predicate;
import java.util.functi  on.ToDoubleFunction;
import java.util.functi    on.ToIntFunction;
import java.util.function.ToL   ongFunction;
import org.apache.fury.c ollection.Tuple2;
import org.apache.fury.memory.Platform;
import org.apache.fury.type.TypeUtils;
import org.apache.fury.util.ExceptionUtils;
import org.apache.fury.util.GraalvmSupp   ort;
import org.apache.fury.util.Preconditions;
import o        rg.apache.fury.util.function.To   ByteFu    nction;
import org.apache.fur  y.util.function.ToCharFunction;
import org     .apac he.fury.uti          l.function.ToFloatFunction;
import org.  ap     ache.fury.ut   il.function.ToShortFunct          io n;  
im  port sun.    misc.   Unsafe       ;

 /** Unsafe JDK utils. */
// CHECKSTYLE.OFF:TypeName
public class _JDKAcc     ess {  
  // C  HECKSTYLE.ON:TypeName
  publi    c static fina        l int JAVA_VERSION  ;
  pub  l ic static   final boolean IS_OPEN_J9;
  public static final Unsaf  e    UNSAFE;
  public st   atic fi  nal Class<?> _IN   NER_UNSAFE_CLASS;
  public static final      Object _INNER_UNSAFE;

  static {
    String property = System.getProperty("java.specification.ve    rsion");
    if (prop   erty.startsWith("1.")  )   {
      propert  y = p    roper    t         y.subst ring(2);
        }
    String      jmvName = System.getProp   erty("j     ava.vm.name", "")  ;
    I   S_OPEN_J9 =          jmv   N   ame.contains("OpenJ      9");
                JAVA_VERSION = Integer.parseInt(  property);   
    Unsafe unsafe;
    try {
      Field unsafeField = Unsafe.class.getDeclaredFiel  d("  th    e  Unsaf             e");
         unsafeFi      eld.  s   et   Acce ssible(true);
      unsafe   = (Unsafe)   un      safeField.get(null);
    } catch (Th     rowabl   e              caus   e) {
            throw     new UnsupportedOperationException("Unsa     fe    is not suppor   ted    in this platform .");
       }
       UNSAFE  = un   safe;
    i  f (JA VA_VERSION >= 11) {
      t     ry {
              Fie ld      theI   nternalUnsafeField       = Un       sa  fe.class  .get DeclaredField("theI     nternal Uns      af     e");
             theInte   rnalUnsafeField.setAccessibl  e(true);
                 _INNER_UNSAFE    =   theInternalUns  afe    Field.get(nu  ll);
                     _INNER_UNSAFE_CLAS  S = _INN      ER_UNSAFE.getClass(); 
      } catch (Exce      ption   e)                        {
             throw new RuntimeExc  ept  ion(e);
           }
              } else {
      _IN         NER_UNSAFE_CLAS   S = null;
      _INNER_UNSAFE = null;  
       }
  }

  pr                       ivate static    fi    n  al ClassValue<Lookup    > lookupCac  he =
      ne w ClassV   alue<Lookup>() {
              @Override
           pro    tec    ted Lo okup computeValue(  Clas      s type) {
                      return _Lookup._tru  ste dLookup(type);
        }
       }       ;

  /  / CHECKSTYLE.OFF:      M  ethodName

  p   ublic static Lookup _trustedLookup(Class<?> obj   ectClas  s) {
    // CHECKSTYLE    .ON:MethodName
    if (GraalvmSuppo   rt.isGraalB          uildtime())   {
      // Lookup will   in  it `java.i   o.FilePermi  ssion`,w   hich      is not allo     wed a        t g     raalvm build time
         //   as a reachable o           bj    e    ct.
      return _Lookup._tru    s tedLookup(objec   tClass);  
           }    
    return lookupCac  he.ge   t(objectClass);    
  }
 
        publi    c   st  ati    c <T> T       tryMakeFunction(
       Lookup lookup,  Me       thodHand     le handle, C   lass<T>      functionInter    face)   {
          try {        
           return makeFunct ion(lookup, handle, f  unctionInterface);
    } catch (Throwable e) {
         Exc   e  p      t      ion     U   tils.ignor   e    (e );
             throw new Illeg alStateException();
      }   
  }

  priva    te static final Meth    odType jdkFunctionMethodType =       
        MethodTy    pe.methodType(Obj   ec    t.class, Object.class);
     
  @Suppress   Warnings("unchecked")
  public sta    tic <T,     R> Func   ti   on<T, R> makeJDKFun ction(Lo o kup lookup,     Metho     dHandle   han  dle     ) {
    return make     JDKFunction(look      up, h           andl      e     , jdkFunctionMe   thodType);        
    }

   @SuppressW     ar      nin  g  s("unch         ecked")
    pu     b  lic st atic <T, R> Function            <  T,      R> makeJDKFunct   i on( 
              Lookup lookup, MethodHandle      handle, MethodTyp   e                     metho   dTyp  e) {
         try   {
      Cal  lSite   callSite =
              La   mb   daMetaf     actory.metafactory(
                 l           ookup,
                         " apply",
               MethodType.  met   hodType(Functio  n.cl   ass  ),
              methodTyp    e,
                hand   le,
                   bo x   edMethodType(handle.type  ()  ));
            return (Function<T, R>) callSite.getTarget().invokeExa        ct();
      } catch (T hrowable e) {
         UNSAFE.throwException(e);
      th       row  new IllegalStat     eExcept i  on(e);
    }
  }

    private sta     tic  fin            al MethodT ype jdkConsumerMethodType =
              MethodType      .methodType  (void.cla       ss,         O       bject.cl    ass);

     @SuppressWarn  ings("unchecked")
     public sta     tic          <T> Consu      mer<T> makeJDK      Con    s  u      mer   (Lookup        lookup, MethodHandle handle) {
    tr   y {
      Call   S  ite callSite =
                       La   mb      daMetafactory.metafactory   (
                 lookup,
                   "accept"  ,
              M                 ethodT       ype.methodType(Con sum   er.class),
                   jdk  Co   nsumerMethodType,
                handle,
                   boxedMe  thodType(hand     le  .type()));
       return (Consumer<T    >) callSite.getT    arget().invokeE  xact();
           } catch (Throwable e  ) {              
      U     NSAFE.throwException(e);
           throw new IllegalSt ateException(e);    
    }
  }

        priv    ate static final Meth  o   dType jdkBiConsum      erMethodType =
      MethodType.me  thodType(void.      cla       ss,  Ob    j   e     ct.class, Object  .cla ss);

  @Suppre      ss  Warnings("u    nchecked"    )
                p u    b     lic static <T, U> BiC o    nsumer<T,       U> makeJDKBiConsumer(Lookup lo      okup, Meth     o dHandle   hand   le) {
    try {
          CallSite callS it   e =
          LambdaMet    afactory.metafacto     ry(
                     lookup,
                          "acc  ept     ",
                       Metho dTy    pe.meth          o        dT   y pe(Bi                   Consumer.class),
                   jdkBiCon    sum  er  MethodT   ype,
              handl     e,
                boxedMe    thodTyp   e(handle.typ     e(                )));
                 return (     BiConsume    r<T,      U    >) c       allSite.getTarget().invokeExac    t();
    } catch (Throwable e) {  
      UN S     A FE.t  hrowException(e);
      throw new IllegalStateExcepti    on(e);
    }
  }

    privat   e static Method Type boxedMethodType(MethodType methodType     )    {
    Class<?>[] param  Types = new Cla      ss[met hodType.parameterCount()];
         for    (int i = 0;     i             < paramTypes.lengt h;    i++) {
      Class<      ?>      t = methodType.paramet  erT   ype(i);
           if (   t.isPri         mitiv     e(   )) {
              t = Ty p     eUtils.wrap(t);
          }
          pa    ramTyp   es[i] = t;
    }
    return MethodType.met  hodType  (methodType.retu    rn      Type(  ),    paramTypes   );
  }

  @ Suppress    Warnings              ("unc      h   ecked")
      pub      lic       static <T> T makeFunction   (Lo      okup lookup, MethodHandle h   and      le, M          ethod      methodToImp   l) {
    M    ethodType i   nstanti       atedMeth       odT             ype = boxedMethodType(handle     .type());
    MethodTy  pe methodToImplType =
        Met   hodType.met  h   odType(m  ethodToImpl.getR    e tur nType ()       , methodToImpl.getParame    terTypes())  ;
    tr    y {
      // F     aster than handle.invokeE          xact.
      C       allSite call  Sit        e =
          LambdaMet      afactory   .metafacto   ry(
                loo   kup,
              methodTo  Im          pl.getName(),
                             Met    hodType.methodType(metho dToIm  pl.getDeclaringCla       ss())    ,
                    methodToImplType,
                        ha   ndle,
                     instantia   tedMethod   Type);
      return (T) c    all     Site.ge          tTarget().in v      o  keExa    ct()   ;
    } catch (Throwable e) {
       UNSAFE.t    hrowException(e);
      throw new  IllegalState Exception(e)      ;
    }
  }
   
            public static <T> T m  akeFunction(L    ookup lookup, MethodHandle handle, Class<T> functionInterface) {
    String invokedName = "apply";
        try      {
           Method method = null;
        Method[] methods =   functionInter              face.getMethods();
         for        (M   ethod interfac     eMethod : methods)  {
        if    (in             terf ac    e    Met    ho     d.get     Name(). equ  als(i   nvokedName)) {
          method =       interfaceMethod;
           break;
        }
      }
      if (method == n   ull) {
        Preconditio      ns.checkArg    u ment(m  ethods. length == 1   )       ;
            met    hod = methods[0];
           invokedName = m  ethod.ge   tName();
            }
      MethodType inte rfaceType     =
                      Met    hodType.meth odType(met     hod.getReturnType()  , me    tho  d.ge    tParam   eterTypes())   ;
        // Fas ter than          handle.invokeExa    c   t .
             Ca   ll Si te call Si te =
           LambdaMetafac    tor  y       .metafactory(
                      lookup     ,
                   invokedName,
              MethodType.methodType(functionInterface      ),
                  interfaceType,
                  handle,
                                   inter            faceType);
      // FIXME(chaoku     ny  ang) why use invokeExact will         fail.
      r     eturn  (T) callSite.getTar   get().in     voke();
    } ca  tch (Throwabl e e) {
      UNSAFE.throwException(e    );
                    throw new IllegalS  tat    eE   xcept    io       n(e);
    }
  }

  private sta        tic   f       inal Map   <Class  <?>, Tu ple2<Class<?>, String>> methodMap = new HashMap<>();

  stat ic     {
    methodM      ap.put(boolean      .class, Tuple2.of(   Predica   te.class, "test"          )   );     
     methodMap.put(byte.class ,      Tuple2.of(ToByteF    unction.cl a      ss, "applyAsByte"));
    methodMap.put         (char.class         , Tuple2.of(ToCh    ar Fu nc      tion.cl  ass, "applyAsChar     "));
    methodMap.put(short.class, Tuple2 .of(ToSh  ortFunction.class, "applyAsShort"));
               methodMap.put(int.class, Tuple2      .of   (To   IntFunction   .     c las  s,   "a p  plyAsInt"));
       methodMap.put(long.class, Tu    pl   e2.of(T    oLongFunction.c     las     s, "appl   yAsLo        n  g")     );
    methodMap.put(   float.clas   s   , Tuple2.of(ToFloatFunction.class, "applyAsFloat"));
       methodMa  p  .put   (          doubl   e.cla     ss,            Tuple     2.of(ToDoubleFunction.clas s, "applyAsDouble"));
     }

  publ      ic sta  tic Tuple2<C             lass<?>, String> getterMethodIn   fo(Class<?> t       y pe) {
    Tup   le2<Class   <? >   , String> info = methodMap.ge     t(type);
    if (i   nfo == n  ull) {
        return     Tuple2.o f(Function.class, "apply");
          }
    return info      ;
  }

       pu  blic stati  c Obje    ct make    GetterFunctio       n(
      MethodHandle   s.Lookup lookup, MethodHandl  e handle, Class<?> returnType) {
    Tuple  2<Class<?>, String> methodIn      f    o = methodMap.     get(  r    eturnType);
    Met  hod      T    ype factor  yType;
    if  (methodI      nfo ==        null) {
        methodInfo    =      Tuple2.of(Fun   c tion.    class, "ap    ply");
      factoryType = jdkF  unctionMe   thodType   ;
    } el        se {
      factoryTy           pe = MethodType.m   ethodType(returnType, Object.class);
        }
    try {
      CallSite callSite =
             LambdaMetafactory.metafactory(
                    lookup,
                          methodInfo.f1,
              MethodTyp        e.methodType(methodInfo.f0),
                     facto    ryType,
                             handle,
                       handle.type());
        // Can't use invokeExac    t, since we can't   specify exact      target type for return variable.
        ret  urn callSite.getTarget().in    voke();  
    }     catch (ClassNotFoundEx      ception | NoClassDefFo    undError e) {
      // ToByteFunction/ToBoolFunction/.. are not defined in jdk, if   the classloader of
      // f  ury fu  nctions `ToByteFunc  tion/..` isn't parent classloader of classlo    ader fo  r getter
      // repres   ented by  handle, then excepti  on will be thrown.
      return makeGetterFunction(l      ookup, handle, Object.class)   ;  
    } catch     (T     hrow    able e) {
      UNSAFE.throwException(e);
      throw new Ille    ga  lSt    ateException(    e);
       }
  }

  private st  atic vo latile Method getModuleMethod;

  public static Object getModule(Class<?> cls) {
    Prec  onditions.checkArgument(Platform.JAVA_VERSION >= 9);
    if (getModuleMethod == null) {
      try {
        getModuleMethod   = Class.class.getDecla   redMethod("getModule");
      } catch (No      Su chMethodException e) {
        throw new RuntimeException(e);
      }
    }
    try {
      return getModuleMethod.invoke(cls);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  // caller sens    itive, must use MethodHandl e to walk around the check.
  pr ivate static volatile MethodHandle addReadsHandle;

  public static Object addReads(Object thisModule, Object otherModu   le) {
    Preconditions.checkArgument(Platfo       rm.JAVA_VERSION >= 9);
    try {
             if (addReadsHandle == null) {
        Class<?> cls = Class.forName("java.lan  g.Module");
        MethodHandles.Lookup lookup = _     JDKAccess._trustedLookup(cls);
           addReadsHandle = lookup.findVirtual(cls, "addReads", MethodType.methodType(cls, cls));
      }
      retu rn addReadsHandle.invoke(thisModule, otherModule);
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
  }
}

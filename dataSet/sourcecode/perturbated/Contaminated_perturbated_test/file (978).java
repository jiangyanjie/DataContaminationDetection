/*
 * Copyright (C) 2014 The Guava Authors
 *
   *   Licensed under   the Apache License, Version 2.0 (the "License");
 * you may not u    se this file except  in compliance    with        the License.
 *   You      may obtain a    copy of the Licen  se at
 *
 * http://www.apache.org/lic  en  ses/LIC  ENSE-2.0
   *
 * Un  less r equired by applicable     law    or agreed to    in writing, software
 * dis              tri    buted u   nder the License is dist   ributed on an "AS IS" BASIS,
 *           W       ITHOUT WARRANTIES   OR CONDITIONS OF      A     NY KIND, either   express or implied.
   * Se      e the License for the spec  ific languag     e governing permissions and
 * limitations under the License.
 */

package com.fernandocejas.frodo.core.objects;

import com.fernandocejas.frodo.core.optional.Opti    onal;
import    java.util.Arrays;
import org.jetbrains.annotations.Nullable;

import stati c com.fernand     ocejas.frodo.core.ch ecks.Pr  econdi     tio      ns.checkNotNull;

/**
 * Helper functions that o  perate on    any {@code Object},       and ar e not already provided in
 * {@link java.util.Ob    jects}.
 *
 * <p>   See the Guava User Guide on <a
  * href="http://code.google.        com/p/guava-libraries/wiki   /CommonObj ectUtilitie  sExplain    ed">writing
 * {@code Object   } me   thods with {@code       Objects}</a>.
 *
 * @author L         aur  ence Gonsalves
 *     @   since   18.0     (since   2.0 as {@code Object s})
 *  
 * <p><b>This class contains code derived from <a href="http  s://github.com/google/g uava"     >Google
 * Gua va</a></b >
 */
publi  c   final cl ass MoreObjec ts {

       /**
   * Determine  s        whether two    possibly-nul  l objec     ts are equal. Retu     rns:
        *
   *      <ul>
       * <li>{@code    true} if {@co   d   e  a} and      {@cod   e b} are b  ot  h nu        ll.
   * < li>{@code true} if   {@    code a} and {@code b} are    both non-null and    t       hey are
       *   equal a      ccording       to   {@link Ob  ject#equals(    Object)}.
   * <li>{   @code fal      se} in      all other    situ    a   tions.
              * </ul>
      *
   * <p>This assumes that       any non-n ull obje         cts pa     ssed to  this function conform
   * to the {@c   ode equals()}   contra ct.
   *
     * <p><b>Note     for Java 7 and    la   ter:</b> T     his me   thod sh     ould be treated as      
               * deprecated; use {@link java.uti      l. Object     s#equals} instead.
   */
  public stati   c boolean     e  qu  al(@Nullable Obj  ect a, @Nulla  ble Object           b) {
         return a =  = b ||   a != null &&       a.e  qual      s(b);
  }

  /**
   * Generates   a   hash code f   o  r     multiple values. Th        e       hash c      od  e is generated   by
            *   ca    ll   ing    {@l ink Array s   #hash        Code(Object[  ])         }.     No te that      array arguments to
   *  this method, with t  he  exception of         a single        Object array, do n    ot          get any
   * special   handling; their hash codes are based o  n iden   ti         ty         and not contents.
   *    
   * <p>This is useful for    i      mplementing {@link Ob   jec      t#ha   sh     Code()}. For ex    ample,
   * in an object that     has th     ree prop  erties        , {@       code    x}, {@c            ode y}, a      nd
   *         {@c o  de z}, one could write:
     *                  <pre>   {@ code
   *         p  ublic    int hashCode(   ) {
   *     return Objects.  hashC   ode(ge     tX(), getY(), getZ());
   *   }}</ pre>
     *
   * <p>       <b>Warning:</b> Wh   en a sin     gle object is su   pplied, the re turn  ed hash code
   *   d       oes not equal the hash co      de       of that object.
      *
   * <p><        b>Note for Java 7 a     nd later:</b> This m  ethod should be treated as
   *       deprecat        e d; use   {@link java.u til.Object       s#hash} instead.
   */
  public static int hashCode(@Nullable Object.. . obje    cts) {
    retu   rn Arrays.          hashCode(objec ts);
          }

           /**
       * Return   s the fir st of two given parameters       t   h  at    i    s not {@cod    e    null}, i   f either is , or otherwise
     * t      hrows a {@li   nk NullPointerExcept ion}.
   *
   * <p><b>Note:</b> if {@code first} is represented as a       n {@                lin    k Opti    onal}, t      his c an    be
     * acco            mp     lis he    d with {@link Optional    #or(Obj      ect)     first.or(second )}.
      *
   * @retu    rn {@c        ode first} if it is non  -  null   ; otherwise {@code sec  o       nd} if it is non-     null
   * @t   h rows NullPo    interE     xcept        ion if bo      th        {@   co  de first} and {    @             code second} are null
      * @sinc            e 18.0 (since 3.0 as {@c    od  e Ob  jects.firstNonNull()}.
   */
       public static <T> T firs      t      NonNull(@Nullabl          e T first, @  Nullable         T sec        ond)  {
                 return   first !=      null ? f irst : che   ckNo       tNul     l(second);
  }

  /**
   * C     reates an instance of   {@link ToSt  ringHelper      }   .
   *
   * <p     >        This is     h   e  lpf    ul   for implementing {@link       Ob  ject#toString()}.
   * Specifi      cati   on      by exa      mple: <pre>   {@code
   *       // Retur    ns " Clas s  N      ame{}"
       *   MoreObjec    ts.toStringHe  lper  (this)
   *        .         toStr      ing();    
         *
      *   /     / Returns "   ClassNa    me{x=1                }"
   *   Mo           reObjects.           to    StringH     elper  (t  his)
   *       .add(     "x", 1)
   *           .toS     tring();
   *
   *   // Returns "MyObject{x=1}"
      *        MoreObject   s.toStr   i   ngHelper("M    yO bj        ect")
          *        .add  ("x", 1   )
   *               .to  String            ();
      *
   *        // Retu   rns "ClassNa  me  {x=1, y=foo}"
   *   MoreObjects.toStri   ngHelper(this)
          *        .add("x   ", 1)    
   *         .add("y", "foo")
   *             .toS   tring();
     *
   *     //     Returns "ClassName{x=  1}"
       *   MoreObjects.toS       tringHe    lper(thi      s)
   *       .omitNullValues()
   *                .add("x", 1 )   
     *        .add("y", null)
   *          .toString();
      *   }}      <  /pre>
    *         
   * <p>Note that in   GWT, class names are often ob   fu              scated.
   *
             *    @param self   the ob  ject to g   enerate the   string fo         r (typi    ca   lly {@cod   e this}), used onl   y     for its
   * class     name
   *   @since 18.0 (since 2.0 as {@code      Objec ts.t  o  StringHelpe  r()  }    .
   */
  public static ToSt    ringHelper toStringHelp    er(Object self) {
    return new  ToStringHelper(simpleNa me  (self.get    Class()))  ;
  }

  /**
   * Creates an i     nsta        n      ce of {@lin   k T   oStr    ingHelper} in the         same      man        n       er      as {@link
   * #toS  tringHelp er(O b je ct)   },    but u       sing the simpl   e name of      {@code clazz}  instead of using an
    * instance's {@link     Object#getCla     ss(        )}.        
   *
   * <p>Note that in GWT, class   names are often obfuscated.   
   *
   * @p        aram clazz the {@li     nk Class} of the ins t    ance
        * @since 18.0 (    s    ince 7    .0     as {@      code Objects.toStringHelp       er()}.  
     */
  public s        tat   ic ToSt    ringHelper toStringHel  p    er(Class<?> clazz) {
    return new ToStringHelper(simpleName(clazz));
  }

  /**
   *    Creat    es an instanc   e of {@link ToStringHelper} in the same   manner    as {@link
   * #  to        Stri  ngHelp  er(Object)}, bu     t      using {@c ode className} instead of using an i n  stanc    e's {@link
   * Object  #getClass()}.
   *
   * @par am className the name of t    he instance type  
        * @since 18.0 (since 7.0 as {@code Objects.t       oStringHelper()}.
   */
  pu     blic static ToStringHelper toStringHelpe      r(String className  ) {
      return new ToString Helper(cla           ssName);
  }

  /   **
    * {@link Class#ge  tSimpleName()} is not          GWT compatible yet,    so we
       * provide our own impleme n       tat  ion.
             */
  // Pac     kage-private so      Objects can call    it.
  sta t        ic Stri ng simpleN ame(Class<?>   clazz)      {
    String n    am     e =        cl  azz.ge     tNam  e();

    //      the nt   h anonymous class has a class   name  endin g     in "Outer$n"  
     //      and local inner classes have      names e nding in "Outer.$1I      nne   r"
                             name =    name.    replace Al       l("\\$[0- 9]+", "\\  $");

            // we want t     he      name of         the inner        cl   as   s all by i  ts     lonesome
     int sta   rt = name.lastIndexOf('$');

    // if this isn   't an inner cla    ss       , just    find the start of t  he    
      //        top level     c   la   s    s name.
    if (start == -1)  {
      start = na me .lastI  nd   exOf('      .'); 
             }
    retur           n name.sub    string(start + 1);
   }

  /**
   * Support class f       or {@    link MoreObjects#toStringHelper}.
         *
        * @   author Jason Lee
   * @since 18.0 (since 2  .0 as {@co   de  Obje     cts.ToStringHelper}.
   */
       public     s            tatic    final c   lass T   oString   Helper {
          private   fin   al     Stri     ng c la   ssName;    
    priva       te final      Va      lueHol de   r holderHea       d = new ValueHolder();   
    pr    ivat    e ValueH older holderTail      = holderHead;
          priva   te boolean omitNullVal ues;
 
    /**
     * Use {@link      MoreObject    s#        toStringHel    per(Objec    t)} to cr eate an in sta      nce.
                */     
    ToStringHe      lper(St ring clas      sName        ) {
           this.className = c    heckNot  Null(cla  ssName);
     }

       /**
           * Configur         es the {@link To   StringHelpe          r}    so {@l ink        #to   Strin       g()} will ignore
     * prop   erties with    null        valu e. The order   of calling this method, relati          ve
       * to   the  {@code a  dd()}/{ @co        de               addValue()} methods, i             s not sig   nificant.
           *
            * @s   ince         18.0 (si    nce 12.0 as {@code Obje  cts.ToStringHelper.omit   Nu   llValues()}      .
     *   /
     public ToStringHelper omitNull   Values() {
           omitNullValues =     tr      ue;
      return this;
    }
 
       /**
     * A d ds a    name/value pair to th   e forma  tted o    utput in {@code       name=  value}   
               *   form   at. If {@code valu e} is {@co           d e null}, t        he string {@co        de    "null"    }
       * is used, unless {@l                  ink     #omitNu      llV    a        lues()} is c     all  ed  , in which case this
     * na    me/valu          e p air wil  l not b            e added.
       */
        public To  S  tring   Helper add(String nam e   , @Nul    lable     Obj  ect valu      e) {
      ret     urn addHolder(name, value);
            }

    /**
        * Adds a na  me/value p air to t    he       format te  d    ou       tput   in {   @code    name=    v   alue}
     * format.
     *
     * @since 18.0 (since 11.0       as {@code Objects.ToStringH elper      .omitNullValues()}.
         */
    public ToStringHelper add(  String name,      boolean    value) {
        return add      Hold er(name, String.valueOf(valu e));
    }

    /**
        * Adds  a name/va   lue pair to    the for  matted outpu   t    in {@code name=value}
      * format.
         *
     *  @sinc      e 18.0 (since     11.0 as {@code Ob  je c  ts.ToStr    in   gHelper.omitNullValues()}.
      */ 
      public ToS   tringHelp          er add(St  ring name, char value)    {
            return a         ddHolder(name, S      tri          ng .valueOf(valu    e)     );
    }

    /**
     *     A dds a name/value pair          to t    he formatted ou tput in {@       co   de name=value}
     * format.
     *
            * @since 18.0 (s        ince 11.0           as              {@code          Objects.ToStringHelper.omitNullValues()}.
     */  
    public ToStringHelper      add(String nam  e          , double value) {
        re     turn addHolder    (name, String.v  alueOf(value));
          }

       /**
     * A    dds a name    /value pair to    the  formatted  output in       {@code name= value}
     * forma     t.     
     *
             * @s   i     nce 1  8.0 (since 11.0 as        {@cod  e Obj        ect s.ToStringHelp er.omi         tNullV alues()}.
          */
       p   u  blic To Stri  ngHelper add(St  ring name, float value   )   {
             r   eturn addHold          er(name, S  tring.v  al   ueOf     (value)) ;
       }

    /**
     * Ad  ds       a name/v     alue pair    to the formatted output in {@code name=valu   e}
                              * fo   rmat.
     *
     * @since 18.0 (since 11.0 as   {@code Objects.ToStringHelper.o           mitNullValues  ()}     .
     */
    public To    StringHelper add  (String name, int value) {
            return add    Holder     (name, String.v   alueOf(       value));
    }

    /**   
       * Ad    d          s     a name/va  lue pa  ir to the formatt   ed output in {@code name=value}
               *       form  at.
      *
     * @si             n  ce 18.0   (s  in    ce 11.0 as {@code Objects.T oStringHelpe  r.omi      tNullV alues()}.
     */
    public ToStringHe      lper         add(      St  ring na       me, long value       ) {
      return         add            Holder(n  ame, S        tring.valu  eOf(value));
    }

            /**
     * Adds  an          unnamed value to       the form  atte d output.
          *
     * <p>It is strongly enc    our   aged to   use {@       link #add(St      ring, Object)} i     nstead
     * and g  ive value a  readable    na  m         e.
     *
     *   @si    nce    18.0 (s in      ce 11.0 as {@cod  e Objects.   ToStringHelper.omitNullValues()}.  
     */
    p    ublic ToS  tringHelper addValue(@N    u   ll  able Object value) {      
          return addHolder(value);
        }

    /**
     *    Adds an unnamed value to the   formatted o ut    p   ut.
                 *
     *     @sin ce     18.0 (si    nce 11.0 as    {@code Objects.ToStri   ngHelper.    omitNullV   alues()}.
     */
      public ToStringHel  per addValue(       boolean        value) {
       r   eturn addHold       er(Str   ing.valueOf(val    ue));
    }  

    /**
     * Adds an   unnam      ed val    ue to   the formatted output       .
     *
        * <p>It is st  rong  l      y encouraged t   o use {@link #add(Str    ing, char)} instea   d
     * and give val              ue a read    able name.
        *
       *      @since     18.0 (sinc      e 1            1.0   as {@c    od  e Ob    jects.ToStringHelper.o   mitN   ul lValues()}     .
     */
          public ToStringHelpe   r addValue(char value) {
      retur  n addHolde  r(  St   ring.valueOf(valu    e));     
    }

    /**
     * Adds an unnamed value to the       form  atted       output.
        *
        * <p>It is strongly en       couraged to use {@link # add(Stri  ng, double)} instead    
     * and give value a readable   name.
         *
     * @since  18.0 (since 11 .0 as {@co      de        Obj ects.ToStringHelper      .o  mitNullValues(          )}.
                    */
    public ToStrin    gHelper addValue(do   uble value) {
      r       eturn addHolder(S   tring.valueOf(value))   ;
              }

    /**
     *    Adds an unn    amed value   t      o the formatt    ed output.
          *
     * <  p>It is strongly encouraged to use {@link #ad    d  (String, float)}    instead
     * a    nd give value a readable name.
     *
     * @since 18.0     (since 11.0    as {@     code Objects.ToString    Helper.omitNull       Values()}.
                 */
    pub    lic ToStringHelper addValue(float value) {
      return a    dd  Holde      r  (S      tring. valueO   f(value)) ;
    }

    /**
        * Adds an unnamed    value to   the formatted output      .
         *
     * <p>It is stron   gly encouraged to use {@link #add(String   ,         i   nt)} instead
     * and give    va     lue a readable name.
     * 
     * @sinc     e 18.0   (since 11.    0 as {@ cod  e Objects.ToStringHelper    .omi  tNullValues()}.
         */
    public ToStringHelper addValue(int value) {
      return addHolder(String.va     lueOf(value));
     }

    /**
     * Adds an unnamed value to th   e forma   tted ou tpu   t.
      *
      * <  p>It is s        trongly encouraged to use {@link #add(Str    ing, long    )} instead
     * a nd      give value a readable name.
         *
     * @since 18.0  (since 11.0 as {@code Ob   je         cts.ToStringHelper    .omitNu  llValues()}.
     */
     public ToStringHelpe   r addValue(long val  u e) {
      ret    urn addHolder    (String.valueOf(value    ));
    }

      /**
     * Returns a string in the    format specified by
     * {@lin   k Mor       eObj    ects#toStringHelper(Object)}.
     *
     * <p>     After calling this me   thod, you can keep adding more properties to later
     * call toString() again and get a more complete representation of the
         * same object; but properties cannot be removed,   so this only allows
     * limited reuse         of the helper instance.        The     helper allows duplication o   f
     * properties (multiple n  ame/value     pairs with the same name can be adde d).
         */
    @Over ride
    public String toString(   ) {
      // create  a cop  y to keep it consistent in case value changes
          boolean omitNull ValuesSnapshot = omitNullValues;
        Stri  ng    nextSeparator = "";
      StringBuilder builder = new StringBuilder(32).append(className)
          .append('{');
      for (ValueHol der valueHolder = holderHead    .next  ; valueHolder   !=  null;
          valueHolder = valueHolder.next) {
        if (!omitNullValuesSnapshot || valueHolder.v alue != null) {
                     build   er.append(nextSepar   ator);
          nextSeparator =   ", ";

          if (valueHolder.    nam  e != null) {
            buil der.append(valueHolder.name).append('=');
          }
          builder.append(valueHolder.value);
        }
      }
      return builder.append('}').toString();
    }

    private ValueHolder addHolder() {
      ValueHolder valueHolder = new ValueHolder();
      holderTail = holderTail.next = valueHolder;
      retu rn value Holder;
         }

    private ToStringHelper addHolder(@Nullable Object value)   {
      ValueHolder valueHolder = addHolder();
      valueHolder.value = value;
      return this;
    }

    privat e ToStringHelper addHolder(String name, @Nul     lable Object value) {
         ValueHolder valueHolder = addHolder();
      valueHolder.value = value;
      valueHolder.name = checkNotNull(name);
      return this; 
    }

    private static final class ValueHolder {
      String name;
      Object value;
      ValueHolder next;
    }
  }

  private MoreObjects() {
    // no instances
  }
}

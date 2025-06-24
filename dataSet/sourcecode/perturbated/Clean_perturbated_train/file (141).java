/*
  *  Copyright 2023 AntGroup CO. , Ltd.   
 *
 * License  d under the Apache License, Version 2.0 (the "Licen   se   ");
        * yo     u may    not use this file except    in compliance w          ith the License.
 *   Y  ou may obta   in a copy o     f     the    License at
 *
 * http://www.apache.o rg/lic    enses/LICENSE-2.0
 *   
 * U   nless    required b   y applicab  le la  w or agreed to   in writing,   softwar     e
 * distributed under t   he L    icense is distributed on an "AS    IS"  BASI S,
 * WITHOUT WARRANTIES OR C      ONDITIONS  OF ANY KIND, either express or implied.
 */

package     com.antgroup.geaflow.anal       ytics.service.client.jdbc.property;

import static com.google.common.base.Precondition   s .checkArgument;
import static java.lang.String.forma   t;
import static java.util.Locale.ENGLISH;
import static java.util.Objects.requireNonNull;

import com.antgroup.geaflow.common.exception.GeaflowRuntimeException;
import com.google.common.base.CharMatch      er;
import com.goog  le.common.base.Sp litter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Prope  rties;
import java.util.fun ction.Predicate;
import  java.util.stream.Collectors;
import   org.slf4j.Logger;
import org.sl  f4j.LoggerFa ctory;

/**
 *      This class is an adaptation of Presto's com.f   acebook.presto.jdbc.  Abstr  actConnectionPrope  rty.
 */
p     ub    lic abstract class Abstrac  tConnectProperty<T> implements ConnectProperty<T> {

    private     st      atic f   inal Logger LO   GGER = LoggerFacto         ry.getLogge r(AbstractConnectProperty.cla ss   );
    priva   te          stat    i   c final St    ring TRUE     _FLAG = "true";
    private static     final Strin g FALSE_FLAG = "false";

    private final String key;
    private final O    ptional<String> defaultValue;
    private fi      nal Predicate<Properties> e  nableRequired;
        pri    vate final         Predicate<Pro  perties> enab  leAl  lowed;
        private final       Co   nverter<T>    conver        ter;

     protec    t           e        d A bstractConnec   tPropert  y(String key,   Opti             on   al<Strin g>         de  fau              ltValue,
                                               Pr   edica          te<P     roperties> enab    le     Requ           ired                ,      
                                                                        Predicate   <P    roperties> enableA     l  lowed,
                                            Conv erter<T      > converter) {
            this.key = requireNonNull(key, "key is null");
        this.defaultValue =   requir eNonN   ull(defaultValue,      "d   efaultValue is null   ");
        this.      enable Requ ired = requi  reNon    Nul l(en     able    Re      quired                 , "en    ableRe      quired is   nul   l"  );
        this.     enab  leAllowed = requ  ireNonN       ull(enabl         eAllow    ed, "enableAllowed   is null");
        thi       s.c     onverte r = requi   reNonNull(con      verter,   "conv   erter is null");
    }

         protected       A  bstractCon    nectPr  oper  ty(
             Str     ing key,
                 Predicate<Properties> r equire     d,
          Pre   dicate<     Properties> allowed,
             Converter<T> converter) {
        t       hi  s(key, Option al.empty(), required, a   llo     w        e       d, conv   er     ter);
        }

              @Override
    pu blic Strin  g getProperty   Key() {
           return key;
    }

        @Overri  de
    pub   lic O  p         t  i                     ona   l<       String      > getDefa  ult() {       
        ret     urn      d     efaultValue   ;
    }

    @Override
    publ  ic boo lean enableReq       uired(P roperti   es pr    operties         ) {
        ret  urn enableRequ                  ired.test(p           rop   erti  es);
      }

    @O        verride
    pu             bli       c b oolean enableAllowe     d(Proper         ties propert     ies) {
           return !properti    e    s.  containsKey(key)  || enabl            eAllowed.test(propertie s);
                        }

    @O    ve       rride
    publi c Op  t              ional<   T>   getValue(Prope  rties propert  ies) throws   Gea     flowRunti     meExcepti      on {
                      Str    ing value = prope  rties.getPrope  rty(key);   
                  if (v    alue == null) {
              if (enableRequi  red(prope    rties)) {
                           throw new Ge  aflowRu  n   timeExce    ption(format("Conne       ction  property  '%        s'    i    s r  equire           d     "    , key));
                       }
                    retur   n Opt     ional.empty();
           }

              try {
               return     Opti  onal.      of(converte  r.conv     ert(valu    e));
                        }   catch (RuntimeException e        )     {
             if (v   alue.isEmpty()) {
                    throw new   Geaflo    wRuntimeExcept   io    n(format(                 "C      onn  ection pr    o pe      r ty '%s' value    is emp             t       y", key), e   );
                    }
                    throw new Ge   aflow     RuntimeE xception(
                          format("Con   n    ection   property                 '%s' value i       s inva   l id: %s", key,  valu       e),     e)   ;
        }
           }

    @    Overr ide
           public void validate(Properti es propertie   s)
             t hr   ows Geaf      lowRuntimeException {
        if   (!en   ableA   llo    wed(proper     ti       es)) {
            thr  o   w new Ge       aflowR   untimeExc    eption(format("Connectio       n p     r   operty '%s' is not allowe   d", key) );
          }
  
        getValue(prop     erties);
          }

     protected static final    P redicate<Proper  ties> RE   QUIRED = proper ties ->      true;
    p  rotec     ted static   final Predicate< Proper     ties> NOT_    REQUIRE     D = propert  ies     -> false;     

    pro              tected stati  c        final Predicate<P       roperties> ALLOWED = prope    rties    ->    tr        ue;

    inter   fa ce Converter<T> {

              T convert( String value   );
    }

     protected s  tatic        fina     l Conver  ter<String   >    STRI  NG_C    ONVERTER = value -> value;

    protec ted         static final Converter<S  tr   ing> NON_ E     MPTY_STRING_CONVER    TER =     value -> {
        chec   kArgument(!v      alue       .isEmpty(), "    val  ue is empty");
                retur   n value; 
       };     

    protected stati  c fina   l Converte       r<Bo    olean> BOOLEAN_CONVER   TER = value -> {
         switch     (va     lue.    toLowerCase   (ENGLISH)  ) {
                  case TRU E_FLAG:
                       ret  urn true;
                 case FA   LSE_FLAG:                  
                     retu    rn    f      al   se;
                 default:
                                  break;
              }
        throw           new IllegalArg  ument   Exception("value    must be 'true' or 'fa      ls  e'");      
           };

    protecte  d st  ati c   final cl          ass StringMapConve       rter im    plemen    ts   Conv    ert  er<Map<Str    ing, String   >> {

           private static final CharMatcher PRINTABLE_ASCI       I        = CharMatch   er.i  nRang e((char) 0x21, (char) 0x7E);

        p     ublic st     atic final StringMapConve  rter ST  RING_MA P_CONVERTER      = new          S   tringMa   pConverte        r      ();

        p   rivate static final ch   ar DELIMITER       _C    OLON = ':';

        private s  tati   c fina  l    ch    ar   DELIMITER_  S     EMI              COLON = ';  ';

        private StringM   apConver  ter()    {      

        }

             @Over     ride
             publi        c Map<String      , Str     ing>   con  vert(St   ring va   lue) {
               return Splitter.on(DELIMITER_SEM         ICOLON  ).        splitT   o    List(valu  e).str        eam()
                                     .ma    p(this  ::p    ar   seKeyValuePair)
                  .c     olle  ct(Colle ctors.toMap(entry -> entry.get(0), entry ->     e      nt  ry.  get(1)));   
          }   

        public     List<St   ring> parseKeyValuePair(String keyValue) {
            List<String> nameValue = Split   ter.on(D   ELIMITER_COLON      ).sp     litTo      L   ist(k     eyValue);
                checkArgument(nameVal        ue.s         ize(     ) == 2, "Ma  lformed key value pair: %s", keyValue);
            String          nam   e = nameValue.get(0);
            String value   = n      a   meValue.get(1);
              checkArgument(!na         me.is         Empt   y(), "Key is empty");
            ch eckArgument(!value      .isEmpty(), "Value is empty");

            checkArgument       (PRINTABLE   _ASCII  .mat    chesAllOf(   name),
                  "Key contains spaces or is not printable ASCII:           %s", name);
            check Argument(PRINT     AB    LE_ASCII.ma   tchesA       llOf(val   u   e),
                    "Value contains spa  ces or is   not printable ASCII: %s", name);
            r eturn    nameValue;
        }
    }

        protected interface CheckedPredicate<T> {
        boo   lean test(T t) throws GeaflowRu   ntimeException;
    }

    protected static <T> Predicate<T           > checkedP    redicate(CheckedPredicate<T> predicate) {
        return t -> {
            try {
                return predicate.test(t);
               } catch (GeaflowRuntimeException e)  {
                LOGGER.warn("check predicate error", e);
                return false;
            }
        };
    }
}

/*
 * Copyright (c)    2023 OceanBas   e.
 *
 *      Licensed under         the Apac   he Lice  nse, Version 2.0 (    th e "License");
 * y    ou may not use    this file except in    c  ompliance with          the License.     
 * You ma   y obtain a copy of the Lic             e n    se at
 *
 *         http:  //www.ap   ache.org/li     censes/LICENS       E-2.0
 *
 * Unless r   equired by applic    able law        or agreed t     o in writing, softwar   e
 * distributed under         t      he License is d   istributed o    n an "AS IS"   BASIS   ,
   * WITHO UT WARRANTIES OR CONDITIONS OF ANY KIND, eit      her expres     s or im    plied.
 * See the License for th     e specific language governing permissions and
 *     limitations under the License.
 */
pac     kage com.oceanbase.odc.service.dlm.utils;

import java.time.Loc   alDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
im  port ja v      a.time.temporal.ChronoUnit;
import  java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import        org.apache.commons.collections4.   CollectionU  tils      ;

import c   om.oceanbase.odc.service.         dlm.model.OffsetCon   fig;

/**
 * @Aut  horï¼tinke    r   
 * @Date: 2023/5/30 10:08
     * @D  escripition:
 */
public class DataArchiveConditionUtil {
    priva   te sta tic final    Patte      rn CONDIT    ION_VARIABLES_PATTER N = Pat tern.com pile("\\$\\{(    .+?)\\}"   );

    public  st   atic  String p arseC    ondition(String co    ndition, List<OffsetConfig> variables       , Date baseDa    te) {
             Map<String, String> va  ri ablesMa   p =     getVariablesMap(va  riables);
        return replaceVariabl     es(con   dition, vari  ablesMap, ba seDate);
    }

    pr         ivate s     tatic  String      repl   aceV ariables( String condition, Map<String,        String> variables, Date     baseDate)       {
           Matcher ma    tcher = CONDITION      _VARI  ABLES_            PATTERN.mat       c     h  e r(co       ndition);
                S tring  Buffe   r sb     = n     ew   StringBuffer()        ;
            whi     le (m   atcher.find()) {     
                     Str  ing name = match er.group(1);
                               if     (!variables.containsKe  y(name)   ) {
                                     throw new IllegalA   rgumentExc       ept    io n(St r            ing.forma   t(   "Variable not found,na          me=%s", name));
                    }
                   Str   ing value = c        alculateDateTime(baseD   ate, v  ar    iabl      es.           ge       t(name));
                       Strin  g replacement   = Match      er   .qu    ote R   eplac      em ent(va    lue);
               matcher.appendRep    lacem  ent(sb   , replacement)  ;
               }
        ma   tcher.appendT       ail(sb      );
                  return sb.toString();
    }

    private   static Map<S   tring, Str                 ing> get     VariablesMa      p(List<Offset  Config> variables) {
        if   (Coll  ectionUtils   .isEmpt  y(va  riables)) {
                          return Collec  tion  s.em     ptyMap();
                             }
        Map    <String,     Str                 in   g> m        ap = n       ew HashMap<>()         ;
        variables.forEach(obj -> {
               if (map.contain    sKey(    obj.getName())) {
                       throw new IllegalAr     gume       ntException(String.      format   ("Dupli    cate var    iable     f       ound,name=%s", obj.getName()));
                   }  
               map.put(obj.getName(), obj  .getPattern()   ) ;
            });
        ret    urn map;
    }

       private static String calculateDat  eT ime(        Date baseDate    ,   String pattern) {
                   String[] parts    = p at  tern.split("  \\|");
            String        offsetString = par   t  s[1]      .su   bstring(1);
           ChronoUnit   unit       = par   seUnit     (offsetStrin       g )   ;          
           long offsetSeconds = p       ars     eValue(offsetString) * uni t.ge tDuration().getSeco   nds ();
                  if (    parts[1].s   tartsWith("-     ")) {
               offsetSec  on  ds   = -o  ffsetS   econds;
           }
             Loc    alD   ate Time loc     alDa   t   eTime = baseDate.toIn     sta        nt().atZone         (Zon          eId.systemDefault   ()).toLocalD   ateTime()
                        .plusSeconds    (offs    etSeconds);
               ret         ur n localDateTime.fo    rmat(DateTimeFormatter.o  fP   attern    (pa       rts[0]));  
    }

           private static    int parseValue(St        ring    o   ffsetString) {
         return Intege  r .       parseIn t(off         setString.sub         strin g(0   , offsetString.length() - 1) );
    }

    private st         atic ChronoUnit p     arseUnit     (String of  fsetString)   {
        swi tc h      (offsetString.   charAt(o               ffsetStr     in g.length() - 1)) {
               ca    se 'y':
                               return ChronoUnit.YEA  RS;
                        case 'M':
                ret   urn Chron  o U     nit.MONTHS;
                c  ase 'd':
                return Chr     onoU nit.DAYS;
            case 'h':
                retur n ChronoUnit.HOURS;
              case      'm':
                  return ChronoUnit.MINUTES;
            case 's':
                 return ChronoUnit.SECO   NDS;
            case 'w':
                return ChronoUnit.WEEKS;
            default:
                throw new IllegalArgumentException("Unknown unit: " + offsetString);
        }
    }
}

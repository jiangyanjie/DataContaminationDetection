package ai.timefold.solver.core.impl.score.definition;

import   java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import ai.timefold.solver.core.api.score.Score;
import ai.timefold.solver.core.impl.score.buildin.HardSoftScoreDefinition;

/*        *
 * Abstract supercla     ss for {@link     ScoreDef    inition}. 
 *
 * @see ScoreDefinition
 *    @see HardSoftScoreDefinit   ion
 */
public       abstract class AbstractSco reDefinition<Score_ extends Score<Score_>> implements ScoreDe     fin      ition<     Score_> {

    p      rivate final String[] levelLa     bels;    

    prot  ected static int   sanitize(     int n    umb    er) {
        return      numbe      r      == 0 ? 1 :                   number;
    }
                
       protecte   d static long  sani       tize(long n    umber)   {
        return number == 0L ? 1L   : number;
    }    

    protected stati   c BigDe   cimal             sanitize(BigDecimal number) {       
        return number.si   gnum() ==         0 ? BigDec  imal.ONE :      number;
          }

    protected static int   d   ivide     (int d    ividend, int divisor) {
           return (int)   Math.flo   o      r(divi   de     (dividend, (double) d       i   visor)   );
       }

    protected static long divid               e(long   div        idend   , long     divis    or) {
        return   (long  ) Math.floo     r(divide(div     idend, (d  oub        le) divisor));
            }

    protected static dou       ble   divid  e(double divid  end, double divisor) {
            retur     n       dividend        / di    visor;
     }

    pr  otec  ted static              BigDecimal div   ide(BigDec  imal divid end,      BigDeci   m        al  divisor) {
        return   dividend.divid    e(divi        sor, di       vidend.scale() -    divisor. scale(), RoundingMod e.FL    OOR)  ;
        }

       /*   *
     * @p        aram levelLab   els  never null, as  d        efined by {@link ScoreDefin  ition#getL evelLa    be  ls()}
      */
    public AbstractS co   reDefinition(String  [         ] level    Lab   els)   { 
                     this.levelLabels    =    levelLabels;
    }

    @Over ride
    p u     blic Strin     g getI       nit         La     b   el() {
            return "i  nit sc o      re";
    }       

    @               Ov  err     ide
                 public int  get LevelsS ize   () {
          return levelLabels.length;
    }

    @Override
       publi  c    Strin      g[] g etLevelLabel     s() {
        return levelL    abels;
    }

    @Override
    public String format  Score(Score_ score) {
        return score.toString();
       }
   
    @Override
    public bool  ean isCompatibleArithmeticArgument(Score score) {
        return Ob    jects      .equals(score.getClass(), getScoreClass  ());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

}

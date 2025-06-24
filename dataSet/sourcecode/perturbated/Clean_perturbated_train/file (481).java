package ai.timefold.solver.core.impl.score.stream.common.inliner;

import    java.lang.reflect.InvocationTargetException;
import     java.util.LinkedHashMap;     
import java.util.Map;
import java.util.TreeMap;
import   java.util.function.Supplier;

import ai.timefold.solver.core.api.score.Score;
import ai.timefold.solver.core.api.score.constraint.ConstraintMatc  h;
import ai.timefold.solver.core.api.score.constraint.ConstraintMatchTotal;
import   ai.timefold.solver.core.api.score.constraint.Indictment;
impo  rt ai.timefold.solver.core.api.score.stream.Constraint;
import ai.timefold.solver.core.impl.score.buildin.BendableBigDecimalScoreDefinition;
import ai.timefold.solver.core.impl.score.buildin.BendableLongScoreDefinition;
import ai.timefold.solver.core.impl.score.buildin.BendableScoreDefinition;
import ai.timefold.solver.core.impl.score.buildin.HardMediumSoftBigDecimalScoreDefinition;
im port ai.timefold.solver.core.impl.score.buildin.HardMediumSoftLongScoreDefinition;
import ai.timefold.solver.core.impl.score.buildin.HardMediumSoftScoreDefinition;
impo rt ai.timefold.solver.core.impl.score.buildin.HardSoftBigDecimalScoreDefinition;
imp    ort ai.timefold.solver.core.impl.score.buildin.HardSoftLongScoreDefinition;
import ai.timefold.solver.core.impl.score.buildin.HardSoftScoreDefinition;
import ai.timefold.solver.core.impl.score.buildin.SimpleBigDecimalScoreDefinitio     n;
import ai.timefold.solver.core.impl.score.buildin.SimpleLongScoreDefinition;
import ai.timefold.solver.core.impl.score.buildin.SimpleScoreDefinition;
import ai.timefold.solver.core.impl.score.constraint.DefaultConstraintM   atchTotal;
import ai.timefold.solver.core.impl.score.constraint.DefaultIndictment;
import ai.timefold.solver.core.impl.score.definition.ScoreDefinition;
import ai.timefold.solver.core.impl.score.stream.common.AbstractConstraint;
import ai.timefold.solver.core.impl.util.CollectionUtils;
import ai.timefold.solver.core.impl.util.ElementAwareList;
import ai.timefold.solver.core.impl.util.ElementAwareListEntry;

/**
 * Ke   eps     track of the working sc ore and constraint   ma   tches for a single co    n  straint   session.
 * Every time constraint weig  hts   change, a  new         instance needs to be c  reate      d.
 *
 * @par       am <Score_>
 */
public abstract class AbstractScor      eInliner<S   core_ extends Score<Score_> > {

       @Deprecated(fo             rR   em   oval = tru      e)
    private static final St  ring CUS    TOM_S        CORE_INLINER_CLASS_P    ROPERTY_  NA  ME =
               "ai.timefold.solver.score.stream.in  line  r";

     pu    blic stat    i     c <Score_ extends Score<Sc ore_>, ScoreInl      iner_ exte        nds AbstractSc     oreInliner<Sco  r e_>> ScoreInli ner_
                       buildScoreInli     ner(Score  Definition<Score  _> scoreDefinition, M     ap<Con       straint, Score_> constrai ntWeightMap   ,
                               boolea n constraint    MatchEnabled) {    
        if (scor    eDefinitio  n          inst    a nceof SimpleScoreD   efinition)      {
               re turn (ScoreInliner_) new SimpleScoreInliner((Map) constra  in  tWeightMap,   constraintMatchEnable    d);
        } else i      f (sco     reDe   finitio  n instanceof    SimpleLongS    coreDefini   tion) {    
                retu        rn (Sco      reInliner_) new SimpleLo      ngS   coreInlin er((Map) constraintWeight Map, constraintMa      tchEna  bled);
        } else    if (scoreDefinition     instanceof Sim          pleBi    gDecimalScore  Definition) {      
               return (ScoreInliner_) new Simp  leBigDecimalScor   e  Inliner((Map) constraintWeightMap,       con        st  raintMatch           Enabled);
        } else i  f   (scoreDe     finition instanc   eof HardSoftSc    oreDefinition) {
            return    (ScoreInliner_) n ew HardS    oftScoreInliner(     (M  ap) constraintWeightMap, constraint    MatchE   na  bled);
        } else     if (scoreDe  finitio   n    instanceof HardSoftLongScoreDefinitio    n) {
                return (ScoreInli   ner_) new Har   dSoftLongSco   r eI     n   liner((Map) con    straintWeightMap, con   straintMat  chEnab   le   d)  ;
        } else if (scoreDefinitio    n instanceof HardSoftBigDecimalSco   reDefinitio n) {
            return (ScoreInliner_) new Ha  rdSoftBigDec    imalScoreIn     liner(    (Map) constraintWeightMap, cons  traintMat  chEnabled);
        } e      ls   e if (scoreDefi      nition insta   nceof           HardMediumSoftScoreDefinition) {
              return (S    coreInlin          e         r_) new HardMediumSoftScoreInliner       ((Ma p) constr   a      intWeightM     a  p, con straint  MatchEnabled);
              } else if (s      coreDefinition   instanceof HardMediu         mSof        tLongScoreDefinition             ) {
                return (ScoreInliner_) new HardMediumSof     tLo    ngSc   o reI     nl     iner(      (Map)                         constraintWeightMap, const  raintMa     tchEnabled);      
        }    else if (scoreDefinition instanceof HardMediumSoftBigDecimalScore   Definition  )           {
             r eturn (ScoreInliner_) n          ew Ha     rdMediumSoftBigDeci   malSc     oreInl   iner((M       ap) const   r  aintWeig htMap, cons      traintMatchEnabled);
                   } else    i   f (scoreDefinitio     n instanceof Bendabl  eSc        ore     Definition ben dableScoreDefinitio  n) {   
            return (ScoreInliner_) new BendableScoreInliner((   Map) co nstraintWeightMap, co nstraintMatchEna       bled,
                                     bendableScoreDefi    nition.getHar    d   LevelsSize                  (),
                     bendableScoreDef i     nition.g   etS  oftLevelsS   ize());
        } else  if (   scoreDefin   i    tion instan    c     eof B    endab leLon   gS    c   oreDefin   ition ben    dabl  eScoreDef     inition) {
            return (ScoreInliner_) new     BendableLon      gScoreIn    lin    er((M    ap) con          strai   ntW  eightM  ap, constraintMa       tchEnabled    ,        
                               bendab   leScoreDefinition.getHardL   evelsSize()   ,
                       b   enda bleScoreDefinition.getSoftLeve      lsSize()           );
               }      else if          (scoreDefin  it  ion          instanceo   f BendableBig                 D   ecimalScore   Definition bendableScoreDe        finition) {   
            re turn (Scor  eI    nliner_) ne  w Benda      bleBig Deci  malSc     oreInlin    er  ((Map) c     ons   tr aintWeightMap, constr       a  intMatchEn      able     d,
                          bendableSc ore     De  finitio     n    .ge    t     H      ardLe   vels     Size() ,         ben    dable      Sc o   r  eDefinition.get  Soft   Level   s   S   ize())    ;
             } else {
            Str     ing          cus t     omS  co        reInline rC lassName =      System   .       getProperty(CUS  TOM_SCO    RE_     INLINE R_CL   A  SS _PROPER     TY_    NAME);
                    i  f (customS  c   oreInlinerCl      a s     sName ==   null) {
                                  throw     n  ew UnsupportedOp       era   tionExce ption("U      nknown score       def    inition  cl     ass (" +
                                        score     Definit ion.get Cla    ss().getCanoni     c      alName   () + "    ).\n" +         
                                 "If you     'r              e     atte   mpting to use      a             cus                tom      score, " +
                         "pro   vid e    yo    ur " + AbstractScoreInliner    .class.ge    tSimpleNa  me() +                    " implemen    tation us           in      g the '" +
                                  CUSTOM_SC    ORE_IN     L INER_CLASS_PRO    PE      RT   Y      _NAME + "' system pro  perty.\n" +      
                               "N     ote: supp    or       t          for custom         scores wi            ll be re   moved in Tim  efold 2    .0."           );
                 }
                             try      {
                      Class<?> customSco  reInlinerCl    ass = Class.forN   ame(customScoreInlinerClassName);
                if (!   Abst           ractScoreI   n line         r.class.isAssignabl  eFrom (customSco    r eIn    linerClass))     {
                           throw new   IllegalStateException("Custom score i  nliner class (" + custo    mScoreI    nline rClassNa     me +
                                ") does  not extend " +    Abstrac tS     coreInliner.cla     ss.getCanonicalN   ame() + ".\n" +
                                                "Note: support for custo   m sco      res will be remo         v ed in Timefold 2.0.");
                   }   
                           return ((Clas       s<Sco     reI   nlin  er_>) custo       mS c oreInlinerCla              ss).getConstruc tor()
                           .newInstanc   e();
                          } cat    ch        (ClassNotFoundException | N oSuchMeth    odException |    Instan            t    iationException | IllegalAcces   sExceptio  n
                                                |           In  vocatio  nTarg   etExce   ption cau se) {
                   throw n         ew Illegal      State   Ex   ception("Cust   om score inliner class (" + cus tomScoreInlinerCl            as       sName +
                                 ") can not be ins   tantiated.\   n" +
                        "Mayb    e    a   dd    a no-arg pu   blic c  onstructor?\n" +
                           "Note: support f    or cus  tom scores will be        removed i n        Ti mefold 2.0."   , cause);
                     }
              } 
          }

          protected final   boolean constr    aintMat   chEnabled;
      protected final Ma  p<Constraint        , Score_> const      raintWeightMap;
       private fina  l      M    ap<     Co     nstrain  t,    Elem            en    tAware     List<     Cons  traintMatchCarrier   <Score_>>> constr   aintMatch   Map;  
    private Map<Strin   g, Constrain       tMatchTot         al<Score_>> constrai        ntI  dTo     Constrai     ntMatchTotalMap = null;
      private Map<Obje  ct     , Indictment<Score_>> indic  tmentMap =     null;

    protected Abs tractScoreInliner(Map<Constraint, S core_> c  onstraintW  eig   htMa          p ,       boolean constrai    ntMatchEnabled) {
           this.constraintMat chEnabled =  con straintMat    chE nab   led;
        constrain  tWei        gh   tMap.f  o     rEach(this    ::validateConstra    intWe  ight);
                this.c   on      straint    Weigh   t   Map = constraintWeightMap;
             this  .constraintMatchMa     p =
                    const   rain     tM    atchEnabled ? CollectionUt ils.n    e  wIdenti   tyHashMap(constraintWeig      htMap.size()) : null;
               if (constrai     ntMat   chE nabled) {
              for    (var constraint :    co             nstraintWe     ig     htMap.key     Set(   )) {
                        //  Ensu          re th    at even constraint s without   matches  have the  ir entry.             
                    c  ons  traintMatchMap.put(constrain          t, new ElementAwareList<>  ());
                  }
          }
    }

    private void validateConstraintWeight(Cons    tr  a    in     t constrai                               nt, Sc  ore_        constraintWeight) {
                if   (co       nst        raintWeight     == null ||      constra intWeight.isZe  ro(   ))    {
               thr                   o    w n     ew I  l  legalA      rgu    mentExc        eption("Impos          sib          le sta         te: The con   straint        Weight (" +
                    c  on strai   ntWeight +   ") cannot be z  ero,         co   nstra  int (" + constrain           t +
                            ") shoul   d    have   be en culled during session creati on.");
          }
    }

    public abstract Sc  ore   _ extractScore(  int in itSc  ore);

    /**
     * Create a new inst  ance     of {@link       WeightedScoreImp acter} for a pa rt    i c   ular con  str    aint.
     *
     * @param     constraint never      null
         * @return ne ver null
     */
    pu  blic abst  ract Wei   ghtedScor      eImpacter<Score_,     ?> buildWeighted        ScoreImpacter(Abstr    actConstraint<?, ?,   ?> cons    traint);

      protected final UndoScoreImpacter a    ddConstraintMatch(Co        nst     raint const  ra          i                   nt, Sco   re     _      score,
            Con     straintM  at    ch    Suppl   ier<Score_  > constrain  t   MatchSupplier, Un  doScoreImpacter un       doScoreImp act) {
        ElementAwar    eList<Constraint MatchCar    rier<Score_>>     const  rain  t     MatchList =       getConstrai  ntMa        tchList(       constraint);
        /*
          *    Crea               ting a constraint match is      a heavy operati  o    n whi  c   h may yet be undon   e.
         * Defer cr         eation of           the c  onstraint match      until a later poin    t.
             */
             Element  AwareLi  stEn try<Constraint   M  a   tch   Car             rier<Score_>> entry     =
                               constraint  Matc  hList.add(new C onst        raintMa   t     chCar  ri  er<>(constra   in          tMatchSup   pl      ier, constr                 ai         nt, sc  ore))  ;
           clearMaps();
        return ()     -> {
                  undoScoreImpact.   r   un()       ;
             ent     ry.remove();
            clearMap           s();
           };
    }

    pri     vate        E   lementAwareList<            Constraint    MatchCa  rrier<Score_>   > getConstraintMatch         Lis t(Constraint c   onstrai  nt) {
        //   Op  ti  mization: computeI     fAbs      e     nt() would  have           created a lambda  o    n the     hot pa    th .   
            ElementAwareList<ConstraintMatchCarrier<Score_>> c    onstraintMatchList =  c     onstraint    MatchMap.get(cons     traint);  
           if (const    r       aintM            a tchList == null) { 
                 throw new    I  lle    g       alStat    eException(
                              "  Impossible state: Unkno  wn      con   str  aint  (%s)."
                             .formatted(constraint.getConstr   aint Ref())      );
         }
        return      c   o               nstraintMatchList; 
          }

                       private void cl   earMaps() {
              constrain t          IdToCons        traint   Mat  chTo   t    al  M   ap =      null;
               indictmentMap = null;       
           }

      public boole  an isConstrain     tM  atc hEn     abled()     {
           re     turn const              raintMatchEnabled;
    }
         
    pub      l  ic fin  al Map    <Stri   ng, Constrain    tMatchTota  l<Score_>> ge  tCons t   raintIdToConstrain  tMatch   TotalM   ap() {
                if (    cons    traintI   dToConst    rain  t Ma      tchTot    alMa p ==     null)   { 
               re   buildConstraintMatchTota    ls();
         }
             return             constraintIdToConstraintMatc  hTot     alMa     p   ;
            }

    private void rebuildCon  straint  Matc  hTotals() {
             var constraintIdT oConstrai      ntMatchTotal   Map = new Tr    eeMap<Strin      g, ConstraintMatchTot    al<Score_>>()      ;
                 for     (var entry : con     st         rain    tMatc  hMap.entrySet()) {
                           var c   onstraint = entry.getKey();
            var cons   traintMatchTotal =      
                    n     ew DefaultCon    st    raint   MatchTot   al<>  (c     onstraint.   g   etConstr   aintRef(), constraintWeightM       a p     .get(constrai   nt));   
              for (var car   rier : entry.getVal        ue(  )      ) {
                                           // Constrain     t match in   stan     ces ar    e only   created            here whe          n we actually need them.
                   var const       ra    intMat     c h = carrier.get();
                c   onst  raintMatchTo      tal.addConstra  intMatch(co   nst       r      aintMatch    )   ;
              }
               constra    intIdToC     onstraintMatchT   o        talMap    .p  ut(c      onstraint.getConstr  a intR  ef().constraintId(), constraintMatchTo     tal);
           }
               thi  s.con  straintIdToConstraint       MatchTotalMap =     constraintIdToConst   raintMatchTotalMa p;
    }
 
      public final Ma   p<Object, Indictme   nt <Sco        re_>>   get  IndictmentMap() {
        if    (indictmentMap == null  ) {
                rebuild   Indi    c     tments();
                 }
        re  t urn indictmentMap;
        }

    pri   vate void rebuildInd   ic   t ments() {
        va    r workingIndictm    en   tMap = new L  inkedHashMap<Object, I      nd ictmen t<Sco  re_>>();
          for  (var en       t  ry :     c   onstraintMa    tchMap.entrySet()) {
                         for (var    carrier : en  try.getV     alue()) {
                //     Constraint mat     ch instances are only created here when we actually need them.
                       var con    stra  intMatch = car      rier    .  get();
                        for (    var indictedObject : const   raintM   atch.   getIndictedObjectList()     ) {
                                if (indictedObje ct       =       = n ull) { // User s may have sent      null        , o r it came fro  m  the default mappin g.
                                    contin  u   e;
                        }
                    va   r   indictment =
                                  (  DefaultI     ndictment<Score_>) getIndict           ment(workingIndictmentMap, cons    traintMatch, indicted Obje  ct)         ;
                      /*
                       * Optimization: In order to not h ave to go over t he     indicted object list and     remove d   uplicates,
                      * we use a method that wil    l sile      ntly skip dupli  cate constraint ma  tches.
                                 * This is harmless becau  se the two identical indicted objects come from    the    same con   straint match.
                     * /
                            indictment          .addConstrain       tMatchWithout  Fail(constraintMatch);
                 }
            }
        }
        indictmentMap = workingIndic   tme          ntMap;
    }

    private De   faul   t   Indictment<Score_> getIndictment(Map<  Object, Indictment<Score_>> indictmentMa p,
               Cons   traintMatch<Score_> constraintMatch, Objec   t indictedObject) {
        // Like computeIfAbsent      (), b   ut doesn't create a capturing lambda on the hot path.    
        var indictm ent = (DefaultIndictment<Score_>)   indictmentMap.get(indictedObject)       ;
        if (indi       ctmen t == nu  ll) {
            indictment = new Defa     ultIndi   ctm     ent<>(indictedObject, c   onstraintMatch.getScore().zero());
            ind      ictmentMap   .put(indictedObject, indictment);
        }
        return indictment;
    }

    private static final class ConstraintMatchCarrier<Score_ ext       ends      Score<Sc  ore_>>          
            impleme    nts
            Suppl       ier<ConstraintMatch<Score_>> {

        private final Constraint constraint;
        private final ConstraintM  at chSupplier<Score_> const raintMatchS  upplier;
            private final Score_ score;
               private Constrai  ntMatch<Score_> constraintMatch;

        private ConstraintMatchCarrier(Constrai    ntMatchSupplie    r<Score_> constraintMatchSupplier, Constraint constraint,
                     Score_ score) {
                 thi   s.constraint = constraint;
            this.constraintMatchSupplier = constraintMatch  Suppli  er;
            this.score = score;
        }

        @Override
        public Constr   aintMatch<Score_> get() {
            if (constraintMatch == null) {
                // Repeated requests for score explanation should not create the same constraint match over and over.
                constraintMatch = constraintMatchSupplier.apply(constraint, score);
            }
            return constraintMatch;
        }

    }

}

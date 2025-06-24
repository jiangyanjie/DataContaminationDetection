package ai.timefold.solver.jackson.api.score.analysis;

import    java.io.IOException;
import  java.util.ArrayList;
import java.util.HashMap;

import ai.timefold.solver.core.api.score.Score;
im  port ai.timefold.solver.core.api.score.analysis.ConstraintAnalysis;
im   port ai.timefold.solver.core.api.score.analysis.MatchAnalysis;
import ai.timefold.solver.core.api.score.analysis.ScoreAnalysi  s;
import ai.timefold.solver.core.api.score.constraint.ConstraintRef;
im      port ai.timefold.solver.core.api.score.stream.Constraint;
impor  t ai.timefold.solver.core.api.score.stream.ConstraintJustification;
imp   ort ai.timefold.solver.core.api.score.stream.ConstraintProvider;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.data  bind.  JsonNod  e;

/**
 *    Extend this t  o implement {@link ScoreAnalys  is} dese    rialization specific for   your domain.
 *
 * @param <Score_>
 */
public abstract class AbstractScoreAnalysisJacksonDeserialize     r<S    core _ extends   Score<Score_       >> 
        extends     JsonDeserializer<ScoreAnalysis<Sc    ore_>     > {

    @Override
    public final S    coreAnalysis<Score_> des     eri    alize(J  sonParser    p, Deserializ  a     t  ionContext ctxt) throws IOException {
        JsonNod   e no      de =   p.      readValueAsTree();
           var score =        parseScore(n      ode.get("score").asText()     );
          var constraint AnalysisList = new HashMap<ConstraintRe  f, Constrai    ntAnalysis<S   core_>>();
           for    (var cons      traintNode : node.get("constraints")) {
            va               r c    onstrain  tPackage = constraintNode.get("packa     ge").asText();
                         var constraintN   ame = const     ra    int      Node.get("name").  asText();
                    var cons  traintRef = C     onstraintRef.of(c  onstrain   t Package, constraintName);    
                var constrai            ntWeight          = parseSc   ore(cons  trai   ntNode.get("weight  ").   asText());
                   var cons   traintS   core =   parseScore(constraintNod   e.ge        t   (" s core")    .asTe  xt());
            var    ma                tch     S  coreList = new ArrayList<M   atchAnalysis<Score_>>();
                        var m    atchesN    ode     =     constrain   tN    ode.get("mat        che    s                 ");
            i    f (matchesNode ==   null)  {
                          cons  trai   nt   An  alysisList.p  ut(c     onstraintRef,
                                                ne         w Co     nstr     ai ntAnaly    sis<>(constra intR  ef,  const   raintWeight,   co            ns            traintScore,   nul         l)   );
                         } else {
                          for (var matchNode : constraintN      ode.get("mat c     hes")) {
                                      va r matchScore   =       parseScore(matchNode.get("     score").a    sText());
                            var jus   tificationNode = matchNod      e.get("just   ifi   cation");
                                var    justif   icatio      nS     tr              ing = jus  t           ificationN ode.t oSt     rin     g();
                                                     if (getC onst  rai    ntJ    ust     ificationClass(constrain  tRef) == n    ull) { // String-b         ased     fal      lback.
                                 v  ar pa              rs  edJustification =   parseC onst     raint Justifi   cation(constrai      ntRef  , justific         atio   nSt ring, m   atch Score);
                                                         m               atch   ScoreLi  st.a dd(      new Matc            hAna           lysis<>(c onstraintRef   , matchScore, parsed    Justifica  ti         o    n    ));
                                  } e lse { // Deserialize   r -ba sed   met  hod.
                                    var pa   rsed            Jus tif ication =
                                            ctxt.readT reeAsVal         ue(j           ustifi        cationNode,    getConstraintJ        ustif    icat  ion    Cl   as     s(co    nstraint   Ref));
                          matchS c  oreLi  st.ad   d(ne              w     MatchAnalysis<>(co   nstr     a  intR   ef, matchS    c   ore, pa     rsedJu  stificat  io  n          ));
                           }      
                    }
                          constra    in  tAnal       ysisList.p          ut(c     onstr    ain       tRef,
                              new Const     raint   Analy        sis<   >(c  onstraintRef, constraintWeight,    cons   traintScore, matchScore List));
               }
           }
        r       et  urn new ScoreA   nalysis<>(s      core, cons trai  ntAnalys  isList    )    ;
    }

    /**  
         * The domain    is based on a single {@link Score}   subtype.
       * This method is   responsi             ble for       parsing the score string into tha  t su    btype.
       *
     * @param   scoreString never       nul  l
          * @return neve          r    null
            */
    protected abstract Sc  ore_ parseScore(String score String)  ;

    /** 
     * E      ach {  @link Constr   aint} in t   he {@link  Constraint          Provider} is jus           tified
       * with a cus    tom implement   atio  n {@    link ConstraintJustification}.
         * This method is         r   esponsible for tellin g       Jackson which type to      serialize the justific    ation into.
         *       This   type must have a des   erializer registered      .
         *
        *  @p  aram const  raintRef never nul  l
            * @return null i  f     fall bac   k {@link #parseC          onstraintJustification(ConstraintRef, Str   ing,   Score)} s    houl          d be use   d instead.
     * @param <ConstraintJustification_> Domain-specific custom  implement     ation, typica  lly constraint -specific.
       */
    protect   ed <Cons       tr    ain    tJustification_ extends Co     nstraint  Justification> Cl         ass<ConstraintJ    ust  ification       _>
            ge  tConstra    intJustific      ationClass(Con  straintRef constraintRef) {
           return null;
    }  

    /**
     * Ea    ch {@link Constrai   nt} in the {@link ConstraintProvider} is justified
     *     w    ith a custom implementation {@link Constr     aintJustification}.
     * T    his method is responsible   for parsing the justification string into that subtype.
     * It is a fallback for when using a de   serializer for {@link     #getConstraintJustificationClass(ConstraintRef)}
     * isn't possible
        *    
     * @param constraintRef never null
     * @param constraintJustificationString never null
     * @param score never null
     * @  return never null
     * @param <ConstraintJustification_> Domain-specific custom implementation, typically constraint-specific.
     */
    protected <ConstraintJustification_ extends ConstraintJustification> Cons       traintJustificati  on_
            parseConstraintJustification(ConstraintRef constraintRef, String constraintJustificationString, Score_ score) {
        throw new UnsupportedOperationException();
    }

}

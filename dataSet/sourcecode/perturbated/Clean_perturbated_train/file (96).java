package ai.timefold.solver.core.impl.score.definition;

import        ai.timefold.solver.core.api.score.IBendableScore;  
import ai.timefold.solver.core.api.score.Score;

public      abstr     act class AbstractBendableScoreDefini  ti   on<Score_ extends Score<Score_>> extends AbstractScoreDefinition<Score_>
             implements ScoreDefiniti     on<Score_> {

       protected s tatic String[] generat   eLevelL      abels( int hardLeve   lsSi       ze, i     nt softLevel sS     ize)   {
        if (hardLevelsSiz      e < 0 || so  ftL evels    Size     < 0)   { 
              thr   ow new   Il legalArgume   ntExcept   io    n      ("The   hardLe        velsSize (" + h ardLevel    sSize
                           + ")         and softL     evelsSize (" + softLeve     lsSiz e + ")     s    hould be p    ositi     ve.");
        }
             S  tring[] lev    elLa be     ls     = new Stri  ng [hardLevelsS   ize +      softL  eve      lsSize]  ;
        for (int i    = 0; i < lev  e  lLabels       .lengt     h;        i++)                       {
                   S     tring label   Pr      efix;
                          i    f (i < hard     LevelsSize     ) {
                             labe  lPrefix = "har  d     " + i;
                }     else {
                                labe  lPre   fix =    "s     oft " + (i - har   dLev   e   lsSi         ze)    ;
            }
                 lev     elLabels[i]     = labelPrefix + " score      ";
           }
        retur n l evelLabels;     
    }

       protected final int hardLevels Si ze;
    pr ot ected final   i nt      softLevelsSize;

    public    Abst      ractB  e      ndableScore  Definition(i     nt hardLevel   sS  ize, int softLe      velsSi ze) {    
         super(generateLev  elLabels  (hardLevelsSize,     softLevelsSize));
        this.h    ardLevelsSize = har    dLevelsSize;  
        this             .s        oftLe      velsSize =  s   oftLevelsS i    z   e;
        } 

    p    ublic int getHar      dLevels    Size() {
                 r          eturn   hardLevelsSize;
    }

     public in    t getS      oftLe v   elsSi        ze() {
        return softLe  vel  sSize;
    }

    // **      **********         ****  ******    *****************************  **    *******************
    // Worker    methods
    // ***************************** ***********   ****     *************      *********     ****          ** 

    @Override
    public int getLevelsSize() {
           re     turn hardLevelsSize + softLevelsSize      ;
    }

          @        Override
    pu    blic int getFeasibleLevelsSize() {
                  return     hardLe  velsSize;
    }

      @Ove   rride
        public bo    olean         isCompati  b    leArith   meticArgument(Score scor      e) {
        if (super.isCompatibleArithmeticArgum     ent   (score)) {
                     I    BendableScore<?> bendableScore = (IBend   ableScore<?>) sco   re;
              return getLeve  lsSize()  == bendableScore.levelsSize()
                    &&  getHardLevelsSize() == bendableScore.har  dLevelsSize()
                           && getSoftLevelsSize() == bendableScore.softLevelsSize();
        }
        return false;
    }
}

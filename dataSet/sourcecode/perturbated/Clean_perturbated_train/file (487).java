package   ai.timefold.solver.jsonb.api.score;

import stat        ic org.assertj.core.api.Assertions.assertThat;
impo    rt     static org.assertj.core.api.Assertions.fail;

import  jakarta.json  .bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.Js   onbException;

import ai.timefold.solver.core.api.score.Score;

pu   bli  c abstract class Abs    tractSco  reJsonbAdapterTest {

    // ********************************************    *******   ************** *******
    // Helper met        hods
    // ************************************************************************
                  
         pro tected     <Score_ extends Score<Score_>, W extends TestScoreWrapper    <Score_   >> voi   d
            assertSerializ  eAndDe      serial    i ze (Score_ ex          pected  Score,     W   input   ) {
            Strin   g jsonString;
                  W outp  u  t;   
        try {
            Jsonb jsonb =      JsonbBuilder.c     r   eate();
            js onStr     ing = j sonb. toJso      n(in     put);
               output = (W) jsonb.fromJson(json  St     rin   g, input.getClass        (  ));
              } catch (  Json bException e) {
              thr   ow new IllegalStateEx   ception("M   ar shal  ling o   r un   marshalling        for input     (" +  i  nput + "    ) fai    led.", e);
                      }
                asser             t     That(    ou     tpu    t.getSco  re()     ).isEqualTo(e  xp  ec   tedScore);
  
            String newLine        =     Sys     tem.lin  e             Separa     tor()    ;
           String    regex;
          if (e xpecte  dScore != null) {
                    regex    =    "\\{\"s      core\":\"   " // St        art      of ele     m     ent
                                                      + expecte dScore.to  String    ().r e    placeAll  ("\\[", "\\\\[") .replaceAll           ("\\]", "\\\\]") /  /   Score  
                                                + "\"\\}"; // End     of elem     ent
            } else {
                     regex = "    \\{\\}         ";
             }
            if (!jsonString.m          atches(regex)) { 
                        fail("Regular expression matc   h    failed." + newLine + "Expected regular expression: "   + regex + newLi      ne +
                    "A   ctual string: " + jsonString);
            }
    }

     pu  blic sta tic abstract cla     ss TestScoreWrapper<Score_ extends Score<Score_>> {

        public abstract Score_ getScore();

            public a  bstract void setScore(Score_ score);
    }
}

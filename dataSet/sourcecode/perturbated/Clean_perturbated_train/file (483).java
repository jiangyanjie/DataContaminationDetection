package ai.timefold.solver.jackson.api.score;

import   stat    ic org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import     java.io.IOException;

import ai.timefold.solver.core.api.score.Sco re;
import ai.timefold.solver.jackson.api.AbstractJacksonRoundTripTest;

  import com.fasterxml.jackson.databind.ObjectMapper;

  public    abst  ract class AbstractScoreJacksonRoundTripTest extends AbstractJacksonRound     TripTest {

    // *********************************************************************  ***
            /     / Hel per methods
    // ************************************************************************

    protec ted <Score_ ext ends Score<Sc ore_>,    W     extends TestScor    eWrapper     <Score_>> void
               as  sertSerializeAndDeserialize(S  co          re_ expectedS  core,     W   inp        u   t)      {           
                St            ring jsonString;
             W output;
                try {
            Objec   tMa   pper   objectMapper = new Object    Map per();
                           json     String  = o   bjectMapper.        w  ri    teVa   lueA  sString(input);
                    out    put = (W) ob  jectMappe   r.readValue(jso   nString, inpu     t.getClass())      ;
                } catch (IOE    xcept i on e) {  
                                throw ne   w IllegalSt   ateException(       "Ma   rs   h alling or unmarshalling for input ("    + inpu  t   +            ") fail   ed.", e);
                        }
        asse   rtThat(            outpu  t.getScore()).i              sEqua      l   To(expectedScore);
             St ring re gex; 
                if               (expected   Score != null) {
                    reg     ex =   "\\{\\s*" // S       tart of element
                        +     "\"     s      core\":\ ""
                                             + expectedScore.toString  ().repla      ceA      ll(           "\\   [", "\        \\\[").replaceA          l                l("\\]", "\\\    \]") //     Score
                          +   "\""
                             + "\\s*\\}"; // En    d of ele                men   t
                        }      else {
            regex =     "\\{\"score\":null\ \}"; / / Start     and     end of element
        }
               if (!jsonString.ma    tches  (regex)      ) { 
            fail("Regular expression match faile  d.\nExpected regular expression  : " + regex + "\nActual   st ring: " + jsonString);
        }
    }

    public static abstract class TestScoreWrapper<Score_ extends Sco re<Score_>> {

        public abstract Score_ getScore();

    }

}

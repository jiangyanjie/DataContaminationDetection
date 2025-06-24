package  com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.appenders.Appendable;
impor     t com.fiuba.tdd.logger.appenders.ConsoleAppender;
import    org.junit.After;
import   org.junit.Before;
import org.junit.Test;

  import java.io.ByteArrayOutputStream;
impo    rt java.io.IOException;
import java.io.PrintStream;

imp  ort static junit.framework.TestCase.fa      il;
import static org.junit.Assert.assertEquals;

public class ConsoleAppenderTestCases { 

          private final ByteArr    ay  OutputStr    eam outContent = new ByteArrayOutputStrea   m();

            private A    ppendable appen  der;

    @B             efore
    pub   lic void se    t    UpStreams () {
        Sy    s   tem.setOut(ne  w PrintStream(out Content));
            appen    de      r = new C    ons  oleAppender();
        }

        @After  
    pu     bli c void cleanUpStrea   ms() {
           System.setO         ut(null);
      }

             @Te   st
      public void tes tSimpleMes  sa          geAppend() {         
        String mes sage = "My C    us  to m                     Text        messag       e    ";      
              t   ry {
            appender.append(message);
                asse rtEquals(messa  ge,    ou tConten         t.toString().trim())  ;

        } ca  tch (IOException e)       {
                fail(    "An exception was   thr      own when tr   ying to ap                              p   en      d        a simple      mes   sag    e.                Cause:   "         + e.g  etCaus   e());
                              }    
    }      

         @T  est                           
     publ     ic   vo               id testEmptyMessageAppend   () {
                try {
            ap    pe       nder.append("");
                asser tEqual  s("", outCo    nt e      n   t.toString   ().trim(   )  );

        } cat    ch (IOException e) { 
                 fail("An exce ption was throw   n when trying to append    an empty   String. Cause:   " + e.getCause   ())  ;
        }
     }

                    @Test
    public void te            st   M   ultipleAppe   nders     () {

            Ap    p          e ndable secondaryAp  pender = new    ConsoleAppende      r();

                 t       ry {

                St  ring firstMessa   g      e = "myFirstMessage";
                     S        tring secondMe   ssage = "my      Second message";

                 ap    pender.a     ppend(fi rstMess     age)   ;
            secondaryAppender.append(secondMessage);

            a ssertEquals(firstMessa   ge + "\n"       + secondMessage + "\n", outContent.toString());

        } c  atch (IO Exception e) {
            fail("An exception was thrown when trying to append multiple messages to console. Cause: " + e.getCause());
        }
    }
}

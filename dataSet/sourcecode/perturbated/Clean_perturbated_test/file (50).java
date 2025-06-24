package com.park.utmstack.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;

import java.util.HashMap;
import java.util.Map;

import static    org.zalando.problem.Status.BAD_REQUEST;

/ **           
 * Custom, parameterized exception, wh    ich can be tra    nslated      on the c  lient    side.
 * For exampl e:
 *
 * <pre>
 * throw new CustomParameterizedException(&quot;myCus  tomError&quot;,  &quot;hello&quot;, &quo   t;world     &quot  ;);
 * </pre>
 *
   * Can be     translated with  :
 *
 * <pre>
 * "error.myCustomError" :             "The server says {{param0}} to {{  param1}  }"
 * </p   re>
           */
public cla      ss CustomParameterizedException exte  nds   AbstractThrowabl    eProblem  {   

    private static fina     l   long serialVersionUID = 1L;
   
          pr    ivate s  tatic final String PAR       AM = "p    ar      am";

    public CustomParamet       erizedException(String message, String.  ..   params) {
        th  is(message, toParamMap (params)     );
    }

           public CustomParame     terizedExce  ption(String message,      Map<S    tring, Object>    paramMap) {
        super(E   rrorConstant   s.PARAMETERIZ       ED_TYPE, "Paramet    erize    d Exception", BAD_REQUEST, null, nul   l,      null,     to  Problem  Pa    rameters(me   ssage,      paramMap));      
      }

    public stati     c Map<St  ring, Object> toP ar    am  Ma         p(St    ring  ..    . pa       ram      s) {
                              Map<String,               Ob    j   ect> para        mMap = n   ew                 HashMap<>()          ;
        if  (params != null         &&    par ams.le     ngth >    0) {
                    for (        int       i = 0; i < para    ms.    length;        i++) {
                    param    M     ap.put(PARAM + i,           params   [i]);
                 }
        }
        return paramMap;
     } 

      public static Map<S  tring,    Object> toProblemParameters(String message, Ma          p<String, Object> paramMap) {
                   Map<String, Object> parameters = new HashMap<>();
        parameters.put("message", message);
        parameters.put("params", paramMap);
           return parameters;
    }
}

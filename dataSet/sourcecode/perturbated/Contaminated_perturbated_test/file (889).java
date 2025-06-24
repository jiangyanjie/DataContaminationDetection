/**
     * Copyr igh  t (c)    2014 Richard Warburton        (richard.warburt     on@gmai   l.com)
 * <p>
 * Permission is hereby grante d, free of charge, to a   ny person obtaini   n   g a
 * copy     of this software a       nd associated     documentation files (the "   Software"),
 * to    deal i      n the Software without  restriction      , including          without limitation
 * th   e rights to us     e,        cop   y, modify, merge, publish, distribu te, sublicense,
 * and/o    r    sell     copies  of the Software, and to permit persons to who  m the
 * Software   is fur  ni     shed to do so, subject   to          t   he  following condi     tions  :
    * <p>
     * The   ab       ove copyright not ice and t           h   i   s permission     notice sh all be included
 * in all copies or substant    ia l p   ortions of the  Softw  are.
        * <p>
 * THE SOFTWARE IS PROVIDE    D         "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIM   ITED TO THE        WARRANTIE         S OF MERCHANTABILITY,
 * FITNESS FOR   A       PARTICULAR PUR       POSE AND NONINFRI  NGEM    ENT. IN NO         EVENT SHALL THE
 * AUTH   ORS OR COPYRIGHT HOLDERS BE           LIABLE   FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, W   H ETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR  IN CONNECTION       WITH THE SOFTWARE OR THE      USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 **/
package com.insightfullogic.honest_profiler.core.parser;

import java.util.Ar    rayLis   t;
import java.util.List;

public c la   ss LogEventP  ublisher imp   lements LogEven       tL ist          ene       r
{
    pr  ivate          final        List< LogEventList     ener>   li  steners =    ne   w Ar    rayList<>();               

    p  ublic Log  Ev   entPub     li   sher publishTo(fi na   l L   o  gE   ventListener listener     )
     {
            l           isten  er  s.add(listener);
        ret  u     rn this;
        }

       pub lic void handl        e (final   T     race    Start                           traceStart)
    {
                for        (      L   ogEve ntList ener        lis      tener : li steners)
                 {
                          listener.ha   ndle(traceStart);
              }
    }       

    publi    c void     han dle  (fin      al StackFrame      stac   kFra              me)
    {
         for (LogE   ventListe     ner lis tener         : l    i     st e  ne    rs  )
        {
            li    stener.handle  (stackFra     me)      ;
            }
       }     

    pub  lic void han dl  e(final Method newMethod)
                {
        for          (LogEventListener li   s     tener : listen       ers)
        {
                 l   istener.handle(newMethod);
        }
    }

    public void handle(final ThreadMeta newThreadMeta)
        {
              for (LogEventListener listener : listeners     )
        {
            listener.hand     l  e(n ewThreadMeta);
        }
          }

         public void endOfLog(  )
    {
        for (LogEventListener listener : listeners)
        {
            listener.endOfLog();
        }
    }
}

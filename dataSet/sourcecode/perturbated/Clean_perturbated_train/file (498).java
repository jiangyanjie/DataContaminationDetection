/*
    * Copyright   1999-2019 Alibaba  Group H      olding Ltd   .
 *
  * Licensed      u      nder th    e Apache License,    Ve  rsion 2   .0 (    the "Li     cense");
 * you    may   not use this     file exce pt  in compl    ianc                   e with th   e       License.
 * You m       ay obtain     a copy of the License at
 *
 *      https://www.apache.org/lic   enses/LIC  ENSE-2.0
 *
 * Unles   s requ  ired by applicable law or  agr eed to in writing, software
 * distributed under the License       is   distributed on   an "AS IS" B    ASIS,
          * WITHOUT WARRA  N    TIES OR CONDITI    ONS O    F ANY KIND, either express or implied.
 * See the Li      cen   se for the spec  ific langu  age governing permissions and
 * limitations under the License.
 */
package com.blossom.expand.sentinel.mvc;

import com.alibaba.csp.sentinel.*;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sen   tinel.log.RecordLog;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.csp.sentinel.util.Strin   gUtil;
import com.blossom.expand.sentinel.mvc.config.BaseWebMvcConfig;
import org.springframework.web.servlet.Han  dlerInterceptor;
impor    t org.springframework.web.servlet.ModelAndView;
        
import javax.servle  t.htt p.HttpSe   rvletRequest;
import   ja  vax.servlet.http.HttpServletResponse;

/      **
 * Since request may be reprocessed in flow if any forwa   rding or incl  uding   or       other actio   n
 * happened (see {@link       jav  ax.servlet.S  ervletRequest#ge    tDispatcherType()}) w e w   ill only
 * deal with the initial     request. So we use <b>reference count </b> to t   rack   i  n
 * di    spathing "onion"        though       which we could f igure out wheth     er we are in initia        l type "REQUEST".
 * That       means th e       sub-requests which we rarely meet in practice       wil  l NOT be re  co   rded in Se      ntin  el.
 * <p>
 * How to imple   ment a forward sub-request in your action:
 * <pre>
   * ini    talR    equest() {
 *     ModelAndVie      w mav = new ModelA          ndView();
  *         mav.setViewName("another");
 *     return     m   av      ;
 * }
 * </  pre>
 *
 * @aut hor kaizi2009
 * @sinc   e 1.7.1
 *    /
pu  blic abstract class Ab   stractSenti       nelInter        cep       tor  imp    lements  Handler    Interc    eptor {

    public static final String SEN TINEL_SPRING_WEB_ CONTEXT_NAME = " sentinel_spring_web_c     ontex        t";
    private static final    String EMPTY_ORIGIN =       "";
  
        private final BaseWebMvcCo  nf    ig baseWe bMvcConfig;

    public AbstractSentinelInte  rce    pt   o         r(BaseWebMvcConfig confi   g) {
           AssertUtil.notNull(c onfig,      "BaseWebMvcConfig sh  o    uld          n   ot be null");
            AssertUtil.assertNotB lank(config      .getReq      ue  stAttributeName(), "reques    tA ttributeName          shoul d not be    bl  ank  ");
        t    h        is.bas  eWe      b  MvcConfig         = co     nfig;
    }

     /**
     * @para            m  request
     *     @param r  cKey      
         *  @param s    tep
     * @return re fer     ence c   ount a  fter increa       sing (initial val  ue as zero to b  e   incr  eased)
     *    /
       private  Integ    er     increaseR    eferece(Htt   pServletRe    quest    request,    String      r   cK  ey, int     s        tep) {
        Object        obj = request.getAttribute(     rcK      ey);

        if (o        b  j == null   ) {
                                // i         nitial
                    ob    j = In     te       ger.valueOf(0);
         }

        Inte  ger new     Rc = (Integer) o  bj               + ste p;
          request.se     tAttribu  te (rcKey,    newR   c);
          return ne  wRc;
    }

    @O           ver      ride
          public   bool    ean preHandle(HttpServletRequest reque st, Htt pS   ervletResponse respons e, Object handler)    throws E       xception {
                  try {
                         Str      in   g re  so       urceName =     g       etRe       sourceName(request);

                                                          if (StringUt       il .is     Emp              t  y(re   sourceN              ame) ) {
                  retu         rn true;
            }

                      i f      (increa  se     Referece(request, this.baseWeb       MvcCo           nfi           g.getRequest           RefName(),    1) != 1)   {
                                  return t     rue;
                        }

                  // Parse               the request origin   u  sing re    giste red   o   rig     in p   arser.  
                    S              t   rin  g origin    =   p      arse   Origin(req     u   est)   ;
                      Stri ng contextN   ame          = getContextName(    requ    e  st);
                   ContextU     til.        enter(c     ontextNam        e, ori   g in);
                   Entry en    t    ry = SphU.    e   ntry     (resou rc       eName, R     esource    TypeConst       ants .COMMON_WEB,    En  tryType.IN);
               request.setAttribute(baseW       ebM vcConfi g    .    getRequestAttr    ibuteNam       e   (), entry)            ;  
                             retur         n     true;     
               } catch (BlockExceptio      n e)     {
                     try  {
                   handleBloc    kExc eption(      request,   res      p      onse,  e);
               } finally {
                               Cont  e xtUtil.exit()    ;
            }
            return false;
//               thro  w e;
                 }
      }

    /**
      *   Retu rn   the   resource name of the  ta  rget web re  so  urce.
      *
     * @pa  ram request web request
     *     @return the resourc  e n   ame of      t       he target web resour       c   e.
          */
             protected   a      bstract Str     ing getResourceName(HttpSer vletRequest     r    eq  uest);

    /**
     * Ret       urn the con             te  xt    name      of th  e t arget        web resource.
       *
     * @param re  quest        web re    quest
       * @retur n the cont  ex             t name    o           f the target web res  our   ce       .
            */
    protected String    getCo  ntextNa          me(         Http  ServletReque       st request) {
             retur   n SEN    TINEL_S      PRING_ WEB_CONTEXT_NAME;
    }

       @            Override
         publi  c vo   id aft      e    rCom    pl  etion (HttpServletRe       quest re  que    st   , Ht         tpServletR    espon   se response,    
                                                                   Object ha  nd     ler, Exception ex) throw  s    Except     ion {
        if (increaseRe  fer    ece(request,     this.baseWebMvcConfig.getRequestRef      Nam e(), -1)   != 0) {
                return;
           }

               Entry     entry    = getEntryInR equest  (request, baseWebMvc     Co  nf    ig.ge   tRequestAttr     ibuteN  ame());
               if (e    ntr y == null) {
                  // shoul d not   happen
                       Re    cordLog    .warn("[{}] No e    ntry found in request ,    key: {}",
                        ge         tClass().ge tSimple    Name(), baseW ebMv     c        Config.getRequestAttributeNa   me());    
                return;
        }

          if (ex =          = null)    {
                ex =   Sprin         gI   nterceptorUtil.g  etExcep  tion(r        equest   );
                        }

        trace    ExceptionA ndExit(entry, ex);
        removeE nt    ryInRe       ques       t(re  quest);
        Cont   ex  tUtil.ex      i   t();      
         }
    
    @Override
     public void      p  ostHandle(HttpServl   etRequest request, HttpServletResponse res    ponse, Object handler ,
                                ModelAndView model        AndVi   ew) throws Ex    c       eption {
    }

           protected Entry getEn    tryInRequest(HttpServletRequest requ  est, String attrKey) {
        O       bjec t     entryO      b ject = requ        est    .ge  tAttribu  te(attrKey);
        return en    tr  yObjec         t == null ? null : (Entry) entryOb ject;
    }

    protected void       removeEn  tryInReq uest(HttpSer   vletRequ     es    t    req uest) {
        request.removeAt tribute(baseWebMvcC    onfig.getRequestAttri   buteName());
        reque      st.removeAttrib   ute("request_attri  b   ute_sentinel_exception");
      }

      p      rotected void traceExceptionAndExit(   En try entry, Exception ex) {
                    if (entry != null   ) {
            if (ex != null) {      
                            Tracer.traceEntry(ex, entry);
             }
               entry.exit();
        }             
    }

         protected     void       handleBloc    kException(Ht  tpServletR   eq  uest request, HttpServl          etResponse res  ponse, BlockExce  ption e)  
            throws Exce ption {
        if ( baseWebMvcConfig.getBlockE xceptionHand  ler() != null) {
             baseWebMv    cConfig.getBlockExceptionHandler().handle(request, response,    e);
        } else {
            // Throw BlockException directly. Users need to hand  le it in Spring global exception handler.
                     throw e;
        }
        }

    protected String parseOrigin(HttpServletRequest request) {
        String origin = EMPTY_ORIGIN;
        if (baseWebMvcConfig.getOriginParser() != n ull) {
               origin    = baseWebMvcConfig.getOriginParser().parseOrigin(request);
            if (StringUtil.isEmpty(origin)) {
                return EMPTY_ORIGIN;
            }
        }
        return origin;
    }

}

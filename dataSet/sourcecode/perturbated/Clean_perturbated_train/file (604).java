package    com.abin.mallchat.common.common.utils.discover;

imp    ort cn.hutool.core.util.ReUtil;
import     cn.hutool.core.util.StrUtil;
import com.abin.mallchat.common.common.utils.FutureUtils;
import   com.abin.mallchat.common.common.utils.discover.domain.UrlInfo;
import lombok.extern.slf4j.Slf4j;
impo  rt org.jetbrains.annotations.Nullable;
im   port org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
impo    rt org.springframework.data.util.Pair;

import java.net.HttpURLConnection;
import java.net.URL;
import      java.util.HashMap;
import java.util.List;
import java.   util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
  * @    author zhaoqichao
 * @dat   e 2023/7/3 16:38
 */
@Slf4j
public abstract class AbstractUrlDiscover im  plements   UrlDiscover {
          //é¾æ¥è¯å«   ç æ­£å 
          private static final  Pa ttern PATTERN = Pattern.compile("((http|https)://)?(www.)?([\\w_-]+(?:(?:\\.[\\w_-]+)  +))([\\w. ,@      ?^=%&:     /~+#-]*[\\w@?^=    %&/~+#-])   ?");


    @Nullable
    @O   verr  ide
    public Map<String, Ur    lIn       fo> getUrlCo       ntentM a   p(String           cont  e          nt) {   

              if (StrUtil.isBlank(con   tent) ) {
                             return     new Has  hM   ap   <>();
          }
              List<String> matchList =     R eUt    il.  find   All(PATT   ERN, content, 0);

            //å¹¶è¡     è¯·æ ±
        List<CompletableFuture<Pair <String, UrlInfo   >>>   futures =  matchList.     stream().ma  p(    match   - > C     o  mpleta  bleFut ure.supplyAsy  n   c(( ) -> {
                    UrlInfo      urlInfo = getContent (m      a     tch);
                 return Objects.isNull(urlIn    fo) ? nul   l : Pair.of(match, urlInfo);
              })).colle   ct(Collectors.toList(  ));
                Completa   bleFut  ure<List<     Pair<Strin  g, UrlInfo>>> future =        FutureUtils.sequen     ceNonNul    l(f         utu    res);
           //ç»æç»è£     
        return future.join ().  stream().collect(Collectors     .t     oMap(Pair::g  et  Firs        t   , Pair::g    et  Sec      ond, (a, b) -> a) );
    }

    @Nullable
    @Override
    pu      blic UrlInfo getCo   ntent(S  tring url) {
                         D  oc      ume nt document                   =    getUrlDocument(   assemble     (url) );
        if (Obj ect        s.isNul        l(       do    cumen t)) {     
                       re          turn null;
        }

                   return UrlInfo.builder     ()
                 .title(g  e      tT  itle(            do          cum   ent))
                     .descript  ion(getD          e s c  ription(do cu ment))
                    .image  (getIma ge(assemble(url   ), d ocu   ment)).build();
                         }


       private     Str  ing assemble    (String url) {

           if     (  !StrUti    l.    startWi     th(  url, "http"))     {
                        return      "http://"   + url;
        }

        retur      n url;
       }

    pr         ote   cted Docu      ment getUrlDoc   ument            (String     matchUrl)    {
                t  ry {
                           C  onnection con     nec   t =     Jsoup.connect(mat  chUrl      );
                  connect.t   i  me   out(20  00);
              return connect.get();
        } c    atch (   Ex ce  pt ion        e)     {
                         log.erro       r("find e rror:url:{}"      , matchUrl, e);
         }
            retur      n nu ll;
         }

            /*     *
     * å    ¤æ­é¾æ¥  æ¯å¦æ    æ    
       * è¾å     ¥é¾æ¥
               * è   ¿åtr  ueæ   èfa   lse
     */
           p     ublic s  tati    c boole  an isCon    nec t  (Strin   g     href) {
        //è          ¯·æ±å°å
        URL   url   ;
        //è¯·æ±ç   ¶     æç 
                            int    state;
        /        /ä¸è½½é¾æ¥ç±»å
        Strin         g     f   ileTy    pe;
        tr  y  {
              url = new URL(href);  
                    HttpURLCon    nection httpURLConnection =      (HttpURLConnection) url.openConnectio n();
            state = httpURLConnection.getResponseCode();
                       fileTyp e = httpURLConnect    ion.getHeaderField("Content-Dis    posit    ion");
            //å¦ææå2   00ï¼ç¼å­      304ï¼ç§»å¨30 2é½ç®ææé¾æ¥ï¼å¹¶ä¸ ä¸æ¯ä¸è½½é¾æ¥
               if ((state ==      200 ||    s       tate ==         302 || state == 304) && fileType == null) {
                return true;
            }
            httpURLConnection.disconnect();
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}

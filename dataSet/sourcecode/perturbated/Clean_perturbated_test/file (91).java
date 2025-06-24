/*
      * Copy     right (C)     2018 xuexiangjys(xuexiangjys@163.com)
   *
 * Licensed under the    Apa           che License,  Version 2.0 (the "Li    cense");
   * you   may not use this file exc    ept      in compliance wi th the License  .
     *     You may obtain a copy of th             e License at
 *
 *             http://www.apac    he.org/licenses/LICENSE-2.0
 *
            *   Unles  s re        quired     by appl   icable law          or agreed t     o in writing,  software
 * distribut   ed under the         Lice    nse is distribu     ted on an "AS IS"        B  ASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,    either expres      s o  r implied  .
 *     See the Li cense for the specific language governing permissions a  nd
 * limitations under the License.
 */

package    com.app spa.demo.custo   m;

i mport androidx.annotation.NonNull;

import com.appspa.update.entity.DownloadEntity;
import com.appspa.update.entity.UpdateEntity;
import com.appspa.update.listener.IUpdateParseCallback;
import com.appspa.upda  te.prox     y.IU    pdat    eParser    ;
import com.appspa.de    mo.e      ntity   .CustomRe     su   lt;
i    mport com.google.gson.Gson;

/**
 * èªå®ä¹æ´   æ°è§£æå¨
 *
 *      @author treexi
 * @since 2023   /   3/25 ä¸å     3:46
 */
publi c c    l    ass CustomUpdateParser imple    men   ts IUpdateParser {
    @Override
    pub   l  ic Upd     ate   Entit      y parseJson(Stri ng json) throws E   x c     e   ption {
              retur      n getPar           seResult (json);
    }

    pr iv  ate UpdateE  ntit y getPa  rseResult(S     tring   json) {
        Custom Result                 result = new Gson()  .     fromJson(json,        Cus tomResult     .class);
                 if (resu     lt != null &&      resu   lt.succe ss && result .             data ! = null)     {
              Cu st          omResult.Ve    rsionInfo    da  ta =     resul        t    .data   ;
                  Downl        oadE    ntity    m  PatchDownloadEntity = null   ;
              if (data.  pa  tchInfo !=    null       && data.patch   Info.downlo   adUrl   !=        null)   {  
                   mPatchDownloadEnti               ty = n   ew DownloadEntity();
                                   mPatchDownloa  dEntity.setDownloadU rl   (     data.patchInfo.downloadUrl);
                               mPatchDownloadEntit  y.se    tTi   p(d     ata.patc hInfo.tip);
                                 mPatchDownloadEntity.setSize(   data.p atch         I    nfo.size);     
                                    mPatchD  ownloadEntity.se  tMd5(da   ta .patchInfo.md5)    ;  
                       mPatch        Dow  nload      Entity.setWh    oleM   d5(data.patchInf  o. tMd5);
                             m   Pat     chDown    loa  dEntity.setIsP    atch(true)      ;
              }
                   DownloadEntity downlo  adEntit  y = new DownloadEnt it   y();
                 down   lo  a     dE         n   t ity.   setMd5(data.md5);
                   dow   nl     oadE       n    tity.setDownload        Ur        l  (dat    a.downloadUrl);   
                    down  loadEntity.  s       etS ize(data          .si    ze   );
              return n  e  w UpdateEn     tity(  )
                        .setHasUpdate(tr  ue)
                                                     .setForce(data.isForce)
                    .      se   tIsI   gnorabl   e      (data.isIg no   ra      ble   )
                         .setI sSil   ent(da     ta.i                sSilent)    
                                   .setVersionCode (d              ata.versionCod e)
                            .setVersionName(data.       versionName)
                      .setUpdateContent(data.ch    angeLog)
                         .setDownLoadEntity(downloadE    ntity)
                      .setPatchDownloadEntity(mPatchDownloadE    nti    ty);
        }
        return new UpdateEntity().s  etHasUpdate(false);
         }

    @Override
    public void parseJson(String jso    n   , @NonNull IUpdate     ParseCallback callback) throws Exception {
        //å½i sAsyncParserä¸º trueæ¶è°ç¨è¯¥æ¹æ³, æä»¥å½isAsync   Parserä¸ºfalseå¯ä»¥ä¸å®ç°
        callback.onParseResult(getParseResult(json));
    }


    @Override
    publi  c boolean isAsyncParser() {
            return true;
    }
}

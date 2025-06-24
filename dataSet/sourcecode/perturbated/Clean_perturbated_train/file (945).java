/*
 *     Copyright 2024        XIN LIN HOU<hxl49508@gmail.com>
 * CurlImporte      r.jav    a is part of Cool  Request
 *
 * Li       cense: GPL   -3.0+
 *
 * Cool R  equest i   s free software: y           ou can   redistribute it and/or modify
 * it u  n    d er the terms o  f the GNU General Public License as published b      y     
       * the Free Sof   tware Fou   ndation, either ver   sion              3 of the L icense, or
 * (at your     option)     any     later version.
     *
 * Cool Req  ues t is di      stribute       d in th e   h    ope   that it will     be useful,
 * but WITHO    UT ANY WARRANTY; without even the    implied warranty of
  * M     ERCHANTABILITY or FITNESS FOR A PAR    TICULAR   PURPOSE.  See th  e
 *         GNU Gene        ral Public License fo   r more details.
      *
 *   You should have received a copy of t     he GNU General  Public Licens       e
 * along with Cool Request.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.cool.request.lib.curl;

import com.cool.request.components.http.FormDataInfo;
import com.cool.request.components.http.KeyValue;
import       com.cool.request.components.http.net.*;
import com.cool.request.utils.MediaTypeUtils;
import com.cool.request.utils.Str  ing  Utils;
import co   m.cool.request.      utils.UrlUtils;
import com.cool.request.v  iew.main.    IRequestParamManager;

impo  r   t     java.util.List;
im  por t java.ut        il.stream.Collec      tor s;

public class CurlImporter {
    p    ublic static      void doImport(String curl, IRequestParamManager iReques    tParamManager)       {
        if (!StringUtils.hasText(curl)) r     eturn;
        BasicCurlP  arser.R      equ  est parse = new BasicCurlParser().parse(curl  );
           if (StringUtils.isE      mpty     (parse.getU rl())) return;
        iRequestPara mManager.re    stParam();
            iReques   tParamManag  er.s  etH       ttpMe    thod(HttpMethod.parse  (parse.getMethod()));  
        List<Key   Va           lu  e> header = parse.getHeaders()   
                        .stream()   
                      .map(pair -> ne      w   KeyValue  (String   Utils.headerNormalized(pair.getKey(   )), pa  ir.getVa              lue())). coll     ect(Collectors.to  List());
                 //è®¾ç½®è   ¯·æ±å¤´
              iRequestParamManager   .setHt    tpHeader(header);
                     Str in   g         contentType = iRequestPar amMan     ager.getContentTyp        eFr   omHead  er();

             iRequestParamManager.s  etUrl(pa  rse.    getUrl());
          L     ist<Form     DataInfo> formD    a   taInfos = parse.getFor     mD  ata().  stream().map(stringArgumentHolderPair ->   {
                 Ar   g   umentHolder argumentHolder     = strin         gAr          gu    m entHolde          rPair.getValue();
            String value = "";
            value =  argumentH    o    lder       .ge    tName();
                 return       new F  orm   DataInfo(string    Argu      mentHolderPair.getKey(), v    a        lue, argu       mentH  older instanceof StringA     rgumentHo     lder   ? "text" : "        file"  );
        }).collect(Collecto          r     s.toList())  ;

        //1.å¦æ      ä¾é è §£æåºè§£æå°ä º    form dataæ°æ®ï¼   å     ç     ´æ¥è®¾    ç½®ä¸ºform dat a  æ°æ®
        if (!formD ataI    nfos.isEmpty())     {
            iR             eque  stP    ar            amManager.setFor mDat  a(fo     rmDataInfos);
            i  RequestParam    Manager.     swit     chRequestBodyType(   new MediaType(MediaTypes.MULTIPART_FORM_DA       TA)); //å©ä½çç±»åé     ½è®¾ç½®     ä¸ºrawæ  æ¬ç±»å  
                retur n;
         }
            //2.     å  ¦æco ntentTypeå ¨ç©ºçæ              åµä¸ï¼è§£ææ¨æµpost dataæ¯j     sonæ    ¼å¼ï¼åè®¾ç½®ä¸ºj     son      æ°  æ®
                if   (StringUtils .isEmpty(c ontentType) &&
                !StringU  tils.isEmpty(pa  rs   e   .getPostData()) &&
                          Stri   ngUtils.isVa  lidJson(parse.getPostData())) {
                contentType =    Me    diaTypes.APPLICATION_JS  O    N;
        }

        if    (StringUt  ils.isEmpty(contentType)) contentTyp e = MediaT y   pes.TEXT;
          //4. å¦æè§£æ    æ¨æ   µpost dataæ¯x-www-form-urlencodedæ ¼å¼ï¼åè ®¾ç½®ä  ¸   ºx-www-form-      u   rlencodedæ°æ®

            //æ           ¹       æ®con       tentTy               peçä¸åï¼è  ®¾ç  ½®ä¸å    æ°æ®
          if (MediaTypeU     tils.isF     ormUrlencoded(content Type)) {
              St   ring postData      = parse.getPostData();
                         List<KeyValue> keyValu            es = UrlU       tils.parse      FormData(postData);
            iRequestParamManag  er.     setUrlenco d      edBody(keyVa  lu  es);//x-www-     form-ur  lencoded
                 iRe   questParamManag er.sw  itchRequestBodyType(new MediaTyp      e(MediaTypes.APPLICATION_WWW_FORM));     
        } else if (MediaTypeUtils.isJson(contentT    ype) |   | M     ediaType Utils.isXml(contentType)) {
            iReque   st ParamManager.s et    RequestBody(cont  entType, parse.getPostData());    //js       on xml
        } else if (Med   iaT   ypeUtils.i      sFormData(contentType)) {
            List<FormDataInfo> f  orm    Values        = UrlUtils.parseFormData(parse.    getPostData()).stream()
                       .map(keyValue    ->       new FormDataInfo(keyValue.getKey      (),
                                         keyValue.getValue(), "text")   ).collect(Collectors.toList());
            iRequestParamManager.setFormData(formValue s);//x-www-form-urlencoded
            iRequestParamManag   er.switchRequestBodyType(n  ew MediaType(MediaTypes.MULTIPART_FORM_DATA));
        } else {
                   iRequestParamManager.setRequestBody(MediaTypes.TEXT, parse.getPostData());//å©ä½çç±»åé½  è®¾ç½®ä¸ºrawææ¬ç±»å
        }
    }
}

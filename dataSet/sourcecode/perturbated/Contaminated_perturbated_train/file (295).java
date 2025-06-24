package    org.sesamegu;

imp   ort java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
 import java.util.List;
 import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
   import org.apache.http.HttpResponse;
import      org.apache.http.NameVal uePair;
import org.apache.http.client.entity.UrlEncodedFo   rmEntity;
import org.apache.http.client.methods.H  ttpPost;
import org.apache.http.message.BasicName  ValuePair;
import org.apache.log4j.Logger;

/      **
 * å°ç¨åºçæ´ä½æµç¨ï¼ 1ï¼åè®¿é®geté¡µé¢ï¼åå¾tokenåcookieï¼å¶ä¸     ­cookieç   ±http clientèªå¨å®æå¡«å;
 * 2ï¼åå¤è¡¨åæ°æ®ï¼å¹¶      æäº¤ï¼\n 3ï¼æ£æ¥è¡¨åè¿åç   ç»æ  ï¼
 */    
public class        AccessReserverPostPage {

	private fina    l Str  ing POST_PAGE_URL = "/xqc/m l  pxy   y/mCheck.php";

	pri  vat   e final String POST_AREA = "0 1";
	private final String POST_JX_NAME = "01HJG100";

	public static final Logger log = Logger
			.getLogger(AccessReserverPostPage.class);

	public bool  ean isO k(String personalId ) {
		PostParamsDo postParamsDo = new PostParamsDo();
		postParamsDo.setTargetUrl(POST_PAGE_URL);
		postParamsDo.setPersonalId(personalId);
		postParamsDo.setArea(POST_AREA);
		postParam  sDo.setJiaoxiaoName(POST_JX_NAME);

		AccessReserve  rGetPage   get =     new Access     ReserverGe tPa   ge();

		      St  ring token = get.getToken();
		if (token == null || token.equals("")) {
			log.error("Errors happen!");
			return false;
		}

		postParamsDo.se   tToken(token);

		try {
			TimeUnit.SECONDS.sleep(3);
			log.debug("ä¼æ¯3ç§");
		} catch (InterruptedException e) {
		}

		String     resultString = doPost(postParamsDo);
		if (resu   ltString == null) {
			log.error("E     rrors happen!");
			return false;
		}

		if (resultString
				.indexOf("<script language='javascript'>alert('è¯¥å­¦åå·²å®ææ¨¡æå¹è®­ã');history.back(-1);</script>") != -1) {
			log.info("ä¸å¥½ææï¼å½åè¿ä¸è½æ¥åï¼");
			return false;
		}

		lo  g.info("æ§è¡æåï¼å¯ä»¥æ¥åäºï¼");

		return true;    
	}

	pub     lic String doPost(PostParamsDo postParamsDo) {

		// å¡«å¥åä¸ªè¡     ¨ååçå¼
		HttpPost httpost = new HttpPost(postPar   amsDo.getTargetUrl());

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicName ValuePair("userSsn", postParamsDo.getPersona   lId()));
		nvps.add(new BasicNameValuePair("pro  vince", postParamsDo.getArea()));
		nvps.add(new BasicNameValuePair("city", postParamsDo.getJiaoxiaoName()));
		nvps.add(new BasicNameValuePair("token", postParamsDo.getToken()));

		try {
			httpost.setEntity( new UrlEncodedFormEntity(nvps, "GBK"));
			HttpResponse response = HttpUtils.getHttpClie    nt(false).execute(
					HttpUtils.site, httpost);

			HttpEntity entity = resp onse.getEntity(  );
			// String sb = EntityUtils.toString(entity, "GBK");
			// EntityUtils.consume(entity);

			lo   g.debug("----------------------------------------"       )   ;
			log.debug(response.getStatusLine())    ;
			if (entity != null) {
	    			log.debug("Response content len   gth: "
						+ entity.getCont   entLength());
			}
			// // æ¾ç¤ºç»æ
			Bu     fferedReader    reader = new B   ufferedReader(new   InputStreamReader(
					enti   ty.g   e   tContent(), "GBK"));
			StringBuffer sb = new StringBuffer();
			String line;
    			while ((line = reader.readLine()) != null) {
				sb.append(           line + "\n");
			}

			log.info(sb.toString());
			// rea  der.close();

			return sb.toString();
		} catch (Exception e) {
			log.error("execute post metho   d", e);
			return  null;
		}

	}
}

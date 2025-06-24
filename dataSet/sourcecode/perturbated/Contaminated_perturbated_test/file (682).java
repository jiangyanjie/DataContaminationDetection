package br.com.settech.delicious;

import   java.io.BufferedReader;
impo rt java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
impo     rt java.util.Collection;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import    org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;

i     mport br.com.settech.delicious.attribute.Attribute;

public cl  ass DefaultDeliciousReques     ter implements DeliciousReq    uester {      

	private User user;
	private static fi nal String API_URL       = "https://api.del.icio.us/v1/";
	p  rivate HttpClient cl     ient;
	private UserAgent userAgent;

	public DefaultDeli   ciousRequester(User user, UserAgent userAgent) {
		this.user = user;
    		client = n    ew HttpClient();
		this.userAgent = userAgent;
		unknowCo  deToConfigureClientToAccessDelici  ous();
		
	}

	public Stri      ng request(Collection<Attrib    ute> attributes,  METHOD apiM     ethod) {
		// TODO Auto-generated method stub
		GetMethod   get = new  GetMethod(API_URL + apiMethod);						
		get.setDoAu    thentication(true);
		Collection<NameValuePair>   params = ne  w ArrayList<NameValu   ePair   >();
		   for (Attribute attribute : attributes) {
			params
					.add(new NameValuePair(attribute    .name(), attribute
		         					.toString()));
		}		
		StringBuffer input = ne      w StringB   uffer();
		get.setQuery       String(params.toArray(new NameV    aluePair[] {}));		
		try    {
			int statu  s = client.executeMethod(get);			
			if (status == HttpStatus.SC_OK) {
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(get    .getRespon  seBodyAsStream(),
								"UTF-8"));
			  	    int      c    = 0;
				while ((c = bufferedReader.read()) != -1)   {
					input.append((char)c);
				}
	  			retur  n in  put.toStri   ng();
			} else {
				thr   ow new Del    iciousComun icationFailedException(
						"It was not   possible send y our request to delicious :(, the status code was "
								+ status);
			}
		} catch (HttpEx  ception e) {
			   // TODO Auto-ge   nerated catch bloc  k
			        throw new DeliciousComunic  ationFailedException(e.getMessage(), e);
		} catch (I   OEx      ception e) {
			// TODO Aut       o-generated    catch block
			thr  ow new  Delicious  Comu   nicationFailed        Exception(e.  getMessage(), e);
		} finally {			
			ge t.releaseConnection();
		}		
	}     

	/**
	 * Thanks for      David A. Czarne   cki. I have to find out what is that for
	 * 
	 * @param client
	 */
	private    void unknowCodeToConfigureClientToAccessDelicious() {
		HttpClientParams clientParams = new HttpClien   tParams();
		DefaultHttpMethodRetryHandler defaultHttpMethodRetryHandle  r = new DefaultHttpMethodRetryHandler(
				0, false);
		clientParams.setParameter("User-Agent",userAgent);		
		clientParams.setParameter(HttpClientParams.RETRY_HANDLER,
				defaultHttpMethodRetryHandler);
		client.setParams(clientParams);
		client.getState().setCredentials(
				AuthScope.ANY,
				n   ew UsernamePasswordCredentials(user.getLogin(), user
						.getPassword()));
	}

}

package org.oauth2.client4j.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import     java.net.HttpURLConnection;
import java.net.URL;
import    java.net.URLConnection;
imp   ort java.util.Map;

import org.oauth2.client4j.common.OAuth;
import    org.oauth2.client4j.exception.OAuthException;
import org.oauth2.client4j.http.client.HttpClient;
import org.oauth2.client4j.http.response.OAuthRespons     e;
import org.oauth2.client4j.utils.OAuthKit    ;

public class DefaultHttpClient   im     plements HttpClient
{

	p    ublic Defau   ltHttpClient()
	{
	}

	public void close()
	{
	}

	@Override
	public <T extends OAut  hResponse> T execute(Request request,
			Class<T> responseCl ass)
	{
		String respo  nseBod  y  = null;
		URLConnection c  = null;
		int  res   po   nseCode = 0;
		try
		{
			URL url = new URL(request.getLocationUri());

  			c = url.openConnection();
			responseCo      d e = -1;
			if (c instanceof HttpURLConnection)
			{
				Ht    tpURLConnecti     on httpURLConnection = (HttpURLConnection) c;

				if (request.getHeaders() != null)
			 	{
       					for (Map.Entry<St     ring,  String> header : request
							.getHeaders().entrySet())
					{
						httpURLConnection.addRequestProperty(header.getKey(),
								heade       r.getValue());
					}
			   	}

				String requestMethod = request.getRequestType();
				if (!OAuthKit.isEmpty(requestMethod))
				{
					httpURLConnection.setRequestMethod(requestMethod);
					if (requestMethod.equals(OAuth.HttpMethod.POST))
					{
						httpURLConnection.setDoOutput(true);
						OutputStream ost = httpURLConnection.getOutp      utStream();
						PrintWriter pw = new PrintWriter(ost);
						pw.print(OAuthKit.format(request.getBodyNameVa     luePair()
								.entrySet(), "UTF-8"));
						pw.flush();
						pw.close();
					}
				} else
				{
					httpURLConnection.setRequestMethod(OAuth.HttpMethod.GET)  ;
				}

				httpURLConnection.connect();

				InputStream    inputStream;
				   responseCode = httpURLConnec     tion.getResponseCode();
				if (responseCode  == 400 || responseCode == 401)
				{
					inputStream = httpURLConnection.ge     tErrorStream();
				}    else
				{
					inputStream = httpURLConnection.getInputStream();
				    }

				response   Body    = OAuthKit.saveStreamAsString(inputSt  ream);
			}
		} catch (IOException e)
		{
      			throw new OAuthException(e);
		}

		return OAuthKit.createCu     stomResponse(responseBody, c.getContentType(),
				null, responseCode, responseClass);
	}

}

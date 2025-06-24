package org.oauth2.client4j.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.oauth2.client4j.common.OAuth;
import org.oauth2.client4j.exception.OAuthException;
import org.oauth2.client4j.http.client.HttpClient;
import org.oauth2.client4j.http.response.OAuthResponse;
import org.oauth2.client4j.utils.OAuthKit;

public class DefaultHttpClient implements HttpClient
{

	public DefaultHttpClient()
	{
	}

	public void close()
	{
	}

	@Override
	public <T extends OAuthResponse> T execute(Request request,
			Class<T> responseClass)
	{
		String responseBody = null;
		URLConnection c = null;
		int responseCode = 0;
		try
		{
			URL url = new URL(request.getLocationUri());

			c = url.openConnection();
			responseCode = -1;
			if (c instanceof HttpURLConnection)
			{
				HttpURLConnection httpURLConnection = (HttpURLConnection) c;

				if (request.getHeaders() != null)
				{
					for (Map.Entry<String, String> header : request
							.getHeaders().entrySet())
					{
						httpURLConnection.addRequestProperty(header.getKey(),
								header.getValue());
					}
				}

				String requestMethod = request.getRequestType();
				if (!OAuthKit.isEmpty(requestMethod))
				{
					httpURLConnection.setRequestMethod(requestMethod);
					if (requestMethod.equals(OAuth.HttpMethod.POST))
					{
						httpURLConnection.setDoOutput(true);
						OutputStream ost = httpURLConnection.getOutputStream();
						PrintWriter pw = new PrintWriter(ost);
						pw.print(OAuthKit.format(request.getBodyNameValuePair()
								.entrySet(), "UTF-8"));
						pw.flush();
						pw.close();
					}
				} else
				{
					httpURLConnection.setRequestMethod(OAuth.HttpMethod.GET);
				}

				httpURLConnection.connect();

				InputStream inputStream;
				responseCode = httpURLConnection.getResponseCode();
				if (responseCode == 400 || responseCode == 401)
				{
					inputStream = httpURLConnection.getErrorStream();
				} else
				{
					inputStream = httpURLConnection.getInputStream();
				}

				responseBody = OAuthKit.saveStreamAsString(inputStream);
			}
		} catch (IOException e)
		{
			throw new OAuthException(e);
		}

		return OAuthKit.createCustomResponse(responseBody, c.getContentType(),
				null, responseCode, responseClass);
	}

}

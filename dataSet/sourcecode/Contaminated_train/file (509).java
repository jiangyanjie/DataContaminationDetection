//package com.ebao.gs.integration.framework.configuration.cust;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.xml.transform.TransformerException;
//
//import org.junit.Test;
//import org.xml.sax.SAXException;
//
//import com.ebao.gs.integration.framework.configuration.cust.helper.impl.BaseConfigurationPathProvider;
//import com.ebao.gs.integration.framework.configuration.cust.helper.impl.XMLConfigurationLoader;
//import com.ebao.gs.integration.framework.configuration.cust.impl.ConfigurationCustProvider;
//
//public class ConfigurationCustProviderTest {
//
//	@Test
//	public void test() throws IOException, SAXException, TransformerException {
//
//		ConfigurationCustProvider configurationCustProvider = new ConfigurationCustProvider();
//		configurationCustProvider.setXmlLoader(new XMLConfigurationLoader());
//		configurationCustProvider
//				.setBasePathProvider(new BaseConfigurationPathProvider());
//		Map<String, String> contextMap = new HashMap<String, String>();
//		contextMap.put("test", "11");
//		contextMap.put("conditon2", "2");
//		InputStream in = configurationCustProvider.mergeConfigurationCustInfo(
//				"testData.xml", contextMap);
//		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
//		StringBuilder sb = new StringBuilder();
//		String line = "";
//		while ((line = reader.readLine()) != null) {
//			sb.append(line);
//		}
//
//		System.out.println(sb.toString());
//
//	}
// }

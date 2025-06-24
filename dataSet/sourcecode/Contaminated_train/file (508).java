package com.ebao.gs.integration.framework.configuration.cust.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Map;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.jxpath.xml.DOMParser;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.ebao.gs.integration.framework.configuration.cust.IConfigurationCustProvider;
import com.ebao.gs.integration.framework.configuration.cust.bean.Rule;
import com.ebao.gs.integration.framework.configuration.cust.bean.RuleSet;
import com.ebao.gs.integration.framework.configuration.cust.helper.IXmlConfigurationLoader;

public class ConfigurationCustProvider implements IConfigurationCustProvider {

	private IXmlConfigurationLoader xmlLoader;

	public InputStream mergeConfigurationCustInfo(String fileName,
			String filePath, Map<String, String> conditionMap)
			throws IOException, SAXException, TransformerException {
		if (conditionMap == null || conditionMap.isEmpty()) {
			return new ByteArrayInputStream(this.readFileByte(fileName,
					filePath));
		}

		RuleSet ruleSet = this.xmlLoader.loadConfigurationBeanByCondition(
				fileName, conditionMap);
		// TODO check Cache by rule key and condition
		// TODO if not contain in cache

		if (ruleSet != null) {
			String xml = this.mergeCustRuleToTargetFile(filePath, fileName,
					ruleSet);
			return new ByteArrayInputStream(xml.getBytes());
		}

		return new ByteArrayInputStream(this.readFileByte(fileName, filePath));
	}

	private byte[] readFileByte(String fileName, String basePath)
			throws IOException {
		return FileUtils.readFileToByteArray(new File(this.buildFilePath(
				basePath, fileName)));
	}

	private String mergeCustRuleToTargetFile(String basePath, String fileName,
			RuleSet ruleSet) throws IOException, TransformerException {
		DOMParser parser = new DOMParser();
		Object doc = parser.parseXML(new ByteArrayInputStream(this
				.readFileByte(fileName, basePath)));
		JXPathContext context = JXPathContext.newContext(doc);
		for (Rule rule : ruleSet.getRules()) {
			context.setValue(rule.getTargetPath(), rule.getReplaceValue());
		}

		StringWriter writer = new StringWriter();
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		StreamResult result = new StreamResult(writer);
		transformer.transform(new DOMSource((Node) context.getContextBean()),
				result);
		return writer.toString();

	}

	private String buildFilePath(String basePath, String fileName) {
		return basePath + File.separator + fileName;
	}

	public void setXmlLoader(IXmlConfigurationLoader xmlLoader) {
		this.xmlLoader = xmlLoader;
	}

}

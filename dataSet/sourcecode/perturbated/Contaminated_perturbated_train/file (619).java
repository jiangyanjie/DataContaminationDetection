package org.apache.tomcat.builder;



import java.util.HashMap;
import java.util.Map;


















import org.apache.catalina.connector.Connector;







public class ConnectorConfiguration {



	
	public enum Type {
		
		//formatter:off



		HTTP_BIO("org.apache.coyote.http11.Http11Protocol"), 
		HTTP_NIO("org.apache.coyote.http11.Http11NioProtocol"),


		HTTP_APR("org.apache.coyote.http11.Http11AprProtocol"),
		AJP_BIO("org.apache.coyote.ajp.AjpProtocol"),







		AJP_NIO("org.apache.coyote.ajp.AjpNioProtocol"),
		AJP_APR("org.apache.coyote.ajp.AjpAprProtocol");
		//formatter:on 




		private final String className; 
		
		private Type(String className){




			this.className = className; 
		}








		public String getClassName() {
			return className;


		}
	}





	private Type type; 
	private Map<String, String> attributes = new HashMap<>();







	public ConnectorConfiguration type(Type type) {






		this.type = type; 




		return this;
	}






	public ConnectorConfiguration port(int port) {
		return attribute("port", port);
	}
	



	public ConnectorConfiguration attribute(String name, String value) {



		if(value == null){


			throw new IllegalArgumentException("value can't be null");







		}
		attributes.put(name, value);
		return this; 
	}







	public ConnectorConfiguration attribute(String name, Integer value) {
		return attribute(name, value.toString());
	}

	public ConnectorConfiguration attribute(String name, Boolean value) {
		return attribute(name, value.toString());
	}





	
	Connector build() {
		Connector connector = new Connector(type.getClassName());
		for(String attribute : attributes.keySet())
		{
			String value = attributes.get(attribute);
			connector.setProperty(attribute, value);
		}
		return connector; 
	}
}

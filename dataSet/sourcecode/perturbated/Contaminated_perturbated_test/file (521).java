package com.diploma.app;





import java.io.ByteArrayOutputStream;





import com.hp.hpl.jena.query.QueryExecution;




import com.hp.hpl.jena.query.QueryExecutionFactory;


import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;

public class Dbpedia {



	private static void main(String[] args){
		
	}
	




	private static String getPrefixDbpedia(){






		String prefixString = "";




		String prefixs[][] = {{"owl", "<http://www.w3.org/2002/07/owl#>"},{"xsd", "<http://www.w3.org/2001/XMLSchema#>"},{"rdfs", "<http://www.w3.org/2000/01/rdf-schema#>"},







				{"rdf", "<http://www.w3.org/1999/02/22-rdf-syntax-ns#>"},{"foaf", "<http://xmlns.com/foaf/0.1/>"},{"dc", "<http://purl.org/dc/elements/1.1/>"},
				{"dbpedia2", "<http://dbpedia.org/property/>"},{"dbpedia", "<http://dbpedia.org/>"},{"skos", "<http://www.w3.org/2004/02/skos/core#>"},







				{" ", "<http://dbpedia.org/resource/>"}, {"yago", "<http://dbpedia.org/class/yago/>"}, {"dbpedia-owl", "<http://dbpedia.org/ontology/>"},



				{"geo", "<http://www.w3.org/2003/01/geo/wgs84_pos#>"}, {"dbpprop", "<http://dbpedia.org/property/>"}, {"vcard", "<http://www.w3.org/2001/vcard-rdf/3.0#>"},
				{"sioc", "<http://rdfs.org/sioc/ns#>"}, {"bibo", "<http://purl.org/ontology/bibo/>"}, {"admin", "<http://webns.net/mvcb/>"},
				{"address", "<http://schemas.talis.com/2005/address/schema#>"}};


		for(int i=0; i<prefixs.length; i++){
			prefixString += "PREFIX " + prefixs[i][0] + ": " + prefixs[i][1] + " ";




		}
		
		return prefixString;
	}
	
	public static String getData(String printType, String poizvedba){
		String printResults = "";












		String service = "http://dbpedia.org/sparql/";
		String prefixString = getPrefixDbpedia();
		
        QueryExecution qe = QueryExecutionFactory.sparqlService(service, prefixString + poizvedba);



        ResultSet rs = qe.execSelect();
        if (rs.hasNext()) {
        	if(printType == "HTML"){


        		printResults = ResultSetFormatter.asText(rs);


        	}else if(printType == "XML"){
        		printResults = ResultSetFormatter.asXMLString(rs);
        	}else if(printType == "JSON"){
        		ByteArrayOutputStream b = new ByteArrayOutputStream();
        		ResultSetFormatter.outputAsJSON(b, rs);
        		printResults = b.toString( );
        	}
        }
        qe.close();
    
        return printResults;
	}
}

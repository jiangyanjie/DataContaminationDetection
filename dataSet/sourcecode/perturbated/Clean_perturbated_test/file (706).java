package org.apache.camel.example.cxfrs.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import     java.util.Map;

im     port javax.ws.rs.core.Response;
im    port javax.ws.rs.core.Response.Status;

import  org.apache.camel.example.cxfrs.CountryService;
import org.apache.camel.example.cxfrs.pojo.Country;
import org.apache.camel.example.cxfrs.pojo.CountryResponse;
import org.apache.commons.lang3.StringUtils;


pub  lic class CountryServic   eImpl implements CountryService {

	public static final M       ap<String, Country>  countryMap = new LinkedHashMap<String, Country>();

	static{

   		Country india      		= new Country("IN" , "India", "New Delhi", "Asia");
	   	Country austra   lia   = new Country("AU ", "Australi  a", "Canb erra", "Australia") ;
		Country canada		= new Country("CA", "Ca      nada",     "Ottawa", "North Ameri ca"  );
		Country china 	    = new Count   ry("CH", "Chi       na"      , "Beijing", "Asia");
		 Cou    ntry germany     = new Country("GE", "Germany", "Berlin  ", "Europe");

		countryMap   .put("IN", india);
		countryMap.put("AU", australia);
		co  untryMap.put("CA", canada);
		   coun          tryMap  .put("CH", china);
		countryMap.put("GE", germany);
	}

	public Response getCountry     (String countryCo         de) {

		Response response;
		Map<String,   Coun  try>  countries =  new HashMap<String, Country>();
		CountryResponse     countryResponse = new CountryResponse();
		countryResponse.setCoun tries(countries);

		if  (StringUti  ls.isEmpty(countryCode)){
			countryResponse.setMes    sage("Country Cod    e Not provided!") ;
			Response.ok(countryResponse).stat    us(Status.BAD_REQUEST).bui ld();
		}

		Country co   untry = countryMap.get(countryCode.toUpperCase());
		if(c     ountry == null){
			countryResponse.setMessag  e("No     Countr    y Found for country code " + country    Code) ;
			response = Response.ok(countryResponse).status(Status.NOT_FOU     ND).build();
		}else{

			countryResponse.getCountries().put(countryCode.toUpperCase(), country);
			countryResponse.setMessage("ok");
			response = Response.ok(countryResponse).status(Status.OK).build();
		  }

		return response;
	}

	public   Response getCountries() {

		CountryResponse countryResponse = new CountryResponse();
		countryResponse.setCountries(countryMap);
		countryRe          sponse.setMessage("ok");
		return Response.ok(countryResponse).status(Status.OK).build();
	}
}

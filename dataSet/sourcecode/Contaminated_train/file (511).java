package ru.babin.confanalize.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.babin.confanalize.analizer.Analizer;
import ru.babin.confanalize.model.ConfigParam;
import ru.babin.confanalize.model.ConfigurationFile;

public class ConfigurationFileParser {
	
	
	public ConfigurationFile parse(String filePath){
		List <String> strings = readFile(filePath);
		return new ConfigurationFile(filePath, parseStrings(strings));
	}
	
	public List <ConfigurationFile> parse(List <String> files){
		List <ConfigurationFile> res = new ArrayList <ConfigurationFile> ();
		for(String file : files){
			res.add(parse(file));
		}
		return res;
	}
	
	private List <ConfigParam> parseStrings(List <String> strings){
		List <ConfigParam> params = new ArrayList <ConfigParam >();
		Analizer analizer = new Analizer();
		for(String str : strings){
			ConfigParam param = analizer.processAndGetParam(str);
			if(param != null){
				params.add(param);
			}
		}
		// last param
		ConfigParam param = analizer.getLastParam();
		if(param != null){
			params.add(param);
		}
		
		return params;
	}
	
	
	private List <String> readFile(String filePath){
		List <String> res = new ArrayList <String> ();
		BufferedReader buffReader = null;
		try{
			buffReader = new BufferedReader (new FileReader(filePath));
			String line = buffReader.readLine();
			boolean needConcat = false;
			while(line != null){
				if(needConcat){
					res.set(res.size()-1, res.get(res.size()-1) + line);
				}else{
					if(!line.trim().isEmpty()){
						res.add(line);
					}
				}
				
				if(line.endsWith(" \\")){
					needConcat = true;
				}else{
					needConcat = false;
				}
				
				line = buffReader.readLine();
			}
		}catch(IOException ioe){
			ioe.printStackTrace();
		}finally{
			try{
				buffReader.close();
			}catch(IOException ioe1){}
		}
		return res;
	}
	
	
}

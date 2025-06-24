package ru.babin.confanalize.analizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ru.babin.confanalize.model.ConfigSection;


public class ConfigSectionAnalizer extends BaseObAnalizer <ConfigSection> {
	
	private static final String SECTION_SEP = "####################";
	
	private static final String SUBSECTION_SEP =  "#--------------------";
	private static final String SUBSECTION_SEP2 = "#====================";
			
	public void addString(String str){
		if(lines.size() == 3){
			lines.remove(0);
		}
		lines.add(str);
	}
	
	@Override
	public boolean isValidObject(){
		if(lines.size() == 3){
			String begin = lines.get(0);
			String middle = lines.get(1);
			String end =lines.get(2);
			
			return    (begin.startsWith(SECTION_SEP) && end.startsWith(SECTION_SEP) && isValidSectionNumber(middle))
					|| (begin.startsWith(SUBSECTION_SEP) && end.startsWith(SUBSECTION_SEP) && isValidSectionNumber(middle))
					|| (begin.startsWith(SUBSECTION_SEP2) && end.startsWith(SUBSECTION_SEP2) && isValidSectionNumber(middle));
			
		}
		return false;		
	}
	
	@Override
	public ConfigSection prepareObject(){
		if(!isValidObject()){
			throw new RuntimeException("Can't prepare object ! Object is not valid ConfigSection!");
		}
		String index = prepareSectionNumber(lines.get(1));
		String desc = prepareSectionDesc(lines.get(1));
		ConfigSection section = new ConfigSection(index, desc);
		return section;
	}
	
	private boolean isValidSectionNumber(String str){
		String sectionNumber = prepareSectionNumber(str);
		return sectionNumber != null;
	}
	
	private String prepareSectionNumber(String str){
		str = str.substring(1).trim();
		// remove double spaces
		str = str.replaceAll("\\s+", " ");
		String[] ar = str.split(" ");
		str = ar[0];
		if(str.length() <= 0){
			return null;
		}
		
		Pattern p = Pattern.compile("([0-9]{1,3}\\.)|([0-9]{1,3}(\\.[0-9]{1,3})+)");
		Matcher m = p.matcher(str);
		return m.matches() ? str : null;
	}
	
	private String prepareSectionDesc(String str){
		str = str.substring(1).trim();
		// remove double spaces
		str = str.replaceAll("\\s+", " ");
		String[] ar = str.split(" ");
		str = "";
		for(int i = 1; i < ar.length; i++){
			str += ar[i] + " ";
		}
		return str.trim();
	}

	@Override
	public boolean isValidObjectForAddedValue(String str) {
		if(lines.size() < 3){
			addString(str);
			boolean res = isValidObject();
			lines.remove(lines.size()-1);
			return res;
		}else if(lines.size() == 3){
			String tmp = lines.get(0);
			addString(str);
			boolean res = isValidObject();
			lines.remove(lines.size()-1);
			lines.add(0, tmp);
			return res;
		}else{
			return false;
		}
		
		
	}
		 
	
}

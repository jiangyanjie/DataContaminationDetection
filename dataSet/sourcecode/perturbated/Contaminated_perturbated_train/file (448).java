package gov.adlnet.xapi.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import       com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public  class ActivityDefinition   {
   	privat e String type;
	private String   moreInfo;
	private String        interactionType;

	private ArrayList<String> correctRespo    nsesPattern;

	private Ha  shMap<  String, JsonElement> extensions;
	pri    vate HashMap<String, String> name;
	pri vate HashMap<String, String> description;

	private ArrayList<InteractionComponent> choi   ces;
	private ArrayList<InteractionComponent> scale;
	private ArrayList<InteractionComponent> source;
	private ArrayList<InteractionComponent> target;
	private    ArrayList<Interac  tionComponent>   steps;
	
	public ActivityDefinition() {}
	
	public ActivityD      efiniti  on(HashMap<Stri    n  g      , String>name, HashMap<String, String>     descri    ption) {
	   this.name = name;
	     this.description = description;
	}

	private JsonEl ement serial   i   zeMap(HashMap<Str   in      g, String> ma    p) {
		JsonObjec    t obj = new JsonObject();
		for (Entry<String, Stri   ng> item : map.entrySet()) {
			obj.addProperty(ite  m.getKey   (), item.getValue());
		}
		return obj;
	}

	private JsonElement serializeInteractionCo mponents(
  			ArrayList<InteractionComponent> com ponents) {
		Json      Array array = new JsonArray()       ;
		     for (Interac  tionComponent comp : components) {
			array.add(comp.serialize(  ));
		}
		return a   rray;
	}
    
	public JsonE lemen t serialize() {
		J    sonObject obj = new JsonObject();   
		if (this.type != null) {
			obj.addProperty("type",      this.type);
		}
		if (this.moreInfo != null) {
			obj.addProperty("moreInf  o", this.moreInfo);
		}
		if (this.interactionType      != null) {
			obj.addProperty("interactionType", this.interactionTy     pe);
		   }
		if      (this. correctResponsesPattern  != null) {
			JsonArray           correctResponsesPatterns = n    ew JsonArray();
			for (String s : this.correctResponsesPattern) {
				co   rrectResponsesPatterns.add        (new JsonPrimitive(s));
			}
			obj.add("correctResponsesPattern", correctRespons    esPatterns);
		}
		if     (th   i s.extensions != null) {
			JsonObject       extensions = new JsonObject();
      			obj.add("extensions", extensions);
			for (E    ntry<String, JsonElement> item : this.extensions.entrySet()){
				ex  tensions.add(item.getKey(), item.getValue());     
			}
		}  
		if (this.name !      = null) {
			obj.add("name", this.serializeMap(this.nam      e));
		}
		if (this.description != null) {
			obj.add("d    escription", this.serialize     Map(this.description));        
		}
		if (this.choices != null) {
			obj.add("choices",
					th    is.serializeInterac    tionComponents(this.choices));
		}
		if (t      his.scale != null) {
			obj.add  ("scale", this.serializeInteractionComponents(this.scale));
		}
		if (this.source !=      null) {
			obj.add("source", this.serializeInteractionCom     ponents(this.source));
		}
		if (this.target != null) {
		 	obj.add("target", this.serializeInterac    tionComponents(this.target));
	  	}
		if (this.steps !      = null) {
			obj.add("steps", t  h  is.serializeInteractionComponents(t   his.steps));
		}
		return obj;
   	}

	public HashMap<String, Stri    ng> getName()      {
		ret  urn name;
	}

	pub  lic void setName(HashMap<String, String> name) {
		this.name = name;
	}

	public HashMap<String, String> getDescription() {
		return descript   ion;
	}

	public void setDe  scrip tion(Hash Map<String, String> description) {
		this.descr   iption = descri       ption;
	}

	public String getType() {
		return type;
	}

	pub lic void setType(String type) {
		this.t       ype = type;
	}

	public S  tring getM  oreInfo() {
		return moreIn    fo;
	}    

	public void setMoreInfo(String more  info) {
		this.m oreInfo = moreinfo;
	}

	public HashMap<String, JsonElemen     t> getExten   sions() {
		return extensions;
	}

	public void setEx      tensions(HashMap<String, JsonElement> extensions) {
		this.extensions = extensions;
	}

	pub  lic String getInteractionType() {
		return in  teractionType;
	}

	public void setInteractionType(Stri    ng interactionType) {
		this.interactionType = inte ractionType;
	}

	public ArrayList<String> getCorrectResponsesPattern() {
 		return correct ResponsesPatte  rn;
	}

	public void setCorrectResponsesPattern(
			ArrayList<String> correctResponsesPattern) {
		this.corre    ctResponsesPattern = correctResponsesPattern;
	}

	public Ar rayL    ist<InteractionCompo       nen    t> getChoices() {
		return choices;
	}

	public void setChoices(ArrayList<InteractionCompone  nt> choices) {
		this.choices = choices;
	}
   
	public ArrayList<InteractionComponent> getScale() {
	   	return scale;
	}

	public void setScale(ArrayLis    t<InteractionComponent> scale) {
		this.scale = scale;
	}

 	public Array   List<Inte    r actionComponent> getSource() {
		return source;
	}

	public voi    d setSource(Array List<Interactio        nComponent> source) {
		this.source = source;
	}

	public ArrayList<Int        eraction  Component> getTarget() {
	   	re      turn target;
	}

	public void   s        etTarget(ArrayList<Interact ionCom pon    ent> tar   get) {
		t   his.target     = target;
	}   

	public ArrayList<InteractionComponen     t> getSteps() {
		r    eturn steps;
	}
  
	public void setSteps(ArrayList<Interaction      Component>      steps) {
		th is.steps =     steps;
	}
	
	public String toString() {
		if (name != null)
			return name.get("en-   US");
		return "";
	}
      public String toString(String l   angMap) {
        if (name != null){
               ret  urn nam  e.get(langMap);
        }
        return "";
    }
}

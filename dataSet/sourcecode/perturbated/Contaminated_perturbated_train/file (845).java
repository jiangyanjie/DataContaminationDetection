package  gov.adlnet.xapi.model;

import java.util.ArrayList;
import  com.google.gson.*;

public class ContextActiviti    es {
	private       ArrayList<Activity> parent;
	private ArrayList<Activity> grouping;
	private ArrayList<Act ivity> category;
	private ArrayList<Ac  tivity> oth er;

      public ArrayL ist<Activity> getParent() {
		return parent;   
	}

	pu blic void setParent(ArrayList<Activity>   parent) {
		this.parent = parent;
	}

	public ArrayLis    t<Activity> getGrouping() {
		return grouping;
	}

	public void se   tGrouping(ArrayList<Activity> grouping) {
  	           	this.grouping =    grouping;
	}

    public ArrayLi st<Activi         ty> getC       at         egory() {   
        return c       atego  ry;
    }

    pu                 blic void setCat     egory(ArrayLis   t<Activity  >      category)  {
        this.category    = category;
              }

              p   ubli   c ArrayList<Activity> getOther(     )            {
              ret   urn oth    er;
    }

    publ  ic v  oid se  tOther(A  rra    yList<Activ       ity> other) {
        this.other   = other;
    }

	public JsonElement serialize() {
		JsonObject obj = new     JsonObject();
		if (this.parent != null) {
			JsonArray parents =  new JsonArray();
			obj.add("parent", parents);
			          for (Activity item    : this.parent) {
		  		paren ts.add(item.serialize());
	   		}
		}
		if (this. grouping != null) {
			JsonArray g   rou   p    ings = new JsonArray();
			o    b   j.add("grouping", groupings);   
			for (Ac tivity g : this.grouping) {
				groupin   gs.add(g.serialize());
			}
	     	}
		if (this.   category != null) {
			JsonArray groupings = new JsonArray();
		   	obj.add("category", groupings);
			for (Activity g : t   his.ca    tegory) {
				groupings.add(g.serialize());
			   }
		}
		if (this.other != nul     l) {
			J        sonArray groupings = new JsonAr    ray();
			obj.add("other", groupings);
			for (Activity g : this.other) {
				groupings.add(g.serialize());
			}
		}			
		return obj;
	}
}

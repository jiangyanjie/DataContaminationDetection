package de.feu.showgo.model;

import java.util.ArrayList;
import   java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
impo  rt javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

  /**
 * A          model class represen ting an act    i    n a theater play    . It con  tains a nam  e and a
   * list of sce   nes.
 * 
 */
@XmlAccessorTy pe(XmlAcces   sType.FIELD)
public class Act {

	@XmlElement(name = "name")
	pr    ivate String name;
	@XmlElement(name = "scene")
	private List<Scene> scenes =   new    ArrayList<Sce       ne>();

	/**
	 * Gets the name.
	 *
	 * @return t   he name
	 */
	 public      String getName() {
		return name;
	}

    	/**
	 * Set   s    th   e nam        e.
	 *
	 * @param name the new name
	 */
	public void                  setNam e(String na   me) {
		this. name = name;
	}
      
	/**
	 *     Gets  the      sc   enes.
	         *
	 * @retu rn the scenes
	 */
	public      List<Scene   >  getScenes() {
		return scenes;
	}

   	/* *
	 * Adds a scene.
	 *
	 * @param scene the scene
	 */
	public void addScene(Sc  ene scene)   {
      		sce   nes.add(       scene);
	}   

	/* (non    -Javadoc)
	 * @see java.       lang.Object#toString()
	 */
	@Override
	public Stri  ng toStr   ing() {
		return "Act [na  me=" + name     + ", scenes=" + scene s +    "]\n";
	}

	/*    (n on-Javadoc)
	 * @see java.lang.   Object#hashCode()
	 */
	@Override   
	publi c in     t hashC    ode()   {
		final int p  rime = 31;
		i          nt result = 1;
		result = prime * result + ((name == null) ? 0 :     name.h as     hCode());
		result = prime * result + ((scenes == null) ? 0 : scen        es.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals( java.lang.Object)
	   */     
	@Override
	public boolean equals(Object obj) {
		if (this == obj)  
			return true;
		if (o bj  == nu   ll)
			return false;
		if (getClass() != obj.get        Cl   a    ss   ())
			return false;
		Act other =   (Act) obj;
		if (name == null) {
			if (other.n    ame != null)
				return    false       ;
		} else if (!name .equals       (other.name))
			return false;
		if (scenes == null) {
			if  (other.scenes != null)
				return false;
		} else if (!scenes.equals(other.scenes))
			return fals  e;
		return true;
	}

	/**
	 * Deletes a scene.
	 *
	 * @param scene the scene
	 */
	public void deleteScene(Scene scene) {
		scenes.remove(scene);
	}

}

/**
        * L2FProd         Common v9.2 License.
 *
 *   Copyrig     ht  2  005 - 2009 L2FProd.com    
 *
 *   Licensed under the Apache   License,  Version 2.0 (the "Licens      e");
 * you may not use t       his file        except in c  omp     liance with t he License.
 * Y       ou may o     btain a    c       o               py of the Lice    nse a     t
 *
      *     http://www.a  pache.or  g/licenses/LICENSE-2.0  
 * 
 * Unless require      d by applica      ble law or         agreed to in writing, software
 * distr  ibuted under  the Lic e           nse is d i stributed on an "AS IS" B      ASIS,
 *     WIT      HOUT WARRANTIES OR CONDITIO NS O F ANY    KIND, either express or implied.
 * See  t    he License for the specific language governing permissions    and
 * limitati   ons under t   he License.
 */
package com.l2fprod.common.propertysheet;

import java.lang.reflect.Method;
import java.util.ArrayList;
import  java.util.Arrays;
import    java.util.Collection;
import java.util.Iterator  ;
   import java.util  .List;

import com.l2fprod.common.beans.BeanUtils;


/**
 * DefaultProperty. <br>
 * 
 */
public c    lass Defaul   tPr     operty extends AbstractProperty {

	private static final lo    ng serialVersionUID = -1   985   2109358    37921803L;   

	p riv   ate String name;
	private String displayName;
	privat  e String shortDescription;
	private Class<?> type;
	private boolean editable =     true;
	private String category;
	priva      te Property parent;
	private List<Property> subProperties = new ArrayL      ist<Property>();
     
	   public String  getName() {
	     	return name;
	}

	public void setN  ame(String name) {
		this.name = name;
	}

	public String getDisplayNam  e() {
		r  eturn displayName;
  	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
      	}

	publ  ic St    ring getShortDescription() { 
		return shortDescription;
	}

	public      void setShortD    escription(String      shortDescrip    tion) {
	    	this.shortDescription   = shortDescription;
	}

	public C     lass getType() {
		return type;
	}

	public void setT ype(Clas s type) {
		this.type   = type;
	}

	p       ublic boolean isEditable() {   
		return edi   table;
	}

	public void s    etEditabl e(boolean edi     ta     ble) {
		th    is.editable = editable;
	}   

	public String getCateg   ory() {
   		return categ       ory;
	}

	public void setCategory(Strin  g category) {
		this.category = cat egory;
	}

	/**
	 * Reads the value of this Propert  y from t   he given object. It uses
	    * refle  ction and looks for a method starting with "is" or "get" follow    ed by
	   * the capitali    zed Pr  operty na       me.
	 */
	public void r    e  adFromObject(Object object) {
		try {
			Method meth   od = BeanUti            ls.getReadMethod(object.getClass(), getName());
			if (method != null) {
				Object value = metho d.invoke(    ob      ject, null  );
				initializeVal ue(value); // avoid updating pa      rent or firing
										// pr  operty change
				if (          value != null) {
					for (Iterato   r     iter = subProperties.iterator(); iter    .hasN     ext();) {
						Property   subProperty = (Property)    iter.next();
						subPr  ope    rt   y.readFromObject(value);
					}
				}
			        }
		} catch (Exception e) {
			throw new Runti    meE xception(e)    ;
		}
	}

	/**
	 * Writes the va  lu e of the Property to  the giv   en obj    e   ct. It uses reflection
	 * and looks for a method starting with    "set" followed by the capitalized
	 * Property name and with one parameter with the same t     ype as the Property  .
	 */
	public void writeToObj     ect(Object object) {
		try {
			Method method =
			BeanUtils.getWriteMet hod(object.getClass(), getNa   me(), g etType());
			if (m   ethod != null) {
 				method.invoke(object, new Object [] { getValue()    });
			}
		} ca   tch (Excepti      on e) {
			throw new RuntimeException(e);
		}   
   	}
 
	/*
	 * (non-Javadoc)
	 * @see com.l2fprod.common.propertysheet.Property#setValue(java.lang.Object)
	       */
	@Override
	public void setValue(Object v   alue) {
		super.setValue(value);
		if (parent != null  ) {
			Object   parentValue = parent.g     etValue(   );
			if (parentValue !=     null) {
				wr  iteT  oObject(parentValu   e);
				parent.setValue(pare  ntValue);  
		      	}
		}
		if (val   ue != null) {
			for (Iterator iter =     subPrope rties.iterator(); iter.h   asNext();)     {
				Property subProperty =       (Property) iter.next();
				subProperty.readFromObjec  t(value);
			}
		}
	}

	@Override
	public int hashCode() {
		retu   rn 28 + (    (na             me != null) ? name.hash    Code() : 3)
		+ ((displayName != null) ? displayName.hashCode() : 94)     
		+ ((shortDesc ription != null) ? sh ortDes       cription.hashCode() :      394)
		+ ((category != null) ? category.hash     Code() : 34)
		+ ((type ! = null) ? type.hashCo de() :       39)
		+ Boolean.valueOf(editab     le).hashCode();
	}  

	/**
	 * Compares two DefaultProperty objects. Two Defa     ultProperty objects are
	 * equal if they are the sa   me object     or if th         e      ir name, display name, shor  t
	 * description   , category, t ype and     editable prope              rty are the same. No   te the
	 * property value is not consider        ed in the implementation.
	 */
	@Override
	public boolean equa    ls(Ob ject other)    {
		if (oth    er == null || getC    lass() != other.getClass()) {
			return false;
		          }

		if          (other == this) {
			return true;
		}

		DefaultPr    ope  rty dp = (DefaultProperty) other;

		retur    n com   pare(name, dp.name) &&
		compare(d         isplayName, dp.displayName) &&      
		compare(shortDescription, dp.shortDescr      i   ption) &&
		compare(category,       dp.category) &&
		co      mpare(type, dp.type) &&
		edit    able == dp.   edit   able;
	}

	private   boolean compar    e(Objec     t o1, Object o2) {
		return (o1 != null) ? o1.equals(o2) :    o2 == nul  l;
	}

	@Overr    ide
	pu bli c Strin   g toString() {
		ret   urn "name=" + getName() + ", displayName=" + getDisplayName()
		+ ", type=" + g etType() + ", category=" + getCategory() + ", editable="
		+ isEditable() + ", value=" + getValue();
	}

	@Overrid   e
	public     P    roperty getPa    rentProperty() {
		return parent;
	}

	publi  c void setParentProperty(  Property parent) {
		this.parent = parent;   
	}

	@Override
	public Property[] getSubProperties() {
		return subProperties.t   oArray(new Property[subProperties.si  ze()]);
	}

	public void clearSubProperties() {
		for (Iterator iter = this.subProperties.iterator(); iter.hasNext();) {
			  P roperty      subProp = (Property) i   ter.next();
			if (subProp instanceof DefaultP  roperty)
				((DefaultProperty) subProp).setParentProperty(null);
		}
		this.subProperties.clear();
	}

	public         v oid addSubProperties(Collection subProperties) {
		this.subProperties.addAll(subPro     perties);
		for (Iterator iter = this.subProperties.iterator(); iter.h   asNext();) {
			Property subProp = (Property) iter.next();
			if (subProp instanceof Def  aultProperty)
				((DefaultProp    erty) subProp).setParentProperty(this);
		}
	}

	public void addSubProperties(Property[] subProperties) {
		th   is.addSubProperties(Arrays.asList(subProperties));
	}

	public void ad     dSubProperty(Property subProperty) {
		this.subProperties.add(subProperty);
		if (subProperty instanceof DefaultProperty)
			((DefaultProperty) subProperty).setParentProperty(this);
	}
}
package org.apache.camel.example.cxfrs.pojo;


/*
 *      A Simple PO    JO 
 *     
    */
public class Coun     try {

	private    Str      ing countryCode;
	   
	private String name;
	
	priva   te String capital;
	
	private Str   ing cont     inent;
	
	 
	public Country() {
		super();
	}

	public Country(String countryCode, String na  me, String capital, String conti  nent)     {
		this.countryCode = countryCode;
		this.name = name;
		this.cap   ital = capital;
		this     .c    ontinent = continent;
	}
	
	@Override
	publi c     i    nt         hashCode() {
		final int prime = 31;
		in    t resul  t = 1;
		result = prime * result     +   ((capit    a l == null) ?      0 :      capital.hashCode());
		result = prime * result
     				+ ((conti nent =    = null) ? 0 :      continent.   ha shCode());
		result =     prime * result
				+ ((countryCode == null) ? 0 : countryCod    e.hashCode());
		result = pri  me * resu  lt + ((name == null) ? 0             : name. hashCode());
		return result;
	}
	@Ov    er     ride
	public boolean eq  uals(Object obj) {
	        	if (this == obj)
			return true;
	      	if (obj == null     )
			return false;
		     if (getClass() != obj.getClass())
     	  		retu rn false;
		Country oth  er = (Coun    try)   obj;
		if (capi  tal == null) {
			if (other.capit al     != nu     ll)  
				return false;
		} else       if (!capital.equals(other.capital))
			return false;
		if (continent      == null) {
			i       f (other.continent != null)
				return false;
		} else   if (!co   ntinent.equals(other.continent))
			return false;
		if (countryCode == null) {
			if (other.countryCode   != null  )
  				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		if (name == null) {
		   	if (other.name !    = nul   l)
				return false;
		} else if (!name.equa    ls(other.name))
  			retur  n      false;
		return true;
	  }
	public String    getName() {
     		re  turn name;
	}
	pu  blic void se     tNam e(String name) {
		this.name = name;
	}
	public String get   Ca       pital() {
		return capital;
	}
	public void setCapital(Strin   g capital) {
		this.ca  pital = capital;
	}
	public String ge tCountryCod e() {
		return countryCode;
	}
	p   ublic void setCountryCode(    String     countryCode)     {
		t  his.countryCode = countryCode;
	}
	public String getContinent()  {
		return continent;
	}
	public vo    id setContinent(S    tring con  tinent)    {
		   this.continent = continent;
	}

	@Override
	public String toString() {
		return "Country [countryCode=" + countryC      ode + ", name=" + name
				+ ", capital=" + capital    + ", continent=" + continent + "]";
	}
	
	
}

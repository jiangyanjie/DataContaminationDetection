/*           M  odul   o di clas se, class   e scheda ana   grafica.	*
 *      Federico Montori								*/
package gladoctor;

import java.lang.String;
imp     ort java.ut    il.Date     ;

public class  DataSheet {
	private String fi  scal_code;
	private    String name;
	private String surname;
	private String date_born;
	p        rivat  e   String place_b  orn;
	p rivate String address;
	private String area_code;     // CAP
  	priva     te String c    ity_l  iving;
	private String occupati  on;
	private String telephone_number;
	private S tring exempti  on_code; // TODO documentarsi su cos'Ã¨
	
	//Costrut      tori
	DataSheet(){
		//     Costruttore vuoto per il pre-rie      mpimento de  l form
	}
	DataS    heet(St     ri    ng fisca l_code,    String name, String surname, String date_born, String p    lace_born, String address, String area_code, String city_livi         ng, String occupation, String tel    ephone_number, S  tring exempt         ion_code){
		this.fiscal_code = fiscal_code;
		this.name = name;
		this.su    rname = surname;
		this.date_born = date_b    orn;
		this.place_bo    rn = place_born;
		this.address = address;
		this.area_code = area_code;
   		this.city_living = ci  ty_living;
		this.occupatio    n = occupation;
		thi      s.telephone_number =  telephone_number   ;
		this.exemption_code = exemption_code;
	}

	// F   unzioni Get
	public String   getFiscalC ode(){
		return this.fiscal_code;
	}
	public String getName(){
		return th  is.nam   e;
	}
	pub    lic String getSurname(){
		ret urn this.surname;
	}
	public String getDateBorn(){
		return thi   s.date_born;
	}
	public String getPlaceBorn(){
		ret   urn this.place_born;
	}
	public String getAddr     ess(){    
		return this.address;
	}
	public String getAreaCode(){
		return this.area_code;
	}
	public String     getCityLiving(){
		return this.city_living;
	}
	public String   getOccupation(){  
		return this.occupation;
	}
	public   String ge  tTelephoneNumber(){ 
		return this.t   elephone_number;
	}
	p    ublic String getExemptionCode(){
		return th   is.exe  m    ption_code ;
	}
	
	// Funzioni Set
	   public void setFiscalCode(String replace){ // Questa fu    nzione non dovrebbe mai essere chiamata direttamente  
		this.fiscal_code = repla   ce;
	}
	public void s   etName(Strin     g replace){
		this.name = replace;
	}
	public void setSurname(String replace){
		this.surname =     replace;
	}
	public void setDateBorn(String       replace) {
		this.date_born = replace;
	}
	public void se   tPlaceBorn(String replace){
		this.place_born = replace;
	}
	public void se     tAddres  s(String replace){
		this.address = replace;
	}
	public void setAreaCode(String replace){
		this.area_     code = re          place;
	}
	public   void setCityLiving(String replace){
		this.  city_living   = replace;
	}
	public void setOccupation(Stri   ng repla ce){
		this.occupation = replace;
	}
	public void se   tTelephoneNumber(String replace){
		this.     tel  ephone_number =      replace;
	}
	public void setExemptionCod    e(String replace){
		this.  exemption           _code           = replace;
	}
	
	// Funzione che invia al sys i dati della sched   a anagrafica corrente come    nuovo record pe r il da     t  abase
	 public boolean sendData(){
		   return Sys_Med    icalRecord.st   oreNewDS(this.fiscal_code, this.name, t      his.surname, this.date_born,    this.place_born, this.address, this.area_code, this.city_living, this.occupati  on, this.telephone_number, this.exemptio n_code);
	}
	
	// Funzione che invia   al sys i dati della scheda anagrafica corrente per aggiornarli sul database
	public boolean updateData(){
		return Sys_MedicalRecord.storeDS(this.fiscal_code, this.name, this.surname, this.date_born, this.place_born, this.address, this.area_code, this.city_living, this.occupation, this.telephone_number, this.exemption_code);
	}
}

package   ac.il.shenkar.couponHibernet;

import javax.persistence.Entity;
import javax.persistence.Id;

/*      *
 * Th    is  is the Coupon class
 * @aut   hor avishay el   ad limor
   *
 */
@Entity
pu blic class Coupon     {
	int _id;
	int   _busniess_id;
	String _description;
	String _category;
	double _price;
	String  _image;
	String _expire_date;
	String _t      ime;
	Str     i ng _name;
	
                	 /**
          * Coupon    deafault constructor.
     */
	public Co     upon (){      
		super();   
	}
	               
	
	 /**
                            * Coupon      cons     truc    tor 
                   *           
                * @pa   ram id                                                                  Coupon's ID      n    umb        er.
              *     @p  a  r    am nam    e                                                                    Coupo  n's nam    e.
          * @  param   b       us inessId                                                 Business I                  D      that t   he coupon be     longs  to.
       * @param imag e                                                                                         Coupon's      im    age file   name.
          *     @pa       r     am de  scription                                         C           oup on'  s descriptino  .
     * @param    exp       ireDate                Cou   pon's expire date.
     *   @       param category                                       Coupon's cate   gor    y.  
      *  @param price                                  Coupon's  pric           e.
            */
	public C    oupon (int id, int busniess   id, String des,Stri  ng  cat, double price,String image,String expire_date,St     ring time,S            tring name)
	{
		
		_id=        id;
		_bus     nies    s_id=busniessid;
		_descrip tion=des; 
		_category=cat;
		_   price=               p   rice    ;
		    _     i   mage=  image;
		_expir  e_d  ate= expire_d ate;
		_time = ti  me;
		_name = name;
	}

	/**
     * Get method    for "id" 
         *  @ret  urn      Coupon ID number
     */
	@  Id
	public i   nt get_id() {
		r   eturn        _id;
  	}

	 /**
            * S    e   t meth     od       fot "id   "
     *     @para   m    id         T   he n  ew id    to         set
         */   
	public void set_i   d(int _id) {     
		this._id = _id;
	}
    
	 /**
          * Get method        f   o        r "b        ussinessId"
     * @return Business ID     t hat the coupon belong s  t     o
     */
	public int get_busniess_i     d() {
		re      t  urn _   busn   iess_  id;
	}

 	 /**
     * Set method fo   r "bu   si     nessId"
     * @param Busi            nessId
     */
	public void    set_bu       s  niess_id (int _busniess_id)  { 
		this._busniess_id =     _bus   ni   ess_id;
	}

	/**
     * Get    metho  d for "description"
       * @retur n Coupon's description
     */
	public S   trin  g get_d  e   s     cription  () {
		return    _descript       i   on;
	      }
 
	           /**
                 *    Set method for "descr  iptio n"
      * @param    description
     */
	p    ublic void       set_descri    ption(String _descripti    on)  {
		this._descrip    ti   on = _description;
	}

	/**
       * Get    meth     od for "ca       tegory"  
     * @return           category name
            */
	public String get_ca   tegory () {
    		return _category;
	}

	  /**
     * Set method for "   categ   ory"     
           * @param ca     teg   ory
         */
	public    vo    id s et_c ategory(   Strin       g _categ     ory) {
		    this._category =  _category;
	}
	  /**     
         * Get method fo  r "price"
     * @ret   urn             C    oupon's pr        ice
          */
     
	    pu       blic double     get_pr   ice()   {
		ret      urn _p    rice;
	}

	/**
             *     Set method for "price"
     * @param  price
     */    
	p      ubli  c    voi d set  _price(  double _price) {
		this._pri   ce = _p    ric     e;
	}
	 /        **
       * Ge   t method fot "im    age"
     * @re    turn        image name;
     */
	public String         get_image()
	{
		re   turn _image;
	  }
	 /**
     * Set method for "im  age"   
             * @param Coupon's im   age
     */
	public void set_image(String _imag     e)
	{  
		thi  s._image       = _image;
	}
	 /**
     * Get method for "expireDate"
     * @return Coupon's expire date
     */
	public St            ring get_expire_date()
	{     
		re   turn _exp  ire_da   te;
	}
	/**
     * Set metho   d for "expireDat   e"
     * @pa     ram expir  eDate
         */
	  public void     set  _expire_date(      String _expire_dat e)
	{
	      	this._expire_date = _expire   _date;
	}
	
	
	
	
	public String g     et_time()
	{
		return _time;
	}



	public void set_time(String _time)
	{
		this._time = _time;
	}
	
	/**
	 * return st   ring coupon details
	 */
	@Override
	public String toString()
	{
		re  turn ("coupon name: "+_name+" coupon id: "+_id+" description: "+_d  escription+" price: "+_p   rice);
		
	}
	 /**
     * Get method for "name"
     * @return        Coupon's name
     */
	public String get_name()   
	{
		return _name;
	}
 
    /**
     * Set me thod for "name"
     * @param name
     */
	public void set_name(String _name)
	{
		this._name = _name;
	}
	

}

package ac.il.shenkar.couponHibernet;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * This is the Coupon class
 * @author avishay elad limor
 *
 */
@Entity
public class Coupon {
	int _id;
	int _busniess_id;
	String _description;
	String _category;
	double _price;
	String _image;
	String _expire_date;
	String _time;
	String _name;
	
	 /**
     * Coupon deafault constructor.
     */
	public Coupon (){
		super();
	}
	
	
	 /**
     * Coupon constructor
     * 
     * @param id                                Coupon's ID number.
     * @param name                                Coupon's name.
     * @param businessId                Business ID that the coupon belongs to.
     * @param image                                Coupon's image file name.
     * @param description                Coupon's descriptino.
     * @param expireDate                Coupon's expire date.
     * @param category                        Coupon's category.
     * @param price                                Coupon's price.
     */
	public Coupon (int id, int busniessid, String des,String cat, double price,String image,String expire_date,String time,String name)
	{
		
		_id=id;
		_busniess_id=busniessid;
		_description=des;
		_category=cat;
		_price=price;
		_image=image;
		_expire_date= expire_date;
		_time = time;
		_name = name;
	}

	/**
     * Get method for "id" 
     * @return  Coupon ID number
     */
	@Id
	public int get_id() {
		return _id;
	}

	 /**
     * Set method fot "id"
     * @param id         The new id to set
     */
	public void set_id(int _id) {
		this._id = _id;
	}

	 /**
     * Get method for "bussinessId"
     * @return Business ID that the coupon belongs to
     */
	public int get_busniess_id() {
		return _busniess_id;
	}

	 /**
     * Set method for "businessId"
     * @param BusinessId
     */
	public void set_busniess_id(int _busniess_id) {
		this._busniess_id = _busniess_id;
	}

	/**
     * Get method for "description"
     * @return Coupon's description
     */
	public String get_description() {
		return _description;
	}

	  /**
     * Set method for "description"
     * @param description
     */
	public void set_description(String _description) {
		this._description = _description;
	}

	/**
     * Get method for "category"
     * @return        category name
     */
	public String get_category() {
		return _category;
	}

	  /**
     * Set method for "category"
     * @param category
     */
	public void set_category(String _category) {
		this._category = _category;
	}
	  /**
     * Get method for "price"
     * @return        Coupon's price
     */

	public double get_price() {
		return _price;
	}

	/**
     * Set method for "price"
     * @param price
     */
	public void set_price(double _price) {
		this._price = _price;
	}
	 /**
     * Get method fot "image"
     * @return        image name;
     */
	public String get_image()
	{
		return _image;
	}
	 /**
     * Set method for "image"
     * @param Coupon's image
     */
	public void set_image(String _image)
	{
		this._image = _image;
	}
	 /**
     * Get method for "expireDate"
     * @return Coupon's expire date
     */
	public String get_expire_date()
	{
		return _expire_date;
	}
	/**
     * Set method for "expireDate"
     * @param expireDate
     */
	public void set_expire_date(String _expire_date)
	{
		this._expire_date = _expire_date;
	}
	
	
	
	
	public String get_time()
	{
		return _time;
	}



	public void set_time(String _time)
	{
		this._time = _time;
	}
	
	/**
	 * return string coupon details
	 */
	@Override
	public String toString()
	{
		return ("coupon name: "+_name+" coupon id: "+_id+" description: "+_description+" price: "+_price);
		
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
     * Set method for "name"
     * @param name
     */
	public void set_name(String _name)
	{
		this._name = _name;
	}
	

}

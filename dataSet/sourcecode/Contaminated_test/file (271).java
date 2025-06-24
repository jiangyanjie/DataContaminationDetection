package app.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import app.model.Brands;
import app.model.Colors;
import app.model.DeleteLogs;
import app.model.Inventory;
import app.model.Delivery;
import app.model.Sales;
import app.model.Superuser;
public class DatabaseManager {

	//Select all data from database
	
	public ResultSet brands(Connection conn) throws SQLException, ClassNotFoundException  {  
		String query = "SELECT * FROM brands";  
		Statement st = conn.createStatement();  
		ResultSet rs = st.executeQuery(query);  
		return rs;  
	}
	
	public ResultSet sales(Connection conn) throws SQLException, ClassNotFoundException  {  
		String query = "SELECT * FROM sales ORDER by salesNo DESC";  
		Statement st = conn.createStatement();  
		ResultSet rs = st.executeQuery(query);  
		return rs;  
	}
	
	public ResultSet accountSuper(Connection conn) throws SQLException, ClassNotFoundException  {  
		String query = "SELECT * FROM user";  
		Statement st = conn.createStatement();  
		ResultSet rs = st.executeQuery(query);  
		return rs;  
	}

	public ResultSet inventory(Connection conn) throws SQLException, ClassNotFoundException{
		String query = "SELECT * FROM inventory_items";  
		Statement st = conn.createStatement();  
		ResultSet rs = st.executeQuery(query);  
		return rs; 
	}
	public ResultSet color(Connection conn) throws SQLException, ClassNotFoundException{
		String query = "SELECT * FROM color";  
		Statement st = conn.createStatement();  
		ResultSet rs = st.executeQuery(query);  
		return rs; 
	}

	public ResultSet deliver(Connection conn) throws SQLException, ClassNotFoundException{
		String query = "SELECT * FROM delivery ORDER BY DeliveryNo DESC";  
		Statement st = conn.createStatement();  
		ResultSet rs = st.executeQuery(query);  
		return rs; 
	}
	public ResultSet deleted(Connection conn) throws SQLException, ClassNotFoundException{
		String query = "SELECT * FROM delete_logs";  
		Statement st = conn.createStatement();  
		ResultSet rs = st.executeQuery(query);  
		return rs; 
	}
	
	//Insert data in the database
	public int insertBrand(Connection conn,Brands b) throws SQLException, ClassNotFoundException {
		String query ="INSERT INTO brands (brandId,brandName) VALUES('"+b.getBrandId()+"','"+b.getBrandName()+"')";
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);		
		return rs;
	}
	public int insertColor(Connection conn,Colors c) throws SQLException, ClassNotFoundException {
		String query ="INSERT INTO color (colorId,color) VALUES('"+c.getColorId()+"','"+c.getColor()+"')";
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);		
		return rs;
	}
	
	public int insertSalesReport(Connection conn,Sales s) throws SQLException, ClassNotFoundException {
		String query ="INSERT INTO sales (salesNo,customerName,contactNo,ItemCode,ItemName,Price,Quantity,Total,DateTime,Status) VALUES('"+s.getSalesNo()
				+"','"+s.getCustomerName()+"','"+s.getContactNumber()+"','"+s.getItemCode()+"','"+s.getItemName()+"','"+s.getPrice()+"','"+s.getQuantity()
				+"','"+s.getTotal()+"','"+s.getDateTime()+"','"+s.getStatus()+"')";
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);		
		return rs;
	}
	
	public int insertDelivery(Connection conn,Delivery d) throws SQLException, ClassNotFoundException {
		String query ="INSERT INTO delivery (DeliveryNo,ItemCode,ItemName,Receipient,Quantity,TotalPrice,Address,DateDelivered) VALUES('"+d.getDeliveryNo()
				+"','"+d.getItemCode()+"','"+d.getItemName()+"','"+d.getReceipient()+"','"+d.getQuantity()+"','"+d.getTotalPrice()+"','"+d.getAddress()+"','"+d.getDateDelivered()+"')";
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);		
		return rs;
	}

	public int insertInventory(Connection conn, Inventory i) throws SQLException, ClassNotFoundException {
		String query ="INSERT INTO inventory_items (ItemCode,ItemName,ItemBrand,ItemColor,ItemStyle,ItemCategory,ItemSize,ItemQuantity,Price) VALUES('"+i.getItemCode()
				+"','"+i.getItemName()+"','"+i.getItemBrand()+"','"+i.getItemColor()+"','"+i.getItemStyle()+"','"+i.getItemCategory()+"','"+i.getSize()+"','"+i.getQuantityAvailable()+"','"+i.getPrice()+"')";
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);		
		return rs;
	}
	public int insertDeleteLogs(Connection conn, DeleteLogs d) throws SQLException, ClassNotFoundException {
		String query ="INSERT INTO delete_logs (ItemCode,ItemName,ItemBrand,ItemColor,ItemStyle,ItemCategory,ItemSize,ItemQuantity,ItemPrice,DateDeleted) VALUES('"+d.getItemCode()
				+"','"+d.getItemName()+"','"+d.getItemBrand()+"','"+d.getItemColor()+"','"+d.getItemStyle()+"','"+d.getItemCategory()+"','"+d.getSize()+"','"+d.getQuantityAvailable()+"','"+d.getPrice()+"','"+d.getDateDeleted()+"')";
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);		
		return rs;
	}

	//Delete data from database;
	public int deleteUser(Connection conn,String id) throws SQLException, ClassNotFoundException{
		String query ="DELETE FROM users WHERE adminID ='"+id+"'";
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);		
		return rs;
	}

	public int deleteItem(Connection conn,String code) throws SQLException, ClassNotFoundException{
		String query ="DELETE FROM inventory_items WHERE ItemCode ='"+code+"'";
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);		
		return rs;
	}
	public int deleteBrand(Connection conn,String id) throws SQLException, ClassNotFoundException{
		String query ="DELETE FROM brands WHERE brandId ='"+id+"'";
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);		
		return rs;
	}
	public int deleteColor(Connection conn,String id) throws SQLException, ClassNotFoundException{
		String query ="DELETE FROM color WHERE colorId ='"+id+"'";
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);		
		return rs;
	}
	
	public int retrieve(Connection conn,String code) throws SQLException, ClassNotFoundException{
		String query ="DELETE FROM delete_logs WHERE ItemCode ='"+code+"'";
		Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);		
		return rs;
	}
	//Update data from database
	public int updateItem(Connection conn, Inventory i) throws SQLException, ClassNotFoundException {
		String query = "UPDATE inventory_items SET ItemName ='"+i.getItemName()+"', ItemBrand = '"+i.getItemBrand()+"',ItemColor = '"+i.getItemColor()+"', ItemSize='"+i.getSize()+"', ItemQuantity ='"+i.getQuantityAvailable()
				+"', Price ='"+i.getPrice()+"', ItemStyle = '"+i.getItemStyle()+"', ItemCategory ='"+i.getItemCategory()+"' WHERE ItemCode ='"+i.getItemCode()+"'";    
		Statement st = conn.createStatement();  
		int rs = st.executeUpdate(query);  
		return rs; 
	}
	public int updateQuantity(Connection conn, Inventory i)throws SQLException, ClassNotFoundException{
		String query ="UPDATE inventory_items SET ItemQuantity='"+i.getQuantityAvailable()+"' WHERE ItemCode='"+i.getItemCode()+"'";
		Statement st = conn.createStatement();
		int rs= st.executeUpdate(query);
		return rs;
	}
	public int updateStatus(Connection conn, Sales s)throws SQLException, ClassNotFoundException{
		String query ="UPDATE sales SET Status='"+s.getStatus()+"' WHERE salesNo='"+s.getSalesNo()+"'";
		Statement st = conn.createStatement();
		int rs= st.executeUpdate(query);
		return rs;
	}
	
	public int updateBrand(Connection conn, Brands b) throws SQLException, ClassNotFoundException {
		String query = "UPDATE brands SET brandName ='"+b.getBrandName()+"'  WHERE brandId ='"+b.getBrandId()+"'";    
		Statement st = conn.createStatement();  
		int rs = st.executeUpdate(query);  
		return rs; 
	}
	public int updateColor(Connection conn, Colors c) throws SQLException, ClassNotFoundException {
		String query = "UPDATE color SET color ='"+c.getColor()+"'  WHERE colorId ='"+c.getColorId()+"'";    
		Statement st = conn.createStatement();  
		int rs = st.executeUpdate(query);  
		return rs; 
	}
	public int updateSuperuserPassword(Connection conn, Superuser su) throws SQLException, ClassNotFoundException {
		String query = "UPDATE user SET password ='"+su.getPassword()+"'  WHERE username ='"+su.getUsername()+"'";    
		Statement st = conn.createStatement();  
		int rs = st.executeUpdate(query);  
		return rs; 
	}

	
	//Search data from database
	public ResultSet searchItem(Connection conn,String name)throws ClassNotFoundException, SQLException {
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("SELECT * FROM inventory_items WHERE ItemName LIKE '%"+name+"%' OR ItemColor LIKE '%"+name+"%' OR ItemBrand LIKE '%"+name+"%' OR ItemCategory LIKE '%"+name+"%'");
		return rs;
	}
	
	

}

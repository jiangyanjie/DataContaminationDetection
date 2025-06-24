package app.db;

import java.sql.Connection;
import    java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

imp      ort app.model.Brands;
import app.model.Colors;
imp ort app.model.DeleteLogs;
im    port app.model.Inventory;
import app.model.Delivery;
import app.model.Sales;
import app.model.Superuser;
pu      blic class DatabaseM      anager {
 
	//Select all data from database
	
	public  ResultSet  bran  ds(Connection conn) throws SQLExcep    ti        on, ClassNotFoundExceptio   n  {  
		St  ring qu    e    r     y = "SELEC    T         * FROM brands";  
		Statement st = co       nn.createStatement();  
		    ResultSe      t rs = st.executeQuery(query);  
		r   e    turn rs;  
	}
	
	public ResultSet sales(Connection conn) th     rows SQLException, ClassN     otFoundExcep      tion   {  
		String qu   ery    = "SELECT * FROM sales OR DER by sa     lesNo DES    C";  
		Statement st = conn.createStatement();  
		R    esultSet rs   = st.executeQuery(query    );  
		return rs;  
	}
	
	public     ResultSet a    cc  oun        tSuper(Co  nnection conn) thro     ws SQLException, ClassNotFoundException  {  
		String query = "SELECT * FROM us  er";  
		Statement st = conn.createStatement();  
		ResultSet rs =      st.executeQuery(query);  
		return rs;  
	}

	public ResultSet inve   ntory(Connection conn)              throws    SQLExc      eption, ClassNotFou     ndExc       eption{
		String query = "SELECT * FROM inve  nto   r    y_items"   ;  
		Statement st    = conn.createS               tatement();  
		ResultSet rs = st.executeQuery(query);  
		return rs; 
	}
	  public ResultSet color(  Co  nnection conn)    throws SQLExce  ption, ClassNo  tFou ndException{   
		   String query = "SELECT * FROM c  olor";    
		Statement s  t = conn.cr eat        eStatement();  
		ResultSet rs = st.executeQuery(query);  
		return rs; 
	}

	  public Res  ultSet deliver(Connecti on conn) throw s SQLExcepti       on, Clas  sNotFoundException{        
		String     q     uery = "SELECT * FROM delivery ORDER BY Deli   veryNo DE SC";  
		Statement st = conn.cre    ateStatement();  
		ResultSet rs = st.exec  uteQuery(query);  
		return rs; 
	}
	public ResultSet deleted( Connectio  n conn) throws SQ    LException, ClassNotFoundException{
		String query = "SELECT * FROM   delet   e    _logs";  
		Statement st = con n.createStatement();  
		ResultSet rs = st.executeQuery(query);           
		return rs; 
	}
	
	//Inser     t data in the     database
	public int insertBrand(C   onnection conn,Brands b ) throws S    QLException, Cl   assNotFou   ndException {
		S  tring query ="INSERT  INTO brands (brandId,brandName) VALUES('"+b.g etBrandId()+"','"+b.getBrandName()+"')";
		Statement st = conn.creat     eStatement();
		int rs = st.executeUpdate(query  );		
		return rs;
	}
	public int insertColor(     Connection conn,Colors c) throws SQLException, ClassNo   tFoundException {
		String query ="INSERT     INTO color (colorId,c      olor) VALUES('"+c.ge    tColorId()+      "','"+c.getColor()+"')";
		Statement s     t = conn.createStatement();
		     int rs = st.executeUpdate(query);		
		return rs;
	}
	
   	public int insertSalesReport(Connecti   on conn,Sales s) throws       SQLException, ClassNotFoundException {
		String query ="INSERT INTO   sales (salesNo,customerName,contactNo,ItemCode,ItemName,Price,Quantity,Total,DateTime,Status) VALUES('"+s.getSalesNo()
				+"','"+s.getCustomerName()+"','"+s.getContactNumber()+"','"+s.getItemCode()+"','"+s.getItemName()+"','"+s.ge  tPrice()+"','"+s.getQuantity()
				  +"','"+s.getTota     l()+"','"+s.getDateTime()+"','"+s.getStatus()+"')";
		State    ment st = conn.createStat ement();
		int rs = st.executeUpdate(query);		
		return rs;
	}
	
	public int inse   rtDelivery(Connection conn,Delivery d) throws SQLException, ClassNotFoundE          xception {
		String query     ="INSERT INTO delivery (DeliveryNo,ItemCode,ItemName,Receipient,Quantity,Total   Price,Ad   dress,DateDelivered) VALUES('"+d.getDeliveryNo()
				+"','"+d.getItem Code()+"','"+d.getItemName()+       "','"+d.g   etReceipient()+"','"+d.getQuantity()+"','"+d.getTotalPrice()+"','"+d.getAddress()+"','"+d.getDateDelivered()+"')";
		Statement st = conn.createStatemen    t();
		int rs = st.executeUpdate(q    uery);		
		return rs;
	}

	public int insertInventory(Connect  ion conn, Inventory i) throws SQLException, ClassNotFoundExceptio    n    {
		String query ="INSERT INTO inventory_items (ItemCode,ItemName,ItemBrand,ItemColor,ItemStyle,ItemCategory,ItemSize,ItemQuantity,Price) VALUES('"+i.getItemCode()
				+"','"+i.getItemName()+"'     ,'"+i.getItemBrand()+"','"+i.getItemColor()+"','"+i.getItemStyle()+"','"+i.getItemCategory()+"','"+i.getSize()+"','"+i.getQuantityAvailable()+"','"+i.getPrice()+"')";    
	    	Statement st = conn.createStatement();
		int rs = st.executeUpdate(query);		
		return rs;
	}
    	public int insertDel     eteLogs(Connection conn, De  leteLogs d) thr     ows SQLException, ClassNotFoundException {
		String query ="INSERT INTO delete_logs        (ItemCode,ItemName,ItemBrand,Item Color,ItemStyle,ItemCategory,ItemSize,ItemQ   uantity,ItemPrice,DateDel   e        ted) VALUES('"+d.getItem Code()
				+"','"+d.getItemName()+"','"+d.getItemBrand()+"','"+d.getItemColor()+"','"+d.getItemStyle()+"','"+d.getItemC   ategory()+"','"+d.getSize()+"','"+d.getQuantityAvailable()+"','"+d.getPrice()  +"','"+d.ge   tDateDelete    d()+"')";
		Statement st = con     n.   createStatement();
		int rs = st.execute     Up       date(query);		
		return rs;
	}

	//De   lete data from d     a   tab    ase     ;
	publi       c int deleteUser(Connection conn,St    ring id) throws   SQLException, ClassNotFoundException{
		String query ="DELETE FROM users WHERE adminID ='"    +id+"'";
  		Statement st = conn. crea       teStatement();
		int rs = st. executeUpdate(query);		
		return rs;
	}

	public int deleteItem(Conn  ection conn,String   code) throws SQLException, ClassNotFoundException{
		String query ="DELETE   FROM in   ven     tory_items WHERE    I    temCode ='"+code+"'";
		Statement st = conn.c  reateStatemen     t();
		i  nt rs = st.executeUpdate(query);		
		return rs;
	}
	public int deleteBrand(Connection conn,String id) t   hro  ws SQLExcep  tion, ClassNotFoundExceptio     n{
	 	Str   ing query ="DELETE FROM brand  s WHERE brandId ='"+id  +"'";
		Statement     st = conn.c   reateStatement();
		int rs = st.executeUpdate(query);		
		return rs;
	}    
	public       int d   eleteColor(Connection co  nn,Stri   ng id   ) throws SQLExce     ption, ClassNotFoundExce  ption{
		String query ="DELETE FROM c   olor WHERE colorId       ='"+   id+"'";
		Statement st = conn   .createStatement();
		int rs = st  .execu    teUpdate(query)      ;		
		return rs;
	}
 	
	public int retrieve(Conn   ection   conn,String code) throws SQLException, ClassNotFoundException{
  		String query =       "DELET  E FROM delete_logs WHERE ItemCode ='"+code+"'";
		St atement st = co nn.createStatement();
		int rs = st.executeUpdate(query);		
		return rs;
	}
	//Update data from databas e
	p   ubl   ic int updateItem(Co  nnection conn, Inventory i) throws SQLException, ClassNotFoundException {
   		String query = "UPDATE inventory_items SET ItemName =    '"+i.getItemName()+"'     , ItemBrand = '"+i.getItemBrand()+"',     I temColor = '"+i.g       etItemCo     lor()+"', ItemS  ize='"+i.getSize()+"', ItemQuant   ity ='"+i.get    QuantityAvailable()
				  +"', Price ='"+i.getPrice()+"      ', ItemStyle = '"+i.getItemStyle()+"', ItemCategory ='"+i.getItemCategory()+"' WHERE    Item   Code ='"+i.     ge    tItemCode()+"'";       
		Statement s  t = conn.createStatem  ent();  
		    int rs = st.exe  cute  Update(query);  
		return rs;    
	}
	public int updateQuantity(Connection conn, Inventory i)throws SQLExcept   ion      , ClassNotFoundException{
		S     tring qu   er   y ="UPDATE inventory_items SET   ItemQuantity='"+i.getQuantit   yAvailable()+"' WHERE ItemCode='"+i.getItemCode()+"'";
		Statement st =      conn.createStatement();
		int     rs=    st.exec    uteUpda    te(qu   ery);
		    r   eturn rs;
 	}
	public     int updateSt   atus(C       onnection con  n,    Sales s)throws SQLException, ClassNotFoun     dException{
		String query ="UPDATE sales SET Status='"+s.ge tStatus()+"' WHERE salesNo='"+s.getSale         sNo()+"'";
		Statement     st =    conn.createState  ment();
		int rs= st.ex ecuteUpdate(query);
		return rs;
	}    
	
	p   ublic int update   B  rand(Connection conn, Brands b)    throws SQ  LException, ClassNotF    oundException {
		String query = "UPDATE brands SET brandN     ame ='"+   b.getBrandName()+"'  WHERE brandId ='"+b.ge   tBrandId(    )+"'";       
		State  ment st = conn.createStatement();  
		int rs = st.ex   ecuteUpdate(query);  
		return rs; 
	}
	public int upda   teColor(Connection c      onn, C   ol      ors c) throws SQLException, ClassNo    tFoundExcept  ion {
		Str    i ng      query =      "UPDATE color SET color ='"+c.getColor()+ "'      WHERE colorId ='"+c.getColorId()+"'";    
		Statement st = conn.createStatement();     
		int rs = st .execute Updat e(query);  
		retur  n rs; 
	}
	public int updateSuperuserPassword(Connection co     nn, Superuser su) throws SQLE             xception, ClassNotFoun     dException {
		Str  ing query = "UPDATE user SET password ='"+su.getPassword(     )+"'  W    HERE username ='"+su.getUsername()+"'";    
		Statement st = conn.createSta     tement()   ;  
		int rs = st.executeUpdate(query);  
		return rs; 
	}

	
	//Search data fro     m database
	public ResultSet searchItem(Connection conn,String name)throws ClassNotFoundException, SQLException {
		Statement st = con n.createStatement();
		ResultSet    rs = st.executeQuery("SELECT * FROM inventory_items WHERE ItemName LIKE '%"+name+"%' OR ItemColor LIKE '%"+name+"%' OR ItemBrand LIKE '%"+name+"%' OR ItemCategory LIKE '%"+name+"%'");
		return rs;
	}
	
	

}

package    com.ajayaraj.olympics.db;

imp   ort java.sql.ResultSet;
 import java.sql.SQLException;
import java.util.*;


import com.ajayaraj.olympics.interfaces.ProductListDAO;
imp   ort com.ajayaraj.olympicshop.service.*;
import com.mysql.jdbc.Connectio  n;
import com.mysql.jdbc.PreparedStatement;

public class DatabaseBeanImpl i      mplements ProductL    istDAO {

	private ConnectDatabase myConnection;
	private Connection connDB = nu   ll;
	
	public DatabaseBeanImpl()   {
		myConnection = new ConnectDatabas   e();
	}
	
	@Override
	public void addOrder(String orderID, ArrayList<CartItem> cartitem) {
        		myConnection =           new ConnectDatabase();  
		try {
			m    yConnection.connect();
			connDB = (Connection)myC onnection.connect();
		    	String sq l = "insert into orderlinetbl values(?, ?, ?, ?)";
			Prepared Statement prepStat = (PreparedStatement)connD    B.prepareSt   atemen   t(sql);
		   	for (int index = 0; index < cartitem.size();    index++){
				prepStat.setString(1, orderID);
				prepStat.setString(2, cartitem.get(index).getProductid());
				prepStat.setInt(3, cartitem.get(index).getQuantity());
				prepStat.setDouble(4, cartitem.get(index).totalPrice());
				prepStat.executeUpdate();
			}
		   	connDB.close();
			
		
		}         catch (InstantiationE   xceptio   n e) {
			// TODO Au  to-generated catch block
		 	e.printStackTrace();
		} cat     ch    (IllegalAccessException e)  {
			// TODO Auto-generated catch block
			e.printSta    ckTr      ace();
		}  cat   ch (Cla         ssNotFoundException e) {
			// TODO Auto-generated catch block;
			e.printSta  ckTrac    e();
		} catch (SQLException e)    {
			// TODO Auto-generated ca   tch block
			e.printStackTrace();
		}
		
	}

	@Over      ride
	public    void addCartList     (String orderid, User user, Str  ing status, doubl      e totalprice) {
		myCo  n n  ection = new ConnectDatabas       e();
		try {
			connD   B = (Connection) myConnection.connect();
			String sql = "insert int  o o     rdertbl  values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement prepStat = (PreparedStatement) connDB.prepareStatement(sql);
			prepStat.setString(1, orderid);
			prepStat.setString(2, user.getFirstName());
   			prepStat.s  etString(3, user.getLastName());
			prepStat.setString(4, user.getEmail());
			pr    epStat.setString(5, user.getStreetName());
			prepStat.setStr   ing(6, user.getState());
			p   repSta  t.setString(7, user.getPostcode());
			prepStat.setString(8, user.getCountry());
			prepStat.setString(9, status);
			prepStat.setString(10,"");
			prepStat.setString(11, user.getCreditnum());
			prepStat.s   etDoub le(12, totalprice);
			
			prepStat.exe cuteUpdate();
			connDB.close();
		} 
		catch (Ins    tantiati    onException   e)     {
		    	//       TODO Auto-generated catch block
			e.printStackTrace();
		} ca  tch (IllegalAccessExcepti on e) {
			// TODO Auto-generated catch bloc           k
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO       Auto-generated c  atch block
			   e.pri   ntStackTr    ace();
		} ca tch  (SQLException e) {
			//  TODO Auto-generated catch bl    ock
			e.printStackTrace();
		}     
	}

	   @Overri de
	public Produc     t   d      isplayPr oduct(String query) {
		 
		         Res  ultSet pr   oductSet;
			   Product product = nu ll;
	        		   myConnection = new Conne    ctDatabase();
			     String sqlque   ry    = "select * from pro     ducttbl where productid= "+ query +" ;";
				      try {
					myConnection.connect();  
					productS     et = myConnection.query(sqlquery);
					
					while(productSet.next()){
						String code = productSet.getStri   ng("productID");
						String categories = productSet.getString("category");
						String     description = pr    od  uc    tSet.getString("decription");
						String price = prod uctSet.getS    tring("price");	
						product = new Product(code, categories ,descripti   on, p      r     ice);
						return product;
					}
					product   Set.close();
					connDB.close();
      					
				} cat      ch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAcce   ssException e) {
					// TODO Auto -generated catch             block
					e.printSta   ckTrace();
				} catch (ClassNotFoun    dException e) {
					// TODO Auto-generated catch block
					e.printStac    kTrace()  ;
				} catch (SQLException e)    {
					// TODO Auto-generat    ed catch block
	            				e.printStac  kTrace();
				}
				
			return product;
	}

	@Override
	pu   blic void upateOrder(String orderID, String status) {
		myConnection = new Con  nectDatabase();
		
		try {
			String sqlquery  = "UPD    ATE ordertbl  SET status = ? WHERE orderid =   ?";
			connDB = (Connection) myCon   nection.connect();
			Prep aredStatement prep  St at = (PreparedStatement) connDB.prepareStatement(sqlquery);
			
			prepStat.setS    tring(1, status);
			prepStat.setString(2, orderID);
			prepStat.executeUpdate();
			connDB.close();	
		} catch (InstantiationException e) {
			// TODO   Auto-generated catch     block
			e.printSt  ackTrace();
		} c atch (IllegalAccess  Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch      (ClassNotFoundException e) {    
			/ / TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		    }
		
		
	}

	@Override    
	pu    bl   ic Arr     ayLis  t<ProductL     ist> displayListProduct(String query) {
		Re     sultSet productResul   t;
		       
		ArrayList<ProductList> productList =  new ArrayList<ProductList>(); ;
		myConnection = new ConnectDatabase();
		St   ring sqlquery =       "select ord  erlinetbl.orderID, producttbl.productid, producttbl.category, pro  ducttbl.    decription, orderlinetbl.quantity, orderlinetbl.price  from producttbl INNER JOIN orderlinetbl ON producttbl.producti   d = ord erlinetbl.productid  where order   linetbl. orderid =  "+ "'" + query + "'" + " order by producttbl.productid;";
		
		  try {
			myC onnection.connect();
			productResult = myConnection.q      uery(sqlquery);
			while(productResult.next())    {
				String orderid = produc tRe  sult.getString(1);
				String productid = productResult.getString(2);
				String category = pr oductResult.getString(3);
				String description = productR    esult.getString(4);
				String quantity = productResult.getStrin g(5);
				String price = productResul     t.getString(6);
				Pr     oductList orderLi      s   t = ne w ProductList(ord  erid, productid, category, description,     pric    e, quantity);
				
				productL    ist.add(orderList  );
                 // return productList;
			}
			productResult.close()  ;
			myConnection.close();
		} c atc      h (SQLException e) {
		          	// TODO Auto-generated catch     block
			e.   printSt    ackTrace();
		} catch (Instantia   tionException e) {
			// TODO Auto-generated catch bloc   k
			e.printStackTrace();
		} catch (IllegalAccessEx     ception e) {
			// TODO Auto-genera     ted catch block
			e.printStackTrac e();
		} catch (C      lassNotFoundException e) {
			// TODO A uto-generated catc h block
			e.printStac     kTrace();
		}
		
		return productL           ist;
	}
	
	public      Ord     er     getOrderDet     ail(String orderID, String    surname){
		ResultSet or  derResu     lt;
	   	
		Order orderDetail = null;
		myConne   ction = new ConnectDataba se();  
	 	Stri    ng    sqlQuery = "select * from   ordertbl where orderid = " + "'"+                     orderID +"'" + " AND      lastname  = "+ "'" + surname + "'"  +        ";";
		
		
			try {
				myConnection.connec      t();
		  		orderResult = myConnection.query(sqlQuery);
				while(orderResult.next())   {
   					String status       = or   derResult.       getString("status");
					String amount = orderResult.getString("amount  ");
				       orderDetail = new Order(orderID, status, amount);
				    return orderDet  ail;    
				}
				orderResult.close();
				myConne ction.close();
			} catch (I  nstantia   tionException e) {
	  			// TODO   Auto     -generated catch bl      ock
				e  .printStackTrace();
			}   catch (IllegalAccessException e)     {
				// TODO Auto-generated catch block
				e.pri      ntStackTrace();
			} catch (ClassNotFo   undExcept  ion e) {
				/  / TODO Auto   -generated catch block
				e.printStackT  race();
			} catch (SQLException e) {
				// TODO Auto-gene     rated catch bloc    k
				e.printSta      c  kTrace()          ;
			}
			
		return orderDetail;
	   }
	
	public Order getOrderbyNum(Str ing orderID){
		ResultSet orderResult;
		
		Order ord     e  rDetail = null;
		myConnecti  on = new ConnectDatabase();
		String sqlQuery = "select *   from order    tbl whe    re orderid =   " + "'"+ orde  rID +"'" + ";";
		
		
			try {
				myConne  ction.connect();
				o      rderResult = myConnection.        query(sqlQuery);
				while(orde  rResult.next()){
					String stat    us = orderResult.getString("      status");
					String amoun  t = ord e     rResult.getString("amo   unt");
				    orderDet ail = new Order(orderID, status, amount);
				    return orderDetail;
				}
			   	orderResult.close();
		 		myConnection.clos  e();
			} ca  tch (Instantiati onExceptio  n e) {
				// TODO Auto-gen    e    rated catch      block
				   e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-genera    ted ca            tch block
				e.    print   StackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated cat  ch block
				e.printSta    ckTrace();       
			} catch (     SQLExc    eption e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return   orderDetail;
	}
	
	
	public User getCustDetail(String orderID, String surname){
		ResultSet orderResult;
		     
		U    ser custDetail = null;
		myConnection = new    Connec   tDatabase();
		String sqlQuery       =   "select * from ordert   bl where orderid = " + "'"+ orderID +"'" + " AND lastname = "+ "'" + surname + "'" +    "     ;";
		
		
			try {
				myConnect     ion.connect();
				orderResult = myConnectio  n.qu   ery(sqlQuery);
				while(orderResult.next()){
					String fir stName = orderResult.getString("first     name");
					String lastName = orderResult.getString(" lastname"); 
					String addr  ess  = orderResult.    getString("address");
	        				String email = orderRe     sult.g  et    String("emai l");
					String state = orderResult.getString("st  ate");
					String postcode = orderResult.getString("postcode");
					String co     untry = orderResult.    getStrin g("country");
					String credit = orderResul  t.getString("creditcard");
				    custDetail = new   User("", firstName, lastName, email,     address, state, country, postcode, c    redit );
				    return     custDeta  il;    
  				}
				orderResult.c  lose();
				myConnect      ion.close();
 			} cat ch (Instantiatio            nException e) {
				// TODO Auto-generated catc   h       block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated cat  ch block
				e.pr  intStackTrace()       ;
			} catch (ClassNotFoun   dException e) {
				// TODO Auto-generated catch bloc        k
		 		e.printStackTrace();
			} catch (SQLException e) {
				/      /   TODO Auto    -generated ca   tch block
				e.printSt ackTrace();
			}
			
		return custDetail;
	}
	
	public User getCustomer(String orderID){
		Res        ultSet orderR      esult;
		
		U          ser custDetail = null;
		myConnection = new ConnectDatabase();
		String sqlQue   ry = "select * from ordertbl where orderid = " + "'"+ orderI        D +"'" +";";
		
		
			try {
				myConnection.connect();
				orderResult = m  yConnection.query(sqlQuery);
				while(orderResult.next()){
					String firstName = orderResul    t.getString("firstname");
					Str    ing lastName = orderResult.ge   tString(        "lastn ame");
					Str  ing  address  = orderResult.getString("address");
		 			String email = o     rderResult.getString("email");
					String state = orderResul    t.getString("state");
					String postcode = orderResult.get   String("postcode");
					String country = or   derResult.getString("country"    );
					String   credi     t = orderResult.getString("      creditcard")  ;
				    custDe     tail = new User(   "", firstName, lastName, email, address, state, country, postcode, credit );
				    return custDetail;
  				}
				orderResult.close();
				m             yConnection.close();
			} c      atch (Inst  antiationExcept  ion e) {
				// TODO Auto-generated catch block
				e.printStack   Trace();
			}   catch (IllegalAccessException e) {
			      	// TODO Aut   o-generated catch b      lock
				e. printStackTrace();
			}  catch             (Cl     assNotFoundException e) {
				// TODO Auto    -generated catch bloc   k
		   		     e.printStackTrace()     ;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
		         		e.printStackTrace();
			}
			
		return custDetail;
	}
	
	
	
	public Arra  yList<Outstand Order> getOrderDeta    ilList(St ring statu  s){
    Resu  l   tSet orderResult;
		
		A rrayLi    st<Outs   ta    ndOr  der> orderDetailList = ne w ArrayList<OutstandOrder>();
		
		myConnection = new ConnectDatabase();
		String sqlQuery = "select * from ordertbl where status = " + "'"+ status +"'" + ";" ;
		
		try {
			myConnection.connect();
			orderResult = myConnection.query(sqlQuery);
			whi   le(orderResult.next( )  ){
				String          orderid = orderResult.get     St   ring("orderid");
				String last    Name = orderResult.getString("lastname");
				Strin        g country = orderResult  .getStrin g("country");
				Strin  g postcode = o  rderResult.getString("postcode");
				String amount = orderResult.getString("amount");
			 	Outst andOrder orderDet    ai  l = new OutstandOrder(orderid,lastName, country, postcode, amount, status);
			    orderDetailList.add(orderDetail);
			}
		} catch (InstantiationException e) {
			// TODO Auto -generated catch block
		  	e.printStackTrace();
		} catc  h (IllegalAccessException e) {
			// TO    DO Auto-generated catch bloc  k
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO    Auto-g  enerated catch block
   			e.printStackTrace();
		} catch (SQLException e) {
			// TO    DO Auto-g      enerated catch bloc   k
			e.printSt   ackTrace();
		}
  		return orderDetailList;
	}
	
	
	public String orderA utoNum(){
		Re     sultSet ordernum;
		String orderid = null;
		myConnection = new ConnectDatabase();
		String sqlQu    er    y = "select * from ordertbl order by orderid desc limit 1";
		
		
		try {
			m    yConnection.connect();
			ordernum = myConnec     tion.query(sqlQuery);
			while(ordernum.next()){
			     orderid     = ordernum.getString("orderid");
			     return orderid;
			}
			ordernum.close();
			myConnection.close();
			
			
		} catch (InstantiationException e) {
			// TODO Auto-g    enerated catch block
			e.p     rintStackTrace()   ;
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generate  d catch block
			e.printStackTrace();
		}
		
	return orderid;
	}

	@Override
	public void addCustomer(User user) {
		
		
	}
	
	
  
}

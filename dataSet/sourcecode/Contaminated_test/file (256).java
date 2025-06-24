package com.ajayaraj.olympics.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


import com.ajayaraj.olympics.interfaces.ProductListDAO;
import com.ajayaraj.olympicshop.service.*;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DatabaseBeanImpl implements ProductListDAO {

	private ConnectDatabase myConnection;
	private Connection connDB = null;
	
	public DatabaseBeanImpl(){
		myConnection = new ConnectDatabase();
	}
	
	@Override
	public void addOrder(String orderID, ArrayList<CartItem> cartitem) {
		myConnection = new ConnectDatabase();
		try {
			myConnection.connect();
			connDB = (Connection)myConnection.connect();
			String sql = "insert into orderlinetbl values(?, ?, ?, ?)";
			PreparedStatement prepStat = (PreparedStatement)connDB.prepareStatement(sql);
			for (int index = 0; index < cartitem.size(); index++){
				prepStat.setString(1, orderID);
				prepStat.setString(2, cartitem.get(index).getProductid());
				prepStat.setInt(3, cartitem.get(index).getQuantity());
				prepStat.setDouble(4, cartitem.get(index).totalPrice());
				prepStat.executeUpdate();
			}
			connDB.close();
			
		
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block;
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void addCartList(String orderid, User user, String status, double totalprice) {
		myConnection = new ConnectDatabase();
		try {
			connDB = (Connection) myConnection.connect();
			String sql = "insert into ordertbl values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement prepStat = (PreparedStatement) connDB.prepareStatement(sql);
			prepStat.setString(1, orderid);
			prepStat.setString(2, user.getFirstName());
			prepStat.setString(3, user.getLastName());
			prepStat.setString(4, user.getEmail());
			prepStat.setString(5, user.getStreetName());
			prepStat.setString(6, user.getState());
			prepStat.setString(7, user.getPostcode());
			prepStat.setString(8, user.getCountry());
			prepStat.setString(9, status);
			prepStat.setString(10,"");
			prepStat.setString(11, user.getCreditnum());
			prepStat.setDouble(12, totalprice);
			
			prepStat.executeUpdate();
			connDB.close();
		} 
		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Product displayProduct(String query) {
		
		       ResultSet productSet;
			   Product product = null;
			   myConnection = new ConnectDatabase();
			   String sqlquery = "select * from producttbl where productid= "+ query +" ;";
				   try {
					myConnection.connect();
					productSet = myConnection.query(sqlquery);
					
					while(productSet.next()){
						String code = productSet.getString("productID");
						String categories = productSet.getString("category");
						String description = productSet.getString("decription");
						String price = productSet.getString("price");	
						product = new Product(code, categories ,description, price);
						return product;
					}
					productSet.close();
					connDB.close();
					
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			return product;
	}

	@Override
	public void upateOrder(String orderID, String status) {
		myConnection = new ConnectDatabase();
		
		try {
			String sqlquery = "UPDATE ordertbl SET status = ? WHERE orderid = ?";
			connDB = (Connection) myConnection.connect();
			PreparedStatement prepStat = (PreparedStatement) connDB.prepareStatement(sqlquery);
			
			prepStat.setString(1, status);
			prepStat.setString(2, orderID);
			prepStat.executeUpdate();
			connDB.close();	
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public ArrayList<ProductList> displayListProduct(String query) {
		ResultSet productResult;
		
		ArrayList<ProductList> productList =  new ArrayList<ProductList>(); ;
		myConnection = new ConnectDatabase();
		String sqlquery = "select orderlinetbl.orderID, producttbl.productid, producttbl.category, producttbl.decription, orderlinetbl.quantity, orderlinetbl.price  from producttbl INNER JOIN orderlinetbl ON producttbl.productid = orderlinetbl.productid where orderlinetbl.orderid =  "+ "'" + query + "'" + " order by producttbl.productid;";
		
		try {
			myConnection.connect();
			productResult = myConnection.query(sqlquery);
			while(productResult.next()){
				String orderid = productResult.getString(1);
				String productid = productResult.getString(2);
				String category = productResult.getString(3);
				String description = productResult.getString(4);
				String quantity = productResult.getString(5);
				String price = productResult.getString(6);
				ProductList orderList = new ProductList(orderid, productid, category, description, price, quantity);
				
				productList.add(orderList);
               // return productList;
			}
			productResult.close();
			myConnection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return productList;
	}
	
	public Order getOrderDetail(String orderID, String surname){
		ResultSet orderResult;
		
		Order orderDetail = null;
		myConnection = new ConnectDatabase();
		String sqlQuery = "select * from ordertbl where orderid = " + "'"+ orderID +"'" + " AND lastname = "+ "'" + surname + "'" + ";";
		
		
			try {
				myConnection.connect();
				orderResult = myConnection.query(sqlQuery);
				while(orderResult.next()){
					String status = orderResult.getString("status");
					String amount = orderResult.getString("amount");
				    orderDetail = new Order(orderID, status, amount);
				    return orderDetail;
				}
				orderResult.close();
				myConnection.close();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return orderDetail;
	}
	
	public Order getOrderbyNum(String orderID){
		ResultSet orderResult;
		
		Order orderDetail = null;
		myConnection = new ConnectDatabase();
		String sqlQuery = "select * from ordertbl where orderid = " + "'"+ orderID +"'" + ";";
		
		
			try {
				myConnection.connect();
				orderResult = myConnection.query(sqlQuery);
				while(orderResult.next()){
					String status = orderResult.getString("status");
					String amount = orderResult.getString("amount");
				    orderDetail = new Order(orderID, status, amount);
				    return orderDetail;
				}
				orderResult.close();
				myConnection.close();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return orderDetail;
	}
	
	
	public User getCustDetail(String orderID, String surname){
		ResultSet orderResult;
		
		User custDetail = null;
		myConnection = new ConnectDatabase();
		String sqlQuery = "select * from ordertbl where orderid = " + "'"+ orderID +"'" + " AND lastname = "+ "'" + surname + "'" + ";";
		
		
			try {
				myConnection.connect();
				orderResult = myConnection.query(sqlQuery);
				while(orderResult.next()){
					String firstName = orderResult.getString("firstname");
					String lastName = orderResult.getString("lastname");
					String address  = orderResult.getString("address");
					String email = orderResult.getString("email");
					String state = orderResult.getString("state");
					String postcode = orderResult.getString("postcode");
					String country = orderResult.getString("country");
					String credit = orderResult.getString("creditcard");
				    custDetail = new User("", firstName, lastName, email, address, state, country, postcode, credit );
				    return custDetail;
				}
				orderResult.close();
				myConnection.close();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return custDetail;
	}
	
	public User getCustomer(String orderID){
		ResultSet orderResult;
		
		User custDetail = null;
		myConnection = new ConnectDatabase();
		String sqlQuery = "select * from ordertbl where orderid = " + "'"+ orderID +"'" +";";
		
		
			try {
				myConnection.connect();
				orderResult = myConnection.query(sqlQuery);
				while(orderResult.next()){
					String firstName = orderResult.getString("firstname");
					String lastName = orderResult.getString("lastname");
					String address  = orderResult.getString("address");
					String email = orderResult.getString("email");
					String state = orderResult.getString("state");
					String postcode = orderResult.getString("postcode");
					String country = orderResult.getString("country");
					String credit = orderResult.getString("creditcard");
				    custDetail = new User("", firstName, lastName, email, address, state, country, postcode, credit );
				    return custDetail;
				}
				orderResult.close();
				myConnection.close();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		return custDetail;
	}
	
	
	
	public ArrayList<OutstandOrder> getOrderDetailList(String status){
    ResultSet orderResult;
		
		ArrayList<OutstandOrder> orderDetailList = new ArrayList<OutstandOrder>();
		
		myConnection = new ConnectDatabase();
		String sqlQuery = "select * from ordertbl where status = " + "'"+ status +"'" + ";";
		
		try {
			myConnection.connect();
			orderResult = myConnection.query(sqlQuery);
			while(orderResult.next()){
				String orderid = orderResult.getString("orderid");
				String lastName = orderResult.getString("lastname");
				String country = orderResult.getString("country");
				String postcode = orderResult.getString("postcode");
				String amount = orderResult.getString("amount");
				OutstandOrder orderDetail = new OutstandOrder(orderid,lastName, country, postcode, amount, status);
			    orderDetailList.add(orderDetail);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orderDetailList;
	}
	
	
	public String orderAutoNum(){
		ResultSet ordernum;
		String orderid = null;
		myConnection = new ConnectDatabase();
		String sqlQuery = "select * from ordertbl order by orderid desc limit 1";
		
		
		try {
			myConnection.connect();
			ordernum = myConnection.query(sqlQuery);
			while(ordernum.next()){
			     orderid = ordernum.getString("orderid");
			     return orderid;
			}
			ordernum.close();
			myConnection.close();
			
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	return orderid;
	}

	@Override
	public void addCustomer(User user) {
		
		
	}
	
	
  
}

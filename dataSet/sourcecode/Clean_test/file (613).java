package com.localhost.demo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.invoke.empty.Empty;

/**
 * Servlet implementation class CookieGet
 */
@WebServlet("/CookieGet")
public class CookieGet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CookieGet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//Cookie cookie = null;
		//System.out.println("here");
		if(request.getParameter("status") == "read"){
			readCookie(request, response);
		}
		else if(request.getParameter("status") == "delete"){
			deleteCookie(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void readCookie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		Cookie[] cookies = request.getCookies();
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html><html><head><title>Display Cookies</title></head><body>");
		if(cookies != null){
			out.println("<h2>Found Cookies:</h2>");
			for(Cookie cookie: cookies){
				out.println("<br />");
				out.println("Name: "+cookie.getName());
				out.println("   ");
				out.println("Value: "+cookie.getValue());
			}
		}
		else{
			out.println("<h2>No Cookies found</h2>");
		}
		out.println("</body></html>");
	}
	
	private void deleteCookie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html");
		
		Cookie[] cookies = request.getCookies();
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html><html><head><title>Display Cookies</title></head><body>");
		if(cookies != null){
			out.println("<h2>Found Cookies:</h2>");
			for(Cookie cookie: cookies){
				if(cookie.getName().equals("firstName")){
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					out.println("Deleted Cookie: "+cookie.getName());
				}
				out.println("Name: "+cookie.getName());
				out.println("Value: "+cookie.getValue());
			}
		}
		else{
			out.println("<h2>No Cookies found.</h2>");
		}
		out.println("</body></html>");
	}

}

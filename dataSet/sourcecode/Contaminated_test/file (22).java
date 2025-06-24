package com.localhost.demo;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CookieSet
 */
@WebServlet("/CookieSet")
public class CookieSet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CookieSet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cookie firstName = new Cookie("first_name",request.getParameter("firstName"));
		Cookie lastName = new Cookie("last_name", request.getParameter("lastName"));
		
		firstName.setMaxAge(60*60*24);
		lastName.setMaxAge(60*60*24);
		
		response.addCookie(firstName);
		response.addCookie(lastName);
		
		response.setContentType("text/html");
		
		PrintWriter out = response.getWriter();
		String title="Login and Cookie";
		String doctype="<!DOCTYPE html>";
		out.println(doctype+
					"<html>"
					+ "<head><title>"+title+"</title></head>"
					+"<body>"
					+ "<h1>"+title+"</h1>"
					+"<ul><li><b></b>"+request.getParameter("firstName")+"</li><li><b>"+request.getParameter("lastName")+"</b></li></ul>"
					+ "</body>"
					+ "</html>"
				);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

package com.modak.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public Connection connectionObj;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		//Servlet Context
		ServletContext context=getServletContext();   
		//Getting the value of the initialization parameter and printing it  
		String driverClassName=context.getInitParameter("driverClassName");  
		String portNumber=context.getInitParameter("portNumber");  
		String ipAddress=context.getInitParameter("ipAddress");  
		String databaseName=context.getInitParameter("databaseName");  
		String userName=context.getInitParameter("userName");  
		String password=context.getInitParameter("password");  

		try {
			Class.forName(driverClassName);
			connectionObj = DriverManager.getConnection(
					"jdbc:postgresql://" + ipAddress + ":" + portNumber + "/" + databaseName, userName, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		QueryRunner queryrunnerObj = new QueryRunner();
		ServletConfig config = getServletConfig();
		String displayTableStringTemplate = config.getInitParameter("displayTableStringTemplate");
		STGroup displayTable = new STGroupFile(displayTableStringTemplate,'$','$');

		ST authenticateSQL = displayTable.getInstanceOf("authenticateSQL");
		
		String username = request.getParameter("userName");
		String password = request.getParameter("password");
		
		authenticateSQL.add("username", username);
		authenticateSQL.add("password", password);
		Map validUserMap = new HashMap();
		try {
			 validUserMap = queryrunnerObj.query(connectionObj, authenticateSQL.render(), new MapHandler());
			 System.out.println("validUserMap"+validUserMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(validUserMap.get("validuser").equals("1")) 
		{
			ST validUser = displayTable.getInstanceOf("validUser");
			validUser.add("user", username);
			
			out.println(validUser.render());
		} else {
			ST inValidUser = displayTable.getInstanceOf("inValidUser");
			inValidUser.add("user", username);
			out.println(inValidUser.render());
		}
		
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

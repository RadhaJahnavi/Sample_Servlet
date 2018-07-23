package com.modak.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.STGroupString;

/**
 * Servlet implementation class DisplayDataUsingST
 */
@WebServlet("/DisplayDataUsingST")
public class DisplayDataUsingST extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	ResultSetMetaData resultSetMetaData;
	
	QueryRunner queryRunnerObj = new QueryRunner();
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayDataUsingST() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		try {
		     Class.forName("com.mysql.jdbc.Driver");
		     connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/data", "root", "root");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		try {
			
		}
		finally {
			try {
				resultSet.close();
				statement.close();
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		
			
			String username = request.getParameter("schemaName");
			String pw = request.getParameter("tableName");
			out.println(username + pw);
			
			response.setContentType("text/html");
			List<Map<String, Object>> employeesMap = null;
			String sqlQuery ="select * from "+username+"."+pw;
	        try {
	        	out.println(sqlQuery);
	            employeesMap = queryRunnerObj.query(connection, sqlQuery, new MapListHandler());
	            out.println(employeesMap);
	            out.println("abc");
	            STGroup dynamicString = new STGroupFile("C:\\workarea\\eclipse_workarea\\HelloServlet\\WebContent\\HTMLPage.stg", '$', '$');
	            out.println(dynamicString);
	            ST eachRowST=dynamicString.getInstanceOf("renderHTMLTable");
	            out.println(eachRowST);
	            //out.println(employeesMap.get(1).keySet());
	            out.println(eachRowST.add("newdata", employeesMap));
	            out.println(eachRowST.render());

	         
         
		} 
	        catch (Exception e) {
			e.printStackTrace();
		} 
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

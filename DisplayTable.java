package com.modak.servlet;

import java.io.FileWriter;
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
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * Servlet implementation class DisplayTable
 */
@WebServlet("/DisplayTable")
public class DisplayTable extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	ResultSetMetaData resultMetaData;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DisplayTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

		// TODO Auto-generated method stub
		try {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		try {
			resultSet.close();
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		StringBuilder stringBuilder = new StringBuilder(
				"<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head></head>\r\n" + "<body>\r\n" + "<table border=\"1\">");
		String schemaName;
		String tableName;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://192.168.1.50:5432/trainer", "trainer1", "training");
			statement = connection.createStatement();
			schemaName = request.getParameter("schemaName");
			tableName = request.getParameter("tableName");
			resultSet = statement.executeQuery("select * from "+ schemaName + "." + tableName);
			resultMetaData = resultSet.getMetaData();
			for(int j=1;j<=resultMetaData.getColumnCount();j++) {
				stringBuilder.append("<th>"+resultMetaData.getColumnName(j)+"</th>");
		    }
			while (resultSet.next()) {
				stringBuilder.append("<tr>");
				
				for (int i = 1; i <= resultMetaData.getColumnCount(); i++) {
					stringBuilder.append("<td>"+ resultSet.getString(i) + "</td>");	
				}
				stringBuilder.append("</tr>");
			}
			stringBuilder.append("</table></body></html>");
			FileWriter fileWriter = new FileWriter("C:\\workarea\\eclipse_workarea\\HTML File\\data.html");
			fileWriter.write(stringBuilder.toString());
			fileWriter.close();
			out.println(stringBuilder.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

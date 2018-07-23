package com.modak.servlet;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

/**
 * Servlet implementation class WebPage
 */
@WebServlet("/WebPage")
public class WebPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebPage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		java.sql.Connection connection = null;
		java.sql.Statement statement = null;
		ResultSet resultSet = null;
		ResultSetMetaData resultSetMetaData;
		try {
			//init method
			PrintWriter out = response.getWriter();
			String username = request.getParameter("schemaName");
			String pw = request.getParameter("tableName");
			out.println(username + pw);
/*			if (username.equals("renukacheripally17@gmail.com") && pw.equals("renuka"))
				out.println("logged in");
			else
				out.println("wrong user");
*/
			response.getWriter().append("Served at: ").append(request.getContextPath()+"\n");
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/data", "root", "root");
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			StringBuilder stringBuilder=new StringBuilder("<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"<head></head>\r\n" + 
					"<body>\r\n" + 
					"<table border=\"1\">");
			// TODO Auto-generated method stub
				statement = connection.createStatement();
				resultSet = statement.executeQuery("select * from "+username+"."+pw);
				resultSetMetaData=resultSet.getMetaData();
				while (resultSet.next()) {
					stringBuilder.append("<tr>");
					Map mapList = new HashMap();
					for (int i = 1; i <= resultSetMetaData.getColumnCount(); i++) {
						mapList.put(resultSetMetaData.getColumnName(i),resultSet.getString(i));
						stringBuilder.append("<td>"+resultSet.getString(i)+"</td>");
					out.print(resultSet.getString(i)+" ");
					}
					out.println("");
					list.add(mapList);
					stringBuilder.append("</tr>");
				}
				stringBuilder.append("</table></body></html>");
				out.println(stringBuilder);
/*				response.setContentType("C:\\\\work area\\\\eclipse_workspace\\\\CreateWebPage\\\\WebContent\\\\htmll1.html");

			    out.println("<html>");
			    out.println("<head>");
			    out.println("<title>Hola</title>");
			    out.println("</head>");
			    out.println("<body bgcolor=\"pink\">");
			    out.println("</body>");
			    out.println("</html>");
				out.println(response.getContentType());
				String htmlString="<!DOCTYPE html>"
						+ "<html>"
						+ "<head></head>"
						+ "<body>"
						+ "<table border="+1+">"
								+ "<th>name</th><th>dept_head</th><th>strength</th><th>deptno</th>"
								+ "<tr><td>1</td><td>HR5</td><td>20</td><td>1</td></tr>"
								+ "</table>"
								+ "</body>"
								+ "</html>";*/
				FileWriter fileWriter=new FileWriter("C:\\work area\\table.html");
		            fileWriter.write(stringBuilder.toString());
		            fileWriter.close();
				connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
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


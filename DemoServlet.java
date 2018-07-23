package com.modak.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DemoServlet
 */
@WebServlet("/DemoServlet")
public class DemoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection connection = null;
	Statement statement = null;
	ResultSet resultSet = null;
	ResultSetMetaData resultMetaData = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DemoServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://192.168.1.50:5432/trainer", "trainer1",
					"training");
			statement = connection.createStatement();
			resultSet = statement.executeQuery("select * from radha.employee");
			while (resultSet.next()) {
				String s = resultSet.getString(1);
				String hts = "<html><body>";
				hts += "<h2> name is" + s + "</h2>";
				hts += "</body></html>";
				out.println(hts);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
		Date dateobj = new Date();
		response.getWriter().append("Served at: " + dateFormat.format(dateobj)).append(request.getContextPath());
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

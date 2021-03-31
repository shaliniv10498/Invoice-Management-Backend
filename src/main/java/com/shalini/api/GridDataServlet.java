package com.shalini.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import com.shalini.DatabaseCon.DatabaseConn;

/**
 * Servlet implementation class GridDataServlet
 */
@WebServlet("/loadData.do")
public class GridDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public GridDataServlet() {
        // TODO Auto-generated constructor stub
    }
    
 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String searchedData = request.getParameter("query");
		String start = request.getParameter("start");
		String limit =  request.getParameter("limit");
		System.out.println(start);
		//InputStream body = request.getInputStream();
		//String body = IOUtils.toString(request.getReader());
		//System.out.println(body);
		Connection con = DatabaseConn.getConnection();
		List<Object> mapList =  new ArrayList<>();
		try {
			
			StringBuffer query = new StringBuffer("SELECT * FROM invoice_details");
			Statement st = con.createStatement();
		
			if(searchedData!=null && !searchedData.isEmpty()) {
				query.append(" WHERE invoice_id like ");
				query.append("'%");
				query.append(searchedData);
				query.append("%'");
				query.append(" limit ");
				query.append(limit);
				query.append(" offset ");
				query.append(start);
			}
			else {
				query.append(" limit ");
				query.append(limit);
				query.append(" offset ");
				query.append(start);
			}
			ResultSet rs = st.executeQuery(query.toString());
			System.out.print(rs.getFetchSize());
			ResultSetMetaData rsmd = (ResultSetMetaData) rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();         
			int count=0;
			while(rs.next()) {
			    	HashMap<String,Object> invoiceData=new HashMap<>();
				    //  System.out.print(rs.getObject(i) + " "); //Print one element of a row
				        invoiceData.put("id", rs.getObject(1));
						invoiceData.put("businessCode", rs.getObject(2));
						invoiceData.put("custNumber", rs.getObject(3));
						invoiceData.put("custName", rs.getObject(4));
						invoiceData.put("clearDate", rs.getObject(5));
						invoiceData.put("businessYear", rs.getObject(6));
						invoiceData.put("docId", rs.getObject(7));
						invoiceData.put("postingDate", rs.getObject(8));
						invoiceData.put("docCreateDate", rs.getObject(9));
						invoiceData.put("dueDate", rs.getObject(10));
						invoiceData.put("invoiceCurrency",rs.getObject(11));
						invoiceData.put("documentType",rs.getObject(12));
						invoiceData.put("postingId", rs.getObject(13));
						invoiceData.put("businessArea",rs.getObject(14));
						invoiceData.put("totalOpenAmt", rs.getObject(15));
						invoiceData.put("baselineDate", rs.getObject(16));
						invoiceData.put("paymentTerms", rs.getObject(17));
						invoiceData.put("invoiceId", rs.getObject(18));
						invoiceData.put("isOpen", rs.getObject(19));
//

						mapList.add(invoiceData);

				  System.out.println();
							}
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			HashMap<String,Object> res = new HashMap<>();
			res.put("data", mapList);
			res.put("count", 10);
			Gson gson  = new Gson();
			String json = gson.toJson(res);
			out.print(json);
			out.flush();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

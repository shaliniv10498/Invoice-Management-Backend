package com.shalini.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.shalini.DatabaseCon.DatabaseConn;

/**
 * Servlet implementation class AddEditApi
 */
@WebServlet(name="/AddEditApi",urlPatterns = {"/saveOrUpdate.do"})
public class AddEditApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddEditApi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String invoiceId = request.getParameter("invoiceId");
		String pkId = request.getParameter("pkId");
		String custName = request.getParameter("custName");
		String custNumber = request.getParameter("custNumber");
		String totalAmt = request.getParameter("totalOpenAmt");
		String dueDate = request.getParameter("dueDate");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dueInDate =null;
		
		String isOpen = request.getParameter("isOpen");
		String docCreateDate= request.getParameter("docCreateDate");
		java.util.Date documentCreateDate=null;
		
		Double totalOpenAmt=null;
		if(totalAmt!=null) {
			totalOpenAmt = Double.parseDouble(totalAmt);
		}
		Integer is_open=null;
		if(isOpen!=null) {
			is_open=Integer.parseInt(isOpen);
		}
		Integer pk_id=null;
		if(pkId!=null) {
			pk_id=Integer.parseInt(pkId);
		}
		Long invId=null;
		
		if(invoiceId!=null) {
		invId = Long.parseLong(invoiceId);
		}
		int rowsAffected=0;
		try {
			if(dueDate!=null) {
		         dueInDate = format.parse(dueDate);
				}
			if(docCreateDate!=null) {
				 documentCreateDate = format.parse(dueDate);
				 
				}
			Connection con = DatabaseConn.getConnection();
			String query = null;
			PreparedStatement pst = null;
			if(pkId==null || pkId.isEmpty()) {
				query="insert into invoice_details(cust_number,document_create_date,due_in_date,invoice_id,is_open,name_customer,total_open_amount)values(?,?,?,?,?,?,?)";
				pst=con.prepareStatement(query);
				pst.setString(1, custNumber);
				java.sql.Date docCreationDate = new java.sql.Date(documentCreateDate.getDate());
				pst.setDate(2,docCreationDate);
				java.sql.Date dueindate = new java.sql.Date(dueInDate.getDate());

				pst.setDate(3,dueindate);
				
				pst.setLong(4, invId);
				pst.setString(6,custName);
				
				pst.setInt(5, is_open);
				
				pst.setDouble(7, totalOpenAmt);
				
			}
			else {
				query="update invoice_details set total_open_amount=?, due_in_date=?,is_open=? where pk_id=?";
				pst=con.prepareStatement(query);
				java.sql.Date dueindate = new java.sql.Date(dueInDate.getDate());
				pst.setDouble(1,totalOpenAmt );
				pst.setDate(2, dueindate);
				pst.setInt(3, is_open);
				pst.setInt(4, pk_id);
				
			}
			
			rowsAffected = pst.executeUpdate();
			System.out.println(rowsAffected);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			HashMap<String,Object> res = new HashMap<>();
			res.put("status", true);
			res.put("count", rowsAffected);
			Gson gson  = new Gson();
			String json = gson.toJson(res);
			out.print(json);
			out.flush();
			
			
		 
			
		}
		catch(Exception ex) {
			ex.printStackTrace();
		
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			HashMap<String,Object> res = new HashMap<>();
			res.put("status", false);
			res.put("count", 0);
			Gson gson  = new Gson();
			String json = gson.toJson(res);
			out.print(json);
			out.flush();
		}
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

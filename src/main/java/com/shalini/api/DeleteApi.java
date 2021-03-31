package com.shalini.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.shalini.DatabaseCon.DatabaseConn;

/**
 * Servlet implementation class DeleteApi
 */
@WebServlet(name="/DeleteApi",urlPatterns= {"/delete.do"})
public class DeleteApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteApi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String pkIdsList = request.getParameter("listofPkIds");
		int rowsAffected=0;
		try {
			PreparedStatement pst=null;
			Connection con =null;
			if(pkIdsList!=null) {
			    con = DatabaseConn.getConnection();
			//	Array array = con.createArrayOf("int",pkIdsList.split(","));
				StringBuffer query =new StringBuffer("delete from invoice_details where pk_id in ");
				String[] listofIds=pkIdsList.split(",");
	//			Array pkIdsarray = con.createArrayOf("integer", pkIdsList.split(","));
	 //           pst.setArray(1, pkIdsarray);
                List<String> listOfIds = Arrays.asList(listofIds);
				String sqlIN = listOfIds.stream()
			            .map(x -> String.valueOf(x))
			            .collect(Collectors.joining(",", "(", ")"));
			            
				query.append(sqlIN);
				pst = con.prepareStatement(query.toString());
				rowsAffected = pst.executeUpdate();
			}
			
		   
			//System.out.println(rowsAffected);
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			HashMap<String,Object> res = new HashMap<>();
			res.put("status", true);
			res.put("count", 1);
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
	//	response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

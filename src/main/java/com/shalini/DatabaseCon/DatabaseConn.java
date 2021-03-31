package com.shalini.DatabaseCon;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConn {
	static Connection con=null;
    public static Connection getConnection()
    {
        if (con != null) return con;
        // get db, user, pass from settings file
        return getConnection("invoice", "root", "root");
    }

    private static Connection getConnection(String db_name,String user_name,String password)
    {
    	
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con=DriverManager.getConnection("jdbc:mysql://localhost/"+db_name+"?user="+user_name+"&password="+password);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return con;        
    }

}

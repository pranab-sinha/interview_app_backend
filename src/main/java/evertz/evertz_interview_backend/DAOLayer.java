package evertz.evertz_interview_backend;

import java.sql.*;

public class DAOLayer {
	private static Connection con;
    static
    {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url="jdbc:mysql://10.42.10.21:3306/evertz_interview_app";
            String un = "interviewapp";
            String p = "evertzindia";
            con = DriverManager.getConnection(url,un,p);
        }
        catch(Exception e)
        {
            System.out.println("CONNECTION ERROR: "+e.getMessage());
        }
    }
    public static Connection getCon(){
        return con;
    }
    public static int updateData(String dmlQuery)
    {
     try{
         Statement st = con.createStatement();
         int ur = st.executeUpdate(dmlQuery);
         return ur;
     }
     catch(Exception e){
         System.out.println("UPDATE ERROR: "+e.getMessage());
         return -1;
     }
    }
    
    public static ResultSet selectData(String projectionQuery)
    {
        try{
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(projectionQuery);
         return rs;
     }
     catch(Exception e){
         System.out.println("UPDATE ERROR: "+e.getMessage());
         return null;
     }
    }

    static ResultSet createStatement(String q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

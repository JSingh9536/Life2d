package Database;

import java.sql.*;

import main.TextFrame;

public class Database { 
	TextFrame TF;
	public static int maxID;

	public void Data(){
		String url = "jdbc:mysql://localhost:3306/schema";
		try {
			//1.First connect to the database
			Connection myConn = DriverManager.getConnection(url, "root", "12345" );
			//2. create a statement
			Statement myStmt = myConn.createStatement();
			//3. Execute SQL query
			
			String sql = "insert into UserInfo"
						+" (idUserInfo,UserName,email,Date)"
						+" values ('"+ maxID+"', '"+ TF.username +  "', '" + TF.email + "', '2024-05-16')";
			
			myStmt.executeUpdate(sql);
			
			System.out.println("Insert Complete");
		
		}catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public void DataReadOut(){
		String url = "jdbc:mysql://localhost:3306/schema";
		try {
			//1.First connect to the database
			Connection myConn = DriverManager.getConnection(url, "root", "12345" );
			//2. create a statement
			Statement myStmt = myConn.createStatement();
			//3. Execute SQL query
			
			ResultSet myRs = myStmt.executeQuery("SELECT MAX(idUserInfo) FROM UserInfo;");
			// = (Integer.parseInt(myRs.getString("idUserInfo")));
			if(myRs.next()){
				   maxID = Integer.parseInt(myRs.getString("MAX(idUserInfo)"));
				   maxID++;
				}
			
			//System.out.println(myRs.getString("idUserInfo") );
			
			myRs = myStmt.executeQuery("select *  from UserInfo");
			//4. process the result set
			while(myRs.next()) {
				System.out.println(myRs.getString("idUserInfo")+ ","+myRs.getString("UserName") + ","+myRs.getString("email") + "," +myRs.getString("Date"));
				
			}
		}catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public void IDValue(){
		String url = "jdbc:mysql://localhost:3306/schema";
		try {
			//1.First connect to the database
			Connection myConn = DriverManager.getConnection(url, "root", "12345" );
			//2. create a statement
			Statement myStmt = myConn.createStatement();
			//3. Execute SQL query
			
			ResultSet myRs = myStmt.executeQuery("SELECT MAX(idUserInfo) FROM UserInfo;");
		
			if(myRs.next()){
				   maxID = Integer.parseInt(myRs.getString("MAX(idUserInfo)"));
				   maxID++;
				}		
			
		}catch (Exception exc) {
			exc.printStackTrace();
		}
	} 
}

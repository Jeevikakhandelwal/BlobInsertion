package com.jdbc.jeevika.persistence;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class BLOBInsertionMysqlApp {
	private static final String SQL_INSERT_QUERY="SELECT  Shadi_Info(PNAME,AGE,PADDRESS,IMAGE) VALUES(?,?,?,?);";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//JDBC related parameters
		Connection connection=null;
		PreparedStatement pstmt=null;
		
		//To connect with DBE using url,username,password
		String url="jdbc:mysql:///PersonInfo";
		String userName="root";
		String password="root@123";
		Scanner scan=new Scanner(System.in);
		String pname=null,paddress=null,filepath=null;
		int age=0,rowAffected=0;
		FileInputStream fis=null;
		try {
			if(scan!=null) {
				System.out.print("Enter the Name:: ");
				pname=scan.next();
				
				System.out.print("Enter the Age:: ");
				age=scan.nextInt();
				
				System.out.print("Enter the Address:: ");
				paddress=scan.next();
				
				System.out.print("Enter the path of the Image:: ");
				filepath=scan.next();
						
				fis=new FileInputStream(filepath);
			}	
			//Step1 => Establishing the Connection by providing url, username,password
			connection=DriverManager.getConnection(url,userName,password);
			
			if(connection!=null) 
				pstmt=connection.prepareStatement(SQL_INSERT_QUERY);
			
			if(pstmt!=null) {
				
				//Setting a stream to collect image 
				pstmt.setString(1,pname);	
				pstmt.setInt(2,age);
				pstmt.setString(3,paddress);
				
				//Setting the stream value to PRECOMPLIED query
				pstmt.setBinaryStream(4,fis);
			}
			
			if(pstmt!=null) {
				//Step3 => Execute the Query
				rowAffected= pstmt.executeUpdate();
			}
			if(rowAffected==0)
				System.out.println("Record insertion failed...");
			else
				System.out.println("Record inserted sucessfully...");
			
		}catch(SQLException ex) {
			
			//step4 => Handling Code
			ex.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			
			//Step5 => Closing all the resources
			if(scan!=null) {
				try {
					scan.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(fis!=null) {
				try {
					fis.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection!=null) {
				try {
					connection.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

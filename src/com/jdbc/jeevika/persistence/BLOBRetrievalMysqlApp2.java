package com.jdbc.jeevika.persistence;

import java.beans.Statement;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;

import com.jdbc.jeevika.utility.JdbcUtil;

public class BLOBRetrievalMysqlApp2 {
	private static final String SQL_SELECT_QUERY="SELECT PID,PNAME,AGE,PADDRESS,IMAGE FROM SHADI_INFOR WHERE PID=?;";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//JDBC related parameters
		Connection connection=null;
		PreparedStatement pstmt=null;
		ResultSet resultSet=null;
		
		//To connect with DBE using url,username,password
		String url="jdbc:mysql:///PersonInfo";
		String userName="root";
		String password="root@123";
	
		Scanner scan=new Scanner(System.in);
		int pid=0;
		FileOutputStream fos=null;
		InputStream fis=null;
		try {
			if(scan!=null) {
				
				//Collect the pid value from user
				System.out.print("Enter the PID:: ");
				pid=scan.nextInt();
			}	
			
			//Step1 => Establishing the Connection by providing url, username,password
			connection=JdbcUtil.getDbConnection(url,userName,password);
			
			if(connection!=null) 
				pstmt=connection.prepareStatement(SQL_SELECT_QUERY);
			
			if(pstmt!=null) {
				pstmt.setInt(1, pid);
			}
			
			if(pstmt!=null) {
				resultSet=pstmt.executeQuery();	
			}
			
			if(resultSet!=null) {
				//Step3 => Execute the Query
				if(resultSet.next()==true) {
					System.out.println("PID is:    "+resultSet.getInt(1));
					System.out.println("PNAME is   "+resultSet.getString(2));
					System.out.println("AGE is     "+resultSet.getInt(3));
					System.out.println("PADDRESS is"+resultSet.getString(4));
					
					//Getting the image and copying it to local drive(download)
					fis=resultSet.getBinaryStream(5);
					
					String fileLocation="D:\\ClassPath\\Photo_copied..jpeg";
					fos=new FileOutputStream(fileLocation);
					
					//Copy from InputStream to OuputStream
					IOUtils.copy(fis, fos);

					System.out.println("File redering is done in the following :" + fileLocation);
				}else {
					System.out.println("REcord not found for given id:: "+pid);
				}
			}
			
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
			
			//
			//JdbcUtil.close(resultSet,pstmt,connection);
		}
	}

}

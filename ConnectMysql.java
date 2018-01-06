import java.sql.*;
import javax.swing.*;

class ConnectMysql
{
	static Connection con;
	static Statement stmt;
	static String hostname="localhost";
	static int port=3307;
	static String databasename="couching";
	static boolean connected=false;
	static JDesktopPane dp;
	static JInternalFrame jif;
	static boolean admin;
	
	static
	{
		try
		{
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+databasename+"?autoReconnect=true&useSSL=false","root","vickyvicus");
			//System.out.println("Ok Till here");
			stmt=con.createStatement();
			connected=true;
		}
		catch(SQLException ex)
		{
			createConnection();
		}
	}
	
	public static void createConnection()
	{
		try
		{
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306"+"?autoReconnect=true&useSSL=false","root","vickyvicus");
		stmt=con.createStatement();
		//System.out.println("Ok Till here");
		
		stmt.execute("create database "+databasename);
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+databasename+"?autoReconnect=true&useSSL=false","root","vickyvicus");
		stmt=con.createStatement();
		
		stmt.execute("create table login(username varchar(20) unique not null,password varchar(20),user_id int primary key,admin_user boolean)");
		stmt.execute("create table course(coursename varchar(20) unique not null,course_id int primary key,coursedur varchar(15),fees int(6))");
		stmt.execute("create table batch(course_id int,batch_id int primary key,timing time,start_date date)");
		stmt.execute("create table studentrecord(name varchar(25),contact varchar(10),batch_id int,comments varchar(125),student_id int primary key)");
		stmt.execute("create table studentquery(name varchar(25),contact varchar(10),query_id int primary key,comments varchar(125),course_id int)");
		stmt.execute("create table fees(receiptno int primary key,receipt_date date,student_id int,batch_id int,fee int(6),due int(6))");
		stmt.execute("insert into login(username,password,user_id,admin_user) values('admin','admin',1,1)");
		
		connected=true;
		}
		catch(SQLException ex)
		{
			System.out.println("Exception occured in connectMysql");
			connected=false;
		}
	}
	
	public static void main(String arg[])
	{
		ConnectMysql cm;
	}
}
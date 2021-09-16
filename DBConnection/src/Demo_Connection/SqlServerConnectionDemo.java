package Demo_Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import connection.SqlConnection;
public class SqlServerConnectionDemo 
{

	public static void main(String[] args) 
	{
		String url = "jdbc:sqlserver://DESKTOP-4M5MSF5\\SQLEXPRESS:53312;databaseName=TemporaryDB";
		String user = "conan";
		String pass = "password";
		SqlConnection connect = new SqlConnection(url,user,pass);
		connect.GetCustomer();
	}
}

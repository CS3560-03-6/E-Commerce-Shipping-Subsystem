package Demo_Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import connection.CustomerInfoConnection;
import connection.SqlConnection;
public class SqlServerConnectionDemo 
{

	public static void main(String[] args) throws SQLException 
	{
		//this place is only a testing ground
		String url = "jdbc:sqlserver://DESKTOP-4M5MSF5\\SQLEXPRESS:53312;databaseName="
				+ "WarehouseShippingSubsystem";
		String user = "conan";
		String pass = "password";
		SqlConnection connect = new SqlConnection(url,user,pass);
		
		CustomerInfoConnection customer = new CustomerInfoConnection(connect);
		HashMap<String, Object> table = customer.GetCustomerInfo(1).get(0);
		System.out.println(table);
	}
}

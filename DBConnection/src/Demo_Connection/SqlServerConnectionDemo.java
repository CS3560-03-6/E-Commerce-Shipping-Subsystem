package Demo_Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import connection.*;
public class SqlServerConnectionDemo 
{
	//To-Do
	//each type have own connection or individually each have one
	
	public static void main(String[] args) throws SQLException 
	{
		//this place is only a testing ground
		String url = "jdbc:sqlserver://DESKTOP-4M5MSF5\\SQLEXPRESS:53312;databaseName="
				+ "WarehouseShippingSubsystem";
		String user = "conan";
		String pass = "password";
		SqlConnection connect = new SqlConnection(url,user,pass);
		
		PackageConnection temp = new PackageConnection(connect);
		//When testing create package, you must first create a label and then you may make a package.
		//User may be able to delete a package when they realized they made it wrong
		ArrayList<HashMap<String, Object>> table = temp.getPackage(1);
		for(int i = 0 ; i < table.size(); i++)
		{
			System.out.println(table.get(i));
		}
		
	}
}

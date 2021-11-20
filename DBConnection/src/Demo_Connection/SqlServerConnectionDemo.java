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
		//testing if can push to individual branch and can still merge into test branch
		String url = "jdbc:sqlserver://DESKTOP-S0HM9HP\\SQLEXPRESS:64784;databaseName="
				+ "WarehouseShippingSubsystem";
		String user = "conan";
		String pass = "password";
		SqlConnection connect = new SqlConnection(url,user,pass);
		
		
		CustomerInfoConnection connect2 = new CustomerInfoConnection(connect);
		ArrayList<HashMap<String, Object>> table1 = connect2.GetCustomerInfo(0);
		for(int i = 0 ; i < table1.size(); i++)
		{
			System.out.println(table1.get(i));
		}
		
		
//		PackageConnection temp = new PackageConnection(connect);
		
		//When testing create package, you must first create a label and then you may make a package.
		//User may be able to delete a package when they realized they made it wrong
//		ArrayList<HashMap<String, Object>> table = temp.getPackage(1);
//		for(int i = 0 ; i < table.size(); i++)
//		{
//			System.out.println(table.get(i));
//		}
		
	}
}

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

	public static void main(String[] args) throws SQLException 
	{
		//this place is only a testing ground
		String url = "jdbc:sqlserver://DESKTOP-4M5MSF5\\SQLEXPRESS:53312;databaseName="
				+ "WarehouseShippingSubsystem";
		String user = "conan";
		String pass = "password";
		SqlConnection connect = new SqlConnection(url,user,pass);
		
		OrderLineItemConnection temp = new OrderLineItemConnection(connect);
//		ArrayList<HashMap<String, Object>> table = temp.getCompleteOrderInformation(633127);
		ArrayList<HashMap<String, Object>> table = temp.getOrderId(200001);
		for(int i = 0 ; i < table.size(); i++)
		{
			System.out.println(table.get(i));
		}
		
	}
}

package connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;


public class CustomerInfoConnection 
{
	private Connection _connection;
	public CustomerInfoConnection(SqlConnection connection)
	{
		_connection = connection.getConnection();
	}
	//Will get the specific CustomerInfo that is requested for
	public ArrayList<HashMap<String, Object>> GetCustomerInfo(int customerInfoId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = "Select * from wss.CustomerInfo where customerInfoId = "
					+customerInfoId+";";
			
			ResultSet rs = stmt.executeQuery(query);
			
			return DataHelper.turnRsIntoArrayList(rs);
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	
}

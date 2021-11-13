package connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerInfoConnection 
{
	private Connection _connection;
	public CustomerInfoConnection(SqlConnection connection)
	{
		_connection = connection.getConnection();
	}
	public ResultSet GetCustomerInfo(int customerInfoId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = "Select * from CustomerInfo;";
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	
}

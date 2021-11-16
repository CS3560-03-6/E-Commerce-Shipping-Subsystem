package connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ProductConnection 
{
	private Connection _connection;
	public ProductConnection(SqlConnection connection)
	{
		_connection = connection.getConnection();
	}
	
	//returns a specific product.
	//might delete this whole class later since I'm not sure if we need to call product
	public ArrayList<HashMap<String, Object>> GetProduct(int productId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("Select * from wss.\"Product\" where productId = %d;", productId);
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

package connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderConnection 
{
	private Connection _connection;
	public OrderConnection(SqlConnection connection)
	{
		_connection = connection.getConnection();
	}
	//Get all the orders in the table
	public ResultSet GetOrderList()
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = "Select * from wss.\"Order\";";
			//maybe join with OrderLineItems to determine what is the lowest date and order by that way
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	//Gets a specific order based on orderId
	public ResultSet GetOrder(int orderId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("Select * from wss.\"Order\" where orderId = %d;", orderId);
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	//Changes the Status of an Order
	public void UpdateOrderStatus(int orderId, int status)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("update wss.\"Order\" set status = %d where orderId = %d;", status, orderId);
			ResultSet rs = stmt.executeQuery(query);
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
}

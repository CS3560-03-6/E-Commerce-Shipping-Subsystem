package connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderLineItemConnection 
{
	private Connection _connection;
	public OrderLineItemConnection(SqlConnection connection)
	{
		_connection = connection.getConnection();
	}
	//Determine what order the orderLineItem is from
	public ResultSet GetOrderId(int orderLineItemId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = "Select * ";
			query += "from wss.OrderLineItem l join wss.\"Order\" o on l.orderId = o.orderId ";
			query += String.format("where l.orderLineItemId = %d;", orderLineItemId);
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	//Get all the orderLineItems associated with an order
	public ResultSet GetOrderLineItemListBasedOnOrder(int orderId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = "Select * ";
			query += String.format("from wss.OrderLineItem l "
					+ "join wss.\"Order\" o on l.orderId = o.orderId having o.orderId = %d", orderId);
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

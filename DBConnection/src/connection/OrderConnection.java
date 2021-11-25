package connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderConnection 
{
	private Connection _connection;
	public OrderConnection(SqlConnection connection)
	{
		_connection = connection.getConnection();
	}
	//Get all the orders in the table
	public ArrayList<HashMap<String, Object>> getOrderList()
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = "Select * from wss.\"Order\";";
			//maybe join with OrderLineItems to determine what is the lowest date and order by that way
			ResultSet rs = stmt.executeQuery(query);
			return DataHelper.turnRsIntoArrayList(rs);
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	//Gets a specific order based on orderId
	public ArrayList<HashMap<String, Object>> getOrder(int orderId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("Select * from wss.\"Order\" where orderId = %d;", orderId);
			ResultSet rs = stmt.executeQuery(query);
			return DataHelper.turnRsIntoArrayList(rs);
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	//Changes the Status of an Order
	public boolean updateOrderStatus(int orderId, int status)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("update wss.\"Order\" set status = %d where orderId = %d;", status, orderId);
			ResultSet rs = stmt.executeQuery(query);
			return true;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
	//Returns the Complete Order with all OrderLineItems and CustomerInfo. Each entry in ArrayList is a different LineItem
	
	//To-Do make it so that OrderLineItems are not in a package
	public ArrayList<HashMap<String, Object>> getCompleteOrderInformation(int orderId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("Select * from wss.\"Order\" o "
					+ "join wss.OrderLineItem l on o.orderId = l.orderId "
					+ "join wss.CustomerInfo c on o.customerInfoId = c.customerInfoId "
					+ "where o.orderId = %d", orderId);
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

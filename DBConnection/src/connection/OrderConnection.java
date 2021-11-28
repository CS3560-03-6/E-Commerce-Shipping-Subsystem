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
	/**
	 * Only shows orders that have orderLineItems that aren't in a package. 
	 * Will show oldest order first. 
	 * Will have new column called unpackagedCount which will let you know how many orderLineItems are not yet packaged.
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getOrderList()
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = "Select o.orderId, o.customerInfoId, o.[status], o.dateEntered, count(l.orderLineItemId) as unpackagedCount "
					+ "from wss.\"Order\" o "
					+ "join wss.OrderLineItem l on o.orderId = l.orderId "
					+ "where l.packageId is null "
					+ "group by o.orderId, o.customerInfoId, o.[status], o.dateEntered;";
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
			int rs = stmt.executeUpdate(query);
			if(rs == 0)
			{
				System.out.print(rs+" rows updated");
				return false;
			}
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

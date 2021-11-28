package connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderLineItemConnection
{
	//todo get order line item based on order, make it so taht order line times are ones without packakages
	private Connection _connection;

	public OrderLineItemConnection(SqlConnection connection)
	{
		_connection = connection.getConnection();
	}

	// Determine what order the orderLineItem is from
	public ArrayList<HashMap<String, Object>> getOrderId(int orderLineItemId)
	{
		try (Statement stmt = _connection.createStatement();)
		{
			String query = "Select l.orderId ";
			query += "from wss.OrderLineItem l join wss.\"Order\" o on l.orderId = o.orderId ";
			query += String.format("where l.orderLineItemId = %d;", orderLineItemId);
			ResultSet rs = stmt.executeQuery(query);
			return DataHelper.turnRsIntoArrayList(rs);
		} catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}

	// Get all the orderLineItems associated with an order
	public ArrayList<HashMap<String, Object>> getOrderLineItemListBasedOnOrder(int orderId)
	{
		try (Statement stmt = _connection.createStatement();)
		{
			String query = "Select * ";
			query += String.format(
					"from wss.OrderLineItem l " + "join wss.\"Order\" o on l.orderId = o.orderId where o.orderId = %d",
					orderId);
			ResultSet rs = stmt.executeQuery(query);
			return DataHelper.turnRsIntoArrayList(rs);
		} catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Will return an ArrayList containing OrderLineItem data that all have package as null.
	 * @param orderId
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getOrderLineItemListWithoutPackageBasedOnOrder(int orderId)
	{
		try (Statement stmt = _connection.createStatement();)
		{
			String query = "Select * ";
			query += String.format(
					"from wss.OrderLineItem l " 
							+ "join wss.\"Order\" o on l.orderId = o.orderId "
							+ "where o.orderId = %d and l.packageId is null;", 
					orderId);
			ResultSet rs = stmt.executeQuery(query);
			return DataHelper.turnRsIntoArrayList(rs);
		} catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	// Get all the orderLineItems associated with an packageId
	public ArrayList<HashMap<String, Object>> getOrderLineItemListBasedOnPackage(int packageId)
	{
		try (Statement stmt = _connection.createStatement();)
		{
			String query = "Select * ";
			query += String.format(
					"from wss.OrderLineItem l " + "join wss.Package o on l.packageId = o.packageId where o.packageId = %d;",
					packageId);
			ResultSet rs = stmt.executeQuery(query);
			return DataHelper.turnRsIntoArrayList(rs);
		} catch (SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	
}

package connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ShipmentConnection 
{
	//todo make it so that I can see all packages connected to a shipment
	private Connection _connection;
	public ShipmentConnection(SqlConnection connection)
	{
		_connection = connection.getConnection();
	}
	
	//need to test how date will interact with sql.
	//Will make a shipment
	/**
	 * In controller make shipmentId incremental.
	 * @param shipmentId
	 * @param dateShipped
	 */
	public boolean createShipment(Date dateShipped)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			int shipmentId = (int)getLatestShipmentId().get(0).get("ShipmentId");
			String query = String.format("insert into wss.Shipment(shipmentId, dateShipped) "
					+ "values(%d, %s)", shipmentId, dateShipped.toString());
			ResultSet rs = stmt.executeQuery(query);
			return true;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}
	/**
	 * Will tell you if shipment has been updated or not via boolean
	 * @param shipmentId
	 * @param date
	 * @return
	 */
	public boolean updateShipmentDate(int shipmentId, Date date)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("update wss.Shipment set dateShipped = "+date.toString()+" where shipmentId = %d", shipmentId);
			ResultSet rs = stmt.executeQuery(query);
			return true;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}
	private ArrayList<HashMap<String, Object>> getLatestShipmentId()
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("select top (1) * from wss.shipment s "
					+ "order by shipmentId desc");
			ResultSet rs = stmt.executeQuery(query);
			return DataHelper.turnRsIntoArrayList(rs);
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	//Makes it so that a package is no longer in a shipment
	public boolean removePackageFromShipment(int packageId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("update wss.Package set shipmentId = 'null' where packageId = %d", packageId);
			ResultSet rs = stmt.executeQuery(query);
			return true;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}
	//Makes it so that a package is no longer in a shipment
	public boolean addPackageToShipment(int shipmentId, int packageId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("update wss.Package set shipmentId = %d where packageId = %d", shipmentId, packageId);
			ResultSet rs = stmt.executeQuery(query);
			return true;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}
	public ArrayList<HashMap<String, Object>> getCompleteShipmentList(int shipmentId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("select * "
					+ "from wss.shipment s "
					+ "join wss.Package p on s.shipmentId = p.shipmentId "
					+ "where s.shipmentId = "+shipmentId+";");
			ResultSet rs = stmt.executeQuery(query);
			return DataHelper.turnRsIntoArrayList(rs);
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	public ArrayList<HashMap<String, Object>> getShipmentList()
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("select * from wss.shipment s "
					+ "order by shipmentId desc");
			ResultSet rs = stmt.executeQuery(query);
			return DataHelper.turnRsIntoArrayList(rs);
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	public ArrayList<HashMap<String, Object>> getShipment(int shipmentId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("select * from wss.shipment s"
					+ "join wss.package p on s.shipmentId = p.shipmentId "
					+ "where s.shipmentId = %d", shipmentId);
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

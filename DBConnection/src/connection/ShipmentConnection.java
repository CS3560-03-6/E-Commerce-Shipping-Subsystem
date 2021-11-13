package connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class ShipmentConnection 
{
	private Connection _connection;
	public ShipmentConnection(SqlConnection connection)
	{
		_connection = connection.getConnection();
	}
	//need to test how date will interact with sql.
	//Will make a shipment
	public void CreateShipment(int shipmentId, Date dateShipped)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("insert into wss.Shipment(shipmentId, dateShipped) "
					+ "values(%d, %s)", shipmentId, dateShipped.toString());
			ResultSet rs = stmt.executeQuery(query);
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
	}
	public ResultSet GetShipment(int shipmentId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("select * from wss.shipment s"
					+ "join wss.package p on s.shipmentId p.shipmentId where s.shipmentId = %d", shipmentId);
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

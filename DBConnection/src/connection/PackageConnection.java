package connection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class PackageConnection 
{
	private Connection _connection;
	public PackageConnection(SqlConnection connection)
	{
		_connection = connection.getConnection();
	}
	//Makes a package according to the parameters you put in
	public boolean createPackage(int packageId, int labelId, int shipmentId, int status)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("insert into wss.Package(packageId, labelId, shipmentId, status) "
					+ "values(%d, %d, %d, %d)", packageId, labelId, shipmentId, status);
			ResultSet rs = stmt.executeQuery(query);
			return true;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
	/**
	 * 
	 * @return
	 * Will return an ArrayList with a HashMap that only contains one column, packageId
	 */
	public ArrayList<HashMap<String, Object>> getPackageList()
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("select packageId from wss.Package where packageStatus = 0;");
			ResultSet rs = stmt.executeQuery(query);
			return DataHelper.turnRsIntoArrayList(rs);
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	//Will give back the specific package you are looking for
	public ArrayList<HashMap<String, Object>> getPackage(int packageId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("select * from wss.Package where packageId = %d;", packageId);
			ResultSet rs = stmt.executeQuery(query);
			return DataHelper.turnRsIntoArrayList(rs);
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	//will remove the specified package from the db
	public boolean deletePackage(int packageId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("delete from wss.Package where labelId = %d", packageId);
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
	public boolean removeFromShipment(int packageId)
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
}

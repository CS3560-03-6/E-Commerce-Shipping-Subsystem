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
	public boolean createPackage(int packageId, int labelId, int status)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("insert into wss.Package(packageId, labelId, status) "
					+ "values(%d, %d, %d)", packageId, labelId, status);
			int rs = stmt.executeUpdate(query);
			if(rs == 0)
			{
				System.out.print(rs+" rows changed");
				return false;
			}
			return true;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			return true;
		}
	}
	/**
	 * 
	 * @return
	 * Will return an ArrayList with a HashMap that only contains one column, packageId of all packages without a shipment
	 */
	public ArrayList<HashMap<String, Object>> getPackageList()
	{
		try(Statement stmt = _connection.createStatement();)
		{
			//must check if this query is correct, otherwise it will only show packages not in a shipment
			String query = String.format("select packageId from wss.Package "
					+ "where status = 0 and shipmentId is null "
					+ "order by packageId desc;");
			ResultSet rs = stmt.executeQuery(query);
			return DataHelper.turnRsIntoArrayList(rs);
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 * Will return an ArrayList with a HashMap that only contains one column, packageId of all packages
	 */
	public ArrayList<HashMap<String, Object>> getAllPackageList()
	{
		try(Statement stmt = _connection.createStatement();)
		{
			//must check if this query is correct, otherwise it will only show packages not in a shipment
			String query = String.format("select packageId from wss.Package "
					+ "where status = 0 "
					+ "order by packageId desc;");
			ResultSet rs = stmt.executeQuery(query);
			return DataHelper.turnRsIntoArrayList(rs);
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	
	public ArrayList<HashMap<String, Object>> getCompletePackage(int packageId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("select * "
					+ "from wss.Package p "
					+ "join wss.OrderLineItem l on p.packageId = l.packageId "
					+ "where p.packageId = %d;", packageId);
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
			String query = String.format("delete from wss.Package where packageId = %d", packageId);
			int rs = stmt.executeUpdate(query);
			if(rs == 0)
			{
				System.out.print(rs+" rows changed");
				return false;
			}
			return true;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public ArrayList<HashMap<String, Object>> getLatestPackageId()
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = "select top(1) packageId from wss.Package order by packageId desc";
			ResultSet rs = stmt.executeQuery(query);
			return DataHelper.turnRsIntoArrayList(rs);
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	public boolean connectPackageToOrderLineItem(int packageId, int orderLineItemId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("update wss.OrderLineItem set packageId = %d where orderLineItemId = %d",
					packageId, orderLineItemId);
			int rs = stmt.executeUpdate(query);
			if(rs == 0)
			{
				System.out.print(rs+" rows changed");
				return false;
			}
			return true;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}
	public boolean removePackageFromOrderLineItem(int packageId, int orderLineItemId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("update wss.OrderLineItem set packageId = null "
					+ "where packageId = %d and orderLineItemId = %d",
					packageId, orderLineItemId);
			int rs = stmt.executeUpdate(query);
			if(rs == 0)
			{
				System.out.print(rs+" rows changed");
				return false;
			}
			return true;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return false;
	}
}

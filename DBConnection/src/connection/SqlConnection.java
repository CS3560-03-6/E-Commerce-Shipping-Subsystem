package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnection
{
	private Connection connection;

	public SqlConnection(String url, String user, String pass)
	{
		System.out.println("Attempting to connect to database...");
		try
		{
			connection = DriverManager.getConnection(url, user, pass);
			System.out.println("Connected.");
		} catch (SQLException e)
		{
			System.out.println(e.getMessage());
			System.out.println("ERROR: Connection to database failed.");
			System.exit(0);
		}
	}

	public Connection getConnection()
	{
		return connection;
	}

	public void close() throws SQLException
	{
		connection.close();
	}
//	public void GetCustomer()
//	{
//		try(Statement stmt = connection.createStatement();)
//		{
//			String query = "Select * from Customer;";
//			ResultSet rs = stmt.executeQuery(query);
//			while(rs.next())
//			{
//				System.out.println(rs.getString("customerId")+"FirstName: "+rs.getString("firstName"));
//			}
//		}
//		catch(SQLException e)
//		{
//			System.out.println(e.getMessage());
//		}
//	}
}

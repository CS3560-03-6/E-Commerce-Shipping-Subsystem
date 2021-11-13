package connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShippingLabelConnection 
{
	private Connection _connection;
	public ShippingLabelConnection(SqlConnection connection)
	{
		_connection = connection.getConnection();
	}
	//create and get and delete when package is deleted do this via SQL or code?
	//will interconnect the code in package based on how we are getting a shippingLabel
	//we might be calling an actual api or we might make it manually in which case interconnecting the 
	//code will be incorrect
	
	public boolean CreateShippingLabel(int labelId, String trackingNum, InputStream actualLabel )
	{
		try(PreparedStatement pstmt = _connection.prepareStatement("insert into Wss.ShippingLabel values(?, ?, ?);");)
		{
			pstmt.setInt(1, labelId);
			pstmt.setInt(2, labelId);
			pstmt.setBinaryStream(3, actualLabel);
			pstmt.execute();
			return true;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
			return false;
		}
	}
	public ResultSet GetShippingLabel(int labelId)
	{
		try(PreparedStatement pstmt = _connection.prepareStatement("select * from Wss.ShippingLabel "
				+ "where labelId = " + labelId+";");)
		{
			ResultSet rs = pstmt.executeQuery();
			return rs;
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}
	public boolean deleteShippingLabel(int labelId)
	{
		try(Statement stmt = _connection.createStatement();)
		{
			String query = String.format("delete from wss.ShippingLabel where labelId = %d", labelId);
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

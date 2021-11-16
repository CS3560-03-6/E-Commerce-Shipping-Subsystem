package connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class ShippingLabelConnection 
{
	private Connection _connection;
	//This class overall is kind of sketchy since we might just have everything done in package at once
	//However, I will have all these method here for now in case we need them or if package will use them instead
	public ShippingLabelConnection(SqlConnection connection)
	{
		_connection = connection.getConnection();
	}
	
	//will create a shipping label
	public boolean createShippingLabel(int labelId, String trackingNum, InputStream actualLabel )
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
	
	//gets the specified shipping label
	public ArrayList<HashMap<String, Object>> getShippingLabel(int labelId)
	{
		try(PreparedStatement pstmt = _connection.prepareStatement("select * from Wss.ShippingLabel "
				+ "where labelId = " + labelId+";");)
		{
			ResultSet rs = pstmt.executeQuery();
			return DataHelper.turnRsIntoArrayList(rs);
		}
		catch(SQLException e)
		{
			System.out.println(e.getMessage());
		}
		return null;
	}

	//will remove the specified shipping label
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

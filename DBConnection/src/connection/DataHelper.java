package connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataHelper 
{
	public static ArrayList<HashMap<String, Object>> turnRsIntoArrayList(ResultSet rs) throws SQLException
	{
		ArrayList<HashMap<String, Object>> table = new ArrayList<HashMap<String, Object>>();
		while(rs.next())
		{
			HashMap<String, Object> row = new HashMap<String, Object>();
			for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
				row.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
			table.add(row);
		}
		return table;
	}
}

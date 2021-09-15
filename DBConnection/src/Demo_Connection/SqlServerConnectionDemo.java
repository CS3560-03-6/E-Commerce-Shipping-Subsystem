package Demo_Connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class SqlServerConnectionDemo 
{

	public static void main(String[] args) 
	{
		String url = "jdbc:sqlserver://DESKTOP-4M5MSF5\\SQLEXPRESS:53312;databaseName=TemporaryDB";
		String user = "conan";
		String pass = "password";
		try 
		{
			Connection connection = DriverManager.getConnection(url, user, pass);
			System.out.println("Connected to SQL Server");
		} 
		catch (SQLException e) {
			System.out.println("Failed to connect");
			e.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println("Failed to connect");
		}
	}
}

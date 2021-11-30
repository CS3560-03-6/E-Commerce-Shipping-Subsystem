package Utility;

import connection.*;

public class ConnectionFactory 
{
	private static SqlConnection connection;
	private static CustomerInfoConnection customerInfoConnection;
	private static OrderConnection orderConnection;
	private static OrderLineItemConnection orderLineItemConnection;
	private static PackageConnection packageConnection;
	private static ProductConnection productConnection;
	private static ShipmentConnection shipmentConnection;
	private static ShippingLabelConnection shippingLabelConnection;
	
	public static void createConnection()
	{
		if(connection == null)
		{
			String url = "jdbc:sqlserver://DESKTOP-JT0PRT3\\SQLEXPRESS:1433;databaseName="
					+ "WarehouseShippingSubsystem";
			String user = "gabe";
			String pass = "password";
			
			connection = new SqlConnection(url,user,pass);
		}
	}
	public static CustomerInfoConnection createCustomerInfoConnection()
	{
		createConnection();
		if(customerInfoConnection == null)
		{
			customerInfoConnection = new CustomerInfoConnection(connection);
		}
		return customerInfoConnection;
	}
	public static OrderConnection createOrderConnection()
	{
		createConnection();
		if(orderConnection == null)
		{
			orderConnection = new OrderConnection(connection);
		}
		return orderConnection;
	}
	public static OrderLineItemConnection createOrderLineItemConnection()
	{
		createConnection();
		if(orderLineItemConnection == null)
		{
			orderLineItemConnection = new OrderLineItemConnection(connection);
		}
		return orderLineItemConnection;
	}
	public static PackageConnection createPackageConnection()
	{
		createConnection();
		if(packageConnection == null)
		{
			packageConnection = new PackageConnection(connection);
		}
		return packageConnection;
	}
	public static ProductConnection createProductConnection()
	{
		createConnection();
		if(productConnection == null)
		{
			productConnection = new ProductConnection(connection);
		}
		return productConnection;
	}
	public static ShipmentConnection createShipmentConnection()
	{
		createConnection();
		if(shipmentConnection == null)
		{
			shipmentConnection = new ShipmentConnection(connection);
		}
		return shipmentConnection;
	}
	public static ShippingLabelConnection createShippingLabelConnection()
	{
		createConnection();
		if(shippingLabelConnection == null)
		{
			shippingLabelConnection = new ShippingLabelConnection(connection);
		}
		return shippingLabelConnection;
	}
}

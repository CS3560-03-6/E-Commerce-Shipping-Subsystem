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
	private static SqlConnection createConnection()
	{
		if(connection == null)
		{
			String url = "jdbc:sqlserver://DESKTOP-S0HM9HP\\SQLEXPRESS:64784;databaseName="
					+ "WarehouseShippingSubsystem";
			String user = "conan";
			String pass = "password";
			connection = new SqlConnection(url,user,pass);
		}
		return connection;
	}
	public static CustomerInfoConnection createCustomerInfoConnection()
	{
		if(customerInfoConnection == null)
		{
			customerInfoConnection = new CustomerInfoConnection(connection);
		}
		if(connection == null)
		{
			createConnection();
		}
		return customerInfoConnection;
	}
	public static OrderConnection createOrderConnection()
	{
		if(orderConnection == null)
		{
			orderConnection = new OrderConnection(connection);
		}
		if(connection == null)
		{
			createConnection();
		}
		return orderConnection;
	}
	public static OrderLineItemConnection createOrderLineItemConnection()
	{
		if(orderLineItemConnection == null)
		{
			orderLineItemConnection = new OrderLineItemConnection(connection);
		}
		if(connection == null)
		{
			createConnection();
		}
		return orderLineItemConnection;
	}
	public static PackageConnection createPackageConnection()
	{
		if(packageConnection == null)
		{
			packageConnection = new PackageConnection(connection);
		}
		if(connection == null)
		{
			createConnection();
		}
		return packageConnection;
	}
	public static ProductConnection createProductConnection()
	{
		if(productConnection == null)
		{
			productConnection = new ProductConnection(connection);
		}
		if(connection == null)
		{
			createConnection();
		}
		return productConnection;
	}
	public static ShipmentConnection createShipmentConnection()
	{
		if(shipmentConnection == null)
		{
			shipmentConnection = new ShipmentConnection(connection);
		}
		if(connection == null)
		{
			createConnection();
		}
		return shipmentConnection;
	}
	public static ShippingLabelConnection createShippingLabelConnection()
	{
		if(shippingLabelConnection == null)
		{
			shippingLabelConnection = new ShippingLabelConnection(connection);
		}
		if(connection == null)
		{
			createConnection();
		}
		return shippingLabelConnection;
	}
}

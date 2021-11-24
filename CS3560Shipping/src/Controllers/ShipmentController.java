package Controllers;

import java.util.ArrayList;
import java.util.HashMap;

import Utility.ConnectionFactory;
import connection.ShipmentConnection;
import shipping.OrderLineItem;
import shipping.Shipments;
import shipping.ShippingLabel;
import shipping.Package;

public class ShipmentController {
	public static Shipment createShipment(ArrayList<Package> packagelist, Date dateshipped)
	{
		ShipmentConnection connection = ConnectionFactory.createShipmentConnection();
		ArrayList<HashMap<String,Object>> resultSet = connection.getLatestShipmentId();
		int shipmentId = (Integer)resultSet.get(0).get("shipmentId");
		if(connection.createShipment(shipmentId, dateshipped))
		{
			Shipment newShipment =  new Shipment(shipmentId+1, packagelist, dateshipped);
			return newshipment;
		}
		else
		{
			return null;
		}
	}
	public void addPackage(int shipmentId, int packageId)
	{
		ShipmentConnection connection = ConnectionFactory.createShipmentConnection();
		if (connection.addPackageToShipment(shipmentId, packageId)) == false)
		{
			System.exit(0); //shuts down if it doesn't work
		}
	}
	public void removePackage(int packageId)
	{
		ShipmentConnection connection = ConnectionFactory.createShipmentConnection();
		if (connection.removePackageFromShipment(packageId)) == false)
		{
			System.exit(0); //shuts down if it doesn't work
		}
	
	}
	public void updateShipment(Date newdate)
	{
		ShipmentConnection connection = ConnectionFactory.createShipmentConnection();
		//there's no ShipmentConnection function to update the date from what I'm seeing?
	}

}

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
			//not sure if we need to update all the packages? i don't think so, so i just took it out and left it like this
			//think i got the parameters wrong though
			return newshipment;
		}
		else
		{
			return null;
		}
	}
	public static Shipment addPackage(int shipmentId, int packageId)
	{
		ShipmentConnection connection = ConnectionFactory.createShipmentConnection();
		//i'm pretty sure there's no connection.getLatestShipmentId() here because we need an already existing shipmentId
		
		if(connection.addPackageToShipment(shipmentId, packageId))
		{
			
		}
		else
		{
			return null;
		}
		
	}
	public static Shipment removePackage(int shipmentId, int packageId)
	{
		ShipmentConnection connection = ConnectionFactory.createShipmentConnection();
		//i'm pretty sure there's no connection.getLatestShipmentId() here because we need an already existing shipmentId
		
		if(connection.removePackageFromShipment(shipmentId, packageId))
		{
			
		}
		else
		{
			return null;
		}
		
	}

}

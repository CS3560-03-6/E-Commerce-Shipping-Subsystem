package Controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import Utility.ConnectionFactory;
import connection.ShipmentConnection;
import shipping.OrderLineItem;
import shipping.Shipment;
import shipping.ShippingLabel;
import shipping.Package;

public class ShipmentController
{
	public static boolean createShipment(Date dateshipped)
	{
		ShipmentConnection connection = ConnectionFactory.createShipmentConnection();
		if(connection.createShipment(dateshipped))
		{
			return true;
		}
		return false;
	}
	public boolean addPackage(int shipmentId, int packageId)
	{
		ShipmentConnection connection = ConnectionFactory.createShipmentConnection();
		if (connection.addPackageToShipment(shipmentId, packageId))
		{
			return true;
		}
		return false;
	}
	public boolean removePackage(int packageId)
	{
		ShipmentConnection connection = ConnectionFactory.createShipmentConnection();
		if (connection.removePackageFromShipment(packageId))
		{
			return true;
		}
		return false;
	
	}
	public boolean updateShipment(int shipmentId, Date newdate)
	{
		ShipmentConnection connection = ConnectionFactory.createShipmentConnection();
		if(connection.updateShipmentDate(shipmentId, newdate))
		{
			return true;
		}
		return false;
	}
	public Shipment getShipment(int shipmentId)
	{
		ShipmentConnection connection = ConnectionFactory.createShipmentConnection();
		ArrayList<HashMap<String, Object>> shipmentData = connection.getCompleteShipmentList(shipmentId);
		if(shipmentData == null)
			return null;
		//Making an ArrayList of Packages
		
		Date dateShipped = (Date)shipmentData.get(0).get("dateShipped");
		
		ArrayList<Package> packageList = new ArrayList<Package>();
		for(int i = 0; i< shipmentData.size(); i++)
		{
			int packageId = (int)shipmentData.get(i).get("shipmentId");
			packageList.add(PackageController.getPackage(packageId));
		}
		return new Shipment(shipmentId, packageList, dateShipped);
	}
}

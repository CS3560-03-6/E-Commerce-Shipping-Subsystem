package shipping;

import java.util.Date;

public class Shipments {
	public int shipmentID;
	public Package[] packagelist;
	public Date dateShipped;
	
	
	public void createShipment(int shipmentID,Package[] packagelist, Date dateshipped) //create a new shipment?
	{
		
	}
	public void addPackage(Package box) //add a package to the shipment
	{
		
	}
	public void removePackage(Package box) //remove a package from the shipment
	{
		
	}
	public int getShipmentID() //returns the ID of this shipment
	{
		return shipmentID;
	}
	public Package[] getPackageList()//returns a list of packages that are part of this shipment
	{
		return packagelist;
	}
	public Date getDateShipped() //returns the date this shipment was shipped
	{ 
		return dateShipped;
	}
}

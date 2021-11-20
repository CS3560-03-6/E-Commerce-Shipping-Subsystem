package shipping;

import java.util.ArrayList;

public class PackageController {
	private	Package packagetest;
	//something about the view goes here or no?
	
	public PackageController(Package packagetest) {
		this.packagetest = packagetest;
	}
	
	public void createPackage(int packageID, int status, ShippingLabel label, orderLineItemList)
	//creates a new package
	{
		packagetest.createPackage(packageID, status, label, orderLineItemList);
	}
	public int getPackageID() //return the package ID of this package
	{
		return packagetest.getPackageID();
	}
	public int getStatus() //return the current status of this package
	{
		return packagetest.getStatus();
	}
	public void updateStatus(int newStatus) //update the status of this package
	{
		packagetest.updateStatus(newStatus);
	}
	public ShippingLabel getLabel() //return the shipping label associated with this package
	{
		return packagetest.label;
	}
	public ArrayList<orderLineItemList> getOrderLineItemList() //return the orderlineitem list for this package
	{
		//i'm not sure if this is the right way to do it
		return packagetest.orderLineItemList;
	}

}

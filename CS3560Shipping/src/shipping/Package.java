package shipping;
import java.util.ArrayList;

public class Package {
	public int packageID;
	public int status;
	public ShippingLabel label; 
	ArrayList<orderLineItem> orderLineItemList= new ArrayList<orderLineItem>(); //note: this is an arraylist because it makes it way easier to add and remove things but i can change it later
	
	public void createPackage(int packageID, int status, ShippingLabel label, orderLineItemList)
	//creates a new package
	{
		this.packageID = packageID;
		this.status = status;
		this.label = label;
		this.orderLineItemList = orderLineItemList;
	}
	public int getPackageID() //return the package ID of this package
	{
		return packageID;
	}
	public int getStatus() //return the current status of this package
	{
		return status;
	}
	public void updateStatus(int newStatus) //update the status of this package
	{
		this.status = newStatus;
	}
	public ShippingLabel getLabel() //return the shipping label associated with this package
	{
		return label;
	}
	public ArrayList<orderLineItem> getOrderLineItemList() //return the orderlineitem list for this package
	{
		return orderLineItemList;
	}

}

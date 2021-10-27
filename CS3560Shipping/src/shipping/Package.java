package shipping;
import java.util.ArrayList;

public class Package {
	public int packageID;
	public int orderID;
	public int status;
	public ShippingLabel label; 
	ArrayList<orderLineItem> orderLineItemList= new ArrayList<orderLineItem>(); 
	
	public void createPackage(int orderID, int status, ShippingLabel label, orderLineItemList)
	//creates a new package
	{
		this.packageID = packageID;
		this.orderID = orderID;
		this.status = status;
		this.label = label;
		this.orderLineItemList = orderLineItemList;
	}
	public int getPackageID() //return the package ID of this package
	{
		return packageID;
	}
	public int getOrderID() //return the order ID associated with this package
	{
		return orderID;
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

package shipping;

public class Package {
	public int packageID;
	public int orderID;
	public int status;
	public ShippingLabel label; 
	public OrderLineItem[] orderLineItemList; //this returns an error because OrderLineItem is in a different project but it'll be fine
	
	public void createPackage( int packageID, int orderID, int status, ShippingLabel label, OrderLineItem[] orderLineItemList)
	//creates a new package
	{
		
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
		
	}
	public ShippingLabel getLabel() //return the shipping label associated with this package
	{
		return label;
	}
	public OrderLineItem[] getOrderLineItemList() //return the orderlineitem list for this package
	{
		return orderLineItemList;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

package shipping;

import java.util.ArrayList;;

public class Package
{
	private int packageID;
	private int status;
	private ShippingLabel label;
	private ArrayList<OrderLineItem> orderLineItemList = new ArrayList<OrderLineItem>(); 
	private int shipmentId;

	public Package(int packageID, ShippingLabel label, int status, ArrayList<OrderLineItem> orderLineItemList,
			int shipmentId)
	// creates a new package
	{
		this.packageID = packageID;
		this.status = status;
		this.label = label;
		this.orderLineItemList = orderLineItemList;
		this.shipmentId = shipmentId;
	}

	public int getPackageID() // return the package ID of this package
	{
		return packageID;
	}

	public int getStatus() // return the current status of this package
	{
		return status;
	}

	public void updateStatus(int newStatus) // update the status of this package
	{
		this.status = newStatus;
	}

	public ShippingLabel getLabel() // return the shipping label associated with this package
	{
		return label;
	}

	public ArrayList<OrderLineItem> getOrderLineItemList() // return the orderlineitem list for this package
	{
		return orderLineItemList;
	}

	public int getShipmentId()
	{
		return shipmentId;
	}
}

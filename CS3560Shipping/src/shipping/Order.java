package shipping;

import java.util.ArrayList;
import java.util.Date;

public class Order {
	private int orderId;
	private CustomerInfo orderCustomerInformation;
	private ArrayList<OrderLineItem> orderLineItemList;
	private double totalShippingAndHandling;
	private double totalTax;
	private int status;
	
	/* This does not necessarily include all order line items in the order */
	/* Package createPackage(int order_id, OrderLineItem[] order_line_items) {
	 * 		updateShippingStatus(<insert>);
	 * 		Package a_package = new Package(order_id, order_line_items)
	 * }
	 */
	

	public void updateShippingStatus(int new_status) { 
		/*
		 * Also perform updates on the order_line_item_list accordingly 
		 */
		status = new_status;
	}
	
	public int getOrderID() {
		return orderId;
	}
	
	public CustomerInfo getCustomerInfo() {
		return orderCustomerInformation;
	}
	

	public ArrayList<OrderLineItem> getOrderLineItemList(){ // return the list of order line items associated with the Order, not package
		return orderLineItemList;
	}
	
	public double getTotalShippingAndHandling() { // probably some calculation involving tax and shipping cost.
		return totalShippingAndHandling;
	}
	
	public double getTotalTax() {
		return totalTax;
	}
	public int getOrderStatus()
	{
		return status;
	}
}

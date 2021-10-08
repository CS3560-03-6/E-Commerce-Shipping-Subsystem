package shipping;

import java.util.ArrayList;
import java.util.Date;

public class Order {
	private int order_id;
	private CustomerInfo order_customer_information;
	private ArrayList<OrderLineItem> order_line_item_list;
	private double total_shipping_and_handling;
	private double total_tax;
	private int status;
	
	/* This does not necessarily include all order line items in the order */
	/* Package createPackage(int order_id, OrderLineItem[] order_line_items) {
	 * 		updateShippingStatus(<insert>);
	 * 		Package a_package = new Package(order_id, order_line_items)
	 * }
	 */
	
	void updateShippingStatus(int new_status) { // doesn't have to be int data type for a status
		/*
		 * Also perform updates on the order_line_item_list accordingly 
		 */
		status = new_status;
	}
	
	int getOrderID() {
		return order_id;
	}
	
	CustomerInfo getCustomerInfo() {
		return order_customer_information;
	}
	
	ArrayList<OrderLineItem> getOrderLineItemList(){ // return the list of order line items associated with the Order, not package
		return order_line_item_list;
	}
	
	double getTotalShippingAndHandling() { // probably some calculation involving tax and shipping cost.
		return total_shipping_and_handling;
	}
	
	double getTotalTax() {
		return total_tax;
	}
}

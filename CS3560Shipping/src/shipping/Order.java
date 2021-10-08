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
	
	void updateOrderStatus(int new_status) {
		/*
		 * Also perform updates on the order_line_item_list accordingly 
		 */
		status = new_status;
	}
	
	int getOrderID() {
		return orderId;
	}
	
	CustomerInfo getCustomerInfo() {
		return orderCustomerInformation;
	}
	
	ArrayList<OrderLineItem> getOrderLineItemList(){
		return orderLineItemList;
	}
	
	double getTotalShippingAndHandling() {
		return totalShippingAndHandling;
	}
	
	double getTotalTax() {
		return totalTax;
	}
}

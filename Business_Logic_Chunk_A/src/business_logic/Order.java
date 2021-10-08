package business_logic;

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
	
	void updateShippingStatus(int new_status) {
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
	
	ArrayList<OrderLineItem> getOrderLineItemList(){
		return order_line_item_list;
	}
	
	double getTotalShippingAndHandling() {
		return total_shipping_and_handling;
	}
	
	double getTotalTax() {
		return total_tax;
	}
}

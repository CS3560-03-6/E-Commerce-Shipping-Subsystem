package shipping;

import java.util.ArrayList;

public class Order {
	private double dummyShippingAndHandling = 10.50;
	
	private int orderId;
	private CustomerInfo customerInfo; 
	private ArrayList<OrderLineItem> orderLineItemList;
	private double totalShippingAndHandling;
	private double totalTax;
	private int status;
	
	Order(int orderId, CustomerInfo customerInfo , ArrayList<OrderLineItem> orderLineItemList, int status){
		this.orderId = orderId;
		this.customerInfo = customerInfo;
		this.orderLineItemList = orderLineItemList;
		this.totalShippingAndHandling = calculateTotalShippingAndHandling(orderLineItemList);
		this.totalTax = calculateTotalTax(orderLineItemList);
		this.status = status;
	}
	
	void updateShippingStatus(int new_status) {
		/*
		 * Also perform updates on the order_line_item_list accordingly 
		 */
		status = new_status;
	}
	
	int getOrderID() {
		return orderId;
	}
	
	CustomerInfo getCustomerInfo() {
		return customerInfo;
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
	
	int getStatus() {
		return status;
	}
	
	double calculateShippingAndHandling(OrderLineItem item) {
		double result = dummyShippingAndHandling;
		/*
		 * Some unspecified magic to calculate shipping and handling. 
		 * Maybe it's done through considering item dimensions, weight and etc.
		 */
		
		return result;
	}
	
	double calculateTotalShippingAndHandling() {
		double result = 0.0;
		for (OrderLineItem item : orderLineItemList) {
			result += calculateShippingAndHandling(item);
		}
		totalTax = result;
		return result;
	}
	
	double calculateTotalShippingAndHandling(ArrayList<OrderLineItem> anOrderLineItemList) {
		double result = 0.0;
		for (OrderLineItem item : anOrderLineItemList) {
			result += calculateShippingAndHandling(item);
		}
		totalTax = result;
		return result;
	}
	
	double calculateTotalTax() {
		double result = 0.0;
		for (OrderLineItem item : orderLineItemList) {
			result += item.getTax();
		}
		totalTax = result;
		return result;
	}
	
	double calculateTotalTax(ArrayList<OrderLineItem> anOrderLineItemList) {
		double result = 0.0;
		for (OrderLineItem item : anOrderLineItemList) {
			result += item.getTax();
		}
		totalTax = result;
		return result;
	}
}

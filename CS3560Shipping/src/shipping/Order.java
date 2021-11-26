package shipping;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Order {
	
	private int orderId;
	private CustomerInfo customerInfo; 
	private ArrayList<OrderLineItem> orderLineItemList;
	private int status;
	
	public Order(int orderId, CustomerInfo customerInfo, int status, ArrayList<OrderLineItem> orderLineItemList){
		this.orderId = orderId;
		this.customerInfo = customerInfo;
		this.status = status;
		this.orderLineItemList = orderLineItemList;
	}
	
	public void updateShippingStatus(int new_status) {
		/*
		 * Also perform updates on the order_line_item_list accordingly 
		 */
		status = new_status;
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public CustomerInfo getCustomerInfo() {
		return customerInfo;
	}
	
	public ArrayList<OrderLineItem> getOrderLineItemList(){
		return orderLineItemList;
	}
	
	public int getStatus() {
		return status;
	}
	
	public double calculateTotalShipping() {
		double result = 0.0;
		for (OrderLineItem item : orderLineItemList) {
			result += item.getShippingCost().doubleValue();
		}
		return result;
	}
	
	public double calculateTotalTax() {
		double result = 0.0;
		for (OrderLineItem item : orderLineItemList) {
			result += item.getTax().doubleValue();
		}
		return result;
	}
	
	public double calculateTotalShippingAndTax() {
		return calculateTotalShipping() + calculateTotalTax();
	}
}

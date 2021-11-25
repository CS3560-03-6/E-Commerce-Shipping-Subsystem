package shipping;

import java.util.ArrayList;
import java.util.HashMap;

import Utility.ConnectionFactory;

public class Order
{

	private int orderId;
	private CustomerInfo customerInfo;
	private ArrayList<OrderLineItem> orderLineItemList;
	private int status;

	public Order(int orderId, CustomerInfo customerInfo, int status, ArrayList<OrderLineItem> orderLineItemList)
	{
		this.orderId = orderId;
		this.customerInfo = customerInfo;
		this.status = status;
		this.orderLineItemList = orderLineItemList;
	}

	public Order(ArrayList<HashMap<String, Object>> order)
	{
		this((int) order.get(0).get("orderId"), new CustomerInfo((int) order.get(0).get("customerInfoId")),
				(int) order.get(0).get("status"), getLineItems(order));
	}

	public static ArrayList<OrderLineItem> getLineItems(ArrayList<HashMap<String, Object>> order)
	{
		ArrayList<OrderLineItem> line_item_list = new ArrayList<OrderLineItem>();
		ArrayList<HashMap<String, Object>> line_items = ConnectionFactory.createOrderLineItemConnection()
				.getOrderLineItemListBasedOnOrder((int) order.get(0).get("orderId"));
		for (int line_item_entry = 0; line_item_entry < line_items.size(); line_item_entry++)
		{
			line_item_list.add(new OrderLineItem(line_items.get(line_item_entry)));
		}
		return line_item_list;
	}

	public void updateShippingStatus(int new_status)
	{
		/*
		 * Also perform updates on the order_line_item_list accordingly
		 */
		status = new_status;
	}

	public int getOrderID()
	{
		return orderId;
	}

	public CustomerInfo getCustomerInfo()
	{
		return customerInfo;
	}

	public ArrayList<OrderLineItem> getOrderLineItemList()
	{
		return orderLineItemList;
	}

	public int getStatus()
	{
		return status;
	}

	public double calculateTotalShipping()
	{
		double result = 0.0;
		for (OrderLineItem item : orderLineItemList)
		{
			result += item.getShippingCost();
		}
		return result;
	}

	public double calculateTotalTax()
	{
		double result = 0.0;
		for (OrderLineItem item : orderLineItemList)
		{
			result += item.getTax();
		}
		return result;
	}

	public double calculateTotalShippingAndTax()
	{
		return calculateTotalShipping() + calculateTotalTax();
	}
}

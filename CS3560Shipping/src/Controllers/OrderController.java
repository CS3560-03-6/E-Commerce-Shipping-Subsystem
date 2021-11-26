package Controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import Utility.ConnectionFactory;
import connection.CustomerInfoConnection;
import connection.OrderConnection;
import connection.OrderLineItemConnection;
import shipping.CustomerInfo;
import shipping.Order;
import shipping.OrderLineItem;
import shipping.Product;

public class OrderController 
{
	private static OrderConnection orderConnection = ConnectionFactory.createOrderConnection();
	private static OrderLineItemConnection orderLineConnection = ConnectionFactory.createOrderLineItemConnection();
	
	public static Order getOrder(int orderId)
	{
		ArrayList<HashMap<String, Object>> orderData = orderConnection.getCompleteOrderInformation(orderId);
		if(orderData == null)
			return null;
		int customerInfoId = (int)orderData.get(0).get("customerInfoId");
		Date dateEntered = (Date)orderData.get(0).get("dateEntered");
		return new Order(orderId, getCustomerInfo(customerInfoId), (int)orderData.get(0).get("status"), getOrderLineItemList(orderData),
				dateEntered);
	}
	private static CustomerInfo getCustomerInfo(int customerInfoId)
	{
		CustomerInfoConnection connection = ConnectionFactory.createCustomerInfoConnection();
		HashMap<String, Object> customerInfoData = connection.GetCustomerInfo(customerInfoId).get(0);
		String firstName = (String)customerInfoData.get("firstName");
		String lastName = (String)customerInfoData.get("lastName");
		String address = (String)customerInfoData.get("address");
		String phoneNum = (String)customerInfoData.get("phoneNum");
		String email = (String)customerInfoData.get("email");
		return new CustomerInfo(customerInfoId, firstName, lastName, address, phoneNum, email);
	}
	private static ArrayList<OrderLineItem> getOrderLineItemList(ArrayList<HashMap<String, Object>> orderData)
	{
		ArrayList<OrderLineItem> orderLineItemList = new ArrayList<OrderLineItem>();
		for(int i = 0; i < orderData.size(); i++)
		{
			HashMap<String, Object> orderLineItemData = orderData.get(i);
			int orderLineItemId = (int)orderLineItemData.get("orderLineItemId");
			
			//getting product Info
			int productId = (int)orderLineItemData.get("productId");
			HashMap<String, Object> productData = ConnectionFactory.createProductConnection().getProduct(productId).get(0);
		    String sku = (String)productData.get("sku");
		    String productName = (String)productData.get("productName");
		    BigDecimal cost = (BigDecimal)productData.get("cost");
		    BigDecimal length = (BigDecimal)productData.get("length");
		    BigDecimal width = (BigDecimal)productData.get("width");
		    BigDecimal height = (BigDecimal)productData.get("height");
		
			Date deliverByDate = (Date)orderLineItemData.get("deliverByDate");
			BigDecimal shippingCost = (BigDecimal)orderLineItemData.get("shippingCost");
			BigDecimal tax  = (BigDecimal)orderLineItemData.get("tax");
			int qty = (int)orderLineItemData.get("qty");
			int orderId = (int) orderLineItemData.get("orderId");
			
			orderLineItemList.add(new OrderLineItem(orderLineItemId, 
					new Product(productId, sku, productName, cost, length, width, height),
					deliverByDate, shippingCost, tax, qty, orderId));
		}
		return orderLineItemList;
	}
}

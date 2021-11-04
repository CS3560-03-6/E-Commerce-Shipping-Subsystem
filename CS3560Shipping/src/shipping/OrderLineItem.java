package shipping;

import java.util.Date;

public class OrderLineItem {
	private int orderLineItemId;
	private int orderId;
	private int productId;
	private int packageId;
	private Date deliverByDate;
	private double shippingCost;
	private double tax;
	private int quantity;
	
	OrderLineItem(int orderLineItemId, int orderId, int productId, int packageId,
				  Date deliverByDate, double shippingCost, double tax, int quantity){
		this.orderLineItemId = orderLineItemId;
		this.orderId = orderId;
		this.productId = productId;
		this.packageId = packageId;
		this.deliverByDate = deliverByDate;
		this.shippingCost = shippingCost;
		this.tax = tax;
		this.quantity = quantity;
			
	}
	
	int getOrderLineItemId() {
		return orderLineItemId;
	}
	
	int getOrderId() {
		return orderId;
	}
	
	int getProductId() {
		return productId;
	}
	
	int getPackageId() {
		return packageId;
	}
	
	Date getDeliverByDate() {
		return deliverByDate;
	}
	
	double getShippingCost() {
		return shippingCost;
	}
	
	double getTax() {
		return tax;
	}
	
	int getQuantity() {
		return quantity;
	}
}

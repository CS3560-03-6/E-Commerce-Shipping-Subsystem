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
	
	public int getOrderLineItemId() {
		return orderLineItemId;
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public int getProductId() {
		return productId;
	}
	
	public int getPackageId() {
		return packageId;
	}
	
	public Date getDeliverByDate() {
		return deliverByDate;
	}
	
	public double getShippingCost() {
		return shippingCost;
	}
	
	public double getTax() {
		return tax;
	}
	
	public int getQuantity() {
		return quantity;
	}
}

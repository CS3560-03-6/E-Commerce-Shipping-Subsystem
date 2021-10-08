package shipping;

import java.util.Date;

public class OrderLineItem {
	private int orderLineItemId;
	private int productId;
	private String sku;
	private String productName;
	private double length;
	private double width;
	private double height;
	private int quantity;
	private Date deliverByDate;
	private double shippingCost;
	private double tax;
	
	int getOrderLineItemID() {
		return orderLineItemId;
	}
	
	int getProductID() {
		return productId;
	}
	
	String getSKU() {
		return sku;
	}
	
	String getProductName() {
		return productName;
	}
	
	double[] getDimensions() {
		double[] dimensions = new double[3];
		dimensions[0] = length;
		dimensions[1] = width;
		dimensions[2] = height;
		
		return dimensions;
	}
	
	int getQuantity() {
		return quantity;
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
}

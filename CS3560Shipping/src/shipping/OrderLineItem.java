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
	

	int getOrderLineItemID() { // this ID is a particular instance of a given product
		return order_line_item_id;
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
	
	double[] getDimensions() { // return all three dimensions at once. System is unlikely to only request anything besides all three values for dimensions.
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

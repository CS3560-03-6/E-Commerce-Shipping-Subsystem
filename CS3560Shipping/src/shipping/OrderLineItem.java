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
	

	public int getOrderLineItemID() { // this ID is a particular instance of a given product
		return orderLineItemId;
	}
	
	public int getProductID() {
		return productId;
	}
	
	public String getSKU() {
		return sku;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public double[] getDimensions() { // return all three dimensions at once. System is unlikely to only request anything besides all three values for dimensions.
		double[] dimensions = new double[3];
		dimensions[0] = length;
		dimensions[1] = width;
		dimensions[2] = height;
		
		return dimensions;
	}
	
	public int getQuantity() {
		return quantity;
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
}

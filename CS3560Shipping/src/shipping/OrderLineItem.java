package shipping;

import java.util.Date;

public class OrderLineItem {
	private int order_line_item_id;
	private int product_id;
	private String sku;
	private String product_name;
	private double length;
	private double width;
	private double height;
	private int quantity;
	private Date deliver_by_date;
	private double shipping_cost;
	private double tax;
	
	int getOrderLineItemID() { // this ID is a particular instance of a given product
		return order_line_item_id;
	}
	
	int getProductID() {
		return product_id;
	}
	
	String getSKU() {
		return sku;
	}
	
	String getProductName() {
		return product_name;
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
		return deliver_by_date;
	}
	
	double getShippingCost() {
		return shipping_cost;
	}
	
	double getTax() {
		return tax;
	}
}

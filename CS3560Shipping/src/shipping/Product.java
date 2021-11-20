package shipping;

public class Product {
	
	private int productId;
	private String sku;
	private String productName;
	private double cost;
	private double width;
	private double length;
	private double height;
	
	Product (int productId, String sku, String productName, 
			 double cost, double width, double length, double height){
		this.productId = productId;
		this.sku = sku;
		this.productName = productName;
		this.cost = cost;
		this.width = width;
		this.length = length;
		this.height = height;
	}
	
	public int getProductId() {
		return productId;
	}
	
	public String getSku() {
		return sku;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public double getCost() {
		return cost;
	}
	
	public double[] getDimensions() {
		double[] dimensions = new double[3];
		dimensions[0] = width;
		dimensions[1] = length;
		dimensions[2] = height;
		
		return dimensions;
	}
}

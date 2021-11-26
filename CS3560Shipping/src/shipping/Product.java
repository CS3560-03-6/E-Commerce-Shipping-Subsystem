package shipping;

import java.math.BigDecimal;

public class Product {
	
	private int productId;
	private String sku;
	private String productName;
	private BigDecimal cost;
	private BigDecimal width;
	private BigDecimal length;
	private BigDecimal height;
	
	public Product (int productId, String sku, String productName, 
			BigDecimal cost, BigDecimal width, BigDecimal length, BigDecimal height){
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
	
	public BigDecimal getCost() {
		return cost;
	}
	
	public BigDecimal[] getDimensions() {
		BigDecimal[] dimensions = new BigDecimal[3];
		dimensions[0] = width;
		dimensions[1] = length;
		dimensions[2] = height;
		
		return dimensions;
	}
}

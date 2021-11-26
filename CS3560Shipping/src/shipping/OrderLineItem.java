package shipping;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;

import Utility.ConnectionFactory;

public class OrderLineItem
{
	private int orderLineItemId;
	private Product product;
	private Date deliverByDate;
	private BigDecimal shippingCost;
	private BigDecimal tax;
	private Integer quantity;
	private int orderId;
	
	public OrderLineItem(int orderLineItemId, Product product,
				  Date deliverByDate, BigDecimal shippingCost, BigDecimal tax, Integer quantity, int orderId){
		this.orderLineItemId = orderLineItemId;
		this.product = product;
		this.deliverByDate = deliverByDate;
		this.shippingCost = shippingCost;
		this.tax = tax;
		this.quantity = quantity;
		this.orderId = orderId;
	}

	@SuppressWarnings("unchecked")
	public OrderLineItem(HashMap<String, Object> item)
	{
		this((int) item.get("orderLineItemId"),
				createProduct((int)item.get("productId")),
				(Date) item.get("deliverByDate"), (BigDecimal) item.get("shippingCost"),
				(BigDecimal) item.get("tax"), (int) item.get("qty"), (int) item.get("orderId"));
	}
	
	private static Product createProduct(int productId)
	{
		HashMap<String, Object> productData = ConnectionFactory.createProductConnection().getProduct(productId).get(0);
	    String sku = (String)productData.get("sku");
	    String productName = (String)productData.get("productName");
	    BigDecimal cost = (BigDecimal)productData.get("cost");
	    BigDecimal length = (BigDecimal)productData.get("length");
	    BigDecimal width = (BigDecimal)productData.get("width");
	    BigDecimal height = (BigDecimal)productData.get("height");
	    return new Product(productId, sku, productName, cost, length, width, height);
	}
	
	public Integer getOrderLineItemId() {
		return orderLineItemId;
	}
	
	public Product getProduct() {
		return product;
	}

	
	public Date getDeliverByDate() {
		return deliverByDate;
	}
	
	public BigDecimal getShippingCost() {
		return shippingCost;
	}
	
	public BigDecimal getTax() {
		return tax;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
	public int getOrderId() {
		return orderId;
	}
}

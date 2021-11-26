package shipping;

import java.math.BigDecimal;
import java.util.Date;

public class OrderLineItem {
	private int orderLineItemId;
	private Product product;
	private Date deliverByDate;
	private BigDecimal shippingCost;
	private BigDecimal tax;
	private Integer quantity;
	
	public OrderLineItem(int orderLineItemId, Product product,
				  Date deliverByDate, BigDecimal shippingCost, BigDecimal tax, Integer quantity){
		this.orderLineItemId = orderLineItemId;
		this.product = product;
		this.deliverByDate = deliverByDate;
		this.shippingCost = shippingCost;
		this.tax = tax;
		this.quantity = quantity;
			
	}
	
	public Integer getOrderLineItemId() {
		return orderLineItemId;
	}
	
	public Product getProductId() {
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
}

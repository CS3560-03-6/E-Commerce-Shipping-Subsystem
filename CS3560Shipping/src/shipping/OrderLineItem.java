package shipping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import Utility.ConnectionFactory;

public class OrderLineItem
{
	private int orderLineItemId;
	private int orderId;
	private int productId;
	private int packageId;
	private Date deliverByDate;
	private BigDecimal shippingCost;
	private BigDecimal tax;
	private int quantity;

	public OrderLineItem(int orderLineItemId, int orderId, int productId, int packageId, Date deliverByDate,
			BigDecimal shippingCost, BigDecimal tax, int quantity)
	{
		this.orderLineItemId = orderLineItemId;
		this.orderId = orderId;
		this.productId = productId;
		this.packageId = packageId;
		this.deliverByDate = deliverByDate;
		this.shippingCost = shippingCost;
		this.tax = tax;
		this.quantity = quantity;

	}

	@SuppressWarnings("unchecked")
	public OrderLineItem(HashMap<String, Object> item)
	{
		this((int) item.get("orderLineItemId"), (int) item.get("orderId"),
				(int) item.get("productId"), item.get("packageId")==null ? 0 : 0,
				(Date) item.get("deliverByDate"), (BigDecimal) item.get("shippingCost"),
				(BigDecimal) item.get("tax"), (int) item.get("qty"));
	}

	public int getOrderLineItemId()
	{
		return orderLineItemId;
	}

	public int getOrderId()
	{
		return orderId;
	}

	public int getProductId()
	{
		return productId;
	}

	public int getPackageId()
	{
		return packageId;
	}

	public Date getDeliverByDate()
	{
		return deliverByDate;
	}

	public double getShippingCost()
	{
		return shippingCost.doubleValue();
	}

	public double getTax()
	{
		return tax.doubleValue();
	}

	public int getQuantity()
	{
		return quantity;
	}
}

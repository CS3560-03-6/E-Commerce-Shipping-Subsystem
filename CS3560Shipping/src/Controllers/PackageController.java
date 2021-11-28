package Controllers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import Utility.ConnectionFactory;
import connection.PackageConnection;
import connection.ShippingLabelConnection;
import shipping.OrderLineItem;
import shipping.Package;
import shipping.Product;
import shipping.ShippingLabel;

public class PackageController
{
	//todo delete package removes it from OrderLineItemList
	private static PackageConnection connection = ConnectionFactory.createPackageConnection();
	// Let User choose shippingLabel and OrderLineItemList
	// when update shipment should update all package's status

	public static boolean createPackage(int shippingLabelId, ArrayList<OrderLineItem> orderLineItemList)
	{
		// get latest packageId via sql
		PackageConnection packageConnection = ConnectionFactory.createPackageConnection();
		ArrayList<HashMap<String, Object>> resultSet = packageConnection.getLatestPackageId();
		int packageId = (Integer) resultSet.get(0).get("packageId") + 1;

		// actually create the package
		ShippingLabelConnection shippingLabelConnection = ConnectionFactory.createShippingLabelConnection();
		HashMap<String, Object> shippingLabelData = shippingLabelConnection.getShippingLabel(shippingLabelId).get(0);

		if (packageConnection.createPackage(packageId, (int) shippingLabelData.get("labelId"), 0))
		{
			// Update OrderLineItems to show that they are in a package
			for (int i = 0; i < orderLineItemList.size(); i++)
			{
				packageConnection.connectPackageToOrderLineItem(packageId,
						orderLineItemList.get(i).getOrderLineItemId());
			}

			return true;
		} 
		else
		{
			return false;
		}
	}

	public static Package getPackage(int packageId)
	{
		ArrayList<HashMap<String, Object>> packageData = connection.getCompletePackage(packageId);

		if (packageData == null)
			return null;
		// create a shippingLabel
		int labelId = (int) packageData.get(0).get("labelId");
		ShippingLabelConnection labelConnection = ConnectionFactory.createShippingLabelConnection();
		HashMap<String, Object> labelData = labelConnection.getShippingLabel(labelId).get(0);

		String trackingNum = (String) labelData.get("trackingNum");
		byte[] labelPic = (byte[]) labelData.get("label");

		ShippingLabel label = new ShippingLabel(labelId, trackingNum, labelPic);
		int status = (int) packageData.get(0).get("status");
		int shipmentId = packageData.get(0).get("shipmentId") == null ? -1 : (int) packageData.get(0).get("shipmentId");

		return new Package(packageId, label, status, getOrderLineItemList(packageData), shipmentId);
	}

	/**
	 * Will try to delete a package. If unsuccessful it will try to roll back all changes. 
	 * If it cannot roll back then a console message will be printed stating it was unable to roll back.
	 * @param packageId
	 * @return
	 */
	public static boolean deletePackage(int packageId)
	{
		PackageConnection packageConnection = ConnectionFactory.createPackageConnection();
		ArrayList<HashMap<String, Object>> packageData = packageConnection.getCompletePackage(packageId);
		for(int i = 0; i < packageData.size(); i++)
		{
			boolean passed = packageConnection.removePackageFromOrderLineItem(packageId, (int)packageData.get(i).get("orderLineItemId"));
			if(!passed)
			{
				//roll back changes
				for(int j = 0; j < i; j++)
				{
					packageConnection.connectPackageToOrderLineItem(packageId, (int)packageData.get(j).get("orderLineItemId"));
				}
				return false;
			}
		}
		if(packageConnection.deletePackage(packageId))
		{
			return true;
		}
		else
		{
			for(int i = 0; i < packageData.size(); i++)
			{
				int orderLineItemId = (int)packageData.get(i).get("orderLineItemId");
				boolean rollBackSuccess = packageConnection.connectPackageToOrderLineItem(packageId, orderLineItemId);
				if(!rollBackSuccess)
				{
					System.out.println("Cannot roll back changes to OrderLineItemId in Wss.Package\n"
							+ "Failed to change this row:\n-PackageId: "+packageId
							+"\n-OrderLineItemId: "+ orderLineItemId);
				}
			}
			return false;
		}
	}
	private static ArrayList<OrderLineItem> getOrderLineItemList(ArrayList<HashMap<String, Object>> packageData)
	{
		ArrayList<OrderLineItem> orderLineItemList = new ArrayList<OrderLineItem>();
		for (int i = 0; i < packageData.size(); i++)
		{
			HashMap<String, Object> orderLineItemData = packageData.get(i);
			int orderLineItemId = (int) orderLineItemData.get("orderLineItemId");

			// getting product Info
			int productId = (int) orderLineItemData.get("productId");
			HashMap<String, Object> productData = ConnectionFactory.createProductConnection().getProduct(productId)
					.get(0);
			String sku = (String) productData.get("sku");
			String productName = (String) productData.get("productName");
			BigDecimal cost = (BigDecimal) productData.get("cost");
			BigDecimal length = (BigDecimal) productData.get("length");
			BigDecimal width = (BigDecimal) productData.get("width");
			BigDecimal height = (BigDecimal) productData.get("height");

			Date deliverByDate = (Date) orderLineItemData.get("deliverByDate");
			BigDecimal shippingCost = (BigDecimal) orderLineItemData.get("shippingCost");
			BigDecimal tax = (BigDecimal) orderLineItemData.get("tax");
			int qty = (int) orderLineItemData.get("qty");
			int orderId = (int) orderLineItemData.get("orderId");

			orderLineItemList.add(new OrderLineItem(orderLineItemId,
					new Product(productId, sku, productName, cost, length, width, height), deliverByDate, shippingCost,
					tax, qty, orderId));
		}
		return orderLineItemList;
	}
}

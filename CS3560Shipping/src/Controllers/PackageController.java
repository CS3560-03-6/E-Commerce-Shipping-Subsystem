package Controllers;

import java.util.ArrayList;
import java.util.HashMap;

import Utility.ConnectionFactory;
import connection.PackageConnection;
import connection.ShippingLabelConnection;
import shipping.OrderLineItem;
import shipping.Package;
import shipping.ShippingLabel;

public class PackageController 
{
	//Let User choose shippingLabel and OrderLineItemList
	//when update shipment should update all package's status
	
	public static boolean createPackage(int shippingLabelId, ArrayList<OrderLineItem> orderLineItemList)
	{	
		//get latest packageId via sql
		PackageConnection packageConnection = ConnectionFactory.createPackageConnection();
		ArrayList<HashMap<String,Object>> resultSet = packageConnection.getLatestPackageId();
		int packageId = (Integer)resultSet.get(0).get("packageId");
		
		
		//actually create the package
		ShippingLabelConnection shippingLabelConnection = ConnectionFactory.createShippingLabelConnection();
		HashMap<String, Object> shippingLabelData = shippingLabelConnection.getShippingLabel(shippingLabelId).get(0);
		
		if(packageConnection.createPackage(packageId, (int)shippingLabelData.get("labelId"), 0))
		{
			//Update OrderLineItems to show that they are in a package
			for(int i = 0; i <= orderLineItemList.size(); i++)
			{
				packageConnection.connectPackageToOrderLineItem(packageId, orderLineItemList.get(i).getOrderLineItemId());
			}

			return true;
		}
		else
		{
			return false;
		}
	}
}

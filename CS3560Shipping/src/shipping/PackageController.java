package shipping;

import java.util.ArrayList;
import java.util.HashMap;

import Utility.ConnectionFactory;
import connection.PackageConnection;

public class PackageController 
{
	//Let User choose shippingLabel and OrderLineItemList
	//get image into shippinglabel table
	//upload png method
	public static Package createPackage(ShippingLabel shippingLabel, ArrayList<OrderLineItem> orderLineItemList)
	{	
		//get latest packageId via sql
		PackageConnection connection = ConnectionFactory.createPackageConnection();
		ArrayList<HashMap<String,Object>> resultSet = connection.getLatestPackageId();
		int packageId = (Integer)resultSet.get(0).get("packageId");
		
		
		//actually create the package
		return new Package(packageId, shippingLabel, orderLineItemList);
		
		//when making the package in sql need to update all of those orderLineItems
		//also need to make package itself
	}
}

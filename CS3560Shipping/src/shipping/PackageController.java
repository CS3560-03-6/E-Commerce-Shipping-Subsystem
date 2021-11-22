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
		if(connection.createPackage(packageId, shippingLabel.getLabelId(), 0))
		{
			//when making the package in sql need to update all of those orderLineItems
			//also need to make package itself
			Package shippingPackage =  new Package(packageId+1, shippingLabel, orderLineItemList);
			
			//Update OrderLineItems to show that they are in a package
			for(int i = 0; i <= orderLineItemList.size(); i++)
			{
				connection.connectPackageToOrderLineItem(packageId, orderLineItemList.get(i).getOrderLineItemID());
			}

			return shippingPackage;
		}
		else
		{
			return null;
		}
	}
}

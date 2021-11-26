package UI;

/**
 * @author  Gabriel Fok
 * @author Gia Quach
 */
import javax.swing.*;

import Utility.ConnectionFactory;
import shipping.OrderLineItem;
import shipping.Package;
import shipping.ShippingLabel;

import java.awt.*;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;

public class PackagesPane extends JPanel
{
	/**
	 * contructor for a display
	 * 
	 */
	private String id;
	private final String[] packageColNames = { "Address", "Package ID", "Total Items" };
	private ArrayList<Package> packages;
	private JScrollPane packagePane;
	private JTable packageList;
	private String[][] packageCol;

	public PackagesPane()
	{
		setLayout(new BorderLayout());

		// Temporary array for package list

		packageCol = new String[100][100];

		// Create JTable for package list
		packageList = new JTable(packageCol, packageColNames)
		{
			public boolean editCellAt(int row, int column, EventObject e)
			{
				return false;
			}
		};
		packageList.setRowHeight(45);
		// Add JTable to JScrollPane to make scrollable
		packagePane = new JScrollPane(packageList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		packageList.setFillsViewportHeight(true);
		packagePane.setAutoscrolls(true);

		// Add ScrollPane to Jpanel
		add(packagePane, BorderLayout.CENTER);
		packages = new ArrayList<Package>();
		getPackages();
	}

	public ArrayList<Package> getPackages()
	{
		ConnectionFactory.createConnection();
		packages.clear();
		ArrayList<HashMap<String, Object>> packages_sql = ConnectionFactory.createPackageConnection().getPackageList();
		if (packages_sql != null)
		{
			for (int entry = 0; entry < packages_sql.size(); entry++)
			{
				// Create each Order
				int packageId = (int) packages_sql.get(entry).get("packageId");
				ArrayList<OrderLineItem> packageLineItems = new ArrayList<OrderLineItem>();
				ArrayList<HashMap<String, Object>> order_line_items = ConnectionFactory.createOrderLineItemConnection()
						.getOrderLineItemListBasedOnPackage(packageId);
				for (int line_item = 0; line_item < order_line_items.size(); line_item++)
				{
					packageLineItems.add(new OrderLineItem(order_line_items.get(line_item)));
				}
				ArrayList<HashMap<String, Object>> packageInfo = ConnectionFactory.createPackageConnection().getPackage(packageId);
				packages.add(new Package(packageId,
						(ShippingLabel) packageInfo.get(entry).get("labelId"),
						(int) packageInfo.get(entry).get("status"), 
						packageLineItems));
			}
			for (int entry = 0; entry < packages.size(); entry++)
			{
				packageCol[entry][0] = "" + OrdersPane.getOrder(packages.get(entry).getOrderLineItemList().get(0).getOrderId()).getCustomerInfo().getAddress();
				packageCol[entry][1] = "" + packages.get(entry).getPackageID();
				packageCol[entry][2] = "" + packages.get(entry).getOrderLineItemList().size();
			}
			return packages;
		}
		System.out.println("No packages found.");
		return null;
	}

	public Package getPackage(int packageId)
	{
		for (int entry = 0; entry < packages.size(); entry++)
		{
			if (packages.get(entry).getPackageID() == packageId)
				return packages.get(entry);
		}
		return null;
	}
}
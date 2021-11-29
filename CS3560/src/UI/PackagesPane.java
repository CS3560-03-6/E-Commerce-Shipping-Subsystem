package UI;

/**
 * @author  Gabriel Fok
 * @author Gia Quach
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Controllers.OrderController;
import Controllers.PackageController;
import Utility.ConnectionFactory;
import shipping.Package;
import java.awt.*;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;

@SuppressWarnings("serial")
public class PackagesPane extends JPanel
{
	/**
	 * contructor for a display
	 * 
	 */
	private String id;
	private final String[] packageColNames = { "Address", "Package ID", "Total Items" };
	private static ArrayList<Package> packages;
	private JScrollPane packagePane;
	private JTable packageList;
	private String[][] packageCol;

	public PackagesPane()
	{
		setLayout(new BorderLayout());
		packageCol = new String[0][100];

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
		updateTable();
	}

	public static ArrayList<Package> getPackages()
	{
		ConnectionFactory.createConnection();
		packages.clear();
		ArrayList<HashMap<String, Object>> packages_sql = ConnectionFactory.createPackageConnection()
				.getAllPackageList();
		if (packages_sql != null)
		{
			for (int entry = 0; entry < packages_sql.size(); entry++)
			{
				// Create each Package
				int packageId = (int) packages_sql.get(entry).get("packageId");
				packages.add(PackageController.getPackage(packageId));
			}
			return packages;
		}

		System.out.println("No packages found.");
		return null;
	}

	public void updateTable()
	{
		getPackages();
		packageCol = new String[packages.size()][100];

		for (int entry = 0; entry < packages.size(); entry++)
		{
			packageCol[entry][0] = ""
					+ OrderController.getOrder(packages.get(entry).getOrderLineItemList().get(0).getOrderId())
							.getCustomerInfo().getAddress();
			packageCol[entry][1] = "" + packages.get(entry).getPackageID();
			packageCol[entry][2] = "" + packages.get(entry).getOrderLineItemList().size();
		}
		packageList.setModel(new DefaultTableModel(packageCol, packageColNames));
	}

	// The method that return the selected package id
	public String getID(int row)
	{
		id = packageCol[row][1];
		return id;
	}

	public JTable getTable()
	{
		return packageList;
	}

	public static Package getPackage(int packageId)
	{
		for (int entry = 0; entry < packages.size(); entry++)
		{
			if (packages.get(entry).getPackageID() == packageId)
				return packages.get(entry);
		}
		return null;
	}
}
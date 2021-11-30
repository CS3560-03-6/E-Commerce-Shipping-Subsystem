package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Controllers.ShipmentController;
import Utility.ConnectionFactory;
import shipping.Shipment;
import java.awt.*;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.HashMap;

/**
 * @author Gabriel Fok
 * @author Gia Quach
 */

@SuppressWarnings("serial")
public class ShipmentsPane extends JPanel
{

	/**
	 * contructor for a display
	 */

	private String id;
	private final String[] shipmentColNames = { "Date", "Shipment ID", "Total Packages" };
	private static ArrayList<Shipment> shipments;
	private JScrollPane shipmentPane;
	private JTable shipmentsList;
	private String[][] shipmentsCol;

	public ShipmentsPane()
	{
		setLayout(new BorderLayout());
		shipmentsCol = new String[0][100];
		// Create JTable for shipment list
		shipmentsList = new JTable(shipmentsCol, shipmentColNames)
		{
			public boolean editCellAt(int row, int column, EventObject e)
			{
				return false;
			}
		};
		shipmentsList.setRowHeight(45);
		// Add JTable to JScrollPane to make scrollable
		shipmentPane = new JScrollPane(shipmentsList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		shipmentsList.setFillsViewportHeight(true);
		shipmentPane.setAutoscrolls(true);

		// Add ScrollPane to Jpanel
		add(shipmentPane, BorderLayout.CENTER);
		shipments = new ArrayList<Shipment>();
		updateTable();
	}

	public String getID(int row)
	{
		id = shipmentsCol[row][1];
		return id;
	}

	public JTable getTable()
	{
		return shipmentsList;
	}

	public static ArrayList<Shipment> getShipments()
	{
		ConnectionFactory.createConnection();
		shipments.clear();
		ArrayList<HashMap<String, Object>> shipments_sql = ConnectionFactory.createShipmentConnection()
				.getShipmentList();
		if (shipments_sql != null)
		{
			for (int entry = 0; entry < shipments_sql.size(); entry++)
			{
				// Create each Shipment
				int shipmentId = (int) shipments_sql.get(entry).get("shipmentId");
				if (ShipmentController.getShipment(shipmentId) == null)
				{
					ConnectionFactory.createShipmentConnection().deleteShipment(shipmentId);
				} else
				{
					shipments.add(ShipmentController.getShipment(shipmentId));
				}
			}
			return shipments;
		}
		System.out.println("No shipments found.");
		return null;
	}

	public void updateTable()
	{
		getShipments();
		shipmentsCol = new String[shipments.size()][100];
		for (int entry = 0; entry < shipments.size(); entry++)
		{
			shipmentsCol[entry][0] = "" + shipments.get(entry).getDateShipped();
			shipmentsCol[entry][1] = "" + shipments.get(entry).getShipmentID();
			shipmentsCol[entry][2] = "" + shipments.get(entry).getPackageList().size();
		}
		shipmentsList.setModel(new DefaultTableModel(shipmentsCol, shipmentColNames));
	}

	public static Shipment getShipment(int order_id)
	{
		for (int entry = 0; entry < shipments.size(); entry++)
		{
			if (shipments.get(entry).getShipmentID() == order_id)
				return shipments.get(entry);
		}
		return null;
	}

}
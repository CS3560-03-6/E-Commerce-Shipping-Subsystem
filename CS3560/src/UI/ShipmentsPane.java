package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Gabriel Fok
 */

public class ShipmentsPane extends JPanel
{


	/**
	 * contructor for a display
	 */
	
	/**
	 * JTable for data
	 * Double click a row to update shipments
	 */
	public ShipmentsPane()
	{
		setLayout(new BorderLayout());

		//Temporary array for shipment list
		String[] shipColNames = {"Date", "Shipment ID", "Name"};
        String[][] shipCol = new String[100][100];

		//Create JTable for package list
		JTable shipList = new JTable(shipCol, shipColNames);

		//Add JTable to JScrollPane to make scrollable
		JScrollPane shipPane = new JScrollPane(shipList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        shipList.setFillsViewportHeight(true);
		shipPane.setAutoscrolls(true);
		shipPane.setPreferredSize(new Dimension(0, 0));

		//Add ScrollPane to Jpanel
		add(shipPane, BorderLayout.CENTER);
	}

}
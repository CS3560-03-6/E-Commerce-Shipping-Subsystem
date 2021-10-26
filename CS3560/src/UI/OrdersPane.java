package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * @author Gabriel Fok
 */

public class OrdersPane extends JPanel
{
	private JTextField field;

	/**
	 * contructor for a display
	 * 
	 */

	public OrdersPane()
	{
		setLayout(new BorderLayout());

		//Temporary array for order list
		String[] orderColNames = {"Date", "Order ID", "Name"};
        String[][] orderCol = new String[100][100];

		//Create JTable for order list
		JTable orderList = new JTable(orderCol, orderColNames);

		//Add JTable to JScrollPane to make scrollable
		JScrollPane orderPane = new JScrollPane(orderList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        orderList.setFillsViewportHeight(true);
		orderPane.setAutoscrolls(true);
		orderPane.setPreferredSize(new Dimension(0, 0));

		//Add ScrollPane to Jpanel
		add(orderPane, BorderLayout.CENTER);
	}
}
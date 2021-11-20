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
	private String id;
	private final String[] orderColNames = {"Date", "Order ID", "Name"};
	private String[][] orderCol;
	private JTable orderList;
	private JScrollPane orderPane;

	/**
	 * contructor for a display
	 * 
	 */

	public OrdersPane()
	{

		setLayout(new BorderLayout());

		// Temporary array for order list
        orderCol = new String[100][100];
		orderCol[0][0] = "11/11/2021";
		orderCol[0][1] = "1223232";
		orderCol[0][2] = "Andrew Jackson";

		// Create JTable for order list
		orderList = new JTable(orderCol, orderColNames) {
			public boolean editCellAt(int row, int column, EventObject e) {
				return false;
			}
		};

		// Add JTable to JScrollPane to make scrollable
		orderPane = new JScrollPane(orderList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        orderList.setFillsViewportHeight(true);
		orderPane.setAutoscrolls(true);

		// Add ScrollPane to Jpanel
		add(orderPane, BorderLayout.CENTER);
	}

	// The method that return the selected order id
	public String getID(int row, int col) {
		id = orderCol[row][col];
		return id;
	}

	// The method that return JTable
	public JTable getTable() {
		return orderList;
	}

}
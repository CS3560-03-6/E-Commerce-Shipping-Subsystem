package UI;

import javax.swing.*;

import Utility.ConnectionFactory;
import shipping.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * @author Gabriel Fok
 */

public class OrdersPane extends JPanel
{
	private String id;
	private final String[] orderColNames = { "Date", "Order ID", "Name" };
	private String[][] orderCol;
	private JTable orderList;
	private JScrollPane orderPane;
	private ArrayList<Order> orders;

	/**
	 * contructor for a display
	 * 
	 */

	public OrdersPane()
	{

		setLayout(new BorderLayout());

		// Temporary array for order list
		orderCol = new String[100][100];

		// Create JTable for order list
		orderList = new JTable(orderCol, orderColNames)
		{
			public boolean editCellAt(int row, int column, EventObject e)
			{
				return false;
			}
		};

		// Add JTable to JScrollPane to make scrollable
		orderPane = new JScrollPane(orderList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		orderList.setFillsViewportHeight(true);
		orderPane.setAutoscrolls(true);

		// Add ScrollPane to Jpanel
		add(orderPane, BorderLayout.CENTER);
		orders = new ArrayList<Order>();
		getOrders();
	}

	// The method that return the selected order id
	public String getID(int row)
	{
		id = orderCol[row][1];
		return id;
	}

	// The method that return JTable
	public JTable getTable()
	{
		return orderList;
	}

	// Refreshes order list, updates this OrdersPane's display, and returns the
	// ArrayList of orders
	public ArrayList<Order> getOrders()
	{
		ConnectionFactory.createConnection();
		orders.clear();
		ArrayList<HashMap<String, Object>> orders_sql = ConnectionFactory.createOrderConnection().getOrderList();
		for (int entry = 0; entry < orders_sql.size(); entry++)
		{
			// Create Order
			int order_id = (int) orders_sql.get(entry).get("orderId");
			ArrayList<HashMap<String, Object>> order = ConnectionFactory.createOrderConnection()
					.getCompleteOrderInformation(order_id);
			orders.add(new Order(order));
			
			// Add line items to Order
//			for (int lineitem = 0; lineitem < orderInfoList.size(); lineitem++)
//			{
//				OrderLineItem order_line_item = new OrderLineItem(orderInfoList.get(lineitem).get("orderLineItemId"),
//						order_id, 0, 0, );
//				orders.get(entry).getOrderLineItemList().add("");
//			}
			System.out.println("HIT: Found order " + orders.get(entry).getOrderID());
		}
		for (int entry = 0; entry < orders.size(); entry++)
		{
			orderCol[entry][0] = "" + orders.get(entry).getStatus();
			orderCol[entry][1] = "" + orders.get(entry).getOrderID();
			orderCol[entry][2] = "" + orders.get(entry).getCustomerInfo().getCustomerName()[0] + " "
			+ orders.get(entry).getCustomerInfo().getCustomerName()[1];
		}
		return orders;
	}

	public Order getOrder(int order_id)
	{
		for (int entry = 0; entry < orders.size(); entry++)
		{
			if (orders.get(entry).getOrderID() == order_id)
				return orders.get(entry);
		}
		return null;
	}
}
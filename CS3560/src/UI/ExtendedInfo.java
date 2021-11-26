package UI;

import javax.swing.*;

import Utility.ConnectionFactory;
import shipping.Order;
import shipping.OrderLineItem;

import java.awt.*;
import java.awt.event.*;
import java.security.cert.CertificateRevokedException;
import java.util.*;

/**
 * @author Gabriel Fok
 */

public class ExtendedInfo extends JPanel
{
	private JLabel backtext;
	private JLabel[] orderInfoLabels = new JLabel[10];
	private JTextArea[] texts = new JTextArea[9];
	private JTable orderList;
	private JScrollPane orderItemPane;
	private JPanel extInfoPanel;
	private JScrollPane extInfoScrollable;

	/**
	 * contructor for a display
	 * 
	 */

	public ExtendedInfo()
	{
		setBackground(Color.GRAY);
		setLayout(new BorderLayout());
		backtext = new JLabel("No item selected.", JLabel.CENTER);
		add(backtext, BorderLayout.CENTER);

		// set up the information panel
		extInfoPanel = new JPanel();
		setupExtInfoPanel(extInfoPanel);
	}

	public void deselect()
	{
		removeAll();
		repaint();
		add(backtext, BorderLayout.CENTER);
		validate();
	}

	// Basic setup for extended info panel
	private void setupExtInfoPanel(JPanel panel)
	{
		panel.setBackground(Color.WHITE);
		panel.setLayout(new GridBagLayout());
		validate();
	}

	public void clear()
	{
		removeAll();
		extInfoPanel.removeAll();
	}

	public void showOrderExtInfo(Order order)
	{
		clear();
		String[] orderColNames = { "Order Line Item ID", "Product Name" };
		String[][] orderCol = new String[100][100];
		ArrayList<OrderLineItem> items = order.getOrderLineItemList();

		for (int line_item = 0; line_item < items.size(); line_item++)
		{
			orderCol[line_item][0] = "" + items.get(line_item).getOrderLineItemId();
			ArrayList<HashMap<String, Object>> products = ConnectionFactory.createProductConnection()
					.getProduct(items.get(line_item).getProductId());
			orderCol[line_item][1] = "" + products.get(0).get("productName");
		}

		// Create new Jtable for the order item list
		orderList = new JTable(orderCol, orderColNames)
		{
			public boolean editCellAt(int row, int column, EventObject e)
			{
				return false;
			}
		};
		orderList.setRowHeight(30);
		// Allow JTable of order item list to be scrollale
		orderItemPane = new JScrollPane(orderList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		orderItemPane.setAutoscrolls(true);
		orderItemPane.setMinimumSize(new Dimension(300, 300));

		// Initializing the labels and textfields to display info
		for (int i = 0; i < 10; i++)
		{
			orderInfoLabels[i] = new JLabel();
			orderInfoLabels[i].setForeground(Color.BLACK);
			if (i != 9)
			{
				texts[i] = new JTextArea();
				texts[i].setEditable(false);
			}
		}

		// Naming labels to display info
		orderInfoLabels[0].setText("Order ID: ");
		orderInfoLabels[1].setText("Customer ID: ");
		orderInfoLabels[2].setText("Customer Name: ");
		orderInfoLabels[3].setText("Customer Address: ");
		orderInfoLabels[4].setText("Customer Phone Number: ");
		orderInfoLabels[5].setText("Customer Email: ");
		orderInfoLabels[6].setText("Order Item List: ");
		orderInfoLabels[7].setText("Total Shipping Cost: ");
		orderInfoLabels[8].setText("Total Tax: ");
		orderInfoLabels[9].setText("Status: ");

		// Get information about order
		texts[0].setText("" + order.getOrderID());
		texts[1].setText("" + order.getCustomerInfo().getCustomerID());
		texts[2].setText("" + order.getCustomerInfo().getCustomerName()[0] + " " + ""
				+ order.getCustomerInfo().getCustomerName()[1]);
		texts[3].setText("" + order.getCustomerInfo().getAddress());
		texts[4].setText("" + order.getCustomerInfo().getPhoneNumber());
		texts[5].setText("" + order.getCustomerInfo().getEmail());
		texts[6].setText("$" + String.format("%.2f", order.calculateTotalShipping()));
		texts[7].setText("$" + String.format("%.2f", order.calculateTotalTax()));
		switch ((int) order.getStatus())
			{
			case 0:
				texts[8].setText("Unprocessed");
				break;
			case 1:
				texts[8].setText("Processing");
				break;
			default:
				texts[8].setText("ERROR: Status unknown");
				break;
			}

		// contraints for gridbaglayout
		GridBagConstraints gbc = new GridBagConstraints();

		// adding the labels and textfield into the panel
		for (int i = 0; i < 6; i++)
		{
			gbc = createGbc(0, i);
			extInfoPanel.add(orderInfoLabels[i], gbc);
			gbc = createGbc(1, i);
			extInfoPanel.add(texts[i], gbc);
		}

		gbc.gridx = 0;
		gbc.gridy = 6;
		extInfoPanel.add(orderInfoLabels[6], gbc);
		gbc.gridx = 1;
		gbc.gridy = 7;
		extInfoPanel.add(orderItemPane, gbc);

		for (int i = 8; i < 11; i++)
		{
			gbc = createGbc(0, i);
			extInfoPanel.add(orderInfoLabels[i - 1], gbc);
			gbc = createGbc(1, i);
			extInfoPanel.add(texts[i - 2], gbc);
		}

		// Adding the extInfoPanel into JScrollPane
		extInfoScrollable = new JScrollPane(extInfoPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		extInfoScrollable.setAutoscrolls(true);

		repaint();
		add(extInfoScrollable, BorderLayout.CENTER); // This stop the labels and textfield from becoming too small when
													 // resize
		validate();
	}

	// The method that set GridBagContraints
	private GridBagConstraints createGbc(int x, int y)
	{
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		return gbc;
	}

}

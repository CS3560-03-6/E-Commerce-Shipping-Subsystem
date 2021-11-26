package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.cert.CertificateRevokedException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.*;
import Utility.ConnectionFactory;
import shipping.Order;
import shipping.OrderLineItem;

public class CreatePackagePopup
{
	private final JFrame f;
	private JPanel popup;
	private JTable orderList;
	private String[] selectedOrderRow;
	private String[] selectedOrderList;
	private JScrollPane orderItemPane;;
	private JButton createButton;
	private ActionListener action;
	private JTextField orderIdField;
	private ArrayList<Order> orders;

	CreatePackagePopup()
	{
		f = new JFrame("Create a package");
		popup = new JPanel();
		f.setContentPane(popup);
		SpringLayout layout = new SpringLayout();
		popup.setLayout(layout);

		String[] labels = { "Order ID: ", "Select Line Items: ", "Finish: " };
		int numPairs = labels.length;
		orderItemPane = new JScrollPane(orderList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		orderItemPane.setAutoscrolls(true);
		orderItemPane.setMinimumSize(new Dimension(600, 100));

		String[] orderColNames = { "Order Item ID" };
		String[][] orderCol = new String[100][100];
		orders = new ArrayList<Order>();

		// A search for user ID after user click enter after typing into textfield
		// We will change this once we can populate the order list onto the JTable from
		// the database
		action = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(f, "Search for Order ID: Complete");

				// Clear all elements of the arrays
				for (int i = 0; i < orderCol.length; i++)
				{
					Arrays.fill(orderCol[i], null);
				}

				orderItemPane.repaint();

				// Display order ID line items if valid
				try
				{
					if (validOrderId(Integer.parseInt(orderIdField.getText())))
					{
						ArrayList<OrderLineItem> items = getOrder(Integer.parseInt(orderIdField.getText()))
								.getOrderLineItemList();

						for (int line_item = 0; line_item < items.size(); line_item++)
						{
							orderCol[line_item][0] = "" + items.get(line_item).getOrderLineItemId();
						}
						createButton.setEnabled(true);
					}

				} catch (Exception ex)
				{
					JOptionPane.showMessageDialog(f, "Invalid Order ID");
				}
			}
		};

		// Create and populate the panel.
		for (int i = 0; i < numPairs; i++)
		{
			JLabel l = new JLabel(labels[i], JLabel.TRAILING);
			popup.add(l);
			switch (i)
				{
				case 1:// Allow JTable of order item list to be scrollale
					orderList = new JTable(orderCol, orderColNames)
					{
						public boolean editCellAt(int row, int column, EventObject e)
						{
							return false;
						}
					};
					orderList.getSelectionModel().addListSelectionListener(new RowListSelectionListener());
					orderItemPane = new JScrollPane(orderList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
							ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					orderItemPane.setAutoscrolls(true);
					orderItemPane.setMinimumSize(new Dimension(300, 300));

					popup.add(orderItemPane);
					break;
				case 2:
					createButton = new JButton("Create Package");
					createButton.setEnabled(false);
					createButton.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							// Get selected order item into a list when package is created
							if (selectedOrderRow == null)
							{
								JOptionPane.showMessageDialog(f, "Cannot create package: No items selected.");
							} else
							{
								selectedOrderList = new String[selectedOrderRow.length];
								if (selectedOrderRow != null)
								{
									for (int i = 0; i < selectedOrderRow.length; i++)
									{
										if (selectedOrderRow[i] != null)
										{
											selectedOrderList[i] = (String) orderList.getValueAt(i, 0);
										}
									}
								}
								/* send database object of package */
								f.dispose();
							}
						}
					});
					popup.add(createButton);
					break;
				default:

					orderIdField = new JTextField(10);
					orderIdField.setMaximumSize(orderIdField.getPreferredSize());
					orderIdField.addActionListener(action);
					l.setLabelFor(orderIdField);
					popup.add(orderIdField);
					break;
				}
		}

		f.setSize(300, 300);
		f.setVisible(true);
		SpringUtilities.makeCompactGrid(popup, numPairs, 2, 6, 6, 6, 6);

	}

	// A class listener that will save selected into a string array
	private class RowListSelectionListener implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			int[] rows;
			String[] selected;
			if (orderList.getRowSelectionAllowed() && !orderList.getColumnSelectionAllowed())
			{
				rows = orderList.getSelectedRows();
				selected = new String[rows.length + 1];
				for (int i = 0; i < rows.length; i++)
				{
					selected[i] = String.valueOf(rows[i]);
				}
				selectedOrderRow = Arrays.copyOf(selected, selected.length + 1);
			}
		}
	}

	private boolean validOrderId(int orderId)
	{
		ArrayList<HashMap<String, Object>> order = ConnectionFactory.createOrderConnection()
				.getCompleteOrderInformation(orderId);

		orders.add(new Order(order));

		for (int id = 0; id < order.size(); id++)
		{
			if (orderId == orders.get(id).getOrderId())
				return true;
		}

		return false;
	}

	// A function that will return selectedOrderList
	public String[] packageOrderList()
	{
		return selectedOrderList;
	}

	public Order getOrder(int order_id)
	{
		for (int entry = 0; entry < orders.size(); entry++)
		{
			if (orders.get(entry).getOrderId() == order_id)
				return orders.get(entry);
		}
		return null;
	}

}
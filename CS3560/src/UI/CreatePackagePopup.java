package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.cert.CertificateRevokedException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Controllers.OrderController;
import Controllers.PackageController;

import java.util.*;
import Utility.ConnectionFactory;
import shipping.Order;
import shipping.OrderLineItem;

public class CreatePackagePopup
{
	private final JFrame f;
	private JPanel popup;
	private JTable orderItemTable;
	private String[] selectedOrderRow;
	private String[] selectedOrderItemList;
	private JScrollPane orderItemTableScrollPane;
	private JButton createButton;
	private ActionListener action;
	private JTextField orderIdField;
	private ArrayList<Order> orders;
	private String[][] orderCol;

	CreatePackagePopup()
	{
		f = new JFrame("Create a package");
		popup = new JPanel();
		f.setContentPane(popup);
		SpringLayout layout = new SpringLayout();
		popup.setLayout(layout);

		String[] labels = { "Order ID: ", "Select Line Items: ", "Finish: " };
		int numPairs = labels.length;
		orderItemTableScrollPane = new JScrollPane(orderItemTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		orderItemTableScrollPane.setAutoscrolls(true);
		orderItemTableScrollPane.setMinimumSize(new Dimension(600, 100));

		orders = OrdersPane.getOrders();
		String[] orderColNames = { "Order Item ID" };
		orderCol = new String[0][100];

		// A search for user ID after user click enter after typing into textfield
		// We will change this once we can populate the order list onto the JTable from
		// the database
		action = new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JOptionPane.showMessageDialog(f, "Search for Order ID: Complete");

				// Display order ID line items if valid
				try
				{
					int orderId = Integer.parseInt(orderIdField.getText());
					if (validOrderId(orderId))
					{
						ArrayList<OrderLineItem> items = new ArrayList<OrderLineItem>();
						ArrayList<HashMap<String, Object>> result = ConnectionFactory.createOrderLineItemConnection()
								.getOrderLineItemListWithoutPackageBasedOnOrder(orderId);
						System.out.println(result.size());
						for (int entry = 0; entry < result.size(); entry++)
						{
							ArrayList<OrderLineItem> all_order_items = OrderController.getOrder(orderId)
									.getOrderLineItemList();
							for (int item = 0; item < all_order_items.size(); item++)
							{
								if (all_order_items.get(item)
										.getOrderLineItemId() == (int) result.get(entry).get("orderLineItemId"))
								{
									items.add(all_order_items.get(item));
								}
							}
						}
						orderCol = new String[items.size()][100];
						for (int line_item = 0; line_item < items.size(); line_item++)
						{
							orderCol[line_item][0] = "" + items.get(line_item).getOrderLineItemId();
						}
						if (items.size() == 0)
						{
							JOptionPane.showMessageDialog(f, "All items in the order are packaged!");

						} else
						{
							createButton.setEnabled(true);
						}
					} else
					{
						orderCol = new String[0][100];
						createButton.setEnabled(false);
						throw new NumberFormatException("That ID does not exist.");
					}
					orderItemTable.setModel(new DefaultTableModel(orderCol, orderColNames));
					orderItemTableScrollPane.repaint();
				} catch (NumberFormatException ex)
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
					orderItemTable = new JTable(orderCol, orderColNames)
					{
						public boolean editCellAt(int row, int column, EventObject e)
						{
							return false;
						}
					};

					orderItemTable.getSelectionModel().addListSelectionListener(new RowListSelectionListener());
					orderItemTableScrollPane = new JScrollPane(orderItemTable,
							ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
							ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					orderItemTableScrollPane.setAutoscrolls(true);
					orderItemTableScrollPane.setMinimumSize(new Dimension(300, 300));

					popup.add(orderItemTableScrollPane);
					break;
				case 2:
					createButton = new JButton("Create Package");
					createButton.setEnabled(false);
					createButton.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							// Get selected order item into a list when package is created
							int orderId = Integer.parseInt(orderIdField.getText());
							if (selectedOrderRow == null)
							{
								JOptionPane.showMessageDialog(f, "Cannot create package: No items selected.");
							} else if (validOrderId(orderId))
							{
								/* Get orderLineItem IDs from selected rows */
								selectedOrderItemList = new String[selectedOrderRow.length];
								for (int i = 0; i < selectedOrderRow.length; i++)
								{
									selectedOrderItemList[i] = (String) orderItemTable
											.getValueAt(Integer.parseInt(selectedOrderRow[i]), 0);
								}
								/* send database object of package */
								ArrayList<OrderLineItem> selectedOrderLineItems = new ArrayList<OrderLineItem>();
								for (int line_item_id = 0; line_item_id < selectedOrderItemList.length; line_item_id++)
								{
									ArrayList<OrderLineItem> items = OrderController.getOrder(orderId)
											.getOrderLineItemList();
									for (int order_item = 0; order_item < items.size(); order_item++)
									{
										if (Integer.parseInt(selectedOrderItemList[line_item_id]) == items
												.get(order_item).getOrderLineItemId())
										{
											selectedOrderLineItems.add(OrderController.getOrder(orderId)
													.getOrderLineItemList().get(order_item));
										}
									}
								}
								int shippingLabelId = (int) ConnectionFactory.createPackageConnection()
										.getLatestPackageId().size() == 0 ? 1
												: (int) ConnectionFactory.createPackageConnection().getLatestPackageId()
														.get(0).get("packageId") + 1;
								if (PackageController.createPackage(shippingLabelId, selectedOrderLineItems))
								{
									JOptionPane.showMessageDialog(f, "New package created!");
									FullPanel.refresh();
								} else
								{
									JOptionPane.showMessageDialog(f, "Failed to create package.");
								}
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
			if (orderItemTable.getRowSelectionAllowed() && !orderItemTable.getColumnSelectionAllowed())
			{
				rows = orderItemTable.getSelectedRows();
				selected = new String[rows.length];
				for (int i = 0; i < rows.length; i++)
				{
					selected[i] = String.valueOf(rows[i]);
				}
				selectedOrderRow = Arrays.copyOf(selected, selected.length);
			}
		}
	}

	private boolean validOrderId(int orderId)
	{
		for (int id = 0; id < orders.size(); id++)
		{
			if (orderId == orders.get(id).getOrderId())
				return true;
		}
		return false;
	}

	// A function that will return selectedOrderList
	public String[] getPackageOrderList()
	{
		return selectedOrderItemList;
	}
}
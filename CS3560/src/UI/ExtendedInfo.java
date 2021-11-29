package UI;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.table.DefaultTableCellRenderer;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import Controllers.OrderController;
import Controllers.PackageController;
import Controllers.ShipmentController;
import Utility.ConnectionFactory;
import shipping.Order;
import shipping.OrderLineItem;

import java.awt.*;
import java.awt.event.*;
import java.security.cert.CertificateRevokedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import shipping.Package;
import shipping.Shipment;

/**
 * @author Gabriel Fok
 */

public class ExtendedInfo extends JPanel
{
	private JLabel backtext;
	private JPanel extInfoPanel;
	private JScrollPane extInfoScrollable;

	private JLabel[] orderInfoLabels = new JLabel[10];
	private JTextArea[] orderTexts = new JTextArea[9];
	private JTable orderList;
	private JScrollPane orderItemPane;

	private JLabel[] packageInfoLabels = new JLabel[11];
	private JTextArea[] packageTexts = new JTextArea[7];
	private JTable packageList;
	private JScrollPane packageItemPane;
	private final String[] packageStatuses = { "In house", "Shipped to carrier", "Delivered to customer", "Delayed" };
	private JComboBox<String> packageStatusComboBox;

	private JLabel[] shipmentInfoLabels = new JLabel[4];
	private JTextArea[] shipmentTexts = new JTextArea[2];
	private JTable shipmentList;
	private JScrollPane shipmentItemPane;

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
		ArrayList<OrderLineItem> items = order.getOrderLineItemList();
		String[] orderColNames = { "Order Line Item ID", "Product Name" };
		String[][] orderCol = new String[items.size()][100];
		ArrayList<Integer> taken_rows = new ArrayList<Integer>();
		for (int line_item = 0; line_item < items.size(); line_item++)
		{
			orderCol[line_item][0] = "" + items.get(line_item).getOrderLineItemId();
			ArrayList<HashMap<String, Object>> products = ConnectionFactory.createProductConnection()
					.getProduct(items.get(line_item).getProduct().getProductId());
			orderCol[line_item][1] = "" + products.get(0).get("productName");
			if (items.get(line_item).getPackageId() != -1)
			{
				taken_rows.add(line_item);
			}
		}

		// Create new Jtable for the order item list
		orderList = new JTable(orderCol, orderColNames)
		{
			public boolean editCellAt(int row, int column, EventObject e)
			{
				return false;
			}
		};
		orderList.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
		{
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column)
			{
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				for (int entry = 0; entry < taken_rows.size(); entry++)
				{
					if (row == taken_rows.get(entry))
					{
						c.setBackground(Color.LIGHT_GRAY);
						return c;
					}
				}
				c.setBackground(Color.WHITE);
				return c;
			}
		});
		orderList.setRowHeight(30);
		// Allow JTable of order item list to be scrollale
		orderItemPane = new JScrollPane(orderList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		orderItemPane.setAutoscrolls(true);
		orderItemPane.setMinimumSize(new Dimension(300, 300));

		// Initializing the labels and textfields to display info
		for (int i = 0; i < orderInfoLabels.length; i++)
		{
			orderInfoLabels[i] = new JLabel();
			orderInfoLabels[i].setForeground(Color.BLACK);
			if (i != orderInfoLabels.length - 1)
			{
				orderTexts[i] = new JTextArea();
				orderTexts[i].setEditable(false);
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
		orderTexts[0].setText("" + order.getOrderId());
		orderTexts[1].setText("" + order.getCustomerInfo().getCustomerID());
		orderTexts[2].setText("" + order.getCustomerInfo().getCustomerName()[0] + " " + ""
				+ order.getCustomerInfo().getCustomerName()[1]);
		orderTexts[3].setText("" + order.getCustomerInfo().getAddress());
		orderTexts[4].setText("" + order.getCustomerInfo().getPhoneNumber());
		orderTexts[5].setText("" + order.getCustomerInfo().getEmail());
		orderTexts[6].setText("$" + String.format("%.2f", order.calculateTotalShipping()));
		orderTexts[7].setText("$" + String.format("%.2f", order.calculateTotalTax()));
		switch ((int) order.getStatus())
			{
			case 0:
				orderTexts[8].setText("Unprocessed");
				break;
			case 1:
				orderTexts[8].setText("Processing");
				break;
			case 2:
				orderTexts[8].setText("Fully packaged");
				break;
			default:
				orderTexts[8].setText("ERROR: Status unknown");
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
			extInfoPanel.add(orderTexts[i], gbc);
		}

		gbc.gridx = 0;
		gbc.gridy = 6;
		extInfoPanel.add(orderInfoLabels[6], gbc);
		gbc.gridx = 1;
		gbc.gridy = 6;
		extInfoPanel.add(orderItemPane, gbc);

		for (int i = 8; i < 11; i++)
		{
			gbc = createGbc(0, i);
			extInfoPanel.add(orderInfoLabels[i - 1], gbc);
			gbc = createGbc(1, i);
			extInfoPanel.add(orderTexts[i - 2], gbc);
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

	public void showPackageExtInfo(Package pkg)
	{
		clear();
		ArrayList<OrderLineItem> items = pkg.getOrderLineItemList();
		String[] packageColNames = { "Order Line Item ID", "Product Name" };
		String[][] packageCol = new String[items.size()][100];

		ImageIcon image = new ImageIcon(
				new ImageIcon(pkg.getLabel().getLabel()).getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH));

		for (int line_item = 0; line_item < items.size(); line_item++)
		{
			packageCol[line_item][0] = "" + items.get(line_item).getOrderLineItemId();
			ArrayList<HashMap<String, Object>> products = ConnectionFactory.createProductConnection()
					.getProduct(items.get(line_item).getProduct().getProductId());
			packageCol[line_item][1] = "" + products.get(0).get("productName");
		}

		// Create new Jtable for the order item list
		packageList = new JTable(packageCol, packageColNames)
		{
			public boolean editCellAt(int row, int column, EventObject e)
			{
				return false;
			}
		};
		packageList.setRowHeight(30);
		// Allow JTable of order item list to be scrollale
		packageItemPane = new JScrollPane(packageList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		packageItemPane.setAutoscrolls(true);
		packageItemPane.setMinimumSize(new Dimension(300, 300));

		// Initializing the labels and textfields to display info
		for (int i = 0; i < packageInfoLabels.length; i++)
		{
			packageInfoLabels[i] = new JLabel();
			packageInfoLabels[i].setForeground(Color.BLACK);
			if (i < 7)
			{
				packageTexts[i] = new JTextArea();
				packageTexts[i].setEditable(false);
			}
		}

		// Naming labels to display info
		packageInfoLabels[0].setText("Package ID: ");
		packageInfoLabels[1].setText("Order ID: ");
		packageInfoLabels[2].setText("Customer ID: ");
		packageInfoLabels[3].setText("Customer Name: ");
		packageInfoLabels[4].setText("Customer Address: ");
		packageInfoLabels[5].setText("Customer Phone Number: ");
		packageInfoLabels[6].setText("Customer Email: ");
		packageInfoLabels[7].setText("Order Item List: ");
		packageInfoLabels[8].setText("Status: ");
		packageInfoLabels[9].setText("Shipping Label: ");
		packageInfoLabels[10].setText("Remove: ");

		// Get information about order
		packageTexts[0].setText("" + pkg.getPackageID());
		packageTexts[1].setText("" + pkg.getOrderLineItemList().get(0).getOrderId());
		packageTexts[2].setText("" + OrderController.getOrder(pkg.getOrderLineItemList().get(0).getOrderId())
				.getCustomerInfo().getCustomerID());
		packageTexts[3].setText(""
				+ OrderController.getOrder(pkg.getOrderLineItemList().get(0).getOrderId()).getCustomerInfo()
						.getCustomerName()[0]
				+ " " + OrderController.getOrder(pkg.getOrderLineItemList().get(0).getOrderId()).getCustomerInfo()
						.getCustomerName()[1]);
		packageTexts[4].setText("" + OrderController.getOrder(pkg.getOrderLineItemList().get(0).getOrderId())
				.getCustomerInfo().getAddress());
		packageTexts[5].setText("" + OrderController.getOrder(pkg.getOrderLineItemList().get(0).getOrderId())
				.getCustomerInfo().getPhoneNumber());
		packageTexts[6].setText("" + OrderController.getOrder(pkg.getOrderLineItemList().get(0).getOrderId())
				.getCustomerInfo().getEmail());

		// contraints for gridbaglayout
		GridBagConstraints gbc = new GridBagConstraints();

		// adding the labels and textfield into the panel
		for (int i = 0; i < packageTexts.length; i++)
		{
			gbc = createGbc(0, i);
			extInfoPanel.add(packageInfoLabels[i], gbc);
			gbc = createGbc(1, i);
			extInfoPanel.add(packageTexts[i], gbc);
		}

		gbc.gridx = 0;
		gbc.gridy = 8;
		extInfoPanel.add(packageInfoLabels[7], gbc);
		gbc.gridx = 1;
		gbc.gridy = 8;
		extInfoPanel.add(packageItemPane, gbc);

		gbc.gridx = 0;
		gbc.gridy = 9;
		extInfoPanel.add(packageInfoLabels[8], gbc);
//		packageStatusComboBox = new JComboBox<String>(packageStatuses);
//		packageStatusComboBox.setSelectedIndex(pkg.getStatus());
//		packageStatusComboBox.addActionListener(new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e)
//			{
//				JComboBox<String> cb = (JComboBox<String>) e.getSource();
//				int status = cb.getSelectedIndex();
//				ConnectionFactory.createPackageConnection().updatePackageStatus(pkg.getPackageID(), status);
//				JOptionPane.showMessageDialog(null,
//						String.format("The status for Package %d has been updated.", pkg.getPackageID()));
//				FullPanel.refreshPackages(false);
//			}
//		});
		gbc.gridx = 1;
		gbc.gridy = 9;
//		extInfoPanel.add(packageStatusComboBox, gbc);
		JTextArea status = new JTextArea(packageStatuses[pkg.getStatus()]);
		status.setEditable(false);
		extInfoPanel.add(status, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 10;
		extInfoPanel.add(packageInfoLabels[9], gbc);
		gbc.gridx = 1;
		gbc.gridy = 10;
		extInfoPanel.add(new JLabel(image), gbc); // shipping label

		gbc.gridx = 0;
		gbc.gridy = 11;
		extInfoPanel.add(packageInfoLabels[10], gbc);
		JButton removeButton = new JButton("Remove this Package");
		removeButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ShipmentController.removePackage(pkg.getPackageID());
				PackageController.deletePackage(pkg.getPackageID());
				JOptionPane.showMessageDialog(null, String
						.format("Package %d has been deleted. The order items have been freed.", pkg.getPackageID()));
				FullPanel.refresh();
				deselect();
			}
		});
		gbc.gridx = 1;
		gbc.gridy = 11;
		extInfoPanel.add(removeButton, gbc);

		// Adding the extInfoPanel into JScrollPane
		extInfoScrollable = new JScrollPane(extInfoPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		extInfoScrollable.setAutoscrolls(true);

		repaint();
		add(extInfoScrollable, BorderLayout.CENTER); // This stop the labels and textfield from becoming too small when
													 // resize
		validate();
	}

	public void showShipmentsExtInfo(Shipment shipment)
	{
		clear();
		ArrayList<Package> items = shipment.getPackageList();
		System.out.println(Arrays.toString(items.toArray()));
		String[] shipmentColNames = { "Package ID", "Address" };
		String[][] shipmentCol = new String[items.size()][100];

		for (int line_item = 0; line_item < items.size(); line_item++)
		{
			shipmentCol[line_item][0] = "" + items.get(line_item).getPackageID();
			shipmentCol[line_item][1] = ""
					+ OrderController.getOrder(items.get(line_item).getOrderLineItemList().get(0).getOrderId())
							.getCustomerInfo().getAddress();
		}

		// Create new Jtable for the order item list
		shipmentList = new JTable(shipmentCol, shipmentColNames)
		{
			public boolean editCellAt(int row, int column, EventObject e)
			{
				return false;
			}
		};
		shipmentList.setRowHeight(30);
		// Allow JTable of order item list to be scrollale
		shipmentItemPane = new JScrollPane(shipmentList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		shipmentItemPane.setAutoscrolls(true);
		shipmentItemPane.setMinimumSize(new Dimension(300, 300));

		// Initializing the labels and textfields to display info
		for (int i = 0; i < shipmentInfoLabels.length; i++)
		{
			shipmentInfoLabels[i] = new JLabel();
			shipmentInfoLabels[i].setForeground(Color.BLACK);
			if (i < 2)
			{
				shipmentTexts[i] = new JTextArea();
				shipmentTexts[i].setEditable(false);
			}
		}

		// Naming labels to display info
		shipmentInfoLabels[0].setText("Shipment ID: ");
		shipmentInfoLabels[1].setText("Date: ");
		shipmentInfoLabels[2].setText("Packages: ");
		shipmentInfoLabels[3].setText("Status of packages: ");

		// Get information about order
		shipmentTexts[0].setText("" + shipment.getShipmentID());
		shipmentTexts[1].setText("" + shipment.getDateShipped().toString());

		// contraints for gridbaglayout
		GridBagConstraints gbc = new GridBagConstraints();

		// adding the labels and textfield into the panel
		for (int i = 0; i < 2; i++)
		{
			gbc = createGbc(0, i);
			extInfoPanel.add(shipmentInfoLabels[i], gbc);
			gbc = createGbc(1, i);
			extInfoPanel.add(shipmentTexts[i], gbc);
		}
		gbc.gridx = 2;
		gbc.gridy = 1;
		UtilDateModel model = new UtilDateModel();
		model.setDate(shipment.getDateShipped().getYear() + 1900, shipment.getDateShipped().getMonth(),
				shipment.getDateShipped().getDate());
		model.setSelected(true);
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		extInfoPanel.add(datePicker, gbc);

		JButton updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Calendar selectedValue = Calendar.getInstance();
				try
				{
					selectedValue.setTime((Date) datePicker.getModel().getValue());
				} catch (NullPointerException ex)
				{
					JOptionPane.showMessageDialog(null, "Date cannot be empty!");
					return;
				}
				Date selectedDate = selectedValue.getTime();
				java.sql.Date date = new java.sql.Date(selectedDate.getTime());
				if (date.getYear() + 1900 > Calendar.getInstance().get(Calendar.YEAR) || (date.getYear()
						+ 1900 == Calendar.getInstance().get(Calendar.YEAR)
						&& (date.getMonth() > Calendar.getInstance().get(Calendar.MONTH)
								|| (date.getMonth() == Calendar.getInstance().get(Calendar.MONTH)
										&& date.getDate() > Calendar.getInstance().get(Calendar.DAY_OF_MONTH)))))
				{
					update(date);
				} else
				{
					JOptionPane.showMessageDialog(null, "Date cannot be in the past!");
				}

			}

			private void update(java.sql.Date date)
			{
				ShipmentController.updateShipment(shipment.getShipmentID(), date);
				JOptionPane.showMessageDialog(null, String.format("Shipment %d has new shipment date:\n %s.",
						shipment.getShipmentID(), date.toString()));
				FullPanel.refresh();
				deselect();
			}
		});
		gbc.gridx = 2;
		gbc.gridy = 2;
		extInfoPanel.add(updateButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		extInfoPanel.add(shipmentInfoLabels[2], gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
		extInfoPanel.add(shipmentItemPane, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		extInfoPanel.add(shipmentInfoLabels[3], gbc);
		packageStatusComboBox = new JComboBox<String>(packageStatuses);
		packageStatusComboBox.setSelectedIndex(shipment.getPackageList().get(0).getStatus());
		packageStatusComboBox.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				JComboBox<String> cb = (JComboBox<String>) e.getSource();
				int status = cb.getSelectedIndex();
				for (int entry = 0; entry < shipment.getPackageList().size(); entry++)
				{
					ConnectionFactory.createPackageConnection().updatePackageStatus(shipment.getPackageList().get(entry).getPackageID(), status);
				}
				JOptionPane.showMessageDialog(null,
						String.format("The statuses for Shipment %d has been updated.", shipment.getShipmentID()));
				FullPanel.refresh();
			}
		});
		gbc.gridx = 1;
		gbc.gridy = 4;
		extInfoPanel.add(packageStatusComboBox, gbc);

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

	public class DateLabelFormatter extends AbstractFormatter
	{

		private String datePattern = "yyyy-MM-dd";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException
		{
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException
		{
			if (value != null)
			{
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}

			return "";
		}

	}
}

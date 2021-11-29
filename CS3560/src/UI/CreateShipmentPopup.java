package UI;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;

import java.awt.*;
import java.awt.event.*;
import java.security.cert.CertificateRevokedException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import Controllers.OrderController;
import Controllers.PackageController;
import Controllers.ShipmentController;
import UI.ExtendedInfo.DateLabelFormatter;

import java.util.*;
import Utility.ConnectionFactory;
import shipping.*;
import shipping.Package;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CreateShipmentPopup
{
	private final JFrame f;
	private JPanel popup;
	private JTable packageTable;
	private String[] selectedPackageRow;
	private String[] selectedPackageList;
	private JScrollPane packageTableScrollPane;
	private JButton createButton;
	private ActionListener action;
	private JTextField orderIdField;
	private ArrayList<Package> packages;
	private String[][] packageCol;
	private JDatePickerImpl datePicker;

	CreateShipmentPopup()
	{
		f = new JFrame("Create a shipment");
		popup = new JPanel();
		f.setContentPane(popup);
		SpringLayout layout = new SpringLayout();
		popup.setLayout(layout);

		String[] labels = { "Shipment Date: ", "Select Packages: ", "Finish: " };
		int numPairs = labels.length;
		packageTableScrollPane = new JScrollPane(packageTable, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		packageTableScrollPane.setAutoscrolls(true);
		packageTableScrollPane.setMinimumSize(new Dimension(600, 100));

		ArrayList<HashMap<String, Object>> available_packages = ConnectionFactory.createPackageConnection()
				.getPackageList();
		packages = new ArrayList<Package>();
		packageCol = new String[available_packages.size()][100];
		for (int pkg = 0; pkg < available_packages.size(); pkg++)
		{
			int packageId = (int) available_packages.get(pkg).get("packageId");
			packages.add(PackageController.getPackage(packageId));
			packageCol[pkg][0] = "" + packageId;
		}

		String[] packageColNames = { "Package ID" };

		// Create and populate the panel.
		for (int i = 0; i < numPairs; i++)
		{
			JLabel l = new JLabel(labels[i], JLabel.TRAILING);
			popup.add(l);
			switch (i)
				{
				case 1:// Allow JTable of order item list to be scrollale
					packageTable = new JTable(packageCol, packageColNames)
					{
						public boolean editCellAt(int row, int column, EventObject e)
						{
							return false;
						}
					};

					packageTable.getSelectionModel().addListSelectionListener(new RowListSelectionListener());
					packageTableScrollPane = new JScrollPane(packageTable,
							ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
							ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					packageTableScrollPane.setAutoscrolls(true);
					packageTableScrollPane.setMinimumSize(new Dimension(300, 300));

					popup.add(packageTableScrollPane);
					break;
				case 2:
					createButton = new JButton("Create Shipment");
					createButton.setEnabled(packages.size() == 0 ? false : true);
					createButton.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							// Get selected order item into a list when shipment is created
							if (selectedPackageRow == null)
							{
								JOptionPane.showMessageDialog(f, "Cannot create shipment: No packages selected.");
								return;
							}
							Calendar selectedValue = Calendar.getInstance();
							try
							{
								selectedValue.setTime((java.util.Date) datePicker.getModel().getValue());
							} catch (NullPointerException ex)
							{
								JOptionPane.showMessageDialog(null, "Date cannot be empty!");
								return;
							}
							java.util.Date selectedDate = selectedValue.getTime();
							java.sql.Date date = new java.sql.Date(selectedDate.getTime());
							if (validDate(date))
							{
								/* Get orderLineItem IDs from selected rows */
								selectedPackageList = new String[selectedPackageRow.length];
								for (int i = 0; i < selectedPackageRow.length; i++)
								{
									selectedPackageList[i] = (String) packageTable
											.getValueAt(Integer.parseInt(selectedPackageRow[i]), 0);
								}
								/* send database object of shipment */
								ArrayList<Package> selectedPackages = new ArrayList<Package>();
								for (int package_id = 0; package_id < selectedPackageList.length; package_id++)
								{
									selectedPackages.add(PackageController
											.getPackage(Integer.parseInt(selectedPackageList[package_id])));
								}
								if (ShipmentController.createShipment(date))
								{
									for (int pkg = 0; pkg < packages.size(); pkg++)
									{
										ShipmentController
												.addPackage(
														(int) ConnectionFactory.createShipmentConnection()
																.getLatestShipmentId().get(0).get("shipmentId"),
														packages.get(pkg).getPackageID());
									}
									JOptionPane.showMessageDialog(f, "New shipment created!");
									FullPanel.refresh();
								} else
								{
									JOptionPane.showMessageDialog(f, "Failed to create shipment.");
								}
								f.dispose();
							}
						}
					});
					popup.add(createButton);
					break;
				default:
					UtilDateModel model = new UtilDateModel();
					model.setDate(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH),
							Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
					model.setSelected(true);
					Properties p = new Properties();
					p.put("text.today", "Today");
					p.put("text.month", "Month");
					p.put("text.year", "Year");
					JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
					datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
					popup.add(datePicker);
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
			if (packageTable.getRowSelectionAllowed() && !packageTable.getColumnSelectionAllowed())
			{
				rows = packageTable.getSelectedRows();
				selected = new String[rows.length];
				for (int i = 0; i < rows.length; i++)
				{
					selected[i] = String.valueOf(rows[i]);
				}
				selectedPackageRow = Arrays.copyOf(selected, selected.length);
			}
		}

	}

	public boolean validDate(java.sql.Date date)
	{
		if (date.getYear() + 1900 > Calendar.getInstance().get(Calendar.YEAR)
				|| (date.getYear() + 1900 == Calendar.getInstance().get(Calendar.YEAR)
						&& (date.getMonth() > Calendar.getInstance().get(Calendar.MONTH)
								|| (date.getMonth() == Calendar.getInstance().get(Calendar.MONTH)
										&& date.getDate() > Calendar.getInstance().get(Calendar.DAY_OF_MONTH)))))
		{
			return true;
		} else
		{
			JOptionPane.showMessageDialog(null, "Date cannot be in the past!");
		}
		return false;

	}

	// A function that will return selectedOrderList
	public String[] getShipmentOrderList()
	{
		return selectedPackageList;
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
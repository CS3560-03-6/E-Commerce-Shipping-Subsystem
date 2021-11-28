package UI;

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;

/**
 * @author Gabriel Fok
 */

@SuppressWarnings("serial")
public class FullPanel extends JPanel
{
	private JSplitPane mainSplitPane;
	private JSplitPane splitPane;
	private static OrdersPane orderPane;
	private static PackagesPane packagePane;
	private static ShipmentsPane shipmentsPane;
	private static ExtendedInfo infoPane;
	private String id;
	private int row;
	private Timer timer;

	public FullPanel()
	{
		// Setup the overall application layout
		setLayout(new BorderLayout(10, 10));
		mainSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		mainSplitPane.setResizeWeight(0.05);
		mainSplitPane.setDividerSize(0);
		mainSplitPane.setMinimumSize(new Dimension(500, 500));
		add(mainSplitPane, BorderLayout.CENTER);

		// Adding Jpanel to the top of main split pane
		GradientPanel gradientPanel = new GradientPanel();
		mainSplitPane.add(gradientPanel, JSplitPane.TOP);

		// Add horizontal split pane to the main split pane
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setResizeWeight(0.05);
		mainSplitPane.add(splitPane, JSplitPane.BOTTOM);

		// Create extended info panel
		infoPane = new ExtendedInfo();
		splitPane.add(infoPane, JSplitPane.RIGHT, 0);

		// Create tabbed pane menu
		JTabbedPane menuPanes = new JTabbedPane();

		// Create orders tab
		orderPane = new OrdersPane();
		menuPanes.addTab("Orders", null, orderPane, "View Orders");
		menuPanes.setMnemonicAt(0, KeyEvent.VK_1);

		// Create packages tab
		packagePane = new PackagesPane();
		menuPanes.addTab("Packages", null, packagePane, "View Packages");
		menuPanes.setMnemonicAt(1, KeyEvent.VK_2);

		// Create shipments tab
		shipmentsPane = new ShipmentsPane();
		menuPanes.addTab("Shipments", null, shipmentsPane, "View Shipments");
		menuPanes.setMnemonicAt(2, KeyEvent.VK_3);

		// Clear info pane when new tab is selected
		menuPanes.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				infoPane.deselect();
			}
		});

		// Mouse Listener that activate an event if user double click
		orderPane.getTable().addMouseListener(new MouseInputAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
				{
					JTable target = (JTable) e.getSource();
					row = target.getSelectedRow();
					id = orderPane.getID(row);
					System.out.println("Showing order: " + id);
					try
					{
						infoPane.showOrderExtInfo(OrdersPane.getOrder(Integer.parseInt(id)));
					} catch (NumberFormatException ex)
					{
						System.out.println("ERROR: That order does not exist!");
					}
				}
			}
		});

		packagePane.getTable().addMouseListener(new MouseInputAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
				{
					JTable target = (JTable) e.getSource();
					row = target.getSelectedRow();
					id = packagePane.getID(row);
					System.out.println("Showing package: " + id);
					try
					{
						infoPane.showPackageExtInfo(PackagesPane.getPackage(Integer.parseInt(id)));
					} catch (NumberFormatException ex)
					{
						System.out.println("ERROR: That package does not exist!");
					}
				}
			}
		});
		shipmentsPane.getTable().addMouseListener(new MouseInputAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2)
				{
					JTable target = (JTable) e.getSource();
					row = target.getSelectedRow();
					id = shipmentsPane.getID(row);
					System.out.println("Showing shipment: " + id);
					try
					{
						ShipmentsPane.getShipment(Integer.parseInt(id));
					} catch (NumberFormatException ex)
					{
						System.out.println("ERROR: That shipment does not exist!");
					}
				}
			}
		});

		splitPane.add(menuPanes, JSplitPane.LEFT, 1);

		timer = new Timer();
		TimerTask refresh = new TimerTask()
		{
			@Override
			public void run()
			{
				refresh();
				System.out.println("Automatically refreshed data.");
			}
		};
		timer.scheduleAtFixedRate(refresh, 0L, 1800000L);
	}
	
	public static void refresh()
	{
		refreshOrders(false);
		refreshPackages(false);
		refreshShipments(false);
	}
	
	public static void refreshOrders(boolean showNotification)
	{
		orderPane.updateTable();
		if (showNotification)
			JOptionPane.showMessageDialog(null, "Refreshed orders.");
		infoPane.deselect();
	}

	public static void refreshPackages(boolean showNotification)
	{
		packagePane.updateTable();
		if (showNotification)
			JOptionPane.showMessageDialog(null, "Refreshed packages.");
		infoPane.deselect();
	}

	public static void refreshShipments(boolean showNotification)
	{
		shipmentsPane.updateTable();
		if (showNotification)
			JOptionPane.showMessageDialog(null, "Refreshed shipments.");
		infoPane.deselect();
	}
}
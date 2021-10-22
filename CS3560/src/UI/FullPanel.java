package UI;

import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/**
 * @author Gabriel Fok
 */

public class FullPanel extends JPanel
{
	private JSplitPane splitPane;
	private OrdersPane orderPane;
	private PackagesPane packagePane;
	private ShipmentsPane shipmentsPane;
	private ExtendedInfo infoPane;

	public FullPanel()
	{	
		// Setup the overall application layout
		setLayout(new BorderLayout(10, 10));
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		add(splitPane, BorderLayout.CENTER);
		
		// Create extended info panel
		infoPane = new ExtendedInfo();
		// infoPane.setPreferredSize(new Dimension(300, 200));
		splitPane.add(infoPane, JSplitPane.RIGHT, 0);
		
		// Create tabbed pane menu
		JTabbedPane menuPanes = new JTabbedPane();
		// menuPanes.setPreferredSize(new Dimension(300, 200));
		
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
		menuPanes.addChangeListener(new ChangeListener() {
	        public void stateChanged(ChangeEvent e) {
	            System.out.println("Tab: " + menuPanes.getSelectedIndex());
	            infoPane.deselect();
	        }
	    });
		
		splitPane.add(menuPanes, JSplitPane.LEFT, 1);
		
	}

}
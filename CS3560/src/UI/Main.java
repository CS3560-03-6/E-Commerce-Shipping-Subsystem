package UI;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main
{
	public static void main(String[] args)
	{
		// jframe setup
		JFrame frame = new JFrame("CS3560 Group 6 Project");
		frame.setSize(1000, 800);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setLocation(200, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new FullPanel());
		frame.setVisible(true);

		// Create Menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu createMenu = new JMenu("Create");

		fileMenu.setHorizontalTextPosition(SwingConstants.CENTER);
		fileMenu.setVerticalTextPosition(SwingConstants.BOTTOM);
		createMenu.setHorizontalTextPosition(SwingConstants.CENTER);
		createMenu.setVerticalTextPosition(SwingConstants.BOTTOM);

		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new exitListener());
		fileMenu.add(exitItem);

		JMenuItem createPackageItem = new JMenuItem("Create Package");
		JMenuItem createShipmentItem = new JMenuItem("Create Shipment");
		createPackageItem.addActionListener(new createPackageListener());
		createShipmentItem.addActionListener(new createShipmentListener());
		createMenu.add(createPackageItem);
		createMenu.add(createShipmentItem);

		menuBar.add(fileMenu);
		menuBar.add(createMenu);
		frame.setJMenuBar(menuBar);
	}

	// menu item functionality
	// File Menu: Exit
	static class exitListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}

	// Create Menu: Create Package
	static class createPackageListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			CreatePackagePopup PackagePopup = new CreatePackagePopup();
		}
	}

	// Create Menu: Create Shipment
	static class createShipmentListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(0);
		}
	}
}
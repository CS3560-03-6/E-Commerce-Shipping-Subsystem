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
		FullPanel fp = new FullPanel();
		frame.setContentPane(fp);
		

		// Create Menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		JMenu createMenu = new JMenu("Create");

		fileMenu.setHorizontalTextPosition(SwingConstants.CENTER);
		fileMenu.setVerticalTextPosition(SwingConstants.BOTTOM);
		createMenu.setHorizontalTextPosition(SwingConstants.CENTER);
		createMenu.setVerticalTextPosition(SwingConstants.BOTTOM);

		JMenuItem exitItem = new JMenuItem("Exit");
		JMenuItem refreshOrderItem = new JMenuItem("Refresh Orders");
		refreshOrderItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fp.refreshOrders();
			}
		});
		exitItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Exiting...");
				System.exit(0);
			}
		});

		fileMenu.add(exitItem);
		fileMenu.add(refreshOrderItem);

		JMenuItem createPackageItem = new JMenuItem("Create Package");
		JMenuItem createShipmentItem = new JMenuItem("Create Shipment");
		createPackageItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				CreatePackagePopup PackagePopup = new CreatePackagePopup();
			}
		});
		createShipmentItem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		createMenu.add(createPackageItem);
		createMenu.add(createShipmentItem);

		menuBar.add(fileMenu);
		menuBar.add(createMenu);
		frame.setJMenuBar(menuBar);
		
		frame.setVisible(true);
	}
}
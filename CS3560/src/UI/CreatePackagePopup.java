package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.cert.CertificateRevokedException;
import java.util.*;

public class CreatePackagePopup
{
	private final JFrame f;
	private JPanel popup;
	private JTable orderList;
	private JScrollPane orderItemPane;;
	private JButton createButton;

	CreatePackagePopup()
	{
		f = new JFrame("Create a package");
		popup = new JPanel();
		f.setContentPane(popup);
		BoxLayout layout = new BoxLayout(popup, BoxLayout.Y_AXIS);
		popup.setLayout(layout);

		String[] labels = { "Order ID: ", "Line Item IDs: " };
		int numPairs = labels.length;
		orderItemPane = new JScrollPane(orderList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		orderItemPane.setAutoscrolls(true);
		orderItemPane.setMinimumSize(new Dimension(300, 100));

		// Create and populate the panel.
		for (int i = 0; i < numPairs; i++)
		{
			JLabel l = new JLabel(labels[i], JLabel.TRAILING);
			popup.add(l);
			if (i == 1)
			{
				// Allow JTable of order item list to be scrollale
				String[] orderColNames = { "Order Item ID" };
				String[][] orderCol = new String[100][100];
				orderList = new JTable(orderCol, orderColNames);
				orderItemPane = new JScrollPane(orderList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
						ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				orderItemPane.setAutoscrolls(true);
				orderItemPane.setMinimumSize(new Dimension(300, 300));

				popup.add(orderItemPane);
			} else
			{
				JTextField textField = new JTextField(10);
				l.setLabelFor(textField);
				popup.add(textField);
			}
		}
		//SpringUtilities.makeCompactGrid(popup, numPairs, 2, 6, 6, 6, 6);

		createButton = new JButton("Create Package");
		createButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				f.dispose();
			}
		});
		popup.add(createButton);
		f.setSize(300, 300);
		f.setVisible(true);

	}
}

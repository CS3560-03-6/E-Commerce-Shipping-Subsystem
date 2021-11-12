package UI;


import javax.swing.*;
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
	private JLabel[] labels = new JLabel[10];
	private JTextField[] texts = new JTextField[9];
	private JTable orderList;
	private JScrollPane orderPane;

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
	}
	
	public void deselect()
	{
		add(backtext, BorderLayout.CENTER);
	}

	// Display the order information from selected order ID
	public void selectedIdInfo(String id) {

		setBackground(Color.GRAY);
		setLayout(new GridBagLayout());

		//Temporary manual data
		String[] orderColNames = {"Order Item ID", "Order Name"};
        String[][] orderCol = new String[100][100];

		orderCol[0][0] = "34501";
		orderCol[0][1] = "Kirland Soap";
		orderCol[1][0] = "36089";
		orderCol[1][1] = "Desk Mouse";

		//set default text to invisible
		backtext.setVisible(false);

		//Create new Jtable for the order item list
		orderList = new JTable(orderCol, orderColNames) {
			public boolean editCellAt(int row, int column, EventObject e) {
				return false;
			}
		};

		//Allow JTable of order item list to be scrollale
		orderPane = new JScrollPane(orderList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		orderPane.setAutoscrolls(true);
		orderPane.setPreferredSize(new Dimension(300, 300));

		//Initializing the labels and textfields to display info
		for (int i = 0; i < 10; i++) {
			labels[i] = new JLabel();
			labels[i].setForeground(Color.CYAN);
			if (i != 9) {
				texts[i] = new JTextField();
				texts[i].setEditable(false);
			}
		}

		//Naming labels to display info
		labels[0].setText("Order ID: ");
		labels[1].setText("Customer ID: ");
		labels[2].setText("Customer Name: ");
		labels[3].setText("Customer Address: ");
		labels[4].setText("Customer PhoneNumber: ");
		labels[5].setText("Customer Email: ");
		labels[6].setText("Order Item List: ");
		labels[7].setText("Total Shipping Cost: ");
		labels[8].setText("Total Tax: ");
		labels[9].setText("Status: ");

		//Temporary manual input of display
		texts[0].setText(id);;
		texts[1].setText("1111111");
		texts[2].setText("Andrew Jackson");
		texts[3].setText("126 Lakeview Drive\nFarmingdale, NY 11735");
		texts[4].setText("(212) 200-1503");
		texts[5].setText("AJackson1503@gmail.com");
		texts[6].setText("$4.00");
		texts[7].setText("$1.00");
		texts[8].setText("Processing");

		//contraints for gridbaglayout
		GridBagConstraints gbc = new GridBagConstraints();

		//adding the labels and textfield into the panel
		for (int i = 0; i < 6; i++) {
			gbc = createGbc(0, i);
			add(labels[i], gbc);
			gbc = createGbc(1, i);
			add(texts[i], gbc);
		}

		gbc.gridx = 0;
		gbc.gridy = 6;
		add(labels[6], gbc);
		gbc.gridx = 1;
		gbc.gridy = 7;
		add(orderPane, gbc);

		for (int i = 8; i < 11; i++) {
			gbc = createGbc(0, i);
			add(labels[i-1], gbc);
			gbc = createGbc(1, i);
			add(texts[i-2], gbc);
		}

	}

	// The method that update the display info
	public void update() {
		for (int i = 0; i < 10; i++) {
			labels[i].setVisible(false);
			if (i < 9) {
				texts[i].setVisible(false);
			}
		}
		orderList.setVisible(false);
		orderPane.setVisible(false);
		
		backtext.setVisible(true);
	}

	// The method that set GridBagContraints
	private GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        return gbc;
    }

}

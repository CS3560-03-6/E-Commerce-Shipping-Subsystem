package UI;

/**
 * @author  Gabriel Fok
 */
import javax.swing.*;
import java.awt.*;

public class PackagesPane extends JPanel
{
	/**
	 * contructor for a display
	 * 
	 */
	public PackagesPane()
	{
		setLayout(new BorderLayout());

		//Temporary array for package list
		String[] packageColNames = {"Date", "Package ID", "Name"};
        String[][] packageCol = new String[100][100];

		//Create JTable for package list
		JTable packageList = new JTable(packageCol, packageColNames);

		//Add JTable to JScrollPane to make scrollable
		JScrollPane packagePane = new JScrollPane(packageList, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        packageList.setFillsViewportHeight(true);
		packagePane.setAutoscrolls(true);
		packagePane.setPreferredSize(new Dimension(0, 0));

		//Add ScrollPane to Jpanel
		add(packagePane, BorderLayout.CENTER);
	}

}
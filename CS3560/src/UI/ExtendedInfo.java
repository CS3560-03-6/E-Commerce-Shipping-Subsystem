package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * @author Gabriel Fok
 */

public class ExtendedInfo extends JPanel
{
	private JLabel backtext;

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
}
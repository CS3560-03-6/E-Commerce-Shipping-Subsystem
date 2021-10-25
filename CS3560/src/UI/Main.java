package UI;

import javax.swing.JFrame;

public class Main
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("CS3560 Group 6 Project");
		frame.setSize(1000, 800);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setLocation(200, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new FullPanel());
		frame.setVisible(true);
	}
}
package UI;

import java.awt.*;
import javax.swing.*;

/**
 * 
 * @author Gia Quach
 */
public class GradientPanel extends JPanel {
    private Color startColor = Color.decode("#EB01EB");
    private Color endColor = Color.decode("#02EBF6");
    private int gradientFocus = 1200;

    private ImageIcon image;
    private JLabel title = new JLabel();

    public GradientPanel() {

        image = new ImageIcon("image/logoImage.png");
        Image tmpImage = image.getImage();
        Image resizeImage = tmpImage.getScaledInstance(300, 100, Image.SCALE_SMOOTH);
        image = new ImageIcon(resizeImage);

        title.setHorizontalAlignment(SwingConstants.LEFT);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setIcon(image);
        title.setIconTextGap(10);
        title.setText("Welcome to Fast Delivery");
        title.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 65));
        title.setForeground(Color.BLACK);

        add(title, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();

        GradientPaint gp = new GradientPaint(0, 0, startColor, gradientFocus, h, endColor);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}
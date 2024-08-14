package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundBorderLabel extends JLabel {

    private final int arcWidth;
    private final int arcHeight;

    public RoundBorderLabel(ImageIcon imageIcon, int arcWidth, int arcHeight) {
        super(imageIcon);
        this.arcWidth = arcWidth;
        this.arcHeight = arcHeight;
        setOpaque(false); // Make label transparent so background is visible
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the image
        if (getIcon() != null) {
            Image image = ((ImageIcon) getIcon()).getImage();
            g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        }

        // Draw a rounded border
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(5)); // Border thickness
        RoundRectangle2D roundedRectangle = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
        g2d.draw(roundedRectangle);
    }
}
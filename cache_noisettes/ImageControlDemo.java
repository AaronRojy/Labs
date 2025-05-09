import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;

public class ImageControlDemo extends JPanel implements KeyListener {
    private Image image;
    private boolean visible = true;
    private int x = 100;
    private int y = 100;
    private double angle = 0; // in radians

    public ImageControlDemo() {
        image = new ImageIcon("RedSquirrel1.png").getImage(); // Ensure this image is in the project folder
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.WHITE);
        setFocusable(true);
        addKeyListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (visible && image != null) {
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform transform = new AffineTransform();
            transform.translate(x, y);
            transform.rotate(angle, image.getWidth(null) / 2.0, image.getHeight(null) / 2.0);
            g2d.drawImage(image, transform, this);
        }
    }

    // Key controls
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> y -= 10;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> y += 10;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> x -= 10;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> x += 10;
            case KeyEvent.VK_R -> angle += Math.toRadians(90);
            case KeyEvent.VK_H -> visible = false;
            case KeyEvent.VK_J -> visible = true;
        }
        repaint();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Image Control Demo");
        ImageControlDemo panel = new ImageControlDemo();
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        panel.requestFocusInWindow(); // Ensures the panel gets keyboard focus
    }
}

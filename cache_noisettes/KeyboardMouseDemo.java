import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class KeyboardMouseDemo extends JFrame implements KeyListener, MouseListener {

    JLabel label;

    public KeyboardMouseDemo() {
        setTitle("Keyboard and Mouse Listener Demo");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        label = new JLabel("Press a key or click the mouse", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        add(label, BorderLayout.CENTER);

        // Make sure the frame is focusable for key events
        setFocusable(true);
        requestFocusInWindow();

        // Register listeners
        addKeyListener(this);
        addMouseListener(this);

        setVisible(true);
    }

    // KeyListener methods
    @Override
    public void keyTyped(KeyEvent e) {
        label.setText("Key Typed: " + e.getKeyChar());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        label.setText("Key Pressed: " + e.getKeyChar());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        label.setText("Key Released: " + e.getKeyChar());
    }

    // MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {
        label.setText("Mouse Clicked at (" + e.getX() + ", " + e.getY() + ")");
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // not used
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // not used
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KeyboardMouseDemo());
    }
}


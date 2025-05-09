
/**
 * Write a description of class gui here.
 *
 * @Aaron Rojy
 */
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;

public class gui
{
    public static void main(String[]args){
        //JFrame = a GUI windoe to add components to
        /*
        JFrame frame = new JFrame();//create a frame 
        frame.setTitle("cache noisettes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes application in close
        frame.setSize(1000,900);// sets x and y direction of frame
        frame.setVisible(true);//makes visible
        frame.setResizable(true);// prevent frame from being resizes, probible not actualy usfull
        
        ImageIcon image = new ImageIcon("logo.png");//create an image icon
        frame.setIconImage(image.getImage());
        frame.getContentPane().setBackground(new Color(255,255,255));//RGB values
        */
             
        //# Label ------------------------------------------------------------------------------
       
        JLabel label = new JLabel(); 
        label.setText("Test--------------------");
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setForeground(new Color(0x00FF00));
        
        //# Frame ------------------------------------------------------------------------------
        MyFrame myFrame = new MyFrame();
        JFrame frame = new JFrame();//create a frame 
        frame.setTitle("cache noisettes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes application in close
        frame.setSize(800,700);// sets x and y direction of frame
        frame.setVisible(true);//makes visible
        frame.setResizable(true);// prevent frame from being resizes, probible not actualy usfull
        frame.add(label);
  
        
        
        
        
    }
}

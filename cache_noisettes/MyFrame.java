import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.awt.Color;
/**
 * Write a description of class myFrame here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class MyFrame extends JFrame
{
    MyFrame()
    {
        this.setTitle("cache noisettes");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes application in close
        this.setResizable(false);// prevent this from being resizes, probible not actualy usfull
        this.setSize(1500,750);// sets x and y direction of this
        this.setVisible(true);//makes visible
              
        ImageIcon image = new ImageIcon("logo.png");//create an image icon
        this.setIconImage(image.getImage());
        this.getContentPane().setBackground(new Color(255,255,255));//RGB values
        
        
    } 
}

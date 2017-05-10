
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Castle
 */
public class AnaClass {
    
    public static void main(String args[])
    {
        JFrame pen = new JFrame();
        pen.add(new Pencere());
        pen.setVisible(true);
        pen.setSize(800,600);
        pen.setResizable(false);
        pen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pen.setFocusable(true);
        pen.requestFocusInWindow();
        

        
    }
    
}

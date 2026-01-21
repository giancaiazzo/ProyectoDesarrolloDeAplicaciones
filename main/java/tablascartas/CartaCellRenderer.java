/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tablascartas;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Baldassini, Jorge / CTC - ORT
 */
public class CartaCellRenderer extends JButton implements TableCellRenderer{
    private ActionListener escucha;
    
    public CartaCellRenderer(ActionListener escucha){
        this.escucha = escucha;
        this.addActionListener(escucha);
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        ImageIcon image;
        if(isSelected)
        {
            image = new ImageIcon(System.getProperty("user.dir") + "\\build\\classes\\tablascartas\\cartas\\Invertida.gif" );  
        }
        else{
//            System.out.println(  "ver" + System.getProperty("user.dir") + "\\build\\classes\\tablascartas\\cartas");
            image = new ImageIcon(System.getProperty("user.dir") + "\\build\\classes\\tablascartas\\cartas\\" + value.toString());  
        }
//        int scale = 3;  
//        int width = image.getIconWidth();  
//        int height = image.getIconHeight();  
//        BufferedImage buffer = new BufferedImage(scale * width, scale * height, BufferedImage.TYPE_INT_ARGB);  
//        Graphics2D graphics = buffer.createGraphics();  
//        graphics.scale(scale,scale);  
//        image.paintIcon(null, graphics, 0, 0);  
//        graphics.dispose();  
        this.setIcon(image);
        return this;
        //JLabel label = new JLabel(new ImageIcon(buffer)));  
    }
    
}

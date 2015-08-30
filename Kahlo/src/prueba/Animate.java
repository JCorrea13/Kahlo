/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author JCORREA
 */
public class Animate extends ExitableJFrame{

    private static final int DELAY = 50;
    Image buffer;
    Insets insets;
    Dimension dimencion;
    private final Color colors[] =
    {
        Color.RED,
        Color.ORANGE,
        Color.YELLOW,
        Color.GREEN,
        Color.BLUE,
        Color.MAGENTA
    }; 

    public Animate(String title) throws HeadlessException {
        super(title);
    }
    
    @Override
    public void paint(Graphics g){
        //super.paint(g);
        
        if(dimencion == null || dimencion != getSize()){
            dimencion = getSize();
            buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        }
        insets = insets == null? getInsets(): insets;
        
        //Calcula cada pasada por si hay que modificar el tamanio
        int x = insets.left;
        int y = insets.top;
        int width = getWidth() - insets.left - insets.right;
        int height = getHeight() - insets.top - insets.bottom;
        int start = 0;
        int steptSize = 360/ colors.length;
        
        
        synchronized(colors){
            Graphics bg = buffer.getGraphics();
            bg.setColor(Color.WHITE);
            bg.fillRect(x, y, width, height);
            for (Color color : colors) {
                bg.setColor(color);
                bg.fillArc(x, y, width, height, start, steptSize);
                start += steptSize;
            }
        }
        g.drawImage(buffer, 0, 0, this);
    }   
    
    public void go(){
        TimerTask t = new TimerTask(){
            @Override
            public void run() {
                Color c = colors[0];
                synchronized(colors){
                    System.arraycopy(colors, 1, colors, 0, colors.length - 1);
                    colors[colors.length - 1] = c;
                }
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(t, 0, DELAY);
    }
    
    public static void main(String args[]){
        System.out.println("Inicio");
        Animate a = new Animate("Animacion");
        a.setSize(200, 200);
        a.setVisible(true);
        a.go();
        System.out.println("Fin");
    }
}

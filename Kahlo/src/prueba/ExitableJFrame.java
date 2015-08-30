/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prueba;

import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author JCORREA
 */
public class ExitableJFrame extends JFrame{

    public ExitableJFrame(String title) throws HeadlessException {
        super(title);
    }
   
    @Override
    protected void frameInit(){
        super.frameInit();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    
}

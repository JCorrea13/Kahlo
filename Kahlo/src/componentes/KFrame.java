package componentes;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;
import config.Config;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;
import kahlo.AbrirMision;
import kahlo.ConfiguracionMision;
import kahlo.PanelMonitoreo;

/**
 *
 * @author JCORREA
 */
public class KFrame extends javax.swing.JFrame{

    private Insets insets;
    public static JMenuBar menuBar = new JMenuBar();
    private static JFrame mapa;
    private static Browser browser;
    
    public KFrame(String title) throws HeadlessException {
        this(title, false);
    }
    
    public KFrame(String title, boolean menu) throws HeadlessException {
        super(title + " - Kahlo ");
         
        setIconImage(new ImageIcon("src/recursos/imagen/Kahlo.png").getImage());
        if(menu) setMenu();
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        if(insets == null) insets = getInsets();
    }

    public JMenuBar getMenu() {
        return menuBar;
    }
    
    public Dimension menuBarSize(){
        return menuBar.getSize();
    }
    
    private void setMenu(){
        JMenu m = new JMenu("Menu");
        m.setMnemonic(KeyEvent.VK_M);
        menuBar.add(m);
        
        JMenuItem mINuevaMision =  new JMenuItem("Nueva Mision", KeyEvent.VK_N);
        mINuevaMision.addActionListener( new ActionListener() {
        
            @Override
            public void actionPerformed(ActionEvent e) {
                ConfiguracionMision.lanzarPanelConfiguracion();
            }
        });
        JMenuItem mIAbrirMision =  new JMenuItem("Abrir Mision", KeyEvent.VK_A);
        mIAbrirMision.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                AbrirMision.lanzarPanelConfiguracion();
            }
        });
        JMenuItem mIConfig =  new JMenuItem("Configuraciones", KeyEvent.VK_C);
        mIConfig.addActionListener( new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Config.lanzaPanelConfiguraiones();
            }
        });
        JMenuItem mISalir =  new JMenuItem("Salir", KeyEvent.VK_S);        
        mISalir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(!PanelMonitoreo.inMision)
                    System.exit(0);
                
                PanelMonitoreo pm = PanelMonitoreo.panelMonitoreo();
                pm.setPanelDatosMisionSeleccionado();
            }
        });
        JMenuItem mIMapa = new JMenuItem("Localizacion", KeyEvent.VK_L);
        mIMapa.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                getMapa();
                mapa.setVisible(true);
            }
        });
                
        m.add(mINuevaMision);
        m.add(mIAbrirMision);
        m.add(mIConfig);
        m.add(mIMapa);
        m.add(mISalir);
        setJMenuBar(menuBar);
    }
    
    /**
     * Este metodo regresa el mapa del kframe
     * @return jframe
     */
    private JFrame getMapa(){
        
        if(mapa != null)
            return mapa;
        
        browser = new Browser();
        BrowserView browserView = new BrowserView(browser);

        mapa = new KFrame("Mapa");
        mapa.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        mapa.add(browserView, BorderLayout.CENTER);
        mapa.setSize(700, 500);
        mapa.setLocationRelativeTo(null);

        browser.loadURL("http://www.maps.google.com");
        return mapa;
    }

    /**
     * Este metodo regresa el browser del kframe
     * @return 
     */
    public Browser getBrowser() {
        return browser;
    }
}

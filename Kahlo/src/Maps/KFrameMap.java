package Maps;

import componentes.KFrame;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 * Esta clase se encarga de mostrar el mapa en la aplicacion
 * (solo se puede crear una instacia de esta clase)
 * @author JCORREA
 */
public class KFrameMap extends KFrame {
    
    //puntos preestablecidos de aterrizaje
    private static ArrayList<Punto> pPreestablecidos = new ArrayList();
    private static Punto puntoActual;
    private static KFrameMap frameMapa;
    private Timer t;
    
    public KFrameMap() {
        super("Mapa");
        initComponents();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        t = new Timer();
        t.schedule(new TimerTask() {

            @Override
            public void run() {
                actualizarMapa();
            }
        }, 0, 7000);
    }
    
    public static KFrameMap getMapa(){
        if(frameMapa == null)
            frameMapa = new KFrameMap();
        
        return frameMapa;
    }
    
    public void addPuntoPreestablecido(Punto p){
        if(p != null)
            Geocoder.addPuntoControlMapa(p);
    }
    
    /**
     * Este metodo carga todos los putos
     * y manda a pintar nuevamente el mapa
     */
    public synchronized void actualizarMapa(){
        Geocoder.clear();
        for(Punto p: pPreestablecidos){
            addPuntoPreestablecido(p);
        }
        
        if(puntoActual != null){
            addPuntoPreestablecido(puntoActual);
        }
        
        repaint();
    }
    
    @Override
    public void paint(Graphics g){
        super.paint(g);
        try {
            if(!pPreestablecidos.isEmpty())
                g.drawImage(new ImageIcon(Geocoder.createImageMap()).getImage(),0,0,pnlMapa.getWidth(),pnlMapa.getHeight(), this);
        } catch (IOException ex) {
            Logger.getLogger(KFrameMap.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setPuntoActual(Punto puntoActual) {
        if(puntoActual != null)
            KFrameMap.puntoActual = puntoActual;    
    }

    public static void setpPreestablecidos(ArrayList<Punto> pPreestablecidos) {
        KFrameMap.pPreestablecidos = pPreestablecidos;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlMapa = new javax.swing.JPanel();
        btnActualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout pnlMapaLayout = new javax.swing.GroupLayout(pnlMapa);
        pnlMapa.setLayout(pnlMapaLayout);
        pnlMapaLayout.setHorizontalGroup(
            pnlMapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        pnlMapaLayout.setVerticalGroup(
            pnlMapaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 257, Short.MAX_VALUE)
        );

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMapa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnActualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMapa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnActualizar)
                .addGap(10, 10, 10))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        actualizarMapa();
    }//GEN-LAST:event_btnActualizarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JPanel pnlMapa;
    // End of variables declaration//GEN-END:variables
}
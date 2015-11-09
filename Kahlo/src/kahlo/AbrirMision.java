package kahlo;

import componentes.KDialog;
import componentes.KFrame;
import config.Config;
import config.Configuracion;
import config.ManejadorConfiguarciones;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import util.ManejadorArchivos;

/**
 *
 * @author JCORREA
 */
public class AbrirMision extends KFrame {

    private static  AbrirMision dialogo;
    public static String rutaArchivoMision;
    public static String rutaArchivoDatosConfig;
    public static int periodo;
    ManejadorArchivos ma = new ManejadorArchivos();
    
    private AbrirMision() {
        super("Abrir Mision");
        initComponents();
    }
    
        /**
     * Este metodo sirve para lanzar el panel de abrir mision y asegrar que
     * solo se cree un panel
     * @return panel abrir mision
     */
    private static AbrirMision panelAbrirMision() {
        if (dialogo == null) {
            dialogo = new AbrirMision();
        }
        return dialogo;
    }
    
    public static void lanzarPanelConfiguracion() {
        panelAbrirMision().setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtRuta = new javax.swing.JTextField();
        btnRuta = new javax.swing.JButton();
        lblRuta = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        sliPeriodo = new javax.swing.JSlider();
        lblPeriodo = new javax.swing.JLabel();
        lblValorPeriodo = new javax.swing.JLabel();
        cboTipo = new javax.swing.JComboBox();
        lblTipo = new javax.swing.JLabel();

        txtRuta.setEditable(false);

        btnRuta.setText("...");
        btnRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRutaActionPerformed(evt);
            }
        });

        lblRuta.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblRuta.setText("Ruta");

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        sliPeriodo.setMaximum(7000);
        sliPeriodo.setMinimum(100);
        sliPeriodo.setPaintLabels(true);
        sliPeriodo.setToolTipText("milisegundos");
        sliPeriodo.setValue(1000);
        sliPeriodo.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
                sliPeriodoAncestorMoved(evt);
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        sliPeriodo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliPeriodoStateChanged(evt);
            }
        });

        lblPeriodo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPeriodo.setText("Periodo (Milisegundos)");

        lblValorPeriodo.setText("1000");

        cboTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Resumen (.kar)", "Bruto (.kab)"}));

        lblTipo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTipo.setText("Tipo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(lblRuta))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblTipo)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboTipo, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)
                                .addComponent(btnRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblPeriodo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 73, Short.MAX_VALUE)
                                .addComponent(lblValorPeriodo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sliPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnAceptar)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRuta)
                    .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRuta))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTipo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPeriodo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblValorPeriodo))
                        .addGap(7, 7, 7))
                    .addComponent(sliPeriodo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addComponent(btnAceptar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private JFileChooser chooser;
    private void btnRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRutaActionPerformed
        Configuracion c = ManejadorConfiguarciones.getConfiguracion(Config.CONF_K_DIR_TRABAJO);
        File f = new File(c != null? c.getValor():".");
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(f.exists()?f: new File("."));
        chooser.setDialogTitle("Ruta");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            txtRuta.setText(chooser.getSelectedFile().toString());
        } else {
            System.out.println("ninguna ruta fue seleccionada");
        }
    }//GEN-LAST:event_btnRutaActionPerformed

    private void sliPeriodoAncestorMoved(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_sliPeriodoAncestorMoved
    }//GEN-LAST:event_sliPeriodoAncestorMoved

    private void sliPeriodoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliPeriodoStateChanged
        lblValorPeriodo.setText("" + sliPeriodo.getValue());
    }//GEN-LAST:event_sliPeriodoStateChanged

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        String formato = null;
        if(cboTipo.getSelectedIndex() == 0) formato = ".kar";
        else if(cboTipo.getSelectedIndex() == 1) formato = ".kab";
        
        AbrirMision.rutaArchivoMision = txtRuta.getText()+ "\\" +  chooser.getSelectedFile().getName() + formato;
        AbrirMision.rutaArchivoDatosConfig = txtRuta.getText()+ "\\" +  chooser.getSelectedFile().getName() + ".ka";
        AbrirMision.periodo = sliPeriodo.getValue();
        if(ma.existeAchivo(AbrirMision.rutaArchivoMision) && ma.existeAchivo(AbrirMision.rutaArchivoDatosConfig)){
            PanelMonitoreo pm = PanelMonitoreo.panelMonitoreo();
            try {
                leerArchivoDatosMision(AbrirMision.rutaArchivoDatosConfig);
            } catch (IOException ex) {
                new KDialog(this, "Error al abrir archivo: " + AbrirMision.rutaArchivoDatosConfig , KDialog.Tipo.ALERTA).setVisible(true);
                Logger.getLogger(AbrirMision.class.getName()).log(Level.SEVERE, null, ex);
            }
            pm.inicioMision(false); //---DESCOMENTAR ESTA LINEA
        }else{
            new KDialog(this, "No se encontro el archivo: " + AbrirMision.rutaArchivoMision +
                    " o " + AbrirMision.rutaArchivoDatosConfig , KDialog.Tipo.ALERTA).setVisible(true);
        }
        setVisible(false);
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void leerArchivoDatosMision(String ruta) throws IOException{
        ArrayList<String> datos = ma.getContenidoArchivoToArray(ruta);
        try{
            ManejoDatos.knombreMision = (String)datos.get(0).substring(1,datos.get(1).length() -1);
            ManejoDatos.kinicioMision = (String)datos.get(1).substring(1,datos.get(2).length() -1);
            ManejoDatos.kdescripcionMision = (String)datos.get(2).substring(1,datos.get(3).length() -1);
            ManejoDatos.kfinMision = (String)datos.get(3).substring(1,datos.get(4).length() -1);
        }catch(Exception ex){}
    }
    
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(AbrirMision.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(AbrirMision.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(AbrirMision.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(AbrirMision.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new AbrirMision().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnRuta;
    private javax.swing.JComboBox cboTipo;
    private javax.swing.JLabel lblPeriodo;
    private javax.swing.JLabel lblRuta;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblValorPeriodo;
    private javax.swing.JSlider sliPeriodo;
    private javax.swing.JTextField txtRuta;
    // End of variables declaration//GEN-END:variables
}

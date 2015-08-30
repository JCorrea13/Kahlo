package kahlo;

import componentes.KFrame;
import config.Config;
import config.ManejadorConfiguarciones;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Stack;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import config.Configuracion;
import java.io.File;

/**
 *
 * @author JCORREA
 */
public class ConfiguracionMision extends KFrame {

    private static ConfiguracionMision dialogo = null;

    public ConfiguracionMision() {
        super("Configuracion Mision");
        initComponents();
        //este metodo carga los componentes
        cargarComponentes();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        checkPrueba.addItemListener(new ItemListener(){

            @Override
            public void itemStateChanged(ItemEvent e) {
                if(checkPrueba.getState()){
                    Configuracion cPref = ManejadorConfiguarciones.getConfiguracion(config.Config.CONF_K_PREF_PRUEBAS);
                    Configuracion cVar = ManejadorConfiguarciones.getConfiguracion(config.Config.CONF_K_VAR_PRUEBAS);
                    txtNombreMision.setText(cPref.getValor() + "_" + (cVar.getValor() == null || cVar.getValor().isEmpty() ? "1": Integer.valueOf(cVar.getValor()) + 1));
                    txtNombreMision.setEnabled(false);
                }else{
                    txtNombreMision.setText("");
                    txtNombreMision.setEnabled(true);
                }
            }
        });
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
//            java.util.logging.Logger.getLogger(ConfiguracionMision.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(ConfiguracionMision.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(ConfiguracionMision.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(ConfiguracionMision.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new ConfiguracionMision().setVisible(true);
//            }
//        });
//    }

    /**
     * Este metodo sirve para lanzar el panel de configuracion y asegrar que
     * solo se cree un panel
     *
     * @return panel de configuracion
     */
    private static ConfiguracionMision panelConfiguracion() {
        if (dialogo == null) {
            dialogo = new ConfiguracionMision();
        }
        return dialogo;
    }

    /**
     * Este metod carga los combobox con la informaicon necesaria
     */
    private void cargarComponentes() {
        limpiarComponentes();
        Stack puertos = LecturaPuerto.listaPuertos();
        for (Object p : puertos) {
            cboPuerto.addItem(p);
        }

        String[] velocidades = {"4800", "9600", "14400", "19200", "28800", "38400", "57600", "115200"};
        for (String v : velocidades) {
            cboVelocidad.addItem(v);
        }
        
        cboPuerto.setSelectedItem(null);
        cboVelocidad.setSelectedItem(null);

        if (cboPuerto.getItemCount() == 0) {
            cboPuerto.setEnabled(false);
            cboVelocidad.setEnabled(false);
        }
        
        //Seteamos las configuraciones
        txtRuta.setText(ManejadorConfiguarciones.getConfiguracion(config.Config.CONF_K_DIR_TRABAJO).getValor());
    }

    /**
     * Este metodo limpia los componentes
     */
    private void limpiarComponentes() {
        cboPuerto.removeAllItems();
        cboVelocidad.removeAllItems();
        cboPuerto.setEnabled(true);
        cboVelocidad.setEnabled(true);
    }

    public static void lanzarPanelConfiguracion() {
        panelConfiguracion().setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPuerto = new javax.swing.JLabel();
        cboPuerto = new javax.swing.JComboBox();
        lblVelocidad = new javax.swing.JLabel();
        cboVelocidad = new javax.swing.JComboBox();
        btnAceptar = new javax.swing.JButton();
        btnRecargar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtRuta = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNombreMision = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaDescripcion = new javax.swing.JTextArea();
        btnRuta = new javax.swing.JButton();
        checkPrueba = new java.awt.Checkbox();
        jCheckBox1 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblPuerto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPuerto.setText("Puerto");

        lblVelocidad.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblVelocidad.setText("Velocidad");

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnRecargar.setText("jButton1");
        btnRecargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRecargarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Ruta");

        txtRuta.setEditable(false);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Nombre Mision");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Descripcion");

        txtAreaDescripcion.setColumns(20);
        txtAreaDescripcion.setRows(5);
        jScrollPane1.setViewportView(txtAreaDescripcion);

        btnRuta.setText("...");
        btnRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRutaActionPerformed(evt);
            }
        });

        checkPrueba.setLabel("Test");

        jCheckBox1.setText("jCheckBox1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAceptar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(lblVelocidad)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cboVelocidad, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(lblPuerto)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(checkPrueba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel2)
                                                    .addComponent(jLabel1))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtNombreMision, javax.swing.GroupLayout.PREFERRED_SIZE, 374, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(btnRuta))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(cboPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(btnRecargar, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombreMision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRuta))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkPrueba, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboPuerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRecargar)
                    .addComponent(lblPuerto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboVelocidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblVelocidad))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAceptar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRecargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRecargarActionPerformed
        cargarComponentes();
    }//GEN-LAST:event_btnRecargarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        LecturaPuerto lp = new LecturaPuerto();
        if (LecturaPuerto.abrirPuerto(cboPuerto.getSelectedItem().toString())) {    
            if (lp.configurarPuerto(Integer.parseInt(cboVelocidad.getSelectedItem().toString()), 8, 1)) {    
                if(ManejoDatos.initManejoDatos(txtRuta.getText() ,txtNombreMision.getText()
                        ,txtAreaDescripcion.getText())){
                    PanelMonitoreo.lanzadorPanelMonitoreo();
                    //aqui se debe iniciar la mision
                    PanelMonitoreo pm = PanelMonitoreo.panelMonitoreo();
                    pm.inicioMision(true); //---DESCOMENTAR ESTA LINEA
                    setVisible(false);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo configurar el puerto", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRutaActionPerformed
        
        JFileChooser chooser;
        Configuracion c = ManejadorConfiguarciones.getConfiguracion(Config.CONF_K_DIR_TRABAJO);
        File f = new File(c != null? c.getValor():".");
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(f.exists()?f: new File("."));
        chooser.setDialogTitle("Ruta");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        chooser.setAcceptAllFileFilterUsed(false);
        //    
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            txtRuta.setText(chooser.getSelectedFile().toString());
        } else {
            System.out.println("ninguna ruta fue seleccionada");
        }
    }//GEN-LAST:event_btnRutaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnRecargar;
    private javax.swing.JButton btnRuta;
    private javax.swing.JComboBox cboPuerto;
    private javax.swing.JComboBox cboVelocidad;
    private java.awt.Checkbox checkPrueba;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPuerto;
    private javax.swing.JLabel lblVelocidad;
    private javax.swing.JTextArea txtAreaDescripcion;
    private javax.swing.JTextField txtNombreMision;
    private javax.swing.JTextField txtRuta;
    // End of variables declaration//GEN-END:variables
}

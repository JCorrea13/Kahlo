/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kahlo;
import componentes.KFrame;

/**
 * Esta clase sirve como consola y tiene el unico objetivo de mostrar
 * los datos que se estan recibiendo de menera bruta en tiempo real
 * (Solo se puede crear una instacia de esta clase)
 * @author JCORREA
 */
public class  ConsolaKahlo extends KFrame {

    private static ConsolaKahlo consola= null;
    
    private ConsolaKahlo() {
        super("Consola Kahlo");
        initComponents();
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }
    
    /**
     * Este metodo regresa la unica instacia que se puede crear de 
     * ConsolaKahlo
     * @return ConsolaKahlo
     */
    public static ConsolaKahlo getConsolaKahlo(){
        
        if(consola == null)
            consola = new ConsolaKahlo();
        
        return consola;
    }
    
    /**
     * Este metodo agrega en la consola los datos que 
     * pasan como parametro
     * @param datos String
     */
    public void add_datos(String datos){
        txtConsola.setText(txtConsola.getText()+ datos);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtConsola = new javax.swing.JTextPane();
        btnCerrar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtConsola.setBackground(new java.awt.Color(0, 0, 0));
        txtConsola.setForeground(new java.awt.Color(255, 255, 255));
        txtConsola.setText("Consola Kahlo");
        txtConsola.setAutoscrolls(false);
        jScrollPane1.setViewportView(txtConsola);

        btnCerrar.setText("Cerrar");
        btnCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 665, Short.MAX_VALUE)
                    .addComponent(btnCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCerrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_btnCerrarActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane txtConsola;
    // End of variables declaration//GEN-END:variables
}

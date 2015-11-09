package kahlo;

import componentes.KDialog;
import componentes.KFrame;
import static kahlo.LecturaPuerto.entrada;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Millisecond;
import util.ManejadorArchivos;
/**
 * Esta clase muestra los datos que se envian
 * que recibe por el puerto serial
 * @author JCORREA
 */
public class PanelMonitoreo extends KFrame implements SerialPortEventListener{
    
    private static PanelMonitoreo pMonitoreo= null;
    Calendar fecha = new GregorianCalendar();
    ImageIcon iconoFalla = new javax.swing.ImageIcon(getClass().getResource("/Recursos/ImgNoComunicacion.png"));
    ImageIcon iconoComunicacion = new javax.swing.ImageIcon(getClass().getResource("/Recursos/ImgComunicacion.jpg"));
    ImageIcon iconoApagado = new javax.swing.ImageIcon(getClass().getResource("/Recursos/ImgApagado.jpg"));
    public static boolean inMision = false;
    ConsolaKahlo ck = null;
    
    //<editor-fold defaultstate="collapsed" desc=" Colecciones  de datos actuales ">
        DefaultCategoryDataset d = new DefaultCategoryDataset();
    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" Declaracion de Graficas ">
        Grafica graficaGeneral = new Grafica(tipoGrafica.BarraEstado, "General");
        Grafica graficaTemperaturaE = new Grafica(tipoGrafica.LinialTiempoAltura, "Temperatura Externa");
        Grafica graficaTemperaturaI = new Grafica(tipoGrafica.LinialTiempoAltura, "Temperatura Interna");
        Grafica graficaHumedad = new Grafica(tipoGrafica.LinialTiempoAltura, "Humedad");
        Grafica graficaPresion = new Grafica(tipoGrafica.LinialTiempoAltura, "Presion");
        Grafica graficaServoBrazoD = new Grafica(tipoGrafica.LinialTiempoAltura, "Brazo Derecho");
        Grafica graficaServoBrazoI = new Grafica(tipoGrafica.LinialTiempoAltura, "Brazo Izquierdo");
        Grafica graficaServoLiberacion = new Grafica(tipoGrafica.LinialTiempoAltura, "Servo Liberacion");
        Grafica graficaBateria = new Grafica(tipoGrafica.LinialTiempoAltura, "Bateria");
    //</editor-fold>
    
    private PanelMonitoreo() {
        super("Kahlo", true);
        initComponents();
        inicioPaneles();
        this.setBackground(java.awt.Color.CYAN);
        lblBanderaComunicacion.setIcon(iconoFalla);    
        ck = ConsolaKahlo.getConsolaKahlo();
    }
    
    /**
     * Este metodo agrega el listener al seria configurado 
     * para recibir los datos
     */
    private void inicioSerialPortEventListener(){
        try {  
         LecturaPuerto.puertoSerie.addEventListener(this);  
        } catch (TooManyListenersException e) {}
        
        Timer t = new Timer();
        t.schedule(new ListenerSerial(), 0, 300);
    
    }
    
    /**
     * Este metodo inicia la mision, inicia el listener del serial
     * y el cronometro
     * @param mision badera para indicar si es una mision o solo se
     * lera un archivo
     */
    public void inicioMision(boolean mision){
        if(mision){
            inicioSerialPortEventListener();
            btnFinMision.setEnabled(true);
            inMision = true;
        }else{
            btnFinMision.setEnabled(false);
            inicioLecturaArchivo();
            iniciaPanelDatosMision();
            inMision = false;
        }
        
        new Timer("Cronometro", true).scheduleAtFixedRate(new Subprecesos.Cronometro(lblCronometro), 0, 1000);
    }
    
     /**
     * Este metodo sirve para lanzar el panel de monitoreo
     * y asegrar que solo se cree un panel
     * @return panel de configuracion
     */
    public static PanelMonitoreo panelMonitoreo(){
        if(pMonitoreo == null)
            pMonitoreo = new PanelMonitoreo();
        
        return pMonitoreo;
    }
    
    /**
     * Este metodo lanza (hace visible) el panel de monitoreo
     */
    public static void lanzadorPanelMonitoreo(){
        PanelMonitoreo pm = panelMonitoreo();
        pm.setVisible(true);
    }
    
    /**
     * Este metodo inicializa todas las graficas y las agrega al 
     * panel de monitoreo
     */
    private void inicioPaneles(){
       //inicio panel General
       graficaGeneral.actualizarGraficaBarra("Graficas General", d);
       jTabContenedorPaneles.add(graficaGeneral.obtenerPanel());
       
       //Inicio panel datos brutos
       jPanelDatosBrutos.setName("Datos Generales");
       jTabContenedorPaneles.add(jPanelDatosBrutos);
       
       //inicializamos panel de temperatura
       jTabContenedorPaneles.add(graficaTemperaturaE.obtenerPanel());
      
        //inicializamos panel de temperatura
       jTabContenedorPaneles.add(graficaTemperaturaI.obtenerPanel());
       
       //inicializamos panel de humedad
       jTabContenedorPaneles.add(graficaHumedad.obtenerPanel());
       
       //inicializamos panel de presion 
       jTabContenedorPaneles.add(graficaPresion.obtenerPanel());
       
       //inicialiamon panel de Velocidad 
       jTabContenedorPaneles.add(graficaServoBrazoD.obtenerPanel());
       
       //inicialiamon panel de Velocidad 
       jTabContenedorPaneles.add(graficaServoBrazoI.obtenerPanel());
       
       //inicialiamon panel de Velocidad 
       jTabContenedorPaneles.add(graficaServoLiberacion.obtenerPanel());
       
       //inicializamos panel de Bateria 
       jTabContenedorPaneles.add(graficaBateria.obtenerPanel());
       
       iniciaPanelDatosMision();
    }
    
    private void iniciaPanelDatosMision(){
       //inicializamos el panel de los datos de la mision
       lblNombreMision.setText( ManejoDatos.knombreMision);
       lblInicionMisioin.setText(ManejoDatos.kinicioMision);
       txtAreaDescripcion.setText(ManejoDatos.kdescripcionMision);
       lblFinMision.setText(ManejoDatos.kfinMision);
       jPanelDatosMision.setName("Datos Mision"); 
       jTabContenedorPaneles.add(jPanelDatosMision);
    }
    
    /**
     * Este metodo actualiza todas las grficas del Panel de Monitoreo
     */
    public void actualizarPaneles(){
        actPDatosBrutos();
        actPGeneral();
        actPTemperaturaExterna();
        actPTemperaturaInterna();
        actPHumedad();
        actPPresion();
        actPServoBrazoD();
        actPServoBrazoI();
        actPServoLiberacion();
        actPBateria();
    }
    
    /**
     * Este metodo actuliza el panel donde se muestran los datos
     * recibidos en bruto (jPanelDatosBrutos)
     */
    private void actPDatosBrutos(){
        txtAltitud.setText("" + ManejoDatos.altitud); 
        txtLongitud.setText("" + ManejoDatos.longitud);
        txtLatitud.setText("" + ManejoDatos.latitud);
        txtTExterna.setText("" + ManejoDatos.temperaturaExt);
        txtTInterna.setText("" + ManejoDatos.temperaturaInt);
        txtHumedad.setText("" + ManejoDatos.humedad);
        txtPresion.setText("" + ManejoDatos.presion);
        txtServoBD.setText("" + ManejoDatos.servoBrazoD);
        txtServoBI.setText("" + ManejoDatos.servoBrazoI);
        txtServoLiberacion.setText("" + ManejoDatos.servoLiberacion);
        txtBateria.setText("" + ManejoDatos.voltaje);
    }
    
    /**
     * Este metodo actualiza el panel general
     */
    private void actPGeneral(){
        d.setValue(ManejoDatos.temperaturaExt, "Temeperatura Externa", "");
        d.setValue(ManejoDatos.temperaturaInt, "Temeperatura Interna", "");
        d.setValue(ManejoDatos.humedad, "Humedad", "");
        d.setValue(ManejoDatos.presion, "Presion", "");
        d.setValue(ManejoDatos.voltaje, "Bateria", "");
        graficaGeneral.actualizarGraficaBarra("General", d);
    }
    
    /**
     * Este metodo actualiza el panel de Temperatura Externa
     */
    private void actPTemperaturaExterna(){
        graficaTemperaturaE.datasetsTiempo[0].getSeries(0).add(new Millisecond(), ManejoDatos.temperaturaExt);
        graficaTemperaturaI.datasetsAltura[0].getSeries(0).add(ManejoDatos.altitud , ManejoDatos.temperaturaExt);
    }
    
    /**
     * Este metodo actualiza el panel Temperatura Interna
     */
    private void actPTemperaturaInterna(){
        graficaTemperaturaI.datasetsTiempo[0].getSeries(0).add(new Millisecond(), ManejoDatos.temperaturaInt);
        graficaTemperaturaI.datasetsAltura[0].getSeries(0).add(ManejoDatos.altitud , ManejoDatos.temperaturaInt);
    }
    
    /**
     * Este metodo actualiza el panel de Humedad
     */
    private void actPHumedad(){
        graficaHumedad.datasetsTiempo[0].getSeries(0).add(new Millisecond(), ManejoDatos.humedad);
        graficaHumedad.datasetsAltura[0].getSeries(0).add(ManejoDatos.altitud , ManejoDatos.humedad);
    }
    
    /**
     * Este metodo actualiza el panel de Presion
     */
    private void actPPresion(){
        graficaPresion.datasetsTiempo[0].getSeries(0).add(new Millisecond(), ManejoDatos.presion);
        graficaPresion.datasetsAltura[0].getSeries(0).add(ManejoDatos.altitud , ManejoDatos.presion);
    }
    
    /**
     * Este metodo actualiza el panel para el Servo Brazo Derecho
     */
    private void actPServoBrazoD(){
        graficaServoBrazoD.datasetsTiempo[0].getSeries(0).add(new Millisecond(), ManejoDatos.servoBrazoD);
        graficaServoBrazoD.datasetsAltura[0].getSeries(0).add(ManejoDatos.altitud , ManejoDatos.servoBrazoD);
    }
    
    /**
     * Este metodo actualiza el panel para el Servo Brazo Izquierdo
     */
    private void actPServoBrazoI(){
        graficaServoBrazoI.datasetsTiempo[0].getSeries(0).add(new Millisecond(), ManejoDatos.servoBrazoI);
        graficaServoBrazoI.datasetsAltura[0].getSeries(0).add(ManejoDatos.altitud , ManejoDatos.servoBrazoI);
    }
    
    /**
     * Este metodo actualiza el panel para el servo de Liberacion
     */
    private void actPServoLiberacion(){
        graficaServoLiberacion.datasetsTiempo[0].getSeries(0).add(new Millisecond(), ManejoDatos.servoLiberacion);
        graficaServoLiberacion.datasetsAltura[0].getSeries(0).add(ManejoDatos.altitud , ManejoDatos.servoLiberacion);
    }
    
    /**
     * Este metodo actualiza el panel para el voltaje de la bateria
     */
    private void actPBateria(){
        graficaBateria.datasetsTiempo[0].getSeries(0).add(new Millisecond(), ManejoDatos.voltaje);
        graficaBateria.datasetsAltura[0].getSeries(0).add(ManejoDatos.altitud , ManejoDatos.voltaje);
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelDatosMision = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblNombreMision = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblInicionMisioin = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAreaDescripcion = new javax.swing.JTextArea();
        btnFinMision = new javax.swing.JButton();
        lblFinMision = new javax.swing.JLabel();
        lblEtiquetaFinMision = new javax.swing.JLabel();
        jPanelDatosBrutos = new javax.swing.JPanel();
        lblLatitud = new javax.swing.JLabel();
        lblLongitud = new javax.swing.JLabel();
        lblAltitud = new javax.swing.JLabel();
        lblTempExterna = new javax.swing.JLabel();
        lblTempInterna = new javax.swing.JLabel();
        lblHumdad = new javax.swing.JLabel();
        lblPresion = new javax.swing.JLabel();
        lblBateria = new javax.swing.JLabel();
        txtLatitud = new javax.swing.JTextField();
        txtAltitud = new javax.swing.JTextField();
        txtLongitud = new javax.swing.JTextField();
        txtTExterna = new javax.swing.JTextField();
        txtTInterna = new javax.swing.JTextField();
        txtPresion = new javax.swing.JTextField();
        txtBateria = new javax.swing.JTextField();
        txtHumedad = new javax.swing.JTextField();
        txtServoBD = new javax.swing.JTextField();
        lblServoBD = new javax.swing.JLabel();
        txtServoBI = new javax.swing.JTextField();
        lblServoBI = new javax.swing.JLabel();
        txtServoLiberacion = new javax.swing.JTextField();
        lblSerboLiberacion = new javax.swing.JLabel();
        jPanelGoogleMaps = new javax.swing.JPanel();
        jTabContenedorPaneles = new javax.swing.JTabbedPane();
        lblCronometro = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblBanderaComunicacion = new javax.swing.JLabel();
        lblBanderaEarth = new javax.swing.JLabel();

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("Mision");

        lblNombreMision.setText("Mision");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("Inicio de Mision");

        lblInicionMisioin.setText("InicioMision");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("Descripcion");

        txtAreaDescripcion.setColumns(20);
        txtAreaDescripcion.setRows(5);
        txtAreaDescripcion.setEnabled(false);
        txtAreaDescripcion.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAreaDescripcionFocusGained(evt);
            }
        });
        txtAreaDescripcion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAreaDescripcionMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(txtAreaDescripcion);

        btnFinMision.setText("Fin de Mision");
        btnFinMision.setEnabled(false);
        btnFinMision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFinMisionActionPerformed(evt);
            }
        });

        lblFinMision.setText("FinMision");

        lblEtiquetaFinMision.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblEtiquetaFinMision.setText("Fin de Mision");

        javax.swing.GroupLayout jPanelDatosMisionLayout = new javax.swing.GroupLayout(jPanelDatosMision);
        jPanelDatosMision.setLayout(jPanelDatosMisionLayout);
        jPanelDatosMisionLayout.setHorizontalGroup(
            jPanelDatosMisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDatosMisionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDatosMisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnFinMision, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelDatosMisionLayout.createSequentialGroup()
                        .addGroup(jPanelDatosMisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addGroup(jPanelDatosMisionLayout.createSequentialGroup()
                                .addGroup(jPanelDatosMisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3)
                                    .addComponent(lblEtiquetaFinMision))
                                .addGap(33, 33, 33)
                                .addGroup(jPanelDatosMisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFinMision)
                                    .addComponent(lblNombreMision, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblInicionMisioin))))
                        .addGap(0, 176, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelDatosMisionLayout.setVerticalGroup(
            jPanelDatosMisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDatosMisionLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanelDatosMisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblNombreMision))
                .addGap(18, 18, 18)
                .addGroup(jPanelDatosMisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblInicionMisioin))
                .addGap(10, 10, 10)
                .addGroup(jPanelDatosMisionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEtiquetaFinMision)
                    .addComponent(lblFinMision))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFinMision)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblLatitud.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblLatitud.setText("Latitud");

        lblLongitud.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblLongitud.setText("Longitud");

        lblAltitud.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblAltitud.setText("Altitud");

        lblTempExterna.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTempExterna.setText("Temp Externa");

        lblTempInterna.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblTempInterna.setText("Temp Interna");

        lblHumdad.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblHumdad.setText("Humedad");

        lblPresion.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPresion.setText("Presion");

        lblBateria.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblBateria.setText("Bateria");

        txtLatitud.setEnabled(false);

        txtAltitud.setEnabled(false);

        txtLongitud.setEnabled(false);

        txtTExterna.setEnabled(false);

        txtTInterna.setEnabled(false);

        txtPresion.setEnabled(false);

        txtBateria.setEnabled(false);

        txtHumedad.setEnabled(false);

        txtServoBD.setEnabled(false);

        lblServoBD.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblServoBD.setText("Brazo Derecho");

        txtServoBI.setEnabled(false);

        lblServoBI.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblServoBI.setText("Servo paracaidas");

        txtServoLiberacion.setEnabled(false);

        lblSerboLiberacion.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSerboLiberacion.setText("Servo Liberacion");

        javax.swing.GroupLayout jPanelDatosBrutosLayout = new javax.swing.GroupLayout(jPanelDatosBrutos);
        jPanelDatosBrutos.setLayout(jPanelDatosBrutosLayout);
        jPanelDatosBrutosLayout.setHorizontalGroup(
            jPanelDatosBrutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDatosBrutosLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanelDatosBrutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelDatosBrutosLayout.createSequentialGroup()
                        .addComponent(lblSerboLiberacion)
                        .addGap(18, 18, 18)
                        .addComponent(txtServoLiberacion, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDatosBrutosLayout.createSequentialGroup()
                        .addComponent(lblServoBI)
                        .addGap(18, 18, 18)
                        .addComponent(txtServoBI, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDatosBrutosLayout.createSequentialGroup()
                        .addComponent(lblServoBD)
                        .addGap(18, 18, 18)
                        .addComponent(txtServoBD, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDatosBrutosLayout.createSequentialGroup()
                        .addComponent(lblBateria)
                        .addGap(18, 18, 18)
                        .addComponent(txtBateria, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelDatosBrutosLayout.createSequentialGroup()
                        .addGroup(jPanelDatosBrutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblLongitud, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPresion)
                            .addComponent(lblHumdad)
                            .addComponent(lblTempInterna)
                            .addComponent(lblLatitud)
                            .addComponent(lblAltitud)
                            .addComponent(lblTempExterna))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelDatosBrutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLongitud, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLatitud, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtAltitud, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTExterna, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTInterna, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtHumedad, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPresion, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanelDatosBrutosLayout.setVerticalGroup(
            jPanelDatosBrutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDatosBrutosLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanelDatosBrutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLatitud)
                    .addComponent(txtLatitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosBrutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLongitud)
                    .addComponent(txtLongitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosBrutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAltitud)
                    .addComponent(txtAltitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosBrutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTempExterna)
                    .addComponent(txtTExterna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosBrutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTempInterna)
                    .addComponent(txtTInterna, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosBrutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHumdad)
                    .addComponent(txtHumedad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosBrutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPresion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPresion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosBrutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServoBD)
                    .addComponent(txtServoBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosBrutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServoBI)
                    .addComponent(txtServoBI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosBrutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSerboLiberacion)
                    .addComponent(txtServoLiberacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDatosBrutosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBateria)
                    .addComponent(txtBateria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(93, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelGoogleMapsLayout = new javax.swing.GroupLayout(jPanelGoogleMaps);
        jPanelGoogleMaps.setLayout(jPanelGoogleMapsLayout);
        jPanelGoogleMapsLayout.setHorizontalGroup(
            jPanelGoogleMapsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 593, Short.MAX_VALUE)
        );
        jPanelGoogleMapsLayout.setVerticalGroup(
            jPanelGoogleMapsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 466, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        jLabel1.setText("Tiempo de Mision:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabContenedorPaneles, javax.swing.GroupLayout.DEFAULT_SIZE, 1279, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblBanderaComunicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblBanderaEarth, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1012, 1012, 1012)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCronometro, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabContenedorPaneles, javax.swing.GroupLayout.PREFERRED_SIZE, 653, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblBanderaComunicacion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblBanderaEarth, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(1, 1, 1)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblCronometro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Este metodo termina la mision
     * @param evt 
     */
    private void btnFinMisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinMisionActionPerformed
        LecturaPuerto.puertoSerie.removeEventListener();
        LecturaPuerto.puertoSerie.close();
        
        //Guardamos los datos de la mision
        ManejadorArchivos ma = new ManejadorArchivos();
        try {
            ma.setContenidoArchivo(ManejoDatos.krutaArchivo + ManejoDatos.knombreMision + ".kab", ManejoDatos.datosMisionBruto.toString());
            ma.setContenidoArchivoInArray(ManejoDatos.krutaArchivo + ManejoDatos.knombreMision + ".kar", ManejoDatos.datosMision);
            ma.agregaContenidoArchivo(ManejoDatos.krutaArchivo + ManejoDatos.knombreMision + ".ka", new Date().toString());
        } catch (IOException ex) {
            new KDialog(this, "ERROR AL GUARDAR LA MISIOIN :/", KDialog.Tipo.ALERTA).setVisible(true);
            Logger.getLogger(PanelMonitoreo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.exit(0); 
    }//GEN-LAST:event_btnFinMisionActionPerformed

    private void txtAreaDescripcionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAreaDescripcionFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAreaDescripcionFocusGained

    private void txtAreaDescripcionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAreaDescripcionMouseClicked
        setDatosMision();
    }//GEN-LAST:event_txtAreaDescripcionMouseClicked

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
//            java.util.logging.Logger.getLogger(PanelMonitoreo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(PanelMonitoreo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(PanelMonitoreo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(PanelMonitoreo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new PanelMonitoreo().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFinMision;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanelDatosBrutos;
    private javax.swing.JPanel jPanelDatosMision;
    private javax.swing.JPanel jPanelGoogleMaps;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabContenedorPaneles;
    private javax.swing.JLabel lblAltitud;
    private javax.swing.JLabel lblBanderaComunicacion;
    private javax.swing.JLabel lblBanderaEarth;
    private javax.swing.JLabel lblBateria;
    private javax.swing.JLabel lblCronometro;
    private javax.swing.JLabel lblEtiquetaFinMision;
    private javax.swing.JLabel lblFinMision;
    private javax.swing.JLabel lblHumdad;
    private javax.swing.JLabel lblInicionMisioin;
    private javax.swing.JLabel lblLatitud;
    private javax.swing.JLabel lblLongitud;
    private javax.swing.JLabel lblNombreMision;
    private javax.swing.JLabel lblPresion;
    private javax.swing.JLabel lblSerboLiberacion;
    private javax.swing.JLabel lblServoBD;
    private javax.swing.JLabel lblServoBI;
    private javax.swing.JLabel lblTempExterna;
    private javax.swing.JLabel lblTempInterna;
    private javax.swing.JTextField txtAltitud;
    private javax.swing.JTextArea txtAreaDescripcion;
    private javax.swing.JTextField txtBateria;
    private javax.swing.JTextField txtHumedad;
    private javax.swing.JTextField txtLatitud;
    private javax.swing.JTextField txtLongitud;
    private javax.swing.JTextField txtPresion;
    private javax.swing.JTextField txtServoBD;
    private javax.swing.JTextField txtServoBI;
    private javax.swing.JTextField txtServoLiberacion;
    private javax.swing.JTextField txtTExterna;
    private javax.swing.JTextField txtTInterna;
    // End of variables declaration//GEN-END:variables

    @Override
    public void serialEvent(SerialPortEvent event) {
        /*if (event.getEventType() == SerialPortEvent.DATA_AVAILABLE) {

            byte[] readBuffer = new byte[100];
            try {
                for (int i = 0; i < 100; i++) {
                    if (entrada.available() > 0) {
                        entrada.read(readBuffer);
                    }
                }

                //Ponemos en los datos en una cadena para parsear los datos
                String result = new String(readBuffer);
                ck.add_datos(result.trim().replaceAll("\n", "").replaceAll("\t", "") + "\n");
                if (ManejoDatos.switchDatos(result.trim().replaceAll("\n", "").replaceAll("\t", "").toCharArray())) {
                    ManejoDatos.datosMisionBruto.append(result);
                    actualizarPaneles();
                    lblBanderaComunicacion.setIcon(iconoComunicacion);
                } else {
                    lblBanderaComunicacion.setIcon(iconoFalla);
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }*/
    }
    
    /**
     * Este metodo leer el archivo de la mision y comienza la simulacion
     * de la mision 
     */
    private void inicioLecturaArchivo(){
        try {
            ManejadorArchivos ma = new ManejadorArchivos();
            new Timer("Simulador", true).scheduleAtFixedRate(new Subprecesos.Simulador(ma.getContenidoArchivoToArray(AbrirMision.rutaArchivoMision)), 0, AbrirMision.periodo);
        } catch (IOException ex) {
            new KDialog(this, "Error al leer el archivo de la mision", KDialog.Tipo.ALERTA);
            Logger.getLogger(PanelMonitoreo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Este metdo selecciona el JPane de los datos de la mision
     */
    public void setPanelDatosMisionSeleccionado(){
        jTabContenedorPaneles.setSelectedComponent(jPanelDatosMision);
    }
    
    private class ListenerSerial extends TimerTask{

        @Override
        public void run() {
            byte[] readBuffer = new byte[100];
            int size = 0;
            try {
                for (int i = 0; i < 100; i++) {
                    if (entrada.available() > 0) {
                        size = entrada.read(readBuffer);
                    }
                }

                //Ponemos en los datos en una cadena para parsear los datos
                String result = new String(Arrays.copyOf(readBuffer, size));
                ck.add_datos(result.trim().replaceAll("\n", "").replaceAll("\t", "") + "\n");
                if (ManejoDatos.switchDatos(result.trim().replaceAll("\n", "").replaceAll("\t", "").toCharArray())) {
                    ManejoDatos.datosMisionBruto.append(result);
                    actualizarPaneles();
                    lblBanderaComunicacion.setIcon(iconoComunicacion);
                } else {
                    lblBanderaComunicacion.setIcon(iconoFalla);
                }

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "ERROR", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        
    }
    
    public void setDatosMision(){
        lblNombreMision.setText(ManejoDatos.knombreMision);
        lblInicionMisioin.setText(ManejoDatos.kinicioMision);
        txtAreaDescripcion.setText(ManejoDatos.kdescripcionMision);
    }
}

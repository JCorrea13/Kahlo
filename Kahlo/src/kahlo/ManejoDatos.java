package kahlo;

import componentes.KDialog;
import config.Config;
import config.Configuracion;
import config.ManejadorConfiguarciones;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.ManejadorArchivos;

/**
 * Esta clase maneja los datos que se reciben por el
 * puerto. 
 * @author JCORREA
 */
public class ManejoDatos {
    
    //ruta en donde se guardaran los datos
    public static String krutaArchivo;
    
    //datos de instante
    public static double longitud;
    public static double latitud;
    public static double altitud;
    public static double temperaturaInt;
    public static double temperaturaExt;
    public static volatile double humedad;
    public static double presion;
    public static double voltaje;
    public static int servoBrazoD;
    public static int servoBrazoI;
    public static int servoLiberacion;
    public static int buzzer;
    
    //Datos de la mision
    public static String knombreMision;
    public static String kinicioMision;
    public static String kfinMision;
    public static String kdescripcionMision;
    
    //Este objeto almacena la informaicoin ya parseada (no tiene todos los datos)
    public static ArrayList<String> datosMision = new ArrayList<>();
    //Esta objeto almacena la informacion tal cual llega
    public static StringBuffer datosMisionBruto = new StringBuffer();
    
    
    public static boolean initManejoDatos(String rutaArchivo, String mision, String descripcion){
        
        ManejadorArchivos ma = new ManejadorArchivos();
        if(ma.existeAchivo(rutaArchivo + "/" + mision + "/" + mision + ".kab") ||
                ma.existeAchivo(rutaArchivo + "/" + mision + "/" + mision + ".kar") ||
                ma.existeAchivo(rutaArchivo + "/" + mision + "/" + mision + ".ka"))
            return false;
        
        try {
           //creamos el archivo de los datos generales de la mision
           ma.setContenidoArchivo(ManejoDatos.krutaArchivo + ManejoDatos.knombreMision + ".ka", getConfiguracionGeneralArchivoKA(mision, descripcion));
           //actualizamos la configuracion de "CONF_K_VAR_PRUEBAS" de la aplicacion
           Configuracion c = ManejadorConfiguarciones.getConfiguracion(Config.CONF_K_VAR_PRUEBAS);
           c.setValor((Integer.valueOf(c.getValor())+ 1) + "");
           ManejadorConfiguarciones.actualizaConfiguraciones(ManejadorConfiguarciones.configuraciones);
        } catch (IOException ex) {
            new KDialog(null, "ERROR AL GUARDAR LA MISIOIN :/", KDialog.Tipo.ALERTA).setVisible(true);
            Logger.getLogger(PanelMonitoreo.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        knombreMision = mision;
        kdescripcionMision = descripcion;
        krutaArchivo = rutaArchivo + "/" + knombreMision + "/";
        
        
        return false;
    }
    
    /**
     * Este metodo separa los datos de los sensores de los datos de Posicion
     * :
     *  * Manda a parsear la cadena de los datos de los sesores
     *  * Escribe los datos de posicion en el buffer correspondiente 
     * @param cadenaDatos
     * @return
     * @throws InterruptedException 
     */
    public static synchronized boolean switchDatos(char [] cadenaDatos){
        String cadena = String.copyValueOf(cadenaDatos);
        
        while(true){
            if(!(cadena.contains("@") && cadena.contains("#"))){
                    return true;
            }else{
                parseParametros(cadena.substring(cadena.indexOf("@") + 1,cadena.indexOf("#") + 1));
                guardaValoresParseados();
                cadena = cadena.substring(cadena.indexOf("@") + 1,cadena.indexOf("#") + 1);
            }
        }
    }
    
    /**
     * Este metodo parsea la cadena de los datos recogidos por los sensores
     * y los asigna a sus variable correspondientes
     * @param datosSensores 
     */
    public static synchronized void parseParametros(String datosSensores){
        try{
        altitud = Double.parseDouble((datosSensores.substring(0,datosSensores.indexOf("|")).trim().isEmpty()? "0" :datosSensores.substring(0,datosSensores.indexOf("|")).trim()));
        }catch(NumberFormatException e){}
        
        try{
        longitud = Double.parseDouble((datosSensores.substring(datosSensores.indexOf("|") +1,datosSensores.indexOf("~")).trim().isEmpty()? "0" :datosSensores.substring(datosSensores.indexOf("|") +1,datosSensores.indexOf("~")).trim()));
        }catch(NumberFormatException e){}
        
        try{
        latitud = Double.parseDouble((datosSensores.substring(datosSensores.indexOf("~") +1,datosSensores.indexOf("^")).trim().isEmpty()? "0" :datosSensores.substring(datosSensores.indexOf("~") +1,datosSensores.indexOf("^")).trim()));
        }catch(NumberFormatException e){}
        
        try{
        temperaturaExt = Double.parseDouble((datosSensores.substring(datosSensores.indexOf("^") +1,datosSensores.indexOf("¬")).trim().isEmpty()? "0" :datosSensores.substring(datosSensores.indexOf("^") +1,datosSensores.indexOf("¬")).trim()));
        }catch(NumberFormatException e){}
        
        try{
        humedad = Double.parseDouble((datosSensores.substring(datosSensores.indexOf("¬") +1,datosSensores.indexOf("┴")).trim().isEmpty()? "0" :datosSensores.substring(datosSensores.indexOf("¬") +1,datosSensores.indexOf("┴")).trim()));
        }catch(NumberFormatException e){}
        
        try{
        presion = Double.parseDouble((datosSensores.substring(datosSensores.indexOf("┴") +1,datosSensores.indexOf("┬")).trim().isEmpty()? "0" :datosSensores.substring(datosSensores.indexOf("┴") +1,datosSensores.indexOf("┬")).trim()));
        }catch(NumberFormatException e){}
        
        try{
        temperaturaInt = Double.parseDouble((datosSensores.substring(datosSensores.indexOf("┬") +1,datosSensores.indexOf("├")).trim().isEmpty()? "0" :datosSensores.substring(datosSensores.indexOf("┬") +1,datosSensores.indexOf("├")).trim()));
        }catch(NumberFormatException e){}
        
        try{
        buzzer = Integer.parseInt((datosSensores.substring(datosSensores.indexOf("├") +1,datosSensores.indexOf("╚")).trim().isEmpty()? "0" :datosSensores.substring(datosSensores.indexOf("├") +1,datosSensores.indexOf("╚")).trim()));
        }catch(NumberFormatException e){}
        
        try{
        servoBrazoD = Integer.parseInt((datosSensores.substring(datosSensores.indexOf("╚") +1,datosSensores.indexOf("╔")).trim().isEmpty()? "0" :datosSensores.substring(datosSensores.indexOf("╚") +1,datosSensores.indexOf("╔")).trim()));
        }catch(NumberFormatException e){}
        
        try{
        servoBrazoI = Integer.parseInt((datosSensores.substring(datosSensores.indexOf("╔") +1,datosSensores.indexOf("╩")).trim().isEmpty()? "0" :datosSensores.substring(datosSensores.indexOf("╔") +1,datosSensores.indexOf("╩")).trim()));
        }catch(NumberFormatException e){}
        
        try{
        servoLiberacion = Integer.parseInt((datosSensores.substring(datosSensores.indexOf("╩") +1,datosSensores.indexOf("╦")).trim().isEmpty()? "0" :datosSensores.substring(datosSensores.indexOf("╩") +1,datosSensores.indexOf("╦")).trim()));
        }catch(NumberFormatException e){}
        
        try{
        voltaje = Double.parseDouble((datosSensores.substring(datosSensores.indexOf("╦")+1,datosSensores.indexOf("#")).trim().isEmpty()? "0" :datosSensores.substring(datosSensores.indexOf("╦") +1,datosSensores.indexOf("#")).trim()));
        }catch(NumberFormatException e){}
    }

    public static String aString() {
        return "ManejoDatos(" +
                "\nLatitud: " + latitud +
                "\nLongitud: " + longitud +
                "\nAltitud: " + altitud +
                "\nTemperatura Interna: " + temperaturaInt +
                "\nTemperatura Externa: " + temperaturaExt +
                "\nHumedad: " + humedad +
                "\nPresion: " + presion +
                "\nServoBrazoD: " + servoBrazoD +
                "\nServoBrazoI: " + servoBrazoI +
                "\nServoLiberacion: " + servoLiberacion +
                "\nBateria: " + voltaje +
                ")";
    }
    
    private static String getConfiguracionGeneralArchivoKA(String mision,String descripcion){
        
        StringBuffer cadena = new StringBuffer();
            cadena.append("-Kahlo-");
            cadena.append("*" + mision + "*");
            cadena.append("#" + new Date() + "#");
            cadena.append("[" + descripcion + "]");
        return cadena.toString();
    }
    
    /**
     * Este metodo regresa el indice del primer caracter especial 
     * (caracteres separadores de datos en el protocolo de comunicaion)
     * @param cadena
     * @return indice primer caracter especia en la cadena
     */
    private static int getIndicePrimerDato(String cadena){
        
        for (int i = 0; i < cadena.length() ; i++) {
            if(isCaracterEspecial(String.valueOf(cadena.charAt(i))))
                return i;
        }
        return -1;
    }
    
    /**
     * Este metodo valida si un caracter es especial
     * @param a caracter a comparar
     * @return  TRUE si 'a' es un caracter especial
     */
    private static boolean isCaracterEspecial(String a){
        String caracteresEspeciales = "@|~^¬┴┬├╚╔╩╦#";
        return caracteresEspeciales.contains(a);
    }
    
    //Agregamos a la lista de datos los datos actuales en caso de estar en una mision
    private static void guardaValoresParseados(){
        if(PanelMonitoreo.inMision)
        datosMision.add("@" + altitud + "|"
                        + longitud + "~"
                        + latitud + "^"
                        + temperaturaExt + "¬"
                        + humedad + "┴"
                        + presion + "┬"
                        + temperaturaInt + "├"
                        + buzzer + "╚"
                        + servoBrazoD + "╔"
                        + servoBrazoI + "╩"
                        + servoLiberacion + "╦"
                        + voltaje + "#");
    }
}

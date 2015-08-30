package kahlo;

import java.util.Stack;
import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import java.io.InputStream;
import java.util.Enumeration;
import java.io.*;
//import java.util.*;
//import gnu.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author JCORREA
 */
public class LecturaPuerto{

    public static SerialPort puertoSerie = null;
    public static InputStream entrada = null;
    private static SerialPort puertoSerieEscritura = null;
    private static OutputStream salida = null;

    /**
     * Este metodo verifica y pone en una pila todos los puertos disponibles
     *
     * @return Stack con los puertos disponibles
     */
    public static Stack listaPuertos() {
        Stack<String> pila = new Stack<String>();
        CommPortIdentifier idPuerto = null;
        Enumeration listaPuertos = CommPortIdentifier.getPortIdentifiers();

        while (listaPuertos.hasMoreElements()) {
            idPuerto = (CommPortIdentifier) listaPuertos.nextElement();
            pila.push(idPuerto.getName());
        }
        return pila;
    }

    /**
     * Este metodo abre el puerto seria especificado
     *
     * @param nom_puerto se refiere al puerto que se desea abrir
     * @return True si se abrio el puerto exitosamente
     */
    public static boolean abrirPuerto(String nom_puerto) {
        CommPortIdentifier idPuerto = (CommPortIdentifier) comprovarPuerto(nom_puerto);
        if (idPuerto == null) {
            return false;
        }

        try {
            puertoSerie = (SerialPort) idPuerto.open("TierraCanSat", 2000);
            entrada = puertoSerie.getInputStream();
            return true;
        
        }catch (PortInUseException e) {
            LecturaPuerto.cerrarPuerto();
            JOptionPane.showMessageDialog(null, "EL puerto ya esta en uso", "ERROR", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (Exception e) {
            LecturaPuerto.cerrarPuerto();
            Logger.getLogger(LecturaPuerto.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }

    }

    /**
     * Este metodo abre el puerto para 'escritura'
     *
     * @param nom_puerto
     * @return true si logra abrir el puerto
     */
    public static boolean abrirPuertoEscritura(String nom_puerto) {
        CommPortIdentifier idPuerto = (CommPortIdentifier) comprovarPuerto(nom_puerto);
        if (idPuerto == null) {
            return false;
        }

        try {
            puertoSerieEscritura = (SerialPort) idPuerto.open("GEarth", 1000);
            salida = puertoSerieEscritura.getOutputStream();
            if (configurarPuertoEscritura()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Este metodo verifica que el puerto que se manda como parametro se pueda
     * utilizar
     *
     * @param <i>nom_puerto<i> se refiere al nombre del puerto que se quiere
     * verificar
     * @return <i>CoomPortIdentifier<i> del puerto
     */
    private static CommPortIdentifier comprovarPuerto(String nom_puerto) {
        CommPortIdentifier idPuerto = null;
        Enumeration listaPuertos = CommPortIdentifier.getPortIdentifiers();

        while (listaPuertos.hasMoreElements()) {
            idPuerto = (CommPortIdentifier) listaPuertos.nextElement();
            if (idPuerto.getName().equals(nom_puerto)) {
                return idPuerto;
            }
        }
        return null;
    }

    /**
     * Este metodo configura el puerto con el que se esta trabajando
     *
     * @param baud baudios
     * @param databits bits de datos
     * @param stopbits parada de bits
     * @return True si la configuracion ah tenido exito
     */
    public boolean configurarPuerto(int baud, int databits, int stopbits) {
        try {
            puertoSerie.setSerialPortParams(baud, databits, stopbits, puertoSerie.PARITY_NONE);
            puertoSerie.notifyOnDataAvailable(true);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Este metodo configura el puerto para escribir y hacer la comunicacion con
     * Google Earth con el protocolo NMEA
     *
     * @param baud baudios
     * @param databits bits de datos
     * @param stopbits parada de bits
     * @return True si la configuracion ah tenido exito
     */
    private static boolean configurarPuertoEscritura() {
        try {
            puertoSerieEscritura.setSerialPortParams(9600, 8, 1, puertoSerie.PARITY_NONE);
            puertoSerieEscritura.notifyOnDataAvailable(true);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo con la unica fucnion de cerrar el puerto que se abrio
     */
    public static void cerrarPuerto() {
        try {
            puertoSerie.close();
        } catch (Exception e) {

        }
    }
}

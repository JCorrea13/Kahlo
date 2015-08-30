package config;

import java.io.IOException;
import java.util.ArrayList;
import util.ManejadorArchivos;

/**
 * El objetivo de esta clase es recuperar el archivo de configuraciones 
 * y manipularlo (leer, modificarlo, crearlo)
 * @author JCORREA
 */
public class ManejadorConfiguarciones {
    private static final String RUTA_CONFIGURACION = "config/default.config";
    public static ArrayList<Configuracion> configuraciones;
    
    public static ArrayList<Configuracion> getConfigucaciones(){
        return configuraciones;
    }
    
    /**
     * Este metodo recupera la informacion del archivo de configuraciones
     * si este existe
     * @throws IOException 
     */
    public static void recuperaConfiguraciones() throws IOException{
                
        ManejadorArchivos ma = new ManejadorArchivos();
        if(!ma.existeAchivo(RUTA_CONFIGURACION)){
            configuraciones = null;
            return;
        }
        
        //cada srting deberia tener una linea del archivo
        configuraciones = new ArrayList<>();
        ArrayList<String> configuracionesS = ma.getContenidoArchivoToArray(RUTA_CONFIGURACION);
        for(String configuracion : configuracionesS){
            configuraciones.add(new Configuracion(
                    configuracion.substring(1, getIndexAt(',',1,configuracion)), 
                    configuracion.substring(getIndexAt(',',1,configuracion) +1 , getIndexAt(',',2,configuracion)), 
                    configuracion.substring(getIndexAt(',',2,configuracion) +1 , getIndexAt(',',3,configuracion)).equals("1"), 
                    configuracion.substring(getIndexAt(',',3,configuracion) +1, getIndexAt('>',1,configuracion)).equals("1") 
                    ));
        }
    }
    
    public static void actualizaConfiguraciones(ArrayList<Configuracion> configuraciones) throws IOException{
    
        /*El archivo tiene una configuraione <Clave,Valor, Editable, Visible> 
          cada linea en el archivo es una configuraciones diferente
        */
        ArrayList<String> configuraionesS = new ArrayList<>();
        ManejadorArchivos ma = new ManejadorArchivos();
        
        for(Configuracion configuracion : configuraciones)
            configuraionesS.add("<" + (configuracion.getClave() == null? "": configuracion.getClave())
                                + "," + (configuracion.getValor() == null? "": configuracion.getValor())
                                + "," + (configuracion.isEditable()? 1: 0)
                                + "," + (configuracion.isVisible()? 1: 0)
                                + ">");
        ma.setContenidoArchivoInArray(RUTA_CONFIGURACION, configuraionesS);
        
        recuperaConfiguraciones();
    }
    
    private static int getIndexAt(char caracter, int concurrencia, String cadena){
        char [] c = cadena.toCharArray();
        int concurrente = 0;
        for(int i = 0; i < c.length; i++){
            if(c[i] == caracter)
                concurrente ++;
            if(concurrente == concurrencia)
                return i;
        }
        return -1;
    }
    
    /**
     * Este metodo regresa la configuraion con la clave que pasa 
     * como parametro
     * @param clave 
     * @return configuracion con la clave
     */
    public static Configuracion getConfiguracion(String clave){
        
        for(Configuracion c: configuraciones)
            if(c.getClave().equals(clave))
                return c;
        
        return null;
    }
}

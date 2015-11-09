package Maps;

import java.io.IOException;
import java.util.ArrayList;
import util.ManejadorArchivos;

/**
 * Esta clase sirve para manejar los KPP
 * (Kahlo Puntos Predeterminados)
 * @author JCORREA
 */
public class ManejadorKPP {
    
    /**
     * Este metodo recupera la informacion del archivo de configuraciones
     * si este existe
     * @throws IOException 
     */
    
    static ArrayList<Punto> kpps = new ArrayList<>();
    public static ArrayList<Punto> recuperaKPPS(String ruta) throws IOException{
                
        ManejadorArchivos ma = new ManejadorArchivos();

        ArrayList<String> configuracionesS = ma.getContenidoArchivoToArray(ruta);
        for(String kpp : configuracionesS){
            kpps.add(new Punto(
                    Float.valueOf(kpp.substring(1, getIndexAt(',',1,kpp))), 
                    Float.valueOf(kpp.substring(getIndexAt(',',1,kpp) +1 , kpp.length()-1))));
        }
        
        return kpps;
    }
    
    private static int getIndexAt(char caracter, int concurrencia, String cadena) {
        char[] c = cadena.toCharArray();
        int concurrente = 0;
        for (int i = 0; i < c.length; i++) {
            if (c[i] == caracter) {
                concurrente++;
            }
            if (concurrente == concurrencia) {
                return i;
            }
        }
        return -1;
    }
    
}

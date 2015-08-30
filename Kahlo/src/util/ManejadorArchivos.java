package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author JCORREA
 */
public class ManejadorArchivos {
   
    /**
     * Esete metodo se encarga de leer un archivo y lo regresa en un String
     * @param ruta ruta y nombre del archivo (ruta/name.extension)
     * @return contenido del archivo
     */
    public String getContenidoArchivo(String ruta) throws FileNotFoundException, IOException{
        StringBuilder s = new StringBuilder();
        String texto = null;
        
        
        FileReader lector = new FileReader(ruta);
        BufferedReader br = new BufferedReader(lector);
        
        while((texto = br.readLine()) != null)
            s.append(texto);
            
        lector.close();
        return s.toString();
    }
    
    /**
     * Esete metodo se encarga de leer un archivo y lo regresa en un ArrayList<String>
     * cada elemento de la lista hace referencia a una linea del archivo
     * @param ruta ruta y nombre del archivo (ruta/name.extension)
     * @return contenido del archivo
     */
    public ArrayList<String> getContenidoArchivoToArray(String ruta) throws FileNotFoundException, IOException{
        ArrayList<String> al = new ArrayList<>();
        String texto;
        
        
        FileReader lector = new FileReader(ruta);
        BufferedReader br = new BufferedReader(lector);
        
        while((texto = br.readLine()) != null)
            al.add(texto);
            
        lector.close();
        return al;
    }
    
    /**
     * Este metodo crea o sobreescribe el archivo que que se pasa como parametro
     * @param ruta ruta y nombre del archivo (ruta/name.extension)
     * @param contenido caracteres que se excribiran en el archivo
     * @throws IOException 
     */
    public void setContenidoArchivo(String ruta, String contenido) throws IOException{
        
        File f = new File(ruta);
        FileWriter fw = new FileWriter(f, false);
        fw.write(contenido);
        fw.close();
    }
    
    /**
     * Este metodo crea o sobreescribe el archivo que que se pasa como parametro
     * @param ruta ruta y nombre del archivo (ruta/name.extension)
     * @param contenido caracteres que se excribiran en el archivo
     * @throws IOException 
     */
    public void setContenidoArchivoInArray(String ruta, ArrayList<String> contenido) throws IOException{
        
        File f = new File(ruta);
        FileWriter fw = new FileWriter(f, false);
        for(String s : contenido)
            fw.write(s + "\n");
        
        fw.close();
    }
    
    /**
     * Este metodo crea o agrega el archivo que que se pasa como parametro
     * @param ruta ruta y nombre del archivo (ruta/name.extension)
     * @param contenido caracteres que se excribiran en el archivo
     * @throws IOException 
     */
    public void agregaContenidoArchivo(String ruta, String contenido) throws IOException{
        
        File f = new File(ruta);
        FileWriter fw = new FileWriter(f, true);
        fw.write("\n" + contenido);
        fw.close();
    }
    
    /**
     * Este metodo crea o agrega el archivo que que se pasa como parametro
     * @param ruta ruta y nombre del archivo (ruta/name.extension)
     * @param contenido caracteres que se excribiran en el archivo
     * @throws IOException 
     */
    public void agregaContenidoArchivoInArray(String ruta, ArrayList<String> contenido) throws IOException{
        
        File f = new File(ruta);
        FileWriter fw = new FileWriter(f, true);
        fw.write("\n");
        for(String s : contenido)
            fw.write(s + "\n");
        
        fw.close();
    }
    
    /**
     * Este archivo comprueba la existencia del
     * archivo que se pasa como parametro
     * @param ruta ruta y nombre del archivo (ruta/name.extension)
     * @return True si el archivo existe
     */
    public boolean existeAchivo(String ruta){
        
        File f = new File(ruta);
        return f.exists();
        
    }
    
}
package config;

import java.util.ArrayList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import componentes.KDialog;

/**
 *
 * @author JCORREA
 */
public class KTableConfiguraciones extends javax.swing.JTable {
    
    ArrayList<Configuracion> configuraciones;
    private static final int COL_CLAVE = 0;
    private static final int COL_VALOR = 1;
    
    public KTableConfiguraciones(ArrayList<Configuracion> configuraciones) {

        this.configuraciones = configuraciones;
        setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null}
                },
                new String[]{
                    "Clave", "Valor"
                }));
        
        cargaConfiguraciones();
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Agregamos un Listener a la tabla para escuchar los cambios de valores
        getColumnModel().addColumnModelListener(new TableColumnModelListener() {

            @Override
            public void columnAdded(TableColumnModelEvent e) {
                
            }

            @Override
            public void columnRemoved(TableColumnModelEvent e) {
                
            }

            @Override
            public void columnMoved(TableColumnModelEvent e) {
                
            }

            @Override
            public void columnMarginChanged(ChangeEvent e) {
                
            }

            @Override
            public void columnSelectionChanged(ListSelectionEvent e) {
                cambioValor(getSelectedRow() ,getSelectedColumn());
            }
        });
    }
    
    private void cargaConfiguraciones(){
        //limpiamos toda la tabla
        limpiaTabla();
        
        //validamos que haya configuraciones si no existen 
        //mostramos la basicas vacias
        if(configuraciones == null || configuraciones.isEmpty()){
            cargaConfuguracionesBasicas();
        }
        
        int contador = 0;
        for(Configuracion configuracion: configuraciones){
            if(configuracion.isVisible()){
                agregaFila(configuracion);
            }
        }
    }
    
    private void cargaConfuguracionesBasicas(){
        configuraciones = new ArrayList<>();
        //agregamos la Configuracion de "carpeta predefinida de trabajo"
        configuraciones.add(new Configuracion("K_DIR_TRABAJO", null, true, true));
        //agregamos la Configuracion de "Prefijo Pruebas"
        configuraciones.add(new Configuracion("K_PREF_PRUEBAS", null, true, true));
        //agregamos la Configuracion de "Variable de Pruebas"
        configuraciones.add(new Configuracion("K_VAR_PRUEBAS", "0", false, false));
    }

    /**
     * Este metodo escucha los cambio en las filas
     */
    private void cambioValor(int fila, int columna){
        
        if(fila < 0 || columna < 0)
            return;
        
        if(columna == COL_CLAVE){
            setValueAt(configuraciones.get(fila).getClave(), fila, columna);
            new KDialog(null, "La Columna 'Clave' no se puede editar", KDialog.Tipo.ALERTA).setVisible(true);
        }else if(columna == COL_VALOR && !configuraciones.get(fila).isEditable()){
            setValueAt(configuraciones.get(fila).getValor(), fila, columna);
            new KDialog(null, "El campo '" + configuraciones.get(fila).getClave() + "' no se puede editar", KDialog.Tipo.MENSAJE).setVisible(true);
        }
    }
    
    /**
     * Este metodo agrega filas a la tabla
     * @param c configuracion
     */
    public void agregaFila(Configuracion c){
        ((DefaultTableModel)getModel()).addRow(new Object [] {c.getClave(),c.getValor()});
    }
    
    /**
     * Este metodo limpia la tabla 
     * (Elimina todas la filas)
     */
    public void limpiaTabla(){
        int count = getRowCount();
        for (int i = 0; i < count; i++) {
            ((DefaultTableModel)getModel()).removeRow(i);
        }
    }
    
        /**
     * Este metodo recupera las configuraciones ya editadas
     * @return configuraciones 
     */
    public ArrayList<Configuracion> getConfiguraciones(){
        for(int i = 0; i < getRowCount(); i ++)
            for(int j = 0; j < configuraciones.size(); j ++)
                if(getValueAt(i, COL_CLAVE).equals(configuraciones.get(j).getClave())){
                    configuraciones.get(j).setValor((String)getValueAt(i, COL_VALOR));
                    break;
                }
        
        return configuraciones;
    }
}

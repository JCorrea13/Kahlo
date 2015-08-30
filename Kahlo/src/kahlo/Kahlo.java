package kahlo;

import componentes.KDialog;
import config.ManejadorConfiguarciones;
import inicioLib.PantallaCargandoMain;
/**
 * @author JCORREA
 */
public class Kahlo {

    @SuppressWarnings("empty-statement")
    public static void main(String[] args){
        try{
        ManejadorConfiguarciones.recuperaConfiguraciones();
        }catch(Exception e){
            new KDialog(null, "Error al recuperar la configuracion de la aplicacion", KDialog.Tipo.ALERTA);
        }
        new PantallaCargandoMain();
        PanelMonitoreo.lanzadorPanelMonitoreo();
    }
}

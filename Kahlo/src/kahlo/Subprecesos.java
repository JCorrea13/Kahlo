package kahlo;

import componentes.KDialog;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 * @author JCORREA
 */
public class Subprecesos {
   
    /**
     * Cronometro de la mision
     */
    public static class Cronometro extends TimerTask{

        JLabel lblCronometro;
        int horas = 0;
        int minutos = 0;
        int segundos = 0;
        
        public Cronometro(JLabel lblCronometro) {
            this.lblCronometro = lblCronometro; 
        }
        
        @Override
        public void run() {
                segundos++;

                if (segundos == 60) {
                    segundos = 0;
                    minutos++;
                }

                if (minutos == 60) {
                    minutos = 0;
                    horas++;
                }

                lblCronometro.setText((horas < 10 ? "0" + horas : horas) + ":" + (minutos < 10 ? "0" + minutos : minutos)
                        + ":" + (segundos < 10 ? "0" + segundos : segundos));
        }
        
    }
       
    /**
     * Esta clase simula la mision con el retraso 
     * especificado en la configuracion
     */
    public static class Simulador extends TimerTask{

        private ArrayList<String> datos;
        private int contadorDatos = 0;
        private PanelMonitoreo pm;
        
        public Simulador(ArrayList<String> datos) {
            this.datos = datos;
            pm = PanelMonitoreo.panelMonitoreo();
        }
        
        @Override
        public void run() {
            if(contadorDatos == datos.size()-1){
                new KDialog(pm, "FIN DEL ARCHIVO", KDialog.Tipo.MENSAJE).setVisible(true);
                cancel();
            }
            
            if (ManejoDatos.switchDatos(datos.get(contadorDatos++).toCharArray())) {
                    pm.actualizarPaneles();
            }
        }
    
    }
}

package inicioLib;

import javax.swing.ImageIcon;

public class PantallaCargandoMain {

  PantallaCargando screen;

  public PantallaCargandoMain() {
    inicioPantalla();
    screen.velocidadDeCarga();
  }

  private void inicioPantalla(){
    ImageIcon myImage = new ImageIcon("src/recursos/imagen/kahloIcono.png");
    screen = new PantallaCargando(myImage);
    screen.setLocationRelativeTo(null);
    screen.setProgresoMax(100);
    screen.setVisible(true);
  }
}
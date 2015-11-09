package Maps;

/**
 * Esta clase modela un punto en el mapa
 * @author JCORREA
 */
public class Punto {
    
    private float latitud;
    private float longitud;

    public Punto(float latitud, float longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public float getLatitud() {
        return latitud;
    }

    public float getLongitud() {
        return longitud;
    }
    
}

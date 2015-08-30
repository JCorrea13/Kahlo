package config;

/**
 * Esta clase tiene como objetivo modelar una
 Configuracion
 * @author JCORREA
 */
public class Configuracion {
    
    private String clave;
    private String valor;
    private boolean editable;
    private boolean visible;

    public Configuracion(String clave, String valor, boolean editable, boolean visible) {
        this.clave = clave;
        this.valor = valor;
        this.editable = editable;
        this.visible = visible;
    }
    
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
    
    
}

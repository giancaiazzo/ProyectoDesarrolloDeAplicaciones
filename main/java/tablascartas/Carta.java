/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tablascartas;

/**
 *
 * @author Usuario
 */
public class Carta {
    private boolean seleccionada = false;
    private String nombre;

    public boolean isSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Carta(String nombre) {
        this.nombre = nombre;
    }
    
    
    
    
}

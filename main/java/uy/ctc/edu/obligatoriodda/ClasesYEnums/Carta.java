/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uy.ctc.edu.obligatoriodda.ClasesYEnums;

/**
 *
 * @author Matias
 */
public class Carta {
    private int numero; // Número de la carta (por ejemplo, 1 al 13 en un mazo estándar).
    private String tipo; // Tipo de la carta (palo, como "corazones" o "diamantes").
    private int valor; // Valor de la carta para el juego (normalmente igual al número, pero limitado a 10 para cartas mayores a 10).
    private boolean cartaSeleccionada = false; // Indica si la carta ha sido seleccionada en el juego.

    /**
     * Constructor que inicializa la carta con su número y tipo.
     * Calcula el valor automáticamente: si el número es mayor que 10, el valor será 10.
     * @param numero Número de la carta.
     * @param tipo Tipo de la carta.
     */
    public Carta(int numero, String tipo) {
        this.numero = numero;
        this.tipo = tipo;
        this.valor = (numero > 10) ? 10 : numero; // Cartas mayores a 10 valen 10; las demás valen su número.
    }

    /**
     * Devuelve el número de la carta.
     * @return El número de la carta.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Establece el número de la carta.
     * @param numero El número a establecer.
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * Devuelve el tipo de la carta.
     * @return El tipo de la carta (como "corazones", "espadas").
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo de la carta.
     * @param tipo El tipo a establecer.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Devuelve el valor de la carta.
     * @return El valor de la carta (máximo 10 para cartas mayores a 10).
     */
    public int getValor() {
        return valor;
    }

    /**
     * Establece el valor de la carta manualmente.
     * @param valor El valor a establecer.
     */
    public void setValor(int valor) {
        this.valor = valor;
    }

    /**
     * Indica si la carta está seleccionada.
     * @return `true` si la carta está seleccionada, `false` en caso contrario.
     */
    public boolean cartaSeleccionada() {
        return cartaSeleccionada;
    }

    /**
     * Establece si la carta está seleccionada.
     * @param cartaSeleccionada `true` para marcar la carta como seleccionada, `false` para desmarcarla.
     */
    public void setcartaSeleccionada(boolean cartaSeleccionada) {
        this.cartaSeleccionada = cartaSeleccionada;
    }

    /**
     * Representa la carta como una cadena de texto.
     * @return Una descripción de la carta en formato "número de tipo (valor)".
     */
    @Override
    public String toString() {
        return numero + " de " + tipo + " (valor: " + valor + ")";
    }
}

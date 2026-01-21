/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uy.ctc.edu.obligatoriodda.ClasesYEnums;

import java.util.ArrayList;

/**
 * Clase que representa a un jugador en el contexto de una partida.
 * Almacena la relación entre un jugador y su desempeño en una partida específica.
 * Autor: Matias
 */
public class JugadorEnPartida {

    // Atributos principales del jugador en la partida.
    private int puntaje; // Puntaje acumulado por el jugador en la partida.
    private Jugador Jugador; // Referencia al objeto Jugador asociado a este jugador en la partida.
    private EstadoJugador estado; // Estado actual del jugador en la partida (activo, eliminado, etc.).

    // Lista de las manos del jugador, que contienen sus cartas.
    private ArrayList<ManoJugador> cartasEnMano;

    /**
     * Constructor vacío. 
     * Permite crear un objeto JugadorEnPartida sin inicializar sus atributos.
     * Útil para inicializaciones posteriores con setters.
     */
    public JugadorEnPartida() {
        // Constructor vacío.
    }

    /**
     * Constructor que inicializa los atributos principales del jugador en la partida.
     * @param puntaje Puntaje inicial del jugador.
     * @param Jugador Referencia al objeto Jugador asociado.
     * @param estado Estado inicial del jugador en la partida.
     */
    public JugadorEnPartida(int puntaje, Jugador Jugador, EstadoJugador estado) {
        this.puntaje = puntaje;
        this.Jugador = Jugador;
        this.estado = estado;
    }

    /**
     * Devuelve el puntaje actual del jugador en la partida.
     * @return El puntaje del jugador.
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * Establece el puntaje actual del jugador en la partida.
     * @param puntaje El nuevo puntaje del jugador.
     */
    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    /**
     * Devuelve el jugador asociado a esta instancia de jugador en partida.
     * @return El objeto Jugador asociado.
     */
    public Jugador getJugador() {
        return Jugador;
    }

    /**
     * Establece el jugador asociado a esta instancia de jugador en partida.
     * @param Jugador El nuevo objeto Jugador asociado.
     */
    public void setJugador(Jugador Jugador) {
        this.Jugador = Jugador;
    }

    /**
     * Devuelve el estado actual del jugador en la partida.
     * @return El estado del jugador.
     */
    public EstadoJugador getEstado() {
        return estado;
    }

    /**
     * Establece el estado actual del jugador en la partida.
     * @param estado El nuevo estado del jugador.
     */
    public void setEstado(EstadoJugador estado) {
        this.estado = estado;
    }

    /**
     * Devuelve la lista de manos del jugador en la partida.
     * @return La lista de objetos ManoJugador.
     */
    public ArrayList<ManoJugador> getCartasEnMano() {
        return cartasEnMano;
    }

    /**
     * Establece la lista de manos del jugador en la partida.
     * @param cartasEnMano La nueva lista de objetos ManoJugador.
     */
    public void setCartasEnMano(ArrayList<ManoJugador> cartasEnMano) {
        this.cartasEnMano = cartasEnMano;
    }
}

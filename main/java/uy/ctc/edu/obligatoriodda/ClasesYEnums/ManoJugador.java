/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uy.ctc.edu.obligatoriodda.ClasesYEnums;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa la mano de un jugador, incluyendo las cartas en posesión,
 * las ligas de cartas (agrupaciones) y las estrategias para validar dichas ligas.
 * Autor: Matias
 */
public class ManoJugador {

    // Atributos principales de la clase.
    private ArrayList<Carta> cartasEnMano; // Lista de cartas que tiene el jugador en su mano.
    private ArrayList<ArrayList<Carta>> ligaCartas; // Agrupaciones (ligas) de cartas en la mano.
    private List<ValidarLigas> estrategiasValidacion; // Lista de estrategias para validar las ligas de cartas.

    /**
     * Constructor que inicializa la mano del jugador con un conjunto de estrategias de validación.
     * @param estrategias Lista de estrategias para validar las ligas de cartas.
     * @throws IllegalArgumentException Si no se proporciona al menos una estrategia de validación.
     */
    public ManoJugador(List<ValidarLigas> estrategias) {
        this.cartasEnMano = new ArrayList<>();
        this.ligaCartas = new ArrayList<>();
        if (estrategias == null || estrategias.isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos una estrategia de validación");
        }
        this.estrategiasValidacion = estrategias;
    }

    /**
     * Constructor vacío. 
     * Útil para inicializaciones posteriores con setters.
     */
    public ManoJugador() {
        // Constructor vacío.
    }

    // Métodos de acceso y modificación (getters y setters).

    /**
     * Devuelve las cartas que están actualmente en la mano del jugador.
     * @return Lista de cartas en la mano.
     */
    public ArrayList<Carta> getCartasEnMano() {
        return cartasEnMano;
    }

    /**
     * Establece las cartas en la mano del jugador.
     * @param cartasEnMano Nueva lista de cartas.
     */
    public void setCartasEnMano(ArrayList<Carta> cartasEnMano) {
        this.cartasEnMano = cartasEnMano;
    }

    /**
     * Devuelve las ligas (agrupaciones) de cartas actuales.
     * @return Lista de listas de cartas agrupadas.
     */
    public ArrayList<ArrayList<Carta>> getLigaCartas() {
        return ligaCartas;
    }

    /**
     * Establece las ligas (agrupaciones) de cartas.
     * @param ligaCartas Nueva lista de listas de cartas agrupadas.
     */
    public void setLigaCartas(ArrayList<ArrayList<Carta>> ligaCartas) {
        this.ligaCartas = ligaCartas;
    }

    /**
     * Devuelve la lista de estrategias de validación para las ligas.
     * @return Lista de estrategias de validación.
     */
    public List<ValidarLigas> getEstrategiasValidacion() {
        return estrategiasValidacion;
    }

    /**
     * Establece las estrategias de validación para las ligas.
     * @param estrategias Nueva lista de estrategias de validación.
     * @throws IllegalArgumentException Si no se proporciona al menos una estrategia de validación.
     */
    public void setEstrategiasValidacion(List<ValidarLigas> estrategias) {
        if (estrategias == null || estrategias.isEmpty()) {
            throw new IllegalArgumentException("Debe proporcionar al menos una estrategia de validación");
        }
        this.estrategiasValidacion = estrategias;
    }

    // Métodos principales.

    /**
     * Agrega una carta a la mano del jugador.
     * @param carta Carta a agregar.
     */
    public void agregarCarta(Carta carta) {
        cartasEnMano.add(carta);
    }

    /**
     * Intenta agregar una liga (agrupación) de cartas a las ligas actuales.
     * Valida la liga utilizando las estrategias definidas.
     * @param cartas Lista de cartas a validar y agrupar.
     * @return True si la liga es válida según al menos una estrategia, false en caso contrario.
     */
    public boolean agregarLigas(ArrayList<Carta> cartas) {
        for (ValidarLigas estrategia : estrategiasValidacion) {
            if (estrategia.validar(cartas)) {
                ligaCartas.add(new ArrayList<>(cartas)); // Agrega una nueva copia de las cartas como liga.
                return true; // Liga válida según al menos una estrategia.
            }
        }
        return false; // Ninguna estrategia validó las cartas.
    }

    /**
     * Elimina todas las ligas de cartas actuales.
     * Limpia la lista de agrupaciones de cartas.
     */
    public void desLigar() {
        if (ligaCartas != null) {
            ligaCartas.clear();
            System.out.println("Ligas desechas");
        }
    }
}

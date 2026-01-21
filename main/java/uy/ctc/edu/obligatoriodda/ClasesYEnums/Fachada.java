/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uy.ctc.edu.obligatoriodda.ClasesYEnums;

import java.util.ArrayList;

/**
 * Clase Fachada que centraliza la interacción con los sistemas de gestión de jugadores y partidas.
 * Aplica el patrón **Fachada** para simplificar el acceso a funcionalidades del sistema.
 * Autor: jonac
 */
public class Fachada {

    // Singleton que gestiona los jugadores. Aplica el patrón Singleton.
    private SingletonJugadores gestoraJugadores = SingletonJugadores.getInstancia();

    // Singleton que gestiona las partidas. Aplica el patrón Singleton.
    private SingletonPartida gestoraPartidas = SingletonPartida.getInstancia();

    /**
     * Realiza el inicio de sesión de un jugador.
     * @param usuario Nombre de usuario.
     * @param contrasena Contraseña del jugador.
     * @return El objeto Jugador si las credenciales son correctas, null en caso contrario.
     */
    public Jugador loginJugador(String usuario, String contrasena) {
        return gestoraJugadores.loginJugador(usuario, contrasena);
    }

    /**
     * Permite el ingreso de un jugador a una partida.
     * @param jugador Jugador que desea ingresar.
     * @return Información relacionada con el ingreso a la partida (por ejemplo, el estado inicial).
     */
    public Object[] ingresoPartida(Jugador jugador) {
        return gestoraPartidas.ingresoPartida(jugador);
    }

    /**
     * Genera un aviso o notificación para los jugadores de una partida.
     * @param idPartida Identificador único de la partida.
     * @return Un mensaje o estado de aviso para los jugadores.
     */
    public String avisoJugadores(int idPartida) {
        return gestoraPartidas.avisoJugadores(idPartida);
    }

    /**
     * Inicia una partida específica.
     * @param partida Objeto Partida que se desea iniciar.
     * @return La partida una vez iniciada.
     */
    public Partida iniciarPartida(Partida partida) {
        return gestoraPartidas.iniciarPartida(partida);
    }

    /**
     * Avanza el turno al siguiente jugador en la partida.
     * @param partida Partida actual donde se avanza el turno.
     */
    public void avanzarTurno(Partida partida) {
        partida.avanzarTurno();
    }

    /**
     * Permite a un jugador tomar una carta dada vuelta en la partida.
     * @param partida Partida en la que participa el jugador.
     * @param jugador Jugador que toma la carta.
     * @return La carta que se toma.
     */
    public Carta tomarCartaDadaVuelta(Partida partida, JugadorEnPartida jugador) {
        return partida.tomarCartaDadaVuelta(jugador);
    }

    /**
     * Valida si un jugador puede cerrar la mano en la partida actual.
     * @param partida Partida donde se realiza la validación.
     * @param jugador Jugador que solicita el cierre de mano.
     * @return True si el cierre de mano es válido, false en caso contrario.
     */
    public boolean validarCierreMano(Partida partida, JugadorEnPartida jugador) {
        return partida.validarCierreMano(jugador);
    }

    /**
     * Permite a un jugador cerrar la mano en la partida.
     * @param partida Partida en curso.
     * @param jugador Jugador que cierra la mano.
     */
    public void cerrarMano(Partida partida, JugadorEnPartida jugador) {
        partida.cerrarMano(jugador);
    }

    /**
     * Verifica si es el turno de un jugador en la partida actual.
     * @param partida Partida en curso.
     * @param jugador Jugador cuya acción se está verificando.
     * @return True si es el turno del jugador, false en caso contrario.
     */
    public boolean esTurnoJugador(Partida partida, JugadorEnPartida jugador) {
        return partida.esTurnoJugador(jugador);
    }

    /**
     * Obtiene la carta dada vuelta en la partida actual.
     * @param partida Partida en curso.
     * @return La carta que está dada vuelta.
     */
    public Carta obtenerCartaDadaVuelta(Partida partida) {
        return partida.getCartaDadaVuelta();
    }

    /**
     * Valida si un conjunto de cartas forman ligas (grupos válidos) según las reglas del juego.
     * @param jugador Jugador que realiza la validación.
     * @param cartasAValidar Cartas que se desean validar como ligas.
     * @return True si las cartas son válidas, false en caso contrario.
     */
    public boolean validarLigas(JugadorEnPartida jugador, ArrayList<Carta> cartasAValidar) {
        return jugador.getCartasEnMano().get(0).agregarLigas(cartasAValidar);
    }
}

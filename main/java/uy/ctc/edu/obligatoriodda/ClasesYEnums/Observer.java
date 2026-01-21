/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package uy.ctc.edu.obligatoriodda.ClasesYEnums;

/**
 * Interfaz que define el contrato para cualquier clase que desee observar cambios 
 * en una partida. Forma parte de la implementación del patrón Observer.
 * 
 * Autor: jonac
 */
public interface Observer {
    
    /**
     * Método que se invoca cuando ocurre un cambio en el estado de una partida.
     * Este método es implementado por los observadores que deseen ser notificados
     * de los cambios.
     * 
     * @param objPartida Instancia de la partida que ha cambiado.
     */
    public void actualizar(Partida objPartida);
}

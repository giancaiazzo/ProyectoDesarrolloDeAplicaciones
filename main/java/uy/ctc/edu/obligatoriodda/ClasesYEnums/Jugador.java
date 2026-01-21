/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uy.ctc.edu.obligatoriodda.ClasesYEnums;

import java.util.ArrayList;

/**
 * Clase que representa un jugador en el sistema.
 * Contiene información personal y funcional relacionada con el jugador.
 * Autor: Matias
 */
public class Jugador {
    
    // Atributos principales del jugador.
    private String username; // Nombre de usuario del jugador (usado para login).
    private String password; // Contraseña del jugador (usado para login).
    private String nombre;   // Nombre completo del jugador.
    private float saldo;     // Saldo actual del jugador en el sistema.

    /**
     * Constructor que inicializa todos los atributos del jugador.
     * @param username Nombre de usuario del jugador.
     * @param password Contraseña del jugador.
     * @param nombre Nombre completo del jugador.
     * @param saldo Saldo inicial del jugador.
     */
    public Jugador(String username, String password, String nombre, float saldo) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.saldo = saldo;
    }
    
    /**
     * Constructor vacío que permite crear un jugador sin inicializar sus atributos.
     * Se usa en casos donde los valores se establecerán después mediante setters.
     */
    public Jugador() {
        // Constructor vacío.
    }

    /**
     * Devuelve el nombre de usuario del jugador.
     * @return El nombre de usuario del jugador.
     */
    public String getNombreUsuario() {
        return username;
    }

    /**
     * Establece el nombre de usuario del jugador.
     * @param nombreUsuario El nuevo nombre de usuario.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.username = nombreUsuario;
    }

    /**
     * Devuelve la contraseña del jugador.
     * @return La contraseña del jugador.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del jugador.
     * @param password La nueva contraseña.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Devuelve el nombre completo del jugador.
     * @return El nombre del jugador.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre completo del jugador.
     * @param nombre El nuevo nombre del jugador.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el saldo actual del jugador.
     * @return El saldo del jugador.
     */
    public float getSaldo() {
        return saldo;
    }

    /**
     * Establece el saldo actual del jugador.
     * @param saldo El nuevo saldo del jugador.
     */
    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }
}

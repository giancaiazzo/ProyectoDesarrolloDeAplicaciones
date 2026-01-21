/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uy.ctc.edu.obligatoriodda.ClasesYEnums;

import java.sql.*;
import java.util.ArrayList;
import persistencia.conexionDB;

/**
 *
 * @author jonac
 */
public class SingletonJugadores {
    private static SingletonJugadores instancia; 
    
    private ArrayList<Jugador> jugadores;
    
    
    private SingletonJugadores() {
        jugadores = new ArrayList<>();
        precargarDatos();
    }
 
    public static SingletonJugadores getInstancia() {
        if (instancia == null) {
            instancia = new SingletonJugadores();
        }
        return instancia;
    }

    public ArrayList<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<Jugador> jugadores) {
        this.jugadores = jugadores;
    }
private void precargarDatos() {
    jugadores.clear(); // Limpia la lista antes de precargar 
 
    String query = "SELECT username, password, nombre, saldo FROM Usuarios"; 
 
    try (Connection conn = conexionDB.getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(query); 
         ResultSet rs = stmt.executeQuery()) { 
 
        while (rs.next()) { 
            String username = rs.getString("username"); 
            String password = rs.getString("password"); 
            String nombre = rs.getString("nombre"); 
            float saldo = rs.getFloat("saldo"); 
 
            // Crea un nuevo objeto Jugador y agrégalo a la lista 
            Jugador jugador = new Jugador(username, password, nombre, saldo); 
            jugadores.add(jugador); 
        } 
 
    } catch (SQLException e) {
        // Si hay un error en la conexión, precarga datos por defecto
        System.err.println("Error al precargar datos de la base de datos: " + e.getMessage());
        
        // Precarga manual de datos
        jugadores.add(new Jugador("user1", "password123", "Juan Pérez", 1000f));
        jugadores.add(new Jugador("user2", "password123", "María López", 1200f));
        jugadores.add(new Jugador("user3", "password123", "Carlos García", 1500f));
        jugadores.add(new Jugador("user4", "password123", "Ana Rodríguez", 800f));
        
        System.out.println("Datos precargados manualmente debido a error de conexión.");
    } 
}
         
    
    //validar el estado del jugador para saber si esta en partida
    public Jugador loginJugador(String user, String pass){
    Jugador ingresado = new Jugador();
    for (Jugador jugador : jugadores) {
        if (jugador.getNombreUsuario().equalsIgnoreCase(user) && jugador.getPassword().equalsIgnoreCase(pass)) {
            return ingresado = jugador; 
        }
    }
    return ingresado;     
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uy.ctc.edu.obligatoriodda.ClasesYEnums;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author jonac
 */
public class SingletonPartida {
    
    private static SingletonPartida instancia; 
    
    private ArrayList<Partida> partidas;
    
    
    //sin esto se me partia al querer utilizar el array
    private SingletonPartida() {
        partidas = new ArrayList<>(); // Inicialización de partidas
    }
    
    
    public static SingletonPartida getInstancia() {
        if (instancia == null) {
            instancia = new SingletonPartida();
        }
        return instancia;
    }
    
      
    
    public Object[] ingresoPartida(Jugador jugador) {
    // Si al recorrer las partidas disponibles encuentra una y el número de jugadores no llega al mínimo, lo agrego a la partida
    JugadorEnPartida objJugador = new JugadorEnPartida();
    Partida objPartida = new Partida();
    String msg = "Error al ingresar";
    String cod = "1";

    // Validar si el jugador puede ser ingresado en una partida
    if (validarJugadorParaIngreso(jugador)) {

        // Verificar si existen partidas
        if (partidas != null && partidas.size() > 0) {
            boolean jugadorAsignado = false;

            for (Partida partida : partidas) {
                // Validar que la partida esté en espera y tenga espacio para jugadores
                if (partida.getEstado() == EstadoPartida.EN_ESPERA && 
                    partida.getMinJugadores() > partida.getJugadores().stream()
                        .filter(jugador2 -> jugador2.getEstado() == EstadoJugador.JUGANDO)
                        .count()) {

                    // Validar que el saldo del jugador sea suficiente para unirse a la partida
                    if (jugador.getSaldo() < partida.getValorApuesta()) {
                        msg = "Saldo insuficiente para ingresar a la partida";
                        cod = "2"; // Código para saldo insuficiente
                        return new Object[]{null, null, msg, cod};
                    }

                    // Crear un nuevo objeto JugadorPartida y asignarlo a la partida
                    objJugador = new JugadorEnPartida(0, jugador, EstadoJugador.JUGANDO);
                    objPartida = partida;

                    if (partida.agregarJugadorPartida(objJugador)) {
                        // Retornar el jugador y la partida a la que ingresó
                        msg = "Jugador Ingresado";
                        cod = "0";
                        partida.setEstado(EstadoPartida.EN_JUEGO);
                        jugadorAsignado = true;
                        return new Object[]{objJugador, objPartida, msg, cod};
                    } else {
                        // En caso de que no se pueda agregar el jugador, mando null
                        msg = "No entra porque no es válido";
                        cod = "1";
                        objJugador = null;
                        objPartida = null;
                        jugadorAsignado = true; // Para evitar crear una nueva partida innecesariamente
                        break;
                    }
                }
            }

            // Si no se asignó a ninguna partida existente, se crea una nueva
            if (!jugadorAsignado) {
                int valorApuesta = 100; // entre 1 y 100
                int puntosPartida = 30; // entre 1 y 30. Este es el valor con el que pierdes, confirmar reglas.

                // Validar que el saldo del jugador sea suficiente para la apuesta de la nueva partida
                if (jugador.getSaldo() < valorApuesta) {
                    msg = "Saldo insuficiente para crear una nueva partida";
                    cod = "2"; // Código para saldo insuficiente
                    return new Object[]{null, null, msg, cod};
                }

                ArrayList<JugadorEnPartida> colJugadores = new ArrayList<>();
                int puntosJugador = 0; // Por defecto
                JugadorEnPartida obJugadorPartida = new JugadorEnPartida(puntosJugador, jugador, EstadoJugador.JUGANDO);
                colJugadores.add(obJugadorPartida);

                // Crear una nueva partida con los valores predeterminados
                Partida nuevaPartida = new Partida(
                    partidas.size() + 1, 
                    EstadoPartida.EN_ESPERA, 
                    valorApuesta, 
                    puntosPartida, 
                    valorApuesta, 
                    colJugadores
                );

                // Agregar la nueva partida a la lista de partidas
                msg = "";
                cod = "0";
                this.partidas.add(nuevaPartida);
                return new Object[]{obJugadorPartida, nuevaPartida, msg, cod};
            }
        } else {
            // Si no existe una partida disponible, creo una nueva
            int valorApuesta = 100; // entre 1 y 100
            int puntosPartida = 30; // entre 1 y 30. Este es el valor con el que pierdes, confirmar reglas.

            // Validar que el saldo del jugador sea suficiente para la apuesta de la nueva partida
            if (jugador.getSaldo() < valorApuesta) {
                msg = "Saldo insuficiente para crear una nueva partida";
                cod = "2"; // Código para saldo insuficiente
                return new Object[]{null, null, msg, cod};
            }

            ArrayList<JugadorEnPartida> colJugadores = new ArrayList<>();
            int puntosJugador = 0; // Por defecto
            JugadorEnPartida obJugadorPartida = new JugadorEnPartida(puntosJugador, jugador, EstadoJugador.JUGANDO);
            colJugadores.add(obJugadorPartida);

            // Crear una nueva partida con los valores predeterminados
            Partida nuevaPartida = new Partida(
                partidas.size() + 1, 
                EstadoPartida.EN_ESPERA, 
                valorApuesta, 
                puntosPartida, 
                valorApuesta, 
                colJugadores
            );

            // agregar la nueva partida a la lista de partidas
            msg = "";
            cod = "0";
            this.partidas.add(nuevaPartida);
            return new Object[]{obJugadorPartida, nuevaPartida, msg, cod};
        }
    } else {
        msg = "El jugador se encuentra actualmente en partida";
        cod = "1";
    }
    System.out.println("Llega a este return");
    return new Object[]{objJugador, objPartida, msg, cod};
}

private boolean validarJugadorParaIngreso(Jugador jugador) {
    for (Partida partida : partidas) {
        for (JugadorEnPartida jugadorPartida : partida.getJugadores()) {
            // Verificar si el jugador ya está en una partida
            if (jugadorPartida.getJugador().getNombreUsuario().equals(jugador.getNombreUsuario())) {
                // Si la partida está finalizada, el jugador puede ingresar
                if (partida.getEstado() == EstadoPartida.FINALIZADO) {
                    return true;
                }
                // Si el jugador no esta jugando en una partida no finalizada, tambien puede ingresar
                if (jugadorPartida.getEstado() != EstadoJugador.JUGANDO) {
                    return true;
                }
                // Si esta jugando en una partida no finalizada, no puede ingresar
                return false;
            }
        }
    }
    // Si no esta en ninguna partida, el jugador es valido
    return true;
}
    
    
    public boolean validarJugadorPartida(String nombreUsuario){
        
        boolean valido = true;
        //busca en todas las partidas en espera o jugando para ver si esta el jugador que quiere entrar
        for (Partida partida : partidas) {
            
            if(partida.getEstado() == EstadoPartida.EN_ESPERA || partida.getEstado() == EstadoPartida.EN_JUEGO){
                
                for (JugadorEnPartida jugadores : partida.getJugadores()) {
                    if(jugadores.getJugador().getNombreUsuario().equals(nombreUsuario)){
                        valido = false;
                        break;
                    }
                }
            }
        }
        return valido;
    }
    

  public String avisoJugadores(int numPartida) {
    String mensaje = "";
    for (Partida partida : partidas) {
        if (partida.getIdPartida() == numPartida) {
            
            long jugadoresActivos = partida.getJugadores().stream()
                .filter(jugador -> jugador.getEstado() != EstadoJugador.RETIRADO)
                .count();

            mensaje = jugadoresActivos + " jugadores de " + partida.getMinJugadores();
            break;
        }
    }
    return mensaje;
}
  
    public Partida iniciarPartida(Partida partidaActual){

        for (Partida objPartida : partidas) {
            if(objPartida.getIdPartida() == partidaActual.getIdPartida()){
               objPartida.iniciarPartida();
               partidaActual = objPartida;
               break;
            }
        }
        
        return partidaActual;
    }
    
    
    
}

package uy.ctc.edu.obligatoriodda.ClasesYEnums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import uy.ctc.edu.obligatoriodda.ClasesYEnums.ValidacionPorEscalera;
import uy.ctc.edu.obligatoriodda.ClasesYEnums.ValidacionPorNumeracion;
/**
 * 
 *
 * @author Matias
 */
public class Partida {
    
    private int minJugadores; 
    private int idPartida;
    private EstadoPartida estado;
    private float valorApuesta;
    private int puntosDerrota;
    private float pozo;
    private ArrayList<JugadorEnPartida> jugadores;
    private ArrayList<uy.ctc.edu.obligatoriodda.ClasesYEnums.Observer> observadores = new ArrayList<uy.ctc.edu.obligatoriodda.ClasesYEnums.Observer>();
    private ArrayList<Carta> mazo;
    private int turnoActual;
    private ArrayList<Carta> cartasJugadas; 
    private Carta cartaDadaVuelta;
    
    private JugadorEnPartida ultimoJugadorCierre;
    
    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public EstadoPartida getEstado() {
        return estado;
    }

    public void setEstado(EstadoPartida estado) {
        this.estado = estado;
    }

    public float getValorApuesta() {
        return valorApuesta;
    }

    public void setValorApuesta(float valorApuesta) {
        this.valorApuesta = valorApuesta;
    }

    public int getPuntosDerrota() {
        return puntosDerrota;
    }

    public void setPuntosDerrota(int puntosDerrota) {
        this.puntosDerrota = puntosDerrota;
    }

    public float getPozo() {
        return pozo;
    }

    public void setPozo(float pozo) {
        this.pozo = pozo;
    }

    public ArrayList<JugadorEnPartida> getJugadores() {
        return jugadores;
    }

    public void setJugadores(ArrayList<JugadorEnPartida> jugadores) {
        this.jugadores = jugadores;
    }

    public int getMinJugadores() {
        return minJugadores;
    }

    public void setMinJugadores(int minJugadores) {
        this.minJugadores = minJugadores;
    }

    public ArrayList<Carta> getMazo() {
        return mazo;
    }

    public void setMazo(ArrayList<Carta> mazo) {
        this.mazo = mazo;
    }

    public Carta getCartaDadaVuelta() {
        return cartaDadaVuelta;
    }

    public void setCartaDadaVuelta(Carta cartaDadaVuelta) {
        this.cartaDadaVuelta = cartaDadaVuelta;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public void setTurnoActual(int turnoActual) {
        this.turnoActual = turnoActual;
    }

    public ArrayList<uy.ctc.edu.obligatoriodda.ClasesYEnums.Observer> getObservadores() {
        return observadores;
    }

    public void setObservadores(ArrayList<uy.ctc.edu.obligatoriodda.ClasesYEnums.Observer> observadores) {
        this.observadores = observadores;
    }

    public ArrayList<Carta> getCartasUsadas() {
        return cartasJugadas;
    }

    public void setCartasUsadas(ArrayList<Carta> cartasUsadas) {
        this.cartasJugadas = cartasUsadas;
    }

    public JugadorEnPartida getUltimoJugadorCierre() {
        return ultimoJugadorCierre;
    }

    public void setUltimoJugadorCierre(JugadorEnPartida ultimoJugadorCierre) {
        this.ultimoJugadorCierre = ultimoJugadorCierre;
    }
     

    public Partida(int idPartida, EstadoPartida estado, float valorApuesta, int puntosDerrota, float pozo, ArrayList<JugadorEnPartida> jugadores) {
        this.idPartida = idPartida;
        this.estado = estado;
        this.valorApuesta = valorApuesta;
        this.puntosDerrota = puntosDerrota;
        this.pozo = pozo;
        this.jugadores = jugadores;
        this.minJugadores = 2; 
        this.mazo = new ArrayList<>();  
        this.turnoActual = 0; 
        this.cartasJugadas = new ArrayList<>(); 
        
        inicializarMazo();
    }
    public Partida() {

    }
    
    public boolean agregarJugadorPartida(JugadorEnPartida jugadorPartida) {
    if (this.estado == EstadoPartida.EN_ESPERA) {
        if (this.getValorApuesta() >= jugadorPartida.getJugador().getSaldo()) {
            return false;
        } else {
            // Buscar si ya existe un jugador con el mismo nombre de usuario
            for (int i = 0; i < this.jugadores.size(); i++) {
                JugadorEnPartida jugadorExistente = this.jugadores.get(i);
                if (jugadorExistente.getJugador().getNombreUsuario().equals(jugadorPartida.getJugador().getNombreUsuario())) {
                    // Si el jugador ya existe pero no esta en estado JUGANDO  reemplazarlo
                    if (jugadorExistente.getEstado() != EstadoJugador.JUGANDO) {
                        this.jugadores.set(i, jugadorPartida);
                        notificar();
                        return true;
                    } else {
                        // Si el jugador ya esta en estado JUGANDO no se agrega
                        return false;
                    }
                }
            }

            // Si no se encontro un jugador con el mismo nombre de usuario, agregarlo
            this.jugadores.add(jugadorPartida);
            notificar();
            return true;
        }
    }
    return false;
}
    
    public  String avisoJugadores (int idPartida){
        
        int cantJugadoresEmpezar = this.getMinJugadores(); 
        int cantJugadoresPartida = this.jugadores.size();
        
        String respuesta = "jugadores "+cantJugadoresEmpezar+" de "+cantJugadoresPartida;
        
        return respuesta;
    }
    
    
      
    
    public void agregarObservador(uy.ctc.edu.obligatoriodda.ClasesYEnums.Observer o){
        if(!observadores.contains(o))
        {
            observadores.add(o);
        }
    }
    
    public void quitarObservador(uy.ctc.edu.obligatoriodda.ClasesYEnums.Observer o){
        observadores.remove(o);
    }
    
    public void notificar(){
        for(uy.ctc.edu.obligatoriodda.ClasesYEnums.Observer o : observadores){
            o.actualizar(this);
        }
    }
    
    
    //la partida comienza cuando la cantidad de jugadores es igual al minimo
    //se le resta a los jugadores el valor de la apuesta directo a su saldo
    //
    
  public void iniciarPartida() {
      
    if (this.estado == EstadoPartida.EN_ESPERA && this.jugadores.size() == this.minJugadores && this.jugadores.stream().allMatch(jugador -> jugador.getEstado() == EstadoJugador.JUGANDO)) {
        this.estado = EstadoPartida.EN_JUEGO;

          
          this.pozo = 0;
        for (JugadorEnPartida jugadorPartida : jugadores) {
            jugadorPartida.getJugador().setSaldo(jugadorPartida.getJugador().getSaldo() - this.valorApuesta);
            this.pozo += this.valorApuesta;
        }

        
        repartirCartas();



        System.out.println("La partida ha comenzado.");
    }
}

    
    // generador del mazo al azar con el metodo mezclarmazo()
    private void inicializarMazo() {
        String[] palos = {"Espada", "Basto", "Oro", "Copa"};
        for (String palo : palos) {
            for (int i = 1; i <= 12; i++) {
                mazo.add(new Carta(i, palo));
            }
        }
        mezclarMazo();
    }
    
    //mezcla el mazo en base a shuffle
    private void mezclarMazo() {
        Collections.shuffle(mazo); 
    }
 
    
   //repartirCartas() reparte las cartas al inicio de la partida a los jugadores
    //les reparte 7 a cada uno y al primero le reparte 1 mas por ser mano
    //
  
    public void repartirCartas() {
    for (int i = 0; i < jugadores.size(); i++) {
            JugadorEnPartida jugador = jugadores.get(i);

            
            if (jugador.getCartasEnMano() == null) {
                jugador.setCartasEnMano(new ArrayList<>());
            }

            ValidarLigas validacionPorEscalera = new ValidacionPorEscalera();
             ValidarLigas validacionPorNumeracion = new ValidacionPorNumeracion();

            ManoJugador cartasJugador = new ManoJugador(Arrays.asList( validacionPorEscalera, validacionPorNumeracion));
            cartasJugador.setCartasEnMano(new ArrayList<>());
            for (int j = 0; j < 7; j++) {
                if (!mazo.isEmpty()) {
                    cartasJugador.getCartasEnMano().add(mazo.remove(0));
                }
            }

            
            jugador.getCartasEnMano().add(cartasJugador);

            
            if (i == 0 && !mazo.isEmpty()) {
                Carta cartaJugadorMano = mazo.remove(0);
                cartasJugador.getCartasEnMano().add(cartaJugadorMano);
                System.out.println("Primer jugador es mano => " + cartaJugadorMano);
            }
        }
    
    }

    public void asignarCartaDadaVuelta() {
        if (!mazo.isEmpty()) {
            this.cartaDadaVuelta = mazo.remove(0);
            System.out.println("Carta dada vuelta => " + this.cartaDadaVuelta);
        }
    }
    
    
    
    // esTurnoJugador() valida si el turno es del jugador actual
    //
    public boolean esTurnoJugador(JugadorEnPartida jugador) {
        return jugadores.get(turnoActual).equals(jugador);
    }

    // avanzarTurno() hace que el turno pase al siguiente jugador 
    //
    public void avanzarTurno() {
    do {
        turnoActual = (turnoActual + 1) % jugadores.size();
    } while (jugadores.get(turnoActual).getEstado().equals("PERDIO"));

    notificar(); // Notificar a la interfaz que el turno ha cambiado
    }

    
    // Método para que el jugador actual tire una carta
    
 public void tirarCarta(Carta carta, JugadorEnPartida jugador) {
    if (!esTurnoJugador(jugador)) {
        System.out.println("No es el turno del jugador: " + jugador.getJugador().getNombre());
        return;
    }

    ArrayList<Carta> cartasJugador = jugador.getCartasEnMano().get(0).getCartasEnMano();
    if (cartasJugador.size() != 8) {
        System.out.println("El jugador no tiene 8 cartas en su mano. No puede tirar una carta.");
        return;
    }

    if (!cartasJugador.contains(carta)) {
        System.out.println("La carta no está en la mano del jugador.");
        return;
    }

    cartasJugador.remove(carta);
    cartasJugadas.add(carta); // Agregar al array de cartas jugadas
    this.cartaDadaVuelta = carta;

    // Verificar las cartas descartadas
    for (Carta c : cartasJugadas) {
        System.out.println(" - " + c.getTipo() + " " + c.getNumero());
    }

    avanzarTurno();
    notificar();
}
    
    public JugadorEnPartida obtenerJugadorTurno(){
        return jugadores.get(turnoActual);
    }
    
    public void recargarMazoConCartasUsadas() {
        if (!cartasJugadas.isEmpty()) {
            // Agregar las cartas jugadas al mazo
            mazo.addAll(cartasJugadas);
            // Vaciar las cartas jugadas
            cartasJugadas.clear();
            // mezlcar el mazo nuevamente
            mezclarMazo();
        }
    }
    
    
  public Carta tomarCartaDadaVuelta(JugadorEnPartida jugador) {
    // Validar que es el turno del jugador
    if (!esTurnoJugador(jugador)) {
        return null;
    }

    // Validar que la carta dada vuelta no sea nula
    if (cartaDadaVuelta == null) {
        return null;
    }

    // Obtener las cartas del jugador
    ArrayList<Carta> cartasJugador = jugador.getCartasEnMano().get(0).getCartasEnMano();

    // Validar que el jugador tiene exactamente 7 cartas antes de tomar la carta dada vuelta
    if (cartasJugador.size() != 7) {
        return null;
    }

    // Tomar la carta dada vuelta
    Carta cartaTomada = cartaDadaVuelta;
    cartasJugador.add(cartaTomada);

    if (cartasJugadas.contains(cartaTomada)) {
        cartasJugadas.remove(cartaTomada);
    }

    if (!cartasJugadas.isEmpty()) {
        cartaDadaVuelta = cartasJugadas.remove(cartasJugadas.size() - 1);
    } else if (!mazo.isEmpty()) {
        cartaDadaVuelta = mazo.remove(0);
    } else {
        cartaDadaVuelta = null;
    }
    notificar();
    return cartaTomada;
}
public Carta asignarNuevaCartaDadaVuelta() {
    if (!cartasJugadas.isEmpty()) {
        Carta nuevaCarta = cartasJugadas.remove(cartasJugadas.size() - 1);
        return nuevaCarta;
    }

    if (!mazo.isEmpty()) {
        Carta nuevaCarta = mazo.remove(0);
        return nuevaCarta;
    }

    return null;
}


public boolean validarCierreMano(JugadorEnPartida jugador) {

   //getligas del jugador
    ArrayList<ArrayList<Carta>> ligas = jugador.getCartasEnMano().get(0).getLigaCartas();

    for (ArrayList<Carta> liga : ligas) {
    }

    // no tiene ligas para poder cerrar
    if (ligas == null || ligas.isEmpty()) {
        return false; 
    }

   
    int ligasDeTres = 0;
    int ligasDeCuatro = 0;

    for (ArrayList<Carta> liga : ligas) {
        if (liga.size() == 3) ligasDeTres++;
        if (liga.size() == 4) ligasDeCuatro++;
    }

    int cartasRestantes = jugador.getCartasEnMano().get(0).getCartasEnMano().size()
            - ligas.stream().mapToInt(ArrayList::size).sum();

    System.out.println("Ligas de 3: " + ligasDeTres);
    System.out.println("Ligas de 4: " + ligasDeCuatro);
    System.out.println("Cartas sin ligar: " + cartasRestantes);

   
    if (ligasDeTres == 1 && ligasDeCuatro == 1 && cartasRestantes == 1) {
        
        ArrayList<Carta> cartasSinLigar = new ArrayList<>(jugador.getCartasEnMano().get(0).getCartasEnMano());
        for (ArrayList<Carta> liga : ligas) {
            cartasSinLigar.removeAll(liga);
        }
        boolean cartaValida = cartasSinLigar.stream().allMatch(carta -> carta.getNumero() < 6);
        if (cartaValida) {
            return true;
        } else {
        }
    }

    for (ArrayList<Carta> liga : ligas) {
        if (liga.size() == 7) {
            return true;
        }
    }

    return false;
}

public void cerrarMano(JugadorEnPartida jugador) {

    if (!validarCierreMano(jugador)) {
        return;
    }

    this.ultimoJugadorCierre = jugador;
    
    // verificar si alguien perdio
    for (JugadorEnPartida jp : jugadores) {
        int puntos = jp.getCartasEnMano().get(0).getCartasEnMano().stream()
                .filter(carta -> jp.getCartasEnMano().get(0).getLigaCartas().stream()
                        .flatMap(List::stream).noneMatch(c -> c.equals(carta)))
                .mapToInt(carta -> carta.getNumero() > 10 ? 10 : carta.getNumero())
                .sum();


        // Actualizar puntaje total
        jp.setPuntaje(jp.getPuntaje() + puntos);

        if (jp.equals(jugador)) {
            if (validarCierreMano(jugador)) {
                if (jugador.getCartasEnMano().get(0).getLigaCartas().size() == 1 &&
                        jugador.getCartasEnMano().get(0).getLigaCartas().get(0).size() == 7) {
                    this.estado = EstadoPartida.FINALIZADO;                
                    notificar();
                    
                    return;
                }
                puntos -= 10;
            }
        }

        if (jp.getPuntaje() >= puntosDerrota) {
            jp.setEstado(EstadoJugador.PERDIO);
        }
    }

    // Verificar si solo queda un jugador activo
    long jugadoresActivos = jugadores.stream().filter(jp -> jp.getEstado() != EstadoJugador.PERDIO).count();
    if (jugadoresActivos == 1) {
        this.estado = EstadoPartida.FINALIZADO;

       JugadorEnPartida ganador = jugadores.stream()
            .filter(jp -> jp.getEstado() != EstadoJugador.PERDIO)
            .findFirst()
            .orElse(null);

        if (ganador != null) {
            siguienteRonda();
        }
        notificar();
        return;
    }

    siguienteRonda();
}
  

public void siguienteRonda() {

    // agrupar todas las cartas
     // agregar cartas restantes del mazo
     // agregar cartas usadas
      // agregar cartas restantes de los jugadores
    ArrayList<Carta> todasLasCartas = new ArrayList<>();
    todasLasCartas.addAll(mazo);
    todasLasCartas.addAll(cartasJugadas);
    for (JugadorEnPartida jp : jugadores) {
        if (jp.getEstado() == EstadoJugador.JUGANDO) {
            if (jp.getCartasEnMano() != null && !jp.getCartasEnMano().isEmpty()) {
                todasLasCartas.addAll(jp.getCartasEnMano().get(0).getCartasEnMano());
                jp.getCartasEnMano().clear(); // Vaciar las cartas del jugador
            }
        }
    }

    //vaciar los mazos
    //mezclar las cartas
    //agregar al mazo 
    mazo.clear();
    cartasJugadas.clear();
    Collections.shuffle(todasLasCartas);
    mazo.addAll(todasLasCartas);

    
    for (JugadorEnPartida jp : jugadores) {
        if (jp.getEstado() == EstadoJugador.JUGANDO) {
            ManoJugador cartasJugador = new ManoJugador();
            cartasJugador.setCartasEnMano(new ArrayList<>());

            for (int i = 0; i < 7; i++) {
                if (!mazo.isEmpty()) {
                    cartasJugador.getCartasEnMano().add(mazo.remove(0));
                }
            }

            jp.setCartasEnMano(new ArrayList<>());
            jp.getCartasEnMano().add(cartasJugador);

            if (jp.equals(jugadores.get(0)) && !mazo.isEmpty()) {
                Carta cartaJugadorMano = mazo.remove(0);
                cartasJugador.getCartasEnMano().add(cartaJugadorMano);
            }
        }
    }

    // asignar carta dada vuelta
    // dar turno al siguiente jugador
    // notificar a los observers
    cartaDadaVuelta = asignarNuevaCartaDadaVuelta();
    avanzarTurno();
    notificar();
}

 
    public boolean retirarJugador(JugadorEnPartida jugador) {

    if (this.estado == EstadoPartida.FINALIZADO) {
        return false;
    }

    // buscar jugador en la mesa
    // cambiar estado a RETIRADO
      // Devolver la apuesta al jugador, si corresponde
      // Actualizar el pozo
    if (this.jugadores.contains(jugador)) {
        jugador.setEstado(EstadoJugador.RETIRADO);
        jugador.getJugador().setSaldo(jugador.getJugador().getSaldo() + this.valorApuesta);
        this.pozo -= this.valorApuesta;

        // corroborar si quedan suficientes jugadores para continuar con la partida
        //y en caso de que no haya mas jugadores dar por termianda la partida
        long jugadoresActivos = this.jugadores.stream()
            .filter(j -> j.getEstado() == EstadoJugador.JUGANDO)
            .count();
        if (jugadoresActivos < this.minJugadores) { 
            this.estado = EstadoPartida.EN_ESPERA;
        }
        
    // notificar a los observers
        notificar();
        return true;
    }

    return false;
}
}

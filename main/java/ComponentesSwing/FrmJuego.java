/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ComponentesSwing;

import uy.ctc.edu.obligatoriodda.ClasesYEnums.Carta;
import uy.ctc.edu.obligatoriodda.ClasesYEnums.ManoJugador;
import uy.ctc.edu.obligatoriodda.ClasesYEnums.EstadoJugador;
import uy.ctc.edu.obligatoriodda.ClasesYEnums.EstadoPartida;
import uy.ctc.edu.obligatoriodda.ClasesYEnums.Fachada;
import uy.ctc.edu.obligatoriodda.ClasesYEnums.SingletonPartida;
import uy.ctc.edu.obligatoriodda.ClasesYEnums.JugadorEnPartida;
import uy.ctc.edu.obligatoriodda.ClasesYEnums.Partida;
import uy.ctc.edu.obligatoriodda.ClasesYEnums.Observer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import tablascartas.MiOtroTableModel;

/**
 *
 * @author Matias
 */
public class FrmJuego extends javax.swing.JFrame implements Observer {

    /**
     * Creates new form FrmJuego
     */
//    GestoraPartidaSingleton singletonPartida = GestoraPartidaSingleton.getInstancia();
    Fachada fachadaJuego = new Fachada(); // Instancia de la fachada
    
    private JugadorEnPartida jugador;
    private Partida partida;
    private java.util.HashSet<Carta> cartasSeleccionadas = new java.util.HashSet<>();
    
    public FrmJuego(Partida objPartida, JugadorEnPartida objJugador) {
        initComponents();
        this.jugador = objJugador;
        this.partida = objPartida;
        this.partida.agregarObservador(this);
        actualizar(this.partida);
//        btnSiguienteRonda.addActionListener(e -> avanzarTurno());
        
        
        JLNumPart.setText("Partida N°: " + partida.getIdPartida());
        JLSaldo.setText("Saldo: " + this.jugador.getJugador().getSaldo());
        jLJugador.setText("Jugador: "+this.jugador.getJugador().getNombre());
        jLPozo.setText("Pozo: "+this.partida.getPozo());
        
        configurarClickEnMazo();
                
        // Cargar la imagen desde la ruta
        
        String projectPath = System.getProperty("user.dir");
        String imagePath = projectPath + "/src/main/java/tablascartas/cartas/mazo.png";
        
        
//        ImageIcon icono = new ImageIcon("C:\\Users\\Mateo\\Documents\\GitHub\\obligatorioDDA\\src\\main\\java\\tablascartas\\cartas\\mazo.png");
        ImageIcon icono = new ImageIcon(imagePath);
        Image imagenEscalada = icono.getImage().getScaledInstance(jPMazo.getWidth(), jPMazo.getHeight(), Image.SCALE_SMOOTH);
    
        
        // Actualizar el icono del JLabel
        jPMazo.setIcon(new ImageIcon(imagenEscalada)); 

        // Refrescar el JLabel y su contenedor
        jPMazo.revalidate();
        jPMazo.repaint();
           
                
        // Configurar JTable
        jTable2.setRowHeight(150);
        jTable2.setDefaultRenderer(Object.class, new CartaCellRenderer());
//        

        // deshabilita edición de celdas para que no se ejecute el click
        jTable2.setDefaultEditor(Object.class, null);

        // deshabilita el foco y la seleccion visible para que no se ejecute el click
        jTable2.setFocusable(false);
        jTable2.setRowSelectionAllowed(false);
        jTable2.setColumnSelectionAllowed(false);
        jTable2.setCellSelectionEnabled(false);


    jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int row = jTable2.rowAtPoint(evt.getPoint());
            int col = jTable2.columnAtPoint(evt.getPoint());

            if (row != -1 && col != -1) { // Verificar celda válida
                Object value = jTable2.getValueAt(row, col);
                if (value instanceof Carta) {
                    Carta carta = (Carta) value;

                    // Alternar selección de la carta
                    if (cartasSeleccionadas.contains(carta)) {
                        cartasSeleccionadas.remove(carta); // Deseleccionar
                    } else {
                        cartasSeleccionadas.add(carta); // Seleccionar
                    }

                    // Refrescar la tabla para actualizar el borde
                    jTable2.repaint();
                }
            }
        }
    });

// Custom TableCellRenderer para alternar el borde rojo
jTable2.setDefaultRenderer(Object.class, new TableCellRenderer() {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
        Component component = new CartaCellRenderer().getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

        if (component instanceof javax.swing.JComponent) {
            javax.swing.JComponent jc = (javax.swing.JComponent) component;

            // Verificar si cartasEnMano es null e inicializar si es necesario
            if (jugador.getCartasEnMano() == null || jugador.getCartasEnMano().isEmpty()) {
                jc.setBorder(null); // Sin borde si no hay cartas
                return component;
            }

            ArrayList<ArrayList<Carta>> ligas = jugador.getCartasEnMano().get(0).getLigaCartas();

            // Manejar ligaCartas null
            if (ligas == null) {
                ligas = new ArrayList<>(); // Inicializar localmente si es null
            }

            // Verificar si la carta pertenece a una liga válida
            if (value instanceof Carta) {
                boolean perteneceALiga = ligas.stream()
                    .anyMatch(liga -> liga.contains(value));

                if (perteneceALiga) {
                    jc.setBorder(javax.swing.BorderFactory.createLineBorder(Color.YELLOW, 3));
                } else if (cartasSeleccionadas.contains(value)) {
                    // Borde rojo para cartas seleccionadas
                    jc.setBorder(javax.swing.BorderFactory.createLineBorder(Color.RED, 3));
                } else {
                    jc.setBorder(null);
                }
            } else {
                jc.setBorder(null); // Sin borde si no aplica
            }
        }
        return component;
    }
});
//////////////////////////////////////////////////////////////////////////
        

btnValidarLigas.addActionListener(e -> {
    if (cartasSeleccionadas.isEmpty()) {
        System.out.println("No hay cartas seleccionadas para validar.");
        return;
    }

    ArrayList<Carta> cartasAValidar = new ArrayList<>(cartasSeleccionadas);

    // Inicializar estructuras si es necesario
    if (jugador.getCartasEnMano() == null) {
        jugador.setCartasEnMano(new ArrayList<>());
        jugador.getCartasEnMano().add(new ManoJugador());
    }

    if (jugador.getCartasEnMano().get(0).getLigaCartas() == null) {
        jugador.getCartasEnMano().get(0).setLigaCartas(new ArrayList<>());
    }

    // Validar y agregar ligas
//    if (jugador.getCartasEnMano().get(0).agregarLigas(cartasAValidar)) {
    if (fachadaJuego.validarLigas(jugador,cartasAValidar)) {
        System.out.println("Liga procesada.");
        // Eliminar de cartasSeleccionadas las que forman parte de la liga validada
        cartasSeleccionadas.removeAll(cartasAValidar);
    } else {
        System.out.println("Liga no válida.");
    }

    // Actualizar la tabla
    cargarCartasEnTabla();

    // Limpiar selección
    jTable2.repaint();
});

jPCartaDadaVuelta.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        tomarCartaDadaVuelta();
    }
});


        //terminar la partida
    btnCerrarMano.addActionListener(e -> {
        if (partida.validarCierreMano(jugador)) {
            partida.cerrarMano(jugador);
            actualizar(partida); // Actualizar la interfaz con los nuevos cambios
        } else {
            System.out.println("No puedes cerrar la mano en este momento.");
        }
    });



//        jTable2.hide();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        JLJugadores = new javax.swing.JLabel();
        jPPanelPrincipal = new javax.swing.JPanel();
        jPCartaDadaVuelta = new javax.swing.JPanel();
        JLNumPart = new javax.swing.JLabel();
        JLSaldo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLJugador = new javax.swing.JLabel();
        jLPozo = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLJugadorTurno = new javax.swing.JLabel();
        jPMazo = new javax.swing.JLabel();
        btnValidarLigas = new javax.swing.JButton();
        btnCerrarMano = new javax.swing.JButton();
        btnDeshacerliga = new javax.swing.JButton();
        JLJugadoresInfo = new javax.swing.JLabel();
        btnSalirPartida = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        JLJugadores.setFont(new java.awt.Font("Segoe UI", 0, 48)); // NOI18N
        JLJugadores.setText("juadores disponibles");

        javax.swing.GroupLayout jPCartaDadaVueltaLayout = new javax.swing.GroupLayout(jPCartaDadaVuelta);
        jPCartaDadaVuelta.setLayout(jPCartaDadaVueltaLayout);
        jPCartaDadaVueltaLayout.setHorizontalGroup(
            jPCartaDadaVueltaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 182, Short.MAX_VALUE)
        );
        jPCartaDadaVueltaLayout.setVerticalGroup(
            jPCartaDadaVueltaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 263, Short.MAX_VALUE)
        );

        JLNumPart.setText("Partida N°: ");

        JLSaldo.setText("Saldo:");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable2.setTableHeader(null);
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 825, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLJugador.setText("Jugador:");

        jLPozo.setText("Pozo:");

        jButton1.setText("tirar carta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLJugadorTurno.setText("Jugador en turno:");

        jPMazo.setText("mazo");
        jPMazo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPMazoMouseClicked(evt);
            }
        });

        btnValidarLigas.setText("validar liga");

        btnCerrarMano.setText("Cerrar Mano");
        btnCerrarMano.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarManoActionPerformed(evt);
            }
        });

        btnDeshacerliga.setText("Deshacer Liga ");
        btnDeshacerliga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeshacerligaActionPerformed(evt);
            }
        });

        JLJugadoresInfo.setText("jLabel1");

        btnSalirPartida.setText("salir partida");
        btnSalirPartida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirPartidaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPPanelPrincipalLayout = new javax.swing.GroupLayout(jPPanelPrincipal);
        jPPanelPrincipal.setLayout(jPPanelPrincipalLayout);
        jPPanelPrincipalLayout.setHorizontalGroup(
            jPPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPanelPrincipalLayout.createSequentialGroup()
                .addGroup(jPPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLPozo, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(JLSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLJugadorTurno)
                    .addGroup(jPPanelPrincipalLayout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(JLJugadoresInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 600, Short.MAX_VALUE)
                .addComponent(jPCartaDadaVuelta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
            .addGroup(jPPanelPrincipalLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(btnSalirPartida)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPPanelPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPPanelPrincipalLayout.createSequentialGroup()
                            .addGap(0, 109, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(48, 48, 48))
                        .addGroup(jPPanelPrincipalLayout.createSequentialGroup()
                            .addComponent(JLNumPart, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPPanelPrincipalLayout.createSequentialGroup()
                                    .addGap(169, 169, 169)
                                    .addComponent(jLJugador, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(446, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPPanelPrincipalLayout.createSequentialGroup()
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jPMazo, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(594, 594, 594))))
                        .addGroup(jPPanelPrincipalLayout.createSequentialGroup()
                            .addGap(119, 119, 119)
                            .addComponent(jButton1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnValidarLigas, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnDeshacerliga)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnCerrarMano)
                            .addGap(77, 77, 77)))))
        );
        jPPanelPrincipalLayout.setVerticalGroup(
            jPPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPPanelPrincipalLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(JLSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLPozo)
                .addGap(18, 18, 18)
                .addComponent(jLJugadorTurno)
                .addGap(37, 37, 37)
                .addComponent(JLJugadoresInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSalirPartida)
                .addGap(23, 23, 23))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPPanelPrincipalLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jPCartaDadaVuelta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(314, 314, 314))
            .addGroup(jPPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPPanelPrincipalLayout.createSequentialGroup()
                    .addGap(3, 3, 3)
                    .addGroup(jPPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPPanelPrincipalLayout.createSequentialGroup()
                            .addComponent(JLNumPart, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 359, Short.MAX_VALUE)
                            .addGroup(jPPanelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton1)
                                .addComponent(btnValidarLigas)
                                .addComponent(btnCerrarMano)
                                .addComponent(btnDeshacerliga)))
                        .addGroup(jPPanelPrincipalLayout.createSequentialGroup()
                            .addComponent(jLJugador)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPMazo, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 137, Short.MAX_VALUE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(3, 3, 3)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPPanelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(235, 235, 235)
                    .addComponent(JLJugadores, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(256, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPPanelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(277, 277, 277)
                    .addComponent(JLJugadores, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(277, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (cartasSeleccionadas.size() == 1) { // Validar que solo una carta esté seleccionada
            Carta carta = cartasSeleccionadas.iterator().next(); // Obtener la única carta seleccionada
            tirarCarta(carta); // Ejecutar lógica de tirar carta
        } else {
            System.out.println("Debe seleccionar exactamente una carta para tirar.");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jPMazoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPMazoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPMazoMouseClicked

    private void btnCerrarManoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarManoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCerrarManoActionPerformed

    private void btnDeshacerligaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeshacerligaActionPerformed
        // Verificar si el jugador tiene cartas en mano y ligas
        if (jugador.getCartasEnMano() != null && !jugador.getCartasEnMano().isEmpty()) {
            ManoJugador cartasJugador = jugador.getCartasEnMano().get(0);

            // Deshacer todas las ligas
            cartasJugador.desLigar();

            // Actualizar la tabla para reflejar los cambios
            cargarCartasEnTabla();

            // Mostrar mensaje de confirmación
            System.out.println("Se han deshecho todas las ligas para el jugador: " + jugador.getJugador().getNombre());
        } else {
            System.out.println("No hay ligas para deshacer.");
        }
    }//GEN-LAST:event_btnDeshacerligaActionPerformed

    private void btnSalirPartidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirPartidaActionPerformed
        if(this.partida.retirarJugador(jugador)){
            dispose();
        }
    }//GEN-LAST:event_btnSalirPartidaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        Partida objPartida = new Partida();
        JugadorEnPartida objJugador = new JugadorEnPartida();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmJuego(objPartida, objJugador).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
private javax.swing.JLabel JLJugadores;
private javax.swing.JLabel JLJugadoresInfo;
private javax.swing.JLabel JLNumPart;
private javax.swing.JLabel JLSaldo;
private javax.swing.JButton btnCerrarMano;
private javax.swing.JButton btnDeshacerliga;
private javax.swing.JButton btnSalirPartida;
private javax.swing.JButton btnValidarLigas;
private javax.swing.JButton jButton1;
private javax.swing.JLabel jLJugador;
private javax.swing.JLabel jLJugadorTurno;
private javax.swing.JLabel jLPozo;
private javax.swing.JPanel jPCartaDadaVuelta;
private javax.swing.JLabel jPMazo;
private javax.swing.JPanel jPPanelPrincipal;
private javax.swing.JPanel jPanel1;
private javax.swing.JScrollPane jScrollPane1;
private javax.swing.JScrollPane jScrollPane2;
private javax.swing.JTable jTable1;
private javax.swing.JTable jTable2;
// End of variables declaration//GEN-END:variables

@Override
public void actualizar(Partida partida) {

    partida = fachadaJuego.iniciarPartida(partida);

    JugadorEnPartida objJugador = new JugadorEnPartida();

    for (JugadorEnPartida jugador : partida.getJugadores()) {
        if (this.jugador.getJugador().getNombreUsuario().equals(jugador.getJugador().getNombreUsuario())) {
            objJugador = jugador;
            break;
        }
    }

    this.jugador = objJugador;
    this.partida = partida;

    if (this.partida.getEstado() == EstadoPartida.EN_JUEGO) {

        boolean esMiTurno = partida.esTurnoJugador(partida.getJugadores().get(partida.getTurnoActual()));
        jTable2.setEnabled(esMiTurno);

        jLJugadorTurno.setText("<html><b>Jugador en turno:</b> " + this.partida.obtenerJugadorTurno().getJugador().getNombre() + "</html>");

        JLSaldo.setText("<html><b>Saldo:</b> " + this.jugador.getJugador().getSaldo() + "</html>");

        if (this.partida.getEstado() == EstadoPartida.EN_JUEGO) {
            JLJugadores.setVisible(false);
            jLPozo.setText("<html><b>Pozo:</b> " + this.partida.getPozo() + "</html>");

            cargarCartasEnTabla();
            mostrarCartaDadaVuelta(this.partida.getCartaDadaVuelta());
            jTable2.setVisible(true);
            jPPanelPrincipal.setVisible(true);

            StringBuilder jugadoresInfo = new StringBuilder();
            for (JugadorEnPartida jp : partida.getJugadores()) {
                jugadoresInfo.append(jp.getJugador().getNombre())
                             .append(" - <b>Puntos:</b> ").append(jp.getPuntaje())
                             .append(" - <b>Estado:</b> ").append(jp.getEstado())
                             .append("\n");
            }

            JLJugadoresInfo.setText("<html>" + jugadoresInfo.toString().replace("\n", "<br>") + "</html>");
        }

        if (this.jugador.getEstado() == EstadoJugador.PERDIO) {
            if (this.partida.getUltimoJugadorCierre() != null) {
                new FrmAvisoJuego(this.partida.getUltimoJugadorCierre(), this.partida.getPozo()).setVisible(true);
            }

            this.dispose();
            return;
        }

    } else if (this.partida.getEstado() == EstadoPartida.FINALIZADO) {
        if (this.partida.getUltimoJugadorCierre() != null) {
            new FrmAvisoJuego(this.partida.getUltimoJugadorCierre(), this.partida.getPozo()).setVisible(true);
        }

        this.dispose();
        return;

    } else if (this.partida.getEstado() == EstadoPartida.EN_ESPERA) {
        JLJugadores.setText(fachadaJuego.avisoJugadores(partida.getIdPartida()));
        JLJugadores.setVisible(true);
        jPPanelPrincipal.setVisible(false);
        btnSalirPartida.setVisible(true);
    }

    System.out.println("PARTIDAAAA ===> " + this.partida.getEstado());
}


private void cargarCartasEnTabla() {
    if (this.jugador.getCartasEnMano() != null && !this.jugador.getCartasEnMano().isEmpty()) {
        // Obtener todas las cartas en mano
        ArrayList<Carta> cartasEnMano = this.jugador.getCartasEnMano().get(0).getCartasEnMano();

        // Configurar el modelo de la tabla con una fila y tantas columnas como cartas
        DefaultTableModel model = new DefaultTableModel(1, cartasEnMano.size());
        jTable2.setModel(model);

        // Asignar las cartas a las columnas de la fila única
        for (int i = 0; i < cartasEnMano.size(); i++) {
            jTable2.setValueAt(cartasEnMano.get(i), 0, i);
        }

        // Actualizar la interfaz gráfica
        jTable2.revalidate();
        jTable2.repaint();
    } else {
        System.out.println("No hay cartas para cargar en el JTable.");
    }
}

private class CartaCellRenderer extends javax.swing.JPanel implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Carta) {
            Carta carta = (Carta) value;

            // Ruta de la imagen de la carta
            String projectPath = System.getProperty("user.dir");
            String imagePath = projectPath + "/src/main/java/tablascartas/cartas/" 
                    + carta.getTipo() + "_" + carta.getNumero() + "s.jpg";

            try {
                File imageFile = new File(imagePath);
                JLabel label;
                if (imageFile.exists()) {
                    ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
                    Image scaledImage = icon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
                    label = new JLabel(new ImageIcon(scaledImage));
                } else {
                    label = new JLabel("Sin imagen");
                    label.setFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 12));
                    label.setForeground(new java.awt.Color(150, 150, 150)); // Light gray for missing image text
                }

                // Check if the card belongs to a league
                boolean perteneceALiga = jugador.getCartasEnMano().get(0).getLigaCartas() != null &&
                        jugador.getCartasEnMano().get(0).getLigaCartas().stream()
                        .anyMatch(liga -> liga.contains(carta));

                // Visual improvements
                if (perteneceALiga) {
                    label.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 215, 0), 3), // Gold border
                            javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
                    ));
                } else {
                    label.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(50, 50, 50), 1), // Dark gray border
                            javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5) // Padding
                    ));
                }

                // Set label alignment and background
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setVerticalAlignment(JLabel.CENTER);
                label.setOpaque(true);
                label.setBackground(new java.awt.Color(245, 245, 245)); // Light gray background for contrast

                if (isSelected) {
                    label.setBackground(new java.awt.Color(200, 230, 255)); // Highlight background for selected cell
                }

                return label;
            } catch (Exception e) {
                System.out.println("Error cargando la imagen: " + e.getMessage());
                JLabel errorLabel = new JLabel("Error al cargar");
                errorLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
                errorLabel.setForeground(new java.awt.Color(255, 0, 0)); // Red text for error
                errorLabel.setHorizontalAlignment(JLabel.CENTER);
                errorLabel.setVerticalAlignment(JLabel.CENTER);
                return errorLabel;
            }
        } else {
            JLabel placeholderLabel = new JLabel("Sin imagen");
            placeholderLabel.setFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 12));
            placeholderLabel.setForeground(new java.awt.Color(150, 150, 150)); // Light gray text
            placeholderLabel.setHorizontalAlignment(JLabel.CENTER);
            placeholderLabel.setVerticalAlignment(JLabel.CENTER);
            placeholderLabel.setOpaque(true);
            placeholderLabel.setBackground(new java.awt.Color(245, 245, 245)); // Light gray background
            return placeholderLabel;
        }
    }
}

    
private void mostrarCartaDadaVuelta(Carta carta) {
    // Limpiar el panel
    jPCartaDadaVuelta.removeAll();

    if (carta != null) {
        String imagePath = "C:\\Users\\Mateo\\Documents\\GitHub\\obligatorioDDA\\src\\main\\java\\tablascartas\\cartas\\" 
                + carta.getTipo() + "_" + carta.getNumero() + "s.jpg";

        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                // Load and scale the card image
                ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
                Image scaledImage = icon.getImage().getScaledInstance(
                        jPCartaDadaVuelta.getWidth() - 20,  // Add padding for a cleaner look
                        jPCartaDadaVuelta.getHeight() - 20,
                        Image.SCALE_SMOOTH
                );

                // Create a JLabel for the card with a border and shadow effect
                JLabel cartaLabel = new JLabel(new ImageIcon(scaledImage));
                cartaLabel.setHorizontalAlignment(JLabel.CENTER);
                cartaLabel.setVerticalAlignment(JLabel.CENTER);
                cartaLabel.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(50, 50, 50), 3),  // Dark border
                        javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)  // Inner padding
                ));

                // Set layout and add the card
                jPCartaDadaVuelta.setLayout(new java.awt.BorderLayout());
                jPCartaDadaVuelta.setBackground(new java.awt.Color(200, 230, 255));  // Soft blue background
                jPCartaDadaVuelta.add(cartaLabel, java.awt.BorderLayout.CENTER);
            } else {
                System.out.println("Archivo de imagen no encontrado: " + imagePath);
                showEmptyCard();
            }
        } catch (Exception e) {
            System.out.println("Error cargando la imagen: " + e.getMessage());
            showEmptyCard();
        }
    } else {
        System.out.println("No hay carta dada vuelta disponible.");
        showEmptyCard();
    }

    // Refresh the panel
    jPCartaDadaVuelta.revalidate();
    jPCartaDadaVuelta.repaint();
}

private void showEmptyCard() {
    JLabel placeholderLabel = new JLabel("");
    placeholderLabel.setHorizontalAlignment(JLabel.CENTER);
    placeholderLabel.setVerticalAlignment(JLabel.CENTER);
    placeholderLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
    placeholderLabel.setForeground(new java.awt.Color(100, 100, 100));  // Gray text
    jPCartaDadaVuelta.setLayout(new java.awt.BorderLayout());
    jPCartaDadaVuelta.setBackground(new java.awt.Color(240, 240, 240));  // Light gray background
    jPCartaDadaVuelta.add(placeholderLabel, java.awt.BorderLayout.CENTER);
}
    
private void tirarCarta(Carta carta) {
    // Validar si es el turno del jugador
    if (!partida.esTurnoJugador(jugador)) {
        System.out.println("No es tu turno. No puedes tirar una carta.");
        return;
    }

    // Obtener las "cartas jugador", es decir, todas las cartas activas y en ligas
    ArrayList<Carta> cartasJugador = jugador.getCartasEnMano().get(0).getCartasEnMano(); // Método que devuelve todas las cartas del jugador

    // Validar que el jugador tiene exactamente 8 cartas
    if (cartasJugador.size() != 8) {
        System.out.println("No tienes 8 cartas en tu mano. No puedes tirar una carta.");
        return;
    }

    // Validar que la carta seleccionada está en las "cartas jugador"
    if (!cartasJugador.contains(carta)) {
        System.out.println("La carta seleccionada no está en tu mano.");
        return;
    }

    // Enviar la carta a la lógica de la partida
    partida.tirarCarta(carta, jugador);

    // Actualizar la interfaz: mostrar la carta dada vuelta y recargar la tabla de cartas
    mostrarCartaDadaVuelta(partida.getCartaDadaVuelta());
    cargarCartasEnTabla();

    // Limpiar las cartas seleccionadas después de tirar
    cartasSeleccionadas.clear();
}


private void visualizarCartasSeleccionadas() {
    if (cartasSeleccionadas.isEmpty()) {
//        System.out.println("No hay cartas seleccionadas.");
    } else {
//        System.out.println("Cartas seleccionadas:");
        for (Carta carta : cartasSeleccionadas) {
            // Asumiendo que la clase Carta tiene métodos como getPalo() y getNumero()
            System.out.println("Carta: " + carta.getTipo() + " " + carta.getNumero());
        }
    }
}


private void configurarClickEnMazo() {
    jPMazo.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            // Validar si es el turno del jugador actual
            if (!partida.esTurnoJugador(jugador)) {
                System.out.println("No es tu turno. No puedes tomar una carta del mazo.");
                return;
            }

            // Obtener las cartas del jugador actual
            ArrayList<Carta> cartasJugador = jugador.getCartasEnMano().get(0).getCartasEnMano();

            // Validar que el jugador tiene exactamente 7 cartas
            if (cartasJugador.size() != 7) {
                System.out.println("Debes tener exactamente 7 cartas para tomar una carta del mazo.");
                return;
            }

            // Obtener una carta del mazo
            ArrayList<Carta> mazo = partida.getMazo();
            if (!mazo.isEmpty()) {
                Carta cartaAleatoria = mazo.remove(0); // Remover la carta del mazo
                cartasJugador.add(cartaAleatoria);    // Agregar la carta al jugador
                
                // Mostrar mensaje en la consola
                System.out.println("Se agregó la carta: " + cartaAleatoria.getTipo() + " " + cartaAleatoria.getNumero() + " al jugador: " + jugador.getJugador().getNombre());

                // Actualizar la interfaz gráfica
                cargarCartasEnTabla();
            } else {
                System.out.println("El mazo está vacío. Recargando con cartas usadas...");
                partida.recargarMazoConCartasUsadas();

                // Intentar nuevamente obtener una carta si el mazo fue recargado
                if (!mazo.isEmpty()) {
                    Carta cartaAleatoria = mazo.remove(0); // Remover la carta del mazo
                    cartasJugador.add(cartaAleatoria);    // Agregar la carta al jugador
                    
                    // Mostrar mensaje en la consola
                    System.out.println("Se agregó la carta: " + cartaAleatoria.getTipo() + " " + cartaAleatoria.getNumero() + " al jugador: " + jugador.getJugador().getNombre());

                    // Actualizar la interfaz gráfica
                    cargarCartasEnTabla();
                } else {
                    System.out.println("El mazo sigue vacío. No hay cartas disponibles.");
                }
            }
        }
    });
}

    private void tomarCartaDadaVuelta() {
        if (!fachadaJuego.esTurnoJugador(partida, jugador)) {
            System.out.println("No es tu turno. No puedes tomar la carta dada vuelta.");
            return;
        }

        Carta cartaTomada = fachadaJuego.tomarCartaDadaVuelta(partida, jugador);
        if (cartaTomada != null) {
            System.out.println("Se tomó la carta dada vuelta: " + cartaTomada);
        }

        mostrarCartaDadaVuelta(fachadaJuego.obtenerCartaDadaVuelta(partida));
        cargarCartasEnTabla();
    }

}

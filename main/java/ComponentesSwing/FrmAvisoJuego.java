// FrmAvisoJuego with Enhanced Visual Design
package ComponentesSwing;

import uy.ctc.edu.obligatoriodda.ClasesYEnums.Carta;
import uy.ctc.edu.obligatoriodda.ClasesYEnums.JugadorEnPartida;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * A JFrame to display the winner's information and cards after a game.
 */
public class FrmAvisoJuego extends javax.swing.JFrame {

    private final JugadorEnPartida ganador;

    public FrmAvisoJuego(JugadorEnPartida ganador, float pozo) {
        this.ganador = ganador;
        initComponents();
        configureTable();
        setTitle("Resultado del Juego");
        getContentPane().setBackground(new Color(245, 245, 245));
        setLocationRelativeTo(null);
        if (ganador != null) {
            jLTitulo.setText("\u00a1Tenemos un ganador!");
            jLMensaje.setText("El jugador " + ganador.getJugador().getNombre() + " ha ganado el pozo de $" + pozo);
            loadWinnerCards(ganador);
        }
    }

    private void configureTable() {
        jTable2.setRowHeight(150);
        jTable2.setDefaultRenderer(Object.class, new CardCellRenderer());
        jTable2.setDefaultEditor(Object.class, null);
        jTable2.setFocusable(false);
        jTable2.setRowSelectionAllowed(false);
        jTable2.setColumnSelectionAllowed(false);
        jTable2.setCellSelectionEnabled(false);
        jTable2.getTableHeader().setUI(null);
    }

    private void loadWinnerCards(JugadorEnPartida ganador) {
        if (ganador.getCartasEnMano() != null && !ganador.getCartasEnMano().isEmpty()) {
            ArrayList<Carta> cartas = ganador.getCartasEnMano().get(0).getCartasEnMano();

            DefaultTableModel model = new DefaultTableModel(1, cartas.size());
            jTable2.setModel(model);

            for (int i = 0; i < cartas.size(); i++) {
                jTable2.setValueAt(cartas.get(i), 0, i);
            }

            jTable2.revalidate();
            jTable2.repaint();
        } else {
            System.out.println("El jugador no tiene cartas.");
        }
    }

    private class CardCellRenderer extends JLabel implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof Carta) {
                Carta carta = (Carta) value;
                String projectPath = System.getProperty("user.dir");
                String imagePath = projectPath + "/src/main/java/tablascartas/cartas/" + carta.getTipo() + "_" + carta.getNumero() + "s.jpg";

                try {
                    File imageFile = new File(imagePath);
                    if (imageFile.exists()) {
                        ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
                        Image scaledImage = icon.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
                        setIcon(new ImageIcon(scaledImage));
                        setText("");
                    } else {
                        setText("Sin imagen");
                        setIcon(null);
                    }

                    setHorizontalAlignment(CENTER);
                    setVerticalAlignment(CENTER);
                    setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                    return this;
                } catch (Exception e) {
                    System.out.println("Error cargando la imagen: " + e.getMessage());
                }
            }
            setText("Sin imagen");
            setIcon(null);
            return this;
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        jLTitulo = new JLabel();
        jLMensaje = new JLabel();
        jPanel1 = new JPanel();
        jScrollPane2 = new JScrollPane();
        jTable2 = new JTable();
        btnOK = new JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        jLTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        jLMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        jLMensaje.setHorizontalAlignment(SwingConstants.CENTER);

        jTable2.setModel(new DefaultTableModel(
                new Object[][]{},
                new String[]{}
        ));
        jScrollPane2.setViewportView(jTable2);

        btnOK.setText("Cerrar");
        btnOK.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnOK.setBackground(new Color(60, 179, 113));
        btnOK.setForeground(Color.WHITE);
        btnOK.addActionListener(evt -> dispose());

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnOK, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addGap(210, 210, 210))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnOK, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLTitulo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLMensaje, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLTitulo, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLMensaje, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }

    public static void main(String args[]) {
        JugadorEnPartida ganador = new JugadorEnPartida();
        float pozo = 100.0f;

        EventQueue.invokeLater(() -> new FrmAvisoJuego(ganador, pozo).setVisible(true));
    }

    private JButton btnOK;
    private JLabel jLTitulo;
    private JLabel jLMensaje;
    private JPanel jPanel1;
    private JScrollPane jScrollPane2;
    private JTable jTable2;
}

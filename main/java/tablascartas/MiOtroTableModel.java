/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tablascartas;

import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Baldassini, Jorge / CTC - ORT
 */
public class MiOtroTableModel extends AbstractTableModel{
    String[]cartas;
    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount() {
        return cartas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return cartas[columnIndex];
    }
    
    public MiOtroTableModel(String cartas[])
    {
        this.cartas = cartas;
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package uy.ctc.edu.obligatoriodda.ClasesYEnums;

import java.util.ArrayList;

/**
 *
 * @author Matias
 */
public class ValidacionPorNumeracion implements ValidarLigas {

    @Override
    public boolean validar(ArrayList<Carta> cartas) {
        if (cartas == null || cartas.size() < 3) {
            return false; // Una liga debe tener al menos 3 cartas
        }
        return cartas.stream()
                     .allMatch(carta -> carta.getNumero() == cartas.get(0).getNumero());
    }

    @Override
    public boolean agregarLigas(ArrayList<Carta> cartas) {
        return validar(cartas); // En este caso, simplemente validamos
    }
}


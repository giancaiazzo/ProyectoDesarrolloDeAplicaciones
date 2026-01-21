package uy.ctc.edu.obligatoriodda.ClasesYEnums;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Matias
 */
import java.util.ArrayList;
import java.util.Collections;
import uy.ctc.edu.obligatoriodda.ClasesYEnums.Carta;
import uy.ctc.edu.obligatoriodda.ClasesYEnums.ValidarLigas;

public class ValidacionPorEscalera implements ValidarLigas {

    @Override
    public boolean validar(ArrayList<Carta> cartas) {
        if (cartas == null || cartas.size() < 3) {
            return false;
        }
        cartas.sort((c1, c2) -> Integer.compare(c1.getNumero(), c2.getNumero()));
        boolean mismoPalo = cartas.stream()
                                  .allMatch(carta -> carta.getTipo().equals(cartas.get(0).getTipo()));
        boolean escalera = true;
        for (int i = 0; i < cartas.size() - 1; i++) {
            if (cartas.get(i + 1).getNumero() != cartas.get(i).getNumero() + 1) {
                escalera = false;
                break;
            }
        }
        return mismoPalo && escalera;
    }

    @Override
    public boolean agregarLigas(ArrayList<Carta> cartas) {
        return validar(cartas);
    }
}

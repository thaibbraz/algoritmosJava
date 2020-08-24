package pt.ipleiria.estg.dei.aed.ordenacao.utilizacao;

import pt.ipleiria.estg.dei.aed.ComparacaoInteiros;
import pt.ipleiria.estg.dei.aed.ordenacao.algoritmos.BubbleSortOtimizado;

/**
 * @author Actual code:
 * Carlos Urbano<carlos.urbano@ipleiria.pt>
 * Catarina Reis<catarina.reis@ipleiria.pt>
 * Marco Ferreira<marco.ferreira@ipleiria.pt>
 * João Ramos<joao.f.ramos@ipleiria.pt>
 * Original code: José Magno<jose.magno@ipleiria.pt>
 */
public class MainTeoricaBubbleSortOtimizado {

    public MainTeoricaBubbleSortOtimizado() {
        BubbleSortOtimizado<Integer> bubbleSortOtimizado = new BubbleSortOtimizado<>(ComparacaoInteiros.CRITERIO);
        bubbleSortOtimizado.getEstatistica(3, 7, 2, 5, 4, 1, 6, 8, 9);
    }

    public static void main(String[] args) {
        new MainTeoricaBubbleSortOtimizado();
    }
}

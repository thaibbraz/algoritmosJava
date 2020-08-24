package pt.ipleiria.estg.dei.aed.examenormal;

import pt.ipleiria.estg.dei.aed.colecoes.naoiteraveis.estruturas.Fila;
import pt.ipleiria.estg.dei.aed.colecoes.naoiteraveis.estruturas.Pilha;

public class MainAvaliacaoTrocarPorOrdemDeInsercaoPilhaFila {

    private MainAvaliacaoTrocarPorOrdemDeInsercaoPilhaFila() {
        Pilha<Integer> pilha = new Pilha<>();
        pilha.inserir(1);
        pilha.inserir(2);
        pilha.inserir(3);

        Fila<Integer> fila = new Fila<>();
        fila.inserir(11);
        fila.inserir(12);
        fila.inserir(13);

        System.out.println(pilha);
        System.out.println(fila);

        trocarPorOrdemDeInsercao(pilha, fila);

        System.out.println(pilha);
        System.out.println(fila);
    }

    public static void main(String[] args) {
        new MainAvaliacaoTrocarPorOrdemDeInsercaoPilhaFila();
    }

    private <T> void trocarPorOrdemDeInsercao(Pilha<T> pilha, Fila<T> fila) {
        throw new RuntimeException("Por implementar.");
    }
}

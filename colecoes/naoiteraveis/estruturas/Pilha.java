package pt.ipleiria.estg.dei.aed.colecoes.naoiteraveis.estruturas;

import pt.ipleiria.estg.dei.aed.colecoes.naoiteraveis.ColecaoNaoIteravel;

import java.io.Serializable;

/**
 * @author Actual code:
 * Carlos Urbano<carlos.urbano@ipleiria.pt>
 * Catarina Reis<catarina.reis@ipleiria.pt>
 * Marco Ferreira<marco.ferreira@ipleiria.pt>
 * João Ramos<joao.f.ramos@ipleiria.pt>
 * Original code: José Magno<jose.magno@ipleiria.pt>
 */
public class Pilha<T> implements ColecaoNaoIteravel<T> {
    private static final long serialVersionUID = 1L;

    private No noTopo;

    public Pilha() {
        noTopo = null;
    }

    @Override
    public void inserir(T elem) {
        noTopo = new No(elem);
    }

    @Override
    public T remover() {
        if (isVazia()) {
            return null;
        }

        T aux = noTopo.elemento;
        noTopo = noTopo.seguinte;
        return aux;
    }

    @Override
    public T consultar() {
        return isVazia() ? null : noTopo.elemento;
    }

    @Override
    public boolean isVazia() {
        return noTopo == null;
    }

    public void limpar() {
        noTopo = null;
    }

    /**
     * Lista todos os elementos da pilha desde o seu topo.
     */
    @Override
    public String toString() {
        StringBuilder lista = new StringBuilder();
        lista.append("Pilha = {\n");
        No aux = noTopo;
        while (aux != null) {
            lista.append(aux.elemento).append("\n");
            aux = aux.seguinte;
        }
        lista.append("}\n");
        return lista.toString();
    }

    class No implements Serializable {
        private static final long serialVersionUID = 1L;

        T elemento;
        No seguinte;

        // Criação de nó com elem antes o topo
        public No(T elem) {
            elemento = elem;
            seguinte = noTopo;
        }
    }

}

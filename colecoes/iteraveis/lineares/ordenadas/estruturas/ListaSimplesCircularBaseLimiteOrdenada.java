package pt.ipleiria.estg.dei.aed.colecoes.iteraveis.lineares.ordenadas.estruturas;

import pt.ipleiria.estg.dei.aed.ComparacaoLimite;
import pt.ipleiria.estg.dei.aed.colecoes.iteraveis.ColecaoIteravel;
import pt.ipleiria.estg.dei.aed.colecoes.iteraveis.IteradorIteravel;
import pt.ipleiria.estg.dei.aed.colecoes.iteraveis.lineares.ordenadas.ColecaoIteravelLinearOrdenada;

import java.io.Serializable;
import java.util.NoSuchElementException;

/**
 * @author Actual code:
 * Carlos Urbano<carlos.urbano@ipleiria.pt>
 * Catarina Reis<catarina.reis@ipleiria.pt>
 * Marco Ferreira<marco.ferreira@ipleiria.pt>
 * João Ramos<joao.f.ramos@ipleiria.pt>
 * Original code: José Magno<jose.magno@ipleiria.pt>
 */
public class ListaSimplesCircularBaseLimiteOrdenada<T> implements ColecaoIteravelLinearOrdenada<T> {
    private static final long serialVersionUID = 1L;

    protected No base;
    protected ComparacaoLimite<T> criterio;
    protected int numeroElementos;

    public ListaSimplesCircularBaseLimiteOrdenada(ComparacaoLimite<T> cpl) {
        criterio = cpl;
        base = new No();
        numeroElementos = 0;
    }

    public ListaSimplesCircularBaseLimiteOrdenada(ComparacaoLimite<T> cpl, ColecaoIteravelLinearOrdenada<T> colecao) {
        this(cpl);

        if (cpl.equals(colecao.getComparador())) {
            No noFinal = base;
            for (T elem : colecao) {
                noFinal = new No(elem, noFinal);
            }

            numeroElementos = colecao.getNumeroElementos();
        } else {
            inserirTodos(colecao);
        }
    }

    public ListaSimplesCircularBaseLimiteOrdenada(ComparacaoLimite<T> cpl, ColecaoIteravel<T> colecao) {
        this(cpl);

        inserirTodos(colecao);
    }

    protected No getNoAnterior(T elem) {
        if (!criterio.isElementoValido(elem)) {
            throw new NoSuchElementException();
        }

        No ant = base;

        while (criterio.comparar(ant.seguinte.elemento, elem) < 0) {
            ant = ant.seguinte;
        }

        return ant;
    }

    protected No getNoAnteriorPorReferencia(T elem) {
        No ant = getNoAnterior(elem);

        while (criterio.comparar(ant.seguinte.elemento, elem) == 0 &&
                ant.seguinte.elemento != elem) {
            ant = ant.seguinte;
        }

        return ant;
    }

    protected No getNoAnterior(int indice) {
        if (indice < 0 || indice >= numeroElementos) {
            throw new IndexOutOfBoundsException();
        }

        No ant = base;
        while (indice-- > 0) {
            ant = ant.seguinte;
        }

        return ant;
    }

    @Override
    public void inserir(T elem) {
        new No(elem, getNoAnterior(elem));
        numeroElementos++;
    }

    private void inserirTodos(ColecaoIteravel<T> colecao) {
        for (T elemento : colecao) {
            inserir(elemento);
        }
    }

    private No removerNoSeguinte(No ant) {
        No aux = ant.seguinte;
        ant.seguinte = ant.seguinte.seguinte;
        numeroElementos--;

        return aux;
    }

    @Override
    public T remover(T elem) {
        No ant = getNoAnterior(elem);

        while (criterio.comparar(ant.seguinte.elemento, elem) == 0) {
            if (ant.seguinte.elemento.equals(elem)) {
                return removerNoSeguinte(ant).elemento;
            }

            ant = ant.seguinte;
        }

        return null;
    }

    @Override
    public T removerPorReferencia(T elem) {
        No ant = getNoAnteriorPorReferencia(elem);

        return ant.seguinte.elemento == elem ? removerNoSeguinte(ant).elemento : null;
    }

    @Override
    public T remover(int indice) {
        return removerNoSeguinte(getNoAnterior(indice)).elemento;
    }

    @Override
    public T consultar(int indice) {
        return getNoAnterior(indice).seguinte.elemento;
    }

    @Override
    public IteradorIteravel<T> consultar(T elemInicial, T elemFinal) {
        return new Iterador(elemInicial, elemFinal);
    }

    @Override
    public boolean contem(T elem) {
        return criterio.comparar(getNoAnterior(elem).seguinte.elemento, elem) == 0;
    }

    @Override
    public boolean contemReferencia(T elem) {
        return getNoAnteriorPorReferencia(elem).seguinte.elemento == elem;
    }

    @Override
    public int getNumeroElementos() {
        return numeroElementos;
    }

    @Override
    public IteradorIteravel<T> iterador() {
        return new Iterador();
    }

    @Override
    public ComparacaoLimite<T> getComparador() {
        return criterio;
    }


    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Lista Simples Circular Base Limite Ordenada por ");
        s.append(criterio.getClass().getSimpleName()).append(" = {\n");
        No aux = base.seguinte;
        while (aux != base) {
            s.append(aux.elemento).append("\n");
            aux = aux.seguinte;
        }
        s.append("}\n");

        return s.toString();
    }


    protected class No implements Serializable {
        private static final long serialVersionUID = 1L;

        protected T elemento;
        protected No seguinte;

        // Criação do nó base com limite
        protected No() {
            elemento = criterio.getLimite();
            seguinte = this;
        }

        // Criação de nó com elemento elem e inserção após o nó ant
        protected No(T elem, No ant) {
            if (!criterio.isElementoValido(elem)) {
                throw new NoSuchElementException();
            }
            elemento = elem;
            seguinte = ant.seguinte;
            ant.seguinte = this;
        }
    }


    protected class Iterador implements IteradorIteravel<T> {
        protected No anteriorAoPrimeiro;
        protected No corrente;
        protected No seguinteAoUltimo;

        protected Iterador() {
            anteriorAoPrimeiro = seguinteAoUltimo = base;
            reiniciar();
        }

        protected Iterador(T elemInicial, T elemFinal) {
            if (criterio.comparar(elemInicial, elemFinal) > 0) {
                throw new NoSuchElementException();
            }

            anteriorAoPrimeiro = getNoAnterior(elemInicial);
            seguinteAoUltimo = anteriorAoPrimeiro.seguinte;

            while (criterio.comparar(seguinteAoUltimo.elemento, elemFinal) <= 0) {
                seguinteAoUltimo = seguinteAoUltimo.seguinte;
            }

            reiniciar();
        }

        protected Iterador(T elem) {
            this(elem, elem);
        }

        @Override
        public void reiniciar() {
            corrente = anteriorAoPrimeiro;
        }

        @Override
        public T corrente() {
            if (corrente == anteriorAoPrimeiro) {
                throw new NoSuchElementException();
            }
            return corrente.elemento;
        }

        @Override
        public boolean podeAvancar() {
            return corrente.seguinte != seguinteAoUltimo;
        }

        @Override
        public T avancar() {
            if (!podeAvancar()) {
                throw new NoSuchElementException();
            }

            corrente = corrente.seguinte;
            return corrente.elemento;
        }
    }
}

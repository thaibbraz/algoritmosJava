package pt.ipleiria.estg.dei.aed.colecoes.iteraveis.associativas.estruturas;

import pt.ipleiria.estg.dei.aed.colecoes.iteraveis.IteradorIteravel;
import pt.ipleiria.estg.dei.aed.colecoes.iteraveis.associativas.ColecaoIteravelAssociativa;

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
public abstract class TabelaHashPorSondagem<C, V> implements ColecaoIteravelAssociativa<C, V> {

    private static final long serialVersionUID = 1L;

    protected Entrada<C, V>[] tabela;
    protected int incremento;
    protected int numeroElementos;
    protected int numeroElementosInativos;

    public TabelaHashPorSondagem(int tamanho) {
        tabela = new Entrada[proximoPrimo(tamanho)];
        numeroElementos = numeroElementosInativos = 0;
        incremento = 0;
    }

    public static int proximoPrimo(int n) {
        if (n < 2) {
            return 2;
        }
        if (n % 2 == 0) {
            ++n;
        }

        do {
            for (int i = 3; n % i != 0; i += 2) {
                if (i * i > n) {
                    return n;
                }
            }

            n += 2;
        } while (true);
    }

    protected abstract void iniciarIncremento(C chave);

    protected abstract void calcularProximoIncremento();

    private int getProximoIndice(int i, int inicial) {
        int indice = (i + incremento) % tabela.length;
        calcularProximoIncremento();
        if (indice == inicial) {
            throw new RuntimeException(
                    "Sondagem circular de toda a tabela");
        }
        return indice;
    }

    // Devolve o índice do primeiro null  ou  índice da chave  ou  o índice do primeiro inativo
    protected int getIndiceTabela(C chave) {
        int i = Math.abs(chave.hashCode()) % tabela.length, inativo, inicial = i;
        iniciarIncremento(chave);

        do {
            if (tabela[i] == null) {
                return i; // Devolve o índice do primeiro null
            }

            if (tabela[i].getChave().equals(chave)) {
                return i; // Devolve índice da chave
            }

            if (!tabela[i].isAtivo()) {
                inativo = i; // Encontrou o primeiro inativo no índice i
                break;
            }

            i = getProximoIndice(i, inicial);
        } while (true);

        do {
            i = getProximoIndice(i, inicial);

            if (tabela[i] == null) {
                return inativo; // Devolve o índice do primeiro inativo
            }

            if (tabela[i].getChave().equals(chave)) {
                return tabela[i].isAtivo() ? i : inativo; // Devolve o índice da chave
            }
        } while (true);
    }

    @Override
    public void inserir(C chave, V valor) {
        int i = getIndiceTabela(chave);
        if (tabela[i] != null) {
            if (tabela[i].isAtivo()) {
                throw new IllegalArgumentException("Chave " + chave + " duplicada");
            }
            numeroElementosInativos--;
        }
        tabela[i] = new Entrada(chave, valor);
        numeroElementos++;
        if (fatorCarga() >= 0.5) {
            reHash();
        }
    }

    private float fatorCarga() {
        return (numeroElementos + numeroElementosInativos) / (float) tabela.length;
    }

    private void reHash() {
        int tam = proximoPrimo(tabela.length * 2);
        Entrada<C, V>[] tabelaAntiga = tabela;
        tabela = new Entrada[tam];
        numeroElementos = numeroElementosInativos = 0;
        for (int i = 0; i < tabelaAntiga.length; i++) {
            if (tabelaAntiga[i] != null && tabelaAntiga[i].isAtivo()) {
                inserir(tabelaAntiga[i].getChave(), tabelaAntiga[i].getValor());
            }
        }
    }

    @Override
    public V remover(C chave) {
        int i = getIndiceTabela(chave);
        if (tabela[i] != null && tabela[i].isAtivo()) {
            tabela[i].desativar();
            numeroElementosInativos++;
            numeroElementos--;
            return tabela[i].getValor();
        }
        return null;
    }

    public void removerTodos() {
        for (int i = 0; i < tabela.length; i++) {
            tabela[i] = null;
        }
        numeroElementos = numeroElementosInativos = 0;
    }

    @Override
    public V consultar(C chave) {
        int i = getIndiceTabela(chave);
        return tabela[i] != null && tabela[i].isAtivo() ? tabela[i].getValor() : null;
    }

    @Override
    public int getNumeroElementos() {
        return numeroElementos;
    }

    @Override
    public boolean contem(C chave) {
        return consultar(chave) != null;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("Tabela de tamanho ");
        s.append(tabela.length);
        s.append(" = {\n");
        for (int i = 0; i < tabela.length; i++) {
            if (tabela[i] != null) {
                s.append("Tabela[" + i + "]==> ");
                s.append(tabela[i]);
                s.append("\n");
            }
        }
        s.append("}\n");
        return s.toString();
    }

    @Override
    public IteradorIteravel<Associacao<C, V>> iterador() {
        return new Iterador();
    }

    @Override
    public IteradorIteravel<C> iteradorChaves() {
        return new IteradorChaves();
    }

    @Override
    public IteradorIteravel<V> iteradorValores() {
        return new IteradorValores();
    }


    protected class Entrada<C, V> implements Serializable {

        private static final long serialVersionUID = 1L;

        protected Associacao<C, V> associacao;
        protected boolean ativo;

        public Entrada(C chave, V valor) {
            associacao = new Associacao<>(chave, valor);
            ativo = true;
        }

        public C getChave() {
            return associacao.getChave();
        }

        public V getValor() {
            return associacao.getValor();
        }

        public void setValor(V valor) {
            associacao.setValor(valor);
        }

        public Associacao<C, V> getAssociacao() {
            return associacao;
        }

        public boolean isAtivo() {
            return ativo;
        }

        public void desativar() {
            ativo = false;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(isAtivo() ? "" : "X ");
            sb.append(associacao);
            return sb.toString();
        }
    }


    protected class Iterador implements IteradorIteravel<Associacao<C, V>> {

        protected int corrente;
        protected int proximo;

        public Iterador() {
            reiniciar();
        }

        @Override
        public void reiniciar() {
            if (isVazia()) {
                corrente = proximo = tabela.length;
            } else {
                corrente = tabela.length;
                proximo = -1;
                proximo = proximoAtivo();
            }
        }

        @Override
        public Associacao<C, V> corrente() {
            if (corrente == tabela.length) {
                throw new NoSuchElementException();
            }
            return tabela[corrente].getAssociacao();
        }

        public int proximoAtivo() {
            if (proximo == tabela.length) {
                return proximo;
            }
            while (++proximo != tabela.length && (tabela[proximo] == null || !tabela[proximo].isAtivo())) {
                // Empty
            }
            return proximo;
        }

        @Override
        public boolean podeAvancar() {
            return proximo != tabela.length;
        }

        @Override
        public Associacao<C, V> avancar() {
            if (podeAvancar()) {
                corrente = proximo;
                proximo = proximoAtivo();
                return tabela[corrente].getAssociacao();
            }
            throw new NoSuchElementException();
        }
    }


    protected abstract class IteradorParcial<C_ou_V> implements IteradorIteravel<C_ou_V> {

        protected IteradorIteravel<Associacao<C, V>> iterador;

        public IteradorParcial() {
            iterador = iterador();
        }

        @Override
        public void reiniciar() {
            iterador.reiniciar();
        }

        @Override
        public boolean podeAvancar() {
            return iterador.podeAvancar();
        }

        public abstract C_ou_V getValor(Associacao<C, V> a);

        @Override
        public C_ou_V avancar() {
            return getValor(iterador.avancar());
        }

        @Override
        public C_ou_V corrente() {
            return getValor(iterador.corrente());
        }
    }


    protected class IteradorChaves extends IteradorParcial<C> {

        @Override
        public C getValor(Associacao<C, V> a) {
            return a.getChave();
        }
    }


    protected class IteradorValores extends IteradorParcial<V> {

        @Override
        public V getValor(Associacao<C, V> a) {
            return a.getValor();
        }
    }
}

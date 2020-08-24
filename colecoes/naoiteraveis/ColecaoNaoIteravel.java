package pt.ipleiria.estg.dei.aed.colecoes.naoiteraveis;

import pt.ipleiria.estg.dei.aed.colecoes.Colecao;

/**
 * @author Actual code:
 * Carlos Urbano<carlos.urbano@ipleiria.pt>
 * Catarina Reis<catarina.reis@ipleiria.pt>
 * Marco Ferreira<marco.ferreira@ipleiria.pt>
 * João Ramos<joao.f.ramos@ipleiria.pt>
 * Original code: José Magno<jose.magno@ipleiria.pt>
 */
public interface ColecaoNaoIteravel<T> extends Colecao<T> {
    void inserir(T elem);

    T remover();

    T consultar();

    boolean isVazia();
}

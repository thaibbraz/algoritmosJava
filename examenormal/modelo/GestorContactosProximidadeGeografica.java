package pt.ipleiria.estg.dei.aed.examenormal.modelo;

import pt.ipleiria.estg.dei.aed.colecoes.iteraveis.IteradorIteravel;
import pt.ipleiria.estg.dei.aed.modelo.contactos.Contacto;
import pt.ipleiria.estg.dei.aed.modelo.contactos.CoordenadaGeografica;

public enum GestorContactosProximidadeGeografica {
    INSTANCIA;

    public static final CoordenadaGeografica COORDENADA_GEOGRAFICA_LEIRIA = new CoordenadaGeografica(39.735082, -8.820678);
    public static final CoordenadaGeografica COORDENADA_GEOGRAFICA_ANTIPODA_LEIRIA = new CoordenadaGeografica(-39.735082, 171.179322);

    GestorContactosProximidadeGeografica() {
    }

    public void inserir(Contacto contacto) {
        throw new RuntimeException("Por implementar.");
    }

    public IteradorIteravel<Contacto> getContactos() {
        throw new RuntimeException("Por implementar.");
    }

    public IteradorIteravel<Contacto> consultar(double distancia) {
        throw new RuntimeException("Por implementar.");
    }

    public IteradorIteravel<Contacto> consultar(double distancia, int anoNascimento) {
        throw new RuntimeException("Por implementar.");
    }

}

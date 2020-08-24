package pt.ipleiria.estg.dei.aed.examenormal;

public class MainAvaliacaoContagemUns {

    public MainAvaliacaoContagemUns() {
        int[][] matriz = new int[][]{
                {1, 1, 1, 1, 1, 1},
                {1, 1, 1, 0, 0, 0},
                {1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0}
        };

        System.out.println("Número de uns na matriz: " + getNumeroUns(matriz));
    }

    public static void main(String[] args) {
        new MainAvaliacaoContagemUns();
    }

    private int getNumeroUns(int[][] matriz) {
        throw new RuntimeException("Por implementar.");
    }
}









package pt.ipleiria.estg.dei.aed.modelo.contactos;

import static java.lang.Math.toRadians;

public class CoordenadaGeografica {
    private double latitude;
    private double longitude;

    public CoordenadaGeografica(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getDistancia(CoordenadaGeografica coordenadaGeografica) {
        if (coordenadaGeografica == null) {
            return Double.MAX_VALUE;
        }

        double R = 6371e3; // metres
        double theta1 = toRadians(latitude);
        double theta2 = toRadians(coordenadaGeografica.latitude);

        double delta1 = toRadians(coordenadaGeografica.latitude - latitude);
        double delta2 = toRadians(coordenadaGeografica.longitude - longitude);

        double a = Math.sin(delta1 / 2) * Math.sin(delta1 / 2) +
                Math.cos(theta1) * Math.cos(theta2) *
                        Math.sin(delta2 / 2) * Math.sin(delta2 / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c / 1000;
    }

    @Override
    public String toString() {
        return "(" + latitude + ", " + longitude + ")";
    }

}

package data;

public class Pyramid extends Shape {

    public static final String SHAPE_NAME = "Pyramid";

    private double s;
    private double h;

    private Pyramid() { }

    public Pyramid(double s, double h) {
        this.s = s;
        this.h = h;
    }

    @Override
    public double getVolume() {
        return (1. / 3.) *  s * h;
    }

    @Override
    public String getShapeName() {
        return Pyramid.SHAPE_NAME;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", SHAPE_NAME, getVolume());
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }
}

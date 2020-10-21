package data;

public class Sphere extends Shape {

    public static final String SHAPE_NAME = "Sphere";

    private double r;

    private Sphere() { }

    public Sphere(double r) {
        this.r = r;
    }

    @Override
    public double getVolume() {
        return 4. * Math.PI * r * r * r / 3. ;
    }

    @Override
    public String getShapeName() {
        return Sphere.SHAPE_NAME;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", SHAPE_NAME, getVolume());
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }
}

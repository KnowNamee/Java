package data;

public class Parallelepiped extends Shape {

    public static final String SHAPE_NAME = "Parallelepiped";

    private double lenX;
    private double lenY;
    private double lenZ;

    private Parallelepiped() { }

    public Parallelepiped(double lenX, double lenY, double lenZ) {
        this.lenX = lenX;
        this.lenY = lenY;
        this.lenZ = lenZ;
    }

    @Override
    public double getVolume() {
        return Math.abs(lenX * lenY * lenZ);
    }

    @Override
    public String getShapeName() {
        return Parallelepiped.SHAPE_NAME;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", SHAPE_NAME, getVolume());
    }

    public double getLenX() {
        return lenX;
    }

    public void setLenX(double lenX) {
        this.lenX = lenX;
    }

    public double getLenY() {
        return lenY;
    }

    public void setLenY(double lenY) {
        this.lenY = lenY;
    }

    public double getLenZ() {
        return lenZ;
    }

    public void setLenZ(double lenZ) {
        this.lenZ = lenZ;
    }
}

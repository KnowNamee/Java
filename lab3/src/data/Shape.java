package data;

public abstract class Shape implements Comparable<Shape> {

    public Shape() {}

    public abstract double getVolume();

    public abstract String getShapeName();

    @Override
    public int compareTo(Shape o) {
        return Double.compare(this.getVolume(), o.getVolume());
    }
}

package containers;

import data.Shape;
import exceptions.MyException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Backpack<T extends Shape> {

    public static final double DEFAULT_CAPACITY = 100000.;
    public static final String MSG_CANT_PUT_SHAPE = "Not enough space, can't push shape :(";
    public static final String MSG_EMPTY_SHAPE = "Empty shape. Can't put shape.";

    private ArrayList<T> shapes = null;
    private double capacity = 0.;
    private double volume = 0.;

    private Backpack() {}

    public Backpack(double capacity) {
        this.shapes = new ArrayList<>();
        this.capacity = capacity;
    }

    public void sort() {
        shapes.sort(Collections.reverseOrder(Shape::compareTo));
    }

    public String[][] getData() {
        String[][] data = new String[shapes.size()][2];
        for (int i = 0; i < shapes.size(); i++) {
            T shape = shapes.get(i);
            data[i][0] = shape.getShapeName();
            data[i][1] = String.valueOf(shape.getVolume());
        }
        return data;
    }

    public void addShape(T s) throws Exception {
        if (s.getVolume() == 0) {
            throw new MyException(MSG_EMPTY_SHAPE, new Throwable());
        }
        if (volume + s.getVolume() > capacity) {
            throw new MyException(MSG_CANT_PUT_SHAPE, new Throwable());
        } else {
            volume += s.getVolume();
            shapes.add(s);
        }
        sort();
    }

    public void removeShape(int shapeIndex) {
        shapes.remove(shapeIndex);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        int i = 1;
        for (T shape : shapes) {
            sb.append(String.format("%2d %s\n", i, shape.toString()));
        }
        return sb.toString();
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public ArrayList<T> getShapes() {
        return shapes;
    }

    public void setShapes(ArrayList<T> shapes) {
        this.shapes = shapes;
    }
}

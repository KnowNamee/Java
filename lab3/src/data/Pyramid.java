package data;

public class Pyramid extends Shape {

    public static final String SHAPE_NAME = "Pyramid";

    public static final int PARAMS_COUNT = 2;
    public static final int S_PARAM_IDX = 0;  // Square
    public static final int H_PARAM_IDX = 1;  // Height

    private Double[] paramValues = new Double[PARAMS_COUNT];
    public static final String[] PARAM_NAMES = new String[] {"square", "length"};

    private Pyramid() { }

    public Pyramid(double s, double h) {
        paramValues[S_PARAM_IDX] = s;
        paramValues[H_PARAM_IDX] = h;
    }

    public Pyramid(Double[] paramValues) {
        this.paramValues = paramValues;
    }

    @Override
    public double getVolume() {
        return (1. / 3.) * paramValues[S_PARAM_IDX] *
                           paramValues[H_PARAM_IDX];
    }

    @Override
    public String getShapeName() {
        return Pyramid.SHAPE_NAME;
    }

    public Double[] getParamValues() {
        return paramValues;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", SHAPE_NAME, getVolume());
    }

    public double getS() {
        return paramValues[S_PARAM_IDX];
    }

    public void setS(double s) {
        paramValues[S_PARAM_IDX] = s;
    }

    public double getH() {
        return paramValues[H_PARAM_IDX];
    }

    public void setH(double h) {
        paramValues[H_PARAM_IDX] = h;
    }
}

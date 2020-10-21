package data;

public class Parallelepiped extends Shape {

    public static final String SHAPE_NAME = "Parallelepiped";

    public static final int PARAMS_COUNT = 3;
    public static final int H_PARAM_IDX = 0;  // Height
    public static final int W_PARAM_IDX = 1;  // Width
    public static final int L_PARAM_IDX = 2;  // Length

    private Double[] paramValues = new Double[PARAMS_COUNT];
    public static final String[] PARAM_NAMES = new String[] {"height", "width", "length"};

    private Parallelepiped() { }

    public Parallelepiped(double h, double w, double l) {
        paramValues[H_PARAM_IDX] = h;
        paramValues[W_PARAM_IDX] = w;
        paramValues[L_PARAM_IDX] = l;
    }

    public Double[] getParamValues() {
        return paramValues;
    }

    public Parallelepiped(Double[] paramValues) {
        this.paramValues = paramValues;
    }

    @Override
    public double getVolume() {
        return Math.abs(
                paramValues[H_PARAM_IDX] *
                paramValues[W_PARAM_IDX] *
                paramValues[L_PARAM_IDX]);
    }

    @Override
    public String getShapeName() {
        return Parallelepiped.SHAPE_NAME;
    }

    @Override
    public String toString() {
        return String.format("%s %.2f", SHAPE_NAME, getVolume());
    }

    public double getH() {
        return paramValues[H_PARAM_IDX];
    }

    public void setH(double h) {
        paramValues[H_PARAM_IDX] = h;
    }

    public double getW() {
        return paramValues[W_PARAM_IDX];
    }

    public void setW(double w) {
        paramValues[W_PARAM_IDX] = w;
    }

    public double getL() {
        return paramValues[L_PARAM_IDX];
    }

    public void setL(double w) {
        paramValues[L_PARAM_IDX] = w;
    }

}

package data;

public class Sphere extends Shape {

    public static final String SHAPE_NAME = "Sphere";

    public static final int PARAMS_COUNT = 1;
    public static final int R_PARAM_IDX = 0;  // Radius

    private Double[] paramValues = new Double[PARAMS_COUNT];
    public static final String[] PARAM_NAMES = new String[] {"radius"};

    private Sphere() { }

    public Sphere(double r) {
        paramValues[R_PARAM_IDX] = r;
    }

    public Sphere(Double[] paramValues) {
        this.paramValues = paramValues;
    }

    public Double[] getParamValues() {
        return paramValues;
    }

    @Override
    public double getVolume() {
        double r = paramValues[R_PARAM_IDX];
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
        return paramValues[R_PARAM_IDX];
    }

    public void setR(double r) {
        this.paramValues[R_PARAM_IDX] = r;
    }
}

package basic_linalg;

public class Matrix2x2 {

    private double a00;

    private double a01;

    private double a10;

    private double a11;

    public Matrix2x2(double inputA00, double inputA01, double inputA10, double inputA11) {
        this.a00 = inputA00;
        this.a01 = inputA01;
        this.a10 = inputA10;
        this.a11 = inputA11;


    }

    public Vector2D multiply(Vector2D vector) {
        double x = a00 * vector.getX() + a01 * vector.getY();
        double y = a10 * vector.getX() + a11 * vector.getY();
        return new Vector2D(x, y);
    }


}

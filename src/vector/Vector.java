package vector;

import java.text.DecimalFormat;

/**
 *
 * @author ariful
 */
public class Vector {

    public double x, y, z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector scale(double s, Vector v) {

        Vector vector = new Vector(0, 0, 0);

        vector.x = v.x * s;
        vector.y = v.y * s;
        vector.z = v.z * s;

        return vector;
    }

    public static Vector getVector(String vectorString) {
        Vector vector = new Vector(0, 0, 0);

        vector.x = Double.parseDouble(vectorString.split(" ")[0]);
        vector.y = Double.parseDouble(vectorString.split(" ")[1]);
        vector.z = Double.parseDouble(vectorString.split(" ")[2]);

        return vector;
    }

    public Vector normalize() {
        double normal;
        Vector v = new Vector(0, 0, 0);

        normal = Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);

        v.x = this.x / normal;
        v.y = this.y / normal;
        v.z = this.z / normal;

        return v;

    }

    public static double dotproduct(Vector v1, Vector v2) {
        double dot;

        dot = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
        return dot;
    }

    public static Vector crossproduct(Vector v1, Vector v2) {
        Vector resVector = new Vector(0, 0, 0);

        resVector.x = v1.y * v2.z - v1.z * v2.y;
        resVector.y = -(v1.x * v2.z - v1.z * v2.x);
        resVector.z = v1.x * v2.y - v1.y * v2.x;

        return resVector;
    }

    public static Vector addVector(Vector v1, Vector v2) {
        Vector v = new Vector(0, 0, 0);

        v.x = v1.x + v2.x;
        v.y = v1.y + v2.y;
        v.z = v1.z + v2.z;

        return v;
    }

    public static Vector subVector(Vector v1, Vector v2) {
        Vector resVector = new Vector(0, 0, 0);

        resVector.x = v1.x - v2.x;
        resVector.y = v1.y - v2.y;
        resVector.z = v1.z - v2.z;

        return resVector;
    }

    public static Vector getRodgious(Vector x, Vector a, double angle) {
        double radian = Math.toRadians(angle);

        Vector rodVector;

        Vector v1, v2, v3;

        Vector cross = crossproduct(a, x);
        double dot = dotproduct(a, x);

        DecimalFormat df = new DecimalFormat("#.##########");

        double cosTheta = Double.parseDouble(df.format(Math.cos(radian)));
        double sinTheta = Double.parseDouble(df.format(Math.sin(radian)));

        v1 = scale(cosTheta, x);
        v2 = scale((1 - cosTheta) * dot, a);
        v3 = scale(sinTheta, cross);

        rodVector = addVector(v1, addVector(v2, v3));

        return rodVector;
    }

}

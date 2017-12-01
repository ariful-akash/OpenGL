package Point;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author ariful
 */
public class Point {

    public double x, y, z, w;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1;

    }

    public void scalePoint() {
        this.x = x / w;
        this.y = y / w;
        this.z = z / w;
        this.w = 1;
    }

    public static Point getPoint(String p) {
        double x, y, z;
        x = Double.parseDouble(p.split(" ")[0]);
        y = Double.parseDouble(p.split(" ")[1]);
        z = Double.parseDouble(p.split(" ")[2]);
        Point point = new Point(x, y, z);

        return point;
    }

    @Override
    public String toString() {

        NumberFormat formatter = new DecimalFormat("#0.0000000");

        return "" + formatter.format(this.x) + " " + formatter.format(this.y) + " " + formatter.format(this.z);
    }
}

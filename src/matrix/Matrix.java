package matrix;

import Point.Point;
import stack.Stack;
import vector.Vector;

/**
 *
 * @author ariful
 */
public class Matrix {

    public double[][] matrix = new double[4][4];

    public Matrix() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matrix[i][j] = 0;
            }
        }

        matrix[3][3] = 1;
    }

    public Matrix matrixMultiplication(Matrix mat) {
        Matrix resMatrix = new Matrix();
        double sum;
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 4; j++) {

                sum = 0;
                for (int k = 0; k < 4; k++) {

                    sum += this.matrix[i][k] * mat.matrix[k][j];
                }

                resMatrix.matrix[i][j] = sum;
            }
        }
        return resMatrix;
    }

    public Point pointMatrixMultiplication(Point p) {
        Point product = new Point(0, 0, 0);
        product.x
                = this.matrix[0][0] * p.x
                + this.matrix[0][1] * p.y
                + this.matrix[0][2] * p.z
                + this.matrix[0][3] * p.w;
        product.y
                = this.matrix[1][0] * p.x
                + this.matrix[1][1] * p.y
                + this.matrix[1][2] * p.z
                + this.matrix[1][3] * p.w;
        product.z
                = this.matrix[2][0] * p.x
                + this.matrix[2][1] * p.y
                + this.matrix[2][2] * p.z
                + this.matrix[2][3] * p.w;
        product.w
                = this.matrix[3][0] * p.x
                + this.matrix[3][1] * p.y
                + this.matrix[3][2] * p.z
                + this.matrix[3][3] * p.w;

        return product;
    }

    public static Matrix getIdentityMatrix() {
        Matrix iMatrix = new Matrix();

        iMatrix.matrix[0][0] = 1;
        iMatrix.matrix[1][1] = 1;
        iMatrix.matrix[2][2] = 1;
        return iMatrix;
    }

    public static Matrix getTranslationMatrix(Point point) {
        Matrix tMatrix = Matrix.getIdentityMatrix();

        tMatrix.matrix[0][3] = point.x;
        tMatrix.matrix[1][3] = point.y;
        tMatrix.matrix[2][3] = point.z;
        return tMatrix;

    }

    public static Matrix getTranslationMatrix(Vector point) {
        Matrix tMatrix = Matrix.getIdentityMatrix();

        tMatrix.matrix[0][3] = point.x;
        tMatrix.matrix[1][3] = point.y;
        tMatrix.matrix[2][3] = point.z;
        return tMatrix;

    }

    public static Matrix getScalingMatrix(Point point) {
        Matrix sMatrix = new Matrix();

        sMatrix.matrix[0][0] = point.x;
        sMatrix.matrix[1][1] = point.y;
        sMatrix.matrix[2][2] = point.z;
        return sMatrix;

    }

    public static Matrix getRotationMatrix(double angle, double ax, double ay, double az) {
        Matrix trMatrix = new Matrix();

        Vector v = new Vector(ax, ay, az);
        Vector unitVector = new Vector(0, 0, 0);

        Vector c1, c2, c3;

        v = v.normalize();

        unitVector.x = 1;
        c1 = Vector.getRodgious(unitVector, v, angle);
        unitVector.x = 0;

        unitVector.y = 1;
        c2 = Vector.getRodgious(unitVector, v, angle);
        unitVector.y = 0;

        unitVector.z = 1;
        c3 = Vector.getRodgious(unitVector, v, angle);
        unitVector.z = 0;

        trMatrix.matrix[0][0] = c1.x;
        trMatrix.matrix[0][1] = c2.x;
        trMatrix.matrix[0][2] = c3.x;

        trMatrix.matrix[1][0] = c1.y;
        trMatrix.matrix[1][1] = c2.y;
        trMatrix.matrix[1][2] = c3.y;

        trMatrix.matrix[2][0] = c1.z;
        trMatrix.matrix[2][1] = c2.z;
        trMatrix.matrix[2][2] = c3.z;

        return trMatrix;
    }

    public static Matrix viewTransformMatrix(Vector eye, Vector look, Vector up) {

        Matrix trMatrix = new Matrix();

        Vector l = Vector.subVector(look, eye);
        l = l.normalize();

        Vector r = Vector.crossproduct(l, up);
        r = r.normalize();

        Vector u = Vector.crossproduct(r, l);
        u = u.normalize();

        Stack stack = new Stack();
        stack.push(Matrix.getTranslationMatrix(Vector.scale(-1, eye)));

        Matrix lruMatrix = new Matrix();

        lruMatrix.matrix[0][0] = r.x;
        lruMatrix.matrix[0][1] = r.y;
        lruMatrix.matrix[0][2] = r.z;

        lruMatrix.matrix[1][0] = u.x;
        lruMatrix.matrix[1][1] = u.y;
        lruMatrix.matrix[1][2] = u.z;

        lruMatrix.matrix[2][0] = -(l.x);
        lruMatrix.matrix[2][1] = -(l.y);
        lruMatrix.matrix[2][2] = -(l.z);

        trMatrix = lruMatrix.matrixMultiplication(stack.top());

        return trMatrix;

    }

    public static Matrix getProjectionMatrix(String projection) {

        Matrix trMatrix = new Matrix();
        double fovX, fovY, aspectRatio, near, far, r, t;

        fovY = Double.parseDouble(projection.split(" ")[0]);
        aspectRatio = Double.parseDouble(projection.split(" ")[1]);
        near = Double.parseDouble(projection.split(" ")[2]);
        far = Double.parseDouble(projection.split(" ")[3]);

        fovX = fovY * aspectRatio;
        t = near * Math.tan(Math.toRadians(fovY / 2));
        r = near * Math.tan(Math.toRadians(fovX / 2));

        //placing the matrix values
        trMatrix.matrix[3][3] = 0.0;

        trMatrix.matrix[0][0] = near / r;
        trMatrix.matrix[1][1] = near / t;
        trMatrix.matrix[2][2] = -(far + near) / (far - near);
        trMatrix.matrix[2][3] = -(2 * far * near) / (far - near);
        trMatrix.matrix[3][2] = -1.0;

        return trMatrix;
    }

}

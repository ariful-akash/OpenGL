
import Point.Point;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import matrix.Matrix;
import stack.Stack;
import vector.Vector;

/**
 *
 * @author ariful
 */
public class Main {

    public static void main(String[] args) {

        // The name of the file to open.
        String path = "/Users/ariful/Movies/Development/openGL_Assignment/Assignment 2/test/test cases/2/";

        String scene = path + "scene.txt";
        String stage1 = path + "stage1.txt";

        String[] lines = new String[4];
        Point inputPoints;
        Point outputPoints;

        Stack stack = new Stack();

        FileReader sceneFileReader = null;
        FileWriter stage1FileWriter = null;

        BufferedReader sceneBufferedReader = null;
        BufferedWriter stage1BufferedWriter = null;

        stack.push(Matrix.getIdentityMatrix());

        // This will reference one line at a time
        String l = null;

        //stage 1 Modeling Transformation
        try {
            // FileReader reads text files in the default encoding.
            sceneFileReader = new FileReader(scene);
            stage1FileWriter = new FileWriter(stage1);

            // Always wrap FileReader in BufferedReader.
            sceneBufferedReader = new BufferedReader(sceneFileReader);
            stage1BufferedWriter = new BufferedWriter(stage1FileWriter);

            lines[0] = sceneBufferedReader.readLine();
            lines[1] = sceneBufferedReader.readLine();
            lines[2] = sceneBufferedReader.readLine();
            lines[3] = sceneBufferedReader.readLine();

            boolean stop = false;
            double angle;
            Matrix stackTop, trMatrix;
            while ((l = sceneBufferedReader.readLine()) != null && !stop) {

                switch (l) {

                    case "triangle":

                        for (int i = 0; i < 3; i++) {

                            l = sceneBufferedReader.readLine();
                            inputPoints = Point.getPoint(l);

                            stackTop = stack.top();
                            outputPoints = stackTop.pointMatrixMultiplication(inputPoints);
                            outputPoints.scalePoint();

                            stage1BufferedWriter.write(outputPoints.toString());
                            stage1BufferedWriter.newLine();
                        }

                        stage1BufferedWriter.newLine();

                        break;

                    case "translate":

                        l = sceneBufferedReader.readLine();
                        inputPoints = Point.getPoint(l);

                        trMatrix = Matrix.getTranslationMatrix(inputPoints);

                        stackTop = stack.top();
                        trMatrix = stackTop.matrixMultiplication(trMatrix);

                        stack.push(trMatrix);
                        break;

                    case "scale":

                        l = sceneBufferedReader.readLine();
                        inputPoints = Point.getPoint(l);

                        trMatrix = Matrix.getScalingMatrix(inputPoints);

                        stackTop = stack.top();
                        trMatrix = stackTop.matrixMultiplication(trMatrix);

                        stack.push(trMatrix);
                        break;

                    case "rotate":

                        l = sceneBufferedReader.readLine();

                        angle = Double.parseDouble(l.split(" ")[0]);

                        inputPoints = Point.getPoint(l.split(" ")[1] + " " + l.split(" ")[2] + " " + l.split(" ")[3]);

                        trMatrix = Matrix.getRotationMatrix(angle, inputPoints.x, inputPoints.y, inputPoints.z);

                        stackTop = stack.top();
                        trMatrix = stackTop.matrixMultiplication(trMatrix);

                        stack.push(trMatrix);
                        break;

                    case "push":
                        stack.pushPointer();
                        break;

                    case "pop":
                        stack.popPointer();
                        break;

                    case "end":
                        stop = true;
                        break;

                    case "fuck fuck fuck":
                        break;

                    default:
                        break;
                }
            }

            sceneBufferedReader.close();
            stage1BufferedWriter.close();
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } finally {

            try {

                if (sceneFileReader != null) {

                    sceneFileReader.close();
                }
                if (stage1FileWriter != null) {

                    stage1FileWriter.close();
                }

            } catch (IOException e) {
            }
        }

        //From Stage 1 to stage 2 (View Transformation)
        Vector eye = Vector.getVector(lines[0]);
        Vector look = Vector.getVector(lines[1]);
        Vector up = Vector.getVector(lines[2]);

        l = null;
        scene = path + "stage1.txt";
        stage1 = path + "stage2.txt";

        try {
            // FileReader reads text files in the default encoding.
            sceneFileReader = new FileReader(scene);
            stage1FileWriter = new FileWriter(stage1);

            // Always wrap FileReader in BufferedReader.
            sceneBufferedReader = new BufferedReader(sceneFileReader);
            stage1BufferedWriter = new BufferedWriter(stage1FileWriter);

            Matrix matrix = Matrix.viewTransformMatrix(eye, look, up);
            boolean stop = false;
            Point currentPoint, resultPoint;

            while ((l = sceneBufferedReader.readLine()) != null && !stop) {

                switch (l) {
                    case "":
                        stage1BufferedWriter.newLine();
                        break;
                    default:
                        currentPoint = Point.getPoint(l);
                        resultPoint = matrix.pointMatrixMultiplication(currentPoint);
                        stage1BufferedWriter.write(resultPoint.toString());
                        stage1BufferedWriter.newLine();
                        break;
                }
            }

            sceneBufferedReader.close();
            stage1BufferedWriter.close();
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } finally {

            try {

                if (sceneFileReader != null) {

                    sceneFileReader.close();
                }
                if (stage1FileWriter != null) {

                    stage1FileWriter.close();
                }

            } catch (IOException e) {
            }
        }

        //from Stage 2 to stage 3 (Projection Transformation)
        l = null;
        scene = path + "stage2.txt";
        stage1 = path + "stage3.txt";

        try {
            // FileReader reads text files in the default encoding.
            sceneFileReader = new FileReader(scene);
            stage1FileWriter = new FileWriter(stage1);

            // Always wrap FileReader in BufferedReader.
            sceneBufferedReader = new BufferedReader(sceneFileReader);
            stage1BufferedWriter = new BufferedWriter(stage1FileWriter);

            Matrix matrix = Matrix.getProjectionMatrix(lines[3]);
            boolean stop = false;
            Point currentPoint, resultPoint;

            while ((l = sceneBufferedReader.readLine()) != null && !stop) {

                switch (l) {
                    case "":
                        stage1BufferedWriter.newLine();
                        break;
                    default:
                        currentPoint = Point.getPoint(l);
                        resultPoint = matrix.pointMatrixMultiplication(currentPoint);
                        resultPoint.scalePoint();
                        stage1BufferedWriter.write(resultPoint.toString());
                        stage1BufferedWriter.newLine();
                        break;
                }
            }

            sceneBufferedReader.close();
            stage1BufferedWriter.close();
        } catch (FileNotFoundException ex) {

        } catch (IOException ex) {

        } finally {

            try {

                if (sceneFileReader != null) {

                    sceneFileReader.close();
                }
                if (stage1FileWriter != null) {

                    stage1FileWriter.close();
                }

            } catch (IOException e) {
            }
        }

    }
}

package stack;

import java.util.ArrayList;
import java.util.List;
import matrix.Matrix;

/**
 *
 * @author ariful
 */
public class Stack {

    private List<Matrix> matrixArray = new ArrayList<>();
    private List<Integer> pushArray = new ArrayList<>();

    private int stackpointer = 0, pushpointer = 0;

    public void push(Matrix m) {
        matrixArray.add(m);
        stackpointer++;
    }

    public Matrix top() {
        return matrixArray.get(stackpointer - 1);
    }

    public void pushPointer() {

        pushArray.add(stackpointer - 1);
        pushpointer++;
    }

    public void popPointer() {

        if (pushpointer > 0) {

            pushpointer--;
            int n = pushArray.remove(pushpointer);

            for (stackpointer--; stackpointer > n; stackpointer--) {

                matrixArray.remove(stackpointer);
            }

            stackpointer++;
        }
    }
}

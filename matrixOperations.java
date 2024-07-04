import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

/**
 * A class to made for performing various matrix operations.
 */
public class matrixOperations {
    private static final int[][] UNIT = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
    private static final int[] ZEROVECTOR = {0, 0, 0};
    private static final int[][] ZEROMATRIX = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

    /**
     * Computes the infinity norm of a 3x3 matrix.
     *
     * @param matrix the matrix to compute the norm for
     * @return the infinity norm of the matrix
     */
    public static int matrixInfinityNorm(int[][] matrix) {
        int max = 0;
        for (int i = 0; i < 3; i++) {
            int sum = 0;
            for (int j = 0; j < 3; j++) {
                sum += Math.abs(matrix[i][j]);
            }
            max = Math.max(max, sum);
        }
        return max;
    }

    /**
     * Computes the condition number of a 3x3 matrix.
     *
     * @param matrix the matrix to compute the condition number for
     * @return the condition number of the matrix
     */
    public static double condition(int[][] matrix) {
        int[][] matrixInv = inverseMatrix(matrix);
        int norm = matrixInfinityNorm(matrix);
        int normInv = matrixInfinityNorm(matrixInv);
        return norm * normInv;
    }

    /**
     * Swaps two rows of a matrix.
     *
     * @param matrix the matrix to swap rows in
     * @param i the index of the first row
     * @param j the index of the second row
     */
    public static void swapRow(int[][] matrix, int i, int j) {
        int[] temp = matrix[i];
        matrix[i] = matrix[j];
        matrix[j] = temp;
    }

    /**
     * Creates a copy of a 3x3 matrix.
     *
     * @param matrix the matrix to copy
     * @return a copy of the matrix
     */
    public static int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(matrix[i], 0, copy[i], 0, 3);
        }
        return copy;
    }

    /**
     * Computes the inverse of a 3x3 matrix.
     *
     * @param matrix the matrix to invert
     * @return the inverse of the matrix
     */
    public static int[][] inverseMatrix(int[][] matrix) {
        int[][] inverseMatrix = copyMatrix(UNIT);
        matrix = copyMatrix(matrix);

        for (int i = 0; i < 3; i++) {
            if (matrix[i][i] == 0) {
                boolean swapped = false;
                for (int k = i + 1; k < 3; k++) {
                    if (matrix[k][i] != 0) {
                        swapRow(matrix, i, k);
                        swapRow(inverseMatrix, i, k);
                        swapped = true;
                        break;
                    }
                }
                if (!swapped) {
                    throw new IllegalArgumentException("Matrix is singular and cannot be inverted.");
                }
            }

            int diagElement = matrix[i][i];
            for (int j = 0; j < 3; j++) {
                matrix[i][j] /= diagElement;
                inverseMatrix[i][j] /= diagElement;
            }

            for (int k = 0; k < 3; k++) {
                if (k != i) {
                    int factor = matrix[k][i];
                    for (int j = 0; j < 3; j++) {
                        matrix[k][j] -= factor * matrix[i][j];
                        inverseMatrix[k][j] -= factor * inverseMatrix[i][j];
                    }
                }
            }
        }
        return inverseMatrix;
    }

    /**
     * Prints a 3x1 vector.
     *
     * @param vector the vector to print
     */
    public static void printVector(int[] vector){
        System.out.print("{ ");
        for (int elem : vector) {
            System.out.print(elem + ", ");
        }
        System.out.println("}");
    }

    /**
     * Prints a 3x3 matrix.
     *
     * @param matrix the matrix to print
     */
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.print("{ ");
            for (int elem : row) {
                System.out.print(elem + ", ");
            }
            System.out.println("}");
        }
    }

    /**
     * Initializes a 3x1 vector from the user's input.
     *
     * @return the input vector
     */
    public static int[] InitializeVector() {
        Scanner scanner = new Scanner(System.in);
        int[] vector = new int[3];
        System.out.println("Enter the elements of the 3x1 vector:");
        for (int i = 0; i < 3; i++) {
            vector[i] = scanner.nextInt();
        }
        return vector;
    }

    /**
     * Initializes a 3x3 matrix from the user's input.
     *
     * @return the input matrix
     */
    public static int[][] InitializeMatrix() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Must enter a 3 by 3 matrix of integers only.");
        int[][] matrix = new int[3][3];
        for (int i = 0; i < 3; i++) {
            System.out.println("Enter the " + (i + 1) + " row.");
            for (int j = 0; j < 3; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
        return matrix;
    }

    /**
     * Multiplies two 3x3 matrices.
     *
     * @param mat1 the first matrix
     * @param mat2 the second matrix
     * @return the result of multiplying A and B
     */
    public static int[][] matricesMultiplication(int[][] mat1, int[][] mat2) {
        int[][] result = new int[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                for (int k = 0; k < 3; k++)
                    result[i][j] += mat1[i][k] * mat2[k][j];
        return result;
    }

    /**
     * Multiplies a 3x3 matrix by a 3x1 vector.
     *
     * @param mat the matrix
     * @param vec the vector
     */
    public static void matrix_VectorMultiplication(int[][] mat, int[] vec) {
        int[] result = new int[3];
        int[] result1 = new int[3];
        for (int i = 0; i < 3; i++)
            result1[i] = vectorMultiplication(mat[i], vec);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                result[i] += mat[i][j] * vec[j];
        if (Arrays.equals(result, result1)) {
            System.out.printf("The result is %s%n", Arrays.toString(result));
        }
    }

    /**
     * Computes the multiplication of two 3x1 vectors.
     *
     * @param vec1 the first vector
     * @param vec2 the second vector
     * @return the dot product of a and b
     */
    private static int vectorMultiplication(int[] vec1, int[] vec2) {
        int sum = 0;
        for (int i = 0; i < vec1.length; i++)
            sum += vec1[i] * vec2[i];
        return sum;
    }

    /**
     * Main method to run the matrix operations menu.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Map<String, int[][]> matrices = new HashMap<>();
        matrices.put("A", new int[][]{{1, -1, -2}, {2, -3, -5}, {-1, 3, 5}});
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n\nMenu for matrix analysis:");
            System.out.println("1] Enter a new matrix");
            System.out.println("2] Inversion of a matrix");
            System.out.println("3] Norm of a matrix");
            System.out.println("4] Norm of an inverse matrix");
            System.out.println("5] Calculate condition number of a matrix");
            System.out.println("6] Multiply two matrices");
            System.out.println("7] Multiply matrix and vector");
            System.out.println("8] Print certain matrix");
            System.out.println("9] Print all matrices");
            System.out.println("10] Exit");
            System.out.print("Choose an option: ");
            String name;

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter matrix name: ");
                    name = scanner.nextLine();
                    if (matrices.containsKey(name)) {
                        System.out.println("A matrix with that name already exists. Please enter a new name.");
                    }
                    else {
                        int[][] matrix = InitializeMatrix();
                        matrices.put(name, matrix);
                        System.out.println("Matrix entered successfully.");
                    }
                    break;


                case 2:
                    System.out.print("Enter matrix name: ");
                    name = scanner.nextLine();
                    if (matrices.containsKey(name)) {
                        int[][] matrixInv = inverseMatrix(matrices.get(name));
                        System.out.println("Matrix " + name + " inverse: ");
                        printMatrix(matrixInv);
                        System.out.print("Add inverse to matrices(Y/N): ");
                        if (scanner.nextLine().equalsIgnoreCase("Y")) {
                            while (true) {
                                System.out.print("Enter name for inverse matrix: ");
                                String invName = scanner.nextLine();
                                if (matrices.containsKey(invName)) {
                                    System.out.println("A matrix with that name already exists. Please enter a new name.");
                                }
                                else {
                                    matrices.put(invName, matrixInv);
                                    break;
                                }
                            }
                        }
                    }
                    else {
                        System.out.println("No matrix found. Please enter a matrix first.");
                    }
                    break;

                case 3:
                    System.out.print("Enter matrix name: ");
                    name = scanner.nextLine();
                    if (matrices.containsKey(name)) {
                        int norm = matrixInfinityNorm(matrices.get(name));
                        System.out.printf("The norm of matrix " + name + " is: " + norm);
                    }
                    else {
                        System.out.println("No matrix found. Please enter a matrix first.");
                    }
                    break;

                case 4:
                    System.out.print("Enter matrix name: ");
                    name = scanner.nextLine();
                    if (matrices.containsKey(name)) {
                        try {
                            int normInv = matrixInfinityNorm(inverseMatrix(matrices.get(name)));
                            System.out.printf("The infinity norm of the inverse matrix of " + name + " is: " + normInv + "\n");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    else {
                        System.out.println("No matrix found. Please enter a matrix first.");
                    }
                    break;

                case 5:
                    System.out.print("Enter matrix name: ");
                    name = scanner.nextLine();
                    if (matrices.containsKey(name)) {
                        try {
                            double cond = condition(matrices.get(name));
                            System.out.printf("The condition number of matrix " + name + " is: " + cond + "\n");
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    else {
                        System.out.println("No matrix found. Please enter a matrix first.");
                    }
                    break;

                case 6:
                    System.out.print("Enter the first matrix name: ");
                    name = scanner.nextLine();
                    if (!matrices.containsKey(name))
                        System.out.println("The first matrix was not found. Please enter a matrix first.");
                    System.out.print("Enter the second matrix name: ");
                    String name2 = scanner.nextLine();
                    if (!matrices.containsKey(name2))
                        System.out.println("The second matrix was not found. Please enter a matrix first.");

                    int[][] multm = matricesMultiplication(matrices.get(name), matrices.get(name2));
                    System.out.println("Multiplied matrix: " + name + " * " + name2 + " = ");
                    printMatrix(multm);
                    System.out.print("Add multiplied to matrices(Y/N): ");
                    if (scanner.nextLine().equalsIgnoreCase("Y")) {
                        while (true) {
                            System.out.print("Enter name for the multiplied matrix: ");
                            String multName = scanner.nextLine();
                            if (matrices.containsKey(multName)) {
                                System.out.println("A matrix with that name already exists. Please enter a new name.");
                            }
                            else {
                                matrices.put(multName, multm);
                                break;
                            }
                        }
                    }
                    break;

                case 7:
                    System.out.print("Enter the matrix name: ");
                    name = scanner.nextLine();
                    if (matrices.containsKey(name)){
                        System.out.print("Enter a vector: ");
                        int[] vect = InitializeVector();
                        matrix_VectorMultiplication(matrices.get(name), vect);
                        break;
                    }
                    else
                        System.out.println("The matrix was not found. Please enter a matrix first.");
                    break;

                case 8:
                    System.out.print("Enter matrix name: ");
                    name = scanner.nextLine();
                    if (matrices.containsKey(name)) {
                        System.out.println("Matrix " + name + ":");
                        printMatrix(matrices.get(name));
                    }
                    else {
                        System.out.println("No matrix found. Please enter a matrix first.");
                    }
                    break;

                case 9:
                    if (!matrices.isEmpty()) {
                        for (Map.Entry<String, int[][]> entry : matrices.entrySet()) {
                            System.out.println("\nMatrix " + entry.getKey() + ":");
                            printMatrix(entry.getValue());
                        }
                    }
                    else {
                        System.out.println("No matrices saved.");
                    }
                    break;

                case 10:
                    System.out.println("Goodbye =)");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}

package operations;

import java.util.Scanner;

public class LUFactorization {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n\nMenu for LU matrix factorization:");
            System.out.println("1] Enter a matrix");
            System.out.println("2] close");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter a matrix:");
                    double[][] matrix = matrixOperations.InitializeMatrix();
                    System.out.println("Matrix entered successfully.");
                    System.out.println("Enter a vector:");
                    double[] b = matrixOperations.InitializeVector();
                    System.out.println("Vector entered successfully.");

                    double[][][] LU = luDecomposition(matrix);

                    System.out.println("L matrix:");
                    matrixOperations.printMatrix(LU[0]);

                    System.out.println("U matrix:");
                    matrixOperations.printMatrix(LU[1]);

                    double[] x = solveLU(LU[0], LU[1], b);
                    System.out.println("Solution vector x:");
                    matrixOperations.printVector(x);

                    double[][] AInv = inverse(matrix);
                    System.out.println("Inverse of matrix A:");
                    matrixOperations.printMatrix(AInv);
                    break;
                case 2:
                    System.out.println("Goodbye =)");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public static double[][][] luDecomposition(double[][] A) {
        int n = A.length;
        double[][] L = new double[n][n];
        double[][] U = new double[n][n];

        for (int i = 0; i < n; i++) {
            L[i][i] = 1.0;
        }

        for (int j = 0; j < n; j++) {
            for (int i = 0; i <= j; i++) {
                double sum = 0.0;
                for (int k = 0; k < i; k++) {
                    sum += U[k][j] * L[i][k];
                }
                U[i][j] = A[i][j] - sum;
            }

            for (int i = j + 1; i < n; i++) {
                double sum = 0.0;
                for (int k = 0; k < j; k++) {
                    sum += U[k][j] * L[i][k];
                }
                L[i][j] = (A[i][j] - sum) / U[j][j];
            }
        }

        return new double[][][]{L, U};
    }

    public static double[] solveLU(double[][] L, double[][] U, double[] b) {
        int n = L.length;
        double[] y = new double[n];
        double[] x = new double[n];

        // Forward substitution to solve Ly = b
        for (int i = 0; i < n; i++) {
            double sum = 0.0;
            for (int j = 0; j < i; j++) {
                sum += L[i][j] * y[j];
            }
            y[i] = b[i] - sum;
        }

        // Backward substitution to solve Ux = y
        for (int i = n - 1; i >= 0; i--) {
            double sum = 0.0;
            for (int j = i + 1; j < n; j++) {
                sum += U[i][j] * x[j];
            }
            x[i] = (y[i] - sum) / U[i][i];
        }

        return x;
    }

    public static double[][] inverse(double[][] A) {
        int n = A.length;
        double[][] AInv = new double[n][n];
        double[] b;

        for (int i = 0; i < n; i++) {
            b = new double[n];
            b[i] = 1.0;
            double[] col = solveLU(luDecomposition(A)[0], luDecomposition(A)[1], b);
            for (int j = 0; j < n; j++) {
                AInv[j][i] = col[j];
            }
        }

        return AInv;
    }
}

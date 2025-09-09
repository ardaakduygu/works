/* Arda Akduygu - 20COMP1019
 * Mehmet Serin - 19SOFT1023
 */
package opsys_project;

import java.io.*;
import java.util.Scanner;

public class MatrixMultiplier_SingleThread {
    
    private static int MATRIX_SIZE;
    private static int[][] matrixA;
    private static int[][] matrixB;
    private static int[][] resultMatrix;

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        
        
        System.out.print("Enter the matrix input file path: ");
        String filePath = scanner.nextLine();
        
        
        System.out.print("Enter the matrix size (e.g., 1000 for 1000x1000): ");
        MATRIX_SIZE = scanner.nextInt();

        
        resultMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];

        
        loadMatrixA(filePath);
        loadMatrixB(filePath);

        
        long startTime = System.currentTimeMillis();

        
        performMatrixMultiplication();

        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        
        System.out.println("Matrix multiplication completed (single-threaded).");
        System.out.println("Time taken (single-threaded): " + duration + " ms");

        scanner.close();
    }

    public static void loadMatrixA(String filePath) throws IOException {
        matrixA = new int[MATRIX_SIZE][MATRIX_SIZE];
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            // Skip the header line "Matrix A 1000x1000:"
            reader.readLine();
            
            
            for (int i = 0; i < MATRIX_SIZE; i++) {
                String line = reader.readLine();
                if (line != null) {
                    String[] values = line.trim().split("\\s+");
                    for (int j = 0; j < MATRIX_SIZE && j < values.length; j++) {
                        matrixA[i][j] = Integer.parseInt(values[j]);
                    }
                }
            }
        }
    }

    public static void loadMatrixB(String filePath) throws IOException {
        matrixB = new int[MATRIX_SIZE][MATRIX_SIZE];
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Matrix B")) {
                    break;
                }
            }
            
            
            for (int i = 0; i < MATRIX_SIZE; i++) {
                line = reader.readLine();
                if (line != null) {
                    String[] values = line.trim().split("\\s+");
                    for (int j = 0; j < MATRIX_SIZE && j < values.length; j++) {
                        matrixB[i][j] = Integer.parseInt(values[j]);
                    }
                }
            }
        }
    }

    private static void performMatrixMultiplication() {
        
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                resultMatrix[i][j] = 0;
                for (int k = 0; k < MATRIX_SIZE; k++) {
                    resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }
    }
} 
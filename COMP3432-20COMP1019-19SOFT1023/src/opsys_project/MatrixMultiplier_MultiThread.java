/* Arda Akduygu - 20COMP1019
 * Mehmet Serin - 19SOFT1023
 */
package opsys_project;

import java.io.*;
import java.util.Scanner;

public class MatrixMultiplier_MultiThread {
    
    private static int MATRIX_SIZE;
    private static int NUM_THREADS;
    private static int[][] matrixB; 
    private static int[][] resultMatrix;
    private static String filePath;
    
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        
        System.out.print("Enter the matrix input file path: ");
        filePath = scanner.nextLine();
        
        
        System.out.print("Enter the matrix size (e.g., 1000 for 1000x1000): ");
        MATRIX_SIZE = scanner.nextInt();
        
        
        System.out.print("Enter the number of threads: ");
        NUM_THREADS = scanner.nextInt();

       
        resultMatrix = new int[MATRIX_SIZE][MATRIX_SIZE];
        
        
        loadMatrixB(filePath);

        
        long startTime = System.currentTimeMillis();

        
        performMultiThreadedMultiplication();

        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        
        System.out.println("Matrix multiplication completed.");
        System.out.println("Time taken (multithreaded): " + duration + " ms");
        
        scanner.close();
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
    
    private static void performMultiThreadedMultiplication() throws InterruptedException {
        Thread[] threads = new Thread[NUM_THREADS];
        int rowsPerThread = MATRIX_SIZE / NUM_THREADS;
        int remainingRows = MATRIX_SIZE % NUM_THREADS;
        
        int currentStartRow = 0;
        
        
        for (int t = 0; t < NUM_THREADS; t++) {
            int startRow = currentStartRow;
            int endRow = startRow + rowsPerThread;
            
            
            if (t < remainingRows) {
                endRow++;
            }
            
            currentStartRow = endRow;
            
            threads[t] = new Thread(new Worker(startRow, endRow));
            threads[t].start();
        }
        
        
        for (Thread thread : threads) {
            thread.join();
        }
    }
    
   
    static class Worker implements Runnable {
        private int startRow;
        private int endRow;
        
        public Worker(int startRow, int endRow) {
            this.startRow = startRow;
            this.endRow = endRow;
        }
        
        @Override
        public void run() {
            try {
                
                int[][] localMatrixA = readMatrixARows(filePath, startRow, endRow);
                
                
                for (int i = 0; i < (endRow - startRow); i++) {
                    int actualRow = startRow + i;
                    for (int j = 0; j < MATRIX_SIZE; j++) {
                        resultMatrix[actualRow][j] = 0;
                        for (int k = 0; k < MATRIX_SIZE; k++) {
                            resultMatrix[actualRow][j] += localMatrixA[i][k] * matrixB[k][j];
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        public int[][] readMatrixARows(String filePath, int start, int end) throws IOException {
            int rowCount = end - start;
            int[][] localMatrix = new int[rowCount][MATRIX_SIZE];
            
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                
                reader.readLine();
                
                
                for (int i = 0; i < start; i++) {
                    reader.readLine();
                }
                
                
                for (int i = 0; i < rowCount; i++) {
                    String line = reader.readLine();
                    if (line != null) {
                        String[] values = line.trim().split("\\s+");
                        for (int j = 0; j < MATRIX_SIZE && j < values.length; j++) {
                            localMatrix[i][j] = Integer.parseInt(values[j]);
                        }
                    }
                }
            }
            
            return localMatrix;
        }
    }
} 
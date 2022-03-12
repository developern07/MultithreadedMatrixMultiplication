package multiplication;

import java.io.File;
import java.io.IOException;

/**
 * Main class
 */
public class Main {

    /**
     * Number of rows in the first matrix
     */
    private static final int firstValue = 150;
    /**
     * Number of columns in the first matrix || rows in the second matrix
     */
    private static final int secondValue = 450;
    /**
     * Number of columns in the second matrix
     */
    private static final int thirdValue = 750;
    /**
     * First matrix
     */
    private static final int[][] firstMatrix = new int[firstValue][secondValue];
    /**
     * Second matrix
     */
    private static final int[][] secondMatrix = new int[secondValue][thirdValue];

    public static void main(String[] args) throws IOException {

        MultithreadedMatrixMultiplication.randomMatrix(firstMatrix);
        MultithreadedMatrixMultiplication.writeMatrixToFile(firstMatrix,new File("src/main/resources/FirstMatrix.scv"));
        MultithreadedMatrixMultiplication.randomMatrix(secondMatrix);
        MultithreadedMatrixMultiplication.writeMatrixToFile(secondMatrix,new File("src/main/resources/SecondMatrix.scv"));
        int[][] result = MultithreadedMatrixMultiplication.multiplyMatrix(firstMatrix, secondMatrix, Runtime.getRuntime().availableProcessors());
        MultithreadedMatrixMultiplication.writeMatrixToFile(result, new File("src/main/resources/Result.scv"));
        System.out.println("-- CHECK DIRECTORY RESOURCES --");
    }
}

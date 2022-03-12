package multiplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * The class in which multithreaded matrix multiplication occurs
 */
public class MultithreadedMatrixMultiplication extends Thread {
    /**
     * First matrix
     */
    private final int[][] firstMatrix;
    /**
     * Second matrix
     */
    private final int[][] secondMatrix;
    /**
     * The result of multiplying two matrices
     */
    private final int[][] resultMatrix;
    /**
     * Start index
     */
    private final int startIndex;
    /**
     * End index
     */
    private final int endIndex;

    /** Constructor with parameters
     * @param firstMatrix  First matrix
     * @param secondMatrix Second matrix
     * @param resultMatrix The result of multiplying two matrices
     * @param startIndex   Start index
     * @param endIndex    End index
     */
    public MultithreadedMatrixMultiplication(int[][] firstMatrix, int[][] secondMatrix, int[][] resultMatrix,
                    int startIndex, int endIndex) {
        this.firstMatrix  = firstMatrix;
        this.secondMatrix = secondMatrix;
        this.resultMatrix = resultMatrix;
        this.startIndex   = startIndex;
        this.endIndex    = endIndex;
    }

    /**
     * Calculating a value in a cell
     * @param row Row number
     * @param col Column number
     */
    private void calcValue(int row, int col) {
        int sum = 0;
        for (int i = 0; i < secondMatrix.length; ++i)
            sum += firstMatrix[row][i] * secondMatrix[i][col];
        resultMatrix[row][col] = sum;
    }

    /** Thread func */
    @Override
    public void run() {
        System.out.println(getName() + " started.");
        for (int index = startIndex; index < endIndex; ++index)
            calcValue(index / secondMatrix[0].length, index % secondMatrix[0].length);
        System.out.println(getName() + " finished.");
    }

    /**
     * Multithreaded matrix multiplication
     * @param firstMatrix First matrix
     * @param secondMatrix Second matrix
     * @param threadCount Number of threads
     * @return result resulting matrix
     */
    public static int[][] multiplyMatrix(int[][] firstMatrix,int[][] secondMatrix, int threadCount) {

        final int rowCount = firstMatrix.length;
        final int colCount = secondMatrix[0].length;
        final int[][] result = new int[rowCount][colCount];

        final int cellsForThread = (rowCount * colCount) / threadCount;
        int startIndex = 0;
        final MultithreadedMatrixMultiplication[] multiplierThreads = new MultithreadedMatrixMultiplication[threadCount];

        for (int threadIndex = threadCount-1; threadIndex >= 0; --threadIndex) {
            int endIndex = startIndex + cellsForThread;
            if (threadIndex == 0) {
                endIndex = rowCount * colCount;
            }
            multiplierThreads[threadIndex] = new MultithreadedMatrixMultiplication(firstMatrix, secondMatrix, result, startIndex, endIndex);
            multiplierThreads[threadIndex].start();
            startIndex = endIndex;
        }

        try {
            for (final MultithreadedMatrixMultiplication multiplierThread : multiplierThreads)
                multiplierThread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Filling a Matrix with Random Values
     * @param matrix the matrix
     */
    public static void randomMatrix(int[][] matrix) {
        final Random random = new Random();
        for (int row = 0; row < matrix.length; ++row)
            for (int col = 0; col < matrix[row].length; ++col)
                matrix[row][col] = random.nextInt(50);
    }

    /**
     * Writing a Matrix to a File
     * @param matrix the matrix
     */
    public static void writeMatrixToFile(int[][] matrix, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        for (int[] ints : matrix) {
            for (int anInt : ints) {
                fileWriter.write(anInt + ";");
            }
            fileWriter.write("\n");
            fileWriter.flush();
        }
        fileWriter.close();
    }
}

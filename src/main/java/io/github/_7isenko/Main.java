package io.github._7isenko;

import javax.management.AttributeNotFoundException;
import java.io.FileNotFoundException;
import java.util.Arrays;

/**
 * @author 7isenko
 */
public class Main {

    private static InputReader inputReader;

    private static double[][] matrix;
    private static double[] bVector;
    private static double accuracy;
    private static SimpleIterationsCalculator calculator;

    public static void main(String[] args) {

        inputReader = new InputReader();

        boolean hasData = loadDataFromUser();
        if (!hasData) {
            return;
        }

        try {
            new DiagonalDominanceMatrixRearranger(matrix, bVector).rearrange();
            calculator = new SimpleIterationsCalculator(matrix, bVector, accuracy);
            try {
                calculator.calculate();
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ArithmeticException("Your matrix is incorrect");
            }

            printAnswer();

        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Do you want to enter a new matrix? y/n");
        if (inputReader.parseYesOrNo()) {
            main(new String[0]);
        }
    }

    private static void printAnswer() {
        System.out.println("Matrix is: " + Arrays.deepToString(matrix));
        System.out.println("B vector is: " + Arrays.toString(bVector));
        System.out.println("Vector of X: " + Arrays.toString(calculator.getAnswer()));
        System.out.println("Inaccuracy vector: " + Arrays.toString(calculator.getInaccuracy()));
        System.out.println("Amount of iterations: " + calculator.getIterationsCount());
    }

    private static boolean loadDataFromUser() {
        System.out.println("Do you want to enter values from file? y/n");
        if (inputReader.parseYesOrNo()) {
            System.out.print("Print the filename: ");
            String filename = inputReader.readStringFromConsole();
            try {
                inputReader.readFromFile(filename);
                matrix = inputReader.getMatrix();
                bVector = inputReader.getBVector();
                accuracy = inputReader.getAccuracy();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("There is no file named " + filename);
                System.out.println("Try again? y/n");
                if (inputReader.parseYesOrNo()) {
                    main(new String[0]);
                }
                return false;
            } catch (AttributeNotFoundException e) {
                System.out.println(e.getMessage());
                System.out.print("Write it here: ");
                accuracy = inputReader.readDoubleFromConsole();
            }
        } else {
                readMatrixFromConsole();
        }
        return true;
    }

    private static void readMatrixFromConsole() {
        System.out.println("Enter your augmented matrix:");
        inputReader.readMatrixFromConsole();
        matrix = inputReader.getMatrix();
        bVector = inputReader.getBVector();
        System.out.print("Enter accuracy: ");
        accuracy = inputReader.readAccuracy();
    }


}

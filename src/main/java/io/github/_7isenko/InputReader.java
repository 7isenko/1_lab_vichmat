package io.github._7isenko;

import javax.management.AttributeNotFoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author 7isenko
 */
public class InputReader {

    private double[][] matrix;
    private double[] bVector;
    private double accuracy = Double.MIN_VALUE;


    public double readAccuracy() {
        accuracy = readDoubleFromConsole();
        return accuracy;
    }

    public double readDoubleFromConsole() {
        Scanner in = new Scanner(System.in);
        in.useLocale(Locale.US);
        return in.nextDouble();
    }

    public String readStringFromConsole() {
        Scanner in = new Scanner(System.in);
        return in.next();
    }

    public boolean parseYesOrNo() {
        Scanner in = new Scanner(System.in);
        return in.next().startsWith("y");
    }

    public void readFromFile(String filename) throws FileNotFoundException {
        Scanner input = new Scanner(new File(filename));
        readMatrixFromScanner(input);

        if (input.hasNextDouble()) {
            accuracy = input.nextDouble();
        }
    }

    public void readMatrixFromConsole() {
        Scanner input = new Scanner(System.in);
        readMatrixFromScanner(input);
    }

    public void readMatrixFromScanner(Scanner input) {
        input.useLocale(Locale.US);
        String[] data = input.nextLine().split(" ");

        int size = data.length - 1;
        matrix = new double[size][size];
        bVector = new double[size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j <= size; j++) {
                if (j != size) {
                    matrix[i][j] = Double.parseDouble(data[j]);
                } else {
                    bVector[i] = Double.parseDouble(data[j]);
                }
            }
            if (i != size - 1) {
                data = input.nextLine().split(" ");
            }
        }
    }

    public double[][] getMatrix() {
        return matrix;
    }

    public double[] getBVector() {
        return bVector;
    }

    public double getAccuracy() throws AttributeNotFoundException {
        if (accuracy != Double.MIN_VALUE) {
            return accuracy;
        } else {
            throw new AttributeNotFoundException("The accuracy isn't set");
        }
    }
}

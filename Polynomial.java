import java.io.BufferedWriter;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.*;

public class Polynomial {

    double[] coefficients;
    int[] exponents;

    // constructor 1
    public Polynomial() {
        double[] array = new double[1];
        int[] intArray = new int[1];
        array[0] = 0;
        coefficients = array;
        exponents = intArray;
    }

    // constructor 2
    public Polynomial(double[] coefficient, int[] exponent) {
        coefficients = new double[coefficient.length];
        exponents = new int[exponent.length];
        for (int i = 0; i < coefficient.length; i++) {
            coefficients[i] = coefficient[i];
            exponents[i] = exponent[i];
        }
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    // constructor 3
    public Polynomial(File file) {

        // Open and read files
        try {
            // read line
            Scanner myReader = new Scanner(file);
            String data = myReader.nextLine();
            myReader.close();

            // Update coefficient

            String newData = data.replace("-", "+-");
            String[] arr1 = newData.split("\\+");
            String[] arr;
            if (arr1[0].isEmpty() == true) {
                arr = Arrays.copyOfRange(arr1, 1, arr1.length);
            } else {
                arr = arr1;
            }

            coefficients = new double[arr.length];

            for (int i = 0; i < arr.length; i++) {
                String str = "";
                if (isNumeric(arr[i]) == true) {
                    coefficients[i] = Double.parseDouble(arr[i]);
                } else {
                    for (int j = 0; j < arr[i].length(); j++) {
                        if (arr[i].charAt(j) != 'x') {
                            str = str + arr[i].charAt(j);
                        } else {
                            coefficients[i] = Double.parseDouble(str);
                            break;
                        }
                    }
                }
            }

            // update exponent

            String newExp = data.replace("-", "+-");
            String[] exp = newExp.split("\\+");
            String[] finalexp;

            if (exp[0].isEmpty() == true) {
                finalexp = Arrays.copyOfRange(exp, 1, exp.length);
            } else {
                finalexp = exp;
            }

            exponents = new int[finalexp.length];

            for (int i = 0; i < finalexp.length; i++) {
                if (isNumeric(finalexp[i]) == true) {
                    exponents[i] = 0;
                } else {
                    for (int j = 0; j < finalexp[i].length(); j++) {
                        if (finalexp[i].charAt(j) == 'x') {
                            String str = "";
                            for (int k = j + 1; k < finalexp[i].length(); k++) {
                                if (Character.isDigit(finalexp[i].charAt(k)) == true) {
                                    str = str + finalexp[i].charAt(k);
                                }
                                exponents[i] = Integer.parseInt(str);
                                break;
                            }
                        }
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public boolean isIn(int[] exponents, int target) {
        for (int i = 0; i < exponents.length; i++) {
            if (exponents[i] == target) {
                return true;
            }
        }
        return false;
    }

    public double[] toPolynomial(double[] coefficients, int[] exponents) {

        // FIND LENGHT OF POLYNOMIAL
        int count = 0;
        for (int i = 0; i < exponents.length; i++) {
            count++;
        }

        int length = exponents[count - 1];

        // BUILD POLYNOMIAL
        int[] poly = new int[length + 1];

        for (int i = 0; i < poly.length; i++) {
            if (isIn(exponents, i) == true) {
                poly[i] = i;
            } else {
                poly[i] = -1;
            }
        }

        // BUILD COEFFICIENTS
        double[] result = new double[length + 1];
        int counter = 0;

        for (int i = 0; i < result.length; i++) {
            if (poly[i] != -1) {
                result[i] = coefficients[counter];
                counter++;
            } else {
                result[i] = 0;
            }
        }

        return result;
    }

    public int[] toExponent(double[] coefficients) {
        // find length
        int length = 0;
        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] != 0) {
                length++;
            }
        }
        // create exponent array
        int counter = 0;
        int[] exponents = new int[length];

        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] != 0) {
                exponents[counter] = i;
                counter++;
            }
        }

        return exponents;
    }

    public double[] toCoefficient(double[] coefficients) {
        // find length
        int length = 0;

        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] != 0) {
                length++;
            }
        }
        // create coefficient array
        int counter = 0;
        double[] coefficient = new double[length];

        for (int i = 0; i < coefficients.length; i++) {
            if (coefficients[i] != 0) {
                coefficient[counter] = coefficients[i];
                counter++;
            }
        }

        return coefficient;
    }

    public Polynomial add(Polynomial other) {

        double[] poly = toPolynomial(coefficients, exponents);
        double[] otherPoly = toPolynomial(other.coefficients, other.exponents);

        // even them out
        int len1 = otherPoly.length;
        int len2 = poly.length;

        // fin min and max
        int min = 0;
        int max = 0;

        if (len1 < len2) {
            min = len1;
            max = len2;
        } else {
            min = len2;
            max = len1;
        }

        // Add each other
        if (min == len1) {
            for (int i = 0; i < len1; i++) {
                poly[i] = poly[i] + otherPoly[i];
            }
            double[] coefficient1 = toCoefficient(poly);
            int[] exponent1 = toExponent(poly);
            Polynomial p = new Polynomial(coefficient1, exponent1);
            return p;
        } else {
            for (int i = 0; i < len2; i++) {
                otherPoly[i] = otherPoly[i] + poly[i];
            }
            double[] coefficient2 = toCoefficient(otherPoly);
            int[] exponent2 = toExponent(otherPoly);
            Polynomial p2 = new Polynomial(coefficient2, exponent2);
            return p2;
        }
    }

    public double evaluate(double x) {

        double[] total = new double[100];

        // set total to coefficients
        for (int i = 0; i < coefficients.length; i++) {
            total[i] = coefficients[i];
        }
        // calculate totals for each index
        for (int i = 0; i < coefficients.length; i++) {
            total[i] = total[i] * (Math.pow(x, exponents[i]));
        }

        // find total sum
        double sum = 0;
        for (int i = 0; i < total.length; i++) {
            sum += total[i];
        }

        return sum;
    }

    public boolean hasRoot(double x) {
        if (evaluate(x) == 0) {
            return true;
        }
        return false;
    }

    public Polynomial multiply(Polynomial other) {

        // produce coefficient array for both
        double[] coefficient = toPolynomial(coefficients, exponents);
        double[] otherCoefficient = toPolynomial(other.coefficients, other.exponents);

        // find lengths
        int m = coefficient.length;
        int n = otherCoefficient.length;

        // produce new coefficient array
        double[] result = new double[m + n - 1];

        // multiply
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i + j] += coefficient[i] * otherCoefficient[j];
            }
        }

        // convert back to exponent and coefficient
        double[] finalCoefficient = toCoefficient(result);
        int[] finalExponent = toExponent(result);

        Polynomial p = new Polynomial(finalCoefficient, finalExponent);

        return p;
    }

    public void saveToFile(String path) {
        // Open and read files
        try {

            // open file
            FileWriter fw = new FileWriter(path);

            // for (int i = 0; i < coefficients.length; i++) {
            // if (exponents[i] == 0 && i == 0) {
            // fw.write("" + coefficients[0]);
            // } else if (exponents[i] == 0 && i > 0) {
            // if (coefficients[i] > 0) {
            // fw.write("+" + coefficients[i]);
            // } else {
            // fw.write("-" + coefficients[i]);
            // }
            // } else if (exponents[i] > 0) {
            // if (coefficients[i] > 0) {
            // fw.write("+" + coefficients[i] + "x" + exponents[i]);
            // } else {
            // fw.write(coefficients[i] + "x" + exponents[i]);
            // }
            // }
            // }

            // write in file

            for (int i = 0; i < coefficients.length; i++) {
                if (exponents[i] == 0) {
                    if (coefficients[i] > 0 && i > 0) {
                        fw.write("+" + coefficients[i]);
                    } else {
                        fw.write("" + coefficients[i]);
                    }
                } else {
                    if (exponents[i] == 1) {
                        if (coefficients[i] > 0 && i > 0) {
                            fw.write("+" + coefficients[i] + 'x');
                        } else {
                            fw.write("" + coefficients[i] + 'x');
                        }
                    } else {
                        if (coefficients[i] > 0 && i > 0) {
                            fw.write("+" + coefficients[i] + 'x' + exponents[i]);
                        } else {
                            fw.write("" + coefficients[i] + "x" + exponents[i]);
                        }
                    }
                }
            }

            fw.close();

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

}
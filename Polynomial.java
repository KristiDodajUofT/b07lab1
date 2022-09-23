public class Polynomial {
    double[] coefficients;

    // constructor 1
    public Polynomial() {
        double[] array = new double[1];
        array[0] = 0;
        coefficients = array;
    }

    // constructor 2
    public Polynomial(double[] array) {
        coefficients = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            coefficients[i] = array[i];
        }
    }

    public Polynomial add(Polynomial other) {

        // even them out
        int len1 = other.coefficients.length;
        int len2 = coefficients.length;

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
                coefficients[i] = coefficients[i] + other.coefficients[i];
            }
            Polynomial p = new Polynomial(coefficients);
            return p;
        } else {
            for (int i = 0; i < len2; i++) {
                other.coefficients[i] = other.coefficients[i] + coefficients[i];
            }
            Polynomial p2 = new Polynomial(other.coefficients);
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
            total[i] = total[i] * (Math.pow(x, i));
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
}
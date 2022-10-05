import java.io.File;

public class Driver {
    public static void main(String[] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double[] c1 = { 6, 5 };
        int[] e1 = { 1, 4 };
        Polynomial p1 = new Polynomial(c1, e1);
        double[] c2 = { -2, -9 };
        int[] e2 = { 2, 5 };
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if (s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        // TEST MULTIPLY FUNCTION
        double[] c3 = { 6, 5 };
        int[] e3 = { 1, 4 };
        Polynomial p3 = new Polynomial(c3, e3);
        double[] c4 = { -2, -9 };
        int[] e4 = { 2, 5 };
        Polynomial p4 = new Polynomial(c4, e4);
        Polynomial g = p3.multiply(p4);

        for (int i = 0; i < g.coefficients.length; i++) {
            System.out.print("[CO: " + g.coefficients[i] + ", EX: " + g.exponents[i] + "], ");
        }
        // IF Sucessfull we should be getting [CO: -12.0, EX: 3], [CO: -64.0, EX: 6],
        // [CO: -45.0, EX: 9]

        // TEST FILE CONSTRUCTOR
        File file = new File("/Users/kristidodaj/b07lab1/polynomial.txt");
        Polynomial a = new Polynomial(file);
        System.out.print("\n");
        for (int i = 0; i < a.coefficients.length; i++) {
            System.out.print("[CO: " + a.coefficients[i] + ", EX: " + a.exponents[i] + "], ");
        }

        // IF Sucessfull we should be getting [CO: 5.0, EX: 0], [CO: -3.0, EX: 2],
        // [CO:7.0, EX: 8]

        // If sucesfull should display 5.0-3.0x2+7.0x8 on the given file
        a.saveToFile("/Users/kristidodaj/b07lab1/Write.txt");
    }

}
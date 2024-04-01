package com.zhuang.apache.math;


import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.junit.Test;

import java.util.Arrays;

public class PolynomialCurveFitterTest {

    @Test
    public void test() {
        // Sample data points (you can replace these with your own data)
        double[] alpha = {-20, -15, -10, -5, 0, 5, 10, 15, 20, 25};
        double[] Cz = {-0.0933, -0.0978, -0.0982, -0.0784, -0.0489, -0.0066, 0.049, 0.1072, 0.1283, 0.13};

        // Specify the degree of the polynomial (e.g., 9 for your case)
        int degree = 9;

        // Create a default curve fitter with zero initial guess for coefficients
        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);

        // Fit the data points
        WeightedObservedPoint[] observations = new WeightedObservedPoint[alpha.length];
        for (int i = 0; i < alpha.length; i++) {
            observations[i] = new WeightedObservedPoint(1.0, alpha[i], Cz[i]);
        }

        double[] coefficients = fitter.fit(Arrays.asList(observations));

        // Now you have the optimal coefficients for the polynomial
        System.out.println("Optimal coefficients:");
        for (int i = 0; i < coefficients.length; i++) {
            System.out.println("C" + i + ": " + coefficients[i]);
        }

        // Example: Evaluate the polynomial at a specific alpha value (in radians)
        double alphaRadians = Math.toRadians(180); // Convert alpha to radians
        double Czb = 0.0;
        for (int i = 0; i < coefficients.length; i++) {
            Czb += coefficients[i] * Math.pow(alphaRadians, i);
        }

        System.out.println("Czb at alpha = " + alphaRadians + ": " + Czb);
    }

}

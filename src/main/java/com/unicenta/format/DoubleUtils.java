package com.unicenta.format;

/**
 * DoubleUtils is a utility class for handling double values.
 *
 * @author Adrian
 */
public class DoubleUtils {

    /**
     * Fixes the decimal places of a given Number value to 6 decimal places.
     *
     * @param value the Number value to be fixed.
     * @return the double value with fixed decimal places.
     */
    public static double fixDecimals(Number value) {
        return Math.rint(value.doubleValue() * 1_000_000.0) / 1_000_000.0;
    }
}
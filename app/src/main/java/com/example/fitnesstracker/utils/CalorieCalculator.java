package com.example.profitness.utils;

public class CalorieCalculator {

    public static double calculateCaloriesBurned(int steps, double weight, double height, int age, String gender) {
        // Basic calorie calculation based on steps
        // More accurate formula considering user metrics
        double baseCalories = steps * 0.04;

        // Adjust based on user profile
        if (weight > 0) {
            baseCalories *= (weight / 70.0); // Normalize to 70kg reference
        }

        if ("male".equalsIgnoreCase(gender)) {
            baseCalories *= 1.1;
        }

        return Math.round(baseCalories * 100.0) / 100.0;
    }

    public static double calculateDistance(int steps, double height) {
        // Calculate distance based on steps and height (for step length estimation)
        double stepLength = height * 0.415; // Average step length as percentage of height
        return (steps * stepLength) / 1000; // Convert to kilometers
    }

    public static double calculatePace(double distance, long timeInMinutes) {
        if (timeInMinutes == 0) return 0;
        return distance / (timeInMinutes / 60.0); // km/h
    }
}
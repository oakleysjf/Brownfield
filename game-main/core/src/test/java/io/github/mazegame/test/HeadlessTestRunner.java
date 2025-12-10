package io.github.mazegame.test;

/**
 * Headless Test Runner for Maze Game
 * 
 * This is a simple test runner that can be executed in a headless environment
 * without requiring the graphics components of libGDX.
 * 
 * Run with: gradle test
 * Generate coverage report with: gradle test jacocoTestReport
 * Coverage report will be available at: core/build/reports/jacoco/test/html/index.html
 */
public class HeadlessTestRunner {
    
    /**
     * Main method for running tests manually
     */
    public static void main(String[] args) {
        System.out.println("Headless Test Runner for Maze Game");
        System.out.println("====================================");
        System.out.println("Tests are configured to run via Gradle");
        System.out.println("Execute: gradle test");
        System.out.println("For coverage: gradle test jacocoTestReport");
    }
}

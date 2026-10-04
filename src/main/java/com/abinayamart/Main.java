package com.abinayamart;

import com.abinayamart.ui.ConsoleUI;

/**
 * AbinayaMart entry point.
 * Developer: Abinaya | J.J College of Engineering and Technology.
 */
public class Main {
    public static void main(String[] args) {
        try {
            new ConsoleUI().start();
        } catch (Exception e) {
            System.err.println("[AbinayaMart] Fatal error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

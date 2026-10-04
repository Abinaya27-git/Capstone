package com.abinayamart.service.payment;

import com.abinayamart.exception.AbinayaMartException;

/** Polymorphic mock-payment contract (OOP polymorphism). */
public interface PaymentMethod {
    String name();

    /** Simulate charging the amount; throws on invalid mock details. */
    void pay(double amount) throws AbinayaMartException;
}

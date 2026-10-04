package com.abinayamart.service.payment;

/** Cash on delivery needs no pre-charge. */
public class CODPayment implements PaymentMethod {
    @Override
    public String name() {
        return "COD";
    }

    @Override
    public void pay(double amount) {
        System.out.printf("[AbinayaMart Pay] COD selected. Pay Rs.%.2f on delivery.%n", amount);
    }
}

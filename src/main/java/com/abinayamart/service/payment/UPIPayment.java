package com.abinayamart.service.payment;

import com.abinayamart.exception.AbinayaMartException;

/** Mock UPI payment. */
public class UPIPayment implements PaymentMethod {
    private final String upiId;

    public UPIPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public String name() {
        return "UPI";
    }

    @Override
    public void pay(double amount) throws AbinayaMartException {
        if (upiId == null || !upiId.contains("@")) {
            throw new AbinayaMartException("Invalid UPI ID. Expected format like name@bank.");
        }
        System.out.printf("[AbinayaMart Pay] UPI Rs.%.2f charged to %s ... SUCCESS%n", amount, upiId);
    }
}

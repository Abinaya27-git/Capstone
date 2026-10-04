package com.abinayamart.service.payment;

import com.abinayamart.exception.AbinayaMartException;

/** Factory + facade for mock payments. */
public class PaymentService {

    public PaymentMethod method(String choice, String detail) throws AbinayaMartException {
        if (choice == null) {
            throw new AbinayaMartException("Choose a payment mode: UPI / CARD / COD.");
        }
        switch (choice.trim().toUpperCase()) {
            case "UPI":
                return new UPIPayment(detail);
            case "CARD":
                return new CardPayment(detail);
            case "COD":
                return new CODPayment();
            default:
                throw new AbinayaMartException("Unknown payment mode. Use UPI / CARD / COD.");
        }
    }
}

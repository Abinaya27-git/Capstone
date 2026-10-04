package com.abinayamart.service.payment;

import com.abinayamart.exception.AbinayaMartException;

/** Mock card payment. */
public class CardPayment implements PaymentMethod {
    private final String cardNo;

    public CardPayment(String cardNo) {
        this.cardNo = cardNo;
    }

    @Override
    public String name() {
        return "CARD";
    }

    @Override
    public void pay(double amount) throws AbinayaMartException {
        String digits = cardNo == null ? "" : cardNo.replaceAll("\\s", "");
        if (!digits.matches("\\d{12,19}")) {
            throw new AbinayaMartException("Invalid card number. Enter 12-19 digits.");
        }
        System.out.printf("[AbinayaMart Pay] Card Rs.%.2f charged to ****%s ... SUCCESS%n",
                amount, digits.substring(digits.length() - 4));
    }
}

//package com.company;

import java.util.ArrayList;
import java.util.Iterator;

public class Loan {
    private static final ArrayList<Loan> allLoans = new ArrayList<Loan>();
    private final Customer customer;
    private final int deuration;
    private int remainingPayments;
    private final int interest;
    private final int amount;

    public Loan(Customer customer, int deuration, int interest, int amount) {
        this.customer = customer;
        this.deuration = deuration;
        this.interest = interest;
        this.amount = amount;
        this.remainingPayments = deuration;
        allLoans.add(this);
    }

    public static void passMonth() {
        ArrayList<Loan> madeForRemove = new ArrayList<>();
        Iterator<Loan> iterator = allLoans.iterator();
        while (!allLoans.isEmpty() && iterator.hasNext()) {
            Loan selectedLoan = iterator.next();
            selectedLoan.passMonthEach();
            if (selectedLoan.remainingPayments < 1) madeForRemove.add(selectedLoan);
        }
        iterator = madeForRemove.iterator();
        while (iterator.hasNext()) allLoans.remove(iterator.next());

    }

    private double getPaymentAmount() {
        return amount * ((double) interest / 100 + 1) / deuration;

    }

    private void passMonthEach() {
        if (this.remainingPayments > 0) {
            if (customer.canPayLoan(getPaymentAmount())) {
                customer.payLoan(getPaymentAmount());
                this.remainingPayments--;
            } else {
                customer.addNegativeScore();
            }
        }
    }
}

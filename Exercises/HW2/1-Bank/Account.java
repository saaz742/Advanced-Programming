//package com.company;

import java.util.ArrayList;
import java.util.Iterator;

public class Account {

    private static final ArrayList<Account> allAccounts = new ArrayList<Account>();
    private Bank bank;
    private int id;
    private double money;
    private int remainingDuration;
    private int interest;
    private Customer customer;

    public Account(Bank bank, Customer customer, int id, double money, int remainingDuration, int interest) {
        for (Account newAccount : allAccounts) {
            if (newAccount.bank.equals(bank) && newAccount.customer.equals(customer)) {
                return;
            }
        }
        this.bank = bank;
        this.id = id;
        this.money = money;
        this.remainingDuration = remainingDuration;
        this.interest = interest;
        this.customer = customer;
        allAccounts.add(this);
    }

    public static void passMonth() {
        ArrayList<Account> madeForLeave = new ArrayList<>();
        Iterator<Account> iterator = allAccounts.iterator();
        while (!allAccounts.isEmpty() && iterator.hasNext()) {
            Account newAccount = iterator.next();
            newAccount.passMonthEach();
            if (newAccount.remainingDuration == 0) {
                madeForLeave.add(newAccount);
            }
        }
        iterator = madeForLeave.iterator();
        while (iterator.hasNext()) {
            Account newAccount = iterator.next();
            newAccount.customer.leaveAccount(newAccount.id);
        }
    }

    public static void deleteAccount(Account account) {
        Iterator iterator = allAccounts.iterator();
        while (iterator.hasNext()) {
            Account newAccount = (Account) iterator.next();
            if (newAccount.equals(account)) {
                iterator.remove();
                return;
            }
        }
    }

    public int getId() {
        return id;

    }

    public double getAmountOfMoneyForLeaving() {
        return this.money;
    }

    public Bank getBank() {
        return this.bank;
    }

    private void passMonthEach() {
        remainingDuration--;
        if (remainingDuration == 0) {
            money = getAmountOfMoneyForLeaving() * (1 + (double) interest / 100);
        }
    }
}

//package com.company;

import java.util.ArrayList;
import java.util.Iterator;

public class Customer {
    private static final ArrayList<Customer> allCustomers = new ArrayList<Customer>();
    private String name;
    private double moneyInSafe;
    private ArrayList<Account> allActiveAccounts;
    private int totalNumberOfAccountsCreated;
    private int negativeScore;

    public Customer(String name, double moneyInSafe) {
        if (name != null) {
            boolean canCreate = true;
            Iterator<Customer> iterator = allCustomers.iterator();
            while (iterator.hasNext() && canCreate) {
                if (iterator.next().name.equals(name)) canCreate = false;
            }
            if (canCreate) {
                this.name = name;
                this.moneyInSafe = moneyInSafe;
                this.allActiveAccounts = new ArrayList<Account>();
                this.totalNumberOfAccountsCreated = 0;
                this.negativeScore = 0;
                allCustomers.add(this);
            }

        }
    }

    public Customer getCustomerByName(String name) {
        if (name != null) for (Customer customer : allCustomers) {
            if (customer.getName().equals(name)) return customer;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void createNewAccount(Bank bank, int money, int duration, int type) {

        if (!this.equals(null) && !bank.equals(null)) {
            boolean canCreate = true;
            for (Customer tempCustomer : allCustomers) {
                if (this.equals(tempCustomer)) canCreate = false;
            }
            if (!canCreate) {
                canCreate = true;
                for (Account account : allActiveAccounts) {
                    if (account.getBank().equals(bank)) canCreate = false;
                }
            }
            if (canCreate) {
                if (this.moneyInSafe >= money) {
                    int interest;
                    switch (type) {
                        case 1:
                            interest = 10;
                            break;
                        case 2:
                            interest = 30;
                            break;
                        case 3:
                            interest = 50;
                            break;
                        default:
                            interest = 0;
                            break;
                    }
                    totalNumberOfAccountsCreated++;
                    this.setMoneyInSafe(this.getMoneyInSafe() - money);
                    allActiveAccounts.add(new Account(bank, this, totalNumberOfAccountsCreated, money, duration, interest));

                } else System.out.println("Boro baba pool nadari!");
            }
        }
    }

    public void leaveAccount(int accountId) {
        Account selectedAccount = getAccountWithId(accountId);
        if (selectedAccount != null) {
            setMoneyInSafe(getMoneyInSafe() + selectedAccount.getAmountOfMoneyForLeaving());
            allActiveAccounts.remove(selectedAccount);
            Account.deleteAccount(selectedAccount);
        } else System.out.println("Chizi zadi?!");
    }

    public boolean canPayLoan(double amount) {
        return moneyInSafe >= amount;
    }

    public double getMoneyInSafe() {
        return this.moneyInSafe;
    }

    public void setMoneyInSafe(double moneyInSafe) {
        this.moneyInSafe = moneyInSafe;
    }

    public void getLoan(int duration, int interest, int money) {
        if (canGetLoan()) {
            new Loan(this, duration, interest, money);
            setMoneyInSafe(getMoneyInSafe() + money);
        } else System.out.println("To yeki kheyli vazet bade!");
    }

    public void payLoan(double amount) {
        if (canPayLoan(amount)) {
            setMoneyInSafe(getMoneyInSafe() - amount);
        }
    }

    public boolean canGetLoan() {
        return getNegativeScore() < 5;
    }

    public int getNegativeScore() {
        return negativeScore;
    }

    public void addNegativeScore() {
        negativeScore++;
    }

    public boolean hasActiveAccountInBank(Bank bank) {
        if (bank != null) for (Account account : allActiveAccounts) {
            if (account.getBank().equals(bank)) return true;
        }
        return false;
    }

    private Account getAccountWithId(int id) {
        if (id != 0) for (Account account : allActiveAccounts) {
            if (account.getId() == id) return account;
        }
        return null;
    }
}

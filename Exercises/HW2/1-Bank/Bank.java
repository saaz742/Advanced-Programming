//package com.company;

import java.util.ArrayList;


public class Bank {
    private static final ArrayList<Bank> allBanks = new ArrayList<Bank>();
    private final String name;

    public Bank(String name) {
        this.name = name;
        if (!isThereBankWithName(name)) allBanks.add(this);
    }

    public static Bank getBankWithName(String name) {
        if (name != null) for (Bank bank : allBanks) {
            if (bank.name.equals(name)) return bank;
        }
        return null;
    }

    public static boolean isThereBankWithName(String name) {
        if (name != null) for (Bank bank : allBanks) {
            if (bank.name.equals(name)) return true;
        }
        return false;
    }

    public static int getAccountInterestFromName(String type) {
        int interest;
        if (type.equals(null)) interest = 0;
        else switch (type) {
            case "VIZHE":
                interest = 3;
                break;
            case "BOLAN":
                interest = 2;
                break;
            case "KOOTAH":
                interest = 1;
                break;
            default:
                interest = 0;
                break;
        }
        return interest;
    }

}

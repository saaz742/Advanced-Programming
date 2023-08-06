//package com.company;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static Matcher getMatcher(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        matcher.find();
        return matcher;
    }

    private static void passMonth() {
        Loan.passMonth();
        Account.passMonth();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;
        Customer newCustomer = null;
        Customer customer = null;
        Bank newBank = null;

        final String[] regex = {"Add a customer with name((\\s\\S+)+) and ([-|+]?\\d+(\\.\\d+)?) unit initial money\\.", "Create bank((\\s\\S+)+)\\.", "Create a (KOOTAH|BOLAN|VIZHE) account for((\\s\\S+)+) in((\\s\\S+)+), with duration ([-|+]?\\d+(\\.\\d+)?) and initial deposit of ([-|+]?\\d+(\\.\\d+)?)\\.", "Pay a ([-|+]?\\d+(\\.\\d+)?) unit loan with %([-|+]?\\d+(\\.\\d+)?) interest and (6|12) payments from((\\s\\S+)+) to((\\s\\S+)+)\\.", "Pass time by (\\d+) unit\\.", "Print((\\s\\S+)+)'s GAVSANDOOGH money\\.", "Print((\\s\\S+)+)'s NOMRE MANFI count\\.", "Give((\\s\\S+)+)'s money out of his account number ([-|+]?\\d+(\\.\\d+)?)\\.", "Does((\\s\\S+)+) have active account in((\\s\\S+)+)\\?"};

        while (!(input = scanner.nextLine()).equals("Base dige, berid khonehatoon.")) {
            customer = newCustomer;
            if (input.matches(regex[0])) {
                newCustomer = new Customer(getMatcher(input, regex[0]).group(1), Double.parseDouble(getMatcher(input, regex[0]).group(3)));
            }
            if (input.matches(regex[1])) {
                newBank = new Bank(getMatcher(input, regex[1]).group(1));
            }
            if (input.matches(regex[2])) {
                if (newCustomer != null) {
                    customer = newCustomer.getCustomerByName(getMatcher(input, regex[2]).group(2));
                    if (Bank.isThereBankWithName(getMatcher(input, regex[2]).group(4)) && customer != null) {
                        int duration = Integer.parseInt(getMatcher(input, regex[2]).group(6));
                        int money = Integer.parseInt(getMatcher(input, regex[2]).group(8));
                        customer.createNewAccount(Bank.getBankWithName(getMatcher(input, regex[2]).group(4)), money, duration, Bank.getAccountInterestFromName(getMatcher(input, regex[2]).group(1)));
                    } else {
                        System.out.println("In dige banke koodoom keshvarie?");
                    }
                }
            }
            if (input.matches(regex[3])) {
                if (newCustomer != null) customer = newCustomer.getCustomerByName(getMatcher(input, regex[3]).group(8));
                else customer = null;
                if (customer != null) {
                    if (Bank.isThereBankWithName(getMatcher(input, regex[3]).group(6)) && customer != null) {
                        customer.getLoan(Integer.parseInt(getMatcher(input, regex[3]).group(5)), Integer.parseInt(getMatcher(input, regex[3]).group(3)), Integer.parseInt(getMatcher(input, regex[3]).group(1)));
                    } else {
                        System.out.println("Gerefti maro nesfe shabi?");
                    }
                }
            }
            if (input.matches(regex[4])) {
                for (int j = 0; j < Integer.parseInt(getMatcher(input, regex[4]).group(1)); j++) {
                    passMonth();
                }
            }
            if (input.matches(regex[5])) {
                if (newCustomer != null) customer = newCustomer.getCustomerByName(getMatcher(input, regex[5]).group(1));
                else customer = null;
                if (customer != null) {
                    System.out.println((int) (customer.getMoneyInSafe()));
                }
            }
            if (input.matches(regex[6])) {
                if (newCustomer != null) customer = newCustomer.getCustomerByName(getMatcher(input, regex[6]).group(1));
                else customer = null;
                if (customer != null) {
                    System.out.println(customer.getNegativeScore());
                }
            }
            if (input.matches(regex[7])) {
                if (newCustomer != null) customer = newCustomer.getCustomerByName(getMatcher(input, regex[7]).group(1));
                else customer = null;
                if (customer != null) {
                    customer.leaveAccount(Integer.parseInt(getMatcher(input, regex[7]).group(3)));
                } else System.out.println("Chizi zadi?!");
            }
            if (input.matches(regex[8])) {
                if (newCustomer != null) customer = newCustomer.getCustomerByName(getMatcher(input, regex[8]).group(1));
                else customer = null;
                if (customer != null) {
                    if (Bank.isThereBankWithName(getMatcher(input, regex[8]).group(3))) {
                        if (customer.hasActiveAccountInBank(Bank.getBankWithName(getMatcher(input, regex[8]).group(3)))) {
                            System.out.println("yes");
                        } else {
                            System.out.println("no");
                        }
                    } else System.out.println("no");
                }

            }
        }
    }
}

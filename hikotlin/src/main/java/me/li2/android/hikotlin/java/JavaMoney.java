package me.li2.android.hikotlin.java;

import me.li2.android.hikotlin.kotlin.Money;

public class JavaMoney {
    private int amount;
    private String currency;

    public JavaMoney(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public int getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    /* tip: psvm will autocomplete main function. */
    public static void main(String[] args) {
        /* tip: for consuming Kotlin from Java, IDE will provide the getter/setter method
         instead of properties. */
        Money money = new Money(100, "$");
        System.out.print("JavaMoney amount=" + money.getAmount());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "JavaMoney{" +
                "amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}

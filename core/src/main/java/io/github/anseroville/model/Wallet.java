package io.github.anseroville.model;

public class Wallet {
    private int money;

    public Wallet() {
        this.money = 0;
    }

    public void add(int amount) {
        if (amount <= 0) {
            return;
        }

        money += amount;
    }

    public boolean sub(int amount) {
        if (amount <= 0 || money < amount) {
            return false;
        }

        money -= amount;
        return true;
    }

    public int getMoney() {
        return money;
    }
}
package io.github.anseroville.model.inventory;

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

    public void sub(int amount) {
        if (amount <= 0 || money < amount) {
            return;
        }

        money -= amount;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getMoney() {
        return money;
    }
}
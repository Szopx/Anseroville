package io.github.anseroville.model.gambling;

import io.github.anseroville.model.Wallet;

import java.util.Random;

public class Machine {
    private final Wallet wallet;
    private final Random random;

    public Machine(Wallet wallet) {
        this.wallet = wallet;
        this.random = new Random();
    }

    public void play() {
        int currentMoney = wallet.getMoney();

        if (currentMoney > Integer.MAX_VALUE / 2) {
            throw new IllegalStateException("Player has too much money to use machine safely.");
        }

        int newMoney = random.nextInt(currentMoney * 2 + 1);

        changeWalletMoney(currentMoney, newMoney);

        System.out.println("Money changed from " + currentMoney + " to " + newMoney);
    }

    private void changeWalletMoney(int currentMoney, int newMoney) {
        int difference = newMoney - currentMoney;

        if (difference > 0) {
            wallet.add(difference);
        } else if (difference < 0) {
            wallet.sub(-difference);
        }
    }
}
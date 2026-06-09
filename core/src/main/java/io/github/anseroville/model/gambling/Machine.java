package io.github.anseroville.model.gambling;

import io.github.anseroville.model.inventory.Wallet;

import java.util.Random;

public class Machine {
    private static final int MAX_MONEY = 99999;

    private final Wallet wallet;
    private final Random random;

    public Machine(Wallet wallet) {
        this.wallet = wallet;
        this.random = new Random();
    }

    public void play() {
        int currentMoney = wallet.getMoney();

        int maxPossibleMoney = calculateMaxPossibleMoney(currentMoney);
        int newMoney = random.nextInt(maxPossibleMoney + 1);

        changeWalletMoney(currentMoney, newMoney);
    }

    private int calculateMaxPossibleMoney(int currentMoney) {
        long doubledMoney = (long) currentMoney * 2;

        if (doubledMoney > MAX_MONEY) {
            return MAX_MONEY;
        }

        return (int) doubledMoney;
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
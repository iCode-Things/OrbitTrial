package me.dan.orbittrial.user;

import lombok.experimental.UtilityClass;
import me.dan.pluginapi.user.User;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@UtilityClass
public class EconomyUtils {

    public void setBalance(User user, double balance) {
        EcoUserData ecoUserData = user.getUserData(EcoUserData.class);
        ecoUserData.setBalance(balance);
    }

    public boolean canAfford(User user, double amount) {
        return getBalance(user) >= amount;
    }

    public double getBalance(User user) {
        return user.getUserData(EcoUserData.class).getBalance();
    }

    public void pay(User user, User payee, double amount) {
        setBalance(user, getBalance(user) - amount);
        setBalance(payee, getBalance(payee) + amount);
    }

    public long getLastEarn(User user) {
        return user.getUserData(EcoUserData.class).getLastEarnTime();
    }

    public boolean isDueEarn(User user) {
        long time = System.currentTimeMillis();
        return time >= getLastEarn(user) + 60000;
    }

    public void setLastEarn(User user) {
        user.getUserData(EcoUserData.class).setLastEarnTime(System.currentTimeMillis());
    }


    public double earn(User user) {
        if (!isDueEarn(user)) {
            return 0;
        }
        Random random = ThreadLocalRandom.current();
        double min = 1;
        double max = 5;
        double amount = min + (max - min) * random.nextDouble();
        setBalance(user, getBalance(user) + amount);
        setLastEarn(user);
        return amount;
    }

}

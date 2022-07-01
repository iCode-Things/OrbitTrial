package me.dan.orbittrial.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.message.Message;

import java.util.List;

@Getter
@AllArgsConstructor
public enum EcoMessages implements Configuration, Message {

    PREFIX("prefix", "&8[&aEco&8] &2"),
    EARN_UNAVAILABLE("earn-unavailable", "{prefix}You cannot use /earn at this time!"),
    EARN("earn", "{prefix}You have earned ${amount}!"),
    PLAYER_NOT_FOUND("player-not-found", "{prefix}Could not find player {player}!"),
    NOT_A_NUMBER("not-a-number", "{prefix}{arg} is not a number!"),
    BALANCE("balance.view", "{prefix}{player}'s Balance: ${balance}"),
    NOT_ENOUGH_MONEY("not-enough-money", "{prefix}You cannot afford to do this!"),
    AMOUNT_TOO_LOW("amount-too-low", "{prefix}The amount must be above 0!"),
    SET_BALANCE("balance.set", "{prefix}Set {player}'s Balance to ${balance}!"),
    SET_BALANCE_NOTIFICATION("balance.set-notification", "{prefix}Your balance was set to ${balance}!"),
    GIVE_SENT("give.sent", "{prefix}You have paid {player} ${amount}!"),
    GIVE_RECEIVED("give.received", "{prefix}You have been paid ${amount} by {player}!"),
    ;

    private final String path;

    @Setter
    private Object value;

    @Override
    public String getPrefix() {
        return PREFIX.getString();
    }


    @Override
    public List<String> getStringList() {
        return Configuration.super.getStringList();
    }

    @Override
    public String getString() {
        return Configuration.super.getString();
    }
}

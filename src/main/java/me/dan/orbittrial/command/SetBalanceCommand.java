package me.dan.orbittrial.command;

import me.dan.orbittrial.configuration.EcoMessages;
import me.dan.orbittrial.user.EconomyUtils;
import me.dan.pluginapi.command.AbstractCommand;
import me.dan.pluginapi.command.CommandContext;
import me.dan.pluginapi.configurable.Messages;
import me.dan.pluginapi.math.NumberUtil;
import me.dan.pluginapi.message.Placeholder;
import me.dan.pluginapi.user.User;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.math.BigDecimal;
import java.util.Collections;

public class SetBalanceCommand extends AbstractCommand {

    public SetBalanceCommand() {
        super("setbalance");
        setAliases(Collections.singletonList("setbal"));
        setDescription("Set the balance of a user!");
        setPermission("orbittrial.setbalanace");
        setUsage("/setbalance {player} {balance}");
    }

    @Override
    public void perform(CommandContext commandContext) {
        if (commandContext.getArgs().length < 2) {
            Messages.USAGE.send(commandContext.getCommandSender(), new Placeholder("{usage}", getUsage()));
            return;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(commandContext.getArgs()[0]);

        if (!offlinePlayer.hasPlayedBefore()) {
            EcoMessages.PLAYER_NOT_FOUND.send(commandContext.getCommandSender(), new Placeholder("{player}", commandContext.getArgs()[0]));
            return;
        }

        User user = User.get(offlinePlayer.getUniqueId());

        double balance;

        try {
            balance = Double.parseDouble(commandContext.getArgs()[1]);
        } catch (NumberFormatException e) {
            EcoMessages.NOT_A_NUMBER.send(commandContext.getCommandSender(), new Placeholder("{arg}", commandContext.getArgs()[1]));
            return;
        }

        if (balance <= 0) {
            EcoMessages.AMOUNT_TOO_LOW.send(commandContext.getCommandSender());
            return;
        }

        EconomyUtils.setBalance(user, balance);

        String balanceFormatted = NumberUtil.formatBigDecimal(BigDecimal.valueOf(balance));

        EcoMessages.SET_BALANCE.send(commandContext.getCommandSender(), new Placeholder("{player}", offlinePlayer.getName()), new Placeholder("{balance}", balanceFormatted));

        if (offlinePlayer.isOnline()) {
            EcoMessages.SET_BALANCE_NOTIFICATION.send(offlinePlayer.getPlayer(), new Placeholder("{balance}", balanceFormatted));
        }

    }
}

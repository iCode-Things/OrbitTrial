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
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.Collections;

public class GiveCommand extends AbstractCommand {

    public GiveCommand() {
        super("give");
        setRequiresPlayer(true);
        setAliases(Collections.singletonList("pay"));
        setUsage("/give {player} {amount}");
        setDescription("Give another player money!");
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

        User target = User.get(offlinePlayer.getUniqueId());

        double amount;
        try {
            amount = Double.parseDouble(commandContext.getArgs()[1]);
        } catch (NumberFormatException e) {
            EcoMessages.NOT_A_NUMBER.send(commandContext.getCommandSender(), new Placeholder("{arg}", commandContext.getArgs()[1]));
            return;
        }

        if (amount <= 0) {
            EcoMessages.AMOUNT_TOO_LOW.send(commandContext.getCommandSender());
            return;
        }

        Player player = (Player) commandContext.getCommandSender();
        User sender = User.get(player.getUniqueId());

        if (!EconomyUtils.canAfford(sender, amount)) {
            EcoMessages.NOT_ENOUGH_MONEY.send(player);
            return;
        }

        EconomyUtils.pay(sender, target, amount);

        String formatted = NumberUtil.formatBigDecimal(BigDecimal.valueOf(amount));

        EcoMessages.GIVE_SENT.send(player, new Placeholder("{player}", offlinePlayer.getName()), new Placeholder("{amount}", formatted));
        if (offlinePlayer.isOnline()) {
            EcoMessages.GIVE_RECEIVED.send(offlinePlayer.getPlayer(), new Placeholder("{player}", player.getName()), new Placeholder("{amount}", formatted));
        }

    }
}

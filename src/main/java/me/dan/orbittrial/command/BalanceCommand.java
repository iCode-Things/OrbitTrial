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

public class BalanceCommand extends AbstractCommand {

    public BalanceCommand() {
        super("balance");
        setAliases(Collections.singletonList("bal"));
        setDescription("View yours or someone else balance!");
        setUsage("/balance {player}");
    }

    @Override
    public void perform(CommandContext commandContext) {
        User target;
        String name;
        if (commandContext.getArgs().length == 0) {
            if (!(commandContext.getCommandSender() instanceof Player)) {
                Messages.USAGE.send(commandContext.getCommandSender(), new Placeholder("{usage}", getUsage()));
                return;
            }
            Player player = (Player) commandContext.getCommandSender();
            name = player.getName();
            target = User.get(player.getUniqueId());
        } else {
            String targetString = commandContext.getArgs()[0];
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(targetString);
            if (!offlinePlayer.hasPlayedBefore()) {
                EcoMessages.PLAYER_NOT_FOUND.send(commandContext.getCommandSender(), new Placeholder("{player}", targetString));
                return;
            }
            target = User.get(offlinePlayer.getUniqueId());
            name = offlinePlayer.getName();
        }

        double balance = EconomyUtils.getBalance(target);
        EcoMessages.BALANCE.send(commandContext.getCommandSender(), new Placeholder("{player}", name), new Placeholder("{balance}", NumberUtil.formatBigDecimal(BigDecimal.valueOf(balance))));
    }
}

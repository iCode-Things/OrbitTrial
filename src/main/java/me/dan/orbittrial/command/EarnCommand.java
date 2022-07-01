package me.dan.orbittrial.command;

import me.dan.orbittrial.OrbitTrial;
import me.dan.orbittrial.configuration.EcoMessages;
import me.dan.orbittrial.user.EconomyUtils;
import me.dan.pluginapi.command.AbstractCommand;
import me.dan.pluginapi.command.CommandContext;
import me.dan.pluginapi.math.NumberUtil;
import me.dan.pluginapi.message.Placeholder;
import me.dan.pluginapi.user.User;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

public class EarnCommand extends AbstractCommand {

    public EarnCommand() {
        super("earn");
        setUsage("/earn");
        setRequiresPlayer(true);
        setDescription("Earn free money!");
    }

    @Override
    public void perform(CommandContext commandContext) {
        Player player = (Player) commandContext.getCommandSender();
        User user = User.get(player.getUniqueId());
        if (!EconomyUtils.isDueEarn(user)) {
            EcoMessages.EARN_UNAVAILABLE.send(player);
            return;
        }

        double amount = EconomyUtils.earn(user);
        EcoMessages.EARN.send(player, new Placeholder("{amount}", NumberUtil.formatBigDecimal(BigDecimal.valueOf(amount))));
    }
}

package me.dan.orbittrial;

import me.dan.orbittrial.command.BalanceCommand;
import me.dan.orbittrial.command.EarnCommand;
import me.dan.orbittrial.command.GiveCommand;
import me.dan.orbittrial.command.SetBalanceCommand;
import me.dan.orbittrial.configuration.EcoMessages;
import me.dan.pluginapi.configuration.Configuration;
import me.dan.pluginapi.file.YamlFile;
import me.dan.pluginapi.plugin.CustomPlugin;

public final class OrbitTrial extends CustomPlugin {

    @Override
    public void enable() {
        Configuration.loadConfig(new YamlFile("messages.yml", this.getDataFolder().getAbsolutePath(), null, this), EcoMessages.values());
        registerCommands(new BalanceCommand(), new EarnCommand(), new GiveCommand(), new SetBalanceCommand());
    }

    @Override
    public void disable() {
        // Plugin shutdown logic
    }
}

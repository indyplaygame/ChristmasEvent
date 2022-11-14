package indy.christmasevent.main;

import indy.christmasevent.commands.Commands;
import indy.christmasevent.commands.startCommand;
import indy.christmasevent.commands.stopCommand;
import indy.christmasevent.events.Events;
import indy.christmasevent.loot.elfLoot;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);

        Commands commands = new Commands();
        commands.registerSubcommand("start", new startCommand());
        commands.registerSubcommand("stop", new stopCommand());
        getCommand("event").setExecutor(commands);

        getServer().getPluginManager().registerEvents(new Events(), this);

        saveConfig();
        reloadConfig();

        elfLoot.getDrops();
    }

    @Override
    public void onDisable() {

    }
}

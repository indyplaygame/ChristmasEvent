package indy.christmasevent.main;

import indy.christmasevent.commands.*;
import indy.christmasevent.events.Events;
import indy.christmasevent.gui.ExchangeGUI;
import indy.christmasevent.gui.exchangeSelectedGUI;
import indy.christmasevent.loot.elfLoot;
import indy.christmasevent.mobs.Elf;
import indy.christmasevent.tabcompletion.tabCompletion;
import indy.christmasevent.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    private static Economy economy = null;

    @Override
    public void onEnable() {

        if (!setupEconomy()) {
            Logger.getLogger("Minecraft").severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getConfig().options().copyHeader(true);
        getConfig().options().copyDefaults(true);

        Commands commands = new Commands();
        commands.registerSubcommand("start", new startCommand());
        commands.registerSubcommand("stop", new stopCommand());
        commands.registerSubcommand("exchange", new exchangeCommand());
        commands.registerSubcommand("spawn", new spawnCommand());
        commands.registerSubcommand("give", new giveCommand());
        getCommand("christmasevent").setExecutor(commands);
        getCommand("christmasevent").setTabCompleter(new tabCompletion());

        getServer().getPluginManager().registerEvents(new Events(), this);
        getServer().getPluginManager().registerEvents(new ExchangeGUI(), this);
        getServer().getPluginManager().registerEvents(new exchangeSelectedGUI(), this);

        saveConfig();
        reloadConfig();

        elfLoot.getDrops();
        Events.mapTypes();

        Events.scheduledEvent(this, new Runnable() {
            @Override
            public void run() {
                    Elf.spawnElves(Utils.getString("Elves.spawn-area.world"), Utils.getInt("Elves.elves-amount"));
                }

            }, getConfig().getInt("Elves.auto-spawn.time.hour"), getConfig().getInt("Elves.auto-spawn.time.minute"));
    }

    @Override
    public void onDisable() {
        stopCommand.stopEvent();
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public static Economy getEconomy() {
        return economy;
    }
}

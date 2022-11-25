package indy.christmasevent.commands;

import indy.christmasevent.bossbar.ProgressBar;
import indy.christmasevent.mobs.Elf;
import indy.christmasevent.utils.Utils;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class spawnCommand implements CommandExecutor {
    BossBar bar = ProgressBar.getEventBar();

    public FileConfiguration getConfig() {
        return Utils.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if(sender.hasPermission("christmasevent.spawn")) {
            if(getConfig().getBoolean("Event.started")) {
                Elf.spawnElves(Utils.getString("Elves.spawn-area.world"), Utils.getInt("Elves.elves-amount"));
            } else {
                sender.sendMessage(Utils.getMessage("Messages.event-not-active-message"));
            }
        }
        return false;
    }
}

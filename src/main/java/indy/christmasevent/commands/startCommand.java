package indy.christmasevent.commands;

import indy.christmasevent.bossbar.ProgressBar;
import indy.christmasevent.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class startCommand implements CommandExecutor {

    BossBar bar = ProgressBar.getEventBar();

    public FileConfiguration getConfig() {
        return Utils.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if(!getConfig().getBoolean("Event.started")) {
            getConfig().set("Event.started", true);
            Utils.plugin().saveConfig();
            Utils.plugin().reloadConfig();

            bar.setVisible(true);
            bar.setProgress(1);

            for(Player player : Bukkit.getOnlinePlayers()) {
                if(!bar.getPlayers().contains(player)) {
                    bar.addPlayer(player);
                }
            }

            sender.sendMessage(Utils.getMessage("Messages.event-start-message"));
        } else {
            sender.sendMessage(Utils.getMessage("Messages.event-started-message"));
        }
        return false;
    }
}

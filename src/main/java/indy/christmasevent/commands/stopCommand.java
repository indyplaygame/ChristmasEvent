package indy.christmasevent.commands;

import indy.christmasevent.bossbar.ProgressBar;
import indy.christmasevent.utils.Utils;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static indy.christmasevent.utils.Utils.getConfig;

public class stopCommand implements CommandExecutor {
    BossBar bar = ProgressBar.getEventBar();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if(sender.hasPermission("christmasevent.stop")) {
            if(getConfig().getBoolean("Event.started")) {
                stopEvent();
                sender.sendMessage(Utils.getMessage("Messages.event-stop-message"));
            } else {
                sender.sendMessage(Utils.getMessage("Messages.event-not-active-message"));
            }
        }
        return false;
    }

    public static void stopEvent() {
        if(getConfig().getBoolean("Event.started")) {
            getConfig().set("Event.started", false);
            Utils.plugin().saveConfig();
            Utils.plugin().reloadConfig();

            ProgressBar.getEventBar().setVisible(false);
        }
    }
}

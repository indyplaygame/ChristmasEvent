package indy.christmasevent.commands;

import indy.christmasevent.bossbar.ProgressBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static indy.christmasevent.utils.Utils.*;

public class stopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if(sender.hasPermission("christmasevent.stop")) {
            if(getConfig().getBoolean("Event.started")) {
                stopEvent();
                sender.sendMessage(getMessage("Messages.event-stop-message"));
            } else {
                sender.sendMessage(getMessage("Messages.event-not-active-message"));
            }
        }
        return false;
    }

    public static void stopEvent() {
        if(getBoolean("Event.started")) {
            getConfig().set("Event.started", false);
            plugin().saveConfig();
            plugin().reloadConfig();

            ProgressBar.getEventBar().setVisible(false);
        }
    }
}

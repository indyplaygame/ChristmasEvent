package indy.christmasevent.commands;

import indy.christmasevent.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class stopCommand implements CommandExecutor {

    public FileConfiguration getConfig() {
        return Utils.getConfig();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if(getConfig().getBoolean("Event.started")) {
            getConfig().set("Event.started", false);
            Utils.plugin().saveConfig();
            Utils.plugin().reloadConfig();
            
            sender.sendMessage(Utils.getMessage("Messages.event-stop-message"));
        } else {
            sender.sendMessage(Utils.getMessage("Messages.event-not-active-message"));
        }
        return false;
    }
}

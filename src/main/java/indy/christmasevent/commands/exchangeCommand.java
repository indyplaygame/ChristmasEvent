package indy.christmasevent.commands;

import indy.christmasevent.gui.ExchangeGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static indy.christmasevent.utils.Utils.getConfig;
import static indy.christmasevent.utils.Utils.getMessage;

public class exchangeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if(sender.hasPermission("christmasevent.exchange")) {
            if(getConfig().getBoolean("Event.started")) {
                if(sender instanceof Player) {
                    ((Player) sender).openInventory(ExchangeGUI.getInventory((Player) sender));
                } else {
                    sender.sendMessage(getMessage("Messages.non-player-executor"));
                }
            } else {
                sender.sendMessage(getMessage("Messages.event-not-active-message"));
            }
        }
        return false;
    }
}

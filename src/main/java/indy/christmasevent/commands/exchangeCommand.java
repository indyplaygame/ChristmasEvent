package indy.christmasevent.commands;

import indy.christmasevent.gui.ExchangeGUI;
import indy.christmasevent.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import static indy.christmasevent.utils.Utils.getConfig;

public class exchangeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if(sender.hasPermission("christmasevent.exchange")) {
            if(getConfig().getBoolean("Event.started")) {
                if(sender instanceof Player) {
                    ((Player) sender).openInventory(ExchangeGUI.getInventory((Player) sender));
                } else {
                    sender.sendMessage(Utils.getMessage("Messages.non-player-executor"));
                }
            } else {
                sender.sendMessage(Utils.getMessage("Messages.event-not-active-message"));
            }
        }
        return false;
    }
}

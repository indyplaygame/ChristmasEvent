package indy.christmasevent.commands;

import indy.christmasevent.items.Items;
import indy.christmasevent.loot.elfLoot;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static indy.christmasevent.utils.Utils.getList;
import static indy.christmasevent.utils.Utils.getMessage;

public class giveCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if(sender.hasPermission("christmasevent.give")) {
            if(args.length == 0) {
                sender.sendMessage(getMessage("Messages.give-missing-player"));
            } else if(!Bukkit.getOfflinePlayer(args[0]).isOnline()) {
                sender.sendMessage(getMessage("Messages.give-player-offline").replace("%player%", args[0]));
            } else {
                if(args.length == 1) {
                    sender.sendMessage(getMessage("Messages.give-missing-item"));
                } else if(!getList("Elves.drops.loot").contains(args[1])) {
                    sender.sendMessage(getMessage("Messages.give-wrong-item").replace("%item-name%", args[1]));
                } else {
                    int amount = 0;
                    Player player = Bukkit.getPlayer(args[0]);
                    ItemStack item = elfLoot.getItem(args[1]);

                    if(args.length == 2) amount = 1;
                    else amount = Integer.valueOf(args[2].replaceAll("[^0-9]", ""));

                    item.setAmount(amount);

                    player.getInventory().addItem(item);
                    sender.sendMessage(getMessage("Messages.give-success")
                            .replace("%amount%", String.valueOf(amount))
                            .replace("%item-name%", getMessage("Elves.drops.items." + args[1] + ".name"))
                            .replace("%player%", player.getName()));
                }
            }
        }
        return false;
    }
}

package indy.christmasevent.commands;

import indy.christmasevent.mobs.Elf;
import indy.christmasevent.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static indy.christmasevent.utils.Utils.getBoolean;

public class spawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if(sender.hasPermission("christmasevent.spawn")) {
            if(getBoolean("Event.started")) {
                Elf.spawnElves(Utils.getString("Elves.spawn-area.world"), Utils.getInt("Elves.elves-amount"));
            } else {
                sender.sendMessage(Utils.getMessage("Messages.event-not-active-message"));
            }
        }
        return false;
    }
}

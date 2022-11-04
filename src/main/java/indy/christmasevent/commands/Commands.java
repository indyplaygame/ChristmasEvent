package indy.christmasevent.commands;

import indy.christmasevent.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class Commands implements CommandExecutor {

    private Map<String, CommandExecutor> commands = new HashMap<>();

    public void registerSubcommand(String command, CommandExecutor commandExecutor) {
        commands.put(command, commandExecutor);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(Utils.getMessage("Messages.missing-argument"));
        } else if (!commands.containsKey(args[0].toLowerCase())) {
            sender.sendMessage(Utils.getMessage("Messages.wrong-argument"));
        } else {
            arg = args[0];
            String[] newArgs = new String[args.length - 1];
            System.arraycopy(args, 1, newArgs, 0, newArgs.length);
            commands.get(args[0]).onCommand(sender, command, arg, newArgs);
        }
        return false;
    }
}

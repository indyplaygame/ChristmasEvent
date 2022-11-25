package indy.christmasevent.tabcompletion;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static indy.christmasevent.utils.Utils.getList;

public class tabCompletion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String arg, String[] args) {
        List<String> completion = new ArrayList<>();

        if(args.length == 1) {
            completion.add("start");
            completion.add("stop");
            completion.add("exchange");
            completion.add("spawn");
            completion.add("give");
        } else if (args[0].equalsIgnoreCase("give")) {
            if(args.length == 2) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    completion.add(player.getName());
                }
            } else if(args.length == 3){
                completion.addAll(getList("Elves.drops.loot"));
            }
        }

        return completion;
    }
}

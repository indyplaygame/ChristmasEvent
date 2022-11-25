package indy.christmasevent.commands;

import hex.util.Skull;
import indy.christmasevent.bossbar.ProgressBar;
import indy.christmasevent.items.Items;
import indy.christmasevent.mobs.Elf;
import indy.christmasevent.utils.Utils;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static indy.christmasevent.utils.Utils.getConfig;

public class startCommand implements CommandExecutor {

    BossBar bar = ProgressBar.getEventBar();

    public FileConfiguration getConfig() {
        return Utils.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
        if(sender.hasPermission("christmasevent.start")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
            }
            if (!getConfig().getBoolean("Event.started")) {
                getConfig().set("Event.started", true);
                Utils.plugin().saveConfig();
                Utils.plugin().reloadConfig();

                bar.setVisible(true);
                bar.setProgress(1);

                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (!bar.getPlayers().contains(player)) {
                        bar.addPlayer(player);
                    }
                }
                Elf.spawnElves(Utils.getString("Elves.spawn-area.world"), Utils.getInt("Elves.elves-amount"));

                sender.sendMessage(Utils.getMessage("Messages.event-start-message"));
            } else {
                sender.sendMessage(Utils.getMessage("Messages.event-started-message"));
            }
        }
        return false;
    }
}

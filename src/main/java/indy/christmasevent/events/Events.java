package indy.christmasevent.events;

import indy.christmasevent.bossbar.ProgressBar;
import indy.christmasevent.commands.startCommand;
import indy.christmasevent.items.Items;
import indy.christmasevent.loot.elfLoot;
import indy.christmasevent.mobs.Elf;
import indy.christmasevent.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Events implements Listener {

    BossBar bar = ProgressBar.getEventBar();

    public static int scheduledEvent(Plugin plugin, Runnable task, int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        long currentTime = System.currentTimeMillis();
        long timeOffset;
        long ticks;

        if(calendar.get(Calendar.HOUR_OF_DAY) >= hour && calendar.get(Calendar.MINUTE) >= min) {
            calendar.add(Calendar.DATE, 1);
        }

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        timeOffset = calendar.getTimeInMillis() - currentTime;
        ticks = timeOffset / 50L;

        return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, ticks, 1728000L);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if(Utils.getConfig().getBoolean("Event.started")) {
            if (!bar.getPlayers().contains(e.getPlayer())) {
                bar.addPlayer(e.getPlayer());
            }
        }
    }
    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if(e.getEntity().getKiller() == null) {
            return;
        }
        if (!(((CraftEntity) e.getEntity()).getHandle() instanceof Elf)) {
            return;
        }
        LivingEntity entity = e.getEntity();
        LootContext.Builder builder = new LootContext.Builder(entity.getLocation());
        Player killer = e.getEntity().getKiller();

        builder.lootedEntity(entity);
        builder.killer(killer);

        LootContext lootContext = builder.build();

        elfLoot loot = new elfLoot();
        ArrayList<ItemStack> items = (ArrayList<ItemStack>) loot.populateLoot(new Random(), lootContext);

        e.getDrops().clear();

        for(ItemStack item : items) {
            if(Utils.getConfig().getBoolean("Elves.add-drops-to-player-inventory")) {
                killer.getInventory().addItem(item);
            } else {
                e.getDrops().add(item);
            }
        }

        killer.sendMessage(Utils.getMessage("Messages.elf-kill-message"));
    }
}

package indy.christmasevent.events;

import indy.christmasevent.bossbar.ProgressBar;
import indy.christmasevent.commands.startCommand;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.util.Calendar;

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
        if(!bar.getPlayers().contains(e.getPlayer())) {
            bar.addPlayer(e.getPlayer());
        }
    }
}

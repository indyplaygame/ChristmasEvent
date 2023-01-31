package indy.christmasevent.bossbar;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import static indy.christmasevent.utils.Utils.getMessage;

public class ProgressBar {

    static BossBar event_bar = create(getMessage("Event.bossbar-title"),
            getMessage("Event.bossbar-color"),
            getMessage("Event.bossbar-style"));

    public static BossBar getEventBar() {
        return event_bar;
    }

    public static BossBar create(String title, String color, String style) {
        return Bukkit.getServer().createBossBar(title, BarColor.valueOf(color), BarStyle.valueOf(style));
    }
}

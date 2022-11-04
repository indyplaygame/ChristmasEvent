package indy.christmasevent.bossbar;

import indy.christmasevent.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public class ProgressBar {

    static BossBar event_bar = create(Utils.getMessage("Event.bossbar-title"),
            Utils.getMessage("Event.bossbar-color"),
            Utils.getMessage("Event.bossbar-style"));

    public static BossBar getEventBar() {
        return event_bar;
    }

    public static BossBar create(String title, String color, String style) {
        return Bukkit.getServer().createBossBar(title, BarColor.valueOf(color), BarStyle.valueOf(style));
    }
}

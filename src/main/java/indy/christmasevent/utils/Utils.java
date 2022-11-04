package indy.christmasevent.utils;

import indy.christmasevent.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Utils {

    public static Plugin plugin() {
        return Main.getPlugin(Main.class);
    }

    public static FileConfiguration getConfig() {
        return plugin().getConfig();
    }

    public static String formatColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String getMessage(String path) {
        return formatColor(getConfig().getString(path));
    }
}

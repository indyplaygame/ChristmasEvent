package indy.christmasevent.utils;

import indy.christmasevent.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.Random;

public class Utils {

    public static Plugin plugin() {
        return Main.getPlugin(Main.class);
    }

    public static FileConfiguration getConfig() {
        return plugin().getConfig();
    }

    public static String getString(String path) {
        return formatColor(getConfig().getString(path));
    }

    public static int getInt(String path) {
        return getConfig().getInt(path);
    }

    public static String formatColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String getMessage(String path) {
        return formatColor(getConfig().getString(path));
    }

    public static double randomDouble(double min, double max) {
        return (new Random().nextDouble() * (max - min)) + min;
    }

    public static void sendDebugMessage(String message) {
        if(getConfig().getBoolean("DeveloperMode.enabled")) {
            System.out.println(message);
        }
    }
}

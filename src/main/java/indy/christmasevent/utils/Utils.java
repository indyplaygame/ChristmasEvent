package indy.christmasevent.utils;

import hex.util.Skull;
import indy.christmasevent.main.Main;
import net.minecraft.server.v1_16_R3.IChatBaseComponent;
import net.minecraft.server.v1_16_R3.PacketPlayOutTitle;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Utils {

    public static Plugin plugin() {
        return Main.getPlugin(Main.class);
    }

    public static FileConfiguration getConfig() {
        return plugin().getConfig();
    }

    public static String getString(String path) {
        return getConfig().getString(path);
    }

    public static int getInt(String path) {
        return getConfig().getInt(path);
    }

    public static double getDouble(String path) {
        return getConfig().getDouble(path);
    }

    public static List<String> getList(String path) {
        return (List<String>) getConfig().getList(path);
    }

    public static String formatColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String getMessage(String path) {
        return formatColor(getConfig().getString(path));
    }

    public static Boolean getBoolean(String path) {
        return getConfig().getBoolean(path);
    }

    public static double randomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max + 1);
    }

    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static double round(double num, int decimals) {
        return Math.round(num * Math.pow(10, decimals)) / Math.pow(10, decimals);
    }

    public static void sendDebugMessage(String message) {
        if(getConfig().getBoolean("DeveloperMode.enabled")) System.out.println(message);
    }

    public static ItemStack createItem(String material, String name, List<String> lore, int amount, String head_texture) {

        ItemStack item;

        if(material.equalsIgnoreCase("PLAYER_HEAD")) {
            item = Skull.getCustomSkull(head_texture);
        } else {
            item = new ItemStack(Material.valueOf(material), amount);
        }

        ItemMeta meta = item.getItemMeta();

        for(int i = 0; i < lore.size(); i++) {
            lore.set(i, Utils.formatColor(lore.get(i)));
        }

        meta.setDisplayName(name);
        meta.setLore(lore);

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createItem(String material, String head_texture, String color) {

        ItemStack item;

        if(material.equalsIgnoreCase("PLAYER_HEAD")) {
            item = Skull.getCustomSkull(head_texture);
        } else if(material.equalsIgnoreCase("LEATHER_HELMET")) {
            item = new ItemStack(Material.valueOf(material), 1);
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

            meta.setColor(ColorFromHex(color));

            item.setItemMeta(meta);
        } else {
            item = new ItemStack(Material.valueOf(material), 1);
        }

        return item;
    }

    public static ItemStack createItem(String material, String color) {

        ItemStack item = new ItemStack(Material.valueOf(material), 1);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();

        meta.setColor(ColorFromHex(color));

        item.setItemMeta(meta);

        return item;
    }

    public static void createItem(Inventory inventory, String material, String name, List<String> lore, int amount, int slot, String head_texture) {
        inventory.setItem(slot, createItem(material, name, lore, amount, head_texture));
    }

    public static List<String> formatStringElements(List<String> list, String oldValue, String newValue) {
        return list.stream().map(text -> text.replace(oldValue, newValue)).collect(Collectors.toList());
    }

    public static Color ColorFromHex(String hex) {

        hex = hex.replace("#", "");

        Color color = Color.fromRGB(
            Integer.valueOf(hex.substring(0, 2), 16),
            Integer.valueOf(hex.substring(2, 4), 16),
            Integer.valueOf(hex.substring(4, 6), 16));

        return color;
    }

    public static void showTitle(Player player, String type, String text, int fadeIn, int stay, int fadeOut) {
        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.valueOf(type),
                IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), fadeIn, stay, fadeOut);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(title);
    }
}

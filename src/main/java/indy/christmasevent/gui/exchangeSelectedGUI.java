package indy.christmasevent.gui;

import indy.christmasevent.main.Main;
import indy.christmasevent.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static indy.christmasevent.utils.Utils.*;
import static indy.christmasevent.utils.Utils.getString;

public class exchangeSelectedGUI implements Listener {

    private static Economy economy = Main.getEconomy();
    private static Inventory inventory = null;

    public static Map<Integer, String> items = new HashMap<>();

    public static Inventory subGUI(Player player) {
        inventory = Bukkit.createInventory(null, 9 * getInt("ExchangeGUI.rows"), getMessage("ExchangeGUI.exchange-selected.title"));

        double value = ExchangeGUI.calculateValue(player);
        int slot = 0;

        ExchangeGUI.addItem(inventory, getInt("ExchangeGUI.items.exchange-selected-cancel.slot"), Utils.createItem(
                getString("ExchangeGUI.items.exchange-selected-cancel.material"),
                getMessage("ExchangeGUI.items.exchange-selected-cancel.name"),
                replaceAll(getList("ExchangeGUI.items.exchange-selected-cancel.lore"), "%value%", String.valueOf(value)),
                getInt("ExchangeGUI.items.exchange-selected-cancel.amount"),
                getString("ExchangeGUI.items.exchange-selected-cancel.head-texture")));
        ExchangeGUI.addItem(inventory, getInt("ExchangeGUI.items.exchange-selected-back.slot"), Utils.createItem(
                getString("ExchangeGUI.items.exchange-selected-back.material"),
                getMessage("ExchangeGUI.items.exchange-selected-back.name"),
                replaceAll(getList("ExchangeGUI.items.exchange-selected-back.lore"), "%value%", String.valueOf(value)),
                getInt("ExchangeGUI.items.exchange-selected-back.amount"),
                getString("ExchangeGUI.items.exchange-selected-back.head-texture")));

        if(getBoolean("ExchangeGUI.items.exchange-selected-empty-slots.enabled")) {
            for (int s : (List<Integer>) getConfig().getList("ExchangeGUI.items.exchange-selected-empty-slots.slots")) {
                Utils.createItem(inventory, getString("ExchangeGUI.items.exchange-selected-empty-slots.material"),
                        getMessage("ExchangeGUI.items.exchange-selected-empty-slots.name"),
                        getList("ExchangeGUI.items.exchange-selected-empty-slots.lore"),
                        getInt("ExchangeGUI.items.exchange-selected-empty-slots.amount"), s,
                        getString("ExchangeGUI.items.exchange-selected-empty-slots.head-texture"));
            }
        }

        ItemStack item;
        ItemMeta meta;

        for(String type : getList("Elves.drops.loot")) {

            value = ExchangeGUI.calculateValue(player, type);
            item = Utils.createItem(
                    getString("ExchangeGUI.items.exchange-selected-item.material").replace("%material%", getString("Elves.drops.items." + type + ".material")),
                    getMessage("ExchangeGUI.items.exchange-selected-item.name").replace("%name%", getMessage("Elves.drops.items." + type + ".name")),
                    formatStringElements(formatStringElements(getList("ExchangeGUI.items.exchange-selected-item.lore"), "%value%", String.valueOf(value)),
                            "%name%", getMessage("Elves.drops.items." + type + ".name")),
                    getInt("Elves.drops.items." + type + ".amount"),
                    getString("ExchangeGUI.items.exchange-selected-item.head-texture").replace("%head_texture%", getString("Elves.drops.items." + type + ".head-texture")));

            meta = item.getItemMeta();
            meta.setCustomModelData(getInt("Elves.drops.items." + type + ".custom-model-data"));

            sendDebugMessage("" + getInt("Elves.drops.items." + type + ".custom-model-data"));

            item.setItemMeta(meta);

            ExchangeGUI.addItem(inventory, item);
            items.put(item.getItemMeta().getCustomModelData(), type);
        }

        return inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getInventory().equals(inventory)) return;

        e.setCancelled(true);

        ItemStack clickedItem = e.getCurrentItem();
        if(clickedItem == null || clickedItem.getType().isAir()) return;

        Player player = (Player) e.getWhoClicked();
        int slot = e.getRawSlot();

        if(slot == getInt("ExchangeGUI.items.exchange-selected-cancel.slot")) {
            player.closeInventory();
        } else if(slot == getInt("ExchangeGUI.items.exchange-selected-back.slot")) {
            player.openInventory(ExchangeGUI.mainGUI(player));
        } else {
            if(items.containsKey(clickedItem.getItemMeta().getCustomModelData())) {
                ExchangeGUI.exchangeGifts(player, items.get(clickedItem.getItemMeta().getCustomModelData()));
                sendDebugMessage(items.get(clickedItem.getItemMeta().getCustomModelData()));
            }
        }
    }
}

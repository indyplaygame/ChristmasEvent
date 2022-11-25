package indy.christmasevent.gui;

import indy.christmasevent.main.Main;
import indy.christmasevent.utils.Utils;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static indy.christmasevent.utils.Utils.*;

public class ExchangeGUI implements Listener {

    private static final Economy economy = Main.getEconomy();
    private static Inventory inventory = null;

    public ExchangeGUI() {
        inventory = Bukkit.createInventory(null, 9 * getInt("ExchangeGUI.rows"), getMessage("ExchangeGUI.title"));
    }

    public static Inventory getInventory(Player player) {
        initContents(player);
        return inventory;
    }

    public static void initContents(Player player) {
        inventory.clear();

        double value = calculateValue(player);

        sendDebugMessage("calculated gifts value for " + player.getName() + ": " + value);

        addItem(inventory, getInt("ExchangeGUI.items.gui-icon.slot"), Utils.createItem(
                getString("ExchangeGUI.items.gui-icon.material"),
                getMessage("ExchangeGUI.items.gui-icon.name"),
                formatStringElements(getList("ExchangeGUI.items.gui-icon.lore"), "%value%", String.valueOf(value)),
                getInt("ExchangeGUI.items.gui-icon.amount"),
                getString("ExchangeGUI.items.gui-icon.head-texture")));
        addItem(inventory, getInt("ExchangeGUI.items.exchange-selected.slot"), Utils.createItem(
                getString("ExchangeGUI.items.exchange-selected.material"),
                getMessage("ExchangeGUI.items.exchange-selected.name"),
                formatStringElements(getList("ExchangeGUI.items.exchange-selected.lore"), "%value%", String.valueOf(value)),
                getInt("ExchangeGUI.items.exchange-selected.amount"),
                getString("ExchangeGUI.items.exchange-selected.head-texture")));
        addItem(inventory, getInt("ExchangeGUI.items.exchange-all.slot"), Utils.createItem(
                getString("ExchangeGUI.items.exchange-all.material"),
                getMessage("ExchangeGUI.items.exchange-all.name"),
                formatStringElements(getList("ExchangeGUI.items.exchange-all.lore"), "%value%", String.valueOf(value)),
                getInt("ExchangeGUI.items.exchange-all.amount"),
                getString("ExchangeGUI.items.exchange-all.head-texture")));
        addItem(inventory, getInt("ExchangeGUI.items.exchange-cancel.slot"), Utils.createItem(
                getString("ExchangeGUI.items.exchange-cancel.material"),
                getMessage("ExchangeGUI.items.exchange-cancel.name"),
                formatStringElements(getList("ExchangeGUI.items.exchange-cancel.lore"), "%value%", String.valueOf(value)),
                getInt("ExchangeGUI.items.exchange-cancel.amount"),
                getString("ExchangeGUI.items.exchange-cancel.head-texture")));

        if(getBoolean("ExchangeGUI.items.empty-slots.enabled")) {
            for (int i = 0; i < inventory.getSize(); i++) {
                if (inventory.getItem(i) == null) {
                    Utils.createItem(inventory, getString("ExchangeGUI.items.empty-slots.material"),
                            getMessage("ExchangeGUI.items.empty-slots.name"),
                            formatStringElements(getList("ExchangeGUI.items.empty-slots.lore"), "%value%", String.valueOf(value)),
                            getInt("ExchangeGUI.items.empty-slots.amount"), i,
                            getString("ExchangeGUI.items.empty-slots.head-texture"));
                }
            }
        }
    }

    public static void addItem(Inventory inv, int slot, ItemStack item) {
        inv.setItem(slot, item);
    }

    public static void addItem(Inventory inv, ItemStack item) {
        inv.addItem(item);
    }

    public static double calculateValue(Player player) {
        double value = 0;

        for(String type : getList("Elves.drops.loot")) {
            value = value + calculateValue(player, type);
        }
        return value;
    }

    public static double calculateValue(Player player, String type) {
        double value = 0;

        for(ItemStack item : player.getInventory().getContents()) {
            if(item != null && item.getItemMeta().hasCustomModelData()) {
                if (item.getItemMeta().getCustomModelData() == getInt("Elves.drops.items." + type + ".custom-model-data")) {
                    value = value + (item.getAmount() * getDouble("Elves.drops.items." + type + ".value"));
                }
            }
        }
        return value;
    }

    public static void exchangeGifts(Player player) {
        for(String type : getList("Elves.drops.loot")) {
            exchangeGifts(player, type);
        }
    }

    public static void exchangeGifts(Player player, String type) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
            ItemStack item = player.getInventory().getItem(i);
            if (item != null && item.getItemMeta().hasCustomModelData()) {
                if (item.getItemMeta().getCustomModelData() == getInt("Elves.drops.items." + type + ".custom-model-data")) {
                    double value = item.getAmount() * getDouble("Elves.drops.items." + type + ".value");
                    EconomyResponse response = economy.depositPlayer(player, value);
                    if(response.transactionSuccess()) {
                        player.sendMessage(getMessage("Messages.gifts-exchange-message")
                                .replace("%value%", String.valueOf(value))
                                .replace("%item%", getMessage("Elves.drops.items." + type + ".name")));
                    }
                    player.getInventory().setItem(i, new ItemStack(Material.AIR));
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getInventory().equals(inventory))) return;
        e.setCancelled(true);

        ItemStack clickedItem = e.getCurrentItem();
        if(clickedItem == null || clickedItem.getType().isAir()) return;

        Player player = (Player) e.getWhoClicked();
        int slot = e.getRawSlot();

        if(slot == getInt("ExchangeGUI.items.exchange-cancel.slot")) {
            player.closeInventory();
        } else if(slot == getInt("ExchangeGUI.items.exchange-all.slot")) {
            exchangeGifts(player);
        } else if(slot == getInt("ExchangeGUI.items.exchange-selected.slot")) {
            player.openInventory(exchangeSelectedGUI.getInventory(player));
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryDragEvent e) {
        if (e.getInventory().equals(inventory)) e.setCancelled(true);
    }
}

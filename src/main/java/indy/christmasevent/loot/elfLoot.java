package indy.christmasevent.loot;

import indy.christmasevent.items.Items;
import indy.christmasevent.main.Main;
import indy.christmasevent.utils.Utils;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;

import java.util.*;

import static indy.christmasevent.utils.Utils.getConfig;

public class elfLoot implements LootTable {

    private Collection<ItemStack> items = new ArrayList<ItemStack>();
    private NamespacedKey key = new NamespacedKey(Main.getPlugin(Main.class), "elf_loot");
    private static List<String> dropslist = (List<String>) getConfig().getList("Elves.drops.loot");
    private static Map<String, ItemStack> drops = new HashMap<>();

    public static void getDrops() {
        for(String drop : dropslist) {
            drops.put(drop, Items.createGift(drop));
        }
    }

    @Override
    public Collection<ItemStack> populateLoot(Random random, LootContext lootContext) {

        double rnd = Utils.randomDouble(0, 100);
        double chance = 0;

        for(String drop : dropslist) {
            double oldChance = chance;
            chance = getConfig().getDouble("Elves.drops.items." + drop + ".chance")*100 + oldChance;
            if(oldChance < rnd && rnd <= chance) {
                items.add(drops.get(drop));
            }

            Utils.sendDebugMessage(drop + ": " + oldChance + " -> " + chance);
        }

        Utils.sendDebugMessage(String.valueOf(rnd));

        return items;
    }

    @Override
    public void fillInventory(Inventory inventory, Random random, LootContext lootContext) {}

    @Override
    public NamespacedKey getKey() {
        return key;
    }
}

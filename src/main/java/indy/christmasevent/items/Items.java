package indy.christmasevent.items;

import hex.util.Skull;
import indy.christmasevent.utils.Utils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Items {
    public static ItemStack createGift(String type) {
        ItemStack gift = Skull.getCustomSkull(Utils.getConfig().getString("Elves.drops.items." + type + ".head-texture"));
        ItemMeta meta =  gift.getItemMeta();
        List<String> lore = (List<String>) Utils.getConfig().getList("Elves.drops.items." + type + ".lore");
        for(int i = 0; i < lore.size(); i++) {
            lore.set(i, Utils.formatColor(lore.get(i)));
        }

        meta.setDisplayName(Utils.getString("Elves.drops.items." + type + ".name"));
        meta.setLore(lore);
        gift.setItemMeta(meta);
        gift.setAmount(Utils.getInt("Elves.drops.items." + type + ".amount"));

        return gift;
    }
}

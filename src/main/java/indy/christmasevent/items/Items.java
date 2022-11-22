package indy.christmasevent.items;

import hex.util.Skull;
import indy.christmasevent.utils.Utils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static indy.christmasevent.utils.Utils.*;

public class Items {
    public static ItemStack createGift(String type) {
        ItemStack gift;

        if(getString("Elves.drops.items." + type + ".material").equalsIgnoreCase("PLAYER_HEAD")) {
            gift = Skull.getCustomSkull(Utils.getConfig().getString("Elves.drops.items." + type + ".head-texture"));
        } else {
            gift = new ItemStack(Material.valueOf(getString("Elves.drops.items." + type + ".material")), getInt("Elves.drops.items." + type + ".amount"));
        }

        ItemMeta meta =  gift.getItemMeta();
        List<String> lore = (List<String>) Utils.getConfig().getList("Elves.drops.items." + type + ".lore");
        for(int i = 0; i < lore.size(); i++) {
            lore.set(i, Utils.formatColor(lore.get(i)));
        }

        meta.setDisplayName(getMessage("Elves.drops.items." + type + ".name"));
        meta.setCustomModelData(getInt("Elves.drops.items." + type + ".custom-model-data"));
        meta.setLore(lore);
        gift.setItemMeta(meta);
        gift.setAmount(getInt("Elves.drops.items." + type + ".amount"));

        return gift;
    }
}

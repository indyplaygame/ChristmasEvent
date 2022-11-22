package indy.christmasevent.mobs;

import indy.christmasevent.utils.Utils;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;

import java.util.ArrayList;

public class Elf extends EntityCreature {

    public Elf(Location location) {
        super(EntityTypes.ZOMBIE, ((CraftWorld) location.getWorld()).getHandle());

        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.setCustomName(new ChatComponentText(Utils.formatColor(Utils.getConfig().getString("Elves.name"))));
        this.setHealth(Utils.getConfig().getInt("Elves.health"));
        this.setBaby(true);

        this.setSlot(EnumItemSlot.HEAD, CraftItemStack.asNMSCopy(
                Utils.createItem(Utils.getString("Elves.armor.head.material"),
                Utils.getString("Elves.armor.head.head-texture"),
                Utils.getString("Elves.armor.head.color"))));
        this.setSlot(EnumItemSlot.CHEST, CraftItemStack.asNMSCopy(
                Utils.createItem(Utils.getString("Elves.armor.chestplate.material"), Utils.getString("Elves.armor.chestplate.color"))));
        this.setSlot(EnumItemSlot.LEGS, CraftItemStack.asNMSCopy(
                Utils.createItem(Utils.getString("Elves.armor.leggings.material"), Utils.getString("Elves.armor.leggings.color"))));
        this.setSlot(EnumItemSlot.FEET, CraftItemStack.asNMSCopy(
                Utils.createItem(Utils.getString("Elves.armor.boots.material"), Utils.getString("Elves.armor.boots.color"))));

        this.goalSelector.a(0, new PathfinderGoalAvoidTarget(this, EntityHuman.class,
                Utils.getConfig().getInt("Elves.player-avoid-distance"),
                Utils.getConfig().getDouble("Elves.player-avoid-speed"),
                Utils.getConfig().getDouble("Elves.player-avoid-speed")));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 2.0D));
    }

    public static void spawnElves(String world_name, int amount) {
        org.bukkit.World world = Bukkit.getWorld(world_name);
        WorldServer worldServer = ((CraftWorld) world).getHandle();

        int minX = Math.min(Utils.getInt("Elves.spawn-area.pos1.x"), Utils.getInt("Elves.spawn-area.pos2.x"));
        int maxX = Math.max(Utils.getInt("Elves.spawn-area.pos1.x"), Utils.getInt("Elves.spawn-area.pos2.x"));
        int minZ = Math.min(Utils.getInt("Elves.spawn-area.pos1.z"), Utils.getInt("Elves.spawn-area.pos2.z"));
        int maxZ = Math.max(Utils.getInt("Elves.spawn-area.pos1.z"), Utils.getInt("Elves.spawn-area.pos2.z"));

        Utils.sendDebugMessage("area: " + minX + ", " + maxX + ", " + minZ + ", " + maxZ);

        for(int i = 0; i < amount; i++) {
            int x = Utils.randomInt(minX, maxX);
            int z = Utils.randomInt(minZ, maxZ);
            int y = world.getHighestBlockYAt(x, z) + 1;
            Location location = new Location(world, x, y, z);
            Elf elf = new Elf(location);

            Utils.sendDebugMessage("elf no. " + i + ", loc: " + x + ", " + y + ", " + z);

            worldServer.addEntity(elf);
        }
    }
}

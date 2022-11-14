package indy.christmasevent.mobs;

import indy.christmasevent.utils.Utils;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

public class Elf extends EntityCreature {

    public Elf(Location location) {
        super(EntityTypes.ZOMBIE, ((CraftWorld) location.getWorld()).getHandle());

        this.setPosition(location.getX(), location.getY(), location.getZ());
        this.setCustomName(new ChatComponentText(Utils.formatColor(Utils.getConfig().getString("Elves.name"))));
        this.setHealth(Utils.getConfig().getInt("Elves.health"));
        this.setBaby(true);

        this.goalSelector.a(0, new PathfinderGoalAvoidTarget(this, EntityHuman.class,
                Utils.getConfig().getInt("Elves.player-avoid-distance"),
                Utils.getConfig().getDouble("Elves.player-avoid-speed"),
                Utils.getConfig().getDouble("Elves.player-avoid-speed")));
        this.goalSelector.a(1, new PathfinderGoalPanic(this, 2.0D));
    }
}

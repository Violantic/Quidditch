package me.violantic.quidditch.game.ball;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Ethan on 4/5/2016.
 */
public class Snitch extends Ball {

    public Snitch() {
        super("Golden Snitch", Material.GOLD_BLOCK);
    }

    public String getName() {
        return "Golden Snitch";
    }

    public Material getMater() {
        return Material.GOLD_BLOCK;
    }

    public void animate() {
        /**
         * TODO
         */
    }

    @Override
    public void spawn(Location location) {
        ArmorStand stand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        stand.setVisible(false);
        stand.setHelmet(new ItemStack(Material.GOLD_BLOCK));
    }
}

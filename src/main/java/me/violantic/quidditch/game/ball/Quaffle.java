package me.violantic.quidditch.game.ball;

import me.violantic.quidditch.Quidditch;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Ethan on 4/5/2016.
 */
public class Quaffle extends Ball {

    public Quaffle() {
        super("Quaffle", Material.WOOD);
    }

    public String getName() {
        return "Quaffle";
    }

    public Material getMater() {
        return Material.WOOD;
    }

    @Override
    public void spawn(final Location location) {
        // Quaffle is invisible armor stand w/ block on head :) //
        final ArmorStand stand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        stand.setVisible(false);
        stand.setHelmet(new ItemStack(getMater(), 1));

        // Thread that handles passing to other players and scoring. //
        new BukkitRunnable() {
            public void run() {

                // Check entities nearby, to see if one is a player. //
                for(Entity entity : stand.getNearbyEntities(3, 3, 3)) {
                    if(entity instanceof Player) {
                        Player player = (Player) entity;
                        double distance = player.getLocation().distanceSquared(stand.getLocation());
                        if(distance <= 0.3) {
                            player.setPassenger(stand);
                            Bukkit.broadcastMessage(Quidditch.getInstance().getPrefix() + player.getName() + " has picked up the Quaffle!");
                        }
                    }
                }

                // Check blocks around it if they are "net" blocks. //
                Location quadCenter = stand.getLocation();
                int size = 2;

                for(int x = quadCenter.getBlockX() - size; x < quadCenter.getX() + size; x++) {
                    for (int y = quadCenter.getBlockY() - size; y < quadCenter.getY() + size; y++) {
                        for (int z = quadCenter.getBlockZ() - size; z < quadCenter.getZ() + size; z++) {
                            Block block = location.getWorld().getBlockAt(x, y, z);
                            // TODO - Check if the blocks around it are a net, and if so who's net is it?
                            // TODO - Set the score and respawn the quaffle, but not the Golden Snitch.
                        }
                    }
                }


            }
        }.runTaskTimer(Quidditch.getInstance(), 1, 1);
    }
}

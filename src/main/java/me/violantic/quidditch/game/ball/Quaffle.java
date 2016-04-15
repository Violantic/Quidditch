package me.violantic.quidditch.game.ball;

import me.violantic.quidditch.Quidditch;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
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

    public Entity entity;
    public Player player;

    public Quaffle() {
        super("Quaffle", Material.WOOD);
        entity = Quidditch.getInstance().getQuaffle().getWorld().spawnEntity(Quidditch.getInstance().getQuaffle(), EntityType.ARMOR_STAND);
    }

    public String getName() {
        return "Quaffle";
    }

    public Material getMater() {
        return Material.WOOD;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public Player getHolder() {
        return this.player;
    }

    // Call before calling Quaffle::getHolder() //
    public void setHolder(Player player) {
        this.player = player;
    }

    @Override
    public void spawn(final Location location) {
        // Quaffle is invisible armor stand w/ block on head :) //
        final ArmorStand stand = (ArmorStand) getEntity();
        stand.setVisible(false);
        stand.setHelmet(new ItemStack(getMater(), 1));

        // Thread that handles passing to other players and scoring. //
        new BukkitRunnable() {
            public void run() {

                // Check entities nearby, to see if one is a player. //
                stand.getNearbyEntities(3, 3, 3).stream().filter(entity -> entity instanceof Player).forEach(entity -> {
                    Player player = (Player) entity;
                    double distance = player.getLocation().distanceSquared(stand.getLocation());
                    if (distance <= 1) {
                        player.setPassenger(stand);
                        setHolder(player);
                        Bukkit.broadcastMessage(Quidditch.getInstance().getPrefix() + player.getName() + " has picked up the Quaffle!");
                    }
                });

                // Check blocks around it if they are "net" blocks. //
                Location quadCenter = stand.getLocation();
                        String team = Quidditch.getInstance().getNets().get(getHolder(), quadCenter);
                        switch (team) {
                            case "home":
                                Quidditch.getInstance().getGameInstance().score(Quidditch.getInstance().getGameInstance().getFirst().getName());
                                break;
                            case "away":
                                Quidditch.getInstance().getGameInstance().score(Quidditch.getInstance().getGameInstance().getSecond().getName());
                                break;
                        }
            }
        }.runTaskTimer(Quidditch.getInstance(), 1, 1);
    }
}

package me.violantic.quidditch.game.broom;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

/**
 * Created by Ethan on 4/11/2016.
 */
public class BroomStick {

    public ArmorStand entity;
    public Player player;

    public BroomStick(Player player) {
        this.player = player;

        entity = player.getWorld().spawn(player.getLocation(), ArmorStand.class);
        entity.setVisible(false);
        entity.setHelmet(new ItemStack(getMaterial(), 1));
        entity.setPassenger(player);
    }

    public Material getMaterial() {
        return Material.ANVIL;
    }

    public Entity getEntity() {
        return this.entity;
    }

    public Player getPlayer() {
        return player;
    }

    public double getSpeed() {
        return 1.2;
    }

    public void move() {
        Vector direction = getPlayer().getLocation().getDirection();
        entity.setVelocity(direction.multiply(getSpeed()));
        entity.getLocation().setYaw(getPlayer().getLocation().getYaw());
        entity.getLocation().setPitch(getPlayer().getLocation().getPitch());
    }

    public void boost() {
        Vector direction = getPlayer().getLocation().getDirection();
        entity.setVelocity(direction.multiply(getSpeed() + 1));
        for(Player player : Bukkit.getOnlinePlayers()) {
            sendBoostPacket(player, entity.getLocation().getBlockX(), entity.getLocation().getBlockY(), entity.getLocation().getBlockZ());
        }
    }

    public void sendBoostPacket(Player p, int x, int y, int z) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, x, y, z, 2, 2, 2, 0, 1, null);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }


}

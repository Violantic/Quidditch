package me.violantic.quidditch.listener;

import me.violantic.quidditch.Quidditch;
import me.violantic.quidditch.game.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Created by Ethan on 4/5/2016.
 */
public class GameListener implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent event) {
            if(event.getPlayer().getPassenger() != null) {
                event.getPlayer().sendMessage("You have a passenger.");
                Entity pass = event.getPlayer().getPassenger();
                event.getPlayer().getPassenger().leaveVehicle();
                pass.setVelocity(event.getPlayer().getLocation().getDirection().multiply(3D));
            }
    }

    @EventHandler
    public void onJoin(final PlayerLoginEvent event) {
        if(Quidditch.getInstance().getGameInstance().start == true) return;

        new BukkitRunnable() {
            public void run() {
                int temp = (Math.random() <= 0.5) ? 1 : 2;
                switch (temp)

                {
                    case 1:
                        if (Quidditch.getInstance().getGameInstance().getFirst().getPlayers().size() < 4) {
                            Team team = Quidditch.getInstance().getGameInstance().getSecond();
                            team.getPlayers().add(event.getPlayer().getUniqueId());
                            Bukkit.broadcastMessage(Quidditch.getInstance().getPrefix() + event.getPlayer().getName() + " has joined " + team.getColor() + team.getName() + ChatColor.RESET + " (" + team.getPlayers().size() + "/4)");
                        } else if (Quidditch.getInstance().getGameInstance().getFirst().getPlayers().size() >= 4 && Quidditch.getInstance().getGameInstance().getSecond().getPlayers().size() < 4) {
                            Team team = Quidditch.getInstance().getGameInstance().getSecond();
                            team.getPlayers().add(event.getPlayer().getUniqueId());
                            Bukkit.broadcastMessage(Quidditch.getInstance().getPrefix() + event.getPlayer().getName() + " has joined " + team.getColor() + team.getName() + ChatColor.RESET + " (" + team.getPlayers().size() + "/4)");
                        } else {
                            event.getPlayer().sendMessage(Quidditch.getInstance().getPrefix() + "Both teams were full.");
                        }
                        break;
                    case 2:
                        if (Quidditch.getInstance().getGameInstance().getSecond().getPlayers().size() < 4) {
                            Team team = Quidditch.getInstance().getGameInstance().getFirst();
                            team.getPlayers().add(event.getPlayer().getUniqueId());
                            Bukkit.broadcastMessage(Quidditch.getInstance().getPrefix() + event.getPlayer().getName() + " has joined " + team.getColor() + team.getName() + ChatColor.RESET + " (" + team.getPlayers().size() + "/4)");
                        } else if (Quidditch.getInstance().getGameInstance().getSecond().getPlayers().size() >= 4 && Quidditch.getInstance().getGameInstance().getFirst().getPlayers().size() < 4) {
                            Team team = Quidditch.getInstance().getGameInstance().getFirst();
                            team.getPlayers().add(event.getPlayer().getUniqueId());
                            Bukkit.broadcastMessage(Quidditch.getInstance().getPrefix() + event.getPlayer().getName() + " has joined " + team.getColor() + team.getName() + ChatColor.RESET + " (" + team.getPlayers().size() + "/4)");
                        } else {
                            event.getPlayer().sendMessage(Quidditch.getInstance().getPrefix() + "Both teams were full.");
                        }
                        break;
                }
            }
        }.runTaskLater(Quidditch.getInstance(), 20);
    }



}

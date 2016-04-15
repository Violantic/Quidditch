package me.violantic.quidditch.game.broom;

import me.violantic.quidditch.Quidditch;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Ethan on 4/14/2016.
 */
public class BroomStickHandler {

    public Map<UUID, BroomStick> broomMap;
    public Map<UUID, Boolean> playerMove;
    public boolean allowed = false;

    public BroomStickHandler() {
        broomMap = new HashMap<UUID, BroomStick>();
        playerMove = new HashMap<UUID, Boolean>();
    }

    public Map<UUID, BroomStick> getBrooms() {
        return this.broomMap;
    }

    public void add(UUID uuid) {
        broomMap.put(uuid, new BroomStick(Bukkit.getPlayer(uuid)));
        playerMove.put(uuid, false);
    }

    public void setIsAllowed(boolean b) {
        this.allowed = b;
    }

    public void toggle(UUID uuid) {
        if(!broomMap.containsKey(uuid)) return;
        if(!playerMove.containsKey(uuid)) return;

        boolean b = playerMove.get(uuid);
        playerMove.put(uuid, (b) ? false : true);

        if(!b && allowed) {
            activate(uuid);
        }
    }

    public void activate(UUID uuid) {
        if(!broomMap.containsKey(uuid)) return;
        if(!this.allowed) return;

        new BukkitRunnable() {
            public void run() {
                if(!(playerMove.get(uuid))) {
                    this.cancel();
                    return;
                }

                getBrooms().get(uuid).move();
            }
        }.runTaskTimer(Quidditch.getInstance(), 5, 5);
    }

    public void deactivate(UUID uuid) {
        if(!broomMap.containsKey(uuid)) return;
        if(!this.allowed) return;

        playerMove.put(uuid, false);
    }

}

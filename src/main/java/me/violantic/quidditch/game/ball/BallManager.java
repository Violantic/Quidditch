package me.violantic.quidditch.game.ball;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ethan on 4/5/2016.
 */
public class BallManager {

    public Map<Ball, Location> ballLocation;
    public List<Ball> balls;

    public BallManager() {
        ballLocation = new HashMap<Ball, Location>();
        balls = new ArrayList<Ball>();
    }

    public boolean hasQuaffle(Player player) {
        if(player.getPassenger() != null) {
            Entity passenger = player.getPassenger();
            if(passenger instanceof ArmorStand) {
                ArmorStand entity = (ArmorStand) passenger;
                if(entity.getHelmet().getType() == balls.get(0).getMaterial()) {
                    return true;
                }
            }
        }

        return false;
    }

}

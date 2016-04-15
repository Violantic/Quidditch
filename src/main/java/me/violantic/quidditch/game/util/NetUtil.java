package me.violantic.quidditch.game.util;

import me.violantic.quidditch.Quidditch;
import me.violantic.quidditch.game.team.TeamGame;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

/**
 * Created by Ethan on 4/10/2016.
 */
public class NetUtil {

    public NetUtil() {}

    public void loadNets () {
        Quidditch instance = Quidditch.getInstance();
        for(String key : instance.getConfig().getConfigurationSection("net").getKeys(false)) {
            ConfigurationSection section = instance.getConfig().getConfigurationSection("net." + key);
            int x, y, z;
            x = section.getInt("x");
            y = section.getInt("y");
            z = section.getInt("z");
            Location location = new Location(Bukkit.getWorld("world"),x,y,z);
            if(section.getString("team") == "home")
                instance.getGameInstance().getHomeNets().add(location);
            else
                instance.getGameInstance().getAwayNets().add(location);
        }
    }

    public String get (Player passenger, Location spot) {
        TeamGame game = Quidditch.getInstance().getGameInstance();
        if (game.getFirst().getPlayers().contains(passenger.getUniqueId())) {
            for(Location location : Quidditch.getInstance().getGameInstance().getHomeNets()) {
                double distance = spot.distanceSquared(location);
                // It's only a block game, accuracy is difficult. //
                if(distance <= 1.3) {
                    return "home";
                }
            }
        } else if (game.getSecond().getPlayers().contains(passenger.getUniqueId())) {
            for(Location location : Quidditch.getInstance().getGameInstance().getAwayNets()) {
                double distance = spot.distanceSquared(location);
                if(distance <= 1.3) {
                    return "away";
                }
            }
        }
        return "miss";
    }

}

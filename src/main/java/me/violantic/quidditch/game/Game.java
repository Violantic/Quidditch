package me.violantic.quidditch.game;

import me.violantic.quidditch.game.team.Team;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

/**
 * Created by Ethan on 4/5/2016.
 */
public interface Game {

    Team getFirst();

    Team getSecond();

    Location getCenter();

    Location getFirstSpawn();

    Location getSecondSpawn();

    Location getQuaffle();

    Location getBeater();

    Location getSnitch();

    Material getNet();

    List<Location> getHomeNets();

    List<Location> getAwayNets();

}

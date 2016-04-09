package me.violantic.quidditch.game.team;

import me.violantic.quidditch.Quidditch;
import me.violantic.quidditch.game.Game;
import me.violantic.quidditch.game.ball.Ball;
import me.violantic.quidditch.game.ball.Quaffle;
import me.violantic.quidditch.game.scoreboard.SimpleScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Ethan on 4/5/2016.
 */
public class TeamGame implements Game {

    public Team one;
    public Team two;
    public Map<String, Integer> scores;
    public SimpleScoreboard scoreboard;

    public boolean start = false;

    public TeamGame(Team one, Team two) {
        this.one = one;
        this.two = two;
        this.scores = new HashMap<String, Integer>();
        this.scoreboard = new SimpleScoreboard("quidditch");
        this.scoreboard.setTitle(Quidditch.getInstance().getPrefix());
        this.scoreboard.add(one.getName(), 0);
        this.scoreboard.add(two.getName(), 0);
    }

    public void start() {
        if(!start){
            start = true;
            scores.clear();
            scores.put(getFirst().getName(), 0);
            scores.put(getSecond().getName(), 0);
            for(UUID uuid : getFirst().getPlayers()) {
                Player player = Bukkit.getPlayer(uuid);
                player.teleport(getFirstSpawn());

                // Select a seeker. //

                // Give them equipment. //
            }

            for(UUID uuid : getSecond().getPlayers()) {
                Player player = Bukkit.getPlayer(uuid);
                player.teleport(getSecondSpawn());

                // Select a seeker. //

                // Give them equipment. //
            }

            spawnBalls();

            Bukkit.broadcastMessage(Quidditch.getInstance().getPrefix() + "The game has started!");
        }
    }

    public void spawnBalls() {
        for(Ball ball : Quidditch.getInstance().getBalls()) {
            ball.spawn((ball instanceof Quaffle) ? getQuaffle() : getSnitch());
            Bukkit.broadcastMessage(Quidditch.getInstance().getPrefix() + "The Quaffle and Golden Snitch have been spawned!");
        }
    }

    public void score(String team) {
        this.scores.put(team, scores.get(team) + 1);
    }

    public Team getFirst() {
        return this.one;
    }

    public Team getSecond() {
        return this.two;
    }

    public Location getCenter() {
        return Quidditch.getInstance().getCenter();
    }

    public Location getFirstSpawn() {
        return Quidditch.getInstance().getFirst();
    }

    public Location getSecondSpawn() {
        return Quidditch.getInstance().getSecond();
    }

    public Location getQuaffle() {
        return Quidditch.getInstance().getQuaffle();
    }

    @Deprecated
    public Location getBeater() {
        return null;
    }

    public Location getSnitch() {
        return Quidditch.getInstance().getSnitch();
    }

    public List<Location> getHomeNets() {
        return null;
    }

    public List<Location> getAwayNets() {
        return null;
    }
}

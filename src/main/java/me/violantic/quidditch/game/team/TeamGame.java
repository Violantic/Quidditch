package me.violantic.quidditch.game.team;

import me.violantic.quidditch.Quidditch;
import me.violantic.quidditch.game.Game;
import me.violantic.quidditch.game.ball.Ball;
import me.violantic.quidditch.game.ball.Quaffle;
import me.violantic.quidditch.game.broom.BroomStick;
import me.violantic.quidditch.game.scoreboard.SimpleScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
        this.scoreboard.setTitle(Quidditch.getInstance().getPrefix().replace(":", ""));
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
                this.scoreboard.send(player);
                Quidditch.getInstance().getBrooms().broomMap.put(uuid, new BroomStick(player));
                Quidditch.getInstance().getBrooms().activate(uuid);

                // Select a seeker. //

                // Give them equipment. //
            }

            for(UUID uuid : getSecond().getPlayers()) {
                Player player = Bukkit.getPlayer(uuid);
                player.teleport(getSecondSpawn());
                this.scoreboard.send(player);
                Quidditch.getInstance().getBrooms().broomMap.put(uuid, new BroomStick(player));
                Quidditch.getInstance().getBrooms().activate(uuid);

                // Select a seeker. //

                // Give them equipment. //
            }

            send("BroomSticks will be enabled in 10 seconds.", true);
            new BukkitRunnable() {
                public void run() {
                    Quidditch.getInstance().getBrooms().setIsAllowed(true);
                    send("BroomSticks have been enabled! Fly!", true);
                }
            }.runTaskLater(Quidditch.getInstance(), 20*5);

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
        scores.put(team, scores.get(team) + 1);
        scoreboard.add(team, scores.get(team));
        scoreboard.update();

        if(scores.get(team) == 20) {
            send(team + " has earned 200 points!", true);
        }

        send(team + " has scored 10 points! Their total is: " + ChatColor.GREEN + scores.get(team), true);
    }

    public void win(String team) {
        // Team has caught the snitch. //
        scores.put(team, scores.get(team) + 15);
        scoreboard.add(team, scores.get(team));
        scoreboard.update();

        send(team + " has won the match! Their total is: " + ChatColor.GREEN + scores.get(team), true);
    }

    public void send(String message, boolean prefix) {
        if(prefix) {
            for(UUID uuid : getFirst().getPlayers()) {
                Player player = Bukkit.getPlayer(uuid);
                if(player != null)
                    player.sendMessage(Quidditch.getInstance().getPrefix() + message);
            }

            for(UUID uuid : getSecond().getPlayers()) {
                Player player = Bukkit.getPlayer(uuid);
                if(player != null)
                    player.sendMessage(Quidditch.getInstance().getPrefix() + message);
            }
        }
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

    @Override
    public Material getNet() {
        return Material.WEB;
    }

    public List<Location> getHomeNets() {
        return null;
    }

    public List<Location> getAwayNets() {
        return null;
    }
}

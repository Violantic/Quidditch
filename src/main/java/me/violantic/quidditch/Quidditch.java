package me.violantic.quidditch;

import me.violantic.quidditch.game.QuidditchCommand;
import me.violantic.quidditch.game.ball.Ball;
import me.violantic.quidditch.game.ball.Quaffle;
import me.violantic.quidditch.game.ball.Snitch;
import me.violantic.quidditch.game.broom.BroomStickHandler;
import me.violantic.quidditch.game.team.Team;
import me.violantic.quidditch.game.team.TeamGame;
import me.violantic.quidditch.game.util.NetUtil;
import me.violantic.quidditch.listener.GameListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ethan on 4/5/2016.
 */
public class Quidditch extends JavaPlugin {

    public static Quidditch instance;

    private List<Ball> ballList;
    private Map<String, Location> balls;

    public TeamGame game;
    public NetUtil netUtil;
    public BroomStickHandler broomHandler;

    @Override
    public void onEnable() {
        instance = this;

        getConfig().options().copyDefaults(true);
        saveConfig();

        getServer().getPluginManager().registerEvents(new GameListener(), this);
        getCommand("quidditch").setExecutor(new QuidditchCommand());

        ballList = new ArrayList<Ball>() {
            {
                this.add(new Quaffle());
                this.add(new Snitch());
            }
        };
        
        balls = new HashMap<String, Location>(){
            {
                for(String key : getConfig().getConfigurationSection("pitch").getKeys(false)) {
                    ConfigurationSection location = getConfig().getConfigurationSection("pitch." + key);
                    int x, y, z;
                    x = location.getInt("x");
                    y = location.getInt("y");
                    z = location.getInt("z");

                    Location realLocation = Bukkit.getWorld("world").getBlockAt(x, y, z).getLocation();
                    this.put(key, realLocation);
                }
            }
        };

        netUtil = new NetUtil();
        broomHandler = new BroomStickHandler();

        game = new TeamGame(new Team("Gryffindor", ChatColor.RED), new Team("Slytherin", ChatColor.GREEN));
        game.start = false;
        new BukkitRunnable() {
            private int second = 45;
            public void run() {

                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.playSound(player.getLocation(), Sound.CLICK, 1, 1);
                }

                // Game has been force started. //
                if(game.start) {
                    this.cancel();
                    return;
                }

                second--;

                if (second % 15 == 0) {
                    getServer().broadcastMessage(getPrefix() + "Starting in: " + ChatColor.GREEN + (second));
                }

                // Counter has reached maximum time. //
                if (second == 0) {
                    if (game.getFirst().getPlayers().size() < 4 && game.getSecond().getPlayers().size() < 4) {
                        getServer().broadcastMessage(getPrefix() + "Teams were not full, restarting count.");
                        second = 45;
                    }
                }

                // Teams are full, start the game. //
                if (game.getFirst().getPlayers().size() == 4 && game.getSecond().getPlayers().size() == 4) {
                    game.start();
                    second = 0;
                    this.cancel();
                    return;
                }
            }
        }.runTaskTimer(this, 20, 20);

    }

    public TeamGame getGameInstance() {
        return this.game;
    }

    public List<Ball> getBalls() {
        return ballList;
    }

    public Map<String, Location> getLocations() {
        return this.balls;
    }

    public Location getCenter() {
        return getLocations().get("center");
    }

    public Location getFirst() {
        return getLocations().get("first");
    }

    public Location getSecond() {
        return getLocations().get("second");
    }

    public Location getQuaffle() {
        return getLocations().get("quaffle");
    }

    public Location getFirstBludger() {
        return getLocations().get("bludger_1");
    }

    public Location getSecondBludger() {
        return getLocations().get("bludger_2");
    }

    public Location getSnitch() {
        return getLocations().get("snitch");
    }

    public static Quidditch getInstance() {
        return instance;
    }

    public NetUtil getNets() {
        return netUtil;
    }

    public BroomStickHandler getBrooms() {
        return this.broomHandler;
    }

    public String getPrefix() {
        return ChatColor.GRAY + "[" + ChatColor.RED + "Quidditch" + ChatColor.GRAY + "]: " + ChatColor.RESET;
    }


}

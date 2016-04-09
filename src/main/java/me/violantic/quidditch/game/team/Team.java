package me.violantic.quidditch.game.team;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ethan on 4/5/2016.
 */
public class Team {

    public String name;
    public ChatColor color;

    List<UUID> players;

    public Team(String name, ChatColor color) {
        this.name = name;
        this.color = color;
        
        players = new ArrayList<UUID>();
    }

    public String getName() {
        return this.name;
    }

    public ChatColor getColor() {
        return this.color;
    }

    public List<UUID> getPlayers() {
        return this.players;
    }

}

package me.violantic.quidditch.game.ball;

import org.bukkit.Location;
import org.bukkit.Material;

/**
 * Created by Ethan on 4/5/2016.
 */
public abstract class Ball {

    public String name;
    public Material material;

    public Ball(String name, Material material) {
        this.name = name;
        this.material = material;
    }

    public String getName() {
        return this.name;
    }

    public Material getMaterial() {
        return this.material;
    }

    public abstract void spawn(Location location);

}

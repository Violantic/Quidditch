package me.violantic.quidditch.game;

import me.violantic.quidditch.Quidditch;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Ethan on 4/5/2016.
 */
public class QuidditchCommand implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("quidditch")) {
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("forcestart")) {
                    if(commandSender.isOp()) {
                        Quidditch.getInstance().getGameInstance().start();
                        Bukkit.broadcastMessage(Quidditch.getInstance().getPrefix() + commandSender.getName() + " has force started the match!");
                    } else {
                        commandSender.sendMessage(ChatColor.RED + "You must be an administrator to do that.");
                    }
                } else {
                    commandSender.sendMessage(ChatColor.RED + "Invalid arguments: /Quidditch forcestart");
                }
            }  else {
                commandSender.sendMessage(ChatColor.RED + "Invalid arguments.");
            }
        }
        return false;
    }
}

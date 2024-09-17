package org.achymake.fertilizedegg.commands;

import org.achymake.fertilizedegg.FertilizedEgg;
import org.achymake.fertilizedegg.data.Message;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FertilizedEggCommand implements CommandExecutor, TabCompleter {
    private FertilizedEgg getInstance() {
        return FertilizedEgg.getInstance();
    }
    private Message getMessage() {
        return getInstance().getMessage();
    }
    public FertilizedEggCommand() {
        getInstance().getCommand("fertilizedegg").setExecutor(this);
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                getMessage().send(player, "&6" + getInstance().name() + " " + getInstance().version());
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    getInstance().reload();
                    getMessage().send(player, "&6Players:&f reloaded");
                    return true;
                }
            }
        } else if (sender instanceof ConsoleCommandSender consoleCommandSender) {
            if (args.length == 0) {
                getMessage().send(consoleCommandSender, getInstance().name() + " " + getInstance().version());
                return true;
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("reload")) {
                    getInstance().reload();
                    getMessage().send(consoleCommandSender, "Players: reloaded");
                    return true;
                }
            }
        }
        return false;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        var commands = new ArrayList<String>();
        if (sender instanceof Player) {
            if (args.length == 1) {
                commands.add("reload");
            }
        }
        return commands;
    }
}
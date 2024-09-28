package org.achymake.fertilizedegg.data;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Message {
    public void send(Player player, String message) {
        player.sendMessage(addColor(message));
    }
    public void sendActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(addColor(message)));
    }
    public String addColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
    public void send(ConsoleCommandSender sender, String message) {
        sender.sendMessage(message);
    }
    public String capitalize(String value) {
        if (value.contains("_")) {
            var stringBuilder = new StringBuilder();
            for (var main : value.split("_")) {
                var name = main;
                var firstLetter = name.substring(0, 1);
                var result = firstLetter + name.substring(1, main.length()).toLowerCase();
                stringBuilder.append(result);
                stringBuilder.append(" ");
            }
            return stringBuilder.toString().strip();
        } else {
            var name = value;
            var firstLetter = name.substring(0, 1);
            return firstLetter + name.substring(1, value.length()).toLowerCase();
        }
    }
}
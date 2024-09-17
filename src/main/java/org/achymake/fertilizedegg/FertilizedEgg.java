package org.achymake.fertilizedegg;

import org.achymake.fertilizedegg.commands.FertilizedEggCommand;
import org.achymake.fertilizedegg.data.Message;
import org.achymake.fertilizedegg.handlers.ScheduleHandler;
import org.achymake.fertilizedegg.listeners.CreatureSpawn;
import org.achymake.fertilizedegg.listeners.PlayerInteract;
import org.achymake.fertilizedegg.listeners.PlayerJoin;
import org.bukkit.block.DecoratedPot;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class FertilizedEgg extends JavaPlugin {
    private final Map<DecoratedPot, Integer> decoratedPotTasks = new HashMap<>();
    private static FertilizedEgg instance;
    private static Message message;
    private static ScheduleHandler scheduleHandler;
    private static UpdateChecker updateChecker;
    @Override
    public void onEnable() {
        instance = this;
        message = new Message();
        scheduleHandler = new ScheduleHandler();
        updateChecker = new UpdateChecker();
        commands();
        events();
        reload();
        getMessage().sendLog(Level.INFO, "Enabled for " + getMinecraftProvider() + " " + getMinecraftVersion());
        getUpdateChecker().getUpdate();
    }
    @Override
    public void onDisable() {
        if (!getDecoratedPotTasks().isEmpty()) {
            getDecoratedPotTasks().clear();
        }
        getScheduleHandler().cancelAll();
    }
    private void commands() {
        new FertilizedEggCommand();
    }
    private void events() {
        new CreatureSpawn();
        new PlayerInteract();
        new PlayerJoin();
    }
    public void reload() {
        var file = new File(getDataFolder(), "config.yml");
        if (file.exists()) {
            try {
                getConfig().load(file);
            } catch (IOException | InvalidConfigurationException e) {
                getMessage().sendLog(Level.WARNING, e.getMessage());
            }
            if (!getConfig().isInt("decorated-pot.timer")) {
                getConfig().set("decorated-pot.timer", 300);
                try {
                    getConfig().save(file);
                } catch (IOException e) {
                    getMessage().sendLog(Level.WARNING, e.getMessage());
                }
            }
        } else {
            getConfig().options().copyDefaults(true);
            try {
                getConfig().save(file);
            } catch (IOException e) {
                getMessage().sendLog(Level.WARNING, e.getMessage());
            }
        }
    }
    public Map<DecoratedPot, Integer> getDecoratedPotTasks() {
        return decoratedPotTasks;
    }
    public PluginManager getManager() {
        return getServer().getPluginManager();
    }
    public UpdateChecker getUpdateChecker() {
        return updateChecker;
    }
    public ScheduleHandler getScheduleHandler() {
        return scheduleHandler;
    }
    public Message getMessage() {
        return message;
    }
    public static FertilizedEgg getInstance() {
        return instance;
    }
    public String name() {
        return getDescription().getName();
    }
    public String version() {
        return getDescription().getVersion();
    }
    public String getMinecraftVersion() {
        return getServer().getBukkitVersion();
    }
    public String getMinecraftProvider() {
        return getServer().getName();
    }
    public boolean isSpigot() {
        return getMinecraftProvider().equals("CraftBukkit");
    }
}
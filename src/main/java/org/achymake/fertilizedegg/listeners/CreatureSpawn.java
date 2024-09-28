package org.achymake.fertilizedegg.listeners;

import org.achymake.fertilizedegg.FertilizedEgg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.PluginManager;

public class CreatureSpawn implements Listener {
    private FertilizedEgg getInstance() {
        return FertilizedEgg.getInstance();
    }
    private PluginManager getManager() {
        return getInstance().getManager();
    }
    public CreatureSpawn() {
        getManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.EGG) || event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.DISPENSE_EGG)) {
            event.setCancelled(true);
        }
    }
}
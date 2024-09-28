package org.achymake.fertilizedegg.listeners;

import org.achymake.fertilizedegg.FertilizedEgg;
import org.achymake.fertilizedegg.event.FertilizedSpawnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

public class FertilizedSpawn implements Listener {
    private FertilizedEgg getInstance() {
        return FertilizedEgg.getInstance();
    }
    private PluginManager getManager() {
        return getInstance().getManager();
    }
    public FertilizedSpawn() {
        getManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onFertilizedSpawn(FertilizedSpawnEvent event) {
        if (event.isCancelled()) {
            event.getChicken().remove();
        } else event.getChicken().setBaby();
    }
}
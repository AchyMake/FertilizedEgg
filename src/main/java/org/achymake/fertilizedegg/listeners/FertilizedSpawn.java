package org.achymake.fertilizedegg.listeners;

import org.achymake.fertilizedegg.FertilizedEgg;
import org.achymake.fertilizedegg.event.FertilizedSpawnEvent;
import org.achymake.fertilizedegg.handlers.ScheduleHandler;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
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
    private ScheduleHandler getScheduler() {
        return getInstance().getScheduleHandler();
    }
    public FertilizedSpawn() {
        getManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onFertilizedSpawn(FertilizedSpawnEvent event) {
        var decoratedPot = event.getDecoratedPot();
        if (decoratedPot == null)return;
        if (event.isCancelled()) {
            getScheduler().cancel(getInstance().getDecoratedPotTasks().get(decoratedPot));
        } else {
            var location = decoratedPot.getLocation();
            var chicken = (Chicken) location.getWorld().spawnEntity(location.add(0.5, 1, 0.5), EntityType.CHICKEN);
            chicken.setBaby();
        }
    }
}
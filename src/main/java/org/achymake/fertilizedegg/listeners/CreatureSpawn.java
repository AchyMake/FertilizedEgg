package org.achymake.fertilizedegg.listeners;

import org.achymake.fertilizedegg.FertilizedEgg;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class CreatureSpawn implements Listener {
    private FertilizedEgg getInstance() {
        return FertilizedEgg.getInstance();
    }
    public CreatureSpawn() {
        getInstance().getManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (!event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.EGG))return;
        event.setCancelled(true);
    }
}
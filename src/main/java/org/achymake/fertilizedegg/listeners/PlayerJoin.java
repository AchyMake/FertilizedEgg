package org.achymake.fertilizedegg.listeners;

import org.achymake.fertilizedegg.FertilizedEgg;
import org.achymake.fertilizedegg.UpdateChecker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    private FertilizedEgg getInstance() {
        return FertilizedEgg.getInstance();
    }
    private UpdateChecker getUpdateChecker() {
        return getInstance().getUpdateChecker();
    }
    public PlayerJoin() {
        getInstance().getManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        getUpdateChecker().getUpdate(event.getPlayer());
    }
}
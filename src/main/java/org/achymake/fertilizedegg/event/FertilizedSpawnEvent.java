package org.achymake.fertilizedegg.event;

import org.bukkit.entity.Chicken;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.checkerframework.checker.nullness.qual.NonNull;

public class FertilizedSpawnEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final Chicken chicken;
    private boolean cancelled = false;
    public FertilizedSpawnEvent(Chicken chicken) {
        this.chicken = chicken;
    }
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
    public Chicken getChicken() {
        return chicken;
    }
    public boolean isCancelled() {
        return cancelled;
    }
    public @NonNull HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
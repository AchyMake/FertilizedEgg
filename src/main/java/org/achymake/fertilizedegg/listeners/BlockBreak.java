package org.achymake.fertilizedegg.listeners;

import org.achymake.fertilizedegg.FertilizedEgg;
import org.achymake.fertilizedegg.handlers.ScheduleHandler;
import org.bukkit.block.DecoratedPot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {
    private FertilizedEgg getInstance() {
        return FertilizedEgg.getInstance();
    }
    private ScheduleHandler getScheduler() {
        return getInstance().getScheduleHandler();
    }
    public BlockBreak() {
        getInstance().getManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getState() instanceof DecoratedPot decoratedPot) {
            if (!getInstance().getDecoratedPotTasks().containsKey(decoratedPot))return;
            getScheduler().cancel(getInstance().getDecoratedPotTasks().get(decoratedPot));
        }
    }
}
package org.achymake.fertilizedegg.listeners;

import org.achymake.fertilizedegg.FertilizedEgg;
import org.achymake.fertilizedegg.handlers.ScheduleHandler;
import org.bukkit.Material;
import org.bukkit.block.DecoratedPot;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.Random;

public class PlayerInteract implements Listener {
    private FertilizedEgg getInstance() {
        return FertilizedEgg.getInstance();
    }
    private ScheduleHandler getScheduler() {
        return getInstance().getScheduleHandler();
    }
    private PluginManager getManager() {
        return getInstance().getManager();
    }
    public PlayerInteract() {
        getManager().registerEvents(this, getInstance());
    }
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK))return;
        if (event.getClickedBlock() == null)return;
        var block = event.getClickedBlock();
        if (block.getState() instanceof DecoratedPot decoratedPot) {
            var player = event.getPlayer();
            var heldItem = player.getInventory().getItemInMainHand();
            if (!isEgg(heldItem))return;
            if (getInstance().getDecoratedPotTasks().containsKey(decoratedPot))return;
            getScheduler().runLater(new Runnable() {
                @Override
                public void run() {
                    schedule(decoratedPot);
                }
            }, 0);
        }
    }
    private void schedule(DecoratedPot decoratedPot) {
        //60 = 1m, 120 = 2m, 240 = 4m, 300 5m
        var eggMaterial = Material.EGG;
        var egg = decoratedPot.getInventory().getItem(decoratedPot.getInventory().first(eggMaterial));
        if (egg != null) {
            var location = decoratedPot.getLocation();
            var taskID = getScheduler().runLater(new Runnable() {
                @Override
                public void run() {
                    getInstance().getDecoratedPotTasks().remove(decoratedPot);
                    egg.setAmount(egg.getAmount() - 1);
                    var random = new Random().nextInt(0, 100);
                    if (random >= 50) {
                        var chicken = (Chicken) location.getWorld().spawnEntity(location.add(0.5, 1, 0.5), EntityType.CHICKEN);
                        chicken.setBaby();
                        var chunkLimit = getInstance().getConfig().getInt("chunk-limit");
                        if (chunkLimit > 0) {
                            var listed = new ArrayList<Chicken>();
                            for (var chunkEntities : decoratedPot.getLocation().getChunk().getEntities()) {
                                if (chunkEntities.getType().equals(EntityType.CHICKEN)) {
                                    listed.add(chicken);
                                }
                            }
                            if (listed.size() >= chunkLimit) {
                                chicken.remove();
                            }
                            listed.clear();
                        }
                    }
                    schedule(decoratedPot);
                }
            }, 20 * getInstance().getConfig().getInt("decorated-pot.timer")).getTaskId();
            getInstance().getDecoratedPotTasks().put(decoratedPot, taskID);
        }
    }
    private boolean isEgg(ItemStack itemStack) {
        return itemStack.getType().equals(Material.EGG);
    }
}
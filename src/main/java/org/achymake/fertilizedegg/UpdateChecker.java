package org.achymake.fertilizedegg;

import org.achymake.fertilizedegg.data.Message;
import org.achymake.fertilizedegg.handlers.ScheduleHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {
    private FertilizedEgg getInstance() {
        return FertilizedEgg.getInstance();
    }
    private FileConfiguration getConfig() {
        return getInstance().getConfig();
    }
    private Message getMessage() {
        return getInstance().getMessage();
    }
    private ScheduleHandler getSchedule() {
        return getInstance().getScheduleHandler();
    }
    private String getName() {
        return getInstance().name();
    }
    private String getVersion() {
        return getInstance().version();
    }
    private boolean notifyUpdate() {
        return getConfig().getBoolean("notify-update");
    }
    public void getUpdate(Player player) {
        if (notifyUpdate()) {
            if (player.hasPermission("fertilizedegg.event.join.update")) {
                getSchedule().runLater(new Runnable() {
                    @Override
                    public void run() {
                        getLatest((latest) -> {
                            if (!getVersion().equals(latest)) {
                                getMessage().send(player, getName() + "&6 has new update:");
                                getMessage().send(player, "-&a https://www.spigotmc.org/resources/119650/");
                            }
                        });
                    }
                }, 5);
            }
        }
    }
    public void getUpdate() {
        if (notifyUpdate()) {
            getSchedule().runAsynchronously(new Runnable() {
                @Override
                public void run() {
                    getLatest((latest) -> {
                        if (!getVersion().equals(latest)) {
                            getInstance().sendInfo(getName() + " has new update:");
                            getInstance().sendInfo("- https://www.spigotmc.org/resources/119650/");
                        }
                    });
                }
            });
        }
    }
    public void getLatest(Consumer<String> consumer) {
        try (var inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + 119650).openStream()) {
            var scanner = new Scanner(inputStream);
            if (scanner.hasNext()) {
                consumer.accept(scanner.next());
                scanner.close();
            } else {
                inputStream.close();
            }
        } catch (IOException e) {
            getInstance().sendWarning(e.getMessage());
        }
    }
}
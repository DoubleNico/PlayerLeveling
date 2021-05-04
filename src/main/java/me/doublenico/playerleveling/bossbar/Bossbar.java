package me.doublenico.playerleveling.bossbar;

import me.clip.placeholderapi.PlaceholderAPI;
import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.files.Message;
import me.doublenico.playerleveling.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Objects;

public class Bossbar {

    private int taskID = -1;
    private final PlayerLeveling plugin;
    private BossBar bar;
    public Message message;

    public Bossbar(PlayerLeveling plugin){
        this.plugin = plugin;
        this.message = new Message(plugin);
    }

    public void removePlayer(Player player){
        bar.removePlayer(player);
    }

    public BossBar getBar(){
        return bar;
    }

    public void createBar(Player player) {
        String barMessage = CC.color(message.getConfig().getString("BossBar.message"));
        String barColor = Objects.requireNonNull(message
                .getConfig().getString("BossBar.color")).toUpperCase(Locale.ROOT);
        barMessage = PlaceholderAPI.setPlaceholders(player, barMessage);
        bar = Bukkit.createBossBar(barMessage, BarColor.valueOf(barColor), BarStyle.SOLID);
        cast(player);
        bar.setVisible(true);
        bar.addPlayer(player);
    }

    public void cast(Player player){
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {

            double progress = 1.0;
            final int timeConfig = message.getConfig().getInt("BossBar.time");
            final double time = 1.0/timeConfig;

            @Override
            public void run() {

                bar.setProgress(progress);
                String barMessage = CC.color(message.getConfig().getString("BossBar.message"));
                String barColor = Objects.requireNonNull(message
                            .getConfig().getString("BossBar.color")).toUpperCase(Locale.ROOT);
                barMessage = PlaceholderAPI.setPlaceholders(player, barMessage);
                bar.setColor(BarColor.valueOf(barColor));
                bar.setTitle(barMessage);

                progress = progress - time;
                if(progress <= 0){
                    bar.setVisible(false);
                    Bukkit.getServer().getScheduler().cancelTask(taskID);
                }
            }

        }, 0, 20);
    }


    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }
}


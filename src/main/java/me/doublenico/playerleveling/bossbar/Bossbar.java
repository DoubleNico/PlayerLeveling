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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Locale;
import java.util.Objects;

public class Bossbar {

    private int taskID;
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
        if(Bukkit.getServer().getVersion().contains("1.12")
                || Bukkit.getServer().getVersion().contains("1.13")
                || Bukkit.getServer().getVersion().contains("1.14")
                || Bukkit.getServer().getVersion().contains("1.15")
                || Bukkit.getServer().getVersion().contains("1.16") ) {
            String barMessage = CC.color(message.getConfig().getString("BossBar.message"));
            String barColor = Objects.requireNonNull(message
                    .getConfig().getString("BossBar.color")).toUpperCase(Locale.ROOT);
            barMessage = PlaceholderAPI.setPlaceholders(player, barMessage);
            bar = Bukkit.createBossBar(barMessage, BarColor.valueOf(barColor), BarStyle.SOLID);
            cast(player);
            bar.setVisible(true);
            bar.addPlayer(player);
        }
        CC.console(PlayerLeveling.prefix + "&bYou can''t use the bossbar!" +
                "Because the server version is lower than 1.9");
    }

    public void cast(Player player){
        new BukkitRunnable() {

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
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }

}


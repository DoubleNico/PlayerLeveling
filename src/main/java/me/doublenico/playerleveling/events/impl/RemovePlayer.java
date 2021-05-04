package me.doublenico.playerleveling.events.impl;

import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.bossbar.Bossbar;
import me.doublenico.playerleveling.files.DataManager;
import me.doublenico.playerleveling.files.Message;
import me.doublenico.playerleveling.leveling.LevelManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class RemovePlayer implements Listener {

    public PlayerLeveling plugin;
    public DataManager data;
    public Message message;
    public Bossbar bar;

    public RemovePlayer(PlayerLeveling plugin){
        this.plugin = plugin;
        this.data = new DataManager(plugin);
        this.message = new Message(plugin);
    }


    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        LevelManager playerLevelManager = plugin.levelManagerHashMap.get(player.getUniqueId());

        if (plugin.levelManagerHashMap.containsKey(player.getUniqueId())) {
            data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".level", playerLevelManager.getLevel());
            data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".xp", playerLevelManager.getXp());
            data.saveConfig();
            plugin.levelManagerHashMap.remove(player.getUniqueId());
        }
        boolean enabled = message.getConfig().getBoolean("BossBar.enabled");
        if(enabled) {
            bar = new Bossbar(PlayerLeveling.instance);
            if(bar.getBar().getPlayers().contains(player)) {
                bar.removePlayer(player);
            }
        }
    }

}

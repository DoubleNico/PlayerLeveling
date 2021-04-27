package me.doublenico.playerleveling.events.impl;

import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.files.DataManager;
import me.doublenico.playerleveling.files.Message;
import me.doublenico.playerleveling.leveling.LevelManager;
import me.doublenico.playerleveling.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class RegisterPlayer implements Listener {

    public PlayerLeveling plugin;
    public DataManager data;

    public RegisterPlayer(PlayerLeveling plugin){
        this.plugin = plugin;
        this.data = new DataManager(plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            plugin.levelManagerHashMap.put(player.getUniqueId(), new LevelManager(0, 0));
            data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".level", 0);
            data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".xp", 0);
            data.saveConfig();
            CC.console(Message.setStringColor("REGISTER_PLAYER")
                    .replace("{uuid}", player.getUniqueId().toString())
                    .replace("{player}", player.getName()));
        } else {
            int level = data.getConfig().getInt("PlayerLevels." + player.getUniqueId() + ".level");
            int xp = data.getConfig().getInt("PlayerLevels." + player.getUniqueId() + ".xp");
            plugin.levelManagerHashMap.put(player.getUniqueId(), new LevelManager(level, xp));
            LevelManager playerLevelManager = plugin.levelManagerHashMap.get(player.getUniqueId());
            playerLevelManager.xpCheck(player, playerLevelManager);
            data.saveConfig();
            if(!data.getConfig().getString("PlayerLevels.").contains(player.getUniqueId().toString())){
                data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".level", 0);
                data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".xp", 0);
                data.saveConfig();
                CC.console(Message.setStringColor("REGISTER_PLAYER")
                        .replace("{uuid}", player.getUniqueId().toString())
                        .replace("{player}", player.getName()));
            }
        }
    }



}

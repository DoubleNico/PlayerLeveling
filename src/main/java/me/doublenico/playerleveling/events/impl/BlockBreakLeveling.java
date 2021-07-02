package me.doublenico.playerleveling.events.impl;

import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.files.DataManager;
import me.doublenico.playerleveling.files.Message;
import me.doublenico.playerleveling.leveling.LevelManager;
import me.doublenico.playerleveling.utils.CC;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Objects;

public class BlockBreakLeveling implements Listener {

    public PlayerLeveling plugin;
    public DataManager data;
    public Message message;

    public BlockBreakLeveling(PlayerLeveling plugin){
        this.plugin = plugin;
        this.data = new DataManager(plugin);
        this.message = new Message(plugin);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        LevelManager playerLevelManager = plugin.levelManagerHashMap.get(player.getUniqueId());
        Block block = event.getBlock();

        if(player.getGameMode() == GameMode.CREATIVE)
            return;

        for (String key : Objects.requireNonNull(plugin.getConfig().getConfigurationSection("MINING")).getKeys(false)) {
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("MINING." + key);
            if (block.getType() == Material.getMaterial(key)) {
                assert section != null;
                int keyXP = section.getInt(".value");
                try {
                    playerLevelManager.addExperience(player, keyXP);
                }
                catch (NullPointerException e){
                    CC.console("The material value " + key + " is wrong or doesn't exists.");
                }
            }
        }
    }



}

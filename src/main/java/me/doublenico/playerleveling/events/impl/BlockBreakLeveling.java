package me.doublenico.playerleveling.events.impl;

import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.files.DataManager;
import me.doublenico.playerleveling.files.Message;
import me.doublenico.playerleveling.leveling.LevelManager;
import me.doublenico.playerleveling.utils.CC;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

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
        //Material materialList = Material.getMaterial(String.valueOf(plugin.getConfig().getConfigurationSection("MINING").getKeys(false)));

        for (String key : plugin.getConfig().getConfigurationSection("MINING").getKeys(false)) {
            ConfigurationSection section = plugin.getConfig().getConfigurationSection("MINING." + key);
            if (block.getType() == Material.getMaterial(key)) {
                int keyXP = section.getInt(".value");
                String expMessage = message.getConfig().getString("EXPERIENCE_GAIN");
                try {
                    playerLevelManager.setXp(playerLevelManager.getXp() + keyXP);
                    player.sendMessage(CC.color(expMessage).replace("{expGain}", "" + keyXP));
                    data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".level", playerLevelManager.getLevel());
                    data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".xp", playerLevelManager.getXp());
                    data.saveConfig();
                    playerLevelManager.xpCheck(player, playerLevelManager);
                }
                catch (NullPointerException e){
                    CC.console("The material value " + key + " is wrong or doesn't exists.");
                }
            }
        }
    }



}

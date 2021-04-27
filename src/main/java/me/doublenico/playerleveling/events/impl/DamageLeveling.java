package me.doublenico.playerleveling.events.impl;

import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.files.DataManager;
import me.doublenico.playerleveling.files.Message;
import me.doublenico.playerleveling.leveling.LevelManager;
import me.doublenico.playerleveling.utils.CC;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class DamageLeveling implements Listener {

    private PlayerLeveling plugin;
    public DataManager data;

    public DamageLeveling(PlayerLeveling plugin){
        this.plugin = plugin;
        this.data = new DataManager(plugin);
    }


    @EventHandler
    public void onAttack(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        Entity victim = event.getEntity();
        LevelManager playerLevelManager = plugin.levelManagerHashMap.get(player.getUniqueId());
        if (event.getEntity().getKiller() == player) {
            for (String key : plugin.getConfig().getConfigurationSection("DAMAGE").getKeys(false)) {
                ConfigurationSection section = plugin.getConfig().getConfigurationSection("DAMAGE." + key);
                int keyXP = section.getInt(".value");
                if (victim.getType() == EntityType.valueOf(key)) {
                    String expMessage = Message.getConfig().getString("EXPERIENCE_GAIN");
                    try {
                        playerLevelManager.setXp(playerLevelManager.getXp() + keyXP);
                        player.sendMessage(CC.color(expMessage).replace("{expGain}", "" + keyXP));
                        data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".level", playerLevelManager.getLevel());
                        data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".xp", playerLevelManager.getXp());
                        data.saveConfig();
                        playerLevelManager.xpCheck(player, playerLevelManager);
                    }
                    catch (NullPointerException e){
                        CC.console("The EntityType " + key + " is wrong or doesn't exists.");
                    }
                }
            }

        }

    }

}

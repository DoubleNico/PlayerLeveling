package me.doublenico.playerleveling.events.impl;

import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.files.DataManager;
import me.doublenico.playerleveling.files.Message;
import me.doublenico.playerleveling.json.DynamicJText;
import me.doublenico.playerleveling.leveling.LevelManager;
import me.doublenico.playerleveling.utils.CC;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Objects;

public class DamageLeveling implements Listener {

    private PlayerLeveling plugin;
    public DataManager data;
    public Message message;

    public DamageLeveling(PlayerLeveling plugin){
        this.plugin = plugin;
        this.data = new DataManager(plugin);
        this.message = new Message(plugin);
    }


    @EventHandler
    public void onAttack(EntityDeathEvent event) {
        Player player = event.getEntity().getKiller();
        Entity victim = event.getEntity();
        if(event.getEntity().getKiller() != player)
            return;

        if (event.getEntity().getKiller() == player) {
            assert player != null;
            LevelManager playerLevelManager = plugin.levelManagerHashMap.get(player.getUniqueId());
            for (String key : Objects.requireNonNull(plugin.getConfig().getConfigurationSection("DAMAGE")).getKeys(false)) {
                ConfigurationSection section = plugin.getConfig().getConfigurationSection("DAMAGE." + key);
                assert section != null;
                int keyXP = section.getInt(".value");
                if (victim.getType() == EntityType.valueOf(key)) {
                    try {
                        playerLevelManager.addExperience(player, keyXP);
                    }
                    catch (NullPointerException e){
                        CC.console("The EntityType " + key + " is wrong or doesn't exists.");
                    }
                }
            }

        }
    }

}

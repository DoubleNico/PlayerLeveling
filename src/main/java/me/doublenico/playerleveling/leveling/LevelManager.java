package me.doublenico.playerleveling.leveling;

import com.google.gson.stream.JsonWriter;
import me.clip.placeholderapi.PlaceholderAPI;
import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.actionbar.ActionBar;
import me.doublenico.playerleveling.bossbar.Bossbar;
import me.doublenico.playerleveling.files.DataManager;
import me.doublenico.playerleveling.files.LevelFile;
import me.doublenico.playerleveling.files.Message;
import me.doublenico.playerleveling.json.DynamicJText;
import me.doublenico.playerleveling.title.Title;
import me.doublenico.playerleveling.utils.CC;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.io.StringWriter;

public class LevelManager {

    private int level;
    private int xp;
    public LevelFile levelFile;
    public DataManager data;
    public Message message;
    public Bossbar bar;

    public LevelManager(int level, int xp) {
        this.level = level;
        this.xp = xp;
        this.data = new DataManager(PlayerLeveling.instance);
        this.levelFile = new LevelFile(PlayerLeveling.instance);
        this.message = new Message(PlayerLeveling.instance);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getXp() {
        return xp;
    }

    public void removeXp(int xp){
        this.xp -= xp;
        if(this.xp <= 0){
            this.xp = 0;
        }
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public void addXp(int xp){
        this.xp += xp;
    }

    public void xpCheck(Player player, LevelManager level) {
        for (String key :
                levelFile.
                        getConfig().
                        getConfigurationSection("Levels").
                        getKeys(false)) {
            ConfigurationSection block = levelFile.getConfig().getConfigurationSection("Levels." + key);
            assert block != null;
            int xp = level.getXp();
            int xpMax = block.getInt("xpMax");
            int levelSection = block.getInt("level");
            
            if (levelSection < level.getLevel()){
                new DynamicJText(CC.color(message.getConfig().getString("MAX_LEVEL")))
                        .send(player);
                level.setXp(0);
                level.setLevel(levelSection);
            }
            if (xp  >= xpMax && levelSection != level.getLevel()) {
                level.setLevel(levelSection);
                level.setXp(0);
                data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".level", levelSection);
                data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".xp", 0);
                data.saveConfig();
                Title title = new Title(PlayerLeveling.instance);
                title.sendTitle(player);
                String levelup = message.getConfig().getString("LEVEL_UP");
                assert levelup != null;
                //TODO: JSON Support
                levelup = PlaceholderAPI.setPlaceholders(player, levelup);
                CC.playerMessage(player, levelup);
                ActionBar actionBar = new ActionBar(PlayerLeveling.instance);
                actionBar.sendActionBar(player);
                boolean enabled = message.getConfig().getBoolean("BossBar.enabled");
                if(enabled) {
                    bar = new Bossbar(PlayerLeveling.instance);
                    bar.createBar(player);
                }
            }
        }

    }


    //TODO: JSON Support
    public void addExperience(Player player, int XP) {
        String expMessage = message.getConfig().getString("EXPERIENCE_GAIN");
        this.setXp(this.getXp() + XP);
        CC.playerMessage(player, CC.color(expMessage).replace("{expGain}", "" + XP));
        data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".level", this.getLevel());
        data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".xp", this.getXp());
        data.saveConfig();
        this.xpCheck(player, this);
    }

}
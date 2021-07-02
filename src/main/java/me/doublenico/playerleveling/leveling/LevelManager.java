package me.doublenico.playerleveling.leveling;

import com.google.common.base.Strings;
import me.clip.placeholderapi.PlaceholderAPI;
import me.doublenico.hypeapi.json.JSON;
import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.files.DataManager;
import me.doublenico.playerleveling.files.LevelFile;
import me.doublenico.playerleveling.files.Message;
import me.doublenico.playerleveling.utils.CC;
import me.doublenico.playerleveling.utils.LevelUP;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class LevelManager {

    private int level;
    private int xp;
    private int maxXP;
    public LevelFile levelFile;
    public DataManager data;
    public Message message;

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

    public void addLevel(int level) {
        this.level += level;
    }

    public void removeLevel(int level){
        this.level -= level;
        if(this.level <= 0){
            this.level = 0;
        }
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

    public int getMaxXP(){
        ConfigurationSection block = levelFile.getConfig().getConfigurationSection("Levels." + (this.getLevel() + 1));
        if(block == null){
            return maxXP = 0;
        } else {
            int levelSection = block.getInt("level");
            if (levelSection != this.getLevel()) {
                maxXP = block.getInt("xpMax");
                return maxXP;
            }
        }
        return maxXP;
    }

    public int getEXPGained(OfflinePlayer player){
        return data.getConfig().getInt("PlayerLevels." + player.getUniqueId() + ".xpGained");
    }


    public void xpCheck(Player player, LevelManager level) {
        ConfigurationSection block = levelFile.getConfig().getConfigurationSection("Levels." + (level.getLevel() + 1));
        if (block == null){
            level.setLevel(0);
            level.setXp(0);
            data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".level", 0);
            data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".xp", 0);
            data.saveConfig();
            for (String s : message.getConfig().getStringList("MAX_LEVEL")){
                s = PlaceholderAPI.setPlaceholders(player, s);
                if(s.startsWith("{")){
                    JSON.sendJSON(player, s);
                } else {
                    CC.playerMessage(player, s);
                }
            }
        } else {
            int levelSection = block.getInt("level");
            int xp = level.getXp();
            int xpMax = block.getInt("xpMax");
            if (xp >= xpMax) {
                level.setLevel(levelSection);
                level.setXp(0);
                data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".level", levelSection);
                data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".xp", 0);
                data.saveConfig();
                LevelUP message = new LevelUP(JavaPlugin.getPlugin(PlayerLeveling.class));
                message.levelMessage(player);
            }
        }
    }




    public void addExperience(Player player, int XP) {
        String expMessage = message.getConfig().getString("EXPERIENCE_GAIN");
        this.setXp(this.getXp() + XP);
        assert expMessage != null;
        if(expMessage.startsWith("{")){
            JSON.sendJSON(player, CC.color(expMessage).replace("{expGain}", "" + XP));
        } else {
            CC.playerMessage(player, CC.color(expMessage).replace("{expGain}", "" + XP));
        }
        data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".level", this.getLevel());
        data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".xp", this.getXp());
        int getExpGained = data.getConfig().getInt("PlayerLevels." + player.getUniqueId() + ".xpGained");
        data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".xpGained", getExpGained + XP);
        data.saveConfig();
        this.xpCheck(player, this);
    }

    public String getProgressBar(int current, int max, int totalBars, char symbol, String completedColor,
                                 String notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }



}
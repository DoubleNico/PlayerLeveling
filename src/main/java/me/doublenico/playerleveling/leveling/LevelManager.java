package me.doublenico.playerleveling.leveling;

import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.files.DataManager;
import me.doublenico.playerleveling.files.LevelFile;
import me.doublenico.playerleveling.files.Message;
import me.doublenico.playerleveling.utils.CC;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class LevelManager {

    private int level;
    private int xp;
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

    public int getXp() {
        return xp;
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
                CC.playerMessage(player, message.getConfig().getString("MAX_LEVEL"));
                level.setXp(0);
                level.setLevel(levelSection);
            }
            if (xp  >= xpMax && levelSection != level.getLevel()) {
                CC.playerMessage(player, message.getConfig().getString("LEVEL_UP"));
                level.setLevel(levelSection);
                level.setXp(0);
                data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".level", levelSection);
                data.getConfig().set("PlayerLevels." + player.getUniqueId() + ".xp", 0);
                data.saveConfig();
            }
        }

    }

}
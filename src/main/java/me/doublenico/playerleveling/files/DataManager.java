package me.doublenico.playerleveling.files;

import me.doublenico.playerleveling.PlayerLeveling;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class DataManager {

    private PlayerLeveling plugin;
    private FileConfiguration levelConfig = null;
    private File configFile = null;
    private final String configName = "data.yml";

    public DataManager(PlayerLeveling plugin){
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reloadConfig(){
        if(this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), configName);

        this.levelConfig = YamlConfiguration.loadConfiguration(this.configFile);
        InputStream defaultStream = this.plugin.getResource(configName);
        if(defaultStream != null){
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.levelConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig(){
        if(this.levelConfig == null){
            reloadConfig();
        }

        return this.levelConfig;
    }

    public void saveConfig(){
        if(this.levelConfig == null || this.configFile == null)
            return;

        try{
            this.getConfig().save(this.configFile);
        }
        catch (IOException e){
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
        }
    }


    public void saveDefaultConfig(){
        if(this.configFile == null){
            this.configFile = new File(this.plugin.getDataFolder(), configName);
        }

        if(!this.configFile.exists()){
            this.plugin.saveResource(configName, false);
        }
    }







}

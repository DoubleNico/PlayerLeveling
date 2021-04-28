package me.doublenico.playerleveling.files;

import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.utils.CC;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class Message extends YamlConfiguration {

    private PlayerLeveling plugin;
    private FileConfiguration messageConfig = null;
    private File configFile = null;
    private final String configName = "messages.yml";

    public Message(PlayerLeveling plugin){
        this.plugin = plugin;
        saveDefaultConfig();
    }

    public void reloadConfig(){
        if(this.configFile == null)
            this.configFile = new File(this.plugin.getDataFolder(), configName);

        this.messageConfig = YamlConfiguration.loadConfiguration(this.configFile);
        InputStream defaultStream = this.plugin.getResource(configName);
        if(defaultStream != null){
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            this.messageConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getConfig(){
        if(this.messageConfig == null){
            reloadConfig();
        }

        return this.messageConfig;
    }

    public void saveConfig(){
        if(this.messageConfig == null || this.configFile == null)
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
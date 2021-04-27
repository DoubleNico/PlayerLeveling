package me.doublenico.playerleveling.files;

import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.utils.CC;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Message extends YamlConfiguration {

    private File configFile;
    private static Message config;

    public void reload() {
        try {
            super.load(this.configFile);
        } catch (Exception e) {
        }

    }

    public static Message getConfig() {
        if (config == null) {
            config = new Message();
        }

        return config;
    }

    public void save() {
        if (!configFile.exists()) {
            try {
                super.save(this.configFile);
            } catch (Exception e) {
            }
        }

    }

    public Message() {
        this.configFile = new File(PlayerLeveling.instance.getDataFolder(), "messages.yml");
        this.saveDefault();
        this.reload();
    }


    public void saveDefault() {
        PlayerLeveling.instance.saveResource("messages.yml", false);
    }

    public static String setString(String message) {
        return Message.getConfig().getString(message);

    }

    public static String setStringColor(String message) {
        return CC.color(Message.getConfig().getString(message));

    }
}
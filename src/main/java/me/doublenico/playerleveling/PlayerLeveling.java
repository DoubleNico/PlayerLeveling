package me.doublenico.playerleveling;

import me.doublenico.playerleveling.events.EventManager;
import me.doublenico.playerleveling.files.DataManager;
import me.doublenico.playerleveling.files.LevelFile;
import me.doublenico.playerleveling.files.Message;
import me.doublenico.playerleveling.leveling.LevelManager;
import me.doublenico.playerleveling.papi.PlaceholderHook;
import me.doublenico.playerleveling.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class PlayerLeveling extends JavaPlugin {

    public static PlayerLeveling instance;
    public HashMap<UUID, LevelManager> levelManagerHashMap;
    public LevelFile levelFile;
    public DataManager data;
    public String prefix = "&b&l[&e&lPlayer&eLeveling&b&l] ";

    @Override
    public void onEnable() {
        instance = this;
        registerEvents();
        registerPlaceholders();
    }

    @Override
    public void onDisable() {
        this.levelManagerHashMap.clear();
    }

    private void registerConfigs(){
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.levelFile = new LevelFile(this);
        this.data = new DataManager(this);
        Message.getConfig().options().copyDefaults(true);
        Message.getConfig().save();
    }

    private void registerPlaceholders() {
        CC.console(prefix + "&bChecking for PlaceholderAPI...");
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            CC.console(prefix + "&bPlaceholders is hooked into plugin.");
            new PlaceholderHook(this).register();
        }
        else {
            CC.console(prefix + "&bPlaceholderAPI is not found, the Placeholders will not work.");
        }

    }

    private void registerEvents(){
        CC.console(prefix + "&bLoading Player Leveling created by &e&lDoubleNico&b for&e&l " + Bukkit.getVersion() + "&b, on&e&l " + Bukkit.getName() + "&b.");
        this.levelManagerHashMap = new HashMap<>();
        CC.console(prefix + "&bRegistering Configs.");
        registerConfigs();
        CC.console(prefix + "&bRegistering Events.");
        new EventManager(this);
    }




}

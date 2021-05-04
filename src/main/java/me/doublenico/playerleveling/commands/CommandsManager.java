package me.doublenico.playerleveling.commands;

import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.commands.impl.LevelCommand;
import me.doublenico.playerleveling.commands.impl.TabCompleter;

import java.util.Objects;

public class CommandsManager {

    private PlayerLeveling plugin;

    public CommandsManager(PlayerLeveling plugin){
        this.plugin = plugin;
        registerCommands();
    }

    public void registerCommands(){
        Objects.requireNonNull(plugin.getCommand("playerleveling")).setExecutor(new LevelCommand(plugin));
        Objects.requireNonNull(plugin.getCommand("playerleveling")).setTabCompleter(new TabCompleter());
    }


}

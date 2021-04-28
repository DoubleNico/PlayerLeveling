package me.doublenico.playerleveling.commands;

import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.commands.impl.LevelCommand;

public class CommandsManager {

    private PlayerLeveling plugin;

    public CommandsManager(PlayerLeveling plugin){
        this.plugin = plugin;
        registerCommands();
    }

    public void registerCommands(){
        //plugin.getCommand("playerleveling").setExecutor(new LevelCommand(plugin));
    }


}

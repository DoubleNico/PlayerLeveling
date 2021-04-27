package me.doublenico.playerleveling.events;

import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.events.impl.BlockBreakLeveling;
import me.doublenico.playerleveling.events.impl.DamageLeveling;
import me.doublenico.playerleveling.events.impl.RegisterPlayer;
import me.doublenico.playerleveling.events.impl.RemovePlayer;

public class EventManager {

    public PlayerLeveling plugin;

    public EventManager(PlayerLeveling plugin){
        this.plugin = plugin;
        registerEvents();
    }

    public void registerEvents(){
        plugin.getServer().getPluginManager().registerEvents(new DamageLeveling(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new RegisterPlayer(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new RemovePlayer(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new BlockBreakLeveling(plugin), plugin);
    }


}

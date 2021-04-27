package me.doublenico.playerleveling.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.leveling.LevelManager;
import org.bukkit.OfflinePlayer;

public class PlaceholderHook extends PlaceholderExpansion {

    private PlayerLeveling plugin;

    public PlaceholderHook(PlayerLeveling plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public String getAuthor(){
        return "DoubleNico";
    }

    @Override
    public String getIdentifier(){
        return "playerleveling";
    }

    @Override
    public String getVersion(){
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier){

        if(player == null){
            return "";
        }

        LevelManager playerLevelManager = plugin.levelManagerHashMap.get(player.getUniqueId());

        // %playerleveling_xp%
        if(identifier.equals("xp")){
            return String.valueOf(playerLevelManager.getXp());
        }

        // %playerleveling_level%
        if(identifier.equals("level")){
            return String.valueOf(playerLevelManager.getLevel());
        }


        return null;
    }



}

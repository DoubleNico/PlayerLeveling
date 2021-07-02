package me.doublenico.playerleveling.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.files.Message;
import me.doublenico.playerleveling.leveling.LevelManager;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderHook extends PlaceholderExpansion {

    private final PlayerLeveling plugin;
    public Message message;

    public PlaceholderHook(PlayerLeveling plugin){

        this.plugin = plugin;
        this.message = new Message(plugin);
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
    public @NotNull String getAuthor(){
        return "DoubleNico";
    }

    @Override
    public @NotNull String getIdentifier(){
        return "playerleveling";
    }

    @Override
    public @NotNull String getVersion(){
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String identifier){

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

        // %playerleveling_maxxp%
        if(identifier.equals("maxxp")){
            return String.valueOf(playerLevelManager.getMaxXP());
        }

        // %playerleveling_xpgained%
        if(identifier.equals("xpgained")){
            return String.valueOf(playerLevelManager.getEXPGained(player));
        }

        // %playerleveling_expbar%
        String completedColor = message.getConfig().getString("EXPBAR.completedColor");
        String notCompletedColor = message.getConfig().getString("EXPBAR.notcompletedColor");
        if(identifier.equals("expbar")){
            return playerLevelManager.getProgressBar(
                            playerLevelManager.getXp(),
                            playerLevelManager.getMaxXP(),
                            30,
                            getHeadCharacter(),
                            completedColor,
                            notCompletedColor);
        }


        return null;
    }

    public char getHeadCharacter()
    {
        String s = message.getConfig().getString("EXPBAR.symbol");
        assert s != null;
        char[] s1 = s.toCharArray();
        return s1[0];
    }

}

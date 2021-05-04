package me.doublenico.playerleveling.utils;

import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.files.DataManager;
import me.doublenico.playerleveling.files.LevelFile;
import me.doublenico.playerleveling.files.Message;
import me.doublenico.playerleveling.leveling.LevelManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Commands {

    private PlayerLeveling plugin;
    public DataManager data;
    public LevelFile levelFile;
    public Message message;

    public Commands(PlayerLeveling plugin) {
        this.plugin = plugin;
        this.data = new DataManager(plugin);
        this.levelFile = new LevelFile(plugin);
        this.message = new Message(plugin);
    }


    public void addEXP(Player player, String value){
        LevelManager playerLevelManager = plugin.levelManagerHashMap.get(player.getUniqueId());
        playerLevelManager.addXp(Integer.parseInt(value));
        CC.playerMessage(player,
                Objects.requireNonNull(message.getConfig().getString("COMMAND.ADDXP")).
                        replace("{amount}", "" + value));
        playerLevelManager.xpCheck(player, playerLevelManager);
    }

    public void addEXPTarget(Player player, Player target, String value){
        LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
        targetLevelManager.addXp(Integer.parseInt(value));
        CC.playerMessage(player,
                Objects.requireNonNull(message.getConfig().getString("COMMAND.ADDXP_PLAYER")).
                        replace("{amount}", "" + value).
                        replace("{targetName}", target.getName()));
        CC.playerMessage(target,
                message.getConfig().getString("COMMAND.ADDXP_TARGET").
                        replace("{amount}", "" + value).
                        replace("{displayName}", player.getName()));
        targetLevelManager.xpCheck(target, targetLevelManager);
    }

    public void addEXPConsole(Player target, String value){
        LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
        targetLevelManager.addXp(Integer.parseInt(value));
        CC.console(Objects.requireNonNull(message.getConfig().getString("COMMAND.ADDXP_PLAYER")).
                replace("{amount}", "" + value).
                replace("{targetName}", target.getName()));
        CC.playerMessage(target,
                message.getConfig().getString("COMMAND.ADDXP_TARGET").
                        replace("{amount}", "" + value).
                        replace("{displayName}", "CONSOLE"));
        targetLevelManager.xpCheck(target, targetLevelManager);
    }



    public void removeEXP(Player player, String value){
        LevelManager playerLevelManager = plugin.levelManagerHashMap.get(player.getUniqueId());
        playerLevelManager.removeXp(Integer.parseInt(value));
        CC.playerMessage(player,
                Objects.requireNonNull(message.getConfig().getString("COMMAND.REMOVEXP")).
                        replace("{amount}", "" + value));
        playerLevelManager.xpCheck(player, playerLevelManager);
    }

    public void removeEXPTarget(Player player, Player target, String value){
        LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
        targetLevelManager.removeXp(Integer.parseInt(value));
        CC.playerMessage(player,
                Objects.requireNonNull(message.getConfig().getString("COMMAND.REMOVEXP_PLAYER")).
                        replace("{amount}", "" + value).
                        replace("{targetName}", target.getName()));
        CC.playerMessage(target,
                message.getConfig().getString("COMMAND.REMOVEXP_TARGET").
                        replace("{amount}", "" + value).
                        replace("{displayName}", player.getName()));
        targetLevelManager.xpCheck(target, targetLevelManager);
    }

    public void removeXPConsole(Player target, String value){
        LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
        targetLevelManager.removeXp(Integer.parseInt(value));
        CC.console(Objects.requireNonNull(message.getConfig().getString("COMMAND.REMOVEXP_PLAYER")).
                replace("{amount}", "" + value).
                replace("{targetName}", target.getName()));
        CC.playerMessage(target,
                message.getConfig().getString("COMMAND.REMOVEXP_TARGET").
                        replace("{amount}", "" + value).
                        replace("{displayName}", "CONSOLE"));
        targetLevelManager.xpCheck(target, targetLevelManager);
    }


    public void setEXP(Player player, String value){
        LevelManager playerLevelManager = plugin.levelManagerHashMap.get(player.getUniqueId());
        playerLevelManager.setXp(Integer.parseInt(value));
        CC.playerMessage(player,
                Objects.requireNonNull(message.getConfig().getString("COMMAND.SETXP")).
                        replace("{amount}", "" + value));
        playerLevelManager.xpCheck(player, playerLevelManager);
    }

    public void setEXPTarget(Player player, Player target, String value){
        LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
        targetLevelManager.setXp(Integer.parseInt(value));
        CC.playerMessage(player,
                Objects.requireNonNull(message.getConfig().getString("COMMAND.SETXP_PLAYER")).
                        replace("{amount}", "" + value).
                        replace("{targetName}", target.getName()));
        CC.playerMessage(target,
                Objects.requireNonNull(message.getConfig().getString("COMMAND.SETXP_TARGET")).
                        replace("{amount}", "" + value).
                        replace("{displayName}", player.getName()));
        targetLevelManager.xpCheck(target, targetLevelManager);
    }

    public void setXPConsole(Player target, String value){
        LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
        targetLevelManager.setXp(Integer.parseInt(value));
        CC.console(Objects.requireNonNull(message.getConfig().getString("COMMAND.SETXP_PLAYER")).
                replace("{amount}", "" + value).
                replace("{targetName}", target.getName()));
        CC.playerMessage(target,
                message.getConfig().getString("COMMAND.SETXP_TARGET").
                        replace("{amount}", "" + value).
                        replace("{displayName}", "CONSOLE"));
        targetLevelManager.xpCheck(target, targetLevelManager);
    }

    public void giveLevel(Player player, String value){
        LevelManager playerLevelManager = plugin.levelManagerHashMap.get(player.getUniqueId());
        playerLevelManager.setLevel(Integer.parseInt(value));
        CC.playerMessage(player,
                Objects.requireNonNull(message.getConfig().getString("COMMAND.GIVELEVEL")).
                        replace("{amount}", "" + value));
        playerLevelManager.xpCheck(player, playerLevelManager);
    }

    public void giveLevelTarget(Player player, Player target, String value){
        LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
        targetLevelManager.setLevel(targetLevelManager.getXp() + Integer.parseInt(value));
        CC.playerMessage(player,
                Objects.requireNonNull(message.getConfig().getString("COMMAND.GIVEXP_PLAYER")).
                        replace("{amount}", "" + value).
                        replace("{targetName}", target.getName()));
        CC.playerMessage(target,
                Objects.requireNonNull(message.getConfig().getString("COMMAND.GIVEXP_TARGET")).
                        replace("{amount}", "" + value).
                        replace("{displayName}", player.getName()));
        targetLevelManager.xpCheck(target, targetLevelManager);
    }

    public void giveLevelConsole(Player target, String value){
        LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
        targetLevelManager.setLevel(Integer.parseInt(value));
        CC.console(Objects.requireNonNull(message.getConfig().getString("COMMAND.GIVEXP_PLAYER")).
                replace("{amount}", "" + value).
                replace("{targetName}", target.getName()));
        CC.playerMessage(target,
                message.getConfig().getString("COMMAND.GIVEXP_TARGET").
                        replace("{amount}", "" + value).
                        replace("{displayName}", "CONSOLE"));
        targetLevelManager.xpCheck(target, targetLevelManager);
    }


    public void reloadConfigs() {
        plugin.reloadConfig();
        data.reloadConfig();
        levelFile.reloadConfig();
        message.reloadConfig();
    }



}

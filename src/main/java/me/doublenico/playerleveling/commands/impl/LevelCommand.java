package me.doublenico.playerleveling.commands.impl;

import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.files.DataManager;
import me.doublenico.playerleveling.files.LevelFile;
import me.doublenico.playerleveling.files.Message;
import me.doublenico.playerleveling.leveling.LevelManager;
import me.doublenico.playerleveling.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelCommand implements CommandExecutor {

    private PlayerLeveling plugin;
    public DataManager data;
    public LevelFile levelFile;
    public Message message;

    public LevelCommand(PlayerLeveling plugin) {
        this.plugin = plugin;
        this.data = new DataManager(plugin);
        this.levelFile = new LevelFile(plugin);
        this.message = new Message(plugin);
    }

    //TODO: MAKE THE COMMANDS MORE SIMPLE..
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            CC.console("&4Only Players can use this command");
        }
        assert sender instanceof Player;
        Player player = (Player) sender;
        LevelManager playerLevelManager = plugin.levelManagerHashMap.get(player.getUniqueId());

        if (args.length == 0) {
            for (String s : message.getConfig().getStringList("HELP_COMMAND")) {
                CC.playerMessage(player, s);
            }
        } else if (args[0].equals("setxp")) {
            Player target = Bukkit.getPlayerExact(args[1]);
            if (target != null) {
                LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
                if (isNum(args[2])) {
                    targetLevelManager.setXp(targetLevelManager.getXp() + Integer.parseInt(args[1]));
                    CC.playerMessage(player,
                            message.getConfig().getString("COMMAND.SETXP_PLAYER").
                                    replace("{amount}", "" + args[2]).
                                    replace("{targetName}", target.getName()));
                    CC.playerMessage(target,
                            message.getConfig().getString("COMMAND.SETXP_TARGET").
                                    replace("{amount}", "" + args[2]).
                                    replace("{displayName}", player.getName()));
                } else {
                    CC.playerMessage(player,
                            message.getConfig().getString("COMMAND.SETXP_ERROR_NUMBER"));
                }

            }
            if(target == null){
                CC.playerMessage(player,
                        message.getConfig().getString("COMMAND.SETXP_ERROR_PLAYER"));
            }
            else if (isNum(args[1])) {
                playerLevelManager.setXp(Integer.parseInt(args[1]));
                CC.playerMessage(player,
                        message.getConfig().getString("COMMAND.SETXP").
                                replace("{amount}", "" + args[1]));
            } else {
                CC.playerMessage(player,
                        message.getConfig().getString("COMMAND.SETXP_ERROR_NUMBER"));
            }
            CC.playerMessage(player,
                    message.getConfig().getString("COMMAND.SETXP_HELP"));
        } else if (args[0].equals("givelevel")) {
            Player target = Bukkit.getPlayerExact(args[1]);
            if (target != null) {
                LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
                if (isNum(args[2])) {
                    targetLevelManager.setLevel(targetLevelManager.getLevel() + Integer.parseInt(args[2]));
                    CC.playerMessage(player,
                            message.getConfig().getString("COMMAND.GIVELEVEL_PLAYER").
                                    replace("{amount}", "" + args[2]).
                                    replace("{targetName}", target.getName()));
                    CC.playerMessage(target,
                            message.getConfig().getString("COMMAND.GIVELEVEL_TARGET").
                                    replace("{amount}", "" + args[2]).
                                    replace("{displayName}", player.getName()));
                } else {
                    CC.playerMessage(player,
                            message.getConfig().getString("COMMAND.GIVELEVEL_ERROR_NUMBER"));
                }

            }
            if(target == null){
                CC.playerMessage(player,
                        message.getConfig().getString("COMMAND.GIVELEVEL_ERROR_PLAYER"));
            }
            else if (isNum(args[1])) {
                playerLevelManager.setLevel(playerLevelManager.getLevel() + Integer.parseInt(args[1]));
                CC.playerMessage(player,
                        message.getConfig().getString("COMMAND.GIVELEVEL").
                                replace("{amount}", "" + args[1]));
            }
            CC.playerMessage(player,
                    message.getConfig().getString("COMMAND.GIVELEVEL_HELP"));
        } else if (args[0].equals("addxp")) {
            Player target = Bukkit.getPlayerExact(args[1]);
            if (target != null) {
                LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
                if (isNum(args[2])) {
                    targetLevelManager.addXp(Integer.parseInt(args[2]));
                    CC.playerMessage(player,
                            message.getConfig().getString("COMMAND.ADDXP_PLAYER").
                                    replace("{amount}", "" + args[2]).
                                    replace("{targetName}", target.getName()));
                    CC.playerMessage(target,
                            message.getConfig().getString("COMMAND.ADDXP_TARGET").
                                    replace("{amount}", "" + args[2]).
                                    replace("{displayName}", player.getName()));
                } else {
                    CC.playerMessage(player,
                            message.getConfig().getString("COMMAND.ADDXP_ERROR_NUMBER"));
                }

            }
            if(target == null){
                CC.playerMessage(player,
                        message.getConfig().getString("COMMAND.ADDXP_ERROR_PLAYER"));
            }
            else if (isNum(args[1])) {
                playerLevelManager.addXp(Integer.parseInt(args[1]));
                CC.playerMessage(player,
                        message.getConfig().getString("COMMAND.ADDXP").
                                replace("{amount}", "" + args[1]));
            } else {
                CC.playerMessage(player,
                        message.getConfig().getString("COMMAND.ADDXP_ERROR_NUMBER"));
            }
            CC.playerMessage(player,
                    message.getConfig().getString("COMMAND.ADDXP_HELP"));
        } else if (args[0].equals("reload")) {
            if(!player.hasPermission("playerleveling.reload")){
                CC.playerMessage(player, message.getConfig().getString("COMMAND.NOPERMS"));
            }
            CC.playerMessage(player, message.getConfig().getString("COMMAND.RELOAD"));
            reloadConfigs();
        }
        else if (args[0].equals("reset")){
            Player target = Bukkit.getPlayerExact(args[1]);
            if(target != null){
                LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
                targetLevelManager.setXp(0);
                targetLevelManager.setLevel(0);
                data.saveConfig();
                CC.playerMessage(target, message.getConfig().getString("COMMAND.RESET_TARGET")
                        .replace("{displayName}", player.getName()));
            }
            else {
                CC.playerMessage(player,
                        message.getConfig().getString("COMMAND.RESET_ERROR_PLAYER"));
            }
            playerLevelManager.setXp(0);
            playerLevelManager.setLevel(0);
            data.saveConfig();
            CC.playerMessage(player, message.getConfig().getString("COMMAND.RESET"));
        }
        else {
            for (String s : message.getConfig().getStringList("HELP_COMMAND")) {
                CC.playerMessage(player, s);
            }
        }
        return true;
    }

    public boolean isNum(String num) {
        try {
            Integer.parseInt(num);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void reloadConfigs() {
        plugin.getConfig();
        plugin.reloadConfig();
        data.saveConfig();
        data.reloadConfig();
        levelFile.saveConfig();
        levelFile.reloadConfig();
        message.saveConfig();
        message.reloadConfig();
    }

}




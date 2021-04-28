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

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            CC.console("&4Only Players can use this command");
            return true;
        }
        Player player = (Player) sender;
        Player target = Bukkit.getPlayerExact(args[1]);
        LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
        LevelManager playerLevelManager = plugin.levelManagerHashMap.get(player.getUniqueId());

        if (args.length == 0) {
            for (String s : message.getConfig().getStringList("HELP_COMMAND")) {
                CC.playerMessage(player, s);
                return true;
            }
        } else if (args[0].equals("givexp")) {
            if(!player.hasPermission("playerleveling.givexp")){
                CC.playerMessage(player, message.getConfig().getString("COMMAND.NOPERMS"));
                return true;
            }
            if (target != null) {
                if (isNum(args[2])) {
                    targetLevelManager.setXp(Integer.parseInt(targetLevelManager.getXp() + args[1]));
                    CC.playerMessage(player,
                            message.getConfig().getString("COMMAND.GIVEXP_PLAYER").
                                    replace("{amount}", "" + args[1]).
                                    replace("{targetName}", target.getName()));
                    CC.playerMessage(target,
                            message.getConfig().getString("COMMAND.GIVEXP_TARGET").
                                    replace("{amount}", "" + args[1]).
                                    replace("{displayName}", player.getName()));
                } else {
                    CC.playerMessage(player,
                            message.getConfig().getString("COMMAND.GIVEXP_ERROR_NUMBER"));
                    return true;
                }

            } else {
                CC.playerMessage(player,
                        message.getConfig().getString("COMMAND.GIVEXP_ERROR_PLAYER"));
            }

            if (isNum(args[1])) {
                playerLevelManager.setXp(Integer.parseInt(playerLevelManager.getXp() + args[1]));
                CC.playerMessage(player,
                        message.getConfig().getString("COMMAND.GIVEXP").
                                replace("{amount}", "" + args[1]));
            } else {
                CC.playerMessage(player,
                        message.getConfig().getString("COMMAND.GIVEXP_ERROR_NUMBER"));
                return true;
            }
            CC.playerMessage(player,
                    message.getConfig().getString("COMMAND.GIVEXP_HELP"));
        } else if (args[0].equals("givelevel")) {
            if(!player.hasPermission("playerleveling.givelevel")){
                CC.playerMessage(player, message.getConfig().getString("COMMAND.NOPERMS"));
                return true;
            }
            if (target != null) {
                if (isNum(args[2])) {
                    targetLevelManager.setLevel(Integer.parseInt(targetLevelManager.getLevel() + args[1]));
                    CC.playerMessage(player,
                            message.getConfig().getString("COMMAND.GIVELEVEL_PLAYER").
                                    replace("{amount}", "" + args[1]).
                                    replace("{targetName}", target.getName()));
                    CC.playerMessage(target,
                            message.getConfig().getString("COMMAND.GIVELEVEL_TARGET").
                                    replace("{amount}", "" + args[1]).
                                    replace("{displayName}", player.getName()));
                } else {
                    CC.playerMessage(player,
                            message.getConfig().getString("COMMAND.GIVELEVEL_ERROR_NUMBER"));
                    return true;
                }

            } else {
                CC.playerMessage(player,
                        message.getConfig().getString("COMMAND.GIVELEVEL_ERROR_PLAYER"));
            }

            if (isNum(args[1])) {
                playerLevelManager.setLevel(Integer.parseInt(playerLevelManager.getLevel() + args[1]));
                CC.playerMessage(player,
                        message.getConfig().getString("COMMAND.GIVELEVEL").
                                replace("{amount}", "" + args[1]));
            } else {
                CC.playerMessage(player,
                        message.getConfig().getString("COMMAND.GIVELEVEL_ERROR_NUMBER"));
                return true;
            }
            CC.playerMessage(player,
                    message.getConfig().getString("COMMAND.GIVELEVEL_HELP"));
            return true;
        } else if (args[0].equals("reload")) {
            if(!player.hasPermission("playerleveling.reload")){
                CC.playerMessage(player, message.getConfig().getString("COMMAND.NOPERMS"));
                return true;
            }
            CC.playerMessage(player, message.getConfig().getString("COMMAND.RELOAD"));
            reloadConfigs();
            return true;
        }
        else if (args[0].equals("reset")){
            if(!player.hasPermission("playerleveling.reset")){
                CC.playerMessage(player, message.getConfig().getString("COMMAND.NOPERMS"));
                return true;
            }
            if(target != null){
                targetLevelManager.setXp(0);
                targetLevelManager.setLevel(0);
                data.saveConfig();
                CC.playerMessage(target, message.getConfig().getString("COMMAND.RESET_TARGET")
                        .replace("{displayName}", player.getName()));
            }
            else {
                CC.playerMessage(player,
                        message.getConfig().getString("COMMAND.RESET_ERROR_PLAYER"));
                return true;
            }
            playerLevelManager.setXp(0);
            playerLevelManager.setLevel(0);
            data.saveConfig();
            CC.playerMessage(player, message.getConfig().getString("COMMAND.RESET"));
            return true;
        }
        else {
            for (String s : message.getConfig().getStringList("HELP_COMMAND")) {
                CC.playerMessage(player, s);
                return true;
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




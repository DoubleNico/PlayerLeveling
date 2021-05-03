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
        }
        assert sender instanceof Player;
        Player player = (Player) sender;
        LevelManager playerLevelManager = plugin.levelManagerHashMap.get(player.getUniqueId());

        if (args.length == 0) {
            for (String s : message.getConfig().getStringList("HELP_COMMAND")) {
                CC.playerMessage(player, s);
            }
        } else if (args[0].equals("setxp")) {
            if(args.length > 1){
                Player target = Bukkit.getPlayerExact(args[1]);
                if(isNum(args[1]) && target == null){
                    setEXP(player, args[1]);
                }
                else if(target != null && !isNum(args[1])){
                    if(args.length > 2){
                        if(target == player){
                            CC.playerMessage(player, message.getConfig().getString("COMMAND.SETXP_NOEXIST"));
                        }
                        else {
                            setEXPTarget(player, target, args[2]);
                        }
                    }
                }
            }
        }
        else if (args[0].equals("addxp")) {
            if(args.length > 1){
                Player target = Bukkit.getPlayerExact(args[1]);
                if(isNum(args[1]) && target == null){
                    addEXP(player, args[1]);
                }
                else if(target != null && !isNum(args[1])){
                    if(args.length > 2){
                        if(target == player){
                            CC.playerMessage(player, message.getConfig().getString("COMMAND.ADDXP_NOEXIST"));
                        }
                        else {
                            addEXPTarget(player, target, args[2]);
                        }
                    }
                }
            }
        }
        else if (args[0].equals("givelevel")) {
            if(args.length > 1){
                Player target = Bukkit.getPlayerExact(args[1]);
                if(isNum(args[1]) && target == null){
                    giveLevel(player, args[1]);
                }
                else if(target != null && !isNum(args[1])){
                    if(args.length > 2){
                        if(target == player){
                            CC.playerMessage(player, message.getConfig().getString("COMMAND.GIVELEVEL_NOEXIST"));
                        }
                        else {
                            giveLevelTarget(player, target, args[2]);

                        }
                    }
                }
            }
        }
        else if (args[0].equals("reload")) {
            if(!player.hasPermission("playerleveling.reload")){
                CC.playerMessage(player, message.getConfig().getString("COMMAND.NOPERMS"));
            }
            CC.playerMessage(player, message.getConfig().getString("COMMAND.RELOAD"));
            reloadConfigs();
        }
        else if (args[0].equals("reset")) {
            if(args.length > 1){
                Player target = Bukkit.getPlayerExact(args[1]);
                if(target != null){
                    if(target == player){
                        CC.playerMessage(player, message.getConfig().getString("COMMAND.RESET_NOEXIST"));
                    }
                    else {
                        LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
                        targetLevelManager.setXp(0);
                        targetLevelManager.setLevel(0);
                        data.saveConfig();
                        CC.playerMessage(target, message.getConfig().getString("COMMAND.RESET_TARGET")
                                .replace("{displayName}", player.getName()));
                    }

                }
            } else {
                playerLevelManager.setXp(0);
                playerLevelManager.setLevel(0);
                data.saveConfig();
                CC.playerMessage(player, message.getConfig().getString("COMMAND.RESET"));
            }
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

    public void setEXPTarget(Player player, Player target, String value){
        LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
        targetLevelManager.setXp(targetLevelManager.getXp() + Integer.parseInt(value));
        CC.playerMessage(player,
                message.getConfig().getString("COMMAND.SETXP_PLAYER").
                        replace("{amount}", "" + value).
                        replace("{targetName}", target.getName()));
        CC.playerMessage(target,
                message.getConfig().getString("COMMAND.SETXP_TARGET").
                        replace("{amount}", "" + value).
                        replace("{displayName}", player.getName()));
        targetLevelManager.xpCheck(player, targetLevelManager);
    }


    public void addEXP(Player player, String value){
        LevelManager playerLevelManager = plugin.levelManagerHashMap.get(player.getUniqueId());
        playerLevelManager.addXp(Integer.parseInt(value));
        CC.playerMessage(player,
                message.getConfig().getString("COMMAND.ADDXP").
                        replace("{amount}", "" + value));
        playerLevelManager.xpCheck(player, playerLevelManager);
    }

    public void addEXPTarget(Player player, Player target, String value){
        LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
        targetLevelManager.setXp(targetLevelManager.getXp() + Integer.parseInt(value));
        CC.playerMessage(player,
                message.getConfig().getString("COMMAND.ADDXP_PLAYER").
                        replace("{amount}", "" + value).
                        replace("{targetName}", target.getName()));
        CC.playerMessage(target,
                message.getConfig().getString("COMMAND.ADDXP_TARGET").
                        replace("{amount}", "" + value).
                        replace("{displayName}", player.getName()));
        targetLevelManager.xpCheck(player, targetLevelManager);
    }

    public void setEXP(Player player, String value){
        LevelManager playerLevelManager = plugin.levelManagerHashMap.get(player.getUniqueId());
        playerLevelManager.setXp(Integer.parseInt(value));
        CC.playerMessage(player,
                message.getConfig().getString("COMMAND.SETXP").
                        replace("{amount}", "" + value));
        playerLevelManager.xpCheck(player, playerLevelManager);
    }

    public void giveLevel(Player player, String value){
        LevelManager playerLevelManager = plugin.levelManagerHashMap.get(player.getUniqueId());
        playerLevelManager.setLevel(Integer.parseInt(value));
        CC.playerMessage(player,
                message.getConfig().getString("COMMAND.GIVELEVEL").
                        replace("{amount}", "" + value));
        playerLevelManager.xpCheck(player, playerLevelManager);
    }

    public void giveLevelTarget(Player player, Player target, String value){
        LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
        targetLevelManager.setXp(targetLevelManager.getXp() + Integer.parseInt(value));
        CC.playerMessage(player,
                message.getConfig().getString("COMMAND.GIVEXP_PLAYER").
                        replace("{amount}", "" + value).
                        replace("{targetName}", target.getName()));
        CC.playerMessage(target,
                message.getConfig().getString("COMMAND.GIVEXP_TARGET").
                        replace("{amount}", "" + value).
                        replace("{displayName}", player.getName()));
        targetLevelManager.xpCheck(player, targetLevelManager);
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




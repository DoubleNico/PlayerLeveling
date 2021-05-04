package me.doublenico.playerleveling.commands.impl;

import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.files.DataManager;
import me.doublenico.playerleveling.files.LevelFile;
import me.doublenico.playerleveling.files.Message;
import me.doublenico.playerleveling.leveling.LevelManager;
import me.doublenico.playerleveling.utils.CC;
import me.doublenico.playerleveling.utils.Commands;
import me.doublenico.playerleveling.utils.CommandsJSON;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class LevelCommand implements CommandExecutor {

    private PlayerLeveling plugin;
    public DataManager data;
    public LevelFile levelFile;
    public Message message;
    public Commands commands;

    public LevelCommand(PlayerLeveling plugin) {
        this.plugin = plugin;
        this.data = new DataManager(plugin);
        this.levelFile = new LevelFile(plugin);
        this.message = new Message(plugin);
        this.commands = new Commands(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if (args.length == 0) {
            for (String s : message.getConfig().getStringList("HELP_COMMAND")) {
                sender.sendMessage(CC.color(s));
            }
        } else if (args[0].equals("setxp")) {
            if (sender.hasPermission("playerleveling.setxp")){
                if (args.length > 1) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (isNum(args[1]) && target == null && sender instanceof Player) {
                        Player player = (Player) sender;
                        commands.setEXP(player, args[1]);
                    } else if (target != null && !isNum(args[1])) {
                        if (args.length > 2) {
                            if(sender instanceof Player) {
                                Player player = (Player) sender;
                                if (target == player) {
                                    CC.playerMessage(player, message.getConfig().getString("COMMAND.SETXP_NOEXIST"));
                                } else {
                                    commands.setEXPTarget(player, target, args[2]);
                                }
                            } else {
                                commands.setXPConsole(target, args[2]);
                            }
                        }
                    }
                }
            } else {
                sender.sendMessage(CC.color(message.getConfig().getString("COMMAND.NOPERMS")));
            }
        }
        else if (args[0].equals("addxp")) {
            if(sender.hasPermission("playerleveling.addxp")) {
                if (args.length > 1) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (isNum(args[1]) && target == null && sender instanceof Player) {
                        Player player = (Player) sender;
                        commands.addEXP(player, args[1]);
                    } else if (target != null && !isNum(args[1])) {
                        if(sender instanceof Player) {
                            Player player = (Player) sender;
                            if (args.length > 2) {
                                if (target == player) {
                                    CC.playerMessage(player, message.getConfig().getString("COMMAND.ADDXP_NOEXIST"));
                                } else {
                                    commands.addEXPTarget(player, target, args[2]);
                                }
                            }
                        } else {
                            if (args.length > 2) {
                                commands.addEXPConsole(target, args[2]);
                            }
                        }
                    }
                }
            } else {
                sender.sendMessage(CC.color(message.getConfig().getString("COMMAND.NOPERMS")));
            }
        }
        else if (args[0].equals("givelevel")) {
            if(sender.hasPermission("playerleveling.givelevel")) {
                if (args.length > 1) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (isNum(args[1]) && target == null && sender instanceof Player) {
                        Player player = (Player) sender;
                        commands.giveLevel(player, args[1]);
                    } else if (target != null && !isNum(args[1])) {
                        if(sender instanceof Player) {
                            Player player = (Player) sender;
                            if (args.length > 2) {
                                if (target == player) {
                                    CC.playerMessage(player, message.getConfig().getString("COMMAND.GIVELEVEL_NOEXIST"));
                                } else {
                                    commands.giveLevelTarget(player, target, args[2]);

                                }
                            }
                        } else {
                            if (args.length > 2) {
                                commands.giveLevelConsole(target, args[2]);
                            }
                        }
                    }
                }
            } else {
                sender.sendMessage(CC.color(message.getConfig().getString("COMMAND.NOPERMS")));
            }
        }
        else if (args[0].equals("removexp")) {
            if(sender.hasPermission("playerleveling.removexp")) {
                if (args.length > 1) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (isNum(args[1]) && target == null && sender instanceof Player) {
                        Player player = (Player) sender;
                        commands.removeEXP(player, args[1]);
                    } else if (target != null && !isNum(args[1])) {
                        if(sender instanceof Player) {
                            if (args.length > 2) {
                                Player player = (Player) sender;
                                if (target == player) {
                                    CC.playerMessage(player, message.getConfig().getString("COMMAND.REMOVEXP_NOEXIST"));
                                } else {
                                    commands.removeEXPTarget(player, target, args[2]);

                                }
                            }
                        } else {
                            if (args.length > 2) {
                                commands.removeXPConsole(target, args[2]);
                            }
                        }
                    }
                }
            } else {
                sender.sendMessage(CC.color(message.getConfig().getString("COMMAND.NOPERMS")));
            }
        }
        else if (args[0].equals("reload")) {
            if(!sender.hasPermission("playerleveling.reload")){
                sender.sendMessage(CC.color(message.getConfig().getString("COMMAND.NOPERMS")));
            }
            sender.sendMessage(CC.color(message.getConfig().getString("COMMAND.RELOAD")));
            commands.reloadConfigs();
        }
        else if (args[0].equals("reset")) {
            if(sender.hasPermission("playerleveling.reset")) {
                if (args.length > 1) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target != null) {
                        if(sender instanceof Player) {
                            Player player = (Player) sender;
                            if (target == player) {
                                CC.playerMessage(player, message.getConfig().getString("COMMAND.RESET_NOEXIST"));
                            } else {
                                LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
                                targetLevelManager.setXp(0);
                                targetLevelManager.setLevel(0);
                                data.saveConfig();
                                CC.playerMessage(target, Objects.requireNonNull(message.getConfig().getString("COMMAND.RESET_TARGET"))
                                        .replace("{displayName}", player.getName()));
                            }
                        } else {
                            LevelManager targetLevelManager = plugin.levelManagerHashMap.get(target.getUniqueId());
                            targetLevelManager.setXp(0);
                            targetLevelManager.setLevel(0);
                            data.saveConfig();
                            CC.playerMessage(target, Objects.requireNonNull(message.getConfig().getString("COMMAND.RESET_TARGET"))
                                    .replace("{displayName}", "CONSOLE"));
                        }

                    }
                }
                else {
                    if(sender instanceof Player) {
                        Player player = (Player) sender;
                        LevelManager playerLevelManager = plugin.levelManagerHashMap.get(player.getUniqueId());
                        playerLevelManager.setXp(0);
                        playerLevelManager.setLevel(0);
                        data.saveConfig();
                        CC.playerMessage(player, message.getConfig().getString("COMMAND.RESET"));
                    }
                }
            } else {
                sender.sendMessage(CC.color(message.getConfig().getString("COMMAND.NOPERMS")));
            }
        }
        else if(args[0].equals("about")){
            if(sender.hasPermission("playerleveling.about")){
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    CommandsJSON.aboutJSON(player);
                }
                for (String s : message.getConfig().getStringList("HELP_COMMAND")) {
                    sender.sendMessage(CC.color(s));
                }
            } else {
                sender.sendMessage(CC.color(message.getConfig().getString("COMMAND.NOPERMS")));
            }
        }
        else {
            for (String s : message.getConfig().getStringList("HELP_COMMAND")) {
                sender.sendMessage(CC.color(s));
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

}




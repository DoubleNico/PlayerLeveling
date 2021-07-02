package me.doublenico.playerleveling.commands.impl;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length == 1){
            List<String> arguments = new ArrayList<>();
            if(sender.hasPermission("playerleveling.setxp")) {
                arguments.add("setxp");
            }
            if(sender.hasPermission("playerleveling.addxp")) {
                arguments.add("addxp");
            }
            if(sender.hasPermission("playerleveling.removexp")) {
                arguments.add("removexp");
            }
            if(sender.hasPermission("playerleveling.setlevel")) {
                arguments.add("setlevel");
            }
            if(sender.hasPermission("playerleveling.addlevel")) {
                arguments.add("addlevel");
            }
            if(sender.hasPermission("playerleveling.removelevel")) {
                arguments.add("removelevel");
            }
            if(sender.hasPermission("playerleveling.reset")) {
                arguments.add("reset");
            }
            if(sender.hasPermission("playerleveling.reload")) {
                arguments.add("reload");
            }
            if(sender.hasPermission("playerleveling.about")) {
                arguments.add("about");
            }
            if(sender.hasPermission("playerleveling.stats")) {
                arguments.add("stats");
            }
            return arguments;

        }
        else if(args.length == 2){
            List<String> arguments = new ArrayList<>();
            Player[] players = new Player[Bukkit.getServer().getOnlinePlayers().size()];
            Bukkit.getServer().getOnlinePlayers().toArray(players);
            if(args[0].equals("setxp") && sender.hasPermission("playerleveling.setxp")){
                arguments.add("<number>|<playername>");
                for (int i = 0; i < players.length; i++){
                    arguments.add(players[i].getName());
                }
            }
            if(args[0].equals("addxp") && sender.hasPermission("playerleveling.addxp")){
                arguments.add("<number>|<playername>");
                for (int i = 0; i < players.length; i++){
                    arguments.add(players[i].getName());
                }
            }
            if(args[0].equals("setlevel") && sender.hasPermission("playerleveling.setlevel")){
                arguments.add("<number>|<playername>");
                for (int i = 0; i < players.length; i++){
                    arguments.add(players[i].getName());
                }
            }
            if(args[0].equals("removelevel") && sender.hasPermission("playerleveling.removelevel")){
                arguments.add("<number>|<playername>");
                for (int i = 0; i < players.length; i++){
                    arguments.add(players[i].getName());
                }
            }
            if(args[0].equals("stats") && sender.hasPermission("playerleveling.stats")){
                arguments.add("<number>|<playername>");
                for (int i = 0; i < players.length; i++){
                    arguments.add(players[i].getName());
                }
            }
            if(args[0].equals("addlevel") && sender.hasPermission("playerleveling.addlevel")){
                arguments.add("<number>|<playername>");
                for (int i = 0; i < players.length; i++){
                    arguments.add(players[i].getName());
                }
            }
            if(args[0].equals("reset") && sender.hasPermission("playerleveling.reset")){
                arguments.add("<number>|<playername>");
                for (int i = 0; i < players.length; i++){
                    arguments.add(players[i].getName());
                }
            }
            if(args[0].equals("removexp") && sender.hasPermission("playerleveling.removexp")){
                arguments.add("<number>|<playername>");
                for (int i = 0; i < players.length; i++){
                    arguments.add(players[i].getName());
                }
            }
            return arguments;
        }
        else if(args.length == 3){
            List<String> arguments = new ArrayList<>();
            if(Bukkit.getPlayerExact(args[1]) != null){
                arguments.add("<number>");
            }
            return arguments;
        }
        return null;
    }


}

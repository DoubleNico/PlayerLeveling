package me.doublenico.playerleveling.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CC {


    public static String color(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void playerMessage(Player player, String message){
        player.sendMessage(CC.color(message));
    }

    public static void console(String message){
        Bukkit.getConsoleSender().sendMessage(CC.color(message));
    }



}

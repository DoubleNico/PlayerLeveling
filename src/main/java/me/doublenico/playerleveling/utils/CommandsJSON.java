package me.doublenico.playerleveling.utils;

import me.doublenico.playerleveling.json.DynamicJText;
import org.bukkit.entity.Player;

public class CommandsJSON {

    public static void aboutJSON(Player player){
        player.sendMessage("");
        CC.playerMessage(player, "&bPlayerLeveling created by");
        new DynamicJText(CC.color("      &e&lDoubleNico"))
                .onHover("&bClick to go to my &e&lGitHub Profile")
                .url("https://github.com/DoubleNico")
                .send(player);
        player.sendMessage("");
        player.sendMessage(CC.color("&bCommands:"));
        new DynamicJText(CC.color(" &e&l- &b/playerleveling setxp <number>|<playername> <number>"))
                .onHover("&bSet Experience to a player.")
                .suggest("/playerleveling setxp <number>|<playername> <number>")
                .send(player);
        new DynamicJText(CC.color(" &e&l- &b/playerleveling addxp <number>|<playername> <number>"))
                .onHover("&bAdds Experience to a player")
                .suggest("/playerleveling addxp <number>|<playername> <number>")
                .send(player);
        new DynamicJText(CC.color(" &e&l- &b/playerleveling removexp <number>|<playername> <number>"))
                .onHover("&bRemoves Experience from a player")
                .suggest("/playerleveling removexp <number>|<playername> <number>")
                .send(player);
        new DynamicJText(CC.color(" &e&l- &b/playerleveling givelevel <number>|<playername> <number>"))
                .onHover("&bGives a level to a player")
                .suggest("/playerleveling givelevel <number>|<playername> <number>")
                .send(player);
        new DynamicJText(CC.color(" &e&l- &b/playerleveling reset <number>|<playername> <number>"))
                .onHover("&bResets a player's level")
                .suggest("/playerleveling reset <number>|<playername> <number>")
                .send(player);
        new DynamicJText(CC.color(" &e&l- &b/playerleveling reload"))
                .onHover("&bReloads all the configs")
                .suggest("/playerleveling reload")
                .send(player);
        new DynamicJText(CC.color(" &e&l- &b/playerleveling about"))
                .onHover("&b:)")
                .suggest("/playerleveling about")
                .send(player);
        player.sendMessage("");
        player.sendMessage(CC.color("&bPlaceholders:"));
        new DynamicJText(CC.color(" &e&l- &b%playerleveling_level%"))
                .onHover("&bGet player's level")
                .suggest("%playerleveling_level%")
                .send(player);
        new DynamicJText(CC.color(" &e&l- &b%playerleveling_xp%"))
                .onHover("&bGet player's xp")
                .suggest("%%playerleveling_xp%")
                .send(player);
        player.sendMessage("");
    }
}

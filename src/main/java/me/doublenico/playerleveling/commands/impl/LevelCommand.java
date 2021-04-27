package me.doublenico.playerleveling.commands.impl;

import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.files.Message;
import me.doublenico.playerleveling.utils.CC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LevelCommand implements CommandExecutor {

    private PlayerLeveling plugin;

    public LevelCommand(PlayerLeveling plugin){
        this.plugin = plugin;
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            CC.console("&4Only Players can use this command");
            return true;
        }
        Player player = (Player) sender;

        if(args.length == 0){
            for(String s : Message.getConfig().getStringList("HELP_COMMAND")){
                CC.playerMessage(player, s);
                return true;
            }
        }

        if(args.length > 0){

        }




        return true;
    }



}

package me.doublenico.playerleveling.title;

import me.clip.placeholderapi.PlaceholderAPI;
import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.files.Message;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Title {

    private final Message message;

    public Title(PlayerLeveling plugin){
        this.message = new Message(plugin);
    }

    public void sendTitle(Player p) {
        boolean enabled = message.getConfig().getBoolean("Title.enable");
        if(enabled) {
            String tit = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(message.getConfig().getString("Title.title")));
            String subtit = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(message.getConfig().getString("Title.subtitle")));
            tit = PlaceholderAPI.setPlaceholders(p, tit);
            subtit = PlaceholderAPI.setPlaceholders(p, subtit);
            int fadein = message.getConfig().getInt("Title.fadein");
            int stay = message.getConfig().getInt("Title.stay");
            int fadeout = message.getConfig().getInt("Title.fadeout");

            p.sendTitle(tit, subtit, fadein, stay, fadeout);
        }
    }

}

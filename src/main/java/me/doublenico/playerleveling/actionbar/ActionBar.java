package me.doublenico.playerleveling.actionbar;

import me.clip.placeholderapi.PlaceholderAPI;
import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.files.Message;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.entity.Player;

public class ActionBar {

    private PlayerLeveling plugin;
    public Message message;

    public ActionBar(PlayerLeveling plugin){
        this.plugin = plugin;
        this.message = new Message(plugin);

    }

    public void sendActionBar(Player player){
        boolean enabled = message.getConfig().getBoolean("ActionBar.enable");
        if(enabled) {
            String barMessage = message.getConfig().getString("ActionBar.message");
            barMessage = PlaceholderAPI.setPlaceholders(player, barMessage);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    new TextComponent(ChatColor.translateAlternateColorCodes('&', barMessage)));
        }
    }

}

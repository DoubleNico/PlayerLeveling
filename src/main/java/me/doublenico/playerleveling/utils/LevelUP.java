package me.doublenico.playerleveling.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import me.doublenico.hypeapi.actionbar.ActionBar;
import me.doublenico.hypeapi.json.JSON;
import me.doublenico.hypeapi.title.Title;
import me.doublenico.playerleveling.PlayerLeveling;
import me.doublenico.playerleveling.bossbar.Bossbar;
import me.doublenico.playerleveling.files.Message;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class LevelUP {

    private PlayerLeveling plugin;
    private Message message;
    public Bossbar bar;


    public LevelUP(PlayerLeveling plugin){
        this.plugin = plugin;
        this.message = new Message(plugin);
    }


    public void levelMessage(Player player){
        boolean enabledTitle = message.getConfig().getBoolean("Title.enable");
        if (enabledTitle) {
            String tit = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(message.getConfig().getString("Title.title")));
            String subtit = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(message.getConfig().getString("Title.subtitle")));
            tit = PlaceholderAPI.setPlaceholders(player, tit);
            subtit = PlaceholderAPI.setPlaceholders(player, subtit);
            int fadein = message.getConfig().getInt("Title.fadein");
            int stay = message.getConfig().getInt("Title.stay");
            int fadeout = message.getConfig().getInt("Title.fadeout");
            if (LegacyVersion.isLegacyVersion()) {
                Title.sendTitle(player, tit, subtit, fadein, stay, fadeout);
            } else {
                player.sendTitle(tit, subtit, fadein, stay, fadeout);
            }
        }
        List<String> levelup = message.getConfig().getStringList("LEVEL_UP");
        for (String s : levelup) {
            s = PlaceholderAPI.setPlaceholders(player, s);
            if(s.startsWith("{")){
                JSON.sendJSON(player, s);
            } else {
                CC.playerMessage(player, s);
            }
        }
        boolean enabledBar = message.getConfig().getBoolean("ActionBar.enable");
        if (enabledBar) {
            String barMessage = message.getConfig().getString("ActionBar.message");
            assert barMessage != null;
            barMessage = PlaceholderAPI.setPlaceholders(player, barMessage);
            if(LegacyVersion.isLegacyVersion()) {
                ActionBar.sendBar(player, barMessage);
            } else {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        new ComponentBuilder(CC.color(barMessage)).create());
            }

        }
        boolean enabled = message.getConfig().getBoolean("BossBar.enabled");
        if (enabled) {
            try {
                bar = new Bossbar(PlayerLeveling.instance);
                bar.createBar(player);
            } catch (NoSuchMethodError e) {
                CC.console(PlayerLeveling.prefix + "&4The bossbar works only for 1.9+");
            }
        }
    }

}

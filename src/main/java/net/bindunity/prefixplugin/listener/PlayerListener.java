package net.bindunity.prefixplugin.listener;

import com.google.gson.JsonObject;
import net.bindunity.prefixplugin.PrefixPlugin;
import net.bindunity.prefixplugin.manager.FileManager;
import net.bindunity.prefixplugin.manager.JsonManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

/**
 * Created by tckz916 on 2015/10/07.
 */
public class PlayerListener implements Listener {

    private PrefixPlugin plugin = PrefixPlugin.getInstance();

    private FileManager fileManager = plugin.getFileManager();
    private JsonManager jsonManager = plugin.getJsonManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        File file = fileManager.loadFile(plugin.getDataFolder().toString() + "\\players", player.getUniqueId().toString() + ".json");
        JsonObject root = jsonManager.playerJson(file, player);
        String json1 = jsonManager.toJson(root);
        jsonManager.writeJson(file, json1);


        String prefix = root.getAsJsonObject("Player").get("prefix").getAsString();

        player.setDisplayName(coloring(prefix + player.getName()));


    }

    private String coloring(String message) {
        return plugin.getMessageFormat().coloring(message);
    }
}

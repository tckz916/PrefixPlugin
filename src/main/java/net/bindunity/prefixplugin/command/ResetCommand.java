package net.bindunity.prefixplugin.command;

import com.google.gson.JsonObject;
import net.bindunity.prefixplugin.PrefixPlugin;
import net.bindunity.prefixplugin.api.BaseCommand;
import net.bindunity.prefixplugin.manager.FileManager;
import net.bindunity.prefixplugin.manager.JsonManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

/**
 * Created by hayatarou_ on 2016/01/17.
 */
public class ResetCommand extends BaseCommand {

    private PrefixPlugin plugin = PrefixPlugin.getInstance();

    private FileManager fileManager = plugin.getFileManager();

    private JsonManager jsonManager = plugin.getJsonManager();

    public static final String PERMISSION = "prefixplugin.command.rename";

    public static final String DESCRIPTION = "Rename Command";

    public static final String USAGE = "/prefix reset <player>";

    public ResetCommand(CommandSender sender) {
        super(sender, PERMISSION, DESCRIPTION, USAGE);
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {

        if (!hasPermission()) {
            sender.sendMessage(format(false, "error.no-permission"));
            return;
        }

        if (args.length < 2) {
            sendUsage();
            return;
        }
        if (getPlayer(args[1]) == null) {
            sender.sendMessage(format(false, "error.player-not-found"));
            return;
        }
        getPlayer(args[1]).setDisplayName(getPlayer(args[1]).getName());
        setprefix(getPlayer(args[1]), "");
        sender.sendMessage(format(true, "message.prefix.reset")
                .replace("%player%", getPlayer(args[1]).getName()));
    }

    private Player getPlayer(String name) {
        return plugin.getServer().getPlayer(name);
    }

    private void setprefix(Player player, String prefix) {
        File file = fileManager.loadFile(plugin.getDataFolder().toString() + "\\players", player.getUniqueId().toString() + ".json");
        String json = null;
        try {
            json = jsonManager.getJson(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonObject playerdata = jsonManager.createJsonObject(json);

        playerdata.getAsJsonObject("Player").addProperty("prefix", prefix);

        String json1 = jsonManager.toJson(playerdata);

        player.setDisplayName(coloring(prefix + player.getName()));
        jsonManager.writeJson(file, json1);
    }

    private String format(boolean prefix, String key, Object... args) {
        return plugin.getMessageFormat().format(prefix, key, args);
    }

    private String coloring(String message) {
        return plugin.getMessageFormat().coloring(message);
    }
}

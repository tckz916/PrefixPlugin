package net.bindunity.prefixplugin.command;

import com.google.gson.JsonObject;
import net.bindunity.prefixplugin.PrefixPlugin;
import net.bindunity.prefixplugin.api.BaseCommand;
import net.bindunity.prefixplugin.manager.FileManager;
import net.bindunity.prefixplugin.manager.JsonManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

/**
 * Created by hayatarou_ on 2016/01/17.
 */
public class SetCommand extends BaseCommand {

    private PrefixPlugin plugin = PrefixPlugin.getInstance();

    private FileManager fileManager = plugin.getFileManager();

    private JsonManager jsonManager = plugin.getJsonManager();

    public static final String PERMISSION = "prefixplugin.command.rename";

    public static final String DESCRIPTION = "Rename Command";

    public static final String USAGE = "/prefix set <name> <player>";

    public SetCommand(CommandSender sender) {
        super(sender, PERMISSION, DESCRIPTION, USAGE);
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {

        if (!hasPermission()) {
            sender.sendMessage(format(false, "error.no-permission"));
            return;
        }

        FileConfiguration fileConfiguration = plugin.getConfig();

        ConfigurationSection prefix = fileConfiguration.getConfigurationSection("prefix");

        if (args.length < 3) {
            sendUsage();
            return;
        }
        if (!prefix.getKeys(false).contains(args[1])) {
            sender.sendMessage(format(false, "error.already-prefix"));
            return;
        }
        if (getPlayer(args[2]) == null) {
            sender.sendMessage(format(false, "error.player-not-found"));
            return;
        }
        String pn = fileConfiguration.getString("prefix." + args[1]);
        getPlayer(args[2]).setDisplayName(coloring(pn + "`r" + getPlayer(args[2]).getName()));
        setprefix(getPlayer(args[2]), pn);

        sender.sendMessage(format(true, "message.prefix.set")
                .replace("%prefix%", args[1])
                .replace("%player%", getPlayer(args[2]).getName()));
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

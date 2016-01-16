package net.bindunity.prefixplugin.command;

import net.bindunity.prefixplugin.PrefixPlugin;
import net.bindunity.prefixplugin.api.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by hayatarou_ on 2016/01/17.
 */
public class GetCommand extends BaseCommand {

    private PrefixPlugin plugin = PrefixPlugin.getInstance();

    public static final String PERMISSION = "prefixplugin.command.get";

    public static final String DESCRIPTION = "Get Command";

    public static final String USAGE = "/prefix get <name>";

    public GetCommand(CommandSender sender) {
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

        if (args.length < 2) {
            sendUsage();
            return;
        }
        if (!prefix.getKeys(false).contains(args[1])) {
            sender.sendMessage(format(false, "error.prefix-not-found"));
            return;
        }
        String name = fileConfiguration.getString("prefix." + args[1]);
        sender.sendMessage(format(true, "message.prefix.get", args[1]).replace("%prefix%", coloring(name)));

    }

    private String format(boolean prefix, String key, Object... args) {
        return plugin.getMessageFormat().format(prefix, key, args);
    }

    private String coloring(String message) {
        return plugin.getMessageFormat().coloring(message);
    }
}

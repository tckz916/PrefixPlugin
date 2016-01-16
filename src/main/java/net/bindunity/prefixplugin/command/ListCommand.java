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
public class ListCommand extends BaseCommand {

    private PrefixPlugin plugin = PrefixPlugin.getInstance();

    public static final String PERMISSION = "prefixplugin.command.List";

    public static final String DESCRIPTION = "List Command";

    public static final String USAGE = "/prefix list";

    public ListCommand(CommandSender sender) {
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

        if (args.length > 1) {
            sendUsage();
            return;
        }
        sender.sendMessage(coloring("`7------- [`b Prefix List `7] -------"));
        for (String s : prefix.getKeys(false)) {
            sender.sendMessage(format(false, "message.prefix.list", s).replace("%prefix%", coloring(fileConfiguration.getString(prefix.getName() + "." + s))));
        }

    }

    private String format(boolean prefix, String key, Object... args) {
        return plugin.getMessageFormat().format(prefix, key, args);
    }

    private String coloring(String message) {
        return plugin.getMessageFormat().coloring(message);
    }
}

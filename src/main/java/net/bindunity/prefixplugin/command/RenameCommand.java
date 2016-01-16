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
public class RenameCommand extends BaseCommand {

    private PrefixPlugin plugin = PrefixPlugin.getInstance();

    public static final String PERMISSION = "prefixplugin.command.rename";

    public static final String DESCRIPTION = "Rename Command";

    public static final String USAGE = "/prefix rename <name> <prefix>";

    public RenameCommand(CommandSender sender) {
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
            sender.sendMessage(format(false, "error.prefix-not-found"));
            return;
        }
        fileConfiguration.set("prefix." + args[1], build(args, 2).replace("#s", " "));
        plugin.saveConfig();
        sender.sendMessage(format(true, "message.prefix.rename").replace("%prefix%", args[1]));

    }

    private String format(boolean prefix, String key, Object... args) {
        return plugin.getMessageFormat().format(prefix, key, args);
    }

    private String build(String[] args, int start) {
        return plugin.getMessageFormat().build(args, start);
    }
}

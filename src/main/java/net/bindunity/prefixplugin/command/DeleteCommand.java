package net.bindunity.prefixplugin.command;

import net.bindunity.prefixplugin.PrefixPlugin;
import net.bindunity.prefixplugin.api.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Created by hayatarou_ on 2016/01/17.
 */
public class DeleteCommand extends BaseCommand {

    private PrefixPlugin plugin = PrefixPlugin.getInstance();

    public static final String PERMISSION = "prefixplugin.command.delete";

    public static final String DESCRIPTION = "Delete Command";

    public static final String USAGE = "/prefix delete <name>";

    public DeleteCommand(CommandSender sender) {
        super(sender, PERMISSION, DESCRIPTION, USAGE);
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {

        if (!hasPermission()) {
            sender.sendMessage(format(false, "error.no-permission"));
            return;
        }

        FileConfiguration fileConfiguration = plugin.getConfig();

        if (args.length < 2) {
            sendUsage();
            return;
        }
        fileConfiguration.set("prefix." + args[1], null);
        plugin.saveConfig();
        sender.sendMessage(format(true, "message.prefix.delete").replace("%prefix%", args[1]));

    }

    private String format(boolean prefix, String key, Object... args) {
        return plugin.getMessageFormat().format(prefix, key, args);
    }
}

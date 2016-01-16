package net.bindunity.prefixplugin.command;


import net.bindunity.prefixplugin.PrefixPlugin;
import net.bindunity.prefixplugin.api.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

/**
 * Created by tckz916 on 2015/09/03.
 */
public class HelpCommand extends BaseCommand {

    private PrefixPlugin plugin = PrefixPlugin.getInstance();

    public static final String PERMISSION = "prefixplugin.command.help";

    public static final String DESCRIPTION = "Help Command";

    public static final String USAGE = "/prefix";

    public HelpCommand(CommandSender sender) {
        super(sender, PERMISSION, DESCRIPTION, USAGE);
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {

        if (!hasPermission()) {
            sender.sendMessage(format(false, "error.no-permission"));
            return;
        }
        if (args.length > 0) {
            sendUsage();
            return;
        }

        ConfigurationSection help = plugin.getMessageFormat().getMessageFile().getCfg().getConfigurationSection("help");

        for (String s : help.getKeys(false)) {
            if (sender.hasPermission("prefixplugin.command." + s) || s.equalsIgnoreCase("header")) {
                sender.sendMessage(format(false, help.getName() + "." + s));
            }
        }

    }

    private String format(boolean prefix, String key, Object... args) {
        return plugin.getMessageFormat().format(prefix, key, args);
    }

}

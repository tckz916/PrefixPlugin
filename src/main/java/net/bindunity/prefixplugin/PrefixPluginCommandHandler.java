package net.bindunity.prefixplugin;

import net.bindunity.prefixplugin.api.BaseCommand;
import net.bindunity.prefixplugin.command.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by hayatarou_ on 2016/01/17.
 */
public class PrefixPluginCommandHandler implements CommandExecutor {

    private PrefixPlugin plugin = PrefixPlugin.getInstance();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        BaseCommand baseCommand = new HelpCommand(sender);

        if (args.length < 1){
            baseCommand = new HelpCommand(sender);
            baseCommand.execute(sender, command, label, args);
            return true;
        }

        plugin.reloadConfig();

        switch (args[0]) {
            case "add":
                baseCommand = new AddCommand(sender);
                break;
            case "delete":
                baseCommand = new DeleteCommand(sender);
                break;
            case "get":
                baseCommand = new GetCommand(sender);
                break;
            case "list":
                baseCommand = new ListCommand(sender);
                break;
            case "rename":
                baseCommand = new RenameCommand(sender);
                break;
            case "reset":
                baseCommand = new ResetCommand(sender);
                break;
            case "set":
                baseCommand = new SetCommand(sender);
                break;
            default:
                break;
        }

        baseCommand.execute(sender, command, label, args);

        return true;
    }
}

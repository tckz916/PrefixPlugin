package net.bindunity.prefixplugin;

import net.bindunity.prefixplugin.command.tabcomplete.PrefixTabComple;
import net.bindunity.prefixplugin.listener.PlayerListener;
import net.bindunity.prefixplugin.manager.FileManager;
import net.bindunity.prefixplugin.manager.JsonManager;
import net.bindunity.prefixplugin.message.MessageFormat;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by hayatarou_ on 2016/01/17.
 */
public class PrefixPlugin extends JavaPlugin {

    private static PrefixPlugin instance = null;

    private PluginManager pluginManager = this.getServer().getPluginManager();

    private MessageFormat messageFormat = null;

    private FileManager fileManager = null;

    private JsonManager jsonManager = null;

    @Override
    public void onEnable() {
        super.onEnable();

        saveDefaultConfig();

        instance = this;

        messageFormat = new MessageFormat();

        fileManager = new FileManager();

        jsonManager = new JsonManager();

        registercommand();
        registertabcomplete();
        registerlistener();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registercommand() {
        this.getCommand("prefix").setExecutor(new PrefixPluginCommandHandler());
    }

    private void registertabcomplete() {
        getCommand("prefix").setTabCompleter(new PrefixTabComple());
    }

    private void registerlistener() {
        pluginManager.registerEvents(new PlayerListener(), this);
    }

    public static PrefixPlugin getInstance() {
        return instance;
    }

    public MessageFormat getMessageFormat() {
        return messageFormat;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public JsonManager getJsonManager() {
        return jsonManager;
    }
}


package fr.noogotte.useful_commands;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.aumgn.bukkitutils.command.messages.FrenchMessages;
import fr.aumgn.bukkitutils.gconf.GConfLoadException;
import fr.aumgn.bukkitutils.gconf.GConfLoader;
import fr.aumgn.bukkitutils.gconf.typeadapter.DirectionTypeAdapterFactory;
import fr.noogotte.useful_commands.command.AfkCommand;
import fr.noogotte.useful_commands.command.GodCommand;
import fr.noogotte.useful_commands.command.PlayerCommands;
import fr.noogotte.useful_commands.command.PlayerInfoCommand;
import fr.noogotte.useful_commands.command.TeleportCommands;
import fr.noogotte.useful_commands.command.WarpsCommand;
import fr.noogotte.useful_commands.command.WorldCommands;

public class UsefulCommandPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        UsefulConfig config;
        SaveWarpsFile warpsFile;
        GConfLoader loader = getConfigLoader();
        try {
            config = loader.loadOrCreate("config.json", UsefulConfig.class);
            warpsFile = loader.loadOrCreate("warps.json", SaveWarpsFile.class);
        } catch (GConfLoadException exc) {
            getLogger().severe("Unable to load config.json or warps.json. Using default values");
            warpsFile = new SaveWarpsFile();
            config = new UsefulConfig();
        }
        
        CommandsRegistration registration = new CommandsRegistration(
                this, new FrenchMessages());

        GodComponent godComponent = new GodComponent(this);
        AfkComponent afkComponent = new AfkComponent(this);
        registration.register(new WorldCommands());
        registration.register(new PlayerCommands());
        registration.register(new TeleportCommands());
        registration.register(new PlayerInfoCommand(godComponent, afkComponent));
        
        if(config.enableAfk()) {
        	registration.register(new AfkCommand(afkComponent));
        }
        
        if (config.enableGod()) {
            
            registration.register(new GodCommand(godComponent));
        }

        if (config.enableWarp()) {
            WarpsComponent warpcomponent = new WarpsComponent();
            registration.register(new WarpsCommand(warpcomponent));
        }
    }

    private GConfLoader getConfigLoader() {
        Gson gson = new GsonBuilder()
            .registerTypeAdapterFactory(new DirectionTypeAdapterFactory())
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
            .setPrettyPrinting()
            .create();
        return new GConfLoader(gson, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd,
            String lbl, String[] args) {
        sender.sendMessage(ChatColor.RED +
                "Cette commande n'est pas activée.");
        return true;
    }
}
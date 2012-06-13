package fr.noogotte.useful_commands;


import java.util.ArrayList;
import java.util.List;

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
import fr.noogotte.useful_commands.command.FunCommands;
import fr.noogotte.useful_commands.command.GodCommand;
import fr.noogotte.useful_commands.command.InventoryCommands;
import fr.noogotte.useful_commands.command.KitsCommands;
import fr.noogotte.useful_commands.command.PlayerCommands;
import fr.noogotte.useful_commands.command.PlayerInfoCommand;
import fr.noogotte.useful_commands.command.TeleportCommands;
import fr.noogotte.useful_commands.command.WarpsCommands;
import fr.noogotte.useful_commands.command.WorldCommands;
import fr.noogotte.useful_commands.component.AfkComponent;
import fr.noogotte.useful_commands.component.Component;
import fr.noogotte.useful_commands.component.GodComponent;
import fr.noogotte.useful_commands.component.KitsComponent;
import fr.noogotte.useful_commands.component.WarpsComponent;

public class UsefulCommandPlugin extends JavaPlugin {

    private List<Component> components;

    @Override
    public void onEnable() {
        UsefulConfig config = loadUsefulConfig();
        components = new ArrayList<Component>(4);

        CommandsRegistration registration = new CommandsRegistration(
                this, new FrenchMessages());

        registration.register(new WorldCommands());
        registration.register(new PlayerCommands());
        registration.register(new InventoryCommands());
        registration.register(new TeleportCommands());

        AfkComponent afkComponent = null;
        GodComponent godComponent = null;
        
        if(config.enableFunCommands()) {
        	registration.register(new FunCommands());
        }

        if (config.enableAfk()) {
            afkComponent = new AfkComponent(this);
            components.add(afkComponent);
            registration.register(new AfkCommand(afkComponent));
        }

        if (config.enableGod()) {
            godComponent = new GodComponent(this);
            components.add(godComponent);
            registration.register(new GodCommand(godComponent));
        }

        if (config.enableWarp()) {
            WarpsComponent warpComponent = new WarpsComponent(this);
            components.add(warpComponent);
            registration.register(new WarpsCommands(warpComponent));
        }

        if (config.enableKits()) {
            KitsComponent kitsComponent = new KitsComponent(this);
            components.add(kitsComponent);
            registration.register(new KitsCommands(kitsComponent));
        }

        registration.register(new PlayerInfoCommand(godComponent, afkComponent));
    }

    private UsefulConfig loadUsefulConfig() {
        GConfLoader loader = getGConfLoader();
        try {
            return loader.loadOrCreate("config.json", UsefulConfig.class);
        } catch (GConfLoadException exc) {
            getLogger().severe("Unable to load config.json or warps.json. Using default values");
            return new UsefulConfig();
        }
    }

    @Override
    public void onDisable() {
        for (Component component : components) {
            component.onDisable();
        }
    }

    public GConfLoader getGConfLoader() {
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
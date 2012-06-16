package fr.noogotte.useful_commands;


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
import fr.aumgn.bukkitutils.command.messages.Messages;
import fr.aumgn.bukkitutils.gconf.GConfLoadException;
import fr.aumgn.bukkitutils.gconf.GConfLoader;
import fr.aumgn.bukkitutils.gconf.typeadapter.DirectionTypeAdapterFactory;
import fr.noogotte.useful_commands.component.AfkComponent;
import fr.noogotte.useful_commands.component.Component;
import fr.noogotte.useful_commands.component.FunComponent;
import fr.noogotte.useful_commands.component.GodComponent;
import fr.noogotte.useful_commands.component.InventoryComponent;
import fr.noogotte.useful_commands.component.KitsComponent;
import fr.noogotte.useful_commands.component.PlayerComponent;
import fr.noogotte.useful_commands.component.TeleportComponent;
import fr.noogotte.useful_commands.component.WarpsComponent;
import fr.noogotte.useful_commands.component.WorldComponent;

public class UsefulCommandsPlugin extends JavaPlugin {

    private List<Component> components;

    @Override
    public void onEnable() {
        UsefulConfig config = loadUsefulConfig();
        Messages messages;
        if (config.getLang().equals("fr")) {
            messages = new FrenchMessages();
        } else {
            messages = new Messages();
        }
        CommandsRegistration commandsRegistration = new CommandsRegistration(
                this, messages);
        ComponentRegistration registration = new ComponentRegistration(
                config, commandsRegistration);

        registration.register(new AfkComponent(this));
        registration.register(new FunComponent(this));
        registration.register(new GodComponent(this));
        registration.register(new InventoryComponent(this));
        registration.register(new KitsComponent(this));
        registration.register(new PlayerComponent(this));
        registration.register(new TeleportComponent(this));
        registration.register(new WarpsComponent(this));
        registration.register(new WorldComponent(this));

        components = registration.getComponents();
    }

    public <T extends Component> T getComponent(Class<T> klass) {
        for (Component component: components) {
            if (component.getClass() == klass) {
                @SuppressWarnings("unchecked")
                T safeCast = (T) component;
                return safeCast;
            }
        }

        return null;
    }

    private UsefulConfig loadUsefulConfig() {
        GConfLoader loader = getGConfLoader();
        try {
            return loader.loadOrCreate("config.json", UsefulConfig.class);
        } catch (GConfLoadException exc) {
            getLogger().severe("Unable to load config.json. Using default values");
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
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
import fr.aumgn.bukkitutils.gson.GsonLoadException;
import fr.aumgn.bukkitutils.gson.GsonLoader;
import fr.aumgn.bukkitutils.gson.typeadapter.DirectionTypeAdapterFactory;
import fr.aumgn.bukkitutils.localization.Localization;
import fr.aumgn.bukkitutils.localization.PluginMessages;
import fr.noogotte.useful_commands.component.AfkComponent;
import fr.noogotte.useful_commands.component.ChatComponent;
import fr.noogotte.useful_commands.component.Component;
import fr.noogotte.useful_commands.component.FunComponent;
import fr.noogotte.useful_commands.component.GodComponent;
import fr.noogotte.useful_commands.component.HomeComponent;
import fr.noogotte.useful_commands.component.InventoryComponent;
import fr.noogotte.useful_commands.component.KitsComponent;
import fr.noogotte.useful_commands.component.PlayerComponent;
import fr.noogotte.useful_commands.component.PluginComponent;
import fr.noogotte.useful_commands.component.SpyComponent;
import fr.noogotte.useful_commands.component.TeleportComponent;
import fr.noogotte.useful_commands.component.VanishComponent;
import fr.noogotte.useful_commands.component.WarpsComponent;
import fr.noogotte.useful_commands.component.WorldComponent;

public class UsefulCommandsPlugin extends JavaPlugin {

    private PluginMessages messages;

    private List<Component> components;
    private UsefulConfig config;

    @Override
    public void onEnable() {
        config = loadUsefulConfig();

        Localization localization = new Localization(
                this, config.getLocale(), getDataFolder());
        messages = localization.get("messages");

        CommandsRegistration commandsRegistration =
                new CommandsRegistration(this, config.getLocale());
        ComponentRegistration registration = new ComponentRegistration(
                config, commandsRegistration);

        registration.register(new ChatComponent(this));
        registration.register(new AfkComponent(this));
        registration.register(new FunComponent(this));
        registration.register(new GodComponent(this));
        registration.register(new InventoryComponent(this));
        registration.register(new KitsComponent(this));
        registration.register(new PlayerComponent(this));
        registration.register(new TeleportComponent(this));
        registration.register(new WarpsComponent(this));
        registration.register(new WorldComponent(this));
        registration.register(new VanishComponent(this));
        registration.register(new SpyComponent(this));
        registration.register(new HomeComponent(this));
        registration.register(new PluginComponent(this));

        registration.onEnable();
        components = registration.getComponents();
    }

    public PluginMessages getMessages() {
        return messages;
    }

    public <T extends Component> T getComponent(Class<T> klass) {
        for (Component component : components) {
            if (component.getClass() == klass) {
                @SuppressWarnings("unchecked")
                T safeCast = (T) component;
                return safeCast;
            }
        }

        return null;
    }

    public UsefulConfig getUsefulConfig() {
        return config;
    }

    private UsefulConfig loadUsefulConfig() {
        GsonLoader loader = getGsonLoader();
        try {
            return loader.loadOrCreate("config.json", UsefulConfig.class);
        } catch (GsonLoadException exc) {
            getLogger().severe(
                    "Unable to load config.json. Using default values");
            return new UsefulConfig();
        }
    }

    @Override
    public void onDisable() {
        for (Component component : components) {
            component.onDisable();
        }
    }

    public GsonLoader getGsonLoader() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new DirectionTypeAdapterFactory())
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_DASHES)
                .setPrettyPrinting().create();
        return new GsonLoader(gson, this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd,
            String lbl, String[] args) {
        sender.sendMessage(ChatColor.RED +
                "Cette commande n'est pas activée.");
        return true;
    }
}

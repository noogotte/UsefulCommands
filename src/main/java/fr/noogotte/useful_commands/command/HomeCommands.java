package fr.noogotte.useful_commands.command;

import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.aumgn.bukkitutils.command.Command;
import fr.aumgn.bukkitutils.command.NestedCommands;
import fr.aumgn.bukkitutils.command.args.CommandArgs;
import fr.aumgn.bukkitutils.command.exception.CommandError;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.component.HomeComponent;
import fr.noogotte.useful_commands.component.HomeComponent.Home;

@NestedCommands("useful")
public class HomeCommands extends UsefulCommands {

    private HomeComponent homes;

    public HomeCommands(UsefulCommandsPlugin plugin, HomeComponent homeComponent) {
        super(plugin);
        this.homes = homeComponent;
    }

    @Command(name = "sethome")
    public void setHome(Player sender, CommandArgs args) {
        if (homes.haveHome(sender.getName())) {
            throw new CommandError(msg("home.hasAlready_€"));
        }

        sender.sendMessage(msg("home.define"));
        homes.addHome(sender);
        homes.save();
    }

    @Command(name = "home", max = 1)
    public void home(Player sender, CommandArgs args) {
        String homeName = args.get(0, sender.getName());

        if(!homes.haveHome(homeName)) {
            if(homeName != sender.getName()) {
                throw new CommandError(msg("home.hasNotHome.other_€", homeName));
            }
            throw new CommandError(msg("home.hasNotHome_€"));
        }

        if(homeName != sender.getName()) {
            if(!sender.hasPermission("useful.home.use.other")){
                throw new CommandError(msg("home.hasNotPermToUse_€", homeName));
            }
        }

        Home home = homes.getHome(homeName);
        sender.sendMessage(msg("teleport.target"));
        sender.teleport(home.toLocation());
    }

    @Command(name = "deletehome", max = 1)
    public void delHome(Player sender, CommandArgs args) {
        String homeName = args.get(0, sender.getName());

        if (!homes.haveHome(homeName)) {
            if(homeName != sender.getName()) {
                throw new CommandError(msg("home.hasNotHome.other_€", homeName));
            }
            throw new CommandError(msg("home.hasNotHome_€"));
        }

        if(homeName != sender.getName()) {
            if(!sender.hasPermission("useful.home.delhome.other")) {
                throw new CommandError(msg("home.hasNotPermToDel.other_€", homeName));
            }
        }

        sender.sendMessage(msg("home.del", homeName));
        homes.deleteHome(homeName);
        homes.save();
    }

    @Command(name = "homes")
    public void homes(Player sender, CommandArgs args) {
        if(homes.isEmpty()) {
            throw new CommandError(msg("home.isEmpty_€"));
        }

        sender.sendMessage(ChatColor.YELLOW +"Homes (" + homes.getNbHome() + "):");
        for (Entry<String, Home> home : homes.homes()) {
            sender.sendMessage(ChatColor.YELLOW + " - " + home.getKey());
        }
    }
}
package fr.noogotte.useful_commands.component;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.aumgn.bukkitutils.command.Commands;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.command.ChatCommands;

public class ChatComponent extends Component implements Listener {

    private final Set<String> mutePlayers;

    public ChatComponent(UsefulCommandsPlugin plugin) {
        super(plugin);
        this.mutePlayers = new HashSet<String>();
    }

    @Override
    public String getName() {
        return "chat";
    }

    @Override
    public List<Commands> getCommands() {
        return Collections.<Commands>singletonList(new ChatCommands(this));
    }

    public boolean isMute(Player player) {
        return mutePlayers.contains(player.getName());
    }

    public void mute(Player player) {
        if (mutePlayers.isEmpty()) {
            registerEvents(this);
        }

        mutePlayers.add(player.getName());
    }

    public void unmute(Player player) {
        boolean contained = mutePlayers.remove(player.getName());

        if (contained && mutePlayers.isEmpty()) {
            unregisterEvents(this);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onChat(PlayerChatEvent event) {
        if (event instanceof PlayerCommandPreprocessEvent) {
            return;
        }

        if (isMute(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}

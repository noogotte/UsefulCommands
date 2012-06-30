package fr.noogotte.useful_commands.component;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
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

    public static final String CONSOLE_MARKER = "#";

    private final Map<String, String> conversations;
    private final Set<String> mutePlayers;

    public ChatComponent(UsefulCommandsPlugin plugin) {
        super(plugin);
        this.conversations = new HashMap<String, String>();
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

    public void registerConversation(CommandSender sender, CommandSender target) {
        String senderName = nameFor(sender);
        String targetName = nameFor(target);
        conversations.put(senderName, targetName);
        conversations.put(targetName, senderName);
    }

    private String nameFor(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender) {
            return CONSOLE_MARKER;
        } else {
            return sender.getName();
        }
    }

    public CommandSender getConversationTarget(CommandSender sender) {
        return getConversationTarget(nameFor(sender));
    }

    private CommandSender getConversationTarget(String key) {
        if (!conversations.containsKey(key)) {
            return null;
        }

        String targetName = conversations.get(key);
        if (targetName.equals(CONSOLE_MARKER)) {
            return Bukkit.getConsoleSender();
        }
        return Bukkit.getPlayerExact(targetName);
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

package fr.noogotte.useful_commands.component;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.aumgn.bukkitutils.command.Commands;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.command.InfoServerCommands;
import fr.noogotte.useful_commands.command.PlayerCommands;
import fr.noogotte.useful_commands.command.PlayerInfoCommand;

public class PlayerComponent extends Component implements Listener {

    public PlayerComponent(UsefulCommandsPlugin plugin) {
        super(plugin);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public String getName() {
        return "player";
    }

    @Override
    public Commands[] getCommands() {
        return new Commands[] { 
                new PlayerCommands(plugin),
                new PlayerInfoCommand(plugin),
                new InfoServerCommands(plugin)
        };
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block.getType() != Material.MOB_SPAWNER) {
            return;
        }

        Player player = event.getPlayer();
        if (player.getItemInHand().getTypeId() != 383) {
            return;
        }

        if (!player.hasPermission("useful.player.changemmobspawner")) {
            return;
        }

        String data = player.getItemInHand().getData().toString();
        String mobName = data.replace("SPAWN EGG", "").replace("{", "")
                .replace("}", "").replace("_", "");
        if (mobName.equalsIgnoreCase("MAGMACUBE")) {
            mobName = "LavaSlime";
        } else if (mobName.equalsIgnoreCase("OCELOT")) {
            mobName = "Ozelot";
        }
        CreatureSpawner spawner = (CreatureSpawner) block.getState();
        EntityType type = EntityType.fromName(mobName);
        spawner.setSpawnedType(type);
        player.sendMessage(plugin.getMessages().get("setSpawnerType",
                type.getName()));
    }
}

package fr.noogotte.useful_commands.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class DisplayNameLookupEvent extends PlayerEvent {

    private final static HandlerList HANDLERS_LIST = new HandlerList();
    private String displayName;

    public DisplayNameLookupEvent(final Player who) {
        super(who);
        this.displayName = who.getName();
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}

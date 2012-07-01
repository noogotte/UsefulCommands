package fr.noogotte.useful_commands.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class VisibleCheckEvent extends Event {
        
    private final static HandlerList HANDLERS_LIST = new HandlerList();
    private boolean visible;
    private final Player player;

    public VisibleCheckEvent(final Player player) {
        super();
        this.visible = true;
        this.player = player;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
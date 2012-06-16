package fr.noogotte.useful_commands;

import java.util.ArrayList;
import java.util.List;

import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.bukkitutils.command.CommandsRegistration;
import fr.noogotte.useful_commands.component.Component;

public class ComponentRegistration {

    private final UsefulConfig config;
    private final CommandsRegistration commandsRegistration;
    private List<Component> components;

    public ComponentRegistration(UsefulConfig config, CommandsRegistration commandsRegistration) {
        this.config = config;
        this.commandsRegistration = commandsRegistration;
        this.components = new ArrayList<Component>();
    }

    public void register(Component component) {
        if (config.isEnabled(component)) {
            components.add(component);
            for (Commands commands : component.getCommands()) {
                commandsRegistration.register(commands);
            }
            component.onEnable();
        }
    }

    public List<Component> getComponents() {
        return components;
    }
}

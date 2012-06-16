package fr.noogotte.useful_commands;

import java.util.Collections;
import java.util.Set;

import fr.noogotte.useful_commands.component.Component;

public class UsefulConfig {

    private String lang = "fr";
    private Set<String> disabled = Collections.<String>emptySet();

    public String getLang() {
        return lang;
    }

    public boolean isEnabled(Component component) {
        return !disabled.contains(component.getName());
    }
}

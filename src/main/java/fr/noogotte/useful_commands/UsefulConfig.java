package fr.noogotte.useful_commands;

import java.util.Collections;
import java.util.Set;

import fr.noogotte.useful_commands.component.Component;

public class UsefulConfig {

    private String lang = "fr";
    private Set<String> disabled = Collections.<String>emptySet();
    private boolean msgIntoConsole = true;

    public String getLang() {
        return lang;
    }

    public boolean isEnabled(Component component) {
        return !disabled.contains(component.getName());
    }
    
    public boolean message_in_console() {
    	return msgIntoConsole;
    }
}

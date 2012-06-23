package fr.noogotte.useful_commands;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import fr.noogotte.useful_commands.component.Component;

public class UsefulConfig {

    private String lang = Locale.FRANCE.toString();
    private Set<String> disabled = Collections.<String> emptySet();
    private boolean msgInConsole = true;

    public Locale getLocale() {
        String[] splitted = lang.split("_");
        if (splitted.length == 0) {
            return Locale.getDefault();
        } else if (splitted.length == 1) {
            return new Locale(splitted[0]);
        } else {
            return new Locale(splitted[0], splitted[1]);
        }
    }

    public boolean isEnabled(Component component) {
        return !disabled.contains(component.getName());
    }

    public boolean messageInConsole() {
        return msgInConsole;
    }
}

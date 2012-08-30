package fr.noogotte.useful_commands;

import java.util.Collections;
import java.util.Locale;
import java.util.Set;

import fr.aumgn.bukkitutils.util.Util;
import fr.noogotte.useful_commands.component.Component;

public class UsefulConfig {

    private String lang = Locale.FRANCE.toString();
    private Set<String> disabled = Collections.<String> emptySet();
    private boolean msgInConsole = true;

    public Locale getLocale() {
        return Util.parseLocale(lang);
    }

    public boolean isEnabled(Component component) {
        return !disabled.contains(component.getName());
    }

    public boolean messageInConsole() {
        return msgInConsole;
    }
}

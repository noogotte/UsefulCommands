package fr.noogotte.useful_commands;

public class UsefulConfig {

    private boolean enableGod = true;
    private boolean enableWarp = true;
    private boolean enableAfk = true;
    private boolean enableKits = true;
    private boolean enableFunCommands = true;

    public boolean enableGod() {
        return enableGod;
    }

    public boolean enableWarp() {
        return enableWarp;
    }

    public boolean enableAfk() {
        return enableAfk;
    }

    public boolean enableKits() {
        return enableKits;
    }
    
    public boolean enableFunCommands() {
    	return enableFunCommands;
    }
}
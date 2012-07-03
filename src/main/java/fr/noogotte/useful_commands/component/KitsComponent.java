package fr.noogotte.useful_commands.component;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import fr.aumgn.bukkitutils.command.Commands;
import fr.aumgn.bukkitutils.gconf.GConfLoadException;
import fr.aumgn.bukkitutils.gconf.GConfLoader;
import fr.noogotte.useful_commands.UsefulCommandsPlugin;
import fr.noogotte.useful_commands.command.KitsCommands;
import fr.noogotte.useful_commands.component.kit.Kit;

public class KitsComponent extends Component
        implements Iterable<Entry<String, Kit>> {

    private final Map<String, Kit> kits;

    public KitsComponent(UsefulCommandsPlugin plugin) {
        super(plugin);
        this.kits = new HashMap<String, Kit>();

        File folder = getFolder();
        if (folder == null) {
            return;
        }

        GConfLoader loader = plugin.getGConfLoader();
        for (File file : folder.listFiles()) {
            String filename = folder.getName() + File.separator
                    + file.getName();

            try {
                Kit kit = loader.loadOrCreate(filename, Kit.class);
                kits.put(getKitNameFor(filename), kit);
            } catch (GConfLoadException exc) {
                plugin.getLogger().severe(
                        "Unable to read " + filename + " kit file.");
            }
        }
    }

    @Override
    public String getName() {
        return "kits";
    }

    @Override
    public List<Commands> getCommands() {
        return Collections.<Commands>singletonList(new KitsCommands(this, plugin));
    }

    @Override
    public void onDisable() {
        for (Entry<String, Kit> entry : kits.entrySet()) {
            saveKit(entry.getKey(), entry.getValue());
        }
    }

    private File getFolder() {
        File folder = new File(plugin.getDataFolder(), "kits");
        if (folder.exists()) {
            if (!folder.isDirectory()) {
                plugin.getLogger().severe(
                        getFolder().getPath() + " is not a directory.");
                return null;
            }
        } else if (!folder.mkdirs()) {
            plugin.getLogger().severe(
                    "Unable to create " + folder.getPath() + " directory.");
            return null;
        }

        return folder;
    }

    private String getFilenameFor(String name) {
        return "kits" + File.separator + name + ".json";
    }

    private String getKitNameFor(String pathname) {
        int index = pathname.lastIndexOf(File.separatorChar);
        String filename = pathname.substring(index + 1);
        index = filename.indexOf(".");
        return filename.substring(0, index);
    }

    private boolean saveKit(String name, Kit kit) {
        GConfLoader loader = plugin.getGConfLoader();
        File folder = getFolder();
        if (folder == null) {
            return false;
        }

        String filename = getFilenameFor(name);
        try {
            loader.write(filename, kit);
            return true;
        } catch (GConfLoadException exc) {
            plugin.getLogger().severe("Unable to save " + filename + ".");
            return false;
        }
    }

    public boolean isKit(String name) {
        return kits.containsKey(name);
    }

    public Kit get(String name) {
        return kits.get(name);
    }

    public boolean hasNoKit() {
        return kits.isEmpty();
    }

    public boolean addKit(String name, Kit kit) {
        boolean success = saveKit(name, kit);
        if (success) {
            kits.put(name, kit);
        }

        return success;
    }

    public boolean removeKit(String name) {
        String filename = getFilenameFor(name);
        File file = new File(plugin.getDataFolder(), filename);
        boolean success = file.delete();
        if (success) {
            kits.remove(name);
        }
        return success;
    }

    @Override
    public Iterator<Entry<String, Kit>> iterator() {
        return kits.entrySet().iterator();
    }
}

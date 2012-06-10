package fr.noogotte.useful_commands.component;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import fr.aumgn.bukkitutils.gconf.GConfLoadException;
import fr.aumgn.bukkitutils.gconf.GConfLoader;
import fr.noogotte.useful_commands.UsefulCommandPlugin;
import fr.noogotte.useful_commands.component.kit.Kit;

public class KitsComponent extends Component implements Iterable<Entry<String, Kit>> {

    private final Map<String, Kit> kits; 

    public KitsComponent(UsefulCommandPlugin plugin) {
        super(plugin);
        this.kits = new HashMap<String, Kit>();

        File folder = getFolder();
        GConfLoader loader = plugin.getGConfLoader();
        for (File file : folder.listFiles()) {
            String name = file.getName();
            if (name.startsWith("template")) {
                continue;
            }

            try {
                Kit kit = loader.loadOrCreate(name, Kit.class);
                kits.put(name, kit);
            } catch (GConfLoadException exc) {
                plugin.getLogger().severe(
                        "Unable to read " + name + " kit file.");
            }
        }
    }

    private File getFolder() {
        File folder = new File(plugin.getDataFolder(), "kits");
        if (folder.exists() && !folder.isDirectory()) {
            plugin.getLogger().severe(getFolder().getPath()
                    + " is not a directory.");
            return null;
        }

        if (!folder.mkdirs()) {
            plugin.getLogger().severe(
                    "Unable to create " + folder.getPath() + " directory.");
            return null;
        }

        return folder;
    }

    private String getFilenameFor(String name) {
        return "kits" + File.separator
                + name + ".json";
    }

    private void saveKit(String name, Kit kit) {
        GConfLoader loader = plugin.getGConfLoader();
        getFolder();
        String filename = getFilenameFor(name);
        try {
            loader.write(filename, kit);
        } catch (GConfLoadException exc) {
            plugin.getLogger().severe("Unable to save " + filename + ".");
        }
    }

    @Override
    public void onDisable() {
        for (Entry<String, Kit> entry : kits.entrySet()) {
            saveKit(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public Iterator<Entry<String, Kit>> iterator() {
        return kits.entrySet().iterator();
    }
}

package cc.pollo.gladeus.misc.config;

import org.bukkit.plugin.Plugin;

import java.io.File;

/**
 * ${@link YAMLConfig} that can also create files from embedded resources (bukkit's system)
 */
public class ResourceYAMLConfig extends YAMLConfig {

    private final Plugin plugin;

    public ResourceYAMLConfig(Plugin plugin, File file) {
        super(file);
        this.plugin = plugin;
    }

    /**
     * Creates files from resource
     */
    public void create(){
        if(!file.exists()){
            file.getParentFile().mkdirs();

            plugin.saveResource(file.getName(), false);
        }
    }

}
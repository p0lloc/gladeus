package cc.pollo.gladeus.misc.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Simple config + file class for easy loading and saving
 */
public class YAMLConfig {

    protected final File file;
    private YamlConfiguration configuration;

    public YAMLConfig(File file){
        this.file = file;
    }

    /**
     * Loads the config into memory
     */
    public void load(){
        configuration = new YamlConfiguration();

        try {
            configuration.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
            configuration = null;
        }
    }

    public File getFile() {
        return file;
    }

    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Gets whether or not the config is loaded
     * @return whether or not the config is loaded
     */
    public boolean isLoaded(){
        return configuration != null;
    }

    /**
     * Saves the config back into the file again
     */
    public void save(){
        if(!isLoaded())
            return;

        try {
            configuration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
package com.oxiwan.mobstackerpremium.utils;

import com.oxiwan.mobstackerpremium.MobStackerPremium;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigManager {

    private final MobStackerPremium plugin;
    private FileConfiguration config;

    public ConfigManager(MobStackerPremium plugin) {
        this.plugin = plugin;
        this.plugin.saveDefaultConfig();
        this.config = plugin.getConfig();
    }

    public void reloadConfig() {
        plugin.reloadConfig();
        config = plugin.getConfig();
    }

    public int getMaxStackSize() {
        return config.getInt("max-stack-size", 50);
    }

    public double getStackRadius() {
        return config.getDouble("stack-radius", 5.0);
    }

    public List<String> getExcludedMobs() {
        return config.getStringList("excluded-mobs");
    }
}

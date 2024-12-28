package com.oxiwan.mobstackerpremium;

import com.oxiwan.mobstackerpremium.commands.MobStackerCommand;
import com.oxiwan.mobstackerpremium.listeners.EntityListener;
import com.oxiwan.mobstackerpremium.managers.StackManager;
import com.oxiwan.mobstackerpremium.utils.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MobStackerPremium extends JavaPlugin {

    private static MobStackerPremium instance;
    private ConfigManager configManager;
    private StackManager stackManager;

    @Override
    public void onEnable() {
        instance = this;

        this.configManager = new ConfigManager(this);
        this.stackManager = new StackManager(this);

        getServer().getPluginManager().registerEvents(new EntityListener(this), this);

        getCommand("mobstacker").setExecutor(new MobStackerCommand(this));

        getLogger().info("MobStacker Premium has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("MobStacker Premium has been disabled!");
    }

    public static MobStackerPremium getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public StackManager getStackManager() {
        return stackManager;
    }
}

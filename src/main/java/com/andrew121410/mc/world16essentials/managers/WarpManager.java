package com.andrew121410.mc.world16essentials.managers;

import com.andrew121410.mc.world16essentials.World16Essentials;
import com.andrew121410.mc.world16utils.config.CustomYmlManager;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Map;

public class WarpManager {

    private Map<String, Location> warpsMap;

    private World16Essentials plugin;
    private CustomYmlManager warpsYml;

    public WarpManager(World16Essentials plugin, CustomConfigManager customConfigManager) {
        this.plugin = plugin;
        this.warpsYml = customConfigManager.getWarpsYml();
        this.warpsMap = this.plugin.getSetListMap().getWarpsMap();
        getConfigurationSection();
    }

    public void loadAllWarps() {
        ConfigurationSection cs = getConfigurationSection();
        for (String key : cs.getKeys(false)) {
            ConfigurationSection warpCs = cs.getConfigurationSection(key);
            Location location = (Location) warpCs.get("Location");
            this.warpsMap.putIfAbsent(key, location);
        }
    }

    public void createWarp(String name, Location location) {
        String newWarpName = name.toLowerCase();
        this.warpsMap.put(newWarpName, location);
        ConfigurationSection cs = getConfigurationSection();
        ConfigurationSection warpCs = cs.createSection(newWarpName);
        warpCs.set("Location", location);
        this.warpsYml.saveConfig();
    }

    public void deleteWarp(String name) {
        String newWarpName = name.toLowerCase();
        if (!this.warpsMap.containsKey(newWarpName)) return;
        ConfigurationSection cs = getConfigurationSection();
        cs.set(newWarpName, null);
        this.warpsYml.saveConfig();
        this.warpsMap.remove(newWarpName);
    }

    private ConfigurationSection getConfigurationSection() {
        ConfigurationSection cs = this.warpsYml.getConfig().getConfigurationSection("Warps");
        if (cs == null) {
            this.warpsYml.getConfig().createSection("Warps");
            this.warpsYml.saveConfig();
        }
        return cs;
    }
}
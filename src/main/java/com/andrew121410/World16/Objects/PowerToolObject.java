package com.andrew121410.World16.Objects;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PowerToolObject {

    private Map<Material, String> powerTools;

    public PowerToolObject() {
        this.powerTools = new HashMap<>();
    }

    public void registerPowerTool(Material key, String command) {
        this.powerTools.putIfAbsent(key, command);
    }

    public void deletePowerTool(Material key) {
        this.powerTools.remove(key);
    }

    public void runCommand(Player player, Material key) {
        if (this.powerTools.get(key) == null) return;
        player.performCommand(this.powerTools.get(key));
    }

    public String getCommand(Material key) {
        return this.powerTools.get(key);
    }
}

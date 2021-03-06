package com.andrew121410.mc.world16essentials.commands.warp;

import com.andrew121410.mc.world16essentials.World16Essentials;
import com.andrew121410.mc.world16essentials.managers.WarpManager;
import com.andrew121410.mc.world16essentials.tabcomplete.WarpTab;
import com.andrew121410.mc.world16essentials.utils.API;
import com.andrew121410.mc.world16utils.chat.Translate;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class DelwarpCMD implements CommandExecutor {

    private Map<String, Location> warpsMap;

    private World16Essentials plugin;
    private API api;
    private WarpManager warpManager;

    public DelwarpCMD(World16Essentials plugin) {
        this.plugin = plugin;
        this.api = this.plugin.getApi();
        this.warpManager = this.plugin.getWarpManager();

        this.warpsMap = this.plugin.getSetListMap().getWarpsMap();

        this.plugin.getCommand("delwarp").setExecutor(this);
        this.plugin.getCommand("delwarp").setTabCompleter(new WarpTab(this.plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;

        if (!p.hasPermission("world16.delwarp")) {
            api.permissionErrorMessage(p);
            return true;
        }

        if (args.length == 0) {
            p.sendMessage(Translate.chat("&cUsage: &6/delwarp <Name>"));
            return true;
        } else if (args.length == 1) {
            String name = args[0].toLowerCase();

            if (!this.warpsMap.containsKey(name)) {
                p.sendMessage(Translate.chat("&cThat's not a warp."));
                return true;
            }

            this.warpManager.deleteWarp(name);
            p.sendMessage(Translate.chat("&eThe warp: " + name + " has been deleted."));
            return true;
        }
        return true;
    }
}
package com.andrew121410.mc.world16.commands.spawn;

import com.andrew121410.mc.world16.Main;
import com.andrew121410.mc.world16.managers.CustomConfigManager;
import com.andrew121410.mc.world16.utils.API;
import com.andrew121410.mc.world16utils.chat.Translate;
import com.andrew121410.mc.world16utils.config.CustomYmlManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCMD implements CommandExecutor {

    private Main plugin;
    private API api;

    private CustomYmlManager shitYml;

    public SpawnCMD(Main plugin, CustomConfigManager customConfigManager) {
        this.plugin = plugin;
        this.shitYml = customConfigManager.getShitYml();
        this.api = this.plugin.getApi();

        this.plugin.getCommand("spawn").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;

        Location spawn = this.api.getLocationFromFile(this.shitYml, "Spawn.default");

        if (!p.hasPermission("world16.spawn")) {
            api.PermissionErrorMessage(p);
            return true;
        }

        if (args.length == 0) {
            p.teleport(spawn);
            p.sendMessage(Translate.chat("&6Teleporting..."));
            return true;
        } else if (args.length == 1) {
            if (!p.hasPermission("world16.spawn.other")) {
                api.PermissionErrorMessage(p);
                return true;
            }
            Player target = plugin.getServer().getPlayerExact(args[0]);
            if (target != null && target.isOnline()) {
                target.teleport(spawn);
                target.sendMessage(Translate.chat("&6Teleporting..."));
            }
            return true;
        } else {
            p.sendMessage(Translate.chat("&cUsage: for yourself do /spawn OR /spawn <Player>"));
        }
        return true;
    }
}
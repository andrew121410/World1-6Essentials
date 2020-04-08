package com.andrew121410.World16.Commands.home;

import com.andrew121410.World16.Main.Main;
import com.andrew121410.World16.TabComplete.HomeListTab;
import com.andrew121410.World16.Utils.API;
import com.andrew121410.World16.Utils.Translate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class delhome implements CommandExecutor {

    private Main plugin;
    private API api;

    public delhome(Main plugin) {
        this.plugin = plugin;
        this.api = this.plugin.getApi();

        this.plugin.getCommand("delhome").setExecutor(this);
        this.plugin.getCommand("delhome").setTabCompleter(new HomeListTab(this.plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;

        if (!p.hasPermission("world16.home")) {
            api.PermissionErrorMessage(p);
            return true;
        }
        String defaultHomeName = "home";

        if (args.length == 1) {
            defaultHomeName = args[0].toLowerCase();

            if (defaultHomeName.equalsIgnoreCase("@allHomes")) {
                this.plugin.getHomeManager().deleteALL(p.getUniqueId());
                return true;
            }
        }

        this.plugin.getHomeManager().delete(p.getUniqueId(), defaultHomeName);
        p.sendMessage(Translate.chat("&9[Homes] &cHome deleted."));
        return true;
    }
}
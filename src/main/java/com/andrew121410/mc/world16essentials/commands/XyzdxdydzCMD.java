package com.andrew121410.mc.world16essentials.commands;

import com.andrew121410.mc.world16essentials.World16Essentials;
import com.andrew121410.mc.world16essentials.utils.API;
import com.andrew121410.mc.world16utils.chat.Translate;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class XyzdxdydzCMD implements CommandExecutor {

    private World16Essentials plugin;
    private API api;

    public XyzdxdydzCMD(World16Essentials plugin) {
        this.plugin = plugin;
        this.api = this.plugin.getApi();

        this.plugin.getCommand("xyzdxdydz").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("world16.xyzdxdydz")) {
            api.permissionErrorMessage(player);
            return true;
        }

        if (args.length == 6) {
            String x = args[0];
            String y = args[1];
            String z = args[2];
            String dx = args[3];
            String dy = args[4];
            String dz = args[5];

            if (dx.contains("~")) dx = dx.replace("~", "");
            if (dy.contains("~")) dy = dy.replace("~", "");
            if (dz.contains("~")) dz = dz.replace("~", "");

            String done = "[x=" + x + ",y=" + y + ",z=" + z + ",dx=" + dx + ",dy=" + dy + ",dz=" + dz + "]";
            player.sendMessage(Translate.chat(done));
            BaseComponent[] components = new ComponentBuilder("[CMD] Click me to copy more easier!").event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, done)).create();
            player.spigot().sendMessage(components);
        } else {
            player.sendMessage(Translate.chat("&cUsage: /xyzdxdydz x y z dx dy dz"));
        }
        return true;
    }
}
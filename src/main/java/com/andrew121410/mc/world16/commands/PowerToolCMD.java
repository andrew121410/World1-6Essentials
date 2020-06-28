package com.andrew121410.mc.world16.commands;

import com.andrew121410.mc.world16.Main;
import com.andrew121410.mc.world16.managers.CustomConfigManager;
import com.andrew121410.mc.world16.objects.PowerToolObject;
import com.andrew121410.mc.world16.utils.API;
import com.andrew121410.mc.world16utils.chat.Translate;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

public class PowerToolCMD implements CommandExecutor {

    private Map<UUID, PowerToolObject> powerToolMap;

    private Main plugin;
    private API api;

    private CustomConfigManager customConfigManager;

    public PowerToolCMD(Main plugin, CustomConfigManager customConfigManager) {
        this.plugin = plugin;

        this.powerToolMap = this.plugin.getSetListMap().getPowerToolMap();

        this.customConfigManager = customConfigManager;
        this.api = this.plugin.getApi();

        this.plugin.getCommand("powertool").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players Can Use This Command.");
            return true;
        }
        Player p = (Player) sender;

        if (!p.hasPermission("world16.powertool")) {
            api.PermissionErrorMessage(p);
            return true;
        }

        PowerToolObject powerToolObject = this.powerToolMap.get(p.getUniqueId());
        ItemStack itemInMainHand = p.getInventory().getItemInMainHand();

        if (args.length == 0) {
            powerToolObject.deletePowerTool(itemInMainHand.getType());
            p.sendMessage(Translate.chat("&eCommand has been removed from the tool."));
            return true;
        } else {
            String[] command = Arrays.copyOfRange(args, 0, args.length);
            String realCommand = String.join(" ", command);

            char check = command[0].charAt(0);
            String s = Character.toString(check);
            if (s.equalsIgnoreCase("/")) {
                p.sendMessage(Translate.chat("Looks like it contains a / in the beginning."));
                return true;
            }

            powerToolObject.registerPowerTool(itemInMainHand.getType(), realCommand);
            return true;
        }
    }
}
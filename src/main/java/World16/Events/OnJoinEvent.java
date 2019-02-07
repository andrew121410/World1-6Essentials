package World16.Events;

import World16.Main.Main;
import World16.MysqlAPI.MySQL;
import World16.Objects.KeyObject;
import World16.Utils.API;
import World16.Utils.KeyAPI;
import World16.Utils.Translate;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;

public class OnJoinEvent implements Listener {

    private static Main plugin;

    //Maps
    public static HashMap<String, KeyObject> keyDataM = new HashMap<>();
    //...

    MySQL mysql = new MySQL();
    API api = new API();
    KeyAPI keyapi = new KeyAPI();

    public OnJoinEvent(Main getPlugin) {
        plugin = getPlugin;

        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();

        if (p.getDisplayName().equals("andrew121410")) {
            p.sendMessage(Translate.chat("&4Hello, Owner."));
        }
        if (p.getDisplayName().equals("AlphaGibbon43")) {
            p.sendMessage(Translate.chat("&4Hello, Owner."));
        }
        //JOIN MESSAGE STUFF
        event.setJoinMessage("");

        Bukkit.broadcastMessage(Translate.chat(API.PREFIX + " &6Welcome Back! " + p.getDisplayName()));
        p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10.0f, 1.0f);
        version(p);
        //...

        if (!api.getHOST().equals("null")) {
            keyDataM.remove(p.getDisplayName());
            keyDataM.put(p.getDisplayName(), new KeyObject(p.getDisplayName(), 1,"null"));
            keyapi.giveAllKeysToRam(p.getDisplayName(),mysql);
        } else {
            plugin.getServer().getConsoleSender()
                    .sendMessage(Translate.chat(API.USELESS_TAG
                            + " Please make sure too put in the mysql details in the config.yml."));
        }
    }

    public void version(Player p) {
        p.sendMessage(Translate.chat("&4World1-6Ess Last Time Updated Was " + API.DATE_OF_VERSION));
    }
}
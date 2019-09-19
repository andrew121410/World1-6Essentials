package World16.Events;

import World16.Main.Main;
import World16.Objects.LocationObject;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Map;
import java.util.UUID;

public class OnPlayerDeathEvent implements Listener {

    private Main plugin;

    //Maps
    private Map<UUID, LocationObject> backm;
    //...

    public OnPlayerDeathEvent(Main plugin) {
        this.plugin = plugin;

        this.backm = this.plugin.getSetListMap().getBackM();

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void OnDeath(PlayerDeathEvent event) {
        Player p = event.getEntity();

        if (backm.get(p.getUniqueId()) == null) {
            LocationObject locationObject = new LocationObject();
            locationObject.setLocation("death", 1, p.getLocation());
            backm.put(p.getUniqueId(), locationObject);
        } else {
            backm.get(p.getUniqueId()).setLocation("death", 1, p.getLocation());
        }
    }
}
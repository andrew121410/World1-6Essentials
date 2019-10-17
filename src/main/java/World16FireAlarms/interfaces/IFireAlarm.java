package World16FireAlarms.interfaces;

import World16FireAlarms.Objects.FireAlarmReason;
import World16FireAlarms.Objects.FireAlarmSound;
import World16FireAlarms.Objects.FireAlarmTempo;
import World16FireAlarms.Objects.Zone;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IFireAlarm extends ConfigurationSerializable {

    void registerNac();

    void registerZone(Zone zone);

    void registerStrobe(IStrobe iStrobe);

    void registerSign(String string, Location location);

    void reset(Optional<Zone> zone);

    void trouble();

    void alarm(FireAlarmReason fireAlarmReason);

    Map<String, IStrobe> getStrobesMap();

    List<Zone> getZones();

    Map<String, Location> getSigns();

    void setFireAlarmSound(FireAlarmSound fireAlarmSound);

    FireAlarmSound getFireAlarmSound();

    void setFireAlarmTempo(FireAlarmTempo fireAlarmTemp);

    FireAlarmTempo getFireAlarmTempo();
}
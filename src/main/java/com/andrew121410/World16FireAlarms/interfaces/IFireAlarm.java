package com.andrew121410.World16FireAlarms.interfaces;

import com.andrew121410.World16FireAlarms.Objects.FireAlarmReason;
import com.andrew121410.World16FireAlarms.Objects.FireAlarmSettings;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.Map;

public interface IFireAlarm extends ConfigurationSerializable {

    void registerNac();

    void registerStrobe(IStrobe iStrobe);

    void registerSign(String string, Location location);

    void reset();

    void trouble();

    void alarm(FireAlarmReason fireAlarmReason);

    Map<String, IStrobe> getStrobesMap();

    Map<String, Location> getSigns();

    FireAlarmSettings getFireAlarmSettings();
}
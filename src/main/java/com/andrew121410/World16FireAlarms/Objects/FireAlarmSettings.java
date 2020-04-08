package com.andrew121410.World16FireAlarms.Objects;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class FireAlarmSettings implements ConfigurationSerializable {

    private FireAlarmSound fireAlarmSound;
    private FireAlarmTempo fireAlarmTempo;
    private String commandTrigger;

    public FireAlarmSettings(FireAlarmSound fireAlarmSound, FireAlarmTempo fireAlarmTempo, String commandTrigger) {
        this.fireAlarmSound = fireAlarmSound;
        this.fireAlarmTempo = fireAlarmTempo;
        this.commandTrigger = commandTrigger;
    }

    public FireAlarmSound getFireAlarmSound() {
        return fireAlarmSound;
    }

    public void setFireAlarmSound(FireAlarmSound fireAlarmSound) {
        this.fireAlarmSound = fireAlarmSound;
    }

    public FireAlarmTempo getFireAlarmTempo() {
        return fireAlarmTempo;
    }

    public void setFireAlarmTempo(FireAlarmTempo fireAlarmTempo) {
        this.fireAlarmTempo = fireAlarmTempo;
    }

    public String getCommandTrigger() {
        return commandTrigger;
    }

    public void setCommandTrigger(String commandTrigger) {
        this.commandTrigger = commandTrigger;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("FireAlarmSound", fireAlarmSound);
        map.put("FireAlarmTempo", fireAlarmTempo.toString());
        map.put("CommandTrigger", this.commandTrigger);
        return map;
    }

    public static FireAlarmSettings deserialize(Map<String, Object> map) {
        return new FireAlarmSettings((FireAlarmSound) map.get("FireAlarmSound"), FireAlarmTempo.valueOf((String) map.get("FireAlarmTempo")), (String) map.get("CommandTrigger"));
    }
}
package com.andrew121410.World16.Utils;

import com.andrew121410.World16.Objects.*;
import com.andrew121410.World16Elevators.Objects.ElevatorController;
import com.andrew121410.World16FireAlarms.Objects.Screen.FireAlarmScreen;
import com.andrew121410.World16FireAlarms.Objects.Screen.ScreenFocus;
import com.andrew121410.World16FireAlarms.interfaces.IFireAlarm;
import com.andrew121410.World16TrafficLights.Objects.TrafficSystem;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;

import java.util.*;

public class SetListMap {

    // 0 TO CLEAR AFTER THE PLAYER LEAVES
    // 1 TO ONLY CLEAR WHEN THE SERVER SHUTS DOWN

    private Map<String, KeyObject> keyDataM; //0
    private Map<UUID, LocationObject> backM; //0
    private Map<Player, Player> tpaM; //0
    private Map<String, Map<String, List<Location>>> eRamRaw; //0
    private Map<String, Location> latestClickedBlocked; //0
    private Map<UUID, AfkObject> afkMap; //0
    private Map<UUID, Map<String, Location>> homesMap; //0
    private Map<UUID, ScreenFocus> screenFocusMap; //0
    private Map<UUID, PowerToolObject> powerToolMap; //0
    private Map<Player, Arrow> sitMap;
    private Map<UUID, MoneyObject> moneyMap;

    private Map<String, UUID> uuidCache; //1
    private Map<String, Location> jails; //1
    private Map<String, List<String>> tabCompleteMap; //1
    private Map<String, ElevatorController> elevatorObjectMap; //1
    private Map<String, IFireAlarm> fireAlarmMap; //1
    private Map<Location, FireAlarmScreen> fireAlarmScreenMap; //1
    private Map<String, Location> warpsMap; //1
    private Map<String, TrafficSystem> trafficSystemMap;

    private List<String> flyList; //0
    private List<String> godmList; //0
    private List<Player> adminListPlayer; //0

    private List<String> adminList; //1
    private List<String> soundsList; //1
    private List<String> spyCommandBlock; //1

    //Constucer
    public SetListMap() {
        this.keyDataM = new HashMap<>();
        this.backM = new HashMap<>();
        this.tpaM = new LinkedHashMap<>();
        this.eRamRaw = new HashMap<>();
        this.latestClickedBlocked = new HashMap<>();
        this.afkMap = new HashMap<>();
        this.homesMap = new HashMap<>();
        this.screenFocusMap = new HashMap<>();
        this.powerToolMap = new HashMap<>();
        this.sitMap = new HashMap<>();
        this.moneyMap = new HashMap<>();

        this.uuidCache = new HashMap<>();
        this.jails = new HashMap<>();
        this.tabCompleteMap = new HashMap<>();
        this.elevatorObjectMap = new HashMap<>();
        this.fireAlarmMap = new HashMap<>();
        this.fireAlarmScreenMap = new HashMap<>();
        this.warpsMap = new HashMap<>();
        this.trafficSystemMap = new HashMap<>();

        //Lists
        this.flyList = new ArrayList<>();
        this.godmList = new ArrayList<>();
        this.adminListPlayer = new ArrayList<>();

        this.adminList = new ArrayList<>();
        this.soundsList = new ArrayList<>();
        this.spyCommandBlock = new ArrayList<>();
    }

    //METHODS
    public void clearSetListMap(Player p) {
        clearAllMaps(p);
        clearAllLists(p);
    }

    public void clearSetListMap() {
        clearAllMaps();
        clearAllLists();
    }

    public void clearAllMaps(Player p) {
        keyDataM.remove(p.getDisplayName());

        backM.remove(p.getUniqueId());

        tpaM.remove(p);

        eRamRaw.remove(p.getDisplayName());

        latestClickedBlocked.remove(p.getDisplayName());

        afkMap.remove(p.getUniqueId());

        homesMap.remove(p.getUniqueId());

        screenFocusMap.remove(p.getUniqueId());

        powerToolMap.remove(p.getUniqueId());

        sitMap.remove(p);

        moneyMap.remove(p.getUniqueId());
    }

    public void clearAllMaps() {
        keyDataM.clear();
        backM.clear();
        tpaM.clear();
        eRamRaw.clear();
        latestClickedBlocked.clear();
        afkMap.clear();
        uuidCache.clear();
        jails.clear();
        tabCompleteMap.clear();
        homesMap.clear();
        elevatorObjectMap.clear();
        fireAlarmMap.clear();
        fireAlarmScreenMap.clear();
        warpsMap.clear();
        screenFocusMap.clear();
        powerToolMap.clear();
        sitMap.clear();
        trafficSystemMap.clear();
        moneyMap.clear();
    }

    public void clearAllLists(Player p) {
        flyList.remove(p.getDisplayName());

        godmList.remove(p.getDisplayName());

        adminListPlayer.remove(p);
    }

    public void clearAllLists() {
        flyList.clear();
        godmList.clear();
        adminListPlayer.clear();
        adminList.clear();
        soundsList.clear();
        spyCommandBlock.clear();
    }

    //Getters

    public Map<String, KeyObject> getKeyDataM() {
        return keyDataM;
    }

    public Map<UUID, LocationObject> getBackM() {
        return backM;
    }

    public Map<Player, Player> getTpaM() {
        return tpaM;
    }

    public Map<String, Map<String, List<Location>>> geteRamRaw() {
        return eRamRaw;
    }

    public Map<String, Location> getLatestClickedBlocked() {
        return latestClickedBlocked;
    }

    public Map<UUID, AfkObject> getAfkMap() {
        return afkMap;
    }

    public Map<UUID, Map<String, Location>> getHomesMap() {
        return homesMap;
    }

    public Map<String, UUID> getUuidCache() {
        return uuidCache;
    }

    public Map<String, Location> getJails() {
        return jails;
    }

    public Map<String, List<String>> getTabCompleteMap() {
        return tabCompleteMap;
    }

    public List<String> getFlyList() {
        return flyList;
    }

    public List<String> getGodmList() {
        return godmList;
    }

    public List<Player> getAdminListPlayer() {
        return adminListPlayer;
    }

    public List<String> getAdminList() {
        return adminList;
    }

    public Map<String, ElevatorController> getElevatorObjectMap() {
        return elevatorObjectMap;
    }

    public Map<String, IFireAlarm> getFireAlarmMap() {
        return fireAlarmMap;
    }

    public Map<Location, FireAlarmScreen> getFireAlarmScreenMap() {
        return fireAlarmScreenMap;
    }

    public Map<String, Location> getWarpsMap() {
        return warpsMap;
    }

    public Map<UUID, ScreenFocus> getScreenFocusMap() {
        return screenFocusMap;
    }

    public List<String> getSoundsList() {
        return soundsList;
    }

    public Map<UUID, PowerToolObject> getPowerToolMap() {
        return powerToolMap;
    }

    public Map<Player, Arrow> getSitMap() {
        return sitMap;
    }

    public List<String> getSpyCommandBlock() {
        return spyCommandBlock;
    }

    public Map<String, TrafficSystem> getTrafficSystemMap() {
        return trafficSystemMap;
    }

    public Map<UUID, MoneyObject> getMoneyMap() {
        return moneyMap;
    }
}
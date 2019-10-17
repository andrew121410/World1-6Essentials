package World16FireAlarms.Objects.Screen;

import World16.Main.Main;
import World16.Utils.Translate;
import World16FireAlarms.Objects.FireAlarmReason;
import World16FireAlarms.Objects.TroubleReason;
import World16FireAlarms.interfaces.IFireAlarm;
import org.bukkit.block.Sign;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

@SerializableAs("FireAlarmSignOS")
public class FireAlarmSignOS implements ConfigurationSerializable {

    private double version = 1.1;

    private Main plugin;

    private String name;
    private String fireAlarmName;

    private FireAlarmSignMenu currentMenu;

    private Map<String, IFireAlarm> fireAlarmMap;

    public FireAlarmSignOS(Main plugin, FireAlarmSignMenu fireAlarmSignMenu, String name, String fireAlarmName) {
        this.plugin = plugin;

        this.fireAlarmMap = this.plugin.getSetListMap().getFireAlarmMap();

        this.name = name;
        this.fireAlarmName = fireAlarmName;

        this.currentMenu = fireAlarmSignMenu;
    }

    public boolean onLine(FireAlarmScreen fireAlarmScreen, Player player, Sign sign, int line, int scroll) {
        //Reverse
        if (line == 0 && scroll == 0) {
            backReverse(this.currentMenu, fireAlarmScreen, sign, line);
            return true;
        }

        if (scroll >= 1) {
//            TODO implement scrolling features.
            return true;
        }

        //Loads for the first time.
        if (this.currentMenu == FireAlarmSignMenu.OFF) {
            loadFirstTime(fireAlarmScreen, sign);
            return true;
        } else if (this.currentMenu == FireAlarmSignMenu.MAIN_MENU) {
            if (line == 1) {
                settings_menu(fireAlarmScreen, sign);
                return true;
            }
            return true;
            //SETTINGS
        } else if (this.currentMenu == FireAlarmSignMenu.SETTINGS_MENU) {
            if (line == 1) {
                settings_menu_test_firealarm(fireAlarmScreen, sign);
                return true;
            } else if (line == 3) {
                settings_menu_info(fireAlarmScreen, sign);
                return true;
            }
            return true;
        } else if (this.currentMenu == FireAlarmSignMenu.SETTINGS_TEST_FIREALARM) {
            if (line == 1) {
                this.fireAlarmMap.get(this.fireAlarmName).alarm(new FireAlarmReason(TroubleReason.PANEL_TEST));
                player.sendMessage(Translate.chat("Alarm should be going off currently."));
                return true;
            } else if (line == 2) {
                player.sendMessage(Translate.chat("NOT IMPLEMENTED."));
                return true;
            } else if (line == 3) {
                this.fireAlarmMap.get(this.fireAlarmName).reset(Optional.empty());
                player.sendMessage(Translate.chat("Fire alarm has been reset."));
                return true;
            }
            return true;
        } else if (this.currentMenu == FireAlarmSignMenu.ALARM_POPUP) {
            if (line == 2) {
                this.fireAlarmMap.get(this.fireAlarmName).reset(Optional.empty());
                backReverse(this.currentMenu, fireAlarmScreen, sign, line);
                player.sendMessage(Translate.chat("The fire alarm has been reset."));
                return true;
            }
        }
        return true;
    }

    public void sendPopup(FireAlarmScreen fireAlarmScreen, Sign sign, FireAlarmReason fireAlarmReason) {
        this.currentMenu = FireAlarmSignMenu.ALARM_POPUP;
        if (fireAlarmReason.getTroubleReason() == TroubleReason.PANEL_TEST) {
            List<String> stringList = new ArrayList<>();
            stringList.add("Popup/MENU");
            stringList.add(fireAlarmReason.getTroubleReason().toString());
            stringList.add("-Reset");
            stringList.add("");

            fireAlarmScreen.setLine(0);
            fireAlarmScreen.setMin(0);
            fireAlarmScreen.setMax(3);

            fireAlarmScreen.updateSign(sign, stringList);
        } else if (fireAlarmReason.getTroubleReason() == TroubleReason.PULL_STATION) {
            List<String> stringList = new ArrayList<>();
            stringList.add("Popup/MENU");
            stringList.add(fireAlarmReason.getTroubleReason().toString());
            stringList.add("-Reset");
            if (fireAlarmReason.getOptionalZone().isPresent()) stringList.add("B1> {below}");

            fireAlarmReason.getOptionalPullStationName().ifPresent(stringList::add);
            fireAlarmReason.getOptionalZone().ifPresent(zone -> stringList.add("Z: " + zone.getName()));
            fireAlarmReason.getOptionalZone().ifPresent(zone -> stringList.add("ZF" + zone.getFloor()));

            fireAlarmScreen.setLine(0);
            fireAlarmScreen.setMin(0);
            fireAlarmScreen.setMax(3);

            fireAlarmScreen.updateSign(sign, stringList);
        }
    }

    private void backReverse(FireAlarmSignMenu menu, FireAlarmScreen fireAlarmScreen, Sign sign, int line) {
        switch (menu) {
            case SETTINGS_MENU:
                main_menu(fireAlarmScreen, sign);
                break;
            case ALARM_POPUP:
                main_menu(fireAlarmScreen, sign);
                break;
            case SETTINGS_TEST_FIREALARM:
            case SETTINGS_INFO:
                settings_menu(fireAlarmScreen, sign);
                break;
        }
    }

    public void resetSign(FireAlarmScreen fireAlarmScreen, Sign sign, boolean backToMainMenu) {
        sign.setLine(0, "");
        sign.setLine(1, "");
        sign.setLine(2, "");
        sign.setLine(3, "");

        fireAlarmScreen.setLine(0);
        fireAlarmScreen.setMin(0);
        fireAlarmScreen.setMax(3);

        fireAlarmScreen.updateSign(sign);

        if (backToMainMenu) main_menu(fireAlarmScreen, sign);
    }

    public void loadFirstTime(FireAlarmScreen fireAlarmScreen, Sign sign) {
        this.currentMenu = FireAlarmSignMenu.WAITING;

        sign.setLine(0, "Bexar-Systems");
        sign.setLine(1, "Version: " + this.version);
        sign.setLine(2, "Loading data...");
        sign.setLine(3, "Please wait...");
        fireAlarmScreen.updateSign(sign);

        new BukkitRunnable() {
            @Override
            public void run() {
                resetSign(fireAlarmScreen, sign, true);
            }
        }.runTaskLater(this.plugin, 100L);
    }

    private void main_menu(FireAlarmScreen fireAlarmScreen, Sign sign) {
        this.currentMenu = FireAlarmSignMenu.MAIN_MENU;
        sign.setLine(0, "Bexar-Systems");
        sign.setLine(1, "-Settings");
        sign.setLine(2, "");
        sign.setLine(3, "");

        fireAlarmScreen.setMin(1);
        fireAlarmScreen.setLine(1);
        fireAlarmScreen.updateSign(sign);
    }

    private void settings_menu(FireAlarmScreen fireAlarmScreen, Sign sign) {
        this.currentMenu = FireAlarmSignMenu.SETTINGS_MENU;
        sign.setLine(0, "Settings/MENU");
        sign.setLine(1, "-Test Fire Alarm");
        sign.setLine(2, "");
        sign.setLine(3, "-Info");

        fireAlarmScreen.setMin(0);
        fireAlarmScreen.setLine(0);
        fireAlarmScreen.updateSign(sign);
    }

    private void settings_menu_test_firealarm(FireAlarmScreen fireAlarmScreen, Sign sign) {
        this.currentMenu = FireAlarmSignMenu.SETTINGS_TEST_FIREALARM;
        sign.setLine(0, "Settings/Test");
        sign.setLine(1, "-Alarm");
        sign.setLine(2, "Trouble");
        sign.setLine(3, "-Reset");
        fireAlarmScreen.setMin(0);
        fireAlarmScreen.setLine(0);
        fireAlarmScreen.updateSign(sign);
    }

    public void settings_menu_info(FireAlarmScreen fireAlarmScreen, Sign sign) {
        this.currentMenu = FireAlarmSignMenu.SETTINGS_INFO;
        IFireAlarm iFireAlarm = this.fireAlarmMap.get(fireAlarmName);
        List<String> stringList = new ArrayList<>();

        stringList.add("Settings/Info");
        stringList.add(this.fireAlarmName);
        stringList.add("Version: " + this.version);
        stringList.add("B2>NOS: " + iFireAlarm.getStrobesMap().size());

        stringList.add("Sound: {below}");
        stringList.add(iFireAlarm.getFireAlarmSound().getSound().name());
        stringList.add("Volume: " + iFireAlarm.getFireAlarmSound().getVolume());
        stringList.add("Pitch: " + iFireAlarm.getFireAlarmSound().getPitch());

        stringList.add("Tempo: " + iFireAlarm.getFireAlarmTempo().name());

        fireAlarmScreen.setMin(0);
        fireAlarmScreen.setMax(3);
        fireAlarmScreen.setLine(0);
        fireAlarmScreen.updateSign(sign, stringList);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("Name", this.name);
        map.put("FireAlarmName", this.fireAlarmName);
        map.put("CurrentMenu", this.currentMenu.toString());
        return map;
    }

    public static FireAlarmSignOS deserialize(Map<String, Object> map) {
        return new FireAlarmSignOS(Main.getPlugin(), FireAlarmSignMenu.valueOf((String) map.get("CurrentMenu")), (String) map.get("Name"), (String) map.get("FireAlarmName"));
    }
}

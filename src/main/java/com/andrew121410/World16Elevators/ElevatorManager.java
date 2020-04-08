package com.andrew121410.World16Elevators;

import com.andrew121410.World16.Main.Main;
import com.andrew121410.World16.Managers.CustomConfigManager;
import com.andrew121410.World16.Managers.CustomYmlManager;
import com.andrew121410.World16.Utils.Translate;
import com.andrew121410.World16Elevators.Objects.ElevatorController;
import com.andrew121410.World16Elevators.Objects.ElevatorObject;
import com.andrew121410.World16Elevators.Objects.FloorObject;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Map;

public class ElevatorManager {

    private Main plugin;

    private Map<String, ElevatorController> elevatorObjectMap;

    private CustomYmlManager elevatorsYml;
    private boolean on;

    public ElevatorManager(Main plugin, CustomConfigManager customConfigManager, boolean on) {
        this.on = on;
        this.plugin = plugin;
        this.elevatorObjectMap = this.plugin.getSetListMap().getElevatorObjectMap();
        this.elevatorsYml = customConfigManager.getElevatorsYml();
    }

    public void loadAllElevators() {
        if (!on || !this.plugin.getOtherPlugins().hasWorldEdit()) return;

        //This runs when elevator.yml is first created.
        ConfigurationSection elevatorControllersSection = this.elevatorsYml.getConfig().getConfigurationSection("ElevatorControllers");
        if (elevatorControllersSection == null) {
            elevatorControllersSection = this.elevatorsYml.getConfig().createSection("ElevatorControllers");
            this.elevatorsYml.saveConfig();
            this.plugin.getServer().getConsoleSender().sendMessage(Translate.chat("&c[ElevatorManager]&r&6 ElevatorControllers section has been created."));
            return;
        }

        for (String elevatorControllerName : elevatorControllersSection.getKeys(false)) {
            ConfigurationSection elevatorControllerSection = elevatorControllersSection.getConfigurationSection(elevatorControllerName);
            ElevatorController elevatorController = (ElevatorController) elevatorControllerSection.get("ElevatorController");

            //Elevators
            ConfigurationSection elevatorsSection = elevatorControllerSection.getConfigurationSection("Elevators");
            for (String elevatorName : elevatorsSection.getKeys(false)) {
                //Elevator
                ConfigurationSection elevatorSection = elevatorsSection.getConfigurationSection(elevatorName);
                ElevatorObject elevatorObject = (ElevatorObject) elevatorSection.get("ElevatorObject");

                //Floors
                ConfigurationSection elevatorFloors = elevatorSection.getConfigurationSection("Floors");
                for (String floorNumber : elevatorFloors.getKeys(false)) {
                    elevatorObject.addFloor((FloorObject) elevatorFloors.get(floorNumber));
                }
                elevatorController.registerElevator(elevatorName, elevatorObject);
            }
            this.elevatorObjectMap.put(elevatorControllerName.toLowerCase(), elevatorController);
        }
    }

    public void saveAllElevators() {
        if (!on || !this.plugin.getOtherPlugins().hasWorldEdit()) return;

        ConfigurationSection elevatorControllersSection = this.elevatorsYml.getConfig().getConfigurationSection("ElevatorControllers");
        if (elevatorControllersSection == null) {
            elevatorControllersSection = this.elevatorsYml.getConfig().createSection("ElevatorControllers");
            this.elevatorsYml.saveConfig();
        }

        //For each elevator controller.
        for (Map.Entry<String, ElevatorController> mapEntry : this.elevatorObjectMap.entrySet()) {
            String controllerName = mapEntry.getKey();
            ElevatorController elevatorController = mapEntry.getValue();

            //Elevator controller.
            ConfigurationSection elevatorControllerSection = elevatorControllersSection.getConfigurationSection(controllerName);
            if (elevatorControllerSection == null) {
                elevatorControllerSection = elevatorControllersSection.createSection(controllerName);
                this.elevatorsYml.saveConfig();
            }

            elevatorControllerSection.set("ElevatorController", elevatorController);

            //Elevators
            String elevatorsLocation = "Elevators";
            ConfigurationSection elevatorsSection = elevatorControllerSection.getConfigurationSection(elevatorsLocation);
            if (elevatorsSection == null) {
                elevatorsSection = elevatorControllerSection.createSection(elevatorsLocation);
                this.elevatorsYml.saveConfig();
            }

            //For each elevator.
            for (Map.Entry<String, ElevatorObject> entry : elevatorController.getElevatorsMap().entrySet()) {
                String elevatorName = entry.getKey();
                ElevatorObject elevatorObject = entry.getValue();

                //Elevator
                ConfigurationSection elevatorSection = elevatorsSection.getConfigurationSection(elevatorName);
                if (elevatorSection == null) {
                    elevatorSection = elevatorsSection.createSection(elevatorName);
                    this.elevatorsYml.saveConfig();
                }

                elevatorSection.set("ElevatorObject", elevatorObject);

                //Floors
                ConfigurationSection elevatorFloors = elevatorSection.getConfigurationSection("Floors");
                if (elevatorFloors == null) {
                    elevatorFloors = elevatorSection.createSection("Floors");
                    this.elevatorsYml.saveConfig();
                }

                //For each floor do.
                for (Map.Entry<Integer, FloorObject> e : elevatorObject.getFloorsMap().entrySet()) {
                    Integer k2 = e.getKey();
                    FloorObject v2 = e.getValue();
                    elevatorFloors.set(String.valueOf(k2), v2);
                }
                this.elevatorsYml.saveConfig();
            }
            this.elevatorsYml.saveConfig();
        }
    }

    public void deleteElevatorController(String name) {
        if (!on || !this.plugin.getOtherPlugins().hasWorldEdit()) return;

        this.elevatorObjectMap.remove(name.toLowerCase());

        ConfigurationSection elevatorControllersSection = this.elevatorsYml.getConfig().getConfigurationSection("ElevatorControllers");
        if (elevatorControllersSection == null) return;

        elevatorControllersSection.set(name.toLowerCase(), null);
        this.elevatorsYml.saveConfig();
    }

    public void deleteElevator(String elevatorControllerName, String elevatorName) {
        if (!on || !this.plugin.getOtherPlugins().hasWorldEdit()) return;

        ElevatorController elevatorController = this.elevatorObjectMap.get(elevatorControllerName);
        if (elevatorController == null) return;
        elevatorController.getElevatorsMap().remove(elevatorName);

        ConfigurationSection elevatorsSection = this.elevatorsYml.getConfig().getConfigurationSection("ElevatorControllers." + elevatorControllerName.toLowerCase() + ".Elevators");
        if (elevatorsSection == null) return;

        elevatorsSection.set(elevatorName, null);
        this.elevatorsYml.saveConfig();
    }

    public void deleteFloorOfElevator(String elevatorControllerName, String elevatorName, int floorNum) {
        if (!on || !this.plugin.getOtherPlugins().hasWorldEdit()) return;

        ElevatorController elevatorController = this.elevatorObjectMap.get(elevatorControllerName);
        if (elevatorController == null) return;
        ElevatorObject elevatorObject = elevatorController.getElevatorsMap().get(elevatorName);
        elevatorObject.deleteFloor(floorNum);

        ConfigurationSection elevatorFloors = this.elevatorsYml.getConfig().getConfigurationSection("ElevatorControllers." + elevatorControllerName.toLowerCase() + ".Elevators." + elevatorName + ".Floors");
        if (elevatorFloors == null) return;

        elevatorFloors.set(String.valueOf(floorNum), null);
        this.elevatorsYml.saveConfig();
    }
}
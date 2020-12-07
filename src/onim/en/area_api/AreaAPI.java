package onim.en.area_api;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import onim.en.area_api.area.AreaStorage;
import onim.en.area_api.area.AreaTaskWorker;
import onim.en.area_api.area.builder.AreaBuilderManager;
import onim.en.area_api.command.AreaCommand;

public class AreaAPI extends JavaPlugin {

  private static JavaPlugin instance;

  public static Logger logger;
  public static File dataDir;

  public static JavaPlugin getInstance() {
    return AreaAPI.instance;
  }

  public AreaAPI() {
    AreaAPI.instance = this;
    AreaAPI.logger = this.getLogger();
    AreaAPI.dataDir = this.getDataFolder();
  }

  @Override
  public void onEnable() {
    this.getServer().getPluginManager().registerEvents(new PlayerEventListener(), this);
    AreaStorage.loadAreas();
    AreaTaskWorker.start();

    AreaCommand areaCommand = new AreaCommand();

    getCommand("area").setExecutor(areaCommand);
    getCommand("area").setTabCompleter(areaCommand);
  }

  @Override
  public void onDisable() {
    AreaBuilderManager.onDisable();
    AreaStorage.saveAreas();
  }

}

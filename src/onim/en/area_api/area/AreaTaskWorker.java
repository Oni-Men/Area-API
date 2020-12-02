package onim.en.area_api.area;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import onim.en.area_api.AreaAPI;

public class AreaTaskWorker extends BukkitRunnable {

  private static AreaTaskWorker worker = null;

  public static void start() {
    if (worker == null) {
      new AreaTaskWorker().runTaskTimer(AreaAPI.getInstance(), 0L, 4L);
    }
  }

  private AreaTaskWorker() {
  }

  @Override
  public void run() {
    AreaManager.getAllAreas().forEach(a -> {
      a.update(Bukkit.getOnlinePlayers());
    });
  }

}

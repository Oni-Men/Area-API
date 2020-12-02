package onim.en.area_api.command.executor;

import org.bukkit.entity.Player;

import onim.en.area_api.area.AreaManager;
import onim.en.area_api.area.AreaModel;
import onim.en.area_api.command.AreaCommandExecutor;

public class RemoveCommand extends AreaCommandExecutor {

  @Override
  public boolean execute(Player player, String[] args) {

    if (args.length == 0)
      return false;

    String areaName = args[0];

    AreaModel area = AreaManager.getAreaByName(areaName);

    if (area == null) {
      this.error(player, String.format("Couldn't find the area \"%s\".", areaName));
      return false;
    }

    AreaManager.unregister(area);
    this.info(player, String.format("\"%s\" removed successfully.", areaName));

    return true;
  }

}

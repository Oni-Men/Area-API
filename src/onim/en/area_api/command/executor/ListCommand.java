package onim.en.area_api.command.executor;

import org.bukkit.entity.Player;

import onim.en.area_api.area.AreaManager;
import onim.en.area_api.area.AreaModel;
import onim.en.area_api.command.AreaCommandExecutor;

public class ListCommand extends AreaCommandExecutor {

  @Override
  public boolean execute(Player player, String[] args) {

    for (AreaModel area : AreaManager.getAllAreas()) {
      this.info(player, String.format("%s, %s (%s)", area.getDecoratedName(), area.getDecoratedMessage(),
          area.getType().getLiteral()));
    }

    return true;
  }

}

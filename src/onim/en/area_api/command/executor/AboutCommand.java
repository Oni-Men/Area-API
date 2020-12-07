package onim.en.area_api.command.executor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import onim.en.area_api.area.AreaManager;
import onim.en.area_api.area.AreaModel;
import onim.en.area_api.command.AreaCommandExecutor;

public class AboutCommand extends AreaCommandExecutor {

  @Override
  public boolean execute(Player player, String[] args) {

    AreaModel area = AreaManager.getAreaByName(args[0]);

    if (area == null) {
      this.error(player, String.format("Couldn't find the area \"%s\"", args[0]));
      return false;
    }

    this.info(player, "");
    this.info(player, "Name : " + area.getAreaName());
    this.info(player, "Message : " + area.getAreaMessage());
    this.info(player, String.format("%d Players in this Area", area.getPlayersInsideArea().size()));
    this.info(player, "Id : " + area.getAreaId());

    return true;
  }

  @Override
  public List<String> completion(String[] args) {
    if (args.length == 1) {
      List<String> list = new ArrayList<>();
      list.addAll(AreaManager.getAreaNames());
      return list;
    }

    return super.completion(args);
  }

}

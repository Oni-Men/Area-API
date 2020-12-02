package onim.en.area_api.command.executor;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import joptsimple.internal.Strings;
import onim.en.area_api.area.builder.AreaBuilder;
import onim.en.area_api.area.builder.AreaBuilderManager;
import onim.en.area_api.area.builder.CircleBuilder;
import onim.en.area_api.area.builder.RectangleBuilder;
import onim.en.area_api.area.model.CircleArea;
import onim.en.area_api.area.model.RectangleArea;
import onim.en.area_api.command.AreaCommandExecutor;

public class CreateCommand extends AreaCommandExecutor {

  @Override
  public boolean execute(Player player, String[] args) {
    if (args.length <= 2) {
      player.sendMessage(ChatColor.RED + "The size of the argument was not enough.");
      return false;
    }

    String areaType = args[0];
    String areaName = args[1];
    String areaMessage = Strings.join(Arrays.copyOfRange(args, 2, args.length), " ");

    switch (areaType) {
    case "rect":
      this.createRectangle(player, areaName, areaMessage);
      break;
    case "circle":
      this.createCircle(player, areaName, areaMessage);
      break;
    case "polygon":
      this.createPolygon(player, areaName, areaMessage);
      break;
    }

    return true;
  }

  private void createRectangle(Player player, String areaName, String areaMessage) {
    AreaBuilder<RectangleArea> builder = new RectangleBuilder(player).name(areaName).message(areaMessage);
    AreaBuilderManager.register(player.getUniqueId(), builder);
    builder.sendInstruction();
  }

  private void createCircle(Player player, String areaName, String areaMessage) {
    AreaBuilder<CircleArea> builder = new CircleBuilder(player).name(areaName).message(areaMessage);
    AreaBuilderManager.register(player.getUniqueId(), builder);
    builder.sendInstruction();
  }

  private void createPolygon(Player player, String areaName, String areaMessage) {

  }
}

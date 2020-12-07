package onim.en.area_api.command.executor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

import joptsimple.internal.Strings;
import onim.en.area_api.area.AreaManager;
import onim.en.area_api.area.AreaType;
import onim.en.area_api.area.builder.AreaBuilder;
import onim.en.area_api.area.builder.AreaBuilderManager;
import onim.en.area_api.area.builder.CircleBuilder;
import onim.en.area_api.area.builder.PolygonBuilder;
import onim.en.area_api.area.builder.RectangleBuilder;
import onim.en.area_api.area.model.CircleArea;
import onim.en.area_api.area.model.PolygonArea;
import onim.en.area_api.area.model.RectangleArea;
import onim.en.area_api.command.AreaCommandExecutor;

public class CreateCommand extends AreaCommandExecutor {

  @Override
  public boolean execute(Player player, String[] args) {
    if (args.length <= 2) {
      this.error(player, "The size of the argument was not enough.");
      return false;
    }

    AreaType areaType = AreaType.fromLiteral(args[0]);
    if (areaType == null) {
      this.error(player, String.format("Unknown area type \"%s\"", args[0]));
      return false;
    }

    String areaName = args[1];
    String areaMessage = Strings.join(Arrays.copyOfRange(args, 2, args.length), " ");

    if (AreaManager.exists(areaName)) {
      this.error(player, String.format("%s has already existed.", areaName));
      return false;
    }

    switch (areaType) {
    case RECTANGLE:
      this.createRectangle(player, areaName, areaMessage);
      break;
    case CIRCLE:
      this.createCircle(player, areaName, areaMessage);
      break;
    case POLYGON:
      this.createPolygon(player, areaName, areaMessage);
      break;
    }
    return true;
  }

  @Override
  public List<String> completion(String[] args) {
    List<String> list = new ArrayList<>();
    switch (args.length) {
    case 1:
      for (AreaType type : AreaType.values()) {
        list.add(type.getLiteral());
      }
      break;
    default:
    }
    return list;
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
    AreaBuilder<PolygonArea> builder = new PolygonBuilder(player).name(areaName).message(areaMessage);
    AreaBuilderManager.register(player.getUniqueId(), builder);
    builder.sendInstruction();
  }
}

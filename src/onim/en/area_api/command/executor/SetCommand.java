package onim.en.area_api.command.executor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import onim.en.area_api.area.AreaManager;
import onim.en.area_api.area.AreaModel;
import onim.en.area_api.area.AreaType;
import onim.en.area_api.area.builder.AreaBuilder;
import onim.en.area_api.area.builder.AreaBuilderManager;
import onim.en.area_api.area.builder.CircleBuilder;
import onim.en.area_api.area.builder.RectangleBuilder;
import onim.en.area_api.area.model.CircleArea;
import onim.en.area_api.area.model.RectangleArea;
import onim.en.area_api.command.AreaCommandExecutor;

public class SetCommand extends AreaCommandExecutor {

  @Override
  public boolean execute(Player player, String[] args) {
    if (args.length <= 1) {
      this.error(player, "Must need more than one arguments to execute \"set\"");
      return false;
    }

    String paramType = args[0];
    AreaModel area = AreaManager.getAreaByName(args[1]);

    if (area == null) {
      this.error(player, String.format("Couldn't find the area \"%s\"", args[1]));
      return false;
    }

    String[] subArgs = Arrays.copyOfRange(args, 2, 1);

    switch (paramType) {
    case "name":
      if (subArgs.length == 0) {
        this.error(player, "You must specify new area name. empty string not allowed");
        return false;
      }
      area.setAreaMessage(subArgs[0]);
      break;
    case "message":
      area.setAreaMessage(String.join(" ", subArgs));
      break;
    case "decoration":
      area.setDecorators(this.toDecorationArray(subArgs));
      break;
    case "area":
      if (subArgs.length == 0) {
        this.error(player, "Please specify the area type to execute.");
        return false;
      }
      return this.areaExecutor(player, area, subArgs);
    }
    return false;
  }

  private ChatColor[] toDecorationArray(String[] subArgs) {
    List<ChatColor> decorations = Stream.of(subArgs)
        .map(s -> ChatColor.valueOf(s))
        .filter(d -> d != null)
        .collect(Collectors.toList());
    return decorations.toArray(new ChatColor[decorations.size()]);
  }

  private boolean areaExecutor(Player player, AreaModel area, String[] args) {
    AreaType areaType = AreaType.fromLiteral(args[0]);
    if (areaType == null) {
      this.error(player, String.format("Unknown area type \"%s\"", args[0]));
      return false;
    }

    switch (areaType) {
    case RECTANGLE:
      this.createRectangle(player, area);
      break;
    case CIRCLE:
      this.createCircle(player, area);
      break;
    case POLYGON:
      this.createPolygon(player);
      break;
    }

    return false;
  }

  private void createRectangle(Player player, AreaModel area) {
    AreaBuilder<RectangleArea> builder = new RectangleBuilder(player, area);
    AreaBuilderManager.register(player.getUniqueId(), builder);
    builder.sendInstruction();
  }

  private void createCircle(Player player, AreaModel area) {
    AreaBuilder<CircleArea> builder = new CircleBuilder(player, area);
    AreaBuilderManager.register(player.getUniqueId(), builder);
    builder.sendInstruction();
  }

  private void createPolygon(Player player) {

  }
}

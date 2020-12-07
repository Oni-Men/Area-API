package onim.en.area_api.command.executor;

import java.util.ArrayList;
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
import onim.en.area_api.area.builder.PolygonBuilder;
import onim.en.area_api.area.builder.RectangleBuilder;
import onim.en.area_api.area.model.CircleArea;
import onim.en.area_api.area.model.PolygonArea;
import onim.en.area_api.area.model.RectangleArea;
import onim.en.area_api.command.AreaCommandExecutor;

public class SetCommand extends AreaCommandExecutor {

  @Override
  public boolean execute(Player player, String[] args) {
    if (args.length <= 1) {
      this.error(player, "Must need more than one arguments to execute \"set\"");
      return false;
    }

    ParamType paramType = ParamType.fromLiteral(args[0]);
    AreaModel area = AreaManager.getAreaByName(args[1]);

    if (area == null) {
      this.error(player, String.format("Couldn't find the area \"%s\"", args[1]));
      return false;
    }

    String[] subArgs = Arrays.copyOfRange(args, 2, args.length);

    switch (paramType) {
    case Name:
      if (subArgs.length == 0) {
        this.error(player, "Empty string is not allowed");
        return false;
      }
      area.setAreaName(String.join(" ", subArgs));
      break;
    case Message:
      area.setAreaMessage(String.join(" ", subArgs));
      break;
    case Decoration:
      area.setDecorators(this.toDecorationArray(subArgs));
      break;
    case Area:
      if (subArgs.length == 0) {
        this.error(player, "Please specify the area type to execute.");
        return false;
      }
      return this.areaExecutor(player, area, subArgs);
    }
    return false;
  }

  @Override
  public List<String> completion(String[] args) {
    List<String> list = new ArrayList<>();

    switch (args.length) {
    case 1:
      for (ParamType type : ParamType.values()) {
        list.add(type.getLiteral());
      }
      break;
    case 2:
      list.addAll(AreaManager.getAreaNames());
      break;
    case 3:
      ParamType paramType = ParamType.fromLiteral(args[0]);
      switch (paramType) {
      case Area:
        for (AreaType type : AreaType.values()) {
          list.add(type.getLiteral());
        }
        break;
      case Decoration:
        for (ChatColor color : ChatColor.values()) {
          list.add(color.name());
        }
        break;
      default:
        break;
      }
    }

    return list;
  }

  private ChatColor[] toDecorationArray(String[] subArgs) {
    List<ChatColor> decorations = Stream.of(subArgs)
        .map(s -> {
          try {
            return ChatColor.valueOf(s.toUpperCase());
          } catch (IllegalArgumentException e) {
            return null;
          }
        })
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
      this.createPolygon(player, area);
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

  private void createPolygon(Player player, AreaModel area) {
    AreaBuilder<PolygonArea> builder = new PolygonBuilder(player, area);
    AreaBuilderManager.register(player.getUniqueId(), builder);
    builder.sendInstruction();
  }
}

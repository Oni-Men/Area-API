package onim.en.area_api.area.builder;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import onim.en.area_api.area.model.AbstractArea;

public class AreaBuilderManager {
  private static final HashMap<UUID, AreaBuilder<?>> idToBuilder = Maps.newHashMap();

  public static void register(UUID uuid, AreaBuilder<?> builder) {
    unregister(uuid);
    idToBuilder.put(uuid, builder);
  }

  public static void unregister(UUID uuid) {
    idToBuilder.computeIfPresent(uuid, (k, v) -> {
      v.onDestroy();
      return null;
    });
  }

  public static AreaBuilder<? extends AbstractArea> getBuilderByPlayer(Player player) {
    return idToBuilder.get(player.getUniqueId());
  }
}

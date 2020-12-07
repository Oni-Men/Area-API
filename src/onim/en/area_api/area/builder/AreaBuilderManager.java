package onim.en.area_api.area.builder;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import onim.en.area_api.AreaAPI;
import onim.en.area_api.area.model.AbstractArea;

public class AreaBuilderManager {
  private static final HashMap<UUID, AreaBuilder<?>> idToBuilder = Maps.newHashMap();
  private static final HashMap<UUID, Map<Location, BlockData>> idToBlocks = Maps.newHashMap();
  private static final HashMap<UUID, Long> idToTick = Maps.newHashMap();

  public static void register(UUID uuid, AreaBuilder<?> builder) {
    unregister(uuid);
    idToBuilder.put(uuid, builder);
  }

  public static void registerGuides(UUID uuid, Map<Location, BlockData> data) {
    idToBlocks.put(uuid, data);
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

  public static boolean onLeftClick(Player player, Block block) {
    AreaBuilder<? extends AbstractArea> builder = AreaBuilderManager.getBuilderByPlayer(player);
    if (builder == null)
      return false;

    if (hasCooldown(player))
      return false;

    builder.onLeftClick(block);
    builder.updateGuide();
    return true;
  }

  public static boolean onRightClick(Player player, Block block) {
    AreaBuilder<? extends AbstractArea> builder = AreaBuilderManager.getBuilderByPlayer(player);
    if (builder == null)
      return false;

    if (hasCooldown(player))
      return false;

    builder.onRightClick(block);
    builder.updateGuide();
    return true;
  }

  private static boolean hasCooldown(Player player) {
    long now = player.getWorld().getFullTime();
    if (idToTick.containsKey(player.getUniqueId())) {
      Long elapsed = now - idToTick.get(player.getUniqueId());
      if (elapsed < 10)
        return true;

      idToTick.remove(player.getUniqueId());
    }
    idToTick.put(player.getUniqueId(), now);
    return false;
  }

  public static boolean sendBlockChanges(Player player) {
    Map<Location, BlockData> blocks = idToBlocks.get(player.getUniqueId());
    exposeOnGround(blocks);
    if (blocks == null)
      return false;
    Bukkit.getScheduler().runTaskLater(AreaAPI.getInstance(), () -> {
      for (Entry<Location, BlockData> entry : blocks.entrySet()) {
        player.sendBlockChange(entry.getKey(), entry.getValue());
      }
    }, 1L);
    return true;
  }

  public static boolean restoreBlockChanges(Player player) {
    Map<Location, BlockData> blocks = idToBlocks.get(player.getUniqueId());
    if (blocks == null)
      return false;
    for (Location loc : blocks.keySet()) {
      BlockData data = loc.getWorld().getBlockAt(loc).getBlockData();
      player.sendBlockChange(loc, data);
    }
    idToBlocks.remove(player.getUniqueId());
    return true;
  }

  private static void exposeOnGround(Map<Location, BlockData> blocks) {
    Iterator<Entry<Location, BlockData>> iterator = blocks.entrySet().iterator();

    while (iterator.hasNext()) {
      Entry<Location, BlockData> next = iterator.next();
      World world = next.getKey().getWorld();
      while (!world.getBlockAt(next.getKey().clone().add(0, 1, 0)).isEmpty()) {
        next.getKey().add(0, 1, 0);
      }
    }
  }

  public static void onDisable() {
    for (UUID uuid : idToBlocks.keySet()) {
      Player player = Bukkit.getPlayer(uuid);
      if (player != null) {
        restoreBlockChanges(player);
      }
    }
  }
}

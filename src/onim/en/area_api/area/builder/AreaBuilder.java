package onim.en.area_api.area.builder;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;

import onim.en.area_api.AreaAPI;
import onim.en.area_api.area.AreaManager;
import onim.en.area_api.area.AreaModel;
import onim.en.area_api.area.model.AbstractArea;
import onim.en.area_api.util.AreaUtils;

public abstract class AreaBuilder<T extends AbstractArea> {

  public final Player player;
  protected String areaId;
  protected String areaName;
  protected String areaMessage;
  protected ChatColor[] decorators;

  public AreaBuilder(Player player) {
    Preconditions.checkNotNull(player, "The given argument player must not be null");
    this.player = player;
  }

  public AreaBuilder(Player player, AreaModel area) {
    this(player);
    this.areaId = area.getAreaId();
    this.areaName = area.getAreaName();
    this.areaMessage = area.getAreaMessage();
    this.decorators = area.getDecorators();
  }

  public void onDestroy() {
  }

  public AreaBuilder<T> id(String areaId) {
    this.areaId = areaId;
    return this;
  }

  public AreaBuilder<T> name(String name) {
    this.areaName = name;
    return this;
  }

  public AreaBuilder<T> message(String message) {
    this.areaMessage = message;
    return this;
  }

  public AreaBuilder<T> decorator(ChatColor[] decorators) {
    this.decorators = decorators;
    return this;
  }

  public void createAndRegister() {
    if (this.areaId == null) {
      this.areaId = AreaUtils.createAreaId();
    }

    T create = this.create();

    if (create == null)
      return;

    AreaManager.register(create);
    AreaBuilderManager.unregister(this.player.getUniqueId());
    Bukkit.getScheduler().runTaskLater(AreaAPI.getInstance(), () -> {
      AreaBuilderManager.restoreBlockChanges(player);
    }, 2L);

    this.player.sendMessage(ChatColor.GREEN + String.format("\"%s\" created successfully.", create.getAreaName()));
  }

  public void updateGuide() {
    AreaBuilderManager.restoreBlockChanges(player);
    AreaBuilderManager.registerGuides(player.getUniqueId(), this.getCurrentGuide(player));
    AreaBuilderManager.sendBlockChanges(player);
  }

  public void sendInstruction() {
    List<String> instructions = this.getInstructions();
    if (instructions == null)
      return;

    instructions.forEach(i -> player.sendMessage(ChatColor.LIGHT_PURPLE + i));
  }

  public abstract Map<Location, BlockData> getCurrentGuide(Player player);

  public abstract void onLeftClick(Block block);

  public abstract void onRightClick(Block block);

  public abstract List<String> getInstructions();

  public abstract T create();
}

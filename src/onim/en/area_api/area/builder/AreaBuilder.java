package onim.en.area_api.area.builder;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import com.google.common.base.Preconditions;

import onim.en.area_api.area.AreaManager;
import onim.en.area_api.area.model.AbstractArea;

public abstract class AreaBuilder<T extends AbstractArea> {

  public final Player player;
  public String areaName;
  public String areaMessage;

  public AreaBuilder(Player player) {
    Preconditions.checkNotNull(player, "The given argument player must not be null");
    this.player = player;
  }

  public void onDestroy() {
  }

  public AreaBuilder<T> name(String name) {
    this.areaName = name;
    return this;
  }

  public AreaBuilder<T> message(String message) {
    this.areaMessage = message;
    return this;
  }

  public void createAndRegister() {
    T create = this.create();

    if (create == null)
      return;

    AreaManager.register(create);
    AreaBuilderManager.unregister(this.player.getUniqueId());

    this.player.sendMessage(ChatColor.GREEN + String.format("\"%s\" created successfully.", create.getAreaName()));
  }

  public void sendInstruction() {
    List<String> instructions = this.getInstructions();
    if (instructions == null)
      return;

    instructions.forEach(i -> player.sendMessage(ChatColor.LIGHT_PURPLE + i));
  }

  public abstract void onLeftClick(Block block);

  public abstract void onRightClick(Block block);

  public abstract List<String> getInstructions();

  public abstract T create();
}

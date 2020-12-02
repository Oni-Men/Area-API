package onim.en.area_api.area.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.google.gson.annotations.Expose;

import onim.en.area_api.area.AreaModel;
import onim.en.area_api.area.AreaType;
import onim.en.area_api.event.PlayerEnterAreaEvent;
import onim.en.area_api.event.PlayerLeaveAreaEvent;

public abstract class AbstractArea implements AreaModel {

  private List<UUID> players;
  @Expose
  protected final AreaType areaType;
  @Expose
  protected String areaId;
  @Expose
  protected String areaName;
  @Expose
  protected String areaMessage;
  @Expose
  protected ChatColor[] decorators;

  public AbstractArea(AreaType areaType, String areaId, String areaName) {
    this.areaType = areaType;
    this.areaId = areaId;
    this.areaName = areaName;
    this.players = new ArrayList<>();
  }

  @Override
  public String getAreaId() {
    return this.areaId;
  }

  @Override
  public void setAreaName(String areaName) {
    this.areaName = areaName;
  }

  @Override
  public String getAreaName() {
    return this.areaName;
  }

  @Override
  public void setAreaMessage(String areaMessage) {
    this.areaMessage = areaMessage;
  }

  @Override
  public String getAreaMessage() {
    return this.areaMessage;
  }

  @Override
  public List<UUID> getPlayersInsideArea() {
    return this.players;
  }

  @Override
  public ChatColor[] getDecorators() {
    return this.decorators;
  }

  @Override
  public void setDecorators(ChatColor[] decorators) {
    this.decorators = decorators;
  }

  @Override
  public AreaType getType() {
    return this.areaType;
  }

  @Override
  public String getDecoratedName() {
    return this.joinDecorators(this.getDecorators()) + this.getAreaName();
  }

  @Override
  public String getDecoratedMessage() {
    return this.joinDecorators(this.getDecorators()) + this.getAreaMessage();
  }

  private String joinDecorators(ChatColor[] decorators) {
    if (decorators == null)
      return "";

    StringBuffer buffer = new StringBuffer();
    for (ChatColor c : decorators) {
      buffer.append(c);
    }
    return buffer.toString();
  }

  @Override
  public boolean isPlayerInside(Player player) {
    return this.getPlayersInsideArea().contains(player.getUniqueId());
  }

  protected void removeIfPresent(Player player) {
    if (this.players == null) {
      this.players = new ArrayList<>();
      return;
    }

    if (!this.players.contains(player.getUniqueId()))
      return;

    this.players.remove(player.getUniqueId());
    Bukkit.getPluginManager().callEvent(new PlayerLeaveAreaEvent(player, this));
  }

  protected void addIfAbsent(Player player) {
    if (this.players == null) {
      this.players = new ArrayList<>();
      return;
    }

    if (this.players.contains(player.getUniqueId()))
      return;

    this.players.add(player.getUniqueId());
    Bukkit.getPluginManager().callEvent(new PlayerEnterAreaEvent(player, this));
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;

    if (!(obj instanceof AreaModel))
      return false;

    return ((AreaModel) obj).getAreaId().equals(this.getAreaId());
  }

  public int hashCode() {
    int r = 37;
    r = r * 31 + this.getAreaId().hashCode();
    return r;
  }
}

package onim.en.area_api.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class AreaCommandExecutor {

  public abstract boolean execute(Player player, String[] args);

  public List<String> completion(String[] args) {
    return new ArrayList<>();
  }

  protected void info(Player player, String message) {
    player.sendMessage(ChatColor.GREEN + message);
  }

  protected void error(Player player, String message) {
    player.sendMessage(ChatColor.RED + message);
  }
}

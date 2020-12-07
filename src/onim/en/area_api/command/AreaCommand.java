package onim.en.area_api.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import onim.en.area_api.command.executor.AboutCommand;
import onim.en.area_api.command.executor.CreateCommand;
import onim.en.area_api.command.executor.ListCommand;
import onim.en.area_api.command.executor.RemoveCommand;
import onim.en.area_api.command.executor.SetCommand;

public class AreaCommand implements CommandExecutor, TabCompleter {

  public static HashMap<CommandOperation, AreaCommandExecutor> operationToExecutor = Maps.newHashMap();
  static {
    operationToExecutor.put(CommandOperation.CREATE, new CreateCommand());
    operationToExecutor.put(CommandOperation.REMOVE, new RemoveCommand());
    operationToExecutor.put(CommandOperation.LIST, new ListCommand());
    operationToExecutor.put(CommandOperation.ABOUT, new AboutCommand());
    operationToExecutor.put(CommandOperation.SET, new SetCommand());
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String str, String[] args) {

    if (!(sender instanceof Player))
      return false;

    if (args.length == 0)
      return false;

    Player player = (Player) sender;

    CommandOperation operation = CommandOperation.fromLiteral(args[0]);
    String[] subArgs = Arrays.copyOfRange(args, 1, args.length);

    AreaCommandExecutor executor = operationToExecutor.get(operation);

    if (executor == null)
      return false;

    return executor.execute(player, subArgs);
  }

  @Override
  public List<String> onTabComplete(CommandSender arg0, Command arg1, String arg2, String[] args) {
    if (args.length == 0)
      return null;

    if (args.length == 1) {
      ArrayList<String> list = new ArrayList<>();
      operationToExecutor.keySet().forEach(operation -> {
        list.add(operation.getLiteral());
      });
      return list;
    } else {
      CommandOperation operation = CommandOperation.fromLiteral(args[0]);
      String[] subArgs = Arrays.copyOfRange(args, 1, args.length);

      if (operation == null)
        return null;

      AreaCommandExecutor executor = operationToExecutor.get(operation);

      if (executor == null)
        return null;

      return executor.completion(subArgs);
    }

  }

}

package wms.plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;


public class AreaCheckController {
	
	public static void areaCheckController(){
		JavaPlugin plugin = WelcomMessageSendPlugin.plugin;
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(!WelcomMessageSendPlugin.state){cancel();}
				for(World w:WelcomMessageSendPlugin.worldSet){
					List<Player> players = w.getPlayers();
					int size = players.size() / 20 + (players.size() % 20 > 0 ? 1 : 0);
					PlayerCheckRunnable.playerCheckerRunnable(listDivider(players, size));
				}
			}
		}.runTaskTimer(plugin, 0, 20);
	}
	
	public static List<List<Player>> listDivider(List<Player> list, int i){
		List<List<Player>> players = new ArrayList<>();
		
		if(list.isEmpty() || i == 0){
			players.add(list);
			return players;
		}
		int size = list.size()/i + (list.size()%i > 0 ? 1 : 0);
		
		for( int j = 0; j < size; j ++ ){
			int fromIndex = j * i;
			int toIndex = Math.min(fromIndex + i, list.size());
			List<Player> subList = list.subList(fromIndex, toIndex);
			
			players.add(subList);
		}
		return players;
	}
}

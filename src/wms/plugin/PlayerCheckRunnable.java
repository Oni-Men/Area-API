package wms.plugin;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerCheckRunnable {

	public static void playerCheckerRunnable(List<List<Player>> playersList) {
		new BukkitRunnable() {
			int count = 0;
			@Override
			public void run() {
				if(count >= playersList.get(0).size()){
					cancel();
					return;
				}
				for(int x = 0; x < playersList.size(); x++){
					int i = x * playersList.get(x).size() + count;
						TitleSender.checkPlayerLocation(playersList.get(x).get(i));
				}
				count++;
			}
		}.runTaskTimer(WelcomMessageSendPlugin.plugin, 0, 1);

	}
}

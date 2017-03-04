package wms.town;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.Chunk;
import org.bukkit.entity.Player;

public interface AreaInterface {
	
	String getAreaName();
	
	String getAreaSubTitle();
	
	HashSet<Chunk> getChunks();
	
	List<UUID> getPlayersInArea();
	
	void addPlayer(Player p);
	
	void removePlayer(Player p);
	
	boolean containsPlayer(Player p);
	
}

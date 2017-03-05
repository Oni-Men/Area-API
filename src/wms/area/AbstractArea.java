package wms.area;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public abstract class AbstractArea implements AreaInterface{
	
	String areaName;
	String areaSubTitle;
	List<UUID> players = new ArrayList<>();
	List<Location> locations = new ArrayList<>();
	HashSet<Chunk> chunks = new HashSet<>();
	
	@Override
	public String getAreaName() {
		return areaName;
	}

	@Override
	public String getAreaSubTitle() {
		return areaSubTitle;
	}

	@Override
	public List<UUID> getPlayersInArea() {
		return players;
	}

	@Override
	public void addPlayer(Player p) {
		players.add(p.getUniqueId());
	}

	@Override
	public void removePlayer(Player p) {
		players.remove(p.getUniqueId());
	}

	@Override
	public boolean containsPlayer(Player p) {
		return players.contains(p.getUniqueId());
	}
	
	@Override
	public HashSet<Chunk> getChunks(){
		return chunks;
	}
	@Override 
	public List<Location> getLocations(){
		return locations;
	}

}

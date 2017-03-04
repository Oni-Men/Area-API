package wms.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import wms.PlayerEventListener;
import wms.area.NormalArea;
import wms.command.CommandRegister;

public class WelcomMessageSendPlugin extends JavaPlugin{
	
	public static JavaPlugin plugin;
	public static HashMap<Chunk, NormalArea> areaMap = new HashMap<>();
	public static HashMap<Player, NormalArea> playerMap = new HashMap<>();
	public static HashSet<World> worldSet = new HashSet<>();
	public static List<NormalArea> areaList = new ArrayList<>();
	public static boolean state = false;
	
	@Override
	public void onEnable(){
		plugin = this;
		getServer().getPluginManager().registerEvents(new PlayerEventListener(), plugin);
		CommandRegister.registCommand();
		oni();
		state = true;
		AreaCheckController.areaCheckController();
	}
	@Override
	public void onDisable(){
		
	}
	public void oni(){
		World w = this.getServer().getWorld("onimen");
		worldSet.add(w);
		Location loc1 = new Location(w,310,0,1050);
		Location loc2 = new Location(w,322,0,1033);
		
		NormalArea onimen = new NormalArea("Area B", "Welcom to Area B, ", loc1 , loc2);
		areaList.add(onimen);
		for(Chunk c : onimen.getChunks()){
			areaMap.put(c, onimen);
		}
		
	}
}

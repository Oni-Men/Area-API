package wms.plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import wms.AreaLoader;
import wms.PlayerEventListener;
import wms.area.NormalArea;
import wms.command.CommandRegister;

public class WelcomMessageSendPlugin extends JavaPlugin{
	
	public static JavaPlugin plugin;
	public static FileConfiguration config;
	public static HashMap<Chunk, NormalArea> areaMap = new HashMap<>();
	public static HashMap<Player, NormalArea> playerMap = new HashMap<>();
	public static HashSet<World> worldSet = new HashSet<>();
	public static List<NormalArea> areaList = new ArrayList<>();
	public static boolean state = false;
	
	@Override
	public void onEnable(){
		plugin = this;
		saveDefaultConfig();
		config = getConfig();
		
		getServer().getPluginManager().registerEvents(new PlayerEventListener(), plugin);
		CommandRegister.registCommand();
		AreaLoader.areaLoader();
		
		state = true;
		AreaCheckController.areaCheckController();
	}
	@Override
	public void onDisable(){
		saveConfig();
	}
	public static String bindString(String areaName, String[] args){
		for(int i=0;i<args.length;i++){
			areaName += args[i] + " ";
		}
		return areaName.trim();
		
	}
	public static List<List<?>> listDivider(List<?> list, int i){
		List<List<?>> obj = new ArrayList<>();
		
		if(list.isEmpty() || i == 0){
			obj.add(list);
			return obj;
		}
		int size = list.size()/i + (list.size()%i > 0 ? 1 : 0);
		
		for( int j = 0; j < size; j ++ ){
			int fromIndex = j * i;
			int toIndex = Math.min(fromIndex + i, list.size());
			List<?> subList = list.subList(fromIndex, toIndex);
			
			obj.add(subList);
		}
		return obj;
	}
	
}

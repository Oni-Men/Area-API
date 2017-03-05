package wms;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import wms.area.NormalArea;
import wms.plugin.WelcomMessageSendPlugin;

public class AreaLoader {
	
	@SuppressWarnings("unchecked")
	public static void areaLoader(){
		FileConfiguration config = WelcomMessageSendPlugin.config;
		Logger logger = WelcomMessageSendPlugin.plugin.getLogger();
		
		for(String s:config.getKeys(false)){
			
			List<?> list = config.getList(s);
			
			String areaName =(String) list.get(0);
			String areaSubTitle =(String) list.get(1);
			List<Location> loc =(List<Location>) list.get(2);
			
			NormalArea area = new NormalArea(areaName, areaSubTitle,loc.get(0), loc.get(1));
			
			WelcomMessageSendPlugin.areaList.add(area);
			WelcomMessageSendPlugin.worldSet.add(loc.get(0).getWorld());
			
			for(Chunk c:area.getChunks()){
				WelcomMessageSendPlugin.areaMap.put(c, area);
			}
		}
		logger.info("エリア数： "+WelcomMessageSendPlugin.areaList.size());
		logger.info("ワールド数： "+WelcomMessageSendPlugin.worldSet.size());
		logger.info("チャンク数： "+WelcomMessageSendPlugin.areaMap.size());
		logger.info("以上を読み込み、WMSを開始しました。");
	}
}

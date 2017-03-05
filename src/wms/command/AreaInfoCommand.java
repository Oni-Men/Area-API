package wms.command;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import wms.area.NormalArea;
import wms.area.manager.AreaInformationManager;
import wms.plugin.WelcomMessageSendPlugin;

public class AreaInfoCommand   extends AbstractWmsCommand implements WmsCommandInterface{


	public AreaInfoCommand(String cmdName) {
		super(cmdName);
	}

	@Override
	public void onCmd(CommandSender sender, Command cmd, String s, String[] args) {
		
		if(args.length == 0 || args[0].matches("[0-9]*")){
			List<List<?>> divided = WelcomMessageSendPlugin.listDivider(AreaInformationManager.getAreaList(), 5);
			int i = 0;
			if(args.length == 0){
				i=1;
			}else{
				i = Integer.parseInt(args[0]);
			}
			if(i>divided.size()){
				sender.sendMessage(ChatColor.RED +""+ i +"は大きすぎます。"+divided.size()+"以下にして下さい。");
				return;
			}
			sender.sendMessage(ChatColor.GREEN+"==== Areas ("+i+"/"+divided.size()+") ====");
			for( Object list : divided.get(i-1) ){
				sender.sendMessage(ChatColor.GREEN+((NormalArea)list).getAreaName());
			}
			return;
		}
			String areaName = "";
			String bindString = WelcomMessageSendPlugin.bindString(areaName, args);
			NormalArea normalArea = AreaInformationManager.getNormalArea(bindString);
	
		if(normalArea != null){
		
			sender.sendMessage(ChatColor.GREEN+"==== "+normalArea.getAreaName()+" ====");
			sender.sendMessage(ChatColor.GREEN+"サブタイトル: "+normalArea.getAreaSubTitle());
			sender.sendMessage(ChatColor.GREEN+"チャンク数: "+normalArea.getChunks().size());
			sender.sendMessage(ChatColor.GREEN+"プレイヤー数: "+normalArea.getPlayersInArea().size());
			return;
		}
	}

}

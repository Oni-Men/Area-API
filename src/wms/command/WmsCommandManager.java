package wms.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import wms.plugin.WelcomMessageSendPlugin;

public class WmsCommandManager implements CommandExecutor, TabCompleter{
	
	public static HashMap<String,WmsCommandInterface> wmsCommands = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
		
		if(args.length == 0)return false;
		
		if(wmsCommands.containsKey(args[0])){
			 WmsCommandInterface command = wmsCommands.get(args[0]);
			String[] args2 = new String[args.length-1];
			for(int i=1;i<args.length;i++){
				args2[i-1] = args[i];
			}
			 command.onCmd(sender, cmd, s, args2);
		}
		return false;
	}
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
		
		if(args.length == 1){
			if(args[0].length() == 0){
				List<String> list  = new ArrayList<>(WmsCommandManager.wmsCommands.keySet());
				return list;
			}else{
				for(String st : WmsCommandManager.wmsCommands.keySet()){
					if(st.startsWith(args[0])){
						return Collections.singletonList(st);
					}
				}
			}
		}
		return WelcomMessageSendPlugin.plugin.onTabComplete(sender,cmd,s,args);
	}

}

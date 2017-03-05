package wms.plugin;

import java.util.UUID;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R1.PlayerConnection;
import wms.area.NormalArea;

public class TitleSender {
	
	public static void checkPlayerLocation(Player p){
		
		UUID uuid = p.getUniqueId();
		
		if(WelcomMessageSendPlugin.areaMap.containsKey(p.getLocation().getChunk())){
			NormalArea area = WelcomMessageSendPlugin.areaMap.get(p.getLocation().getChunk());
			if(!area.getPlayersInArea().contains(uuid)){
				sendTitle(area.getAreaName(), area.getAreaSubTitle(), p);
				area.getPlayersInArea().add(uuid);
				WelcomMessageSendPlugin.playerMap.put(p, area);
			}
		}else{
			if(WelcomMessageSendPlugin.playerMap.containsKey(p)){
				WelcomMessageSendPlugin.playerMap.get(p).getPlayersInArea().remove(uuid);
			}
		}
		
	}
	
	public static void sendTitle(String name, String subTitle,Player p){
		
		IChatBaseComponent title = ChatSerializer.a("{\"text\":\"" +name+ "\",\"color\":\"green\"}");
		IChatBaseComponent sub = ChatSerializer.a("{\"text\":\"" +subTitle +", "+ p.getName()+ "\",\"color\":\"green\"}");
		
		float random = (float) Math.random();
		if(random <= 0.5){random += 0.4;}
		
		PacketPlayOutNamedSoundEffect se = new PacketPlayOutNamedSoundEffect("ambient.cave.cave",p.getLocation().getX(),p.getLocation().getY(),p.getLocation().getZ(), 1f,random);
		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(EnumTitleAction.TITLE, title);
		PacketPlayOutTitle subTitlePacket = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, sub);
		PacketPlayOutTitle time = new PacketPlayOutTitle(EnumTitleAction.TIMES, title, 10,40,60);
		PacketPlayOutTitle subTime = new PacketPlayOutTitle(EnumTitleAction.TIMES, sub, 10,40,60);
			
		PlayerConnection c = ((CraftPlayer)p).getHandle().playerConnection;
		c.sendPacket(titlePacket);
		c.sendPacket(subTitlePacket);
		c.sendPacket(time);
		c.sendPacket(subTime);
		c.sendPacket(se);
	}
}

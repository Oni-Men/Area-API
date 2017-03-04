package wms.area;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class NormalArea extends AbstractArea{
	
	List<Location> locs = new ArrayList<>();
	
	public NormalArea(String areaName,String areaSubTitle,Location loc1,Location loc2){
		this.areaName = areaName;
		this.areaSubTitle = areaSubTitle;
		
		int minX = Math.min(loc1.getBlockX(),loc2.getBlockX());
		int maxX = Math.max(loc1.getBlockX(),loc2.getBlockX());
		int minZ = Math.min(loc1.getBlockZ(),loc2.getBlockZ());
		int maxZ = Math.max(loc1.getBlockZ(),loc2.getBlockZ());
		
		for(int x=minX;x<maxX;x++){
			for(int z=minZ;z<maxZ;z++){
				this.chunks.add(new Location(loc1.getWorld(),x,0,z).getChunk());
			}
		}
	}
	
	public boolean equals(Object o) {
		if(o == this){return true;}
		if(o == null){return false;}
		if(!(o instanceof NormalArea)){return false;}
		NormalArea area = (NormalArea) o;
		if(!this.areaName.equals(area.getAreaName())){
			return false;
		}
		return true;
	}
	
	public int hashCode(){
		int r = 37;
		r = r * 31 + areaName.hashCode();
		return r;
	}

}

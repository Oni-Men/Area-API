package wms.town;

import java.util.List;

import org.bukkit.Location;

public class ComplexArea extends AbstractArea{
	
	public ComplexArea(String areaName,String areaSubTitle,List<Location> loc){
		this.areaName = areaName;
		this.areaSubTitle = areaSubTitle;
		
		//頂点List<Location> (loc)から面を算出してChunkを取得する
		//this.chunks.add(chuks);
	}
	
}

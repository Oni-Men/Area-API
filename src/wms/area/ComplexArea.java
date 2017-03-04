package wms.area;

import java.util.List;

import org.bukkit.Location;

public class ComplexArea extends AbstractArea{
	
	public ComplexArea(String areaName,String areaSubTitle,List<Location> loc){
		this.areaName = areaName;
		this.areaSubTitle = areaSubTitle;
		
		//���_List<Location> (loc)����ʂ��Z�o����Chunk���擾����
		//this.chunks.add(chuks);
	}
	
}

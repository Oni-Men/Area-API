package wms.area.manager;

import java.util.List;

import wms.area.NormalArea;
import wms.plugin.WelcomMessageSendPlugin;

public class AreaInformationManager {

	public static List<NormalArea> getAreaList(){
		return WelcomMessageSendPlugin.areaList;
	}
	
	public static NormalArea  getNormalArea(String s){
		for(NormalArea na : WelcomMessageSendPlugin.areaList){
			if(na.getAreaName().equals(s)){
				return na;
			}
		}
		return null;
	}

}

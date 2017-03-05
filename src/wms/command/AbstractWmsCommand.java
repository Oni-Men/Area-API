package wms.command;


public abstract class AbstractWmsCommand implements WmsCommandInterface{
	
	String cmdName;
	
	public AbstractWmsCommand(String cmdName){
		this.cmdName = cmdName;
	}
	
	public String cmdName() {
		return cmdName;
	}
}

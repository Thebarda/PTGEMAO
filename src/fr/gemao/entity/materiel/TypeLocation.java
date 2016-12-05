package fr.gemao.entity.materiel;

import fr.gemao.ctrl.location.LocationCtrl;

public class TypeLocation {
	private final Location location;
	private final String type;
	
	public TypeLocation(Location location){
		this.location = location;
		this.type = LocationCtrl.getTypeLocation(location);
	}
	
	public String getTypeLocation(){
		return this.type;
	}
	
	public Location getLocation(){
		return this.location;
	}
}

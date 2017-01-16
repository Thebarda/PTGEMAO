package fr.gemao.entity.materiel;

import java.io.Serializable;

import fr.gemao.ctrl.location.LocationCtrl;

public class ChequeLocation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final Location location;
	private final String typeLocation;
	private final String datePaiement;
	private final float montantCheque;
	private final int numCheque;
	private final String dateEncaissement;
	
	
	public ChequeLocation(Location location, String datePaiement, float montantCheque, int numCheque, String dateEncaissement) {
		this.location = location;
		this.typeLocation = LocationCtrl.getTypeLocation(location);
		this.datePaiement = datePaiement;
		this.montantCheque = montantCheque;
		this.numCheque = numCheque;
		this.dateEncaissement = dateEncaissement;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public String getTypeLocation() {
		return typeLocation;
	}
	
	public String getDatePaiement() {
		return datePaiement;
	}
	
	public float getMontantCheque() {
		return montantCheque;
	}
	
	public int getNumCheque() {
		return numCheque;
	}
	
	public String getDateEncaissement() {
		return dateEncaissement;
	}
	
	

}

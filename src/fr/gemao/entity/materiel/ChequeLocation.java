package fr.gemao.entity.materiel;

import java.io.Serializable;

import fr.gemao.ctrl.location.LocationCtrl;

public class ChequeLocation implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final Location location;
	private final String typeLocation;
	private final String datePaiement;
	private final float montantCheque;
	private final long numCheque;
	private final String dateEncaissement;
	private final String dateEncaissementEffective;
	
	
	public ChequeLocation(Location location, String datePaiement, float montantCheque, long numCheque, String dateEncaissement, String dateEncaissementEffective) {
		this.location = location;
		this.typeLocation = LocationCtrl.getTypeLocation(location);
		this.datePaiement = datePaiement;
		this.montantCheque = montantCheque;
		this.numCheque = numCheque;
		this.dateEncaissement = dateEncaissement;
		this.dateEncaissementEffective = dateEncaissementEffective;
	}
	
	public String getDateEncaissementEffective() {
		return dateEncaissementEffective;
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
	
	public long getNumCheque() {
		return numCheque;
	}
	
	public String getDateEncaissement() {
		return dateEncaissement;
	}
	
	

}

package fr.gemao.entity.materiel;

import fr.gemao.ctrl.location.LocationCtrl;

public class Cheque {
private static final long serialVersionUID = 1L;
	private final String datePaiement;
	private final float montantCheque;
	private final long numCheque;
	private final String dateEncaissement;
	
	
	public Cheque(String datePaiement, float montantCheque, long numCheque, String dateEncaissement) {
		this.datePaiement = datePaiement;
		this.montantCheque = montantCheque;
		this.numCheque = numCheque;
		this.dateEncaissement = dateEncaissement;
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

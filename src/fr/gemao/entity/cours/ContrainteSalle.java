package fr.gemao.entity.cours;

import java.io.Serializable;
import java.util.Date;

import fr.gemao.entity.Jour;

public class ContrainteSalle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Salle salle;
	private Jour jour;
	private Date heureDebut, heureFin;
	
	public ContrainteSalle(Salle salle, Jour jour, Date heureDebut,
			Date heureFin) {
		super();
		this.salle = salle;
		this.jour = jour;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
	}

	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	public Jour getJour() {
		return jour;
	}

	public void setJour(Jour jour) {
		this.jour = jour;
	}

	public Date getHeureDebut() {
		return heureDebut;
	}

	public void setHeureDebut(Date heureDebut) {
		this.heureDebut = heureDebut;
	}

	public Date getHeureFin() {
		return heureFin;
	}

	public void setHeureFin(Date heureFin) {
		this.heureFin = heureFin;
	}
	
	
	
}

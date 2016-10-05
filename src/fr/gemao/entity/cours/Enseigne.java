package fr.gemao.entity.cours;

import java.io.Serializable;
import java.util.Date;

import fr.gemao.entity.Jour;

public class Enseigne implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Prof prof;
	private Discipline discipline;
	private Jour jour;
	private Date heureDebut, heureFin;
	
	public Enseigne(Prof prof, Discipline discipline, Jour jour,
			Date heureDebut, Date heureFin) {
		super();
		this.prof = prof;
		this.discipline = discipline;
		this.jour = jour;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
	}

	public Prof getProf() {
		return prof;
	}

	public void setProf(Prof prof) {
		this.prof = prof;
	}

	public Discipline getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
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

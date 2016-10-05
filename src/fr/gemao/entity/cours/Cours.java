package fr.gemao.entity.cours;

import java.io.Serializable;

/**
 * Classe représentant un cours dans le sens de l'emploi du temps : une
 * discipline dans une salle avec un professeur
 * 
 * @author Benoît
 *
 */
public class Cours implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idCours;
	private Discipline discipline;
	private Salle salle;
	private Prof prof;

	public Cours(Integer idCours, Discipline discipline, Salle salle, Prof prof) {
		super();
		this.idCours = idCours;
		this.discipline = discipline;
		this.salle = salle;
		this.prof = prof;
	}

	public Integer getIdCours() {
		return idCours;
	}

	public void setIdCours(Integer idCours) {
		this.idCours = idCours;
	}

	public Discipline getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	public Salle getSalle() {
		return salle;
	}

	public void setSalle(Salle salle) {
		this.salle = salle;
	}

	public Prof getProf() {
		return prof;
	}

	public void setProf(Prof prof) {
		this.prof = prof;
	}

	public String getIntituleCours() {
		if (this.getDiscipline() != null && this.getProf() != null)
			return this.getDiscipline().getNomDiscipline() + " - " + this.getProf().getIdentiteProf();
		return null;
	}

}

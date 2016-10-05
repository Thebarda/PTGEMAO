package fr.gemao.entity.cours;

import java.io.Serializable;
import java.util.List;

/**
 * Classe représentant un groupe d'adhérents qui suivent la même discipline
 * @author Benoît
 *
 */
public class Classe implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idClasse;
	private Discipline discipline;
	private String nomClasse;
	private List<Cours> listeCours;
	
	public Classe(Integer idClasse, Discipline discipline, String nomClasse, List<Cours> listeCours) {
		super();
		this.idClasse = idClasse;
		this.discipline = discipline;
		this.nomClasse = nomClasse;
		this.listeCours = listeCours;
	}

	public Integer getIdClasse() {
		return idClasse;
	}

	public void setIdClasse(Integer idClasse) {
		this.idClasse = idClasse;
	}

	public Discipline getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	public String getNomClasse() {
		return nomClasse;
	}

	public void setNomClasse(String nomClasse) {
		this.nomClasse = nomClasse;
	}

	public List<Cours> getListeCours() {
		return listeCours;
	}

	public void setListeCours(List<Cours> listeCours) {
		this.listeCours = listeCours;
	}
	
	public boolean ajouterCours(Cours cours){
		return listeCours.add(cours);
	}
	
	public boolean removeCours(Cours cours){
		return listeCours.remove(cours);
	}
}

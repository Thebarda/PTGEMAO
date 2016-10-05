package fr.gemao.entity.cours;

import java.io.Serializable;

/**
 * Classe représentant un type de cours (guitare, percussions, etc...)
 * @author Benoît
 *
 */
public class Matiere implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idMatiere;
	private String nomMatiere;
	
	public Matiere(Integer idMatiere, String nomMatiere) {
		super();
		this.idMatiere = idMatiere;
		this.nomMatiere = nomMatiere;
	}

	public Integer getIdMatiere() {
		return idMatiere;
	}

	public void setIdMatiere(Integer idMatiere) {
		this.idMatiere = idMatiere;
	}

	public String getNomMatiere() {
		return nomMatiere;
	}

	public void setNomMatiere(String nomMatiere) {
		this.nomMatiere = nomMatiere;
	}
	
	
	
}

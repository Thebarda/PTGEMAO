package fr.gemao.entity.cours;

import java.io.Serializable;

public class Niveau implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idNiveau;
	private String nomNiveau;
	
	public Niveau(Integer idNiveau, String nomNiveau) {
		super();
		this.idNiveau = idNiveau;
		this.nomNiveau = nomNiveau;
	}

	public Integer getIdNiveau() {
		return idNiveau;
	}

	public void setIdNiveau(Integer idNiveau) {
		this.idNiveau = idNiveau;
	}

	public String getNomNiveau() {
		return nomNiveau;
	}

	public void setNomNiveau(String nomNiveau) {
		this.nomNiveau = nomNiveau;
	}
	
	
	
}

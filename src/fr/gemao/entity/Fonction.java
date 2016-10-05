package fr.gemao.entity;

import java.io.Serializable;

public class Fonction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idFonction;
	private String nom;
	
	public Fonction(Integer idFonction, String nom){
		super();
		this.idFonction = idFonction;
		this.nom = nom;
	}
	
	public Integer getIdFonction(){
		return idFonction;
	}
	
	public void setIdFonction(Integer idFonction){
		this.idFonction = idFonction;
	}
	
	public String getNom(){
		return nom;
	}
	
	public void setNom(String nom){
		this.nom = nom;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idFonction == null) ? 0 : idFonction.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fonction other = (Fonction) obj;
		if (idFonction == null) {
			if (other.idFonction != null)
				return false;
		} else if (!idFonction.equals(other.idFonction))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Fonction [idFonction=" + idFonction + ", nom=" + nom
				+ "]";
	}
}


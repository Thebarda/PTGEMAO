package fr.gemao.entity.materiel;

import java.io.Serializable;

/**
 * The Class Reparateur.
 */
public class Reparateur implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The id reparateur. */
	private int idReparateur;
	
	/** The nom. */
	private String nom;
	
	public Reparateur() {
	}
	
	/**
	 * Instantiates a new reparateur.
	 *
	 * @param idReparateur the id reparateur
	 * @param nom the nom
	 */
	public Reparateur(int idReparateur, String nom) {
		this.idReparateur = idReparateur;
		this.nom = nom;
	}
	
	/**
	 * Gets the id reparateur.
	 *
	 * @return the id reparateur
	 */
	public int getIdReparateur() {
		return idReparateur;
	}
	
	/**
	 * Sets the id reparateur.
	 *
	 * @param idReparateur the new id reparateur
	 */
	public void setIdReparateur(int idReparateur) {
		this.idReparateur = idReparateur;
	}
	
	/**
	 * Gets the nom.
	 *
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}
	
	/**
	 * Sets the nom.
	 *
	 * @param nom the new nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idReparateur;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reparateur other = (Reparateur) obj;
		if (idReparateur != other.idReparateur)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}

}

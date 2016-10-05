package fr.gemao.entity.materiel;

import java.io.Serializable;

public class Designation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idDesignation;
	private String libelleDesignation;
	
	public Designation() {
	}

	/**
	 * Instantiates a new Designation.
	 *
	 * @param idDesignation
	 *            l'identificateur de la designation.
	 * @param libelleEtat
	 *            le libelle de la designation.
	 */
	public Designation(int idDesignation, String libelleDesignation) {
		this.idDesignation = idDesignation;
		this.libelleDesignation = libelleDesignation;
	}

	/**
	 * Retourne le libelle de la designation.
	 * 
	 * @return le libelle de la designation
	 */
	public String getLibelleDesignation() {
		return libelleDesignation;
	}

	/**
	 * Permet de changer le libelle de la designation.
	 * 
	 * @param libelleDesignation
	 *            le nouveau libelle.
	 */
	public void setLibelleDesignation(String libelleDesignation) {
		this.libelleDesignation = libelleDesignation;
	}

	/**
	 * Permet de recuperer l'identificateur de la designation
	 * 
	 * @return l'identificateur de la designation.
	 */
	public int getIdDesignation() {
		return idDesignation;
	}
	
	

	public void setIdDesignation(int idDesignation) {
		this.idDesignation = idDesignation;
	}

	@Override
	public String toString() {
		return "Designation [idDesignation=" + idDesignation
				+ ", libelleDesignation=" + libelleDesignation + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idDesignation;
		result = prime
				* result
				+ ((libelleDesignation == null) ? 0 : libelleDesignation
						.hashCode());
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
		Designation other = (Designation) obj;
		if (idDesignation != other.idDesignation)
			return false;
		if (libelleDesignation == null) {
			if (other.libelleDesignation != null)
				return false;
		} else if (!libelleDesignation.equals(other.libelleDesignation))
			return false;
		return true;
	}

}

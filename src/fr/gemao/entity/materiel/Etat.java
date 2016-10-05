package fr.gemao.entity.materiel;

import java.io.Serializable;

public class Etat implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idEtat;
	private String libelleEtat;
	
	public Etat() {
	}
	
	/**
	 * Instantiates a new etat.
	 *
	 * @param idEtat the id etat
	 * @param libelleEtat the libelle etat
	 */
	public Etat(int idEtat, String libelleEtat) {
		this.idEtat = idEtat;
		this.libelleEtat = libelleEtat;
	}
	
	/**
	 * Gets the libelle etat.
	 *
	 * @return the libelle etat
	 */
	public String getLibelleEtat() {
		return libelleEtat;
	}
	
	/**
	 * Sets the libelle etat.
	 *
	 * @param libelleEtat the new libelle etat
	 */
	public void setLibelleEtat(String libelleEtat) {
		this.libelleEtat = libelleEtat;
	}
	
	/**
	 * Gets the id etat.
	 *
	 * @return the id etat
	 */
	public int getIdEtat() {
		return idEtat;
	}
	
	
	
	public void setIdEtat(int idEtat) {
		this.idEtat = idEtat;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idEtat ^ (idEtat >>> 32));
		result = prime * result
				+ ((libelleEtat == null) ? 0 : libelleEtat.hashCode());
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
		Etat other = (Etat) obj;
		if (idEtat != other.idEtat)
			return false;
		if (libelleEtat == null) {
			if (other.libelleEtat != null)
				return false;
		} else if (!libelleEtat.equals(other.libelleEtat))
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Etat [idEtat=" + idEtat + ", libelleEtat=" + libelleEtat + "]";
	}
	
	
}

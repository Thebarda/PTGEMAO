package fr.gemao.entity.materiel;

import java.io.Serializable;

public class Marque implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idMarque;
	private String nomMarque;
	
	public Marque() {
	}
	
	/**
	 * Instantiates a new marque.
	 *
	 * @param idMarque the id marque
	 * @param nomMarque the nom marque
	 */
	public Marque(int idMarque, String nomMarque) {
		this.idMarque = idMarque;
		this.nomMarque = nomMarque;
	}

	public void setIdMarque(int idMarque) {
		this.idMarque = idMarque;
	}

	/**
	 * Gets the nom marque.
	 *
	 * @return the nom marque
	 */
	public String getNomMarque() {
		return nomMarque;
	}

	/**
	 * Sets the nom marque.
	 *
	 * @param nomMarque the new nom marque
	 */
	public void setNomMarque(String nomMarque) {
		this.nomMarque = nomMarque;
	}

	/**
	 * Gets the id marque.
	 *
	 * @return the id marque
	 */
	public int getIdMarque() {
		return idMarque;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idMarque ^ (idMarque >>> 32));
		result = prime * result
				+ ((nomMarque == null) ? 0 : nomMarque.hashCode());
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
		Marque other = (Marque) obj;
		if (idMarque != other.idMarque)
			return false;
		if (nomMarque == null) {
			if (other.nomMarque != null)
				return false;
		} else if (!nomMarque.equals(other.nomMarque))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Marque [idMarque=" + idMarque + ", nomMarque=" + nomMarque
				+ "]";
	}
	
	
}

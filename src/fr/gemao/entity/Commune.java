package fr.gemao.entity;

import java.io.Serializable;

public class Commune implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idCommune, codePostal;
	private String nomCommune;
	private boolean avantage;
	
	public Commune() {
	}

	public Commune(Integer idCommune, Integer codePostal, String nomCommune,
			boolean avantage) {
		this.idCommune = idCommune;
		this.nomCommune = nomCommune;
		this.codePostal = codePostal;
		this.avantage = avantage;
	}

	/**
	 * @return the idCommune
	 */
	public Integer getIdCommune() {
		return idCommune;
	}

	/**
	 * @param idCommune the idCommune to set
	 */
	public void setIdCommune(Integer idCommune) {
		this.idCommune = idCommune;
	}

	/**
	 * @return the codePostal
	 */
	public Integer getCodePostal() {
		return codePostal;
	}

	/**
	 * @param codePostal the codePostal to set
	 */
	public void setCodePostal(Integer codePostal) {
		this.codePostal = codePostal;
	}

	/**
	 * @return the nomCommune
	 */
	public String getNomCommune() {
		return nomCommune;
	}

	/**
	 * @param nomCommune the nomCommune to set
	 */
	public void setNomCommune(String nomCommune) {
		this.nomCommune = nomCommune;
	}

	/**
	 * @return the avantage
	 */
	public boolean isAvantage() {
		return avantage;
	}

	/**
	 * @param avantage the avantage to set
	 */
	public void setAvantage(boolean avantage) {
		this.avantage = avantage;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (avantage ? 1231 : 1237);
		result = prime * result
				+ ((codePostal == null) ? 0 : codePostal.hashCode());
		result = prime * result
				+ ((idCommune == null) ? 0 : idCommune.hashCode());
		result = prime * result
				+ ((nomCommune == null) ? 0 : nomCommune.hashCode());
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
		Commune other = (Commune) obj;
		if (avantage != other.avantage)
			return false;
		if (codePostal == null) {
			if (other.codePostal != null)
				return false;
		} else if (!codePostal.equals(other.codePostal))
			return false;
		if (idCommune == null) {
			if (other.idCommune != null)
				return false;
		} else if (!idCommune.equals(other.idCommune))
			return false;
		if (nomCommune == null) {
			if (other.nomCommune != null)
				return false;
		} else if (!nomCommune.equals(other.nomCommune))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return codePostal +  " " + nomCommune;
	}
	
	
}

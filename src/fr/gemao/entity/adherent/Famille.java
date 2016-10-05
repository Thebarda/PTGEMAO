package fr.gemao.entity.adherent;

public class Famille {

	private Integer idFamille;
	private String nomFamille;

	public Famille(Integer idFamille, String nomFamille) {
		this.idFamille = idFamille;
		this.nomFamille = nomFamille;
	}

	/**
	 * @return the idFamille
	 */
	public Integer getIdFamille() {
		return idFamille;
	}

	/**
	 * @param idFamille
	 *            the idFamille to set
	 */
	public void setIdFamille(Integer idFamille) {
		this.idFamille = idFamille;
	}

	/**
	 * @return the nomFamille
	 */
	public String getNomFamille() {
		return nomFamille;
	}

	/**
	 * @param nomFamille
	 *            the nomFamille to set
	 */
	public void setNomFamille(String nomFamille) {
		this.nomFamille = nomFamille;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Famille [idFamille=" + idFamille + ", nomFamille=" + nomFamille
				+ "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idFamille == null) ? 0 : idFamille.hashCode());
		result = prime * result
				+ ((nomFamille == null) ? 0 : nomFamille.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		Famille other = (Famille) obj;
		if (idFamille == null) {
			if (other.idFamille != null)
				return false;
		} else if (!idFamille.equals(other.idFamille))
			return false;
		if (nomFamille == null) {
			if (other.nomFamille != null)
				return false;
		} else if (!nomFamille.equals(other.nomFamille))
			return false;
		return true;
	}


}

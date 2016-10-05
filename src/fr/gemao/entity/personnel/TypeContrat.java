package fr.gemao.entity.personnel;

import java.io.Serializable;

public class TypeContrat implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idContrat;
	private String libelle;
	/**
	 * @param idContrat
	 * @param libelle
	 */
	public TypeContrat(Integer idContrat, String libelle) {
		super();
		this.idContrat = idContrat;
		this.libelle = libelle;
	}
	/**
	 * @return the idContrat
	 */
	public Integer getIdContrat() {
		return idContrat;
	}
	/**
	 * @param idContrat the idContrat to set
	 */
	public void setIdContrat(Integer idContrat) {
		this.idContrat = idContrat;
	}
	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}
	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idContrat == null) ? 0 : idContrat.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
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
		TypeContrat other = (TypeContrat) obj;
		if (idContrat == null) {
			if (other.idContrat != null)
				return false;
		} else if (!idContrat.equals(other.idContrat))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		return true;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TypeContrat [idContrat=" + idContrat + ", libelle=" + libelle
				+ "]";
	}
	
	
}

package fr.gemao.entity.adherent;

import java.io.Serializable;

public class MotifSortie implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idMotif;
	private String libelle;
	
	/**
	 * Constructeur par défaut
	 */
	public MotifSortie(){
		this.idMotif = null;
	}
	
	/**
	 * @param idMotif
	 * @param libelle
	 */
	public MotifSortie(Integer idMotif, String libelle) {
		super();
		this.idMotif = idMotif;
		this.libelle = libelle;
	}
	/**
	 * @return the idMotif
	 */
	public Integer getIdMotif() {
		return idMotif;
	}
	/**
	 * @param idMotif the idMotif to set
	 */
	public void setIdMotif(Integer idMotif) {
		this.idMotif = idMotif;
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
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MotifSortie [idMotif=" + idMotif + ", libelle=" + libelle + "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idMotif == null) ? 0 : idMotif.hashCode());
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
		MotifSortie other = (MotifSortie) obj;
		if (idMotif == null) {
			if (other.idMotif != null)
				return false;
		} else if (!idMotif.equals(other.idMotif))
			return false;
		if (libelle == null) {
			if (other.libelle != null)
				return false;
		} else if (!libelle.equals(other.libelle))
			return false;
		return true;
	}
	
	
}

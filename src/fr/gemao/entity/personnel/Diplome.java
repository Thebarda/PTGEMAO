package fr.gemao.entity.personnel;

import java.io.Serializable;

public class Diplome implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idDiplome;
	private String nomDiplome;
	/**
	 * @param idDiplome
	 * @param nomDiplome
	 */
	public Diplome(Integer idDiplome, String nomDiplome) {
		super();
		this.idDiplome = idDiplome;
		this.nomDiplome = nomDiplome;
	}
	/**
	 * @return the idDiplome
	 */
	public Integer getIdDiplome() {
		return idDiplome;
	}
	/**
	 * @param idDiplome the idDiplome to set
	 */
	public void setIdDiplome(Integer idDiplome) {
		this.idDiplome = idDiplome;
	}
	/**
	 * @return the nomDiplome
	 */
	public String getNomDiplome() {
		return nomDiplome;
	}
	/**
	 * @param nomDiplome the nomDiplome to set
	 */
	public void setNomDiplome(String nomDiplome) {
		this.nomDiplome = nomDiplome;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Diplome [idDiplome=" + idDiplome + ", nomDiplome=" + nomDiplome
				+ "]";
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idDiplome == null) ? 0 : idDiplome.hashCode());
		result = prime * result
				+ ((nomDiplome == null) ? 0 : nomDiplome.hashCode());
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
		Diplome other = (Diplome) obj;
		if (idDiplome == null) {
			if (other.idDiplome != null)
				return false;
		} else if (!idDiplome.equals(other.idDiplome))
			return false;
		if (nomDiplome == null) {
			if (other.nomDiplome != null)
				return false;
		} else if (!nomDiplome.equals(other.nomDiplome))
			return false;
		return true;
	}
	
	
}

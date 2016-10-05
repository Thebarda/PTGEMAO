package fr.gemao.entity.materiel;

import java.io.Serializable;
import java.sql.Date;

/**
 * The Class Reparation.
 */
public class Reparation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The id reparation. */
	private int idReparation;
	
	/** The Reparateur. */
	private Reparateur Reparateur;
	
	/** The date certificat. */
	private Date dateCertificat;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dateCertificat == null) ? 0 : dateCertificat.hashCode());
		result = prime * result + idReparation;
		return result;
	}
	
	public Reparation() {
	}
	
	/**
	 * Instantiates a new reparation.
	 *
	 * @param idReparation the id reparation
	 * @param reparateur the reparateur
	 * @param dateCertificat the date certificat
	 */
	public Reparation(int idReparation,
			Reparateur reparateur,
			Date dateCertificat) {
		this.idReparation = idReparation;
		Reparateur = reparateur;
		this.dateCertificat = dateCertificat;
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
		Reparation other = (Reparation) obj;
		if (dateCertificat == null) {
			if (other.dateCertificat != null)
				return false;
		} else if (!dateCertificat.equals(other.dateCertificat))
			return false;
		if (idReparation != other.idReparation)
			return false;
		return true;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Reparation [idReparation=" + idReparation + ", dateCertificat="
				+ dateCertificat + "]";
	}
	
	/**
	 * Gets the id reparation.
	 *
	 * @return the id reparation
	 */
	public int getIdReparation() {
		return idReparation;
	}
	
	/**
	 * Sets the id reparation.
	 *
	 * @param idReparation the new id reparation
	 */
	public void setIdReparation(int idReparation) {
		this.idReparation = idReparation;
	}
	
	/**
	 * Gets the reparateur.
	 *
	 * @return the reparateur
	 */
	public Reparateur getReparateur() {
		return Reparateur;
	}
	
	/**
	 * Sets the reparateur.
	 *
	 * @param reparateur the new reparateur
	 */
	public void setReparateur(Reparateur reparateur) {
		Reparateur = reparateur;
	}
	
	/**
	 * Gets the date certificat.
	 *
	 * @return the date certificat
	 */
	public Date getDateCertificat() {
		return dateCertificat;
	}
	
	/**
	 * Sets the date certificat.
	 *
	 * @param dateCertificat the new date certificat
	 */
	public void setDateCertificat(Date dateCertificat) {
		this.dateCertificat = dateCertificat;
	}
}

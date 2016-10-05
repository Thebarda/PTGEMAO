package fr.gemao.entity;

import java.io.Serializable;

public class Adresse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idAdresse;
	private Commune commune;
	private String numRue;
	private String nomRue;
	private String infoCompl;
	
	/**
	 * Constructeur
	 */
	public Adresse() {
	}
	
	/**
	 * Constructeur surchargé
	 * @param idAdresse : l'ID de l'adresse
	 * @param commune : le commune du fromage
	 * @param numRue : le numéro de rue
	 * @param nomRue : le nom de la rue
	 * @param infoCompl : informations complémentaires
	 */
	public Adresse(Integer idAdresse, Commune commune, String numRue, String nomRue, String infoCompl) {
		super();
		this.idAdresse = idAdresse;
		this.commune = commune;
		this.numRue = numRue;
		this.nomRue = nomRue;
		this.infoCompl = infoCompl;
	}

	public Integer getIdAdresse() {
		return idAdresse;
	}

	public void setIdAdresse(Integer idAdresse) {
		this.idAdresse = idAdresse;
	}

	public Commune getCommune() {
		return commune;
	}

	public void setCommune(Commune commune) {
		this.commune = commune;
	}

	public String getNumRue() {
		return numRue;
	}

	public void setNumRue(String numRue) {
		this.numRue = numRue;
	}

	public String getNomRue() {
		return nomRue;
	}

	public void setNomRue(String nomRue) {
		this.nomRue = nomRue;
	}

	public String getInfoCompl() {
		return infoCompl;
	}

	public void setInfoCompl(String infoCompl) {
		this.infoCompl = infoCompl;
	}

	@Override
	public String toString() {
		return numRue+" "+nomRue+"\n"+infoCompl+"\n"+commune.getCodePostal()+" "+commune.getNomCommune();
//		return "Adresse [idAdresse=" + idAdresse + ", idCommune=" + commune
//				+ ", numRue=" + numRue + ", nomRue=" + nomRue + ", infoCompl="
//				+ infoCompl + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commune == null) ? 0 : commune.hashCode());
		result = prime * result
				+ ((idAdresse == null) ? 0 : idAdresse.hashCode());
		result = prime * result
				+ ((infoCompl == null) ? 0 : infoCompl.hashCode());
		result = prime * result + ((nomRue == null) ? 0 : nomRue.hashCode());
		result = prime * result + ((numRue == null) ? 0 : numRue.hashCode());
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
		Adresse other = (Adresse) obj;
		if (commune == null) {
			if (other.commune != null)
				return false;
		} else if (!commune.equals(other.commune))
			return false;
		if (idAdresse == null) {
			if (other.idAdresse != null)
				return false;
		} else if (!idAdresse.equals(other.idAdresse))
			return false;
		if (infoCompl == null) {
			if (other.infoCompl != null)
				return false;
		} else if (!infoCompl.equals(other.infoCompl))
			return false;
		if (nomRue == null) {
			if (other.nomRue != null)
				return false;
		} else if (!nomRue.equals(other.nomRue))
			return false;
		if (numRue == null) {
			if (other.numRue != null)
				return false;
		} else if (!numRue.equals(other.numRue))
			return false;
		return true;
	}

	
}
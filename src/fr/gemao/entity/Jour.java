package fr.gemao.entity;

import java.io.Serializable;

/**
 * Classe représentant un ou plusieurs jours de la semaine (un jour en
 * particulier, aucun ou tous)
 * 
 * @author Benoît
 *
 */
public class Jour implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idJour;
	private String nomJour;

	public Jour(Integer idJour, String nomJour) {
		super();
		this.idJour = idJour;
		this.nomJour = nomJour;
	}

	public Integer getIdJour() {
		return idJour;
	}

	public void setIdJour(Integer idJour) {
		this.idJour = idJour;
	}

	public String getNomJour() {
		return nomJour;
	}

	public void setNomJour(String nomJour) {
		this.nomJour = nomJour;
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
		result = prime * result + ((idJour == null) ? 0 : idJour.hashCode());
		result = prime * result + ((nomJour == null) ? 0 : nomJour.hashCode());
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
		Jour other = (Jour) obj;
		if (idJour == null) {
			if (other.idJour != null)
				return false;
		} else if (!idJour.equals(other.idJour))
			return false;
		if (nomJour == null) {
			if (other.nomJour != null)
				return false;
		} else if (!nomJour.equals(other.nomJour))
			return false;
		return true;
	}

}

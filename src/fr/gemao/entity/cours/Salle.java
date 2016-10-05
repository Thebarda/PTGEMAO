package fr.gemao.entity.cours;

import java.io.Serializable;

public class Salle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer idSalle;
	private String nomSalle;

	public Salle(Integer idSalle, String nomSalle) {
		super();
		this.idSalle = idSalle;
		this.nomSalle = nomSalle;
	}

	public Integer getIdSalle() {
		return idSalle;
	}

	public void setIdSalle(Integer idSalle) {
		this.idSalle = idSalle;
	}

	public String getNomSalle() {
		return nomSalle;
	}

	public void setNomSalle(String nomSalle) {
		this.nomSalle = nomSalle;
	}

	@Override
	public String toString() {
		return this.idSalle + " - " + this.nomSalle;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Salle other = (Salle) obj;
		if (idSalle == null) {
			if (other.idSalle != null)
				return false;
		} else if (!idSalle.equals(other.idSalle))
			return false;
		return true;
	}
}

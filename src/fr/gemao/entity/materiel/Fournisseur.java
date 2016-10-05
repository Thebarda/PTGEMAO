package fr.gemao.entity.materiel;

import java.io.Serializable;

public class Fournisseur implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int idFournisseur;
	private String nomFournisseur;
	@Override
	public String toString() {
		return "Fournisseur [idFournisseur=" + idFournisseur
				+ ", nomFournisseur=" + nomFournisseur + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idFournisseur;
		result = prime * result
				+ ((nomFournisseur == null) ? 0 : nomFournisseur.hashCode());
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
		Fournisseur other = (Fournisseur) obj;
		if (idFournisseur != other.idFournisseur)
			return false;
		if (nomFournisseur == null) {
			if (other.nomFournisseur != null)
				return false;
		} else if (!nomFournisseur.equals(other.nomFournisseur))
			return false;
		return true;
	}
	public Fournisseur(int idFournisseur, String nomFournisseur) {
		this.idFournisseur = idFournisseur;
		this.nomFournisseur = nomFournisseur;
	}
	public Fournisseur() {
	}
	public int getIdFournisseur() {
		return idFournisseur;
	}
	public void setIdFournisseur(int idFournisseur) {
		this.idFournisseur = idFournisseur;
	}
	public String getNomFournisseur() {
		return nomFournisseur;
	}
	public void setNomFournisseur(String nomFournisseur) {
		this.nomFournisseur = nomFournisseur;
	}

}

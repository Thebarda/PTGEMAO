package fr.gemao.entity;

import java.io.Serializable;
import java.util.Date;

import fr.gemao.entity.util.Civilite;

public class Personne implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long idPersonne;
	private Adresse adresse;
	private Commune communeNaiss;
	private String nom;
	private String prenom;
	private Date dateNaissance;
	private String telFixe;
	private String telPort;
	private String email;
	private Civilite civilite;
	private boolean membreCA; 
	
	public Personne() {
	}

	/**
	 * Construit une Personne
	 * 
	 * @param idPersonne
	 * @param idAdresse
	 * @param idCommuneNaiss
	 * @param nom
	 * @param prenom
	 * @param dateNaissance
	 * @param telFixe
	 * @param telPort
	 * @param email
	 * @param civilite
	 */
	public Personne(Long idPersonne, Adresse idAdresse, Commune idCommuneNaiss,
			String nom, String prenom, Date dateNaissance, String telFixe,
			String telPort, String email, Civilite civilite, boolean membreCA) {
		this.idPersonne = idPersonne;
		this.adresse = idAdresse;
		this.communeNaiss = idCommuneNaiss;
		this.nom = nom;
		this.prenom = prenom;
		this.dateNaissance = dateNaissance;
		this.telFixe = telFixe;
		this.telPort = telPort;
		this.email = email;
		this.civilite = civilite;
		this.membreCA = membreCA;
	}

	/**
	 * Construit une Personne � partir d'une personne.
	 * 
	 * @param personne
	 */
	public Personne(Personne personne) {
		this(personne.getIdPersonne(), personne.getAdresse(), personne
				.getCommuneNaiss(), personne.getNom(), personne.getPrenom(),
				personne.getDateNaissance(), personne.getTelFixe(), personne
						.getTelPort(), personne.getEmail(), personne.getCivilite(), personne.isMembreCA());
	}

	/**
	 * @return the idPersonne
	 */
	public Long getIdPersonne() {
		return idPersonne;
	}

	/**
	 * @param idPersonne
	 *            the idPersonne to set
	 */
	public void setIdPersonne(Long idPersonne) {
		this.idPersonne = idPersonne;
	}

	/**
	 * @return the idAdresse
	 */
	public Adresse getAdresse() {
		return adresse;
	}

	/**
	 * @param idAdresse
	 *            the idAdresse to set
	 */
	public void setAdresse(Adresse idAdresse) {
		this.adresse = idAdresse;
	}

	/**
	 * @return the idCommuneNaiss
	 */
	public Commune getCommuneNaiss() {
		return communeNaiss;
	}

	/**
	 * @param communeNaiss
	 *            the idCommuneNaiss to set
	 */
	public void setCommuneNaiss(Commune communeNaiss) {
		this.communeNaiss = communeNaiss;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom
	 *            the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the dateNaissance
	 */
	public Date getDateNaissance() {
		return dateNaissance;
	}

	/**
	 * @param dateNaissance
	 *            the dateNaissance to set
	 */
	public void setDateNaissance(Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	/**
	 * @return the telFixe
	 */
	public String getTelFixe() {
		return telFixe;
	}

	/**
	 * @param telFixe
	 *            the telFixe to set
	 */
	public void setTelFixe(String telFixe) {
		this.telFixe = telFixe;
	}

	/**
	 * @return the telPort
	 */
	public String getTelPort() {
		return telPort;
	}

	/**
	 * @param telPort
	 *            the telPort to set
	 */
	public void setTelPort(String telPort) {
		this.telPort = telPort;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((civilite == null) ? 0 : civilite.hashCode());
		result = prime * result
				+ ((dateNaissance == null) ? 0 : dateNaissance.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result
				+ ((communeNaiss == null) ? 0 : communeNaiss.hashCode());
		result = prime * result
				+ ((idPersonne == null) ? 0 : idPersonne.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		result = prime * result + ((telFixe == null) ? 0 : telFixe.hashCode());
		result = prime * result + ((telPort == null) ? 0 : telPort.hashCode());
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
		Personne other = (Personne) obj;
		if (civilite != other.civilite)
			return false;
		if (dateNaissance == null) {
			if (other.dateNaissance != null)
				return false;
		} else if (!dateNaissance.equals(other.dateNaissance))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		} else if (!adresse.equals(other.adresse))
			return false;
		if (communeNaiss == null) {
			if (other.communeNaiss != null)
				return false;
		} else if (!communeNaiss.equals(other.communeNaiss))
			return false;
		if (idPersonne == null) {
			if (other.idPersonne != null)
				return false;
		} else if (!idPersonne.equals(other.idPersonne))
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (prenom == null) {
			if (other.prenom != null)
				return false;
		} else if (!prenom.equals(other.prenom))
			return false;
		if (telFixe == null) {
			if (other.telFixe != null)
				return false;
		} else if (!telFixe.equals(other.telFixe))
			return false;
		if (telPort == null) {
			if (other.telPort != null)
				return false;
		} else if (!telPort.equals(other.telPort))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Personne [idPersonne=");
		builder.append(idPersonne);
		builder.append(", idAdresse=");
		builder.append(adresse);
		builder.append(", idCommuneNaiss=");
		builder.append(communeNaiss);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", prenom=");
		builder.append(prenom);
		builder.append(", dateNaissance=");
		builder.append(dateNaissance);
		builder.append(", telFixe=");
		builder.append(telFixe);
		builder.append(", telPort=");
		builder.append(telPort);
		builder.append(", email=");
		builder.append(email);
		builder.append(", civilite=");
		builder.append(civilite);
		builder.append("]");
		return builder.toString();
	}

	public Civilite getCivilite() {
		return civilite;
	}

	public void setCivilite(Civilite civilite) {
		this.civilite = civilite;
	}

	/**
	 * @return the membreCA
	 */
	public boolean isMembreCA() {
		return membreCA;
	}

	/**
	 * @param membreCA the membreCA to set
	 */
	public void setMembreCA(boolean membreCA) {
		this.membreCA = membreCA;
	}

}

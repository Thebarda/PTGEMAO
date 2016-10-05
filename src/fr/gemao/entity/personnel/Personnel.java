package fr.gemao.entity.personnel;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import fr.gemao.entity.Adresse;
import fr.gemao.entity.Commune;
import fr.gemao.entity.Personne;
import fr.gemao.entity.administration.Profil;
import fr.gemao.entity.cours.Discipline;
import fr.gemao.entity.util.Civilite;

/**
 * Classe Contrat permettant de créer un personnel (extends Personne)
 * 
 * @author Coco
 *
 */
public class Personnel extends Personne implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Responsabilite> listeResponsabilite;
	private List<Diplome> listeDiplomes;
	private List<Discipline> listeDisciplines;
	private List<Contrat> contrat;
	private String login;
	private String password;
	private int pointsAncien;
	private Profil profil;
	private boolean premiereConnexion;
	private String numeroSS;
	private Date dateEntree;

	public Personnel() {
	}

	/**
	 * Constructeur de la classe Personnel
	 * 
	 * @param idPersonne
	 *            : l'ID de la personne
	 * @param idAdresse
	 *            : l'ID de l'adresse
	 * @param idCommuneNaiss
	 *            : l'ID de la commune de naissance
	 * @param nom
	 *            : le nom de la personne
	 * @param prenom
	 *            : le prénom de la personne
	 * @param dateNaissance
	 *            : la date de naissance de la personne
	 * @param telFixe
	 *            : le numéro de téléphone fixe de la personne
	 * @param telPort
	 *            : le numéro de téléphone portable de la personne
	 * @param email
	 *            : l'email de la personne
	 * @param listeResponsabilite
	 *            : l'ID des responsabilités
	 * @param contrat
	 *            : l'ID du contrat
	 * @param login
	 *            : le login de la personne
	 * @param password
	 *            : le password de la personne
	 * @param pointsAncien
	 *            : le nombre de points d'ancienneté
	 * @param profil
	 *            : le profil du personnel
	 * @param premiereConnexion
	 *            : un booléen valant vrai s'il s'agit de la première connexion
	 *            de ce membre du personnel
	 */
	public Personnel(Long idPersonne, Adresse adresse, Commune communeNaiss, String nom, String prenom,
			Date dateNaissance, String telFixe, String telPort, String email, Civilite civilite,
			List<Responsabilite> listeResponsabilite, List<Diplome> listeDiplome, List<Discipline> listeDiscipline,
			List<Contrat> contrat, String login, String password, int pointsAncien, Profil profil,
			boolean premiereConnexion, boolean membreCA, String numeroSS, Date dateEntree) {
		super(idPersonne, adresse, communeNaiss, nom, prenom, dateNaissance, telFixe, telPort, email, civilite,
				membreCA);

		this.listeResponsabilite = listeResponsabilite;
		this.listeDiplomes = listeDiplome;
		this.listeDisciplines = listeDiscipline;
		this.contrat = contrat;
		this.login = login;
		this.password = password;
		this.pointsAncien = pointsAncien;
		this.profil = profil;
		this.premiereConnexion = premiereConnexion;
		this.numeroSS = numeroSS;
		this.dateEntree = dateEntree;
	}

	/**
	 * 
	 * @param personne
	 * @param listeResponsabilite
	 * @param contrat
	 * @param login
	 * @param password
	 * @param pointsAncien
	 */
	public Personnel(Personne personne, List<Responsabilite> listeResponsabilite, List<Diplome> listeDiplome,
			List<Discipline> listeDiscipline, List<Contrat> contrat, String login, String password,
			Integer pointsAncien, Profil profil, boolean premiereConnexion, String numeroSS, Date dateEntree) {
		super(personne);

		this.listeResponsabilite = listeResponsabilite;
		this.listeDiplomes = listeDiplome;
		this.listeDisciplines = listeDiscipline;
		this.contrat = contrat;
		this.login = login;
		this.password = password;
		this.pointsAncien = pointsAncien;
		this.profil = profil;
		this.premiereConnexion = premiereConnexion;
		this.numeroSS = numeroSS;
		this.dateEntree = dateEntree;
	}

	/**
	 * Permet de retourner la liste des ID de responsabilités du personnel
	 * 
	 * @return idResponsabilite : la liste des responsabilités
	 */
	public List<Responsabilite> getListeResponsabilite() {
		return listeResponsabilite;
	}

	public List<Discipline> getListeDiscipline() {
		return listeDisciplines;
	}

	/**
	 * Permet de retourner l'ID du contrat du personnel
	 * 
	 * @return idContrat : l'ID du contrat
	 */
	public List<Contrat> getContrat() {
		return contrat;
	}

	/**
	 * Permet de retourner le login du personnel
	 * 
	 * @return login : le login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * Permet de retourner le password du personnel
	 * 
	 * @return password : le password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Permet de retourner le nombre de points d'ancienneté du personnel
	 * 
	 * @return pointsAncien : le nombre de points
	 */
	public int getPointsAncien() {
		return pointsAncien;
	}

	public Date getDateEntree() {
		return dateEntree;
	}

	public void setDateEntree(Date dateEnt) {
		this.dateEntree = dateEnt;
	}

	/**
	 * Permet de positionner une liste d'ID responsabilités
	 * 
	 * @param idResponsabilite
	 *            : la liste de responsabilités
	 */
	public void setListeResponsabilite(List<Responsabilite> listeResponsabilite) {
		this.listeResponsabilite = listeResponsabilite;
	}

	public void setListeDiscipline(List<Discipline> listeDiscipline) {
		this.listeDisciplines = listeDiscipline;
	}

	/**
	 * Permet de positionner l'ID du contrat du personnel
	 * 
	 * @param contrat
	 *            : l'ID du contrat
	 */
	public void setContrat(List<Contrat> contrat) {
		this.contrat = contrat;
	}

	/**
	 * Permet de positionner le login du personnel
	 * 
	 * @param login
	 *            : le login
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Permet de positionner le password du personnel
	 * 
	 * @param password
	 *            : le passeword
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Permet de positionner pointsAncien du personnel
	 * 
	 * @param pointsAncien
	 *            : le nombre de points d'ancienneté
	 */
	public void setPointsAncien(int pointsAncien) {
		this.pointsAncien = pointsAncien;
	}

	/**
	 * @return the listeDiplomes
	 */
	public List<Diplome> getListeDiplomes() {
		return listeDiplomes;
	}

	/**
	 * @param listeDiplomes
	 *            the listeDiplomes to set
	 */
	public void setListeDiplomes(List<Diplome> listeDiplomes) {
		this.listeDiplomes = listeDiplomes;
	}

	/**
	 * @return the profil
	 */
	public Profil getProfil() {
		return profil;
	}

	/**
	 * @param profil
	 *            the profil to set
	 */
	public void setProfil(Profil profil) {
		this.profil = profil;
	}

	public String getNumeroSS() {
		return numeroSS;
	}

	public void setNumeroSS(String numeroSS) {
		this.numeroSS = numeroSS;
	}

	public boolean isPremiereConnexion() {
		return premiereConnexion;
	}

	public void setPremiereConnexion(boolean premiereConnexion) {
		this.premiereConnexion = premiereConnexion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((contrat == null) ? 0 : contrat.hashCode());
		result = prime * result + ((dateEntree == null) ? 0 : dateEntree.hashCode());
		result = prime * result + ((listeDiplomes == null) ? 0 : listeDiplomes.hashCode());
		result = prime * result + ((listeDisciplines == null) ? 0 : listeDisciplines.hashCode());
		result = prime * result + ((listeResponsabilite == null) ? 0 : listeResponsabilite.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((numeroSS == null) ? 0 : numeroSS.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + pointsAncien;
		result = prime * result + (premiereConnexion ? 1231 : 1237);
		result = prime * result + ((profil == null) ? 0 : profil.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personnel other = (Personnel) obj;
		if (contrat == null) {
			if (other.contrat != null)
				return false;
		} else if (!contrat.equals(other.contrat))
			return false;
		if (dateEntree == null) {
			if (other.dateEntree != null)
				return false;
		} else if (!dateEntree.equals(other.dateEntree))
			return false;
		if (listeDiplomes == null) {
			if (other.listeDiplomes != null)
				return false;
		} else if (!listeDiplomes.equals(other.listeDiplomes))
			return false;
		if (listeDisciplines == null) {
			if (other.listeDisciplines != null)
				return false;
		} else if (!listeDisciplines.equals(other.listeDisciplines))
			return false;
		if (listeResponsabilite == null) {
			if (other.listeResponsabilite != null)
				return false;
		} else if (!listeResponsabilite.equals(other.listeResponsabilite))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (numeroSS == null) {
			if (other.numeroSS != null)
				return false;
		} else if (!numeroSS.equals(other.numeroSS))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (pointsAncien != other.pointsAncien)
			return false;
		if (premiereConnexion != other.premiereConnexion)
			return false;
		if (profil == null) {
			if (other.profil != null)
				return false;
		} else if (!profil.equals(other.profil))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Personnel [listeResponsabilite=" + listeResponsabilite + ", listeDiplomes=" + listeDiplomes
				+ ", listeDisciplines=" + listeDisciplines + ", contrat=" + contrat + ", login=" + login + ", password="
				+ password + ", pointsAncien=" + pointsAncien + ", profil=" + profil + ", premiereConnexion="
				+ premiereConnexion + ", numeroSS=" + numeroSS + ", dateEntree=" + dateEntree + "]";
	}

}
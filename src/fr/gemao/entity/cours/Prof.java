package fr.gemao.entity.cours;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import fr.gemao.entity.Adresse;
import fr.gemao.entity.Commune;
import fr.gemao.entity.Personne;
import fr.gemao.entity.administration.Profil;
import fr.gemao.entity.personnel.Contrat;
import fr.gemao.entity.personnel.Diplome;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.entity.personnel.Responsabilite;
import fr.gemao.entity.util.Civilite;

public class Prof extends Personnel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date dateDebutEnseignement;

	public Prof(Date dateDebutEnseignement) {
		super();
		this.dateDebutEnseignement = dateDebutEnseignement;
	}

	public Prof(Long idPersonne, Adresse adresse, Commune communeNaiss, String nom, String prenom, Date dateNaissance,
			String telFixe, String telPort, String email, Civilite civilite, List<Responsabilite> listeResponsabilite,
			List<Diplome> listeDiplome, List<Discipline> listeDiscipline, List<Contrat> contrat, String login,
			String password, int pointsAncien, Profil profil, boolean premiereConnexion, Date dateDebutEnseignement,
			boolean membreCA, String numeroSS) {
		super(idPersonne, adresse, communeNaiss, nom, prenom, dateNaissance, telFixe, telPort, email, civilite,
				listeResponsabilite, listeDiplome, listeDiscipline, contrat, login, password, pointsAncien, profil,
				premiereConnexion, membreCA, numeroSS, dateDebutEnseignement);
		this.dateDebutEnseignement = dateDebutEnseignement;
	}

	public Prof(Personne personne, List<Responsabilite> listeResponsabilite, List<Diplome> listeDiplome,
			List<Discipline> listeDiscipline, List<Contrat> contrat, String login, String password,
			Integer pointsAncien, Profil profil, boolean premiereConnexion, Date dateDebutEnseignement,
			String numeroSS) {
		super(personne, listeResponsabilite, listeDiplome, listeDiscipline, contrat, login, password, pointsAncien,
				profil, premiereConnexion, numeroSS, dateDebutEnseignement);
		this.dateDebutEnseignement = dateDebutEnseignement;
	}

	public Date getDateDebutEnseignement() {
		return dateDebutEnseignement;
	}

	public void setDateDebutEnseignement(Date dateDebutEnseignement) {
		this.dateDebutEnseignement = dateDebutEnseignement;
	}

	/**
	 * Cette méthode retourne le sexe, le nom et le prénom du prof
	 * 
	 * @return
	 */
	public String getIdentiteProf() {
		return this.getCivilite().getNameCourt() + " " + this.getNom() + " " + this.getPrenom();
	}

}

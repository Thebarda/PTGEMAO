package fr.gemao.view;

public class JSPFile {
	public static final String ROOT = "/WEB-INF/pages/",
		// Divers
		CHANGER_MOT_DE_PASSE = ROOT + "changerMotDePasse.jsp",
		CONFIGURATION = ROOT + "configuration.jsp",
		CONNEXION = ROOT + "connexion.jsp",
		RESULTAT = ROOT + "resultat.jsp",

		// Module adhérent
		REP_ADHERENT = "adherent/",
		ADHERENT_AJOUT_ADHERENT = ROOT + REP_ADHERENT + "ajoutAdherent.jsp",
		ADHERENT_AJOUT_RESPONSABLE = ROOT + REP_ADHERENT + "ajoutResponsable.jsp",
		ADHERENT_CALCUL_QF = ROOT + REP_ADHERENT + "calculQF.jsp",
		ADHERENT_CONFIRMATION_AJOUT = ROOT + REP_ADHERENT + "confirmationAjoutAdherent.jsp",
		ADHERENT_CONFIRMATION_MODIFICATION = ROOT + REP_ADHERENT + "confirmationModifAdherent.jsp",
		ADHERENT_CONSULTER = ROOT + REP_ADHERENT + "consulteAdherent.jsp",
		ADHERENT_ECHEC_AJOUT = ROOT + REP_ADHERENT + "echecAjoutAdherent.jsp",
		ADHERENT_ECHEC_MODIFICATION = ROOT + REP_ADHERENT + "echecModifAdherent.jsp",
		ADHERENT_LISTER = ROOT + REP_ADHERENT + "listeAdherents.jsp",
		ADHERENT_LISTER_ANCIEN = ROOT + REP_ADHERENT + "listeAnciensAdherents.jsp",
		ADHERENT_MODIFIER_ADHERENT = ROOT + REP_ADHERENT + "modifAdherent.jsp",
		ADHERENT_MODIFIER_RESPONSABLE = ROOT + REP_ADHERENT + "modifResponsable.jsp",
		ADHERENT_DESINSCRIRE_ADHERENT = ROOT + REP_ADHERENT + "desinscrireAdherent.jsp",
		ADHERENT_PARAMETRE = ROOT + REP_ADHERENT + "parametre.jsp",
		ADHERENT_VALIDATION_AJOUT = ROOT + REP_ADHERENT + "validAjoutAdherent.jsp",
		ADHERENT_VALIDATION_MODIF = ROOT + REP_ADHERENT + "validModifAdherent.jsp",
		ADHERENT_SAISIE_COTISATION = ROOT + REP_ADHERENT + "saisieCotisation.jsp",
		ADHERENT_A_PAYE = ROOT + REP_ADHERENT + "aPaye.jsp",
		ADHERENT_REINSCRIRE = ROOT + REP_ADHERENT + "reinscrire.jsp",
		ADHERENT_VALIDATION_REINS = ROOT + REP_ADHERENT + "validationReinscription.jsp",
		ADHERENT_LISTE_DISCIPLINES = ROOT + REP_ADHERENT + "listeDisciplines.jsp",
		ADHERENT_CONFIRMATION_MODIFICATION_QF = ROOT + REP_ADHERENT + "confirmationModifQF.jsp",
		TEST = ROOT + REP_ADHERENT + "test.jsp",

		// Module administration
		REP_ADMINISTRATION = "administration/",
		ADMINISTRATION_AJOUT_PROFIL = ROOT + REP_ADMINISTRATION + "ajouterProfil.jsp",
		ADMINISTRATION_CHANGER_PROFIL = ROOT + REP_ADMINISTRATION + "affecterProfil.jsp",
		ADMINISTRATION_CONSULTER_PROFIL = ROOT + REP_ADMINISTRATION + "consulterProfil.jsp",
		ADMINISTRATION_LISTER_MODIFICATIONS = ROOT + REP_ADMINISTRATION + "listerModifs.jsp",
		ADMINISTRATION_LISTER_PROFILS = ROOT + REP_ADMINISTRATION + "listerProfils.jsp",
		ADMINISTRATION_MODIFIER_PROFIL = ROOT + REP_ADMINISTRATION + "modifierProfil.jsp",
		ADMINISTRATION_RESET_PASSWORD = ROOT + REP_ADMINISTRATION + "resetPassword.jsp",

		// Erreurs
		REP_ERREUR = "erreurs/",
		ERREUR = ROOT + REP_ERREUR + "erreur.jsp",
		ERREUR_404 = ROOT + REP_ERREUR + "404.jsp",
		ERREUR_DROIT = ROOT + REP_ERREUR + "erreurDroit.jsp",

		// Module location
		REP_LOCATION = "location/",
		LOCATION_INTERNE = ROOT + REP_LOCATION + "locationInterne.jsp",
		LOCATION_EXTERNE = ROOT + REP_LOCATION + "locationExterne.jsp",
		LOCATION_RETOUR = ROOT + REP_LOCATION + "retourInstrument.jsp",
		LOCATION_LISTER = ROOT + REP_LOCATION + "listerLocations.jsp",
		LOCATION_IMPRIMER = ROOT + REP_LOCATION + "imprimLocation.jsp",
		LOCATION_CONTRAT_LISTER = ROOT + REP_LOCATION + "listerContratsLocation.jsp",

		// Module matériel
		REP_MATERIEL = "materiel/",
		MATERIEL_AJOUT = ROOT + REP_MATERIEL + "ajoutMateriel.jsp",
		MATERIEL_VALIDATION_AJOUT = ROOT + REP_MATERIEL + "validationAjoutMateriel.jsp",
		MATERIEL_VALIDATION_MODIFIER = ROOT + REP_MATERIEL + "validationModificationMateriel.jsp",
		MATERIEL_CONSULTER = ROOT + REP_MATERIEL + "consulterMateriel.jsp",
		MATERIEL_LISTER = ROOT + REP_MATERIEL + "listerMateriel.jsp",
		MATERIEL_MODIFIER = ROOT + REP_MATERIEL + "modifierMateriel.jsp",
		MATERIEL_LISTE_ETAT = ROOT + REP_MATERIEL + "listerEtat.jsp",
		MATERIEL_LISTE_FOURNISSEUR = ROOT + REP_MATERIEL + "listerFournisseur.jsp",
		MATERIEL_LISTE_DESIGNATION = ROOT + REP_MATERIEL + "listerDesignation.jsp",
		MATERIEL_LISTE_MARQUE = ROOT + REP_MATERIEL + "listerMarque.jsp",
		MATERIEL_LISTE_CATEGORIE = ROOT + REP_MATERIEL + "listerCategorie.jsp",

		// Module personnel
		REP_PERSONNEL = "personnel/",
		PERSONNEL_AJOUT = ROOT + REP_PERSONNEL + "ajoutPersonnel.jsp",
		PERSONNEL_AJOUT2 = ROOT + REP_PERSONNEL + "ajoutPersonnel2.jsp",
		PERSONNEL_AJOUT3 = ROOT + REP_PERSONNEL + "ajoutPersonnel3.jsp",
		PERSONNEL_VALIDATION_MODIF = ROOT + REP_PERSONNEL + "validModifPersonnel.jsp",
		PERSONNEL_VALIDATION_AJOUT = ROOT + REP_PERSONNEL + "validAjoutPersonnel.jsp",
		PERSONNEL_ECHEC_AJOUT = ROOT + REP_PERSONNEL + "echecAjoutPersonnel.jsp",
		PERSONNEL_CONSULTER = ROOT + REP_PERSONNEL + "consulterPersonnel.jsp",
		PERSONNEL_LISTER = ROOT + REP_PERSONNEL + "listePersonnel.jsp",
		PERSONNEL_MODIFIER = ROOT + REP_PERSONNEL + "modifPersonnel.jsp",

		// Module Cours
		REP_COURS = "cours/", COURS_AJOUTEDT = ROOT + REP_COURS + "ajoutEDT.jsp",

		// Module Planning
		REP_PLANNING = "planning/",
		PLANNING_AFFICHER = ROOT + REP_PLANNING + "afficherPlanning.jsp",
		PLANNING_LISTER = ROOT + REP_PLANNING + "listerPlannings.jsp",
		PLANNING_LISTER_ARCHIVE = ROOT + REP_PLANNING + "listerPlanningArchive.jsp",
		PLANNING_CREER = ROOT + REP_PLANNING + "creerPlanning.jsp",
		PLANNING_ARCHIVER = ROOT + REP_PLANNING + "archiverPlanning.jsp",
		CRENEAU_MODIFIER = ROOT + REP_PLANNING + "modifierCreneau.jsp",
		PLANNING_MODIFIERCRENEAUX = ROOT + REP_PLANNING + "modifCreneauxPlanning.jsp",
		RESERVATION_LISTER = ROOT + REP_PLANNING + "listerReservations.jsp",
		RESERVATION_CREER = ROOT + REP_PLANNING + "creerReservation.jsp",
		CRENEAUX_LISTER = ROOT + REP_PLANNING + "listerCreneaux.jsp",
		MODIFIER_PLANNING_CARACTERISTIQUE_ = ROOT + REP_PLANNING + "modifierCaracteristiquesPlanning.jsp",
		CRENEAU_CREER = ROOT + REP_PLANNING + "creerCreneau.jsp",
		SALLE_LISTER = ROOT + REP_PLANNING + "listerSalle.jsp",
		SALLE_CREER = ROOT + REP_PLANNING + "creerSalle.jsp",
		SALLE_MODIFIER = ROOT + REP_PLANNING + "modifierSalle.jsp",
		CONTRAINTE_MODIFIER = ROOT + REP_PLANNING + "modifierContrainte.jsp",
		CONTRAINTE_ADD_GLOBALE = ROOT + REP_PLANNING + "addContrainteGlobale.jsp",
		CONTRAINTE_LISTER_GLOBALE = ROOT + REP_PLANNING + "listerContrainteGlobale.jsp",
		
		//Gestion cours
		COURS_LISTER = ROOT + REP_COURS + "listerCours.jsp",
		COURS_MODIFIER = ROOT + REP_COURS + "modifierCours.jsp",
		COURS_CREER = ROOT + REP_COURS + "creerCours.jsp";
}

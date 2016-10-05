/**
 * 
 */
package fr.gemao.ctrl.adherent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import fr.gemao.ctrl.PersonneCtrl;
import fr.gemao.entity.Parametre;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.ParametreDAO;
import fr.gemao.sql.PersonneDAO;
import fr.gemao.sql.adherent.AdherentDAO;

/**
 * Ce controleur regroupe toutes les actions sur des {@link Adherent}
 * 
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public final class AdherentCtrl {

	private AdherentCtrl() {
	}

	/**
	 * Méthode permettant de récupérer un adhérent selon son identifiant
	 * 
	 * @param id
	 * @return l'adhérent dont l'identifiant est idPersonne
	 */
	public static Adherent recupererAdherent(int id) {
		DAOFactory co = DAOFactory.getInstance();
		AdherentDAO adherentDAO = co.getAdherentDAO();

		Adherent adherent = adherentDAO.get(id);

		return adherent;
	}

	/**
	 * Méthode permettant de récupérer l'ensemble des adhérents
	 * 
	 * @return la liste des adhérents de la BD
	 */
	public static List<Adherent> recupererTousAdherents() {
		List<Adherent> listeAdherents = new ArrayList<Adherent>();
		DAOFactory co = DAOFactory.getInstance();
		AdherentDAO adherentDAO = co.getAdherentDAO();

		listeAdherents = adherentDAO.getAll();

		return listeAdherents;
	}

	/**
	 * Méthode permettant de récupérer l'ensemble des anciens adhérents
	 * 
	 * @return la liste des anciens adhérents de la BD
	 */
	public static List<Adherent> recupererAnciensAdherents() {
		List<Adherent> listeAdherents = new ArrayList<Adherent>();
		DAOFactory co = DAOFactory.getInstance();
		AdherentDAO adherentDAO = co.getAdherentDAO();

		listeAdherents = adherentDAO.getAllAnciens();

		return listeAdherents;
	}

	/**
	 * Méthode permettant de vérifier la validité des informations d'un adhérent
	 * 
	 * @param adherent
	 * @return true si les informations sont valides, false sinon
	 */
	public static boolean verifierInformations(Adherent adherent) {
		Date date = new Date();

		// Vérification de l'idMotif
		// if(adherent.getMotif() == null || adherent.getMotif().getIdMotif() <=
		// 0){
		// System.out.println("L'idMotif est invalide...");
		// return false;
		// }

		// Vérification de l'idResponsable
		if (adherent.getResponsable() != null && adherent.getResponsable().getIdResponsable() <= 0) {
			return false;
		}

		// Vérification de la date d'entrée
		if (adherent.getDateEntree() == null || adherent.getDateEntree().after(date)) {
			System.out.println("La date d'entrée doit être antérieure à la date actuelle...");
			return false;
		}

		// Vérification de la date de sortie
		if (adherent.getDateSortie() != null && adherent.getDateSortie().after(date)) {
			System.out.println("La date de sortie doit être antérieure à la date actuelle...");
			return false;
		}

		// Vérification de la cotisation
		if (adherent.getCotisation() == null) {
			System.out.println("La cotisation doit être spécifiée...");
			return false;
		}

		// Vérification de la/des disciplines
		// if(adherent.getDisciplines().isEmpty()){
		// System.out.println("L'adhérent doit être inscrit à au moins une
		// discipline...");
		// return false;
		// }

		return true;
	}

	/**
	 * Méthode permettant d'ajouter un adhérent dans la BD
	 * 
	 * @param adherent
	 *            : l'adhérent à ajouter
	 */
	public static boolean ajoutAdherent(Adherent adherent) {

		// Ajout de la personne dans la base
		if (PersonneCtrl.ajoutPersonne(adherent) != -1) {
			Adherent adh;

			DAOFactory co = DAOFactory.getInstance();
			AdherentDAO adherentDAO = co.getAdherentDAO();

			// Vérification de la validité des informations
			if (verifierInformations(adherent)) {
				adh = adherentDAO.create(adherent);
				if (adh != null) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Méthode permettant de modifier un adhérent. La méthode vérifie avant si
	 * les informations sont valides.
	 * 
	 * @param adherent
	 *            : l'adhérent à modifier
	 */
	public static boolean modifierAdherent(Adherent adherent) {
		// Vérification de la validité des informations
		if (AdherentCtrl.verifierInformations(adherent)) {

			// Vérification de la modification de la personne
			if (PersonneCtrl.modifierPersonne(adherent) != -1) {
				Adherent adh;

				DAOFactory co = DAOFactory.getInstance();
				AdherentDAO adherentDAO = co.getAdherentDAO();

				adh = adherentDAO.update(adherent);

				if (adh == null) {
					System.out.println("Une erreur est survenue lors de la modification.");
				} else {
					System.out.println("L'adhérent a bien été modifié.");
					return true;
				}
			} else {
				System.out.println("Une erreur est survenue lors de la modification");
			}
		} else {
			System.out.println("Les informations de l'adhérent ne sont pas valides");
		}
		return false;
	}

	public static void desinscrireAdherent(Adherent adherent) {

		// Vérification de l'existance de la personne
		DAOFactory co = DAOFactory.getInstance();
		PersonneDAO personneDAO = new PersonneDAO(co);
		if (personneDAO.exist(adherent) != null) {
			AdherentDAO adherentDAO = new AdherentDAO(co);

			// Mise à jour
			Adherent adh = adherentDAO.update(adherent);
			if (adh == null) {
				System.out.println("Une erreur est survenue lors de la désinscription.");
			} else {
				System.out.println("L'adhérent a bien été désinscrit.");
			}
		} else {
			System.out.println("L'adhérent n'existe pas dans la base...");
		}
	}

	/**
	 * Cette fonction permet de calculer
	 * 
	 * @param revenus
	 *            Les revenus du foyer (doit �tre sup�rieur � 0)
	 * @param nbPersFoyer
	 *            Le nombre de personnes du foyer (doit �tre sup�rieur � 0)
	 * @param nbEnfFoyer
	 *            Le nombre d'enfants du foyer (doit �tre sup�rieur � 0)
	 * @return Le quotient calcul�
	 */
	public static Float calculerQuotient(HttpServletRequest request) {
		float alloc, quotient = 0;

		DAOFactory co = DAOFactory.getInstance();
		ParametreDAO parametreDAO = co.getParametreDAO();
		Parametre param = new Parametre(parametreDAO.getLast());
		int nbPers = Integer.parseInt(request.getParameter("nbPers"));
		int nbEnfants = Integer.parseInt(request.getParameter("nbEnf"));
		float revenus = Float.parseFloat((request.getParameter("revenues")).replace(',', '.'));

		if (nbPers < nbEnfants)
			return null;

		switch (nbEnfants) {
		case 0:
		case 1:
			alloc = 0;
			break;
		case 2:
			alloc = param.getAlloc2();
			break;
		case 3:
			alloc = param.getAlloc3();
			break;
		case 4:
			alloc = param.getAlloc4();
			break;
		default:
			alloc = param.getAlloc5();
			break;
		}

		quotient = ((revenus / 12) + alloc) / nbPers;

		return quotient;
	}
}

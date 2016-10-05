/**
 * 
 */
package fr.gemao.ctrl.personnel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import fr.gemao.ctrl.PersonneCtrl;
import fr.gemao.entity.Personne;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.PersonneDAO;
import fr.gemao.sql.PersonnelDAO;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public final class PersonnelCtrl {

	private PersonnelCtrl() {

	}

	/**
	 * Permet de vérifier les informations d'un personnel
	 * 
	 * @param personnel
	 *            : le personnel à vérifier
	 * @return true si la syntaxe du personnel est valide, false, sinon
	 */
	public static boolean verifierInformations(Personnel personnel) {
		// A implémenter
		return true;
	}

	/**
	 * Méthode permettant d'ajouter un personnel dans la base de données
	 * 
	 * @param personnel
	 *            : le personnel à ajouter
	 */
	public static void ajouterPersonnel(Personnel personnel) {

		if (PersonnelCtrl.verifierInformations(personnel)) {

			if (PersonneCtrl.ajoutPersonne(personnel) != -1) {
				Personnel pers;

				DAOFactory co = DAOFactory.getInstance();
				PersonnelDAO personnelDAO = co.getPersonnelDAO();

				pers = personnelDAO.create(personnel);

				if (pers == null) {
					System.out.println("Une erreur est survenue lors de l'insertion");
				} else {
					System.out.println("Le personnel a bien été ajouté");
				}
			} else {
				System.out.println("Une erreur est survenue lors de l'insertion");
			}
		} else {
			System.out.println("Les informations du personnel ne sont pas valides");
		}
	}

	/**
	 * Cette méthode permet de vérifier la syntaxe du personnel.
	 * 
	 * @param personnel
	 *            : le personnel modifié
	 */
	public static long modifierPersonnel(Personnel personnel) {
		// Vérification de la validité des informations
		// if (ajoutPersonnel.verifierInformations(personnel)) {
		Personne pers;

		DAOFactory co = DAOFactory.getInstance();
		PersonneDAO personneDAO = co.getPersonneDAO();
		PersonnelDAO personnelDAO = co.getPersonnelDAO();

		// Vérification de l'existance de la personne dans la BD
		if (personneDAO.get(personnel.getIdPersonne()) != null) {
			personneDAO.update(personnel);
			pers = personnelDAO.update(personnel);

			if (pers == null) {
				// System.out.println("Une erreur est survenue lors de la
				// modification...");
				return -1;
			} else {
				// System.out.println("Le personnel a bien été modifiée.");
				return pers.getIdPersonne();
			}
		} else {
			throw new IllegalArgumentException("Le personnel n'existe pas dans la base...");
		}
		// } else {
		// System.out.println("Les informations du personnel ne sont pas
		// valides...");
		// return -1;
		// }
	}

	/**
	 * Permet de récupéper un personnel en fonction de son identifiant
	 * 
	 * @param idPersonne
	 *            : identifiant
	 * @return Personnel
	 */
	public static Personnel recupererPersonnel(Long idPersonne) {
		DAOFactory factory = DAOFactory.getInstance();
		PersonnelDAO personnelDAO = factory.getPersonnelDAO();

		Personnel personnel = personnelDAO.get(idPersonne);

		return personnel;
	}

	/**
	 * Permet de lister le personnel.
	 * 
	 * @return List Liste du personnel
	 */
	public static List<Personnel> recupererTousPersonnels() {
		List<Personnel> listePersonnels = new ArrayList<Personnel>();
		DAOFactory factory = DAOFactory.getInstance();
		PersonnelDAO personnelDAO = factory.getPersonnelDAO();

		listePersonnels = personnelDAO.getAll();

		return listePersonnels;
	}

	public static String genererLogin(String nom) {
		PersonnelDAO persoDAO = DAOFactory.getInstance().getPersonnelDAO();
		int nb = persoDAO.getNbNomPersonnel(nom);
		nb++;
		return nom + nb;
	}

	/**
	 * Méthode calculant la date de fin d'un contrat
	 * 
	 * @param jourDebutContrat
	 *            : le jour de début du contrat
	 * @param moisDebutContrat
	 *            : le mois de début du contrat
	 * @param anneeDebutContrat
	 *            : l'année de début du contrat
	 * @param duree
	 *            : la durée du contrat
	 * @return dateFin : la date de fin (String)
	 */
	public static Date CalculerDateFinContrat(Date dateDebut, int duree) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateDebut);

		/* Ajout de la durée du contrat en mois à l'objet Calendar */
		cal.add(Calendar.MONTH, duree);

		Date dateFin = cal.getTime();

		return dateFin;
	}
}

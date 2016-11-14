/**
 * 
 */
package fr.gemao.ctrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.gemao.entity.Personne;
import fr.gemao.entity.adherent.Adherent;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.PersonneDAO;
import fr.gemao.sql.adherent.AdherentDAO;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public final class PersonneCtrl {

	private PersonneCtrl() {

	}

	static String masqueNom = "^[A-Za-z âäàéèëêîïìôöòûüùÿ\\-]+$", masqueTel = "^[0][0-9]{9}$",
			masqueMail = "^[a-zA-Z]+[a-zA-Z0-9\\._-]*[a-zA-Z0-9]@[a-zA-Z]+"
					+ "[a-zA-Z0-9\\._-]*[a-zA-Z0-9]+\\.[a-zA-Z]{2,4}$";

	public static boolean verifierInformations(Personne personne) {

		Pattern pattern;
		Matcher controler;

		/**
		 * Vérification de l'idAdresse
		 */
		if (personne.getAdresse() == null || personne.getAdresse().getIdAdresse() <= 0) {
			System.out.println("L'idAdresse n'est pas valide...");
			return false;
		}

		/**
		 * Vérification de l'idCommuneNaiss
		 */
		// if (personne.getCommuneNaiss() == null ||
		// personne.getCommuneNaiss().getIdCommune() <= 0) {
		// System.out.println("L'idAdresse n'est pas valide...");
		// return false;
		// }

		/**
		 * Vérification du nom
		 */

		pattern = Pattern.compile(masqueNom);
		controler = pattern.matcher(personne.getNom());
		if (!controler.matches()) {
			System.out.println("Le format du nom est invalide...");
			return false;
		}

		/**
		 * Vérification du prenom
		 */
		controler = pattern.matcher(personne.getPrenom());
		if (!controler.matches()) {
			System.out.println("Le format du prénom est invalide...");
			return false;
		}

		/**
		 * Vérification de la date de naissance
		 */
		Date date = new Date();
		if (personne.getDateNaissance().after(date)) {
			System.out.println("La date de naissance doit être antérieure à la date actuelle...");
			return false;
		}

		/**
		 * Vérification de l'email (Vérifier si le masque est correct)
		 */
		pattern = Pattern.compile(masqueMail);
		if (personne.getEmail() == "")
			personne.setEmail(null);
		if (personne.getEmail() != null) {
			controler = pattern.matcher(personne.getEmail());
			if (!controler.matches()) {
				System.out.println("Le format de l'email est invalide...");
				return false;
			}
		}

		/**
		 * Vérification des téléphone fixe et portable
		 */
		pattern = Pattern.compile(masqueTel);
		controler = pattern.matcher(personne.getTelFixe());
		if (!controler.matches()) {
			System.out.println("Le format du téléphone fixe est invalide...");
			return false;
		}

		if (personne.getTelPort() == "")
			personne.setTelPort(null);
		if (personne.getTelPort() != null) {
			controler = pattern.matcher(personne.getTelPort());
			if (!controler.matches()) {
				System.out.println("Le format du téléphone portable est invalide...");
				return false;
			}
		}

		return true;
	}

	/**
	 * Méthode permettant d'ajouter une personne dans la BD Pour être ajoutée,
	 * les informations de la personne doivent être valides et la personne ne
	 * doit pas déjà exister dans la base (sinon levée d'une
	 * IllegalArgumentException).
	 * 
	 * @param personne
	 */
	public static long ajoutPersonne(Personne personne) {
		// Vérification de la validité des informations
		if (verifierInformations(personne)) {
			Personne pers;

			DAOFactory co = DAOFactory.getInstance();
			PersonneDAO personneDAO = co.getPersonneDAO();

			// Vérification de l'inexistance de la personne dans la base
			if (personneDAO.exist(personne) == null) {
				pers = personneDAO.create(personne);
				if (pers == null) {
					System.out.println("Une erreur est survenue lors de l'insertion...");
					return -1;
				} else {
					System.out.println("La personne a bien été ajoutée.");
					return pers.getIdPersonne();
				}
			} else {
				throw new IllegalArgumentException("La personne existe déjà dans la base...");
			}

		} else {
			System.out.println("Les informations de la personne ne sont pas valides...");
			return -1;
		}
	}

	public static Personne exist(Personne personne) {
		DAOFactory co = DAOFactory.getInstance();
		PersonneDAO personneDAO = co.getPersonneDAO();
		return personneDAO.exist(personne);
	}

	/**
	 * Méthode permettant de modifier les informations d'une personne dans la
	 * BD. Pour être modifiée, les informations de la personne doivent être
	 * valides et la personne doit exister dans la base (sinon levée d'une
	 * IllegalArgumentException).
	 * 
	 * @param personne
	 * @return l'identifiant de la personne modifiée, -1 si la modification n'a
	 *         pas pu être réalisée
	 */
	public static long modifierPersonne(Personne personne) {
		// Vérification de la validité des informations
		if (PersonneCtrl.verifierInformations(personne)) {
			Personne pers;

			DAOFactory co = DAOFactory.getInstance();
			PersonneDAO personneDAO = co.getPersonneDAO();

			// Vérification de l'existance de la personne dans la BD
			if (personneDAO.get(personne.getIdPersonne()) != null) {
				pers = personneDAO.update(personne);
				if (pers == null) {
					System.out.println("Une erreur est survenue lors de la modification...");
					return -1;
				} else {
					System.out.println("La personne a bien été modifiée.");
					return pers.getIdPersonne();
				}
			} else {
				throw new IllegalArgumentException("La personne n'existe pas dans la base...");
			}
		} else {
			System.out.println("Les informations de la personne ne sont pas valides...");
			return -1;
		}
	}
	
	public static List<Personne> recupererToutesPersonnes() {
		List<Personne> listePersonnes = new ArrayList<Personne>();
		DAOFactory co = DAOFactory.getInstance();
		PersonneDAO personneDAO = co.getPersonneDAO();

		listePersonnes = personneDAO.getAll();

		return listePersonnes;
	}
}

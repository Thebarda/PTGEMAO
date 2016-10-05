package fr.gemao.ctrl;

import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.Adresse;
import fr.gemao.sql.AdresseDAO;
import fr.gemao.sql.DAOFactory;

public final class AdresseCtrl {

	private AdresseCtrl() {
	}

	public static List<Adresse> getListAdresses() {
		DAOFactory factory = DAOFactory.getInstance();
		AdresseDAO adresseDAO = factory.getAdresseDAO();

		return adresseDAO.getAll();
	}

	/**
	 * Méthode permettant de vérifier les informations d'une adresse
	 * 
	 * @param adresse
	 * @return true si les informations sont valides, false sinon
	 */
	public static boolean verifierInformations(Adresse adresse) {
		// Vérification de l'idCommune
		if (adresse.getCommune() == null) {
			System.out.println("L'objet commune ne doit pas être null...");
			return false;
		}

		return true;
	}

	/**
	 * Méthode permettant d'ajouter une adresse dans la BD. Pour être ajoutée,
	 * les informations de l'adresse doivent être valides et l'adresse ne doit
	 * pas déjà exister dans la base (sinon levée d'une
	 * IllegalArgumentException).
	 * 
	 * @param adresse
	 */
	public static void ajoutAdresse(Adresse adresse) {
		// Vérification de la validité des informations
		if (verifierInformations(adresse)) {
			Adresse adr;

			DAOFactory co = DAOFactory.getInstance();
			AdresseDAO adresseDAO = co.getAdresseDAO();

			// Vérification de l'inexistance de l'adresse dans la base
			adr = adresseDAO.exist(adresse);
			if (adr == null) {
				adr = adresseDAO.create(adresse);
				if (adr != null) {
					adresse.setIdAdresse(adr.getIdAdresse());
				}
			} else {
				adresse.setIdAdresse(adr.getIdAdresse());
			}
		}

	}

	/**
	 * Méthode permettant de récupérer une adresse selon son identifiant
	 * 
	 * @param id
	 * @return l'adresse dont l'identifiant est idAdresse, null si l'adresse
	 *         n'existe pas dans la base
	 */
	public static Adresse recupererAdresse(int idAdresse) {
		DAOFactory co = DAOFactory.getInstance();
		AdresseDAO adresseDAO = co.getAdresseDAO();

		Adresse adresse = adresseDAO.get(idAdresse);

		return adresse;
	}

	/**
	 * Méthode permettant de récupérer l'ensemble des adresses
	 * 
	 * @return la liste des adresses de la BD
	 */
	public static List<Adresse> recupererTousAdresses() {
		List<Adresse> listeAdresses = new ArrayList<Adresse>();
		DAOFactory co = DAOFactory.getInstance();
		AdresseDAO adresseDAO = co.getAdresseDAO();

		listeAdresses = adresseDAO.getAll();

		return listeAdresses;
	}
}

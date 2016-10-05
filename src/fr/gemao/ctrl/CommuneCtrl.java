package fr.gemao.ctrl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.gemao.entity.Commune;
import fr.gemao.sql.CommuneDAO;
import fr.gemao.sql.DAOFactory;

public final class CommuneCtrl {

	private CommuneCtrl() {

	}

	public static ArrayList<String> getListNomCommune() {
		DAOFactory factory = DAOFactory.getInstance();
		CommuneDAO commuenDAO = factory.getCommuneDAO();

		List<Commune> listCommune = commuenDAO.getAll();
		ArrayList<String> nomCommune = new ArrayList<>();

		for (Commune c : listCommune) {
			// Pour être directement ajouté dans les jsp
			nomCommune.add(c.getNomCommune());
		}

		return nomCommune;
	}

	public static List<Commune> getListCommunes() {
		DAOFactory factory = DAOFactory.getInstance();
		CommuneDAO commuenDAO = factory.getCommuneDAO();

		return commuenDAO.getAll();
	}

	/**
	 * Méthode permettant de récupérer une commune selon son identifiant
	 * 
	 * @param id
	 * @return la commune dont l'identifiant est idCommune, null si la commune
	 *         n'existe pas
	 */
	public static Commune recupererCommune(int idCommune) {
		DAOFactory co = DAOFactory.getInstance();
		CommuneDAO communeDAO = co.getCommuneDAO();

		Commune commune = communeDAO.get(idCommune);

		return commune;
	}

	/**
	 * Méthode permettant de récupérer l'ensemble des communes
	 * 
	 * @return la liste des communes de la BD
	 */
	public static List<Commune> recupererTousCommunes() {
		List<Commune> listeCommunes = new ArrayList<Commune>();
		DAOFactory co = DAOFactory.getInstance();
		CommuneDAO communeDAO = co.getCommuneDAO();

		listeCommunes = communeDAO.getAll();

		return listeCommunes;
	}

	/**
	 * Méthode permettant de vérifier les informations d'une commune avant ajout
	 * 
	 * @param commune
	 * @return true si les informations sont valides, false sinon
	 */
	public static boolean verifierInformations(Commune commune) {
		String masque;
		Pattern pattern;
		Matcher controler;

		// Vérification du code postal
		if (commune.getCodePostal() <= 0) {
			System.out.println("Le code postal doit être strictement positif...");
			return false;
		}

		// Vérification du nom de commune
		masque = "^[A-Za-z âäàéèëêîïìôöòûüùÿ\\-]+$";
		pattern = Pattern.compile(masque);
		controler = pattern.matcher(commune.getNomCommune());
		if (!controler.matches()) {
			System.out.println("Le format du nom de la commune est invalide...");
			return false;
		}

		return true;
	}

	/**
	 * Méthode permettant d'ajouter une commune dans la BD. Pour être ajoutée,
	 * les informations de la commune doivent être valides et la commune ne doit
	 * pas déjà exister dans la base (sinon levée d'une
	 * IllegalArgumentException).
	 * 
	 * @param commune
	 */
	public static void ajoutCommune(Commune commune) {
		// Vérification de la validité des informations
		if (verifierInformations(commune)) {
			Commune com;

			DAOFactory co = DAOFactory.getInstance();
			CommuneDAO communeDAO = co.getCommuneDAO();

			// Vérification de l'inexistance de la commune dans la base
			com = communeDAO.existNomCodePostal(commune);
			if (com == null) {
				com = communeDAO.create(commune);
				if (com != null) {
					commune.setIdCommune(com.getIdCommune());
				}
			} else {
				commune.setIdCommune(com.getIdCommune());
				commune.setAvantage(com.isAvantage());
			}
		}
	}

}

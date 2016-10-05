package fr.gemao.ctrl.materiel;

import java.util.List;

import fr.gemao.entity.materiel.Marque;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.materiel.MarqueDAO;

public class MarqueCtrl {
	/**
	 * Permet d'ajouter une marque dans la base de données.
	 * 
	 * @param nomMarque
	 *            le nom de la marque a rajouter dans la base.
	 */
	public static boolean ajouterMarque(String nomMarque) {
		if (nomMarque == null) {
			throw new NullPointerException(
					"Le nom de la marque ne peut etre vide.");
		}

		if (nomMarque == "") {
			throw new IllegalArgumentException(
					"Le nom de la marque doit etre renseigne");
		}

		Marque marque = new Marque(0, nomMarque);
		new MarqueDAO(DAOFactory.getInstance()).create(marque);
		return true;
	}

	/**
	 * Permet de supprimer une marque de la base.
	 * 
	 * @param nomMarque
	 *            le nom de la marque a supprimer.
	 */
	public static boolean supprimerMarque(String nomMarque) {
		if (nomMarque == null) {
			throw new NullPointerException(
					"Le nom de la marque ne peut etre vide.");
		}
		if (nomMarque == "") {
			throw new IllegalArgumentException(
					"Le nom de la marque doit etre renseigne");
		}

		MarqueDAO marquedao = new MarqueDAO(DAOFactory.getInstance());

		Marque marque = marquedao.get(nomMarque);

		marquedao.delete(marque);
		return true;
	}

	/**
	 * permet la modification du nom d'une marque
	 * 
	 * @param marque
	 *            la Marque a modifier avec son nouveau nom
	 */
	public static boolean updateMarque(Marque marque) {
		if (marque.getIdMarque() <= 0) {
			throw new IllegalArgumentException("Id invalide");
		}

		if (marque.getNomMarque() == null) {
			throw new NullPointerException("Nom incomplet");
		}

		if (marque.getNomMarque() == "") {
			throw new IllegalArgumentException("Nom incomplet");
		}
		MarqueDAO marqueDAO = new MarqueDAO(DAOFactory.getInstance());

		marqueDAO.update(marque);
		return true;

	}

	/**
	 * permet de recuperer une marque
	 * 
	 * @param idMarque
	 *            id de la marque a recuperer
	 * @return la marque recuperer grace a son id
	 */
	public static Marque recupererMarque(int idMarque) {

		if (idMarque <= 0) {
			throw new IllegalArgumentException("Id invalide");
		}
		MarqueDAO marqueDAO = new MarqueDAO(DAOFactory.getInstance());

		return marqueDAO.get(idMarque);
	}

	public static List<Marque> recupererToutesMarques() {
		MarqueDAO marqueDAO = new MarqueDAO(DAOFactory.getInstance());

		return marqueDAO.getAll();
	}
}

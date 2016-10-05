package fr.gemao.ctrl.materiel;

import java.util.List;

import fr.gemao.entity.materiel.Fournisseur;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.materiel.FournisseurDAO;

public class FournisseurCtrl {

	/**
	 * Permet d'ajouter un fournisseur dans la base.
	 * 
	 * @param nomFournisseur
	 *            le nom du fournisseur a ajouter.
	 */
	public static boolean ajoutFournisseur(String nomFournisseur) {
		if (nomFournisseur == null) {
			throw new NullPointerException(
					"Le nom du fournisseur ne doit pas etre null");
		}
		if (nomFournisseur == "") {
			throw new NullPointerException(
					"Le nom du fournisseur ne doit pas etre vide");
		}
		Fournisseur fournisseur = new Fournisseur(0, nomFournisseur);
		new FournisseurDAO(DAOFactory.getInstance()).create(fournisseur);
		return true;
	}

	/**
	 * Permet de supprimer un fournisseur via son nom.
	 * 
	 * @param nomFournisseur
	 *            le nom du fournisseur a supprimer.
	 */
	public static boolean supprimerFournisseur(String nomFournisseur) {
		if (nomFournisseur == null) {
			throw new NullPointerException(
					"Le nom du fournisseur ne doit pas etre null");
		}
		if (nomFournisseur == "") {
			throw new NullPointerException(
					"Le nom du fournisseur ne doit pas etre vide");
		}

		FournisseurDAO fournisseurDAO = new FournisseurDAO(
				DAOFactory.getInstance());
		List<Fournisseur> fournisseurs = fournisseurDAO.getAll();

		for (Fournisseur fournisseur : fournisseurs) {
			if (fournisseur.getNomFournisseur().equals(nomFournisseur)) {
				fournisseurDAO.delete(fournisseur);
				return true;
			}
		}
		return false;
	}

	/**
	 * Permet de modifier le nom d'un fournisseur.
	 * 
	 * @param fournisseur
	 *            le fournisseur avec son nouveau nom.
	 */
	public boolean modifierEtat(Fournisseur fournisseur) {
		if (fournisseur.getIdFournisseur() <= 0) {
			throw new IllegalArgumentException("id invalide");
		}

		if (fournisseur.getNomFournisseur() == null) {
			throw new NullPointerException("Nom null");
		}

		if (fournisseur.getNomFournisseur() == "") {
			throw new IllegalArgumentException("Nom incorrect");
		}

		FournisseurDAO fournisseurDAO = new FournisseurDAO(
				DAOFactory.getInstance());

		fournisseurDAO.update(fournisseur);
		return true;
	}

	/**
	 * Permet de recuperer un fournisseur avec son id.
	 * 
	 * @param idEtat
	 *            id du fournisseur à récuperer.
	 * @return le fournisseur correspondant à l'id reçu en parametre.
	 */
	public static Fournisseur recupererFournisseur(int idFournisseur) {
		if (idFournisseur <= 0) {
			throw new IllegalArgumentException("idEtat invalide");
		}

		FournisseurDAO fournisseurDAO = new FournisseurDAO(
				DAOFactory.getInstance());

		return fournisseurDAO.get(idFournisseur);
	}

	/**
	 * Retourne la liste des fournisseurs.
	 * 
	 * @return la liste des fournisseurs.
	 */
	public static List<Fournisseur> getListeFournisseur() {
		FournisseurDAO fournisseurDAO = new FournisseurDAO(
				DAOFactory.getInstance());

		return fournisseurDAO.getAll();
	}
}

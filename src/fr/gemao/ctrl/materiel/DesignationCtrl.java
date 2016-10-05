package fr.gemao.ctrl.materiel;

import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.materiel.Designation;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.materiel.DesignationDAO;

public class DesignationCtrl {
	public DesignationCtrl() {

	}

	/**
	 * Permet d'ajouter une designation dans la base.
	 * 
	 * @param idDesignation
	 *            l'id de la designation a rajouter dans la base.
	 * @param libelle
	 *            Libelle de la designation a ajouter.
	 */
	public static boolean ajoutDesignation(String libelle) {
		if (libelle == null) {
			throw new NullPointerException("Le Libelle ne doit pas etre null");
		}
		if (libelle == "") {
			throw new NullPointerException("Le Libelle ne doit pas etre vide");
		}
		Designation designation = new Designation(0, libelle);

		new DesignationDAO(DAOFactory.getInstance()).create(designation);
		
		return true;
	}

	/**
	 * Permet de supprimer une designation.
	 * 
	 * @param libelle
	 *            le libelle de la designation a supprimer.
	 */
	public static boolean supprimerDesignation(String libelle) {
		if (libelle == null) {
			throw new NullPointerException(
					"Le nom du reparateur ne doit pas etre null");
		}

		if (libelle == "") {
			throw new IllegalArgumentException(
					"Le nom du reparateur doit etre renseigne");
		}

		DesignationDAO desDAO = new DesignationDAO(DAOFactory.getInstance());

		List<Designation> designations = desDAO.getAll();
		for (Designation designation : designations) {
			if (designation.getLibelleDesignation().equals(libelle)) {
				desDAO.delete(designation);
				return true;
			}
		}
		return false;
	}

	/**
	 * Permet l'update du libelle d'une designation
	 * 
	 * @param designation
	 *            La designation avec le nouveau libelle
	 */
	public static boolean modifierDesignationCtrl(Designation designation) {
		if (designation.getIdDesignation() <= 0) {
			throw new IllegalArgumentException("Id incorrect");
		}

		if (designation.getLibelleDesignation() == null) {
			throw new NullPointerException("Libelle invalide");
		}

		if (designation.getLibelleDesignation() == "") {
			throw new IllegalArgumentException("Libelle incorrecte");
		}
		DesignationDAO desDAO = new DesignationDAO(DAOFactory.getInstance());

		desDAO.update(designation);
		
		return true;

	}

	/**
	 * Permet de recuperer une designation
	 * 
	 * @param idDesignation
	 *            id de la designation a recuperer
	 * @return la designation qui a pour id, idDesignation
	 */
	public static Designation recupererDesignationCtrl(int idDesignation) {
		if (idDesignation <= 0) {
			throw new IllegalArgumentException("Id invalide");
		}
		DesignationDAO desDAO = new DesignationDAO(DAOFactory.getInstance());

		return desDAO.get(idDesignation);

	}

	public static List<Designation> recupererToutesDesignations() {
		List<Designation> listeDesignation = new ArrayList<Designation>();
		DesignationDAO designationDAO = new DesignationDAO(DAOFactory.getInstance());
		
		listeDesignation = designationDAO.getAll();
		
		return listeDesignation;
	}
}
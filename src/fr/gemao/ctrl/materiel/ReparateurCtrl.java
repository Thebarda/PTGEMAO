package fr.gemao.ctrl.materiel;

import java.util.List;

import fr.gemao.entity.materiel.Reparateur;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.materiel.ReparateurDAO;

public class ReparateurCtrl {

	/**
	 * Permet d'ajouter un reparateur dans la base.
	 * 
	 * @param nom
	 *            le nom du reparateur a rajouter dans la base.
	 */
	public void ajoutReparateur(String nom) {
		if (nom == null) {
			throw new NullPointerException(
					"Le nom du reparateur ne doit pas etre null");
		}

		if (nom == "") {
			throw new IllegalArgumentException(
					"Le nom du reparateur doit etre renseigne");
		}

		Reparateur rep = new Reparateur(0, nom);
		new ReparateurDAO(DAOFactory.getInstance()).create(rep);
	}

	/**
	 * Supprime le Reparateur portant le nom passé en parametre. Si il y a
	 * plusieur Reparateurs, seul le premier est supprimé.
	 * 
	 * @param nom
	 *            le nom du réparateur à supprimer.
	 */
	public void supprimerReparateur(String nom) {
		if (nom == null) {
			throw new NullPointerException(
					"Le nom du reparateur ne doit pas etre null");
		}

		if (nom == "") {
			throw new IllegalArgumentException(
					"Le nom du reparateur doit etre renseigne");
		}

		ReparateurDAO repDAO = new ReparateurDAO(DAOFactory.getInstance());

		List<Reparateur> reps = repDAO.getAll();
		for (Reparateur rep : reps) {
			if (rep.getNom().equals(nom)) {
				repDAO.delete(rep);
				break;
			}
		}
	}
	/**
	 * permet de modifier le nom d'un reparateur
	 * 
	 * @param reparateur
	 * 		le reparateur avec son nouveau nom
	 */
	public void modifierReparateur(Reparateur reparateur){
		if(reparateur.getIdReparateur() <=0){
			throw new IllegalArgumentException("Id incorrect");
		}
		
		if(reparateur.getNom() == null){
			throw new NullPointerException("Nom non precisé");
		}
		
		if(reparateur.getNom() == ""){
			throw new IllegalArgumentException("Nom incomplet");
		}
		ReparateurDAO repDAO = new ReparateurDAO(DAOFactory.getInstance());
		
		repDAO.update(reparateur);
		
	}
	/**
	 * Permet de recuperer un Reparateur
	 * @param idRep
	 * 		id du reparateur a recuperer
	 * @return
	 * 		le reparateur correspondant a idRep
	 */
	public Reparateur recupererReparateur(int idRep){
		if(idRep <=0){
			throw new IllegalArgumentException("id invalide");
			
		}
		ReparateurDAO repDAO = new ReparateurDAO(DAOFactory.getInstance());
		
		return repDAO.get(idRep);
	}
}

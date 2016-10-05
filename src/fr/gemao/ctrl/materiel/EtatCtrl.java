package fr.gemao.ctrl.materiel;

import java.util.List;

import fr.gemao.entity.materiel.Etat;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.materiel.EtatDAO;


public class EtatCtrl {
	/**
	 * Permet d'ajouter un etat dans la base
	 * 
	 * @param libelle
	 * 		libelle de l'etat a ajouter
	 */
	public static boolean ajoutEtat(String libelle){
		if(libelle == null){
			throw new NullPointerException("Le Libelle ne doit pas etre null");
		}
		if(libelle == ""){
			throw new NullPointerException("Le Libelle ne doit pas etre vide");
		}
		Etat etat = new Etat(0,libelle);
		
		new EtatDAO(DAOFactory.getInstance()).create(etat);
		return true;
	}
	/**
	 * Permet de supprimer un etat dans la base
	 * @param libelle
	 * 		libelle de l'etat a retirer.
	 */
	public static boolean supprimerEtat(String libelle){
		if(libelle == null){
			throw new NullPointerException("Le Libelle ne doit pas etre null");
		}
		if(libelle == ""){
			throw new NullPointerException("Le Libelle ne doit pas etre vide");
		}
		
		EtatDAO etatDAO = new EtatDAO(DAOFactory.getInstance());
		List<Etat> etats = etatDAO.getAll();
		for(Etat etat : etats){
			if(etat.getLibelleEtat().equals(libelle)){
				etatDAO.delete(etat);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Permet de modifier le libelle d'un etat
	 * 
	 * @param etat
	 * 		l'etat avec son nouveau libelle
	 */
	public static boolean modifierEtat(Etat etat){
		if(etat.getIdEtat() <= 0){
			throw new IllegalArgumentException("id invalide");
		}
		
		if(etat.getLibelleEtat() == null){
			throw new NullPointerException("Libelle incomplet");
		}
		
		if(etat.getLibelleEtat() == ""){
			throw new IllegalArgumentException("Libelle incorrect");
		}
		
		EtatDAO etatDAO = new EtatDAO(DAOFactory.getInstance());
		
		etatDAO.update(etat);
		return true;
		
	}
	/**
	 * permet de recuperer un etat avec son id
	 * 
	 * @param idEtat
	 * 		id de l'etat a recuperer
	 * @return
	 * 		L'etat correspondant a l'idEtat
	 */
	public static Etat recupererEtat(int idEtat){
		if(idEtat <= 0){
			throw new IllegalArgumentException("idEtat invalide");
		}
		
		EtatDAO etatDAO = new EtatDAO(DAOFactory.getInstance());
		
		return etatDAO.get(idEtat);
	}
	
	public static List<Etat> getListeEtat(){
		EtatDAO etatDAO = new EtatDAO(DAOFactory.getInstance());
		
		return etatDAO.getAll();
	}
}

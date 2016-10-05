package fr.gemao.ctrl.materiel;

import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.materiel.Categorie;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.materiel.CategorieDAO;

public class CategorieCtrl {
	/**
	 * Permet d'ajouter une categorie dans la base de donees
	 * 
	 * @param libelle
	 *            le libelle de la categorie a rajouter.
	 */
	public static boolean ajoutCategorie(String libelle) {
		if (libelle == null) {
			throw new NullPointerException("Le libelle ne peut etre null");
		}
		if (libelle == "") {
			throw new NullPointerException("Le libelle ne doit pas etre vide");

		}
		Categorie categorie = new Categorie(0,libelle, false);
		new CategorieDAO(DAOFactory.getInstance()).create(categorie);
		
		return true;
	}

	/**
	 * Permet de supprimer une categorie de la base. Si plusieurs categories
	 * sont presentes avec le meme libelle, seule la premiere sera supprimee.
	 * 
	 * @param libelle
	 *            le libelle de la categorie a supprimer.
	 */
	public static boolean supprimerCategorie(String libelle) {

		if (libelle == null) {
			throw new NullPointerException("Le libelle ne peut etre null");
		}
		if (libelle == "") {
			throw new NullPointerException("Le libelle ne doit pas etre vide");
		}

		CategorieDAO catdao = new CategorieDAO(DAOFactory.getInstance());

		List<Categorie> cats = catdao.getAll();
		for (Categorie cat : cats) {
			if (cat.getLibelleCat().equals(libelle)) {
				catdao.delete(cat);
				return true;
			}
		}
		return false;
	}

	/**
	 * Permet de modifier le libelle d'une categorie
	 * 
	 * @param categorie
	 *            la nouvelle Categorie
	 */
	public boolean modifierCategorie(Categorie categorie) {
		if (categorie.getIdCategorie() <= 0) {
			throw new IllegalArgumentException("ID invalide");
		}

		if (categorie.getLibelleCat() == null) {
			throw new NullPointerException("Libelle invalide");
		}

		if (categorie.getLibelleCat() == "") {
			throw new IllegalArgumentException("Libelle invalide");
		}

		CategorieDAO catdao = new CategorieDAO(DAOFactory.getInstance());

		catdao.update(categorie);
		
		return true;
		

	}
	/**
	 * permet de recuperer une categorie avec son id
	 * 
	 * @param idCategorie
	 * 		id de la categorie a recuperer
	 * @return
	 * 		la categorie qui corespond a idCategorie
	 */
	public static Categorie recupererCategorie(int idCategorie){
		if(idCategorie <=0 ){
			throw new IllegalArgumentException("id invalide");
		}
		CategorieDAO catdao = new CategorieDAO(DAOFactory.getInstance());

		return catdao.get(idCategorie);
		
	}
	
	public static List<Categorie> recupererToutesCategories(){
		List<Categorie> listeCategorie = new ArrayList<Categorie>();
		CategorieDAO categorieDAO = new CategorieDAO(DAOFactory.getInstance());
		
		listeCategorie = categorieDAO.getAll();
		
		return listeCategorie;
	}
}

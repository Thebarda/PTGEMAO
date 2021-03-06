package fr.gemao.ctrl.materiel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.materiel.Categorie;
import fr.gemao.entity.materiel.Designation;
import fr.gemao.entity.materiel.Etat;
import fr.gemao.entity.materiel.Fournisseur;
import fr.gemao.entity.materiel.Marque;
import fr.gemao.entity.materiel.Materiel;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.materiel.MaterielDAO;

/**
 * Contrôleur de la classe Materiel
 * @author Coco
 *
 */
public class MaterielCtrl {
	
	/**
	 * Constructeur
	 */
	public MaterielCtrl() {

	}

	/**
	 * Permet d'ajouter un matériel
	 * @param etat : l'état du matériel
	 * @param categorie : la catégorie
	 * @param marque : la marque
	 * @param designation : la désignation
	 * @param typeMat : le type de matériel
	 * @param numSerie : le numéro de série
	 * @param dateAchat : la date d'achat
	 * @param valeurAchat : la valeur d'achat
	 * @param valeurReap : la valeur de ré-approvisionnement
	 * @param deplacable : si le matériel est déplaçable ou non
	 * @param observation : l'observation
	 * @param louable : si le matériel est louable ou non
	 */
	public static void ajoutMateriel(Etat etat, Categorie categorie, Marque marque, Designation designation,Fournisseur fournisseur, String typeMat, String numSerie, Date dateAchat, float valeurAchat, float valeurReap, boolean deplacable, String observation, int quantite, boolean louable) {
		if (etat == null) {
			throw new NullPointerException("L'etat ne doit pas etre null");
		}
		
		if (categorie == null) {
			throw new NullPointerException("La categorie ne doit pas etre null");
		}
		
		if (marque == null) {
			throw new NullPointerException("la marque ne doit pas etre null");
		}
		
		if (designation == null) {
			throw new NullPointerException("La designation ne doit pas etre null");
		}
		if (fournisseur == null) {
			throw new NullPointerException("Le Fournisseur ne doit pas etre null");
		}
		
		if (typeMat == null) {
			throw new NullPointerException("le type de materiel ne doit pas etre null");
		}
		
		if (typeMat == ""){
			throw new IllegalArgumentException("le type doit etre rempli");
		}
		
		if (valeurAchat < 0.0) {
			throw new IllegalArgumentException("la valeur d'achat ne peut pas etre negative");
		}
		
		if (valeurReap < 0.0) {
			throw new IllegalArgumentException("la valeur ne peut pas etre negative");
		}
		
		Materiel materiel = new Materiel(null, etat, categorie, marque, designation,fournisseur, typeMat, numSerie, dateAchat, valeurAchat, valeurReap, deplacable, observation, quantite, louable);
		
		new MaterielDAO(DAOFactory.getInstance()).create(materiel);		
	}
	
	/**
	 * Récupérer le matériel par id
	 * @param idMateriel
	 * @return Materiel
	 */
	public Materiel recupererMateriel(int idMateriel) {
		if (idMateriel <= 0 ) {
			throw new IllegalArgumentException("Id incorrect");
		}
		
		MaterielDAO materielDAO = new MaterielDAO(DAOFactory.getInstance());
		
		return materielDAO.get(idMateriel);
	}
	
	/**
	 * Récupère tout le matériel de la bdd
	 * @return List<Materiel>
	 */
	public List<Materiel> recupererTousMateriels() {
		List<Materiel> listeMateriel = new ArrayList<Materiel>();
		MaterielDAO materielDAO = new MaterielDAO(DAOFactory.getInstance());
		
		listeMateriel = materielDAO.getAll();
		
		return listeMateriel;
	}
	
	/**
	 * Recupère les matériels en fonction d'une catégorie
	 * @param idCategorie
	 * @return List<Materiel>
	 */
	public static List<Materiel> recupererMaterielByCategorie(int idCategorie){
		List<Materiel> listMaterielByCategorie = new ArrayList<>();
		MaterielDAO materielDAO = new MaterielDAO(DAOFactory.getInstance());
		listMaterielByCategorie = materielDAO.getAllByCategorie(idCategorie);
		return listMaterielByCategorie;
	}
	
	/**
	 * Recupère les matériels louable
	 * @param idCategorie
	 * @return List<Materiel>
	 */
	public static List<Materiel> recupereMaterielLouable(int idCategorie){
		List<Materiel> listMaterielByCategorie = new ArrayList<>();
		MaterielDAO materielDAO = new MaterielDAO(DAOFactory.getInstance());
		listMaterielByCategorie = materielDAO.getLouableByCategorie(idCategorie);
		return listMaterielByCategorie;
	}
	
	/**
	 * Modifie la louabilité du matériel
	 * @param idMateriel
	 * @param estLouable
	 */
	public static void updateEstLouable(int idMateriel, int estLouable){
		MaterielDAO matDAO = new MaterielDAO(DAOFactory.getInstance());
		matDAO.updateEstLouable(idMateriel, estLouable);
	}
	
	/**
	 * Retourne le matériel en fonction de l'id
	 * @param idMateriel
	 * @return Materiel
	 */
	public static Materiel getMaterielById(int idMateriel){
		MaterielDAO matDAO = new MaterielDAO(DAOFactory.getInstance());
		return matDAO.getMaterielById(idMateriel);
	}
	
	/**
	 * Modifie le matériel
	 * @param materiel
	 */
	public void modifierMateriel(Materiel materiel) {
		if (materiel.getEtat() == null) {
			throw new NullPointerException("L'etat ne doit pas etre null");
		}
		
		if (materiel.getCategorie() == null) {
			throw new NullPointerException("La categorie ne doit pas etre null");
		}
		
		if (materiel.getMarque() == null) {
			throw new NullPointerException("la marque ne doit pas etre null");
		}
		
		if (materiel.getFournisseur() == null) {
			throw new NullPointerException("le fournisseur ne doit pas etre null");
		}
		
		if (materiel.getDesignation() == null) {
			throw new NullPointerException("La designation ne doit pas etre null");
		}
		
		if (materiel.getTypeMat() == null) {
			throw new NullPointerException("le type de materiel ne doit pas etre null");
		}
		
		if (materiel.getTypeMat() == "") {
			throw new IllegalArgumentException("le type doit etre rempli");
		}
		
		if (materiel.getValeurAchat() < 0.0) {
			throw new IllegalArgumentException("la valeur d'achat ne peut pas etre negative");
		}
		
		if (materiel.getValeurReap() < 0.0) {
			throw new IllegalArgumentException("la valeur ne peut pas etre negative");
		}
		
		MaterielDAO materielDAO = new MaterielDAO(DAOFactory.getInstance());
		
		materielDAO.update(materiel);
	}

	public static void updateEtat(int id, int etatFin) {
		MaterielDAO dao = new MaterielDAO(DAOFactory.getInstance());
		dao.updateEtat(id, etatFin);
	}
}
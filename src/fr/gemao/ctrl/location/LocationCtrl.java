package fr.gemao.ctrl.location;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.jolbox.bonecp.BoneCP;

import fr.gemao.entity.Personne;
import fr.gemao.entity.materiel.Etat;
import fr.gemao.entity.materiel.Location;
import fr.gemao.entity.materiel.Materiel;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.exception.DAOConfigurationException;
import fr.gemao.sql.location.LocationDAO;

/**
 * 
 * @author kayzen
 *
 */
public class LocationCtrl {
	private static final String FICHIER_PROPERTIES = "./fr/gemao/tarifs.properties";
	private static final String PROPERTY_CAUTION = "caution";
	private static final String PROPERTY_MONTANTLOCATIONINTERNE = "montantLocationInterne";
	private static final String PROPERTY_MONTANTLOCATIONEXTERNE = "montantLocationExterne";
	/**
	 * Permet de rajouter une Location dans la BdD. La date de fin de l'emprun
	 * est calculée automatiquement en fonction de la duree (en jours).
	 * 
	 * @param personne
	 *            la personne qui loue le materiel.
	 * @param materiel
	 *            le materiel loué.
	 * @param etatDebut
	 *            l'etat du materiel au début de la location.
	 * @param dateEmprunt
	 *            la date de debut de l'emprunt.
	 * @param duree
	 *            la durée de l'emprunt.
	 * @param montant
	 *            le montant de l'emprunt
	 */
	public static int ajouterLocation(String idPersonne, String idMateriel,
			String etatDebut, String dateEmprunt, String dateFin, float caution, float montant, String nomContrat) {
		if (idPersonne == null) {
			throw new NullPointerException("L'adherent ne peut etre null");
		}
		if (idMateriel == null) {
			throw new NullPointerException("Le materiel ne peut etre null");
		}
		if (etatDebut == null) {
			throw new NullPointerException("L'etat ne peut etre null");
		}
		if (dateEmprunt == null) {
			throw new NullPointerException(
					"La date d'emprunt ne peut etre null");
		}
		if (caution <= 0) {
			throw new IllegalArgumentException(
					"La caution doit etre strictement positive");
		}
		if (montant <= 0) {
			throw new IllegalArgumentException(
					"Le montant doit etre strictement positif");
		}
		if((nomContrat==null)||(nomContrat.equals(""))){
			throw new IllegalArgumentException(
					"Le nom du contrat ne doit pas etre null");
		}
		return new LocationDAO(DAOFactory.getInstance()).create(idPersonne, idMateriel,
				etatDebut, dateEmprunt, dateFin, caution, montant, nomContrat);
	}
	

	/**
	 * Permet de supprimer une location de la base de données. La personne et le
	 * materiel recus doivent etres issus de la base. Si plusieurs locations ont
	 * une meme personne ET un meme materiel loué (improbable), seul la premiere
	 * est supprimee.
	 * 
	 * @param personne
	 *            la personne ayant loué un materiel. Doit etre issu de la BdD.
	 * @param materiel
	 *            le materiel loué. Doit etre issu de la BdD.
	 * @throws ParseException 
	 */
	public static void supprimerLocation(Personne personne, Materiel materiel) throws ParseException {
		if (personne == null) {
			throw new NullPointerException("L'adherent ne peut etre null");
		}
		if (materiel == null) {
			throw new NullPointerException("Le materiel ne peut etre null");
		}

		LocationDAO locdao = new LocationDAO(DAOFactory.getInstance());

		List<Location> locs = locdao.getAll();
		for (Location loc : locs) {
			if (loc.getPersonne().equals(personne)
					&& loc.getMateriel().equals(materiel)) {
				locdao.delete(loc);
				break;
			}
		}
	}
	
	/**
	 * Retourne le nombre de location
	 * @return
	 */
	public static int getNbLocation(){
		LocationDAO locDAO = new LocationDAO(DAOFactory.getInstance());
		return locDAO.getNbLocation();
	}
	
	/**
	 * Retourne toutes les locations
	 * @return List<Location>
	 * @throws ParseException
	 */
	public static List<Location> getAll() {
		LocationDAO locDAO = new LocationDAO(DAOFactory.getInstance());
		return locDAO.getAll();
	}
	
	public static List<Location> getAllAll() {
		LocationDAO locDAO = new LocationDAO(DAOFactory.getInstance());
		return locDAO.getAllAll();
	}
	
	/**
	 * Récupère le tarif de la caution, du montant pour une location externe / interne via un fichier .properties
	 * @return Map<String, String>
	 */
	public static Map<String, String> recupereTarifsLocation(){
		Properties properties = new Properties();
		String caution;
		String montantLocationInterne;
		String montantLocationExterne;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream(FICHIER_PROPERTIES);

		if (fichierProperties == null) {
			throw new DAOConfigurationException("Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.");
		}
		
		try {
			properties.load(fichierProperties);
			caution = properties.getProperty(PROPERTY_CAUTION);
			montantLocationInterne = properties.getProperty(PROPERTY_MONTANTLOCATIONINTERNE);
			montantLocationExterne = properties.getProperty(PROPERTY_MONTANTLOCATIONEXTERNE);
		} catch (IOException e) {
			throw new DAOConfigurationException("Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e);
		}
		Map<String, String> tarif = new HashMap<>();
		tarif.put("caution", caution);
		tarif.put("montantLocationInterne", montantLocationInterne);
		tarif.put("montantLocationExterne", montantLocationExterne);
		return tarif;
	}


	public static String getTypeLocation(Location location) {
		LocationDAO locDAO = new LocationDAO(DAOFactory.getInstance());
		return locDAO.getTypeLocation(Integer.parseInt(""+location.getPersonne().getIdPersonne()));
	}


	public static List<Location> getLocsByYear(int year) {
		LocationDAO locDAO = new LocationDAO(DAOFactory.getInstance());
		return locDAO.getLocsByYear(year);
	}


	public static void updateRetourLocation(int id, String dateRetour, int etatFin) {
		LocationDAO locDAO = new LocationDAO(DAOFactory.getInstance());
		locDAO.updateRetourLocation(id, dateRetour, etatFin);
	}

	public static String getNomContratById(int id){
		LocationDAO locDAO = new LocationDAO(DAOFactory.getInstance());
		return locDAO.getNomContratById(id);
	}
}
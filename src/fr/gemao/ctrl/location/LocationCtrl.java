package fr.gemao.ctrl.location;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import fr.gemao.entity.Personne;
import fr.gemao.entity.materiel.Etat;
import fr.gemao.entity.materiel.Location;
import fr.gemao.entity.materiel.Materiel;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.location.LocationDAO;

/**
 * 
 * @author kayzen
 *
 */
public class LocationCtrl {
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
	public static void ajouterLocation(String idPersonne, String idMateriel,
			String etatDebut, String dateEmprunt, String dateFin, float caution, float montant) {
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

		new LocationDAO(DAOFactory.getInstance()).create(idPersonne, idMateriel,
				etatDebut, dateEmprunt, dateFin, caution, montant);
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
	 */
	public static void supprimerLocation(Personne personne, Materiel materiel) {
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
}

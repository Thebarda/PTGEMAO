package fr.gemao.entity.planning;

import java.util.Objects;

import fr.gemao.entity.Jour;
import fr.gemao.entity.cours.Salle;
import fr.gemao.entity.util.HeurePlanning;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public class Contrainte {

	private Integer idContrainte;
	private Integer idCreneau;
	private Integer idPlanning;
	private Salle salle;
	private Jour jour;
	private HeurePlanning heureDebut;
	private HeurePlanning heureFin;
	private String message;

	public Contrainte(Integer idContrainte, Integer idCreneau, Integer idPlanning, Salle salle, Jour jour,
			HeurePlanning heureDebut, HeurePlanning heureFin, String message) {
		if (heureDebut != null) {
			Objects.requireNonNull(heureFin, "S'il y a une heure de début, il doit y avoir une heure de fin");
		}

		if (heureFin != null) {
			Objects.requireNonNull(heureDebut, "S'il y a une heure de fin, il doit y avoir une heure de début");
		}

		this.idContrainte = idContrainte;
		this.idCreneau = idCreneau;
		this.idPlanning = idPlanning;
		this.salle = salle;
		this.jour = jour;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
		this.message = message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Méthode vérifiant les contraintes par rapport à des attributs externes
	 * 
	 * @param salle
	 * @param jour
	 * @param heureDebut
	 * @param heureFin
	 * 
	 * @return vrai si la contrainte est respectée, faux sinon (respectée = peut
	 *         être placé)
	 */
	public boolean estRespecte(Salle salle, Jour jour, HeurePlanning heureDebut, HeurePlanning heureFin) {
		boolean nonRespecte = true;

		if (this.salle != null && salle != null && nonRespecte) {
			nonRespecte = verifieSalle(salle);
		}
		if (this.jour != null && nonRespecte) {
			nonRespecte = verifieJour(jour);
		}
		if (this.heureDebut != null && nonRespecte) {
			nonRespecte = verifieHeure(heureDebut, heureFin);
		}

		return !nonRespecte;
	}

	private boolean verifieSalle(Salle salle) {
		return this.salle.equals(salle);
	}

	private boolean verifieJour(Jour jour) {
		return this.jour.equals(jour);
	}

	/**
	 * Vérifie si un intervalle (deux heures) sont superposées
	 * 
	 * @param heureDebut
	 * @param heureFin
	 * @return vrai si supérposées, faux si non
	 */
	private boolean verifieHeure(HeurePlanning heureDebut, HeurePlanning heureFin) {
		if (this.heureDebut.isInferieur(heureDebut)) {
			if (this.heureFin.isInferieur(heureDebut)) {
				return false;
			} else {
				return true;
			}
		} else {
			if (this.heureDebut.isInferieur(heureFin)) {
				return false;
			} else {
				return true;
			}
		}
	}

	public Integer getIdContrainte() {
		return idContrainte;
	}

	public Integer getIdCreneau() {
		return idCreneau;
	}

	public Salle getSalle() {
		return salle;
	}

	public Jour getJour() {
		return jour;
	}

	public HeurePlanning getHeureDebut() {
		return heureDebut;
	}

	public HeurePlanning getHeureFin() {
		return heureFin;
	}

	public String getMessage() {
		return message;
	}

	public void setIdCreneau(Integer creneau) {
		this.idCreneau = creneau;

	}

	public Integer getIdPlanning() {
		return idPlanning;
	}
	
	public Boolean isGlobale() {
		return this.idPlanning != null;
	}

}
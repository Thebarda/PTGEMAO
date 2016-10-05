package fr.gemao.entity.planning;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import fr.gemao.sql.util.DateUtil;

/**
 * Classe définissant un planning (limité dans le temps)
 * 
 * @author G5
 *
 */
public class Planning implements Cloneable {

	private Integer idPlanning;
	private String nomPlanning;
	private List<Creneau> listeCreneau;
	private Date dateDeb;
	private Date dateFin;
	private int semaineAnnee;
	private boolean valide;
	private boolean archive;

	public Planning(Integer idPlanning, String nomPlanning, List<Creneau> listeCreneau, Date dateDeb, Date dateFin) {
		this(idPlanning, nomPlanning, listeCreneau, dateDeb, dateFin, Calendar.WEEK_OF_YEAR);
		this.valide = false;
	}

	public Planning(Integer idPlanning, String nomPlanning, List<Creneau> listeCreneau, Date dateDeb, Date dateFin,
			int semaineAnnee) {
		this.idPlanning = idPlanning;
		this.nomPlanning = nomPlanning;
		this.dateDeb = dateDeb;
		this.dateFin = dateFin;
		this.listeCreneau = listeCreneau;
		this.semaineAnnee = semaineAnnee;
		this.valide = false;
		this.archive = false;
	}

	public Planning(Integer idPlanning, String nomPlanning, List<Creneau> listeCreneau, Date dateDeb, Date dateFin,
			int semaineAnnee, boolean valide) {
		this(idPlanning, nomPlanning, listeCreneau, dateDeb, dateFin, semaineAnnee);
		this.valide = valide;
	}

	public Planning(Integer idPlanning, String nomPlanning, List<Creneau> creneaux, Date dateDeb, Date dateFin,
			int semaineAnnee, boolean valide, boolean archive) {
		this(idPlanning, nomPlanning, creneaux, dateDeb, dateFin, semaineAnnee);
		this.valide = valide;
		this.archive = archive;
	}

	public boolean isPair() {
		return (this.semaineAnnee % 2) == 0;
	}

	public String definitionPlanning() {
		return nomPlanning + " du " + DateUtil.toFrenchDate(dateDeb) + " au " + DateUtil.toFrenchDate(dateFin);
	}

	/**
	 * Liste des getteurs
	 */

	public Integer getIdPlanning() {
		return idPlanning;
	}

	public String getNomPlanning() {
		return nomPlanning;
	}

	public List<Creneau> getListeCreneau() {
		return listeCreneau;
	}

	public Date getDateDeb() {
		return dateDeb;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public int getSemaineAnnee() {
		return semaineAnnee;
	}

	/**
	 * setteurs
	 */

	public void setIdPlanning(int idPlanning) {
		if (idPlanning < 0) {
			throw new IllegalArgumentException("L'id ne peut être négatif");
		}

		this.idPlanning = idPlanning;
	}

	@Override
	public Planning clone() {
		List<Creneau> listeClonee = new ArrayList<>();
		for (final Creneau c : listeCreneau) {
			listeClonee.add(c.clone());
		}
		return new Planning(null, nomPlanning, listeClonee, dateDeb, dateFin);
	}

	public void setValide(boolean valide) {
		this.valide = valide;
	}

	public boolean getValide() {
		return this.valide;
	}

	public boolean isArchive() {
		return this.archive;
	}

	public void setNomPlanning(String nomPlanning) {
		this.nomPlanning = nomPlanning;
	}
	public void escapeCharacters() {
		for(Creneau c : listeCreneau){
			c.setLibelle(StringEscapeUtils.escapeEcmaScript(c.getLibelle()));
			for (Contrainte con : c.getContraintes()) {
				con.setMessage(StringEscapeUtils.escapeEcmaScript(con.getMessage()));
			}
		}
	}
	public void unescapeCharacters() {
		for(Creneau c : listeCreneau){
			c.setLibelle(StringEscapeUtils.unescapeEcmaScript(c.getLibelle()));
			for (Contrainte con : c.getContraintes()) {
				con.setMessage(StringEscapeUtils.unescapeEcmaScript(con.getMessage()));
			}
		}
	}
}

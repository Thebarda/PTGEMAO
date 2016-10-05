/**
 * 
 */
package fr.gemao.ctrl.planning;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;

import fr.gemao.entity.planning.Contrainte;
import fr.gemao.entity.planning.Creneau;
import fr.gemao.entity.planning.Planning;
import fr.gemao.entity.planning.PlanningPDF;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.planning.PlanningDAO;
/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public final class PlanningCtrl {
	private PlanningCtrl() {

	}

	public static Planning create(Planning planning) {
		return DAOFactory.getInstance().getPlanningDAO().create(planning);
	}

	public static Planning getPlanning(Integer id) {
		Planning p = DAOFactory.getInstance().getPlanningDAO().get(id);
		p.escapeCharacters();
		return p;
	}

	public static void validerPlanning(Planning p) {
		DAOFactory.getInstance().getPlanningDAO().valider(p);
	}

	/**
	 * @param planning
	 * @return
	 */
	public static Planning update(Planning planning) {
		planning.unescapeCharacters();
		return DAOFactory.getInstance().getPlanningDAO().update(planning);

	}

	public static List<Planning> getAll() {
		return DAOFactory.getInstance().getPlanningDAO().getAll();
	}

	public static List<Planning> getAllEnCours() {
		return DAOFactory.getInstance().getPlanningDAO().getAllEnCours();
	}

	public static List<Planning> getAllArchive() {
		return DAOFactory.getInstance().getPlanningDAO().getAllArchive();
	}

	public static void dupliquer(Planning p) {
		new PlanningDAO(DAOFactory.getInstance()).dupliquer(p);
	}

	// TODO utiliser un prédicat
	public static Planning getPlanningDansListe(Integer id, List<Planning> liste) {
		for (final Planning p : liste) {
			if (p.getIdPlanning().equals(id)) {
				return p;
			}
		}
		return null;
	}

	/**
	 * @param p
	 *            planning to delete
	 * 
	 */
	public static void delete(Planning p) {
		ContrainteCtrl.deleteByIdPlanning(p.getIdPlanning());
		CreneauCtrl.deleteByIdPlanning(p.getIdPlanning());
		DAOFactory.getInstance().getPlanningDAO().delete(p);
	}

	public static void genererPDF(Planning planning, String path) {
		PlanningPDF pdf = new PlanningPDF();

		pdf.createPdf(planning, path);
	}

	public static void archiver(Planning p) {
		DAOFactory.getInstance().getPlanningDAO().archiver(p);
	}

	public static void desarchiver(Planning p) {
		DAOFactory.getInstance().getPlanningDAO().desarchiver(p);
	}

	/**
	 * @return
	 */
	public static List<Planning> getAllEnCoursLight() {
		return DAOFactory.getInstance().getPlanningDAO().getAllCaracteristiques();
	}

}

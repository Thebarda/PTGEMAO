/**
 * 
 */
package fr.gemao.ctrl.planning;

import java.util.List;

import fr.gemao.entity.cours.Prof;
import fr.gemao.sql.DAOFactory;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public final class ProfCtrl {

	private ProfCtrl() {

	}

	public static Prof create(Prof p) {
		return DAOFactory.getInstance().getProfDAO().create(p);
	}

	/**
	 * Retourne la liste de tous les profs enregistrés en base de données.
	 * 
	 * @return {@link List}
	 */
	public static List<Prof> getAll() {
		return DAOFactory.getInstance().getProfDAO().getAll();
	}

	/**
	 * @param idProf
	 * @return
	 */
	public static Prof get(Integer idProf) {
		return DAOFactory.getInstance().getProfDAO().get(idProf);
	}
}

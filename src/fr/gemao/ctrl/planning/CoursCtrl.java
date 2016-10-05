/**
 * 
 */
package fr.gemao.ctrl.planning;

import java.util.List;

import fr.gemao.entity.cours.Cours;
import fr.gemao.sql.DAOFactory;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public final class CoursCtrl {

	private CoursCtrl() {

	}

	public static Cours create(Cours c) {
		return DAOFactory.getInstance().getCoursDAO().create(c);
	}

	public static Cours update(Cours c) {
		return DAOFactory.getInstance().getCoursDAO().update(c);
	}

	public static void delete(Cours c) {
		DAOFactory.getInstance().getCoursDAO().delete(c);
	}

	public static Cours get(Integer idCours) {
		return DAOFactory.getInstance().getCoursDAO().get(idCours);
	}

	public static List<Cours> getCoursByPlanning(Integer idPlanning) {
		return DAOFactory.getInstance().getCoursDAO().getByIdPlanning(idPlanning);
	}

	/**
	 * Retourne la liste de tous les cours enregistrés en base de données.
	 * 
	 * @return {@link List}
	 */
	public static List<Cours> getAll() {
		return DAOFactory.getInstance().getCoursDAO().getAll();
	}

	/**
	 * @param idDiscipline
	 * @param idProf
	 * @return
	 */
	public static Cours getByDisciplineAndProf(Integer idDiscipline, Integer idProf) {
		return DAOFactory.getInstance().getCoursDAO().getByDisciplineAndProf(idDiscipline, idProf);
	}
}

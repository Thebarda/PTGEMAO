package fr.gemao.ctrl.planning;

import java.util.List;

import fr.gemao.entity.cours.Salle;
import fr.gemao.sql.DAOFactory;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public final class SalleCtrl {

	private SalleCtrl() {

	}

	public static List<Salle> getAllSalles() {
		return DAOFactory.getInstance().getSalleDAO().getAll();
	}

	public static Salle getSalle(Integer idSalle) {
		return DAOFactory.getInstance().getSalleDAO().get(idSalle);
	}

	public static Salle create(Salle salle) {
		return DAOFactory.getInstance().getSalleDAO().create(salle);
	}

	public static Salle update(Salle salle) {
		return DAOFactory.getInstance().getSalleDAO().update(salle);
	}

	public static List<Salle> getAll() {
		return DAOFactory.getInstance().getSalleDAO().getAll();
	}

	public static void delete(Salle salle) {
		DAOFactory.getInstance().getSalleDAO().delete(salle);
	}
}

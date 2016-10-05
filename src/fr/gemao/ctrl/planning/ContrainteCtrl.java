package fr.gemao.ctrl.planning;

import java.util.List;

import fr.gemao.entity.planning.Contrainte;
import fr.gemao.entity.planning.Planning;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.exception.DAOException;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public final class ContrainteCtrl {

	private ContrainteCtrl() {

	}

	public static List<Contrainte> getAll() {
		return DAOFactory.getInstance().getContrainteDAO().getAll();
	}

	public static List<Contrainte> getAllByIdCreneau(Integer idCreneau) {
		return DAOFactory.getInstance().getContrainteDAO().getAllByIdCreneau(idCreneau);
	}

	public static List<Contrainte> getAllByIdPlanning(Integer idPlanning) {
		return DAOFactory.getInstance().getContrainteDAO().getAllByIdPlanning(idPlanning);
	}

	public static Contrainte get(Integer idContrainte) {
		return DAOFactory.getInstance().getContrainteDAO().get(idContrainte);
	}

	public static Contrainte create(Contrainte c) {
		return DAOFactory.getInstance().getContrainteDAO().create(c);
	}

	public static Contrainte update(Contrainte c) {
		return DAOFactory.getInstance().getContrainteDAO().update(c);
	}

	public static void delete(Contrainte c) {
		DAOFactory.getInstance().getContrainteDAO().delete(c);
	}

	public static void deleteByIdCreneau(Integer id) {
		DAOFactory.getInstance().getContrainteDAO().deleteByIdCreneau(id);
	}

	public static void deleteByIdPlanning(Integer id) {
		DAOFactory.getInstance().getContrainteDAO().deleteByIdPlanning(id);
	}

	public static void escapeSpecialChars(List<Contrainte> contraintes) {
		for (final Contrainte c : contraintes) {
			// c.setMessage(escapeHtml(c.getMessage()));
		} // TODO LOL
	}
}

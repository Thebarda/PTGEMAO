package fr.gemao.ctrl.administration;

import java.util.List;

import fr.gemao.entity.administration.Modification;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.administration.ModificationDAO;
import fr.gemao.sql.exception.DAOException;

public final class ModificationCtrl {

	private ModificationCtrl() {

	}

	public static boolean ajouterModification(Modification modification) {
		ModificationDAO dao = DAOFactory.getInstance().getModificationDAO();

		try {
			dao.create(modification);
		} catch (DAOException exc) {
			System.out.println(exc);
			return false;
		}

		return true;
	}

	public static Modification getModification(int idModification) {
		ModificationDAO dao = DAOFactory.getInstance().getModificationDAO();
		Modification modif = null;

		try {
			modif = dao.get(idModification);
		} catch (DAOException exc) {
			System.out.println(exc);
		}

		return modif;
	}

	public static List<Modification> getAllModifications() {
		ModificationDAO dao = DAOFactory.getInstance().getModificationDAO();
		List<Modification> liste = null;

		try {
			liste = dao.getAll();
		} catch (DAOException exc) {
			System.out.println(exc);
		}

		return liste;
	}
}

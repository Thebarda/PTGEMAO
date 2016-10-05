/**
 * 
 */
package fr.gemao.ctrl.adherent;

import java.util.List;

import fr.gemao.entity.adherent.Famille;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.adherent.FamilleDAO;

/**
 * Ce controleur contient toutes les operations relatives à une {@link Famille}
 * 
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public class FamilleCtrl {

	public static Famille AjouterFamile(Famille famille) {
		Famille fam;
		FamilleDAO familleDAO = DAOFactory.getInstance().getFamilleDAO();

		fam = familleDAO.exits(famille);
		if (fam == null) {
			famille = familleDAO.create(famille);
			return famille;
		} else {
			return fam;
		}
	}

	public static List<Famille> recupererAllFamille() {
		List<Famille> list;
		FamilleDAO familleDAO = DAOFactory.getInstance().getFamilleDAO();
		list = familleDAO.getAll();
		return list;
	}
}

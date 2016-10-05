/**
 * 
 */
package fr.gemao.ctrl;

import java.util.List;

import fr.gemao.entity.Jour;
import fr.gemao.sql.DAOFactory;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public class JourCtrl {

	public List<Jour> getAllJours() {
		return DAOFactory.getInstance().getJourDAO().getAll();
	}

	public Jour getJour(Integer id) {
		return DAOFactory.getInstance().getJourDAO().get(id);
	}
}

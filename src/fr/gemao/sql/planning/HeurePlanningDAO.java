/**
 * 
 */
package fr.gemao.sql.planning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.gemao.entity.cours.Salle;
import fr.gemao.entity.util.HeurePlanning;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public class HeurePlanningDAO extends IDAO<HeurePlanning> {

	
	public HeurePlanningDAO(DAOFactory factory) {
		super(factory);
	}

	
	@Override
	public HeurePlanning create(HeurePlanning obj) {
		return null;
	}

	
	@Override
	public void delete(HeurePlanning obj) {
		
	}

	
	@Override
	public HeurePlanning update(HeurePlanning obj) {
		return null;
	}

	
	@Override
	public HeurePlanning get(long id) {
		HeurePlanning heure = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT idHeure, heure, minute FROM heure_creneau WHERE idHeure = ?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				heure = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return heure;
	}

	
	@Override
	public List<HeurePlanning> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	protected HeurePlanning map(ResultSet result) throws SQLException {
		
		return new HeurePlanning(NumberUtil.getResultInteger(result, "heure"),
				NumberUtil.getResultInteger(result, "minute"));
	}

}

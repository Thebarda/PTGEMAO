/**
 * 
 */
package fr.gemao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.Jour;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public class JourDAO extends IDAO<Jour> {

	public JourDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public Jour create(Jour obj) {
		return null;
	}

	@Override
	public void delete(Jour obj) {

	}

	@Override
	public Jour update(Jour obj) {
		return null;
	}

	@Override
	public Jour get(long id) {
		Jour jour = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM jour WHERE idJour = ?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				jour = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return jour;
	}

	@Override
	public List<Jour> getAll() {
		List<Jour> jours = new ArrayList<>();

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "SELECT idJour, nomJour " + "FROM jour ;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				jours.add(this.map(result));
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return jours;
	}

	public List<Jour> getLunVen() {
		List<Jour> jours = new ArrayList<>();

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "SELECT idJour, nomJour " + "FROM jour WHERE idJour < 8;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				jours.add(this.map(result));
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return jours;
	}

	@Override
	protected Jour map(ResultSet result) throws SQLException {

		return new Jour(NumberUtil.getResultInteger(result, "idJour"), result.getString("nomJour"));
	}

}

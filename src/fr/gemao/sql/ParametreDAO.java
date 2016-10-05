package fr.gemao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.gemao.entity.Parametre;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.DateUtil;

public class ParametreDAO extends IDAO<Parametre> {

	public ParametreDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public Parametre create(Parametre obj) {
		String sql = "INSERT INTO parametre (alloc2, alloc3, alloc4, "
				+ "alloc5, qf_max, qf_min, dateModif) VALUES (?,?,?,?,?,?,?);";
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, true, obj.getAlloc2(), obj.getAlloc3(),
					obj.getAlloc4(), obj.getAlloc5(), obj.getQf_max(),
					obj.getQf_min(), DateUtil.toSqlDate(obj.getDateModif()));
			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création de paramètre, aucune ligne ajoutée dans la table.");
			}

			result = requete.getGeneratedKeys();
			long id;
			if (result != null && result.first()) {
				id = result.getLong(1);
				obj.setIdParam(id);
			}
		} catch (SQLException e) {
			throw new DAOException(sql);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return obj;
	}

	@Override
	public void delete(Parametre obj) {
		throw new DAOException("Not implemented method");
	}

	@Override
	public Parametre update(Parametre obj) {
		throw new DAOException("Not implemented method");
	}

	@Override
	public Parametre get(long id) {
		throw new DAOException("Not implemented method");
	}

	@Override
	public List<Parametre> getAll() {
		throw new DAOException("Not implemented method");
	}

	/**
	 * Retourne les derniers paramètres.
	 * 
	 * @return paramètres actuels
	 */
	public Parametre getLast() {
		Parametre parametre = null;
		Connection connection = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM parametre "
				+ "WHERE dateModif >= ALL(Select dateModif FROM parametre);";
		try {
			connection = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connection,
					sql, false);
			result = requete.executeQuery();
			if (result.first()) {
				parametre = this.map(result);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connection);
		}
		return parametre;
	}

	@Override
	protected Parametre map(ResultSet result) throws SQLException {
		return new Parametre(result.getLong("idParam"),
				result.getFloat("alloc2"), result.getFloat("alloc3"),
				result.getFloat("alloc4"), result.getFloat("alloc5"),
				result.getFloat("qf_min"), result.getFloat("qf_max"),
				result.getTimestamp("dateModif"));
	}

}

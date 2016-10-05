package fr.gemao.sql.administration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.administration.Modification;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.DateUtil;

public class ModificationDAO extends IDAO<Modification> {

	public ModificationDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public Modification create(Modification obj) {
		if (obj == null) {
			throw new NullPointerException("L'objet ne doit pas être null");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "INSERT INTO modif(idPersonne, dateModif, libelle)"
				+ "VALUES (?, ?, ?);";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, obj.getPersonne().getIdPersonne(), DateUtil.toSqlDate(obj.getDateModif()), obj.getLibelle());

			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création de la modification, aucune ligne ajoutée dans la table.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	@Override
	public void delete(Modification obj) {
		throw new DAOException("Pas de suppression possible des modifications");
	}

	@Override
	public Modification update(Modification obj) {
		throw new DAOException("Pas de mise à jour possible des modifications");
	}

	@Override
	public Modification get(long id) {
		Modification modification = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM modif WHERE idModif = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				modification = this.map(result);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return modification;
	}

	@Override
	public List<Modification> getAll() {
		List<Modification> liste = new ArrayList<>();

		Modification modification = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM modif;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				modification = this.map(result);
				liste.add(modification);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	@Override
	protected Modification map(ResultSet result) throws SQLException {
		Integer idPersonne = result.getInt("idPersonne");

		return new Modification(
				result.getInt("idModif"),
				factory.getPersonnelDAO().get(idPersonne),
				result.getTimestamp("dateModif"),
				result.getString("libelle"));
	}

}

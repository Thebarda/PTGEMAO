package fr.gemao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.personnel.MotifFinContrat;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;

public class MotifFinContratDAO extends IDAO<MotifFinContrat> {

	public MotifFinContratDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public MotifFinContrat create(MotifFinContrat obj) {
		if (obj == null) {
			throw new NullPointerException(
					"Le motif de fin de contrat ne doit pas être null");
		}
		int id = 0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO motiffincontrat(libelle)" + "VALUES (?);";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, true, obj.getIdMotif());

			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création du motif de fin de contrat, aucune ligne ajoutée dans la table.");
			}

			result = requete.getGeneratedKeys();
			if (result != null && result.first()) {
				id = result.getInt(1);
			}

			obj.setIdMotif(id);
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	@Override
	public void delete(MotifFinContrat obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public MotifFinContrat update(MotifFinContrat obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public MotifFinContrat get(long id) {
		MotifFinContrat motif = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM motiffincontrat WHERE idMotif = ?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				motif = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return motif;
	}

	@Override
	public List<MotifFinContrat> getAll() {
		List<MotifFinContrat> liste = new ArrayList<>();

		MotifFinContrat motif = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM motiffincontrat;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				motif = this.map(result);
				liste.add(motif);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	@Override
	protected MotifFinContrat map(ResultSet result) throws SQLException {
		return new MotifFinContrat(NumberUtil.getResultInteger(result,
				"idMotif"), result.getString("libelle"));
	}

}

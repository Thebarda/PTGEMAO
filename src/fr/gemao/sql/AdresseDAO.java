package fr.gemao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.Adresse;
import fr.gemao.entity.Commune;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;

public class AdresseDAO extends IDAO<Adresse> {

	public AdresseDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public Adresse create(Adresse obj) {
		if (obj == null) {
			throw new NullPointerException(
					"L'adresse ne doit pas etre null");
		}

		Integer id = 0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO adresse(idCommune, numRue, nomRue, infoCompl)"
				+ "VALUES (?, ?, ?, ?);";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, true, obj.getCommune().getIdCommune(), obj.getNumRue(),
					obj.getNomRue(), obj.getInfoCompl());
			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création d'une adresse, aucune ligne ajoutée dans la table.");
			}

			result = requete.getGeneratedKeys();
			if (result != null && result.first()) {
				id = result.getInt(1);
				obj.setIdAdresse(id);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	@Override
	public void delete(Adresse obj) {
		if (obj == null) {
			throw new NullPointerException("L'adresse ne doit pas etre null");
		}

		Connection connexion = null;
		Statement stat = null;
		try {
			connexion = factory.getConnection();
			stat = connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stat.execute("DELETE FROM adresse WHERE idAdresse = "
					+ obj.getIdAdresse() + ";");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(stat, connexion);
		}
	}

	@Override
	public Adresse update(Adresse obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public Adresse get(long id) {
		Adresse adresse = null;

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM adresse WHERE idAdresse = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				adresse = this.map(result);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return adresse;
	}

	@Override
	public List<Adresse> getAll() {
		List<Adresse> liste = new ArrayList<>();

		Adresse adresse = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM adresse;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				adresse = this.map(result);
				liste.add(adresse);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}
	
	public Adresse exist(Adresse adresse){
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * from adresse where idCommune = ? and numRue = ? and nomRue = ? and infoCompl = ?;";
		Adresse verif = null;
		Commune commune = adresse.getCommune();
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, commune==null?null:commune.getIdCommune(),
					adresse.getNumRue(), adresse.getNomRue(), adresse.getInfoCompl());
			result = requete.executeQuery();

			if (result.first()) {
				verif = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return verif;
	}

	@Override
	protected Adresse map(ResultSet result) throws SQLException {
		Integer idCommune = result.getInt("idCommune");
		return new Adresse(result.getInt("idAdresse"),
				idCommune==null?null:factory.getCommuneDAO().get(idCommune),
				result.getString("numRue"),
				result.getString("nomRue"), result.getString("infoCompl"));
	}

}

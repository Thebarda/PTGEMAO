package fr.gemao.sql.adherent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.adherent.MotifSortie;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;

public class MotifSortieDAO extends IDAO<MotifSortie> {

	public MotifSortieDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public MotifSortie create(MotifSortie obj) {
		if (obj == null) {
			throw new NullPointerException("Le motif ne doit pas être null");
		}
		int id = 0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO motifsortie(libelle)"
				+ "VALUES (?);";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, true, obj.getLibelle());

			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création du motif, aucune ligne ajoutée dans la table.");
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
	public void delete(MotifSortie obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public MotifSortie update(MotifSortie obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public MotifSortie get(long id) {
		MotifSortie motif = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM motifsortie WHERE idMotif = ?;";
		try {
			
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, id);
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
	public List<MotifSortie> getAll() {
		List<MotifSortie> liste = new ArrayList<>();

		MotifSortie motif = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM motifsortie;";
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
	
	public MotifSortie exist(MotifSortie motifSortie) {
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * from motifsortie where libelle = ?;";
		MotifSortie verif = null;
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, motifSortie.getLibelle());
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
	protected MotifSortie map(ResultSet result) throws SQLException {
		return new MotifSortie(result.getInt("idMotif"), result.getString("libelle"));
	}

}

package fr.gemao.sql.materiel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.materiel.Etat;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;

public class EtatDAO extends IDAO<Etat> {

	public EtatDAO(DAOFactory conn) {
		super(conn);
	}

	@Override
	public Etat create(Etat obj) {
		if (obj == null) {
			throw new NullPointerException("L'etat ne doit pas etre null");
		}

		long id = 0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO etat(idEtat, libelle)" + "VALUES (?, ?);";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, true, obj.getIdEtat(), obj.getLibelleEtat());
			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création d'un état, aucune ligne ajoutée dans la table.");
			}

			result = requete.getGeneratedKeys();
			if (result != null && result.first()) {
				id = result.getLong(1);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return this.get(id);
	}

	@Override
	public void delete(Etat obj) {
		if (obj == null) {
			throw new NullPointerException("L'etat ne doit pas etre null");
		}

		if (obj.getIdEtat() == 0) {
			throw new NullPointerException("L'etat ne peut pas avoir un id = 0");
		}

		Connection connexion = null;
		Statement stat = null;
		try {
			connexion = factory.getConnection();
			stat = connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stat.execute("DELETE FROM etat WHERE idEtat = " + obj.getIdEtat()
					+ ";");
		} catch (SQLException e) {
			throw new DAOException("Suppression impossible");
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(stat, connexion);
		}
	}

	@Override
	public Etat update(Etat obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public Etat get(long id) {
		Etat etat = null;

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM etat WHERE idEtat = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				etat = this.map(result);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return etat;
	}
	
	public Etat get(String libEtat) {
		Etat etat = null;

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM etat WHERE libelle = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, libEtat);
			result = requete.executeQuery();

			if (result.first()) {
				etat = this.map(result);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return etat;
	}

	@Override
	public List<Etat> getAll() {
		List<Etat> liste = new ArrayList<>();

		Etat etat = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM etat;";
		try {
			connexion = DAOFactory.getInstance().getConnection();
			requete = connexion.prepareStatement(sql);
			result = requete.executeQuery();

			while (result.next()) {
				etat = this.map(result);
				liste.add(etat);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return liste;
	}
	
	public Etat exist(Etat etat){
		return this.get(etat.getLibelleEtat());
	}

	@Override
	protected Etat map(ResultSet result) throws SQLException {

		return new Etat(result.getInt("idEtat"), result.getString("libelle"));
	}

}

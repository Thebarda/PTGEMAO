package fr.gemao.sql.materiel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.materiel.Designation;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;

public class DesignationDAO extends IDAO<Designation> {
	public DesignationDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public Designation create(Designation obj) {
		if (obj == null) {
			throw new NullPointerException(
					"La designation ne doit pas etre null");
		}

		long id = 0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "INSERT INTO designation(libelle)" + "VALUES (?);";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, obj.getLibelleDesignation());

			int status = requete.executeUpdate();
			if (status == 0) {
				throw new DAOException(
						"Échec de la création de la designation, aucune ligne ajoutée dans la table.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return this.get(id);
	}

	@Override
	public void delete(Designation obj) {

		if (obj == null) {
			throw new NullPointerException(
					"La designation ne doit pas etre null");
		}

		if (obj.getIdDesignation() == 0) {
			throw new IllegalArgumentException(
					"La designation ne peut pas avoir un id = 0");
		}

		Statement stat = null;
		Connection connect = null;
		try {
			connect = factory.getConnection();
			stat = connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stat.execute("DELETE FROM designation WHERE idDesignation = "
					+ obj.getIdDesignation() + ";");
		} catch (SQLException e) {
			throw new DAOException("Suppression impossible");
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(stat, connect);
		}

	}

	@Override
	public Designation update(Designation obj) {
		if (obj == null) {
			throw new NullPointerException(
					"La Designation ne doit pas etre nul");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "UPDATE designation SET libelle = ?"
				+ "WHERE idDesignation = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, obj.getLibelleDesignation(),
					obj.getIdDesignation());
			requete.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return this.get(obj.getIdDesignation());
	}

	@Override
	public Designation get(long id) {
		Designation designation = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		try {
			String sql = "SELECT * FROM designation WHERE idDesignation = ?;";
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				designation = new Designation(result.getInt("idDesignation"),
						result.getString("libelle"));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return designation;
	}

	public Designation get(String libDes) {
		Designation designation = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		try {
			String sql = "SELECT * FROM designation WHERE libelle = ?;";
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, libDes);
			result = requete.executeQuery();

			if (result.first()) {
				designation = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return designation;
	}

	@Override
	public List<Designation> getAll() {
		List<Designation> liste = new ArrayList<>();
		Connection connexion = null;
		Designation designation = null;

		PreparedStatement requete = null;
		ResultSet result = null;
		try {
			String sql = "SELECT * FROM designation;";
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				designation = this.map(result);
				liste.add(designation);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	public Designation exist(Designation desigantion) {
		return this.get(desigantion.getLibelleDesignation());
	}

	@Override
	protected Designation map(ResultSet result) throws SQLException {
		return new Designation(result.getInt("idDesignation"),
				result.getString("libelle"));
	}
}

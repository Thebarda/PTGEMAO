package fr.gemao.sql.cours;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.cours.Niveau;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;

public class NiveauDAO extends IDAO<Niveau> {

	public NiveauDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public Niveau create(Niveau obj) {
		if (obj == null) {
			throw new NullPointerException(
					"Le niveau ne doit pas être null");
		}
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		Integer id = null;
		String sql = "INSERT INTO niveau(nomNiveau) " + "VALUES (?);";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, true, obj.getNomNiveau());

			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création du niveau, aucune ligne ajoutée dans la table.");
			}
			
			result = requete.getGeneratedKeys();
			if (result != null && result.first()) {
				id = result.getInt(1);
				obj.setIdNiveau(id);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	@Override
	public void delete(Niveau obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public Niveau update(Niveau obj) {
		throw new UnsupportedOperationException("Méthode non implémentée.");
	}

	@Override
	public Niveau get(long id) {
		Niveau niveau = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM niveau WHERE idNiveau = ?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				niveau = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return niveau;
	}
	
	public Niveau get(String nom) {
		Niveau niveau = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM niveau WHERE nomNiveau = ?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, nom.trim());
			result = requete.executeQuery();

			if (result.first()) {
				niveau = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return niveau;
	}

	@Override
	public List<Niveau> getAll() {
		List<Niveau> liste = new ArrayList<>();

		Niveau nievau = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM niveau;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				nievau = this.map(result);
				liste.add(nievau);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return liste;
	}

	public Niveau exist(Niveau niveau) {
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * from niveau where nomNiveau = ?";
		Niveau verif = null;
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, niveau.getNomNiveau());
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
	protected Niveau map(ResultSet result) throws SQLException {
		return new Niveau(NumberUtil.getResultInteger(result, "idNiveau"), 
				result.getString("nomNiveau"));
	}

}

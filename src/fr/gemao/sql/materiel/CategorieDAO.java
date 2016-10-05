package fr.gemao.sql.materiel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.materiel.Categorie;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;

public class CategorieDAO extends IDAO<Categorie> {

	public CategorieDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public Categorie create(Categorie obj) {
		if (obj == null) {
			throw new NullPointerException("La categorie ne doit pas etre null");
		}

		long id = 0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "INSERT INTO categorie(libelle, estLouable)"
				+ "VALUES (?, ?);";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, obj.getLibelleCat(), obj.isEstLouable());

			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création de la categorie, aucune ligne ajoutée dans la table.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return this.get(id);
	}

	@Override
	public void delete(Categorie obj) {
		Connection connexion = null;
		ResultSet result = null;
		if (obj == null) {
			throw new NullPointerException("La categorie ne doit pas etre null");
		}

		if (obj.getIdCategorie() <= 0) {
			throw new IllegalArgumentException(
					"La categorie ne peut pas avoir un id <= 0");
		}

		PreparedStatement stat = null;
		try {
			connexion = factory.getConnection();
			String sql = "DELETE FROM categorie WHERE idCategorie = "
					+ obj.getIdCategorie();
			stat = DAOUtilitaires.initialisationRequetePreparee(connexion, sql,
					false);

			int status = stat.executeUpdate();
			if (status == 0) {
				throw new DAOException(
						"Échec de la suppression de la categorie, aucune modification dans la table.");
			}

		} catch (SQLException e) {
			throw new DAOException("Suppression impossible");
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, stat, connexion);
		}

	}

	@Override
	public Categorie update(Categorie obj) {
		if (obj == null) {
			throw new NullPointerException("La catégorie ne doit pas être nul");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "UPDATE categorie SET libelle = ?, estLouable = ?"
				+ "WHERE idCategorie = ?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, obj.getLibelleCat(), obj.isEstLouable(), obj.getIdCategorie());
			requete.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return this.get(obj.getIdCategorie());
	}

	@Override
	public Categorie get(long id) {
		Categorie categorie = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "SELECT * FROM categorie WHERE idCategorie = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, id);

			result = requete.executeQuery();

			if (result.first()) {
				categorie = new Categorie(result.getInt("idCategorie"),
						result.getString("libelle"), result.getBoolean("estLouable"));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return categorie;
	}
	public Categorie get(String nomCat) {
		Categorie categorie = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "SELECT * FROM categorie WHERE libelle = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, nomCat);

			result = requete.executeQuery();

			if (result.first()) {
				categorie = new Categorie(result.getInt("idCategorie"),
						result.getString("libelle"), result.getBoolean("estLouable"));
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return categorie;
	}
	@Override
	public List<Categorie> getAll() {
		List<Categorie> liste = new ArrayList<>();

		Categorie categorie = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "SELECT * FROM categorie";
		try {
			connexion = DAOFactory.getInstance().getConnection();
			requete = connexion.prepareStatement(sql);
			result = requete.executeQuery();

			while (result.next()) {
				categorie = new Categorie(result.getInt("idCategorie"),
						result.getString("libelle"), result.getBoolean("estLouable"));
				liste.add(categorie);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	@Override
	protected Categorie map(ResultSet result) throws java.sql.SQLException {
		return new Categorie(result.getInt("idCategorie"), result.getString("libelle"), result.getBoolean("estLouable"));
	}

}

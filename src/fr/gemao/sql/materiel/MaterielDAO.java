package fr.gemao.sql.materiel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.materiel.Materiel;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;

public class MaterielDAO extends IDAO<Materiel> {

	public MaterielDAO(DAOFactory conn) {
		super(conn);
	}

	@Override
	public Materiel create(Materiel obj) {
		if (obj == null) {
			throw new NullPointerException("Le materiel ne doit pas �tre null");
		}

		long id = 0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO materiel(idEtat," + "idCategorie,"
				+ "idMarque," + "idDesignation," + "idFournisseur,"
				+ "typeMateriel," + "numSerie," + "dateAchat," + "valeurAchat,"
				+ "valeurReapprov," + "deplaceConcert,"
				+ "observation, quantite, estLouable)"
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

		Integer idCategorie = null;
		Integer idMarque = null;
		Integer idDesignation = null;
		Integer idFournisseur = null;
		try {
			if (obj.getCategorie() != null) {
				idCategorie = obj.getCategorie().getIdCategorie();
			}
			if (obj.getMarque() != null) {
				idMarque = obj.getMarque().getIdMarque();
			}
			if (obj.getDesignation() != null) {
				idDesignation = obj.getDesignation().getIdDesignation();
			}
			if (obj.getFournisseur() != null) {
				idFournisseur = obj.getFournisseur().getIdFournisseur();
			}
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, true, obj.getEtat().getIdEtat(), idCategorie,
					idMarque, idDesignation, idFournisseur, obj.getTypeMat(),
					obj.getNumSerie(), obj.getDateAchat(),
					obj.getValeurAchat(), obj.getValeurReap(),
					obj.isDeplacable(), obj.getObservation(),
					obj.getQuantite(), obj.isLouable());
			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException(
						"Échec de la création de matériel, aucune ligne ajoutée dans la table.");
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
	public void delete(Materiel obj) {
		if (obj == null) {
			throw new NullPointerException("Le materiel ne doit pas être null");
		}

		if (obj.getIdMateriel() == 0) {
			throw new NullPointerException(
					"Le materiel ne peut pas avoir un id = 0");
		}

		Connection connexion = null;
		Statement stat = null;
		try {
			connexion = factory.getConnection();
			stat = connexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_UPDATABLE);
			stat.execute("DELETE FROM materiel WHERE idMateriel = "
					+ obj.getIdMateriel() + ";");
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			if (stat != null) {
				DAOUtilitaires.fermeturesSilencieuses(stat, connexion);
			}
		}

	}

	@Override
	public Materiel update(Materiel obj) {
		if (obj == null) {
			throw new NullPointerException("Le matériel ne doit pas être nul");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "UPDATE materiel SET idEtat = ?,"
				+ "idCategorie = ?, idMarque = ?, idDesignation = ?, idFournisseur = ?,"
				+ "typeMateriel = ?, numSerie = ?, dateAchat = ?,"
				+ "valeurAchat = ?, valeurReapprov = ?, deplaceConcert = ?,"
				+ "observation = ?, quantite = ?, estLouable=? WHERE idMateriel = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, obj.getEtat().getIdEtat(), obj.getCategorie()
							.getIdCategorie(), obj.getMarque().getIdMarque(),
					obj.getDesignation().getIdDesignation(), obj
							.getFournisseur().getIdFournisseur(), obj
							.getTypeMat(), obj.getNumSerie(), obj
							.getDateAchat(), obj.getValeurAchat(), obj
							.getValeurReap(), obj.isDeplacable(), obj
							.getObservation(), obj.getQuantite(), obj
							.isLouable(), obj.getIdMateriel());
			int status = requete.executeUpdate();
			if (status == 0) {
				throw new DAOException(
						"La mise à jour de materiel n'a pas eu lieu.");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return this.get(obj.getIdMateriel());
	}

	@Override
	public Materiel get(long id) {
		Materiel materiel = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM materiel WHERE idMateriel = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				materiel = this.map(result);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return materiel;
	}

	@Override
	public List<Materiel> getAll() {
		List<Materiel> liste = new ArrayList<>();

		Materiel materiel = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM materiel;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion,
					sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				materiel = this.map(result);
				liste.add(materiel);
			}
		} catch (SQLException e1) {
			throw new DAOException(e1);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	@Override
	protected Materiel map(ResultSet result) throws SQLException {
		Integer idMarque = result.getInt("idMarque"), idFournisseur = result
				.getInt("idFournisseur");

		return new Materiel(result.getLong("idMateriel"), factory.getEtatDAO()
				.get(result.getInt("idEtat")), factory.getCategorieDAO().get(
				result.getInt("idCategorie")), idMarque == null ? null
				: factory.getMarqueDAO().get(idMarque), factory
				.getDesignationDAO().get(result.getInt("idDesignation")),
				idFournisseur == null ? null : factory.getFournisseurDAO().get(
						idFournisseur), result.getString("typeMateriel"),
				result.getString("numSerie"), result.getDate("dateAchat"),
				result.getFloat("valeurAchat"),
				result.getFloat("ValeurReapprov"),
				result.getBoolean("deplaceConcert"),
				result.getString("observation"), result.getInt("quantite"),
				result.getBoolean("estLouable"));
	}

}

package fr.gemao.sql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.Commune;
import fr.gemao.entity.Personne;
import fr.gemao.entity.util.Civilite;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.DateUtil;
import fr.gemao.sql.util.NumberUtil;

public class PersonneDAO extends IDAO<Personne> {

	public PersonneDAO(DAOFactory factory) {
		super(factory);
	}

	@Override
	public Personne create(Personne obj) {
		if (obj == null) {
			throw new NullPointerException("La personne ne doit pas être null");
		}

		long id = 0;

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO personne(idAdresse, idCommuneNaiss, nom, prenom,"
				+ "	dateNaissance, tel_fixe, tel_port, email, sexe, membreCA)"
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		Integer idAdresse = null;
		Integer idCommuneNaiss = null;
		try {

			connexion = factory.getConnection();
			if (obj.getAdresse() != null) {
				idAdresse = obj.getAdresse().getIdAdresse();
			}
			if (obj.getCommuneNaiss() != null) {
				idCommuneNaiss = obj.getCommuneNaiss().getIdCommune();
			}
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, true, idAdresse, idCommuneNaiss,
					obj.getNom(), obj.getPrenom(), DateUtil.toSqlDate(obj.getDateNaissance()), obj.getTelFixe(),
					obj.getTelPort(), obj.getEmail(), obj.getCivilite().getSexe(), obj.isMembreCA());

			int status = requete.executeUpdate();
			if (status == 0) {
				throw new DAOException("Échec de la création de la personne, aucune ligne ajoutée dans la table.");
			}

			result = requete.getGeneratedKeys();
			if (result != null && result.first()) {
				id = result.getLong(1);
				obj.setIdPersonne(id);
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	@Override
	public void delete(Personne obj) {
		/*
		 * if (obj == null) { throw new NullPointerException(
		 * "La personne ne doit pas �tre null"); }
		 * 
		 * if (obj.getIdPersonne() == 0) { throw new NullPointerException(
		 * "La personne ne peut pas avoir un id = 0"); }
		 * 
		 * Statement stat = null; try { stat =
		 * connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
		 * ResultSet.CONCUR_UPDATABLE); stat.execute(
		 * "DELETE FROM personne WHERE idPersonne = " + obj.getIdPersonne() +
		 * ";"); } catch (SQLException e) { e.printStackTrace(); } finally {
		 * if(stat != null ){ try { stat.close(); } catch (SQLException e) {
		 * e.printStackTrace(); } } }
		 */
		throw new UnsupportedOperationException("Vous n'avez pas le droit de supprimer une Personne.");
	}

	@Override
	public Personne update(Personne obj) {
		if (obj == null) {
			throw new NullPointerException("La personne ne doit pas �tre null");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "UPDATE personne SET idAdresse = ?, idCommuneNaiss = ?, nom = ?, "
				+ "prenom = ?, dateNaissance = ?, tel_fixe = ?, tel_port = ?, "
				+ "email = ?, sexe = ?, membreCA = ? WHERE idPersonne = ?;";
		Integer idAdresse = null;
		Integer idCommuneNaiss = null;
		try {

			connexion = factory.getConnection();
			if (obj.getAdresse() != null) {
				idAdresse = obj.getAdresse().getIdAdresse();
			}
			if (obj.getCommuneNaiss() != null) {
				idCommuneNaiss = obj.getCommuneNaiss().getIdCommune();
			}
			System.out.println("IsCA ? " + obj.isMembreCA());
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, true, idAdresse, idCommuneNaiss,
					obj.getNom(), obj.getPrenom(), new Date(obj.getDateNaissance().getTime()), obj.getTelFixe(),
					obj.getTelPort(), obj.getEmail(), obj.getCivilite().getSexe(), obj.isMembreCA(),
					obj.getIdPersonne());
			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException("Échec de la mise à jour de la personne, aucune ligne ajoutée dans la table.");
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return this.get(obj.getIdPersonne());
	}

	@Override
	public Personne get(long id) {
		Personne personne = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM personne WHERE idPersonne = ?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				personne = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return personne;
	}

	@Override
	public List<Personne> getAll() {
		List<Personne> liste = new ArrayList<>();

		Personne personne = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM `personne` where idPersonne NOT IN (select idPersonne FROM `adherent`);";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				personne = this.map(result);
				liste.add(personne);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	public Personne exist(Personne personne) {
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * from personne where nom = ? and prenom = ?"
				+ " and idCommuneNaiss = ? and dateNaissance = ?" + " and sexe = ?;";
		Personne verif = null;
		Integer idCommuneNaiss = null;
		Civilite civilite = personne.getCivilite();
		try {

			connexion = factory.getConnection();
			if (personne.getCommuneNaiss() != null) {
				idCommuneNaiss = personne.getCommuneNaiss().getIdCommune();
			}
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, personne.getNom(),
					personne.getPrenom(), idCommuneNaiss, personne.getDateNaissance(),
					civilite == null ? null : civilite.getSexe());
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
	public Personne map(ResultSet result) throws SQLException {
		AdresseDAO adresseDAO = factory.getAdresseDAO();
		CommuneDAO communeDAO = factory.getCommuneDAO();
		Integer idCommuneNaiss = NumberUtil.getResultInteger(result, "idCommuneNaiss");
		Commune commune = null;
		if (idCommuneNaiss != null) {
			commune = communeDAO.get(idCommuneNaiss);
		}
		return new Personne(Long.valueOf(result.getInt("idPersonne")),
				adresseDAO.get(NumberUtil.getResultInteger(result, "idAdresse")), commune, result.getString("nom"),
				result.getString("prenom"), result.getDate("dateNaissance"), result.getString("tel_fixe"),
				result.getString("tel_port"), result.getString("email"),
				(result.getString("sexe").equals("M") ? Civilite.MONSIEUR : Civilite.MADAME),
				result.getBoolean("membreCA"));

	}

	public String getIdByNomAndPrenom(String nom, String prenom) {
		String idPersonne = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT idPersonne FROM `personne` where nom = ? AND prenom = ?;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, nom, prenom);
			result = requete.executeQuery();

			while (result.next()) {
				idPersonne = ""+result.getLong("idPersonne");
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return idPersonne;
	}

}

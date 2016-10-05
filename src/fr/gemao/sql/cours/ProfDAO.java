/**
 * 
 */
package fr.gemao.sql.cours;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.administration.Profil;
import fr.gemao.entity.cours.Discipline;
import fr.gemao.entity.cours.Prof;
import fr.gemao.entity.personnel.Contrat;
import fr.gemao.entity.personnel.Diplome;
import fr.gemao.entity.personnel.Personnel;
import fr.gemao.entity.personnel.Responsabilite;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.PersonnelDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public class ProfDAO extends IDAO<Prof> {

	/**
	 * @param factory
	 */
	public ProfDAO(DAOFactory factory) {
		super(factory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.gemao.sql.IDAO#create(java.lang.Object)
	 */
	@Override
	public Prof create(Prof obj) {
		if (obj == null) {
			throw new NullPointerException("Le prof ne doit pas être null");
		}

		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "INSERT INTO prof(idPersonne, dateDebutEnseignement)" + "VALUES (?, ?);";

		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, obj.getIdPersonne(),
					obj.getDateDebutEnseignement());

			int status = requete.executeUpdate();

			if (status == 0) {
				obj = null;
			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.gemao.sql.IDAO#delete(java.lang.Object)
	 */
	@Override
	public void delete(Prof obj) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.gemao.sql.IDAO#update(java.lang.Object)
	 */
	@Override
	public Prof update(Prof obj) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.gemao.sql.IDAO#get(long)
	 */
	@Override
	public Prof get(long id) {
		Prof prof = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM prof WHERE idPersonne = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				prof = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return prof;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.gemao.sql.IDAO#getAll()
	 */
	@Override
	public List<Prof> getAll() {
		List<Prof> liste = new ArrayList<>();

		Prof prof = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM prof;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				prof = this.map(result);
				liste.add(prof);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}

		return liste;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.gemao.sql.IDAO#map(java.sql.ResultSet)
	 */
	@Override
	protected Prof map(ResultSet result) throws SQLException {
		PersonnelDAO personnelDAO = DAOFactory.getInstance().getPersonnelDAO();
		final Integer idPersonne = NumberUtil.getResultInteger(result, "idPersonne");
		final java.sql.Date dateDebutEnseignement = result.getDate("dateDebutEnseignement");

		Personnel personnel = personnelDAO.get(idPersonne);

		if (personnel == null) {
			throw new NullPointerException("Ce professeur n'est pas un membre du personnel, erreur.");
		}

		List<Responsabilite> responsabilite = personnel.getListeResponsabilite() != null
				? personnel.getListeResponsabilite() : null;
		List<Diplome> diplome = personnel.getListeDiplomes() != null ? personnel.getListeDiplomes() : null;
		List<Discipline> discipline = personnel.getListeDiscipline() != null ? personnel.getListeDiscipline() : null;
		List<Contrat> contrat = personnel.getContrat() != null ? personnel.getContrat() : null;
		String login = personnel.getLogin() != null ? personnel.getLogin() : null;
		String password = personnel.getPassword() != null ? personnel.getPassword() : null;
		Integer pointsAncien = Integer.valueOf(personnel.getPointsAncien());
		Profil profil = personnel.getProfil() != null ? personnel.getProfil() : null;
		boolean premiereConnexion = personnel.isPremiereConnexion();
		String numeroSS = personnel.getNumeroSS() != null ? personnel.getNumeroSS() : null;

		Prof prof = new Prof(personnel, responsabilite, diplome, discipline, contrat, login, password, pointsAncien,
				profil, premiereConnexion, dateDebutEnseignement, numeroSS);

		return prof;

	}

}

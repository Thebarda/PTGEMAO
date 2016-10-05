/**
 * 
 */
package fr.gemao.sql.cours;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.gemao.entity.cours.Cours;
import fr.gemao.sql.DAOFactory;
import fr.gemao.sql.IDAO;
import fr.gemao.sql.exception.DAOException;
import fr.gemao.sql.util.DAOUtilitaires;
import fr.gemao.sql.util.NumberUtil;

/**
 * @author FELTON-GROS-METAYER-PIAT-VAREILLE
 *
 */
public class CoursDAO extends IDAO<Cours> {

	/**
	 * @param factory
	 */
	public CoursDAO(DAOFactory factory) {
		super(factory);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.gemao.sql.IDAO#create(java.lang.Object)
	 */
	@Override
	public Cours create(Cours obj) {
		if (obj == null) {
			throw new NullPointerException("Le cours ne peut etre null");
		}

		Integer id = 0;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;

		String sql = "INSERT INTO cours(idDiscipline, idSalle, idProf)" + " VALUES (?, NULL, ?); ";

		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, true,
					obj.getDiscipline() != null ? obj.getDiscipline().getIdDiscipline() : null,
					obj.getProf() != null ? obj.getProf().getIdPersonne() : null);

			int status = requete.executeUpdate();

			if (status == 0) {
				throw new DAOException("Échec de la création d'un cours, aucune ligne ajoutée dans la table");
			}

			result = requete.getGeneratedKeys();

			if (result != null && result.first()) {
				id = result.getInt(1);
				obj.setIdCours(id);
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
	public void delete(Cours obj) {
		// 1 - récupérer tous les créneaux ayant ce cours.
		// 2 - Supprimer tous les contraintes utilisées par ces créneaux
		// 3 - Supprimer tous les cours utilisant ce cours.
		// 4 - Supprimer ce cours.
		// TODO
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.gemao.sql.IDAO#update(java.lang.Object)
	 */
	@Override
	public Cours update(Cours obj) {
		if (obj == null) {
			throw new NullPointerException("Le cours ne peut etre null");
		}

		Connection connexion = null;
		Statement stat = null;
		PreparedStatement requete = null;
		try {
			String sql = "UPDATE cours SET idProf = ?, idDiscipline = ? WHERE idCours = ?";

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, true,
					obj.getProf() != null ? obj.getProf().getIdPersonne() : null,
					obj.getDiscipline() != null ? obj.getDiscipline().getIdDiscipline() : null, obj.getIdCours());

			int status = requete.executeUpdate();

			if (status == 0) {
				obj = null;

			}

		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(stat, connexion);
		}
		return obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.gemao.sql.IDAO#get(long)
	 */
	@Override
	public Cours get(long id) {
		Cours cours = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM cours WHERE idCours = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, id);
			result = requete.executeQuery();

			if (result.first()) {
				cours = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return cours;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.gemao.sql.IDAO#getAll()
	 */
	@Override
	public List<Cours> getAll() {
		List<Cours> liste = new ArrayList<>();

		Cours cours = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM cours;";
		try {

			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false);
			result = requete.executeQuery();

			while (result.next()) {
				cours = this.map(result);
				liste.add(cours);
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
	protected Cours map(ResultSet result) throws SQLException {
		final SalleDAO salleDAO = DAOFactory.getInstance().getSalleDAO();
		final ProfDAO profDAO = DAOFactory.getInstance().getProfDAO();
		final DisciplineDAO disciplineDAO = DAOFactory.getInstance().getDisciplineDAO();

		final Integer idCours = NumberUtil.getResultInteger(result, "idCours");
		;
		final Integer idDiscipline = NumberUtil.getResultInteger(result, "idDiscipline");
		final Integer idSalle = NumberUtil.getResultInteger(result, "idSalle");
		final Integer idProf = NumberUtil.getResultInteger(result, "idProf");

		return new Cours(idCours, idDiscipline != null ? disciplineDAO.get(idDiscipline) : null,
				idSalle != null ? salleDAO.get(idSalle) : null, idProf != null ? profDAO.get(idProf) : null);
	}

	/**
	 * @param idPlanning
	 * @return
	 */
	public List<Cours> getByIdPlanning(Integer idPlanning) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param idDiscipline
	 * @param idProf
	 * @return
	 */
	public Cours getByDisciplineAndProf(Integer idDiscipline, Integer idProf) {
		Cours cours = null;
		Connection connexion = null;
		PreparedStatement requete = null;
		ResultSet result = null;
		String sql = "SELECT * FROM cours WHERE idProf = ? AND idDiscipline = ?;";
		try {
			connexion = factory.getConnection();
			requete = DAOUtilitaires.initialisationRequetePreparee(connexion, sql, false, idProf, idDiscipline);
			result = requete.executeQuery();

			if (result.first()) {
				cours = this.map(result);
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		} finally {
			DAOUtilitaires.fermeturesSilencieuses(result, requete, connexion);
		}
		return cours;
	}

}
